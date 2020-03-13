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
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link PickActionAjaxLink}
 */
public class TestPickActionAjaxLink extends WicketTest {

	/**
	 * result of the pickAction. This is used to assert that the action was
	 * executed.
	 */
	String pickResult = StringConstants.EMPTY;

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.PickActionAjaxLink#PickActionAjaxLink(java.lang.String, org.apache.wicket.model.IModel, gr.interamerican.wicket.callback.PickAction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPickActionAjaxLink() {
		PickActionAjaxLink<String> tested = new PickActionAjaxLink<>(StringConstants.EIGHT, Model.of("text"),
				this::doPick);
		assertEquals(StringConstants.EIGHT, tested.getId());
		assertEquals("text", tested.getModelObject());
		assertNotNull(tested.action);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.PickActionAjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOnClickAjaxRequestTarget() {
		pickResult = null;
		PickActionAjaxLink<String> tested = new PickActionAjaxLink<>(StringConstants.EIGHT, Model.of("text"),
				this::doPick);
		tested.onClick(mock(AjaxRequestTarget.class));
		assertEquals("text", pickResult);
	}

	/**
	 * @param target
	 * @param bean
	 */
	private void doPick(@SuppressWarnings("unused") AjaxRequestTarget target, String bean) {
		pickResult = bean;
	}

	/**
	 * Test method for
	 * {@link PickActionAjaxLink#updateAjaxAttributes(AjaxRequestAttributes)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testUpdateAjaxAttributes() {
		PickActionAjaxLink<String> link = new PickActionAjaxLink<>("id", new Model<>(), this::doPick);
		assertNull(link.confirmationMessage);
		AjaxRequestAttributes attributes = new AjaxRequestAttributes();
		link.updateAjaxAttributes(attributes);
		assertEquals(0, attributes.getAjaxCallListeners().size());
		link = new PickActionAjaxLink<>("id", new Model<>(), this::doPick, "someText");
		assertEquals("someText", link.confirmationMessage);
		link.updateAjaxAttributes(attributes);
		assertEquals(1, attributes.getAjaxCallListeners().size());
	}
}