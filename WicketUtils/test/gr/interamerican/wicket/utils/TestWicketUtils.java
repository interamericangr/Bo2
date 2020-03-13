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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link WicketUtils}.
 */
public class TestWicketUtils extends WicketTest{

	/**
	 * Test {@link WicketUtils#validateNotNull(Component, org.apache.wicket.markup.html.form.FormComponent)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testValidateNotNull() {
		Component component = new EmptyPanel("panelid");
		IModel<List<String>> model = null;
		DropDownChoice<String> formComponent = new DropDownChoice<>("id", model);
		WicketUtils.validateNotNull(component, formComponent);
		assertEquals(1, component.getFeedbackMessages().size());
	}

	/**
	 * Test {@link WicketUtils#validateNotNull(Component, String, org.apache.wicket.markup.html.form.FormComponent...)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testValidateNotNull_withTreeArgs() {
		Component component = new EmptyPanel("panelid");
		IModel<List<String>> model = null;
		DropDownChoice<String> formComponent = new DropDownChoice<>("id", model);
		String resourceKey = "1"; 
		WicketUtils.validateNotNull(component, resourceKey, formComponent);
		assertEquals(1, component.getFeedbackMessages().size());
	}

	/**
	 * Test {@link WicketUtils#setEnabled(boolean, Component...)}
	 */
	@SuppressWarnings({ "nls"})
	@Test
	public void testSetEnabled() {
		Label sampleLabel = tester.startComponentInPage(new Label("test"));
		sampleLabel.setEnabled(false);
		WicketUtils.setEnabled(true, sampleLabel);
		tester.assertEnabled("test");
	}

	/**
	 * Test {@link WicketUtils#setVisible(boolean, Component...)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetVisible() {
		Label sampleLabel = tester.startComponentInPage(new Label("test"));
		sampleLabel.setVisible(false);
		WicketUtils.setVisible(true, sampleLabel);
		tester.assertVisible("test");
	}

	/**
	 * Test {@link WicketUtils#getObject(IChoiceRenderer, String, IModel)}
	 */
	@Test
	public void testGetObject() {
		@SuppressWarnings("nls")
		ChoiceRenderer<BeanWith1Field> cr = new ChoiceRenderer<>("field2", "field2");
		ArrayList<BeanWith1Field> list = new ArrayList<BeanWith1Field>();
		Model<ArrayList<BeanWith1Field>> model = new Model<>(list);
		assertNull(WicketUtils.getObject(cr, null, model));
		list.add(new BeanWith1Field(new Random().nextLong()));
		list.add(new BeanWith1Field(new Random().nextLong()));
		assertSame(list.get(1), WicketUtils.getObject(cr, cr.getIdValue(list.get(1), -1), model));
		assertNull(WicketUtils.getObject(cr, "notValid", model)); //$NON-NLS-1$
	}

	/**
	 * Test {@link WicketUtils#addConfirmationDialog(String, AjaxRequestAttributes)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddConfirmationDialog() {
		AjaxRequestAttributes attributes = new AjaxRequestAttributes();
		assertEquals(0, attributes.getAjaxCallListeners().size());
		WicketUtils.addConfirmationDialog("someText", attributes);
		assertEquals(1, attributes.getAjaxCallListeners().size());
		IAjaxCallListener listener = attributes.getAjaxCallListeners().get(0);
		assertTrue(listener instanceof AjaxCallListener);
		assertEquals("return confirm('someText');", ((AjaxCallListener)listener).getPrecondition(null).toString());
	}
}