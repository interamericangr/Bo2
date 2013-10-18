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

import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.attributes.NamedDescribed;
import gr.interamerican.wicket.callback.AbstractCallbackAction;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * A {@link SimpleFilesPanel} is a {@link ServicePanel} that will present a number of
 * form elements that allow uploading of files. There is no built-in form or submit 
 * behavior. The user is responsible for supplying a {@link CallbackAction} that runs
 * after the (external) form submit.
 * <br/> 
 * When this action is executed, {@link SimpleFilesPanelDef#getUploadedFiles()} will
 * provide a {@link FileUpload} instance for each one of the input files. The order
 * of the uploadedFiles is the same as the order of the {@link SimpleFilesPanelDef#getFileDefinitions()}.
 * 
 *  @see SimpleFilesPanelDef
 *  @see FileUpload
 */
public class SimpleFilesPanel extends ServicePanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * File with supported encodings.
	 */
	private static String SUPPORTED_ENCODINGS_FILE = "/gr/interamerican/wicket/markup/html/panel/supportedCharsets.txt"; //$NON-NLS-1$
	
	 /**
     * Wicket id of name.
     */
    private static final String LABEL_WICKET_ID="name"; //$NON-NLS-1$
    
    /**
     * Wicket id of file.
     */
    private static final String FILE_WICKET_ID="file"; //$NON-NLS-1$
   
    /**
     * Wicket id for the repeater.
     */
    private static final String REPEATER_WICKET_ID = "fileRepeater"; //$NON-NLS-1$
    
    /**
     * Wicket id for supported encodings.
     */
    private static final String SUPPORTED_ENCODINGS_WICKET_ID = "supportedEncodings"; //$NON-NLS-1$
    
    /**
     * Upload file fields.
     */
    private List<FileUploadField> uploadedFileFields;
    
    /**
     * {@link FileUploadField}s repeater.
     */
    private RepeatingView rows;
    
    /**
     * Supported encodings drop down.
     */
    private DropDownChoice<String> supportedEncodingsSelection;
   
	/**
	 * Creates a new MultipleFilesPanel object. 
	 *
	 * @param definition
	 */
	public SimpleFilesPanel(SimpleFilesPanelDef definition) {
		super(definition);
	}
	
	@Override
	public SimpleFilesPanelDef getDefinition() {
		return (SimpleFilesPanelDef) super.getDefinition();
	}
	
	@Override
	protected void init() {
		try {
			String[] suppEncs = StreamUtils.readResourceFile(SUPPORTED_ENCODINGS_FILE, true, true);
			List<String> supportedEncodings = Arrays.asList(suppEncs);
			supportedEncodingsSelection = new DropDownChoice<String>(SUPPORTED_ENCODINGS_WICKET_ID, supportedEncodings);
			supportedEncodingsSelection.setNullValid(false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		getDefinition().getSubmitAction().chainBefore(new BeforeSubmitAction());
		rows = new RepeatingView(REPEATER_WICKET_ID);
	}

	@Override
	protected void paint() {
		uploadedFileFields = new ArrayList<FileUploadField>();
		for(NamedDescribed fd : getDefinition().getFileDefinitions()) {
			WebMarkupContainer wmc = new WebMarkupContainer(fd.getName());
			wmc.add(getLabel(fd));
			FileUploadField fuf = new FileUploadField(FILE_WICKET_ID);
			wmc.add(fuf);
			uploadedFileFields.add(fuf);
			rows.add(wmc);
		}
		add(rows);
		add(supportedEncodingsSelection);
		if(!getDefinition().getShowEncodingsMenu()) {
			supportedEncodingsSelection.setVisible(false);
		}
	}
	
	/**
	 * Creates a label from a {@link NamedDescribed}. The description,
	 * if available, becomes a tooltip.
	 * @param fd
	 * @return Label.
	 */
	Label getLabel(NamedDescribed fd) {
		Label label = new Label(LABEL_WICKET_ID, fd.getName());
		if(!StringUtils.isNullOrBlank(fd.getDescription())) {
			IModel<String> model = new Model<String>(fd.getDescription());
			AttributeModifier am = new AttributeModifier("title", true, model); //$NON-NLS-1$
			label.add(am);
		}
		return label;
	}
	
	/**
	 * Action executed before the supplied callback.
	 * Puts the {@link FileUpload} instances to the {@link SimpleFilesPanelDef}
	 * instance.
	 */
	private class BeforeSubmitAction extends AbstractCallbackAction {
		
		public void callBack(AjaxRequestTarget target) {
			callback();
		}
		
		public void callBack(AjaxRequestTarget target, Form<?> form) {
			callback();
		}
		
		/**
		 * Callback implementation.
		 */
		void callback() {
			List<FileUpload> fileUploads = new ArrayList<FileUpload>();
			for(FileUploadField fuf : uploadedFileFields) {
				fileUploads.add(fuf.getFileUpload());
			}
			SimpleFilesPanelDef def = SimpleFilesPanel.this.getDefinition();
			def.setUploadedFiles(fileUploads);
			uploadedFileFields.clear();
			if(def.getShowEncodingsMenu()) {
				def.setFileEncoding(SimpleFilesPanel.this.supportedEncodingsSelection.getModelObject());
			}
		}

	}

}
