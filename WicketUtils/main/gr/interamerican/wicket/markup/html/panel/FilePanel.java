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

import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A panel that allows to add the content of a file to a model object.
 * The panel's model object is a byte[]. This panel can be used to represent
 * a byte[] field of a bean when creating a {@link SingleBeanPanel}.
 * 
 * This panel allows the user to choose a file from his local filesystem. 
 * When the upload button is pressed the file's content is set to the model object
 * of the panel. Form submissions not happening from pressing the upload button
 * (e.g. from outer forms) do not do anything.
 * 
 * When the file panel is rendered with a non-empty model object, the size of this
 * model object is shown next to the upload button.
 */
public class FilePanel extends Panel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * LOG
	 */
	static final Logger LOG = LoggerFactory.getLogger(FilePanel.class.getName());
	
	/**
	 * Wicket id.
	 */
	static final String FILE_CHOOSER_ID = "fileChooser"; //$NON-NLS-1$
	
	/**
	 * Wicket id.
	 */
	static final String FORM_ID = "form"; //$NON-NLS-1$
	
	/**
	 * Wicket id.
	 */
	private static final String UPLOAD_FILE_BUTTON_ID = "uploadButton"; //$NON-NLS-1$
	
	/**
	 * Wicket id.
	 */
	private static final String DOWNLOAD_FILE_LINK_ID = "fileLink"; //$NON-NLS-1$
	
	/**
	 * Wicket id.
	 */
	private static final String LINK_LABEL_ID = "linkLabel"; //$NON-NLS-1$
	
	/**
	 * Link that allows the user to download the file.
	 */
	private Link<String> downloadFileLink;
	
	/**
	 * Link label.
	 */
	private Label linkLabel;
	
	/**
	 * Current content.
	 */
	private IModel<byte[]> model;
	
	/**
	 * File chooser.
	 */
	private FileUploadField fileChooser;
	
	/**
	 * uploadButtonSubmit
	 */
	boolean uploadButtonSubmit = false;
	
	/**
	 * Client file name
	 */
	String fileName;

	/**
	 * Creates a new FilePanel object. 
	 *
	 * @param id
	 * @param model
	 */
	public FilePanel(String id, final IModel<byte[]> model) {
		super(id, model);
		setOutputMarkupId(true);
		
		this.model = model;
		
		downloadFileLink = new DownloadTemplateLink(DOWNLOAD_FILE_LINK_ID);
		downloadFileLink.setOutputMarkupId(true);
		linkLabel = new Label(LINK_LABEL_ID, new Model<String>());
		downloadFileLink.add(linkLabel);
		updateLinkLabel(null);
		
		fileChooser = new FileUploadField(FILE_CHOOSER_ID, new ListModel<FileUpload>(new ArrayList<FileUpload>()));
		
		Form<Void> panelForm = new Form<Void>(FORM_ID) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			@SuppressWarnings("nls")
			protected void onSubmit() {
				if(!uploadButtonSubmit) {
					LOG.debug("form submit on other form processing - does nothing");
					return; //do not process submits from outer forms
				}
				FileUpload fileUpload = fileChooser.getFileUpload();
				if(fileUpload!=null && fileUpload.getBytes().length > 0) {
					LOG.debug("uploaded template: " + fileUpload.getBytes().length + " bytes");
					model.setObject(fileUpload.getBytes());
					fileName = fileUpload.getClientFileName();
				}
				updateLinkLabel(null);
				uploadButtonSubmit = false;
				super.onSubmit();
			}
		};
		panelForm.setMultiPart(true);
		
		AjaxButton uploadButton = new AjaxButton(
				UPLOAD_FILE_BUTTON_ID, new StringResourceModel(WellKnownResourceIds.FP_UPLOAD_BTN_LABEL, null)) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(FilePanel.this);
				uploadButtonSubmit = true;
			}
		};
			
		add(panelForm);
		add(downloadFileLink);
		panelForm.add(fileChooser, uploadButton, downloadFileLink);
		
	}
	
	/**
	 * Refreshes the link label.
	 *  
	 * @param target
	 */
	@SuppressWarnings("nls")
	private void updateLinkLabel(AjaxRequestTarget target) {
		if(target!=null) {
			target.add(downloadFileLink);
		}
		byte[] content = model.getObject()!=null ? model.getObject() : new byte[0];
		Bytes bytes = Bytes.bytes(content.length);
		linkLabel.setDefaultModelObject("[" + bytes.toString() + "]");
	}
	
	/**
	 * A Link that prompts the user to download the file.
	 */
	private class DownloadTemplateLink extends Link<String> {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new ExportCsvLink object. 
		 *
		 * @param id
		 */
		public DownloadTemplateLink(String id) {
			super(id);
		}

		@SuppressWarnings("nls")
		@Override
		public void onClick() {
			IResourceStream stream = new AbstractResourceStream () {
				/**
				 * serialVersionUID.
				 */
				private static final long serialVersionUID = 1L;
				
				InputStream in = null;
				public InputStream getInputStream()	throws ResourceStreamNotFoundException {
					in = new ByteArrayInputStream(model.getObject());
					return in;
				}
				public void close() throws IOException {
					in.close();
				}
			};
			if(model.getObject()!=null && model.getObject().length >0) {
				LOG.debug("downloading template: " + model.getObject().length + " bytes");
				getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(stream));
			} else {
				LOG.debug("download size 0, no action performed on link clink");
			}
		}
	}
	
	/**
	 * @return Returns the fileName for the local file that was picked.
	 *         In edit mode, this will always return an empty String. 
	 */
	public String getFileName() {
		return fileName;
	}	
}
