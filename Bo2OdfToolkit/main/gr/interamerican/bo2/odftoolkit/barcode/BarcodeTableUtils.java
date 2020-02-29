package gr.interamerican.bo2.odftoolkit.barcode;

import gr.interamerican.bo2.odftoolkit.utils.UserDefinedPropertiesUtils;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.util.HashMap;
import java.util.Map;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.odftoolkit.odfdom.dom.element.meta.MetaUserDefinedElement;
import org.odftoolkit.simple.TextDocument;


/**
 * Utility class that contains constants specifying parameters of
 * tables to be filled with a barcode and relevant methods.
 * 
 */
@SuppressWarnings("nls")
public class BarcodeTableUtils {
	
	/**
	 * Name of the custom property that defines the type of a barcode in a document.
	 * For each barcode table there must be a user defined property with the name
	 * table name + .type that defines the barcode type.
	 * Example: If the barcode that fills the table with name barcode_a, must be a
	 * CODABAR barcode, then the document must have a user defined property with name 
	 * <code>barcode_a.type</code> and value <code>CODABAR</code>. 
	 * If this user defined property is not set, then the default barcode type 
	 * will be created. The default barcode type should be specified in the 
	 * Bo2 deployment properties. If this is not specified, then the default 
	 * barcode type is CODE128.     
	 */
	public static final String TYPE_PROPERTY = ".type";
	
	/**
	 * Name of the custom property that defines the human readable message place.
	 * For each barcode table there must be a user defined property with the name
	 * table name + .msg that defines the human readable message place.
	 * Example: If the barcode that fills the table with name barcode_a, must be have
	 * a human readable message on top, then the document must have a user defined 
	 * property with name <code>barcode_a.msg</code> and value <code>TOP</code>.
	 * If this user defined property is not set, then no message will be created.
	 * Allowed values for this property are TOP, BOTTOM, NONE. 
	 *       
	 */
	public static final String MSG_PROPERTY = ".msg";
	
	/**
	 * Value for message on TOP.
	 */
	public static final String MSG_TOP = "TOP";
	
	/**
	 * Value for message on BOTTOM.
	 */
	public static final String MSG_BOTTOM = "BOTTOM";
	
	/**
	 * Value for no message.
	 */
	public static final String MSG_NONE = "NONE";
	
	/** The Constant placements. */
	private static final Map<String, HumanReadablePlacement> placements = 
		new HashMap<String, HumanReadablePlacement>();
	
	static {
		placements.put(MSG_TOP, HumanReadablePlacement.HRP_TOP);
		placements.put(MSG_BOTTOM, HumanReadablePlacement.HRP_BOTTOM);
	}
	
	/**
	 * Default barcode type.
	 * 
	 * @return Returns the default barcode type.
	 */
	static final BarCodeType getDefaultBarcodeType() {
		return BarCodeType.CODE128;
	}
	
	
	/**
	 * Gets the barcode type.
	 *
	 * @param tablename the tablename
	 * @param doc the doc
	 * @return Returns the barcode type.
	 * @throws DocumentEngineException the document engine exception
	 */
	public static BarCodeType getBarcodeType(String tablename, TextDocument doc) 
	throws DocumentEngineException {		
		String property = tablename+TYPE_PROPERTY;
		MetaUserDefinedElement ude = getUserDefinedElement(doc,property);
		if (ude==null) {
			return getDefaultBarcodeType();
		}
		
		String value = ude.getTextContent();
		if (value!=null) {
			value = value.toUpperCase();
		}
		
		try {
			return BarCodeType.valueOf(value);
		} catch (IllegalArgumentException ilae) {
			String msg = "Invalid barcode type: " + value; //$NON-NLS-1$
			throw new DocumentEngineException(msg);
		}	
	}
	
	/**
	 * Gets the barcode type.
	 *
	 * @param tablename the tablename
	 * @param doc the doc
	 * @return Returns the barcode type.
	 * @throws DocumentEngineException the document engine exception
	 */
	public static HumanReadablePlacement getHumanReadablePlacement(String tablename, TextDocument doc) 
	throws DocumentEngineException {		
		String property = tablename+MSG_PROPERTY;
		MetaUserDefinedElement ude = getUserDefinedElement(doc,property);
		if (ude==null) {
			return HumanReadablePlacement.HRP_NONE;
		}
		
		String value = ude.getTextContent();
		if (value!=null) {
			value = value.toUpperCase();
		}
		HumanReadablePlacement hrp = placements.get(value);
		if (hrp==null) {
			hrp = HumanReadablePlacement.HRP_NONE;
		}
		return hrp;
	}
	
	/**
	 * Gets the MetaUserDefinedElement with the specified name.
	 *
	 * @param doc the doc
	 * @param name the name
	 * @return Returns the element
	 * @throws DocumentEngineException the document engine exception
	 */
	private static MetaUserDefinedElement getUserDefinedElement (TextDocument doc, String name) 
	throws DocumentEngineException {	
		try {
			return UserDefinedPropertiesUtils.getUserDefinedProperty(doc,name);
		} catch (Exception e) {			
			throw new DocumentEngineException(e);
		}
	}
	

}
