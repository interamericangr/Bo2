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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link OdfToolkitEngine}.
 */
@SuppressWarnings("nls")
public class TestOdfToolkitEngine {
	
	
	/**
	 * Work file.
	 */	
	private String smallTextPath = ResourceUtils.inputPath("SmallText.odt"); 
	
	/**
	 * engine to test.
	 */
	OdfToolkitEngine engine = new OdfToolkitEngine();
	
	/**
	 * Assert checks for a BusinessDocument.
	 * 
	 * @param bd
	 *        BusinessDocument to check.
	 */
	void assertDoc(BusinessDocument bd) {
		assertNotNull(bd);
		assertTrue(bd instanceof OdfToolkitTextDocument);
		OdfToolkitTextDocument doc = (OdfToolkitTextDocument) bd;
		assertNotNull(doc.document);
		assertEquals(engine, doc.engine);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testNewDocument() throws DocumentEngineException {
		BusinessDocument bd = engine.newDocument();
		assertDoc(bd);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testOpenDocument_path() throws DocumentEngineException {		
		BusinessDocument bd = engine.openDocument(smallTextPath);
		assertDoc(bd);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testOpenDocument_file() throws DocumentEngineException {
		File file = new File(smallTextPath);
		BusinessDocument bd = engine.openDocument(file);
		assertDoc(bd);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testOpenDocument_stream() 
	throws DocumentEngineException, FileNotFoundException {
		File file = new File(smallTextPath);
		InputStream stream = new FileInputStream(file);
		BusinessDocument bd = engine.openDocument(stream);
		assertDoc(bd);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testSaveDocument_path() 
	throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("Testing save document to path"); 
		String outPath = ResourceUtils.outputPath("OdfToolkitEngine_SaveToPath.odt"); 
		engine.saveDocument(doc,outPath);
	}

	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testSaveDocument_file() 
	throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("Testing save document to file"); 
		String outPath = ResourceUtils.outputPath("OdfToolkitEngine_SaveToFile.odt"); 
		File file = new File(outPath);
		engine.saveDocument(doc,file);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testSaveDocument_stream() 
	throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("Testing save document to stream"); 
		String outPath = ResourceUtils.outputPath("OdfToolkitEngine_SaveToStream.odt"); 
		File file = new File(outPath);
		engine.saveDocument(doc,file);
	}
	
	/**
	 * Test for newDocument()
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testSaveDocument_toSameFile() 
	throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("line1"); 
		String outPath = ResourceUtils.outputPath("OdfToolkitEngine_SaveToSameFile.odt"); 	
		engine.saveDocument(doc,outPath);
		doc.addText("line2"); 
		doc.addText("line3");
		doc.addText("line4");		
		engine.saveDocument(doc);
	}
	
	/**
	 * Test for getToPdf() 
	 * 
	 * @throws DocumentEngineException 
	 */
	@Test
	public void testToPdf() throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("line1");
		doc.addText("line2");
		byte[] pdf = engine.toPdf(doc);
		Assert.assertNotNull(pdf);
	}
	
	/**
	 * Test for saveDocument()
	 * @throws DocumentEngineException 
	 */
	@Test(expected=DocumentEngineException.class)
	public void testSaveDocument_fail() 
	throws DocumentEngineException {
		BusinessDocument doc = engine.newDocument();
		doc.addText("line1"); 
		String outPath = "\\// * ?? hh / hh";
		engine.saveDocument(doc,outPath);		
	}
	
	/**
	 * Test for openDocument()
	 * @throws DocumentEngineException 
	 */
	@Test(expected=DocumentEngineException.class)
	public void testOpenDocument_fail() 
	throws DocumentEngineException {
		engine.openDocument("/\\/\\");
	}
	
	
	


}
