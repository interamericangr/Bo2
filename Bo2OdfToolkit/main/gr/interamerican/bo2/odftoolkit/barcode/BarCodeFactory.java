package gr.interamerican.bo2.odftoolkit.barcode;

import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.HumanReadablePlacement;

/**
 * Factory for barcodes.
 */
public class BarCodeFactory {
	
	/**
	 * BarCode4JHelper object.
	 */
	BarCode4JHelper helper = new BarCode4JHelper();
	
	/**
	 * Gets the image mime type of the barcode created by this factory.
	 *  
	 * @return Returns a string containing the mime type
	 */
	public String getImageType() {
		return helper.getMimeType();
	}

	
	/**
	 * Creates a barcode as a PNG image.
	 * 
	 * @param code
	 *        String to transform to barcode
	 * @param type
	 *        Barcode type 
	 * @param hrp 
	 *        Placement for human readable message
	 *        
	 * @return Returns a byte array that contains a PNG image
	 *         containing the barcode.
	 * 
	 */
	public byte[] getBarCode(String code, BarCodeType type, HumanReadablePlacement hrp) {
		BarcodeGenerator bean = type.getBarCodeGenerator();		
		helper.setHrp(hrp);
		helper.init(bean);		
		return helper.getBarCode(code, bean);
	}
	
	/**
	 * Creates a barcode as a PNG image.
	 *
	 * @param code        String to transform to barcode
	 * @param type        Barcode type 
	 * @return Returns a byte array that contains a PNG image
	 *         containing the barcode.
	 */
	public byte[] getBarCode(String code, BarCodeType type) {
		return getBarCode(code, type, HumanReadablePlacement.HRP_NONE);
	}
	

}
