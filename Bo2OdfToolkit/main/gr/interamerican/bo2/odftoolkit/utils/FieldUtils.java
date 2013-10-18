/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.odftoolkit.span.TextSpanFragment;
import gr.interamerican.bo2.utils.IllegalCharacterFilter;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.TextDocument;
import org.w3c.dom.Node;

/**
 * Processes a document's user fields.
 */
public class FieldUtils {

	/**
	 * Hidden constructor.
	 */
	private FieldUtils() {/*empty*/}
	
	/**
	 * Replaces a user field with a specified text.
	 * 
	 * @param dom 
	 *        OdfFileDom containing the user field.
	 * @param field
	 *        Field to replace.
	 * @param value
	 *        Value to set to the field. 
	 */
	public static void replaceField(OdfFileDom dom, TextUserFieldGetElement field, String value){
		TextSpanElement newTextSpan = new TextSpanElement(dom);
		String txt = StringUtils.trim(value);		
		String[] texts = txt.split(StringConstants.NEWLINE);		
		for (int i = 0; i < texts.length; i++) {
			String token = texts[i];			
			TextSpanFragment fragment = new TextSpanFragment(token);			
			fragment.appendTo(newTextSpan);				
			if (i!=texts.length-1) {
				TextLineBreakElement nl = new TextLineBreakElement(dom);
				newTextSpan.appendChild(nl);				
			}
		}
		OdfUtils.replace(newTextSpan, field);
	}
	
	
	/**
	 * Gets all TextUserFieldGetElement nodes of the specified dom.
	 * 
	 * @param dom
	 *        OdfFileDom to search for TextUserFieldGetElements.
	 *        
	 * @return Returns a list with all TextUserFieldGetElement of the dom.
	 */
	public static List<TextUserFieldGetElement> getUserFieldGetElements(OdfFileDom dom) {
		List<TextUserFieldGetElement> list = new ArrayList<TextUserFieldGetElement>();
		Node root = dom.getRootElement();
		XmlUtils.getAllNodesOfType(root, TextUserFieldGetElement.class, list);
		return list;
	}
	
	/**
	 * Replaces the user fields on a OdfFileDom with the values retrieved by evaluating
	 * each field's name as an OGNL expression against a specified model object.
	 * <br/>
	 * Values are checked for illegal characters.
	 * 
	 * @param dom
	 *        TextDocument on which the fields are replaced by values.
	 * @param model
	 *        OGNL model object.
	 *        
	 */
	public static void setFields(OdfFileDom dom, Object model) {
		List<TextUserFieldGetElement> fields = getUserFieldGetElements(dom);
		for (TextUserFieldGetElement field : fields) {
			String fieldName = field.getTextNameAttribute();
			String value = ExpressionEvaluator.getInstance().getValue(fieldName, model);
			value = IllegalCharacterFilter.SINGLETON.filter(value);
			replaceField(dom, field, value);			
		}
	}
	
	/**
	 * Replaces a document's user fields with the values retrieved by evaluating
	 * each fiald's name as an OGNL expression against a specified model object.
	 * 
	 * @param doc
	 *        TextDocument on which the fields are replaced by values.
	 * @param model
	 *        OGNL model object.
	 *        
	 * @throws Exception
	 */
	public static void setFields(TextDocument doc, Object model) throws Exception {
		setFields(doc.getContentDom(), model);
		setFields(doc.getStylesDom(), model);		
	}
	
	

}
