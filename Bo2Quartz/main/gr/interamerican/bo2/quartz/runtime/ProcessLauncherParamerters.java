package gr.interamerican.bo2.quartz.runtime;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;


/**
 * pramaters for the {@link ProcessLauncher}.
 */
public interface ProcessLauncherParamerters {

	/**
	 * Gets the environment.
	 *
	 * @return the {@link Map} for the environment.
	 */
	Map<String, String> getEnvironment();

	/**
	 * Sets the environment.
	 *
	 * @param env the env
	 */
	void setEnvironment(Map<String, String> env);
	
	/**
	 * Gets the out stream.
	 *
	 * @return the outputstream
	 */
	PrintStream getOutStream();

	/**
	 * Sets the out stream.
	 *
	 * @param stream the new out stream
	 */
	void setOutStream(PrintStream stream);

	/**
	 * Gets the main class.
	 *
	 * @return the main class
	 */
	Class<?> getMainClass();

	/**
	 * Sets the main class.
	 *
	 * @param c the new main class
	 */
	void setMainClass(Class<?> c);

	/**
	 * Gets the memory settings.
	 *
	 * @return the memory settings
	 */
	MemorySetting getMemorySettings();

	/**
	 * Sets the memory settings.
	 *
	 * @param setting the new memory settings
	 */
	void setMemorySettings(MemorySetting setting);
	
	/**
	 * Gets the execution path.
	 *
	 * @return the executions path.
	 */
	String getExecutionPath();

	/**
	 * Sets the execution path.
	 *
	 * @param path the new execution path
	 */
	void setExecutionPath(String path);

	/**
	 * Gets the system variables.
	 *
	 * @return the system variables
	 */
	Map<String, String> getSystemVariables();

	/**
	 * sets the system variables.
	 *
	 * @param vars the vars
	 */
	void setSystemVariables(Map<String, String> vars);

	/**
	 * Gets the classpath files.
	 *
	 * @return the classpath files
	 */
	List<File> getClasspathFiles();

	/**
	 * sets the classpath files. If the list is null or empty the current process classpath files
	 * will be used.
	 *
	 * @param files the new classpath files
	 */
	void setClasspathFiles(List<File> files);
}
