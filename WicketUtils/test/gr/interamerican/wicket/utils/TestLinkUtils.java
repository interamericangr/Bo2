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
package gr.interamerican.wicket.utils;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.markup.html.link.Link;
import org.junit.Test;

import gr.interamerican.wicket.samples.pages.EmptyPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link LinkUtils}
 */
public class TestLinkUtils extends WicketTest {
	/**
	 * Flag regarding the link being clicked.
	 */
	Boolean isClicked = false;

	/**
	 * Test create link.
	 */
	@Test
	public void testCreateLink(){
		Link<String> tested = tester.startComponentInPage(LinkUtils.createLink(EmptyPage.class));
		tester.clickLink(tested);
		tester.assertRenderedPage(EmptyPage.class);
	}

	/**
	 * Test create link 1.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateLinkWithName() {
		Link<String> tested = tester.startComponentInPage(LinkUtils.createLink("test", EmptyPage.class));
		assertEquals("test", tested.getId());
		tester.clickLink(tested);
		tester.assertRenderedPage(EmptyPage.class);
	}
}
