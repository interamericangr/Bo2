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

import org.apache.wicket.markup.html.basic.Label;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link RefreshAjaxLink}
 */
public class TestRefreshAjaxLink extends WicketTest {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.RefreshAjaxLink#RefreshAjaxLink(java.lang.String, org.apache.wicket.MarkupContainer, java.lang.String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRefreshAjaxLink() {
		RefreshAjaxLink tested = new RefreshAjaxLink(StringConstants.EIGHT, tester.getLastRenderedPage(), "sampleTxt");
		assertNotNull(tested);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.RefreshAjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testOnClickAjaxRequestTarget() {
		Label sampleComp = tester.startComponentInPage(new Label("sampleLbl"));
		sampleComp.setOutputMarkupId(true);
		RefreshAjaxLink tested = new RefreshAjaxLink(StringConstants.EIGHT, tester.getLastRenderedPage(), "sampleLbl");
		tested = tester.startComponentInPage(tested);

		assertTrue(sampleComp.isVisible());
		tester.clickLink(tested);
		assertFalse(sampleComp.isVisible());
	}

}
