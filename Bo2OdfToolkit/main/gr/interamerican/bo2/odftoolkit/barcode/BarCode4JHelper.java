package gr.interamerican.bo2.odftoolkit.barcode;

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.handlers.ConcreteMethodInvocator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * Helper class for BarCode4J.
 */
public class BarCode4JHelper {
	
	/**
	 * DPI resolution.
	 */
	int resolution = 150;
	
	/**
	 * Mime type.
	 */
	String mimeType = "image/x-png"; //$NON-NLS-1$

	/**
	 * Orientation landscape.
	 */
	int orientation = 0;
	
	/**
	 * anti aliasing.
	 */
	boolean antialiasing = false;
	
	/**
	 * Human readable placement.
	 */
	HumanReadablePlacement hrp = HumanReadablePlacement.HRP_NONE;

	
	
	/**
	 * Sets the hrp.
	 *
	 * @param hrp the hrp to set
	 */
	public void setHrp(HumanReadablePlacement hrp) {
		this.hrp = hrp;
	}

	/**
	 * Selects the initializer method for each type of AbstractBarcodeBean.
	 */
	TypeBasedSelection<ConcreteMethodInvocator> initializerSelector = 
			new TypeBasedSelection<ConcreteMethodInvocator>();
	
	
	
	
	
	/**
	 *  Creates a new BarCode4JHelper object.
	 */
	@SuppressWarnings("nls")
	BarCode4JHelper() {
		super();		
		ConcreteMethodInvocator generic = new ConcreteMethodInvocator("initGeneric", this);
		ConcreteMethodInvocator code39 = new ConcreteMethodInvocator("initCode39", this);
		initializerSelector.registerSelection(Code39Bean.class, code39);
		initializerSelector.registerSelection(AbstractBarcodeBean.class, generic);
	}
	
	/**
	 * Gets a CanvasProvider for creating a barcode as a PNG image.
	 * 
	 * @param stream
	 *        OutputStream to write the barcode image.
	 *         
	 * @return Returns the CanvasProvider.
	 */
	BitmapCanvasProvider getCanvas(OutputStream stream) {
	    return new BitmapCanvasProvider
	    	(stream, mimeType, resolution, BufferedImage.TYPE_BYTE_BINARY, antialiasing, orientation);		
	}
	
	
	/**
	 * Creates the barcode.
	 *
	 * @param code the code
	 * @param gen the gen
	 * @return Returns a byte array that contains the barcode.
	 */
	byte[] getBarCode(String code, BarcodeGenerator gen)  {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();		
		BitmapCanvasProvider canvas = getCanvas(stream);		    
	    gen.generateBarcode(canvas, code);	   
	    try {
			canvas.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	    byte[] bytes = stream.toByteArray();
	    return bytes;
	}
	
	
	/**
	 * Initializes an AbstractBarCodeBean.
	 * 
	 * Initialization depends on the concrete type of the bean.
	 * 
	 * @param bean
	 *        BarcodeGenerator to initialize.
	 *       
	 */
	void init(BarcodeGenerator bean) {
		ConcreteMethodInvocator cmi = initializerSelector.select(bean);
		if (cmi!=null) {
			Object[] args = {bean};
			cmi.setArguments(args);
			cmi.execute();			
		}
	}
	
	/**
	 * Initialize an AbstractBarcodeBean.
	 *
	 * @param bean the bean
	 */
	void initGeneric(AbstractBarcodeBean bean) {		
		bean.setModuleWidth(UnitConv.in2mm(1.0f / resolution)); //makes the narrow bar width exactly one pixel
		boolean quietzone = !(hrp==HumanReadablePlacement.HRP_NONE);
		bean.doQuietZone(quietzone);
		bean.setMsgPosition(hrp);
	}
	
	/**
	 * Initialize a Code39Bean.
	 *
	 * @param bean the bean
	 */
	void initCode39(Code39Bean bean) {		
		initGeneric(bean);
		bean.setWideFactor(3);
	}
	
	/**
	 * Gets the mimeType.
	 *
	 * @return Returns the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}
	
	

}
