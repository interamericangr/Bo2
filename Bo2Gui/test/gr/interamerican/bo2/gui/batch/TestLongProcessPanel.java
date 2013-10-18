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

import gr.interamerican.bo2.arch.batch.LongProcess;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * test case for {@link LongProcessPanel}
 */
public class TestLongProcessPanel {

	/**
	 * test method for {@link LongProcessPanel#LongProcessPanel(LongProcess)}
	 */
	@Test
	public void testConstructor() {
		LongProcess lp = Mockito.mock(LongProcess.class);
		LongProcessPanel panel = new LongProcessPanel(lp);
		Assert.assertEquals(lp, panel.getModel());
		Assert.assertNotNull(panel.statusPanel);
		Assert.assertEquals(lp, panel.statusPanel.getModel());
		Assert.assertNotNull(panel.commandPanel);
		Assert.assertEquals(lp, panel.commandPanel.getModel());
	}

	
}
