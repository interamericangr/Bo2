package gr.interamerican.bo2.odftoolkit.barcode;

import gr.interamerican.bo2.odftoolkit.utils.FrameUtils;
import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.krysalis.barcode4j.HumanReadablePlacement;


/**
 * Unit test for {@link FrameUtils}.
 */
public class TestBarcodeFactory {

	
	
	
	/**
	 * Generic test for getBarCode().
	 *
	 * @param type the type
	 * @param hrp the hrp
	 * @throws IOException Signals that an I/O exception has occurred.
	 */	
	@SuppressWarnings("nls")
	void testGetBarcode(BarCodeType type, HumanReadablePlacement hrp) throws IOException {
		BarCodeFactory bcf = new BarCodeFactory();
		String value = "1234567890";		
		byte[] bytes;
		String hrpname="";
		if (hrp==null) {
			bytes = bcf.getBarCode(value, type);			
		} else {
			bytes = bcf.getBarCode(value, type, hrp);
			hrpname = "_MSG";
		}
		
		 		
		Assert.assertTrue(bytes.length!=0);
		String filename = type.toString()+hrpname+".png"; 
		String path = ResourceUtils.outputPath(filename);
		File file = new File(path);
		StreamUtils.saveToFile(bytes, file);
	}
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_code39() throws IOException {
		testGetBarcode(BarCodeType.CODE39,null);
	}
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_code39_HRP() throws IOException {
		testGetBarcode(BarCodeType.CODE39,HumanReadablePlacement.HRP_BOTTOM);
	}
	
	
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_code128() throws IOException {
		testGetBarcode(BarCodeType.CODE128,null);
	}
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_code128_HRP() throws IOException {
		testGetBarcode(BarCodeType.CODE128,HumanReadablePlacement.HRP_BOTTOM);
	}
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_codabar() throws IOException {
		testGetBarcode(BarCodeType.CODABAR,null);
	}
	
	/**
	 * Test for getBarCode().
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetBarcode_codabar_HRP() throws IOException {
		testGetBarcode(BarCodeType.CODABAR,HumanReadablePlacement.HRP_BOTTOM);
	}
	
	
	
	
	
	


}
