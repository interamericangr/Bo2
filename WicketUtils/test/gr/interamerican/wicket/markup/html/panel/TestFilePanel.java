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

import static org.junit.Assert.assertSame;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * Unit tests for {@link PickerPanel}
 */
public class TestFilePanel extends WicketTest {
	
	/**
	 * Panel to test.
	 */
	FilePanel panel;	
	
	@Override
	protected Component initializeComponent() {
		IModel<byte[]> model = new Model<byte[]>();
		model.setObject(null);
		panel = new FilePanel(TestPage.TEST_ID, model);
		return panel;
	}
	
	
	/**
	 * Tests creation of {@link PickerPanel}.
	 * 
	 * Also tests that pressing the select button, selects the item. 
	 */	
	@Test
	public void testCreation() {		 
		tester.startPage(testPageSource());
		tester.assertComponent(subjectPath(), FilePanel.class);
		Component actual = tester.getComponentFromLastRenderedPage(subjectPath());
		assertSame(panel, actual);
	}

}
