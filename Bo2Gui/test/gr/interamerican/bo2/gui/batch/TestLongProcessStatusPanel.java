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

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.batch.LongProcessStatus;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * test case for {@link LongProcessStatusPanel}
 */
public class TestLongProcessStatusPanel {

	/**
	 * {@link LongProcessStatusPanel#LongProcessStatusPanel(LongProcessStatus)}
	 */
	@Test
	public void testConstructor() {
		LongProcessStatus model = Mockito.mock(LongProcessStatus.class);
		LongProcessStatusPanel p = new LongProcessStatusPanel(model);
		Assert.assertNotNull(p);		
		for (String string : LongProcessStatusPanel.NAME_FIELDS) {
			Assert.assertNotNull(p.getTextField(string));			
		}
		for (String string : LongProcessStatusPanel.TIME_FIELDS) {
			Assert.assertNotNull(p.getTextField(string));			
		}
		for (String string : LongProcessStatusPanel.COUNT_FIELDS) {
			Assert.assertNotNull(p.getTextField(string));			
		}
		for (String string : LongProcessStatusPanel.FLAGS) {
			Assert.assertNotNull(p.getCheckBox(string));			
		}
	}

	/**
	 * test method for {@link LongProcessStatusPanel#showException()}
	 */
	@Test
	public void testShowException() {
		LongProcessStatus model = Mockito.mock(LongProcessStatus.class);
		LongProcessStatusPanel p = new LongProcessStatusPanel(model);
		p.showException();
		verify(model, atLeastOnce()).getExceptionMessage();
	}
}
