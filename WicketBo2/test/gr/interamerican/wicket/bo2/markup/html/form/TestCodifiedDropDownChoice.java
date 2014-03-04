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

import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.TestPanel;
import gr.interamerican.wicket.test.WicketTest;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link CodifiedDropDownChoice}.
 */
public class TestCodifiedDropDownChoice extends WicketTest {
	/**
	 * The DDC
	 */
	CodifiedDropDownChoice<Long, CompanyRole> ddc;
	/**
	 * Wicket ID of the Tested Drop Down Choice
	 */
	String DDC_WICKET_ID = "ddc"; //$NON-NLS-1$

	/**
	 * Tests Constructor.
	 */
	@Test
	public void testConstructor() {
		tester.startPage(getTestPage());
		Assert.assertSame(ddc, tester.getComponentFromLastRenderedPage(path(DDC_WICKET_ID)));
		Assert.assertEquals(ddc.getChoices(), Arrays.asList(10L, 5L, 95L));
	}

	@Override
	@SuppressWarnings("nls")
	protected Component initializeComponent() {
		CompanyRole one = new CompanyRole(null, null, 5L, "second");
		CompanyRole two = new CompanyRole(null, null, 95L, "third");
		CompanyRole three = new CompanyRole(null, null, 10L, "first");
		ddc = new CodifiedDropDownChoice<Long, CompanyRole>(DDC_WICKET_ID, Arrays.asList(one, two, three),
				CompanyRole.class);
		String markup = "<wicket:panel><select wicket:id=\"" + DDC_WICKET_ID + "\"></select></wicket:panel>";
		return new TestPanel(TestPage.TEST_ID, markup).add(ddc);
	}
}