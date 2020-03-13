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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Unit tests for {@link LinkWithImagePanel}
 */
public class TestLinkWithImagePanel extends WicketTest {

	/**
	 * Test method for {@link LinkWithImagePanel#LinkWithImagePanel(String, SerializableRunnable, EmbeddedImage)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLinkWithImagePanelStringSerializableRunnableEmbeddedImage() {
		LinkWithImagePanel tested = new LinkWithImagePanel("tested", () -> System.out.println("test_runnable"), EmbeddedImage.COPY);
		assertNotNull(tested.get("link"));
		assertNotNull(tested.get("link").get("image"));
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}

	/**
	 * Test method for {@link LinkWithImagePanel#LinkWithImagePanel(String, IModel, SerializableConsumer, EmbeddedImage)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLinkWithImagePanelStringIModelOfTSerializableConsumerOfTEmbeddedImage() {
		LinkWithImagePanel tested = new LinkWithImagePanel("tested", Model.of("text"), (a) -> System.out.println(a), EmbeddedImage.COPY);
		assertNotNull(tested.get("link"));
		assertNotNull(tested.get("link").get("image"));
		tester.startComponentInPage(tested);
		commonAssertions_noError(WebPage.class);
	}
}