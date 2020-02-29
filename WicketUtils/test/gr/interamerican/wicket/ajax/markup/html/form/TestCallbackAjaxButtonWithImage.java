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
package gr.interamerican.wicket.ajax.markup.html.form;

import static org.junit.Assert.*;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Unit tests for {@link CallbackAjaxButtonWithImage}
 */
public class TestCallbackAjaxButtonWithImage extends WicketTest {

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#getStatelessHint()}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetStatelessHint() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target, form) -> target.add(form));
		assertTrue(tested.getStatelessHint());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#CallbackAjaxButtonWithImage(java.lang.String, org.apache.wicket.request.resource.ResourceReference, gr.interamerican.wicket.callback.ICallbackAction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCallbackAjaxButtonWithImageStringResourceReferenceICallbackAction() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target, form) -> target.add(form));
		assertNotNull(tested);
		assertEquals(EmbeddedImage.COPY.getReference(),tested.localizedImageResource.getResourceReference());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#CallbackAjaxButtonWithImage(java.lang.String, org.apache.wicket.request.resource.ResourceReference, gr.interamerican.wicket.callback.ICallbackAction, org.apache.wicket.markup.html.panel.FeedbackPanel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCallbackAjaxButtonWithImageStringResourceReferenceICallbackActionFeedbackPanel() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target, form) -> target.add(form), new FeedbackPanel("sampleFBPanel"));
		assertNotNull(tested);
		assertEquals(EmbeddedImage.COPY.getReference(),tested.localizedImageResource.getResourceReference());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#CallbackAjaxButtonWithImage(java.lang.String, org.apache.wicket.request.resource.ResourceReference, gr.interamerican.wicket.callback.LegacyCallbackAction, org.apache.wicket.markup.html.panel.FeedbackPanel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCallbackAjaxButtonWithImageStringResourceReferenceLegacyCallbackActionFeedbackPanel() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target) -> target.getClass(), new FeedbackPanel("sampleFBPanel"));
		assertNotNull(tested);
		assertEquals(EmbeddedImage.COPY.getReference(),tested.localizedImageResource.getResourceReference());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#CallbackAjaxButtonWithImage(java.lang.String, org.apache.wicket.request.resource.ResourceReference, gr.interamerican.wicket.callback.LegacyCallbackAction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCallbackAjaxButtonWithImageStringResourceReferenceLegacyCallbackAction() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target) -> target.getClass());
		assertNotNull(tested);
		assertEquals(EmbeddedImage.COPY.getReference(),tested.localizedImageResource.getResourceReference());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#CallbackAjaxButtonWithImage(java.lang.String, org.apache.wicket.request.resource.ResourceReference, gr.interamerican.wicket.callback.LegacyCallbackAction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCallbackAjaxButtonWithImageStringResourceReferenceLegacyCallbackActionString() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.BLANK.getReference(), new MockedCallback(),"text");
		assertNotNull(tested);
		assertEquals(EmbeddedImage.BLANK.getReference(),tested.localizedImageResource.getResourceReference());
		assertEquals("text",tested.confirmationText);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#setDefaultModel(org.apache.wicket.model.IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetDefaultModelIModelOfQ() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target) -> target.getClass());
		tested.setDefaultModel(Model.of("test"));
		assertEquals(Model.of("test"), tested.getDefaultModel());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButtonWithImage#onComponentTag(org.apache.wicket.markup.ComponentTag)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOnComponentTagComponentTag() {
		CallbackAjaxButtonWithImage tested = new CallbackAjaxButtonWithImage("tested", EmbeddedImage.COPY.getReference(), (target) -> target.getClass());
		ComponentTag sampleTag = new ComponentTag("input", TagType.OPEN_CLOSE);
		sampleTag.getAttributes().put("type", "image");
		tested.onComponentTag(sampleTag);
		//TODO what should i assert?
	}

}
