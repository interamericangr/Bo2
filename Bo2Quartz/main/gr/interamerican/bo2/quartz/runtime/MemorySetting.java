package gr.interamerican.bo2.quartz.runtime;

/**
 * memory setting for {@link ProcessLauncher}.
 */
public interface MemorySetting {

	/**
	 * Sets the min memory.
	 *
	 * @param m
	 *            minimum memory setting (in MB)
	 */
	void setMinMemory(Long m);

	/**
	 * Gets the min memory.
	 *
	 * @return minimum memory setting (in MB)
	 */
	Long getMinMemory();

	/**
	 * Sets the max memory.
	 *
	 * @param m
	 *            maximum memory setting (in MB)
	 */
	void setMaxMemory(Long m);

	/**
	 * Gets the max memory.
	 *
	 * @return maximum memory setting (in MB)
	 */
	Long getMaxMemory();

	/**
	 * Sets the perm gen.
	 *
	 * @param pg
	 *            permanent generation setting (in MB)
	 * @deprecated not used in java 8
	 */
	@Deprecated
	void setPermGen(Long pg);

	/**
	 * Gets the perm gen.
	 *
	 * @return permanent generation setting (in MB)
	 * @deprecated not used in java 8
	 */
	@Deprecated
	Long getPermGen();
}
