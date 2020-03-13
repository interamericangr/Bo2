package gr.interamerican.bo2.odftoolkit.barcode;

import org.junit.Assert;
import org.junit.Test;
import org.krysalis.barcode4j.HumanReadablePlacement;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;
import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

/**
 * Unit tests for {@link BarcodeTableUtils}.
 */
@SuppressWarnings("nls")
public class TestBarcodeTableUtils {
	
	/**
	 * Unit test for getDefaultBar.
	 */
	public void testGetDefaultBarcodeType() {
		BarCodeType actual = BarcodeTableUtils.getDefaultBarcodeType();
		Assert.assertEquals(BarCodeType.CODE128, actual);
	}
	
	/**
	 * Unit test for getBarcodeType().
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetBarcodeType_withTypeSpecified() throws Exception {		
		String path = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument odf = (OdfToolkitTextDocument) engine.openDocument(path);
		String name = "Barcode_b";
		BarCodeType actual = BarcodeTableUtils.getBarcodeType(name, odf.document);
		Assert.assertEquals(BarCodeType.CODE39, actual);
	}
	
	/**
	 * Unit test for getBarcodeType().
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetBarcodeType_withDefaultType() throws Exception {		
		String path = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument odf = (OdfToolkitTextDocument) engine.openDocument(path);
		String name = "Barcode_c";
		BarCodeType actual = BarcodeTableUtils.getBarcodeType(name, odf.document);
		Assert.assertEquals(BarCodeType.CODE128, actual);
	}
	
	/**
	 * Unit test for getBarcodeType().
	 *
	 * @throws Exception the exception
	 */
	@Test(expected=DocumentEngineException.class)
	public void testGetBarcodeType_withInvalid() throws Exception {		
		String path = ResourceUtils.inputPath("DocumentWithInvalidBarcodeType.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument odf = (OdfToolkitTextDocument) engine.openDocument(path);
		String name = "Barcode_a";
		BarcodeTableUtils.getBarcodeType(name, odf.document);		
	}
	
	
	/**
	 * Test for getHumanReadablePlacement().
	 *
	 * @throws DocumentEngineException the document engine exception
	 */
	@Test
	public void testGetHumanReadablePlacement_withSpecified() throws DocumentEngineException {
		String path = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument odf = (OdfToolkitTextDocument) engine.openDocument(path);
		String name = "Barcode_c";
		HumanReadablePlacement actual = 
			BarcodeTableUtils.getHumanReadablePlacement(name, odf.document);
		Assert.assertEquals(HumanReadablePlacement.HRP_BOTTOM, actual);
	}
	
	/**
	 * Test for getHumanReadablePlacement().
	 *
	 * @throws DocumentEngineException the document engine exception
	 */
	@Test
	public void testGetHumanReadablePlacement_withDefault() throws DocumentEngineException {
		String path = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument odf = (OdfToolkitTextDocument) engine.openDocument(path);
		String name = "Barcode_a";
		HumanReadablePlacement actual = 
			BarcodeTableUtils.getHumanReadablePlacement(name, odf.document);
		Assert.assertEquals(HumanReadablePlacement.HRP_NONE, actual);
	}
}