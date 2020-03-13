package gr.interamerican.bo2.utils.meta;

/**
 * Owner of a {@link BusinessObjectDescriptor}.
 */
public interface BusinessObjectDescriptorOwner {
	
	/**
	 * Gets the business object descriptor.
	 *
	 * @return Returns the owned BusinessObjectDescriptor
	 */
	public BusinessObjectDescriptor<?> getBusinessObjectDescriptor();

}
