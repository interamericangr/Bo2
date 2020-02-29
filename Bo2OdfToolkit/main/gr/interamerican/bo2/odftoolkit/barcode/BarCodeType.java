package gr.interamerican.bo2.odftoolkit.barcode;

import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;

/**
 * Supported barcode types.
 */
public enum BarCodeType {
	
	/**
	 * Code 39.  
	 */
	CODE39(new Code39Bean()),
	
	/**
	 * Code 128.  
	 */
	CODE128(new Code128Bean()),
	
	
	/**
	 * Codabar.
	 */
	CODABAR(new CodabarBean());
	
	/*
	 * List of not yet supported barcodes.
	 * 
	Interleaved(null),
	ITF_14(null),
	UPC_A(null),
	UPC_E(null),
	EAN_8(null),
	EAN_13(null),
	POSTNET(null),
	Royal_Mail_Customer_Barcode(null),
	USPS_Intelligent_Mail(null),
	PDF417(null),
	DataMatrix_sqare(null),
	DataMatrix_rectangular(null),
	QR_Code(null);
	*/
	
	
	
	
	
	
	/**
	 * BarCode generator for this barcode type.
	 */
	AbstractBarcodeBean barCodeGenerator;
	
	/**
	 * Creates a new BarCodeType.
	 *
	 * @param barCodeGenerator the bar code generator
	 */
	private BarCodeType(AbstractBarcodeBean barCodeGenerator) {
		this.barCodeGenerator = barCodeGenerator;
	}

	/**
	 * Gets the barCodeGenerator.
	 *
	 * @return Returns the barCodeGenerator
	 */
	public BarcodeGenerator getBarCodeGenerator() {
		return barCodeGenerator;
	}
	
	

}
