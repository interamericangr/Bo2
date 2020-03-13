package gr.interamerican.bo2.odftoolkit.utils;

import java.util.List;

import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.pkg.OdfAttribute;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;

/**
 * Utilities for Frames.
 */
public class FrameUtils {
	
	/**
	 * Name attribute.
	 */
	private static final OdfName NAME_ATTRIBUTE = 
		OdfName.newName(OdfDocumentNamespace.DRAW,"name"); //$NON-NLS-1$
	
	
	/**
	 * Gets a list with all DrawFrameElement of the specified OdfFileDom.
	 *
	 * @param dom the dom
	 * @return Returns a list with DrawFrameElement
	 */
	public static List<DrawFrameElement> getDrawFrameElements(OdfFileDom dom) {		
		return OdfUtils.getElements(dom, DrawFrameElement.class);
	}
	
	
	
	/**
	 * Gets the name of a DrawFrameElement.
	 *
	 * @param element the element
	 * @return returns the name.
	 */
	public static String getName(DrawFrameElement element) {	
		OdfAttribute attribute = element.getOdfAttribute(NAME_ATTRIBUTE);
		if (attribute!=null) {
			return attribute.getValue();
		}		
		return null;
	}	
	
	/**
	 * Gets the DrawFrameElement with the specified name.
	 *
	 * @param dom the dom
	 * @param name the name
	 * @return Returns the DrawFrameElement with the specified element
	 *         if it exists in the specified dom, otherwise returns
	 *         <code>null</code>
	 */
	public static DrawFrameElement getByName(OdfFileDom dom, String name) {
		List<DrawFrameElement> frames = getDrawFrameElements(dom);
		for (DrawFrameElement element : frames) {			
			String frameName = getName(element); 
			if (name.equals(frameName)) {
				return element;				
			}
		}
		return null;
	}
	
	

	
	

}
