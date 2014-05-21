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
package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.samples.bean.Samples;
import gr.interamerican.bo2.samples.bean.TripInfo;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.beans.Range;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link OdfToolkitTextDocument}.
 */
public class TestOdfToolkitTextDocument {
	
	/**
	 * Unit test
	 * @throws DocumentEngineException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetFields() throws DocumentEngineException {	
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TemplateWithAllTypesOfFields.odt");
		BusinessDocument template = engine.openDocument(path);
		List<String> fields = template.getFields();		
		Assert.assertEquals(4, fields.size());
	}
	
	/**
	 * Unit test
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAppend() throws Exception {	
		OdfToolkitEngine engine = new OdfToolkitEngine();		
		/*
		 * create a document and add some text.
		 */
		BusinessDocument newDoc = engine.newDocument();
		String text = "This page is test for the append method.";
		newDoc.addParagraph(text);
		/*
		 * Now load a small document and append it.
		 */
		String smallTextPath = ResourceUtils.inputPath("SmallText.odt");		
		BusinessDocument smallText = engine.openDocument(smallTextPath);
		newDoc.append(smallText);
		/*
		 * Now add another paragraph.
		 */
		text = "Now we will append the previous document once more!";
		newDoc.addParagraph(text);
		/*
		 * Now append again the document
		 */
		newDoc.append(smallText);
		/*
		 * Now save the document.
		 */
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_Append.odt");
		engine.saveDocument(newDoc,outPath);		
	}
	
	
	
	/**
	 * Unit test
	 * @throws DocumentEngineException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetTable() throws DocumentEngineException {	
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TemplateWithUserFieldsAndTables.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table1 = template.getTable("Table1");
		Assert.assertNotNull(table1);
		DocumentTable table2 = template.getTable("Table2");		
		Assert.assertNotNull(table2);
		DocumentTable table3 = template.getTable("NoTable");		
		Assert.assertNull(table3);
	}
	
	/**
	 * Unit test for getTable(string) when the table is on the header.
	 * 
	 * @throws DocumentEngineException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetTable_onHeader() throws DocumentEngineException {	
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("HeaderTableWithoutLogo.odt");
		BusinessDocument template = engine.openDocument(path);
		DocumentTable headerTable = template.getTable("HeaderTable");
		Assert.assertNotNull(headerTable);
	}
	
	/**
	 * Unit test
	 * @throws DocumentEngineException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetFields() throws DocumentEngineException {	
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TemplateWithUserFields.odt");		
		BusinessDocument template = engine.openDocument(path);				
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "Value of field1");
		map.put("field2", "FIELD2");		
		template.setFields(map);				
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_SetFields.odt");
		engine.saveDocument(template,outPath);
	}
		
	
	/**
	 * Unit test
	 * @throws DocumentEngineException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testMethods() throws DocumentEngineException, ParseException {	
		OdfToolkitEngine engine = new OdfToolkitEngine();
		
		/*
		 * create a document and add some text.
		 */
		OdfToolkitTextDocument newDoc = OdfToolkitEngine.safeCast(engine.newDocument());
		String text = "This page is a preface. The next page contains the letter to family Astaire.";
		newDoc.addParagraph(text);
		newDoc.pageBreak();
		/*
		 * Now create a letter and append it.
		 */ 
		String letterTemplatePath = ResourceUtils.inputPath("TemplateForLetter.odt");
		BusinessDocument letterTemplate = engine.openDocument(letterTemplatePath);
		TripInfo info = new TripInfo();
		Date from = DateUtils.getDate("31/01/2012");
		Date to = DateUtils.getDate("30/03/2012");
		Range<Date> period = new Range<Date>(from, to);
		info.setPeriod(period);
		info.setFamily(Samples.theAstaireFamily());
		info.setDestination("New York");	
		letterTemplate.setFields(info);
		newDoc.append(letterTemplate);
		newDoc.pageBreak();
		
		/*
		 * Now append a small text.
		 */
		String smallTextPath = ResourceUtils.inputPath("SmallText.odt");
		BusinessDocument smallText = engine.openDocument(smallTextPath);
		newDoc.append(smallText);
		newDoc.append(smallText);
		newDoc.append(smallText);
		newDoc.pageBreak();
		newDoc.addParagraph("This is the last paragraph");
		
		String outPath = ResourceUtils.outputPath("OdfToolitTextDocument_Methods.odt");
		engine.saveDocument(newDoc, outPath);		
	}
	
	/**
	 * Test for insertAt().
	 * 
	 * @throws DocumentEngineException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testInsertAt_withOneSection() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DynamicTemplate.odt");
		String smallTextPath = ResourceUtils.inputPath("SmallText.odt");
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_InsertAt_oneSection.odt");
		BusinessDocument template = engine.openDocument(templatePath);
		BusinessDocument small = engine.openDocument(smallTextPath);
		template.insertAt("ReplaceMe1", small);
		template.insertAt("ReplaceMe2", small);
		template.insertAt("ReplaceMe3", small);
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Test for insertAt().
	 * 
	 * @throws DocumentEngineException 
	 */	
	@SuppressWarnings("nls")
	@Test(expected=DocumentEngineException.class)
	public void testInsertAt_withMissingSection() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DynamicTemplate.odt");
		String smallTextPath = ResourceUtils.inputPath("SmallText.odt");		
		BusinessDocument template = engine.openDocument(templatePath);
		BusinessDocument small = engine.openDocument(smallTextPath);
		template.insertAt("Not_existing_section", small);
	}
	
	/**
	 * Test for insertAt().
	 * 
	 * @throws DocumentEngineException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testInsertAt_withTwoSections() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DynamicTemplate.odt");
		String smallTextPath = ResourceUtils.inputPath("TwoSections.odt");
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_InsertAt_twoSections.odt");
		BusinessDocument template = engine.openDocument(templatePath);
		BusinessDocument small = engine.openDocument(smallTextPath);
		template.insertAt("ReplaceMe1", small);
		template.insertAt("ReplaceMe2", small);
		template.insertAt("ReplaceMe3", small);
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Test for insertAt().
	 * @throws Exception 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testAppend_withPageBreaks() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		
		String smallTextPath = ResourceUtils.inputPath("SmallText.odt");
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_AppendAndPageBreak.odt");
		BusinessDocument doc = engine.newDocument();
		BusinessDocument small = engine.openDocument(smallTextPath);
		doc.append(small);
		doc.pageBreak();
		doc.append(small);
		doc.pageBreak();
		doc.append(small);
		doc.pageBreak();
		doc.addParagraph("Trying to duplicate the document");
		doc.pageBreak();
		doc.append(doc);
		engine.saveDocument(doc, outPath);
	}
	
	
	/**
	 * Test for insertAt().
	 * @throws Exception 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testInsertAt_withDocumentContainingTables() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DocTestInsertAtTemplate.odt");
		String smallTextPath = ResourceUtils.inputPath("DocTestInsertAtPart.odt");
		String outPath = ResourceUtils.outputPath("OdfToolkitTextDocument_InsertAt_withDocumentContainingTables.odt");
		BusinessDocument template = engine.openDocument(templatePath);
		BusinessDocument small = engine.openDocument(smallTextPath);
		template.insertAt("Section2", small);
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Test for insertAt().
	 * @throws Exception 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testGetProperty() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DocTestGetProperty.odt");
		String propertyName = "Table1.minLength";		
		BusinessDocument template = engine.openDocument(templatePath);
		OdfToolkitTextDocument odfText = OdfToolkitEngine.safeCast(template);		
		String expected = "30";
		Object actual = odfText.getProperty(propertyName);
		Assert.assertEquals(expected, actual);
	}



}
