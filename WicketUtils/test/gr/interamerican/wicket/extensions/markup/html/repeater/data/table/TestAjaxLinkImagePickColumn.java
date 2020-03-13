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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import static org.junit.Assert.*;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.panels.AjaxLinkWithImagePanel;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Unit tests for {@link AjaxLinkImagePickColumn}
 */
public class TestAjaxLinkImagePickColumn extends WicketTest{

	/**
	 * Test method for {@link gr.interamerican.wicket.extensions.markup.html.repeater.data.table.AjaxLinkImagePickColumn#AjaxLinkImagePickColumn(org.apache.wicket.model.IModel, gr.interamerican.wicket.utils.images.EmbeddedImage, gr.interamerican.wicket.callback.PickAction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkImagePickColumnIModelOfStringEmbeddedImagePickActionOfT() {
		AjaxLinkImagePickColumn<String, String> tested = new AjaxLinkImagePickColumn<>(Model.of("text"), EmbeddedImage.COPY, (a,b) -> a.add(new Label(b))); //$NON-NLS-1$
		assertNotNull(tested);
		assertEquals("text", tested.getDisplayModel().getObject());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.extensions.markup.html.repeater.data.table.AjaxLinkImagePickColumn#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPopulateItem() {
		AjaxLinkImagePickColumn<String, String> tested = new AjaxLinkImagePickColumn<>(Model.of("text"), EmbeddedImage.COPY, (a,b) -> a.add(new Label(b))); //$NON-NLS-1$
		Item<ICellPopulator<String>> sampleItem = new Item<>(StringConstants.EIGHT, 1);
		tested.populateItem(sampleItem, "test", Model.of("text"));
	
		AjaxLinkWithImagePanel sampleComp = new AjaxLinkWithImagePanel("test", Model.of("text"), (a,b) -> a.add(new Label(b)), EmbeddedImage.COPY.getReference());
		Component actual = sampleItem.get("test");
		assertNotNull(actual);
		assertEquals(sampleComp.getId(), actual.getId());
		assertEquals(sampleComp.getDefaultModel(), actual.getDefaultModel());
	}

}
