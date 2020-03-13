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

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Unit tests for {@link LinkImagePickColumn}
 */
public class TestLinkImagePickColumn extends WicketTest { 

	/**
	 * Test method for {@link gr.interamerican.wicket.extensions.markup.html.repeater.data.table.LinkImagePickColumn#LinkImagePickColumn(org.apache.wicket.model.IModel, gr.interamerican.wicket.utils.images.EmbeddedImage, gr.interamerican.bo2.utils.functions.SerializableConsumer)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLinkImagePickColumnIModelOfStringEmbeddedImageSerializableConsumerOfT() {
		LinkImagePickColumn<SampleBean2, String> tested = new LinkImagePickColumn<>(Model.of("text"), EmbeddedImage.EDIT, (a) -> System.out.println(a.getField1()));
		assertNotNull(tested);
		assertEquals("text", tested.getDisplayModel().getObject());
		assertEquals(EmbeddedImage.EDIT.getReference(), tested.reference);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.extensions.markup.html.repeater.data.table.LinkImagePickColumn#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPopulateItem() {
		LinkImagePickColumn<SampleBean2, String> tested = new LinkImagePickColumn<>(Model.of("text"), EmbeddedImage.EDIT, (a) -> System.out.println(a.getField1()));
		Item<ICellPopulator<SampleBean2>> cellItem = new Item<>("id", 0); 
		tested.populateItem(cellItem, "test", Model.of(new SampleBean2()));
	}

}
