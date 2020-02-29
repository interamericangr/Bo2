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

import static org.junit.Assert.*;

import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link RedirectLinkWithArgument}
 */
public class TestRedirectLinkWithArgument extends WicketTest {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.RedirectLinkWithArgument#onClick()}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOnClick() {
		RedirectLinkWithArgument<String> tested = new RedirectLinkWithArgument<String>(StringConstants.EIGHT, Model.of("text"), s -> new TestPage());
		assertNotNull(tested);
		assertEquals(StringConstants.EIGHT, tested.getId());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.RedirectLinkWithArgument#RedirectLinkWithArgument(java.lang.String, org.apache.wicket.model.IModel, gr.interamerican.bo2.utils.functions.SerializableFunction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRedirectLinkWithArgument() {
		RedirectLinkWithArgument<String> tested = new RedirectLinkWithArgument<String>(StringConstants.EIGHT, Model.of("text"), s -> new TestPage());
		tested = tester.startComponentInPage(tested);
		tester.clickLink(StringConstants.EIGHT);
		assertEquals(TestPage.class, tester.getLastRenderedPage().getClass());
	}

}
