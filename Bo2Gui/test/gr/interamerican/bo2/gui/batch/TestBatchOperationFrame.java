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
import gr.interamerican.bo2.test.scenarios.FailingOperation;

import java.util.Properties;

import org.junit.Test;


/**
 * test method for {@link BatchOperationFrame}
 */
public class TestBatchOperationFrame {

	/**
	 * test the constructor.
	 */
	@Test
	public void testConstructor() {
		Properties model = new Properties();
		BatchOperationFrame frame = new BatchOperationFrame(model);
		assertNotNull(frame.inputPanel);
	}

	/**
	 * test the constructor.
	 */
	@Test
	public void testStart() {
		Properties p = new Properties();
		p.setProperty(BatchOperationParmNames.OPERATION_CLASS, FailingOperation.class.getName());
		BatchOperationFrame frame = new BatchOperationFrame(p);
		frame.setVisible(true);
		assertTrue(frame.isVisible());
		frame.start();
		assertFalse(frame.isVisible());
	}
}