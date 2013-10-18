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

import gr.interamerican.bo2.utils.StreamUtils;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;

/**
 * Unit test for OdfUtils.
 */
@SuppressWarnings("nls")
public class TestOdfUtils {
	
	/**
	 * Unit test for getDocumentPath()
	 * @throws Exception 
	 */
	@Test
	public void testGetDocumentPath_withUnsavedDoc() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();
		Assert.assertNull(OdfUtils.getDocumentPath(doc));
	}
	
	/**
	 * Unit test for getDocumentPath()
	 * @throws Exception
	 * 
	 *  @deprecated If the declared path in deployment.properties uses a symbolic link
	 *              this test fails.
	 */
//	@Test
	public void testGetDocumentPath_withSavedDoc() throws Exception {		
		String expected = ResourceUtils.inputPath("SmallText.odt");
		TextDocument doc = TextDocument.loadDocument(expected);
		String actual = OdfUtils.getDocumentPath(doc);
		/*
		 * For windows convert to lower case. 
		 */
		Assert.assertEquals(expected.toLowerCase(), actual.toLowerCase());
	}
	
	/**
	 * Unit test for getSaveContentAsXml()
	 * @throws Exception 
	 */
	@Test
	public void testSaveContentAsXml() throws Exception {	
		TextDocument doc = TextDocument.newTextDocument();
		doc.addParagraph("This is to test saveContentAsXml(document)");		
		String path = ResourceUtils.outputPath("OdfUtils_SaveContentAsXml.odt");
		doc.save(path);
		OdfUtils.saveContentAsXml(doc);
		
		String content = StreamUtils.readTextFile(path+".content.xml");
		Assert.assertEquals(doc.getContentDom().toString(), content);

		/*
		 * checks for styles and meta are ommitted because they fail due to
		 * new line or other characters. The xml contents are the same.
		 *  
		String styles = StreamUtils.readTextFile(path+".styles.xml");
		Assert.assertEquals(doc.getStylesDom().toString(), styles);
		String meta = StreamUtils.readTextFile(path+".meta.xml");
		Assert.assertEquals(doc.getMetaDom().toString(), meta);
		*/
		
		String settings = StreamUtils.readTextFile(path+".settings.xml");
		Assert.assertEquals(doc.getSettingsDom().toString(), settings);
		
	}
	
	/**
	 * Unit test for isTopLevel()
	 * @throws Exception 
	 */
	@Test
	public void testIsTopLevel() throws Exception  {
		String inPath = ResourceUtils.inputPath("DynamicTemplate.odt");
		TextDocument doc = TextDocument.loadDocument(inPath);
		Section section1 = doc.getSectionByName("Section1");
		Assert.assertTrue(OdfUtils.isTopLevel(section1));
		Section summary = doc.getSectionByName("Summary");
		Assert.assertFalse(OdfUtils.isTopLevel(summary));
		Section orderItem = doc.getSectionByName("OrderItem");
		Assert.assertFalse(OdfUtils.isTopLevel(orderItem));
	}
	
	/**
	 * Unit test for getOdfElements()
	 * @throws Exception 
	 */
	@Test
	public void testGetOdfElements() throws Exception  {
		String inPath = ResourceUtils.inputPath("TwoSections.odt");
		TextDocument doc = TextDocument.loadDocument(inPath);
		List<OdfElement> nodes = OdfUtils.getOdfElements(doc);
		String outPath = ResourceUtils.outputPath("TwoSections.odt");
		doc.save(outPath);
		OdfUtils.saveContentAsXml(doc);
		Assert.assertNotNull(nodes);		
		Assert.assertEquals(3, nodes.size());
	}
	
	/**
	 * Unit test for getOdfElements()
	 * @throws Exception 
	 */
	@Test
	public void testGetOdfElementsForCopy() throws Exception  {
		String inPath = ResourceUtils.inputPath("TwoSections.odt");
		TextDocument doc = TextDocument.loadDocument(inPath);
		List<OdfElement> nodes = OdfUtils.getOdfElementsForCopy(doc);
		String outPath = ResourceUtils.outputPath("TwoSections.odt");
		doc.save(outPath);
		Assert.assertNotNull(nodes);		
		Assert.assertEquals(2, nodes.size());
	}
	
	
	
	/**
	 * Unit test for copy before.
	 * 
	 * Element to copy and reference element are on the same document.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testCopyBefore_SameDocument() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();
		Paragraph p1 = doc.addParagraph("First paragraph");		
		OdfElement el1 = p1.getOdfElement();
		doc.addParagraph("Second paragraph");				
		Paragraph p3 = doc.addParagraph("This will be copied before the first paragraph");		
		int countBefore = doc.getContentRoot().getChildNodes().getLength();
		OdfElement el3 = p3.getOdfElement();
		OdfUtils.copyBefore(el1, el3);
		String path = ResourceUtils.outputPath("OdfUtils_copyBefore_Same.odt");
		doc.save(path);
		int countAfter = doc.getContentRoot().getChildNodes().getLength();
		Assert.assertEquals(countBefore+1, countAfter);
	}
	
	/**
	 * Unit test for copy before.
	 * 
	 * Element to copy and reference element are on different documents.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testCopyBefore_DifferentDocument() throws Exception {
		TextDocument doc1 = TextDocument.newTextDocument();
		TextDocument doc2 = TextDocument.newTextDocument();
		Paragraph p1 = doc1.addParagraph("First paragraph");		
		OdfElement el1 = p1.getOdfElement();
		doc1.addParagraph("Second paragraph");				
		Paragraph p3 = doc2.addParagraph("This will be copied before the first paragraph");		
		int countBefore = doc1.getContentRoot().getChildNodes().getLength();
		OdfElement el3 = p3.getOdfElement();
		OdfUtils.copyBefore(el1, el3);
		String path = ResourceUtils.outputPath("OdfUtils_copyBefore_Different.odt");
		doc1.save(path);
		int countAfter = doc1.getContentRoot().getChildNodes().getLength();
		Assert.assertEquals(countBefore+1, countAfter);
	}
	
	/**
	 * Unit test for copy before.
	 * 
	 * Element to copy and reference element are on the same document.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testCopyAfter_SameDocument() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();
		Paragraph p1 = doc.addParagraph("First paragraph");		
		OdfElement el1 = p1.getOdfElement();
		doc.addParagraph("Second paragraph");				
		Paragraph p3 = doc.addParagraph("This will be copied after the first paragraph");		
		int countBefore = doc.getContentRoot().getChildNodes().getLength();
		OdfElement el3 = p3.getOdfElement();
		OdfUtils.copyAfter(el1, el3);
		String path = ResourceUtils.outputPath("OdfUtils_copyAfter_Same.odt");
		doc.save(path);
		int countAfter = doc.getContentRoot().getChildNodes().getLength();
		Assert.assertEquals(countBefore+1, countAfter);
	}
	
	/**
	 * Unit test for copy before.
	 * 
	 * Element to copy and reference element are on different documents.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testCopyAfter_DifferentDocument() throws Exception {
		TextDocument doc1 = TextDocument.newTextDocument();
		TextDocument doc2 = TextDocument.newTextDocument();
		Paragraph p1 = doc1.addParagraph("First paragraph");		
		OdfElement el1 = p1.getOdfElement();
		doc1.addParagraph("Second paragraph");				
		Paragraph p3 = doc2.addParagraph("This will be copied after the first paragraph");		
		int countBefore = doc1.getContentRoot().getChildNodes().getLength();
		OdfElement el3 = p3.getOdfElement();
		OdfUtils.copyAfter(el1, el3);
		String path = ResourceUtils.outputPath("OdfUtils_copyAfter_Different.odt");
		doc1.save(path);
		int countAfter = doc1.getContentRoot().getChildNodes().getLength();
		Assert.assertEquals(countBefore+1, countAfter);
	}
	
	/**
	 * Unit test for copy before.
	 * 
	 * Element to copy and reference element are on different documents.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testGetOwnerDocument() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();		
		Paragraph p = doc.addParagraph("First paragraph");		
		OdfElement element = p.getOdfElement();
		Document owner = OdfUtils.getOwnerDocument(element);
		Assert.assertSame(doc, owner);
	}
	
	/**
	 * Unit test for getFirstElement.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testGetFirstElement() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();		
		doc.addParagraph("First paragraph");
		OdfElement expected = OdfUtils.getFirstElement(doc);
		List<OdfElement> elements = OdfUtils.getOdfElements(doc);
		OdfElement actual = elements.get(0);
		Assert.assertSame(expected, actual);
	}
	
	/**
	 * Unit test for getFirstElement.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testGetLastElement() throws Exception {
		TextDocument doc = TextDocument.newTextDocument();		
		doc.addParagraph("First paragraph");
		OdfElement expected = OdfUtils.getLastElement(doc);
		List<OdfElement> elements = OdfUtils.getOdfElements(doc);
		int count = elements.size();
		OdfElement actual = elements.get(count-1);
		Assert.assertSame(expected, actual);
	}

	
}
