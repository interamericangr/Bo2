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
package gr.interamerican.wicket.bo2.markup.html.form;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link CodifiedDropDownChoice}.
 */
public class TestCodifiedDropDownChoice extends WicketTest {

	/**
	 * Tests Constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked")
		CodifiedDropDownChoice<Long, CompanyRole> ddc = startAndTestComponent(CodifiedDropDownChoice.class,Markup.select);
		Assert.assertEquals(ddc.getChoices(), Arrays.asList(10L, 5L, 95L));
	}

	@Override
	@SuppressWarnings("nls")
	protected Component initializeComponent(String wicketId) {
		CompanyRole one = new CompanyRole(null, null, 5L, "second");
		CompanyRole two = new CompanyRole(null, null, 95L, "third");
		CompanyRole three = new CompanyRole(null, null, 10L, "first");
		return new CodifiedDropDownChoice<Long, CompanyRole>(wicketId, Arrays.asList(one, two, three));
	}
}