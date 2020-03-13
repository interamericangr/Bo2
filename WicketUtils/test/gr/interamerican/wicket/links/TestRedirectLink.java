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
package gr.interamerican.wicket.links;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link RedirectLink}
 */
public class TestRedirectLink extends WicketTest{

	/**
	 * Test method for {@link gr.interamerican.wicket.links.RedirectLink#onClick()}.
	 */
	@Test
	public void testOnClick() {
		RedirectLink tested = new RedirectLink(StringConstants.EIGHT, TestPage::new);
		
		assertEquals(StringConstants.EIGHT, tested.getId());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.links.RedirectLink#RedirectLink(java.lang.String, gr.interamerican.bo2.utils.functions.SerializableSupplier)}.
	 */
	@Test
	public void testRedirectLink(){
		RedirectLink tested = new RedirectLink(StringConstants.EIGHT, TestPage::new);
		tested = tester.startComponentInPage(tested);
		tester.clickLink(tested.getId());
		assertEquals(TestPage.class, tester.getLastRenderedPage().getClass());
	}

}
