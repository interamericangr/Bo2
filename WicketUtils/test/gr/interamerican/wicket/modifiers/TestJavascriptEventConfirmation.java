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
package gr.interamerican.wicket.modifiers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link JavascriptEventConfirmation}.
 */
public class TestJavascriptEventConfirmation {
	
	/**
	 * Test for newValye().
	 */
	@Test
	public void testNewValue() {
		String event = "onClick"; //$NON-NLS-1$
		String msg = "Are you sure?";		 //$NON-NLS-1$
		JavascriptEventConfirmation jec = 
			new JavascriptEventConfirmation(event,msg);
		String newValue = jec.newValue(null, msg);
		Assert.assertNotNull(newValue);		
		Assert.assertTrue(newValue.contains(msg));
	}

}
