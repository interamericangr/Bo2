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
package gr.interamerican.wicket.markup.html.panel;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Tests the {@link DataTableImagePanel}.
 */
public class TestDataTableImagePanel extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new DataTableImagePanel(wicketId, new Model<>(), EmbeddedImage.ARROW_DOWN);
	}

	/**
	 * Test check box panel.
	 */
	@Test
	public void testDataTableImagePanel() {
		DataTableImagePanel tested = (DataTableImagePanel) startAndTestComponent(Panel.class);
		assertTrue(tested.get("image") instanceof Image); //$NON-NLS-1$
	}
}