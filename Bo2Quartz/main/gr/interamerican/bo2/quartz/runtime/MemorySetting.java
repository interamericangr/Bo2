package gr.interamerican.bo2.quartz.runtime;

/**
 * memory setting for {@link ProcessLauncher}
 */
public interface MemorySetting {

	/**
	 * @param m
	 */
	void setMinMemory(Long m);

	/**
	 * @return min memory
	 */
	Long getMinMemory();

	/**
	 * @param m
	 */
	void setMaxMemory(Long m);

	/**
	 * @return max memory
	 */
	Long getMaxMemory();

	/**
	 * @param pg
	 */
	void setPermGen(Long pg);

	/**
	 * @return perm gen
	 */
	Long getPermGen();
}
