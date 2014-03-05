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

import gr.interamerican.bo2.utils.StringConstants;
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

/**
 * A panel that allows to add the content of a file to a model object.
 * The panel's model object is a byte[]. This panel can be used to represent
 * a byte[] field of a bean when creating a {@link SingleBeanPanel}.
 * 
 * This panel allows the user to choose a file from his local filesystem. 
 * When the form is submitted the file's content is set to the model object
 * of the panel. The user may also choose to clear the byte[] that represents
 * the file content. This is performed with the clear button. The clear button
 * submits the form, but in this case a flag indicates that the model of the panel
 * must not be updated with whatever input is in the file chooser. Otherwise, the
 * form's onSubmit would update the model again.
 * 
 * When the file panel is rendered with a non-empty model object, the size of this
 * model object is shown next to the clear button. If the clear button is pressed,
 * this is updated to zero. The size of the model object also serves as a link that
 * allows the user to download the file.
 */
public class FilePanel extends Panel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
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
	private static final String CLEAR_FILE_BUTTON_ID = "clearButton"; //$NON-NLS-1$
	
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
	 * Indicates if the Form submit occurred after pressing the clear button.
	 */
	private boolean isClearSubmit = false;

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
			protected void onSubmit() {
				FileUpload fileUpload = fileChooser.getFileUpload();
				if(fileUpload!=null && !isClearSubmit) {
					if (fileUpload.getBytes().length > 0) {
						model.setObject(fileUpload.getBytes());
					}
				}
				isClearSubmit = false;
				super.onSubmit();
			}
		};
		panelForm.setMultiPart(true);
		
		AjaxButton clearButton = new AjaxButton(
			CLEAR_FILE_BUTTON_ID, new StringResourceModel(WellKnownResourceIds.FP_CLEAR_BTN_LABEL, null)) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				model.setObject(new byte[0]);
				updateLinkLabel(target);
				isClearSubmit = true;
			}
		};
		add(panelForm);
		add(downloadFileLink);
		panelForm.add(fileChooser, clearButton, downloadFileLink);
		
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
	 * A Link that prompts the user to download a CSV with the results.
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
				getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(stream));
			}
		}
	}
	
	/**
	 * @return Returns the fileName for the local file that was picked.
	 *         In edit mode, this will always return an empty String. 
	 */
	public String getFileName() {
		if(fileChooser==null) {
			return StringConstants.EMPTY;
		}
		if(fileChooser.getFileUpload()==null) {
			return StringConstants.EMPTY;
		}
		return fileChooser.getFileUpload().getClientFileName();
	}	
}
