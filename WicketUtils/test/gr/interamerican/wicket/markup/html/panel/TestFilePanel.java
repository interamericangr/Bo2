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

import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * Unit tests for {@link FilePanel}
 */
public class TestFilePanel extends WicketTest {
	
	/**
	 * Tests creation of {@link FilePanel}.
	 */	
	@Test
	public void testCreation() {		 
		tester.startPage(getTestPage());
		tester.assertComponent(subjectPath(), FilePanel.class);
		commonAssertions_noError();
	}
	
	@Override
	protected Component initializeComponent() {
		IModel<byte[]> model = new Model<byte[]>();
		model.setObject(null);
		return new FilePanel(TestPage.TEST_ID, model);
	}

}
