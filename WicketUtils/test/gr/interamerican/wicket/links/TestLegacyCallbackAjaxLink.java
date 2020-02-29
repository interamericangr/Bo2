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
import static org.mockito.Mockito.mock;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link LegacyCallbackAjaxLink}
 */
public class TestLegacyCallbackAjaxLink extends WicketTest{

	/**
	 * Test method for {@link gr.interamerican.wicket.links.LegacyCallbackAjaxLink#LegacyCallbackAjaxLink(java.lang.String, gr.interamerican.wicket.callback.LegacyCallbackAction)}.
	 */
	@Test
	public void testLegacyCallbackAjaxLink() {
		LegacyCallbackAjaxLink tested = new LegacyCallbackAjaxLink(StringConstants.EIGHT, new MockedCallback());
		
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.links.LegacyCallbackAjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@Test
	public void testOnClickAjaxRequestTarget() {
		MockedCallback sampleCallback = new MockedCallback();
		LegacyCallbackAjaxLink tested = new LegacyCallbackAjaxLink(StringConstants.EIGHT, sampleCallback);
		tested.onClick(mock(AjaxRequestTarget.class));
		
		assertTrue(sampleCallback.isExecuted());
	}

}
