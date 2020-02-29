package gr.interamerican.bo2.quartz.runtime;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.runtime.MultiLauncher;
import gr.interamerican.bo2.quartz.QuartzJobSchedulerImpl;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.SystemUtils;
import jpb.DefaultEntryPoint;
import jpb.JavaProcessBuilder;
import jpb.MemArg;
import jpb.command.FixedPathSelector;

/**
 * launches a {@link MultiLauncher} as a separate process.
 */
public class ProcessLauncher {

	/**
	 * indicates the operation to attach to a process generated for the new jvm.
	 */
	private static final Class<? extends Operation> OPERATION2ATTACH = StreamRedirectOperation.class;
	/**
	 * property to set the process object.
	 */
	private static final String PROCESS_PROPERTY = "process"; //$NON-NLS-1$
	/**
	 * property for the outputstream.
	 */
	private static final String PRINT_STREAM = "outputStream"; //$NON-NLS-1$

	/** The Constant CLASS_ENVIRONMENT_PROPERTY. */
	private static final String CLASS_ENVIRONMENT_PROPERTY = "java.class.path"; //$NON-NLS-1$

	/** java home. */
	private static final String JAVA_HOME_ENVIRONMENT_PROPERTY = "java.home"; //$NON-NLS-1$

	/** user dir. */
	private static final String USER_DIR_ENVIRONMENT_PROPERTY = "user.dir"; //$NON-NLS-1$
	/**
	 * properties map.
	 */
	protected static Map<String, Class<?>> propertiesMap = new HashMap<String, Class<?>>();
	static {
		propertiesMap.put(PROCESS_PROPERTY, Process.class);
		propertiesMap.put(PRINT_STREAM, PrintStream.class);
	}

	/**
	 * set to contain all spawn process.
	 */
	private static Set<Process> spawnedProcesses = new HashSet<Process>();

	/**
	 * launches a separate process.
	 *
	 * @param params the params
	 * @param args the args
	 * @return the created process
	 * @throws DataException the data exception
	 */
	public static synchronized JobDescription launch(ProcessLauncherParamerters params,
			String... args) throws DataException {
		Process p = launchJavaProcess(params, args);
		JobDescription bean = attachProcessInputStream(OPERATION2ATTACH, p, params.getOutStream());
		return bean;
	}

	/**
	 * sets the execution path (base directory of the jvm to be instantiated).
	 *
	 * @param jpb the jpb
	 * @param params the params
	 */
	static void setExecutionPath(JavaProcessBuilder jpb, ProcessLauncherParamerters params) {
		if ((jpb == null) || (params == null) || (params.getExecutionPath() == null)) {
			return;
		}
		String executionPath = params.getExecutionPath();
		File execPath = new File(executionPath);
		if (execPath.exists() && execPath.isDirectory()) {
			jpb.workingDirectory(execPath);
			jpb.setSystemProperty(USER_DIR_ENVIRONMENT_PROPERTY, execPath.getAbsolutePath());
		}
	}

	/**
	 * sets the memory to {@link JavaProcessBuilder}.
	 *
	 * @param jpb the jpb
	 * @param params the params
	 */
	static void setMemoryToJavaProcessBuilder(JavaProcessBuilder jpb, ProcessLauncherParamerters params) {
		if ((jpb == null) ){
			return;
		}
		// set memory defaults.
		jpb.minMemory(MemArg.of(64).megaBytes());
		jpb.maxMemory(MemArg.of((long) SystemUtils.maxMemory()).megaBytes());
		if ((params == null) || (params.getMemorySettings() == null)) {
			return;
		}
		if (params.getMemorySettings().getMaxMemory() != null) {
			jpb.maxMemory(MemArg.of(params.getMemorySettings().getMaxMemory()).megaBytes());
		}
		if (params.getMemorySettings().getMinMemory() != null) {
			jpb.minMemory(MemArg.of(params.getMemorySettings().getMinMemory()).megaBytes());
		}
	}

