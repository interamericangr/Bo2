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

import static org.junit.Assert.*;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames;
import gr.interamerican.bo2.utils.DateUtils;

import java.util.Properties;

import org.junit.Test;

/**
 * test case for {@link BatchOperationInputPanel}
 */
public class TestBatchOperationInputPanel {

	/**
	 * test method for the constructor.
	 */
	@Test
	public void testConstructor_withOneArg() {
		Properties model = new Properties();
		model.put("just another random property", DateUtils.today()); //$NON-NLS-1$
		BatchOperationInputPanel p = new BatchOperationInputPanel(model);
		assertEquals(model, p.getModel());
		int expected = (BatchOperationInputPanel.disabledFields.size() + 1) * 2;
		assertEquals(expected, p.getComponentCount());
		assertTrue(p.getTextField("just another random property").isEnabled()); //$NON-NLS-1$
		assertFalse(p.getTextField(BatchOperationParmNames.BATCH_PROCESS_NAME).isEnabled());
	}
}