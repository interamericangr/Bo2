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

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.repeater.RepeatingView;

import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

/**
 * A {@link MultipleFilesPanel} is a {@link ServicePanel} that will present a
 * number of form elements that allow uploading of files.<br>
 * The built in submit behavior of this is that it will set the
 * {@link FileUpload} within each {@link FileDefinition} of the
 * {@link MultipleFilesPanelDef} on submit.<br>
 * DO note that the parent form of this panel needs to have
 * {@link Form#setMultiPart(boolean)} set to true.
 * 
 * @see MultipleFilesPanelDef
 * @see FileUpload
 */
public class MultipleFilesPanel extends ServicePanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new MultipleFilesPanel object.
	 *
	 * @param definition
	 *            the definition
	 */
	public MultipleFilesPanel(MultipleFilesPanelDef definition) {
		super(definition);
	}

	@Override
	public MultipleFilesPanelDef getDefinition() {
		return (MultipleFilesPanelDef) super.getDefinition();
	}

	@Override
	protected void init() {
		// empty
	}

	@SuppressWarnings("nls")
	@Override
	protected void paint() {
		RepeatingView rows = new RepeatingView("fileRepeater");
		for (FileDefinition fd : getDefinition().getFileDefinitions()) {
			WebMarkupContainer wmc = new WebMarkupContainer(rows.newChildId());
			wmc.add(new Label("name", fd.getName()));
			wmc.add(new FileUploadField("file", new FileUploadModel(fd)).setRequired(fd.isRequired()));
			rows.add(wmc);
		}
		add(rows);
	}
}