package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.runtime.MultiLauncher;
import gr.interamerican.bo2.quartz.QuartzJobSchedulerImpl;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * launches a {@link MultiLauncher} as a separate process.
 */
public class ProcessLauncher {

	/**
	 * launches a separate process.
	 *
	 * @param environment
	 * @param args
	 * @throws DataException
	 */
	public static synchronized void launch(Map<String, String> environment, String... args)
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
		attachProcessInputStream(p);
	}

	/**
	 * @param process
	 * @throws DataException
	 */
	private static void attachProcessInputStream(Process process) throws DataException {
		JobScheduler jsp = new QuartzJobSchedulerImpl();
		JobDescription bean = Factory.create(JobDescription.class);
		bean.setOperationClass(StreamRedirectOperation.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("process", process); //$NON-NLS-1$
		bean.setParameters(params);
		List<JobDescription> jobDescriptions = new ArrayList<JobDescription>();
		jobDescriptions.add(bean);
		jsp.submitJobs(jobDescriptions);
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
	 * @throws DataException
	 */
	public static void launchMultilauncher(Class<?> clazz) throws DataException {

		ProcessLauncher.launch(null, getJavaExecutable(), "-classpath", getClasspath(), //$NON-NLS-1$
				MultiLauncher.class.getName(), clazz.getName());
	}

}
