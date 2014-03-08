package gr.interamerican.bo2.utils.meta;

/**
 * Owner of a {@link BusinessObjectDescriptor}
 */
public interface BusinessObjectDescriptorOwner {
	
	/**
	 * @return Returns the owned BusinessObjectDescriptor
	 */
	public BusinessObjectDescriptor<?> getBusinessObjectDescriptor();

}
