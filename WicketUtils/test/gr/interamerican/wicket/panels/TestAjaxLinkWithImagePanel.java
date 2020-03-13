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
package gr.interamerican.wicket.panels;

import static org.junit.Assert.*;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;
import org.junit.Test;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Unit tests for {@link AjaxLinkWithImagePanel}
 */
public class TestAjaxLinkWithImagePanel extends WicketTest{

	/**
	 * Test method for {@link AjaxLinkWithImagePanel#AjaxLinkWithImagePanel(String, LegacyCallbackAction, ResourceReference, Behavior...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkWithImagePanelStringLegacyCallbackActionResourceReference() {
		MockedCallback sampleCallback = new MockedCallback();
		AjaxLinkWithImagePanel tested = new AjaxLinkWithImagePanel("tested", sampleCallback, EmbeddedImage.COPY,
				new AjaxSelfUpdatingTimerBehavior(Duration.MAXIMUM));
		assertNotNull(tested);
		assertEquals("tested", tested.getId());
		assertTrue(tested.get("link").getBehaviors().get(0) instanceof AjaxSelfUpdatingTimerBehavior);
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}

	/**
	 * Test method for {@link AjaxLinkWithImagePanel#AjaxLinkWithImagePanel(String, IModel, PickAction, ResourceReference, Behavior...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkWithImagePanelStringIModelOfTPickActionOfTResourceReference() {
		AjaxLinkWithImagePanel tested = new AjaxLinkWithImagePanel("tested", Model.of("text"), (target,bean) -> target.add(new Label(bean)),EmbeddedImage.COPY);
		assertNotNull(tested);
		assertNotNull(tested.get("link"));
		assertNotNull(tested.get("link").get("image"));
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}
}