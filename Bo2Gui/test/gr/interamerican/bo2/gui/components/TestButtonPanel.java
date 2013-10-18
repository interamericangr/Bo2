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
package gr.interamerican.bo2.gui.components;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.awt.event.ActionListener;

import org.junit.Test;

/**
 * Unit test for {@link ButtonPanel}.
 */
public class TestButtonPanel {
	
	
	
	/**
	 * Assert that a button has one action listener.
	 * 
	 * @param button
	 */
	static void assertButtonHasOneListener(BButton button) {
		ActionListener[] listeners = button.getActionListeners();
		assertEquals(1, listeners.length);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testAddButton() {
		String method = "getField1"; //$NON-NLS-1$
		BeanWith2Fields o = new BeanWith2Fields();				
		ButtonPanel panel = new ButtonPanel();		
		BButton expected = panel.addButton(method, null, o);
		BButton actual = panel.buttons.get(method);
		assertEquals(expected, actual);	
		assertButtonHasOneListener(actual);
	}
	
	
	
	
	
	
	
}
