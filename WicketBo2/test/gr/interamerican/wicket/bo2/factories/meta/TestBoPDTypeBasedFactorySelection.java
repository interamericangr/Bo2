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
package gr.interamerican.wicket.bo2.factories.meta;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestBoPDTypeBasedFactorySelection.
 */
public class TestBoPDTypeBasedFactorySelection {
	
	/** The bo PD type based factory selection. */
	BoPDTypeBasedFactorySelection boPDTypeBasedFactorySelection = 
		new BoPDTypeBasedFactorySelection();
	
	/**
	 * Test init bo PD type based factory selection.
	 */
	@Test
	public void testInitBoPDTypeBasedFactorySelection(){
		Assert.assertNotNull(boPDTypeBasedFactorySelection);
	}

}
