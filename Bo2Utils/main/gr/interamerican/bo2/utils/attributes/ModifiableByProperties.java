package gr.interamerican.bo2.utils.attributes;

import java.util.Properties;

/**
 * An object that gets modified according to a Properties object.
 */
public interface ModifiableByProperties {
	
	/**
	 * Modifies this object according to the specified properties.
	 * 
	 * @param properties
	 */
	public void beModified(Properties properties);

}
