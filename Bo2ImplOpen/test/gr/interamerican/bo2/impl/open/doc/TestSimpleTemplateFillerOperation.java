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
package gr.interamerican.bo2.impl.open.doc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.samples.doc.MockDocument;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link TemplateFillerOperation}.
 */
public class TestSimpleTemplateFillerOperation {
	/**
	 * operation for test.
	 */
	TemplateFillerOperation<Object> operation = new TemplateFillerOperation<Object>() {		
		@Override
		protected void work() throws DataException, LogicException, DocumentEngineException {
			/* empty */			
		}
	};
	
	/**
	 * Test for setModel()
	 */
	@Test
	public void testSetModel() {
		Object model = new Object();
		operation.setModel(model);
		Assert.assertEquals(model, operation.model);
	}
	
	/**
	 * Test for getModel()
	 */
	@Test
	public void testGetModel() {
		operation.model = new Object();		
		Assert.assertEquals(operation.getModel(), operation.model);
	}
	
	/**
	 * Test for setModel()
	 */
	@Test
	public void testSetDocument() {		
		BusinessDocument doc = new MockDocument();
		operation.setDocument(doc);
		Assert.assertEquals(doc, operation.document);
	}
	
	/**
	 * Test for getModel()
	 */
	@Test
	public void testGetDocument() {
		operation.document = new MockDocument();		
		Assert.assertEquals(operation.getModel(), operation.model);
	}
	
	

}
