package gr.interamerican.bo2.quartz.runtime;

/**
 * memory setting for {@link ProcessLauncher}
 */
public interface MemorySetting {

	/**
	 * @param m
	 *            minimum memory setting (in MB)
	 */
	void setMinMemory(Long m);

	/**
	 * @return minimum memory setting (in MB)
	 */
	Long getMinMemory();

	/**
	 * @param m
	 *            maximum memory setting (in MB)
	 */
	void setMaxMemory(Long m);

	/**
	 * @return maximum memory setting (in MB)
	 */
	Long getMaxMemory();

	/**
	 * @param pg
	 *            permanent generation setting (in MB)
	 */
	void setPermGen(Long pg);

	/**
	 * @return permanent generation setting (in MB)
	 */
	Long getPermGen();
}
