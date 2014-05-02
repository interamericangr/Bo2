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
package gr.interamerican.bo2.odftoolkit.jod;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;
import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentEngineUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for JodPdfEngine.
 */
public class TestJodDocumentEngineUtility {
	
	/**
	 * Test subject
	 */
	DocumentEngineUtility subject = new JodDocumentEngineUtility(new Properties());
	
	/**
	 * Unit test for toPdf.
	 * 
	 * @throws DocumentEngineException
	 * @throws IOException
	 */
	@Test
	public void testToPdf() throws DocumentEngineException, IOException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TemplateForLetter.odt"); //$NON-NLS-1$
		BusinessDocument doc = engine.openDocument(path);
		
		OdfToolkitTextDocument odf = OdfToolkitEngine.safeCast(doc);
		byte[] odfAsBytes = odf.asByteArray();
		byte[] pdf = subject.toPdf(odfAsBytes);
		String outPath = ResourceUtils.outputPath("TemplateForLetter.pdf"); //$NON-NLS-1$
		FileOutputStream fos = new FileOutputStream(new File(outPath));
		fos.write(pdf);
		fos.close();
	}
	
	/**
	 * Unit test for toPdf.
	 * 
	 * @throws DocumentEngineException
	 * @throws IOException
	 */
	@Test
	public void testToHtml() throws DocumentEngineException, IOException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TemplateForLetter.odt"); //$NON-NLS-1$
		BusinessDocument doc = engine.openDocument(path);
		
		OdfToolkitTextDocument odf = OdfToolkitEngine.safeCast(doc);
		byte[] odfAsBytes = odf.asByteArray();
		byte[] xhtml = subject.toHtml(odfAsBytes).getBytes(Charset.forName("UTF-8")); //$NON-NLS-1$
		String outPath = ResourceUtils.outputPath("TemplateForLetter.xhtml"); //$NON-NLS-1$
		FileOutputStream fos = new FileOutputStream(new File(outPath));
		fos.write(xhtml);
		fos.close();
	}

}
