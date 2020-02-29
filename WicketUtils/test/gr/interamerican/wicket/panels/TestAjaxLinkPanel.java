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

import static org.junit.Assert.assertNotNull;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link AjaxLinkPanel}
 */
public class TestAjaxLinkPanel extends WicketTest {

	/**
	 * Test method for {@link gr.interamerican.wicket.panels.AjaxLinkPanel#AjaxLinkPanel(String, LegacyCallbackAction, IModel, Behavior...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkPanelStringSerializableRunnableEmbeddedImage() {
		AjaxLinkPanel tested = new AjaxLinkPanel("tested", (a) -> System.out.println("test_runnable"), Model.of(""));
		assertNotNull(tested.get("link"));
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.panels.AjaxLinkPanel#AjaxLinkPanel(String, IModel, PickAction, IModel, Behavior...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkPanelStringIModelOfTSerializableConsumerOfTEmbeddedImage() {
		AjaxLinkPanel tested = new AjaxLinkPanel("tested", Model.of("text"), (t, a) -> System.out.println(a), Model.of(""));
		assertNotNull(tested.get("link"));
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}
}