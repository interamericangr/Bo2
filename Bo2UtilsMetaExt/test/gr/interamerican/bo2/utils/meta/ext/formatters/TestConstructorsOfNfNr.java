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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.utils.meta.ext.formatters.nf.NfMoneyFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nf.NfNamedFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nf.NfTypedSelectableFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nr.NrMoneyFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nr.NrNamedFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nr.NrTypedSelectableFormatter;

import org.junit.Test;

/**
 * Unit test for constructors
 */
public class TestConstructorsOfNfNr {

	/**
	 * Test creation.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testConstructors(){
		new NfMoneyFormatter();
		new NfNamedFormatter();
		new NfTypedSelectableFormatter();

		new NrMoneyFormatter();
		new NrNamedFormatter();
		new NrTypedSelectableFormatter();

	}



}
