package gr.interamerican.bo2.utils.meta.descriptors;

/**
 * Wrapper of a {@link BoPropertyDescriptor}.
 *
 * @param <T> the generic type
 */
public interface BoPropertyDescriptorWrapper<T> {
	
	/**
	 * Gets the wrapped descriptor.
	 * 
	 * @return Returns the wrapped descriptor.
	 */
	public BoPropertyDescriptor<T> getDescriptor();

}
