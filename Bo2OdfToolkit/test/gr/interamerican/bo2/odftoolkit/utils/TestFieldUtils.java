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

import gr.interamerican.bo2.samples.bean.Samples;
import gr.interamerican.bo2.samples.bean.TripInfo;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.beans.Range;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.simple.TextDocument;

/**
 * Unit tests for {@link FieldUtils}
 */
@SuppressWarnings("nls")
public class TestFieldUtils {
	
	/**
	 * Unit test for getUserFieldGetElements(document).
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testGetUserFieldGetElements() throws Exception {
		String inPath = ResourceUtils.inputPath("TemplateWithUserFields.odt");
		TextDocument template = TextDocument.loadDocument(inPath);
		List<TextUserFieldGetElement> list = FieldUtils.getUserFieldGetElements(template.getContentDom());
		Assert.assertEquals(5, list.size());
	}
	
	/**
	 * Unit test for replaceField().
	 * @throws Exception
	 */
	//@Test
	public void testReplaceField() throws Exception {
		String inPath = ResourceUtils.inputPath("TemplateWithUserFields.odt");
		String outPath = ResourceUtils.outputPath("FieldUtils_ReplaceField.odt");
		TextDocument template = TextDocument.loadDocument(inPath);
		List<TextUserFieldGetElement> list = FieldUtils.getUserFieldGetElements(template.getContentDom());
		int initialCount = list.size();
		TextUserFieldGetElement fieldGet = list.get(0);
		FieldUtils.replaceField(template.getContentDom(), fieldGet, "Replaced user field!");
		template.save(outPath);		
		List<TextUserFieldGetElement> newList = FieldUtils.getUserFieldGetElements(template.getContentDom());
		Assert.assertEquals(initialCount-1, newList.size());
	}
	
	/**
	 * Unit test 
	 * @throws Exception 
	 */	
	//@Test
	public void testSetFields() throws Exception {
 		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "           The         value   of  field1  with spaces    ");		
		map.put("field2", " The value of \n(newLine) field2");
		
		String inpath = ResourceUtils.inputPath("TemplateWithUserFields.odt");
		String outpath = ResourceUtils.outputPath("FieldUtils_SetFields.odt");
		
		TextDocument template = TextDocument.loadDocument(inpath);
		FieldUtils.setFields(template, map);
		template.save(outpath);
		List<TextUserFieldGetElement> fields = FieldUtils.getUserFieldGetElements(template.getContentDom());
		Assert.assertEquals(0, fields.size());
		
	}
	
	/**
	 * Unit test for setFields.
	 * 
	 * Sets user fields in header and footer.
	 * @throws Exception 
	 */	
	//@Test
	public void testSetFields_inHeaderAndFooter() throws Exception {
 		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "           The         value   of  field1  with spaces    ");
		map.put("field2", " The value of field2");
		map.put("field3", " Field #3");
		
		String inpath = ResourceUtils.inputPath("TemplateWithUserFieldsInHeaderAndFooter.odt");
		String outpath = ResourceUtils.outputPath("FieldUtils_SetFields_HeaderAndFooter.odt");
		
		TextDocument template = TextDocument.loadDocument(inpath);
		FieldUtils.setFields(template, map);
		template.save(outpath);		
		
		List<TextUserFieldGetElement> contentFields = FieldUtils.getUserFieldGetElements(template.getContentDom());
		List<TextUserFieldGetElement> stylesFields = FieldUtils.getUserFieldGetElements(template.getStylesDom());
		int totalFieldsCount = contentFields.size() + stylesFields.size();
		Assert.assertEquals(0, totalFieldsCount);
		
	}
	
	/**
	 * Unit test for setFields.
	 * 
	 * Sets fields in a letter. A field value contains line breaks.
	 * 
	 * @throws Exception 
	 */	
	@Test
	public void testSetUserFields_Letter() throws Exception {
		String inpath = ResourceUtils.inputPath("TemplateForLetter.odt");
		String outpath = ResourceUtils.outputPath("FieldUtils_SetFields_Letter.odt");
		
		TripInfo info = new TripInfo();
		Date from = DateUtils.getDate("31/05/2011");
		Date to = DateUtils.getDate("30/06/2011");
		Range<Date> period = new Range<Date>(from, to);
		info.setPeriod(period);
		info.setFamily(Samples.theAstaireFamily());
		info.setDestination("\n\n\n\n * Monaco\n\n * France\n * Europe\n");
		
		TextDocument template = TextDocument.loadDocument(inpath);			
		FieldUtils.setFields(template, info);
		template.save(outpath);	
		
		List<TextUserFieldGetElement> fields = FieldUtils.getUserFieldGetElements(template.getContentDom());
		Assert.assertEquals(0, fields.size());
	}
	
	
	
	

}