	/**
	 * sets the system variables.
	 *
	 * @param jpb the jpb
	 * @param params the params
	 */
	static void setSystemVariables(JavaProcessBuilder jpb, ProcessLauncherParamerters params) {
		if ((jpb == null) || (params == null)
				|| ((params.getSystemVariables() == null) || (CollectionUtils.isNullOrEmpty(params.getSystemVariables().entrySet())))) {
			return;
		}
		for (Entry<String,String> entry : params.getSystemVariables().entrySet()) {
			jpb.setSystemProperty(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * launches a java process with the given environment, main class and user arguments.
	 *
	 * @param params the params
	 * @param args the args
	 * @return the process generated
	 * @throws DataException the data exception
	 */
	protected static Process launchJavaProcess(ProcessLauncherParamerters params, String... args) throws DataException {
		JavaProcessBuilder jpb = new JavaProcessBuilder();
		if (params.getEnvironment() != null) {
			Map<String, String> env = jpb.environment();
			env.putAll(params.getEnvironment());
		}
		jpb.redirectErrorStream(true);
		jpb.entryPoint(DefaultEntryPoint.mainClass(params.getMainClass()));
		for (String property : System.getProperties().stringPropertyNames()) {// set the same system properties
			String value = System.getProperty(property);
			jpb.setSystemProperty(property, value);
		}
		jpb.addApplicationArguments(args);
		// addDebugArguments(jpb);
		if (CollectionUtils.isNullOrEmpty(params.getClasspathFiles())) {
			jpb.classPath(getClasspathFiles());// set classpath
		} else {
			jpb.systemProperties().remove(CLASS_ENVIRONMENT_PROPERTY);
			jpb.classPath(params.getClasspathFiles());
		}
		jpb.commandSelector(new FixedPathSelector(getJavaExecutable()));// set java executable
		setMemoryToJavaProcessBuilder(jpb, params);
		setExecutionPath(jpb, params);
		setSystemVariables(jpb, params);
		Process p = null;
		try {
			p = jpb.start();
		} catch (IOException e) {
			throw new DataException(e);
		}
		spawnedProcesses.add(p);
		return p;

	}


	/**
	 * Generate parameter map.
	 *
	 * @param objs the objs
	 * @return the parameter map for the StreamredirectOperation
	 */
	protected static Map<String, Object> generateParameterMap(Object... objs) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object object : objs) {
			for (String key : propertiesMap.keySet()) {
				Class<?> value = propertiesMap.get(key);
				if ((object != null) && value.isAssignableFrom(object.getClass())) {
					params.put(key, object);
				}
			}
		}
		return params;
	}

	/**
	 * Attach process input stream.
	 *
	 * @param operation2attach the operation 2 attach
	 * @param objs            parameters required to be passed at the {@link StreamRedirectOperation}
	 * @return the {@link JobDescription} of the submitted job.
	 * @throws DataException the data exception
	 */
	protected static JobDescription attachProcessInputStream(
			Class<? extends Operation> operation2attach, Object... objs) throws DataException {
		JobScheduler jsp = new QuartzJobSchedulerImpl();
		JobDescription bean = Factory.create(JobDescription.class);
		bean.setOperationClass(operation2attach);
		bean.setParameters(generateParameterMap(objs));
		List<JobDescription> jobDescriptions = new ArrayList<JobDescription>();
		jobDescriptions.add(bean);
		jsp.submitJobs(jobDescriptions);
		return bean;
	}

	/**
	 * Gets the java executable.
	 *
	 * @return the java executable
	 */
	static protected String getJavaExecutable() {
		return System.getProperty(JAVA_HOME_ENVIRONMENT_PROPERTY) + "/bin/java"; //$NON-NLS-1$
	}

	/**
	 * Gets the classpath files.
	 *
	 * @return the classpath files.
	 */
	static protected List<File> getClasspathFiles() {
		URL[] urls = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
		List<File> classpath = new ArrayList<File>();
		for (URL url : urls) {
			classpath.add(new File(url.getFile()));
		}
		return classpath;
	}

	/**
	 * launches a {@link MultiLauncher} as a separate process.
	 *
	 * @param clazz the clazz
	 * @param settings the settings
	 * @return the {@link JobDescription} of the quartz job attached to the created process
	 * @throws DataException the data exception
	 */
	public static JobDescription launchMultilauncher(Class<?> clazz, MemorySetting settings)
			throws DataException {
		ProcessLauncherParamerters params = Factory.create(ProcessLauncherParamerters.class);
		params.setMainClass(MultiLauncher.class);
		params.setMemorySettings(settings);
		JobDescription bean = ProcessLauncher.launch(params, clazz.getName());
		return bean;
	}

	/**
	 * extracts the process from a {@link JobDescription} (if applicable).
	 *
	 * @param bean the bean
	 * @return the process
	 * @throws DataException the data exception
	 */
	public static Process extractProcessFromJobDescription(JobDescription bean)
			throws DataException {
		if (!(OPERATION2ATTACH.isAssignableFrom(bean.getOperationClass()))) {
			throw new DataException(
					"JobDescription bean does not indicate it was attached to process"); //$NON-NLS-1$
		}
		Map<String, Object> params = bean.getParameters();
		Object p = params.get(PROCESS_PROPERTY);
		return (Process) p;
	}

	/**
	 * Exctract stream fromjob description.
	 *
	 * @param bean the bean
	 * @return the PrintStream assigned to the process for output.
	 * @throws DataException the data exception
	 */
	public static PrintStream exctractStreamFromjobDescription(JobDescription bean)
			throws DataException {
		if (!(OPERATION2ATTACH.isAssignableFrom(bean.getOperationClass()))) {
			throw new DataException(
					"JobDescription bean does not indicate it was attached to process"); //$NON-NLS-1$
		}
		Map<String, Object> params = bean.getParameters();
		Object p = params.get(PRINT_STREAM);
		if (p != null) {
			return (PrintStream) p;
		}
		return System.out;
	}

	/**
	 * kills the process that is attached to the given {@link JobDescription} (if applicable).
	 *
	 * @param bean the bean
	 * @return process exit status
	 * @throws DataException the data exception
	 */
	public static int killProcessFromJobDescription(JobDescription bean) throws DataException {
		Process p = extractProcessFromJobDescription(bean);
		p.destroy();
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			throw new DataException(e);
		}
		QuartzUtils.waitJobToComplete(bean);
		spawnedProcesses.remove(p);
		return p.exitValue();
	}

	/**
	 * kills all spawned processes.
	 *
	 * @throws DataException the data exception
	 */
	public static void killallProcesses() throws DataException {
		for (Process p : spawnedProcesses) {
			p.destroy();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				throw new DataException(e);
			}
		}
		spawnedProcesses.clear();
	}
}
