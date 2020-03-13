package gr.interamerican.bo2.odftoolkit.utils;

import java.util.List;

import org.odftoolkit.odfdom.dom.OdfMetaDom;
import org.odftoolkit.odfdom.dom.element.meta.MetaUserDefinedElement;
import org.odftoolkit.simple.TextDocument;

/**
 * Utilities for user defined document properties.
 */
public class UserDefinedPropertiesUtils {
	
	/**
	 * Gets the custom properties of a document.
	 *
	 * @param doc the doc
	 * @return Returns a list with the properties.
	 * @throws Exception the exception
	 */
	public static List<MetaUserDefinedElement> getUserDefinedProperties(TextDocument doc) throws Exception {
		OdfMetaDom meta = doc.getMetaDom();
		return OdfUtils.getElements(meta, MetaUserDefinedElement.class);
	}
	
	/**
	 * Gets the custom property with the specified name.
	 *
	 * @param doc the doc
	 * @param name the name
	 * @return Returns the property if it exists, otherwise returns null.
	 * @throws Exception the exception
	 */
	public static MetaUserDefinedElement getUserDefinedProperty(TextDocument doc, String name) throws Exception {		
		List<MetaUserDefinedElement> list = getUserDefinedProperties(doc);
		for (MetaUserDefinedElement element : list) {
			if (element.getMetaNameAttribute().equals(name)) {
				return element;
			}
		}
		return null;
	}
	
	

}
