package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.runtime.MultiLauncher;
import gr.interamerican.bo2.quartz.QuartzJobSchedulerImpl;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * launches a {@link MultiLauncher} as a separate process.
 */
public class ProcessLauncher {

	/**
	 * parameter for classpath for the java executable
	 */
	protected static final String CLASSPATH_PARAM = "-classpath"; //$NON-NLS-1$
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
	 * @param environment
	 * @param out
	 * @param args
	 * @return the created process
	 * @throws DataException
	 */
	public static synchronized JobDescription launch(Map<String, String> environment,
			PrintStream out, String... args) throws DataException {
		Process p = launchProcess(environment, args);
		JobDescription bean = attachProcessInputStream(OPERATION2ATTACH, p, out);
		return bean;
	}

	/**
	 * @param environment
	 * @param args
	 * @return the process generated
	 * @throws DataException
	 */
	protected static Process launchProcess(Map<String, String> environment, String... args)
			throws DataException {
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.redirectErrorStream(true);
		if (environment != null) {
			Map<String, String> env = pb.environment();
			env.putAll(environment);
		}
		Process p = null;
		try {
			p = pb.start();
		} catch (IOException e) {
			throw new DataException(e);
		}
		spawnedProcesses.add(p);
		return p;
	}

	/**
	 * @param objs
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
	 * @param operation2attach
	 * @param objs
	 *            parameters required to be passed at the {@link StreamRedirectOperation}
	 * @return the jobdescription of the submitted job.
	 * @throws DataException
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
	 * @return the java executable
	 */
	static protected String getJavaExecutable() {
		return System.getProperty("java.home") + "/bin/java"; //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * @return the classpath
	 */
	static protected String getClasspath() {
		URL[] urls = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
		String classpath = StringConstants.EMPTY;
		for (URL url : urls) {
			if (classpath.length() > 0) {
				classpath += StringConstants.COLON;
			}
			classpath = classpath + url.getFile();
		}
		return classpath;
	}

	/**
	 * launches a {@link MultiLauncher} as a separate process.
	 *
	 * @param clazz
	 * @return the {@link JobDescription} of the quartz job attached to the created process
	 * @throws DataException
	 */
	public static JobDescription launchMultilauncher(Class<?> clazz) throws DataException {
		JobDescription bean = ProcessLauncher.launch(null, null, getJavaExecutable(),
				CLASSPATH_PARAM, getClasspath(), MultiLauncher.class.getName(), clazz.getName());
		return bean;
	}

	/**
	 * extracts the process from a {@link JobDescription} (if applicable)
	 *
	 * @param bean
	 * @return the process
	 * @throws DataException
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
	 * @param bean
	 * @return the PrintStream assigned to the process for output.
	 * @throws DataException
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
	 * kills the process that is attached to the given {@link JobDescription} (if applicable)
	 *
	 * @param bean
	 * @return process exit status
	 * @throws DataException
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
	 * kills all spawned processes
	 *
	 * @throws DataException
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
