package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.List;

import org.odftoolkit.odfdom.dom.element.meta.MetaUserDefinedElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.Document;

/**
 * 
 */
public class MetaUtils {
	
	/**
	 * XPath expression to fetch all nodes with specific style.
	 * 
	 * @param name 
	 *        Name of style.
	 *        
	 * @return Returns the xpath expression.
	 */
	@SuppressWarnings("nls")
	static String propertiesWithName(String name) {
		String template = "*//meta:user-defined[@meta:name=%s]";
		String param = StringUtils.surround(name, StringConstants.DOUBLE_QUOTE);
		String expression = String.format(template, param);
		return expression;				
	}
	
	/**
	 * Gets the user defined property of the specified document
	 * that has the specified name.
	 * 
	 * @param doc
	 *        Document
	 * @param name
	 *        Name of user defined property
	 * 
	 * @return Returns a the specified property if it exists.
	 *         Otherwise returns null.
	 *         
	 * @throws Exception 
	 */
	public static String getProperty(Document doc, String name) 
	throws Exception {				
		String xpathExpr = propertiesWithName(name);
		List<OdfElement> list = OdfUtils.getXpath(doc.getMetaDom(),xpathExpr);
		if (list.isEmpty()) {
			return null;
		}
		MetaUserDefinedElement element = (MetaUserDefinedElement)list.get(0);		
		String text = element.getTextContent();
		return text;
	}

}
