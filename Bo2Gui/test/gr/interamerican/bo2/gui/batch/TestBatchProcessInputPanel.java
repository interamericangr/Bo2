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
package gr.interamerican.bo2.gui.batch;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * test case for {@link BatchProcessInputPanel}
 */
public class TestBatchProcessInputPanel {

	/**
	 * test method for the constructor.
	 */
	@Test
	public void testConstructor_withOneArg() {
		Properties model = new Properties();
		BatchProcessInputPanel p=new BatchProcessInputPanel(model);
		assertEquals(model, p.getModel());
		int expected = BatchProcessParmNames.FIELDS.length * 2;
		int actual = p.getComponentCount();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(Boolean.FALSE, p.isEditable());
	}
	
	/**
	 * test method for the constructor.
	 */
	@Test
	public void testConstructor_withTwoArgs() {
		Properties model = new Properties();
		boolean editable = true;
		BatchProcessInputPanel p=new BatchProcessInputPanel(model, editable);
		assertEquals(model, p.getModel());
		int expected = BatchProcessParmNames.FIELDS.length * 2;
		int actual = p.getComponentCount();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(editable, p.isEditable());
	}
	
	
	
}
