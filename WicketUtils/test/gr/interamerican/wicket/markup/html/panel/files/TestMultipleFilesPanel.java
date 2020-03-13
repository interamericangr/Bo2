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
package gr.interamerican.wicket.markup.html.panel.files;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link MultipleFilesPanel}.
 */
public class TestMultipleFilesPanel extends WicketTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Def.
	 */
	MultipleFilesPanelDef def;

	/**
	 * if form was submit
	 */
	private boolean invoked;

	/**
	 * Test construct.
	 */
	@Test
	public void testConstruct() {
		startAndTestComponent(MultipleFilesPanel.class);
		System.out.println(tester.getLastResponseAsString());
	}

	/**
	 * testSubmit.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSubmit() {
		// start a test page with the MultipleFilesPanel
		tester.startPage(new TestPage(this::doSubmitTest, initializeComponent(TestPage.TEST_ID), Markup.div));
		tester.assertComponent(subjectPath(), MultipleFilesPanel.class);
		commonAssertions_noError();
		// submit the form without a file - no invocation
		invoked = false;
		tester.executeAjaxEvent(getAjaxButton(), "onclick");
		assertFalse(invoked);
		// select a file
		FormTester formTester = getFormTester();
		File file = new File(getClass().getResource("/gr/interamerican/wicket/samples/img/delete.jpeg").getFile());
		String fcPath = "testId:fileRepeater:1:file";
		tester.assertComponent("tf:" + fcPath, FileUploadField.class);
		formTester.setFile(fcPath, file, "image/jpeg");
		// submit the form with the file
		invoked = false;
		tester.executeAjaxEvent(getAjaxButton(), "onclick");
		assertTrue(invoked);
		// verify cleanup after invocation
		assertNull(def.getFileDefinitions().get(0).getUploadedFile());
		assertNull(def.getFileDefinitions().get(1).getUploadedFile());

	}

	/**
	 * Does the testing during form submit
	 * 
	 * @param target
	 * @param form
	 */
	void doSubmitTest(@SuppressWarnings("unused") AjaxRequestTarget target, @SuppressWarnings("unused") Form<?> form) {
		assertNotNull(def.getFileDefinitions().get(0).getUploadedFile());
		assertNull(def.getFileDefinitions().get(1).getUploadedFile());
		assertTrue(def.getFileDefinitions().get(0).getUploadedFile().getSize() > 0);
		invoked = true;
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		List<FileDefinition> defs = new ArrayList<FileDefinition>();
		FileDefinition def1 = new FileDefinitionImpl();
		def1.setRequired(true);
		def1.setName("file1"); //$NON-NLS-1$
		FileDefinition def2 = new FileDefinitionImpl();
		def2.setRequired(false);
		def2.setName("file2"); //$NON-NLS-1$
		defs.add(def1);
		defs.add(def2);

		def = new MultipleFilesPanelDefImpl();
		def.setWicketId(wicketId);
		def.setFileDefinitions(defs);
		return new MultipleFilesPanel(def);
	}
}