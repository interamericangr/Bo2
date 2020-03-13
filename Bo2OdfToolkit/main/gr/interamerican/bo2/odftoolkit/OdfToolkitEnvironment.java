package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.util.Properties;

/**
 * Environment variables.
 */
public class OdfToolkitEnvironment {
	
	/**
	 * Instance.
	 */
	private static OdfToolkitEnvironment env = new OdfToolkitEnvironment();
	
	
	/**
	 * Name prefix for tables that serve as placeholders for a barcode.
	 * The value is 'BARCODE' and the prefix is not case sensitive.
	 * Any table in a document that has a name starting with this prefix 
	 * will be filled with a barcode. 
	 */	
	String barcodeTablesNamePrefix; 
	
	/**
	 * Creates a new OdfToolkitEnvironment.
	 *
	 * @param barcodeTablesNamePrefix the barcode tables name prefix
	 */
	private OdfToolkitEnvironment(String barcodeTablesNamePrefix) {
		super();
		this.barcodeTablesNamePrefix = barcodeTablesNamePrefix;
	}
	
	/**
	 * Creates a new OdfToolkitEnvironment.
	 * 
	 */
	@SuppressWarnings("nls")
	private OdfToolkitEnvironment() {
		super();
		Properties p = Bo2UtilsEnvironment.get().getProperties();
		String prefix = p.getProperty("barcodeTablesNamePrefix");
		if (prefix!=null) {
			this.barcodeTablesNamePrefix = prefix;
		} else {
			this.barcodeTablesNamePrefix = "AUTOGENBARCODE";
		}
	}


	

	/**
	 * Gets the barcodeTablesNamePrefix.
	 *
	 * @return Returns the barcodeTablesNamePrefix
	 */
	public String getBarcodeTablesNamePrefix() {
		return barcodeTablesNamePrefix;
	}

	/**
	 * Gets the env.
	 *
	 * @return Returns the env
	 */
	public static OdfToolkitEnvironment get() {
		return env;
	}

	/**
	 * Sets the environment.
	 *
	 * @param barcodeTablesNamePrefix 
	 *        the barcodeTablesNamePrefix to set
	 */
	public static void set(String barcodeTablesNamePrefix) {
		env = new OdfToolkitEnvironment(barcodeTablesNamePrefix);
	}
	
	

	
	
	
	

	

}
