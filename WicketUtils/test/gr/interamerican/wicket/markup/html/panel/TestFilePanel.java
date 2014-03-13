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
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link FilePanel}
 */
public class TestFilePanel extends WicketTest {
	
	/**
	 * Tests creation of {@link FilePanel}.
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testCreationAndSubmit() {		 
		tester.startPage(getTestPage());
		tester.assertComponent(subjectPath(), FilePanel.class);
		commonAssertions_noError();
		
		FilePanel subject = (FilePanel) tester.getComponentFromLastRenderedPage(subjectPath());
		
		//FORM submit not coming from uploadButton is void
		FormTester formTester = getFormTester();
		File file = new File(getClass().getResource("/gr/interamerican/wicket/samples/img/delete.jpeg").getFile());
		String fcPath = TestPage.TEST_ID + ":" + FilePanel.FORM_ID + ":" + FilePanel.FILE_CHOOSER_ID;
		Assert.assertNull(subject.getDefaultModelObject());
		formTester.setFile(fcPath, file, "image/jpeg");
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		Assert.assertNull(subject.getDefaultModelObject());
		Assert.assertNull(subject.getFileName());
		
		//Button submit uploads file to model
		formTester = getFormTester();
		formTester.setFile(fcPath, file, "image/jpeg");
		AjaxButton button = (AjaxButton)
				tester.getComponentFromLastRenderedPage(path("form:uploadButton"));
		tester.executeAjaxEvent(button, "onclick");
		
		Assert.assertTrue(subject.getDefaultModelObject() instanceof byte[]);
		Assert.assertTrue(((byte[]) subject.getDefaultModelObject()).length > 0);
		Assert.assertNotNull(subject.getFileName());
	}
	
	@Override
	protected Component initializeComponent() {
		IModel<byte[]> model = new Model<byte[]>();
		model.setObject(null);
		return new FilePanel(TestPage.TEST_ID, model);
	}

}
