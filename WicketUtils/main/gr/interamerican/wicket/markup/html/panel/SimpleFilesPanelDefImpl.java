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

import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

import gr.interamerican.bo2.utils.attributes.NamedDescribed;
import gr.interamerican.wicket.callback.ChainedCallbackAction;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.markup.html.panel.files.MultipleFilesPanelDefImpl;

/**
 * Implementation of {@link SimpleFilesPanelDef}.
 * @deprecated Switch to {@link MultipleFilesPanelDefImpl}
 */
@Deprecated
public class SimpleFilesPanelDefImpl extends ServicePanelWithBackDefImpl implements SimpleFilesPanelDef {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** fileDefinitions. */
	private List<NamedDescribed> fileDefinitions;
	
	/** uploadedFiles. */
	private List<FileUpload> uploadedFiles;
	
	/** uploadedFiles. */
	private ChainedCallbackAction submitAction;

	@Override
	public List<NamedDescribed> getFileDefinitions() {
		return fileDefinitions;
	}

	@Override
	public void setFileDefinitions(List<NamedDescribed> fileDefinitions) {
		this.fileDefinitions = fileDefinitions;
	}

	@Override
	public List<FileUpload> getUploadedFiles() {
		return uploadedFiles;
	}

	@Override
	public void setUploadedFiles(List<FileUpload> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	@Override
	public ChainedCallbackAction getSubmitAction() {
		return submitAction;
	}

	@Override
	public void setSubmitAction(ChainedCallbackAction submitAction) {
		this.submitAction = submitAction;
	}
}