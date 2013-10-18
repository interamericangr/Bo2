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

import static gr.interamerican.wicket.util.resource.WellKnownResourceIds.FP_CLEAR_BTN_LABEL;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.callback.MethodBasedCallbackAction;
import gr.interamerican.wicket.factories.ButtonFactory;
import gr.interamerican.wicket.factories.LinkFactory;
import gr.interamerican.wicket.markup.html.form.SimpleCommandForm;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.util.resource.ByteArrayAsResourceStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.IResourceStream;

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
	private static final String FILE_CHOOSER_ID = "fileChooser"; //$NON-NLS-1$
	
	/**
	 * Wicket id.
	 */
	private static final String FORM_ID = "form"; //$NON-NLS-1$
	
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
	private AjaxLink<String> downloadFileLink;
	
	/**
	 * Link label.
	 */
	private Label linkLabel;
	
	/**
	 * Current content.
	 */
	IModel<byte[]> model;
	
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
		
		CallbackAction onDownloadLinkClick = 
			new MethodBasedCallbackAction("onDownloadLinkClick", this); //$NON-NLS-1$
		downloadFileLink = LinkFactory.createLink(DOWNLOAD_FILE_LINK_ID, onDownloadLinkClick);
		downloadFileLink.setOutputMarkupId(true);
		linkLabel = new Label(LINK_LABEL_ID, new Model<String>());
		downloadFileLink.add(linkLabel);
		updateLinkLabel(null);
		
		fileChooser = new FileUploadField(FILE_CHOOSER_ID, new Model<FileUpload>());
		
		
		SimpleCommand onFormSubmit = 
			new MethodBasedCallbackAction("onFormSubmit", this); //$NON-NLS-1$
		Form<Void> panelForm = new SimpleCommandForm<Void>(FORM_ID, onFormSubmit);
		panelForm.setMultiPart(true);		
		
		CallbackAction onClearButtonClick = 
			new MethodBasedCallbackAction("onClearButtonClick", this); //$NON-NLS-1$
		IModel<String> buttonModel = new StringResourceModel(FP_CLEAR_BTN_LABEL, null);
		AjaxButton clearButton = ButtonFactory.createButton
			(CLEAR_FILE_BUTTON_ID, buttonModel, onClearButtonClick, null);
		
		add(panelForm);
		add(downloadFileLink);
		
		panelForm.add(fileChooser, clearButton, downloadFileLink);
		
	}
	
	
	/**
	 * Event handler for the click event of the clear button.
	 * 
	 * @param target
	 */
	void onClearButtonClick(AjaxRequestTarget target) {
		model.setObject(new byte[0]);
		updateLinkLabel(target);
		isClearSubmit = true;		
	}
	
	/**
	 * Event handler for the form submit event.
	 */
	void onFormSubmit() {
		FileUpload fileUpload = fileChooser.getFileUpload();
		if(fileUpload!=null && !isClearSubmit) {
			if (fileUpload.getBytes().length > 0) {
				model.setObject(fileUpload.getBytes());
			}
		}
		isClearSubmit = false;
	}
	
	/**
	 * Event handler for the click event of the download link.
	 */
	void onDownloadLinkClick() {
		byte[] bytes = model.getObject();
		if (bytes!=null && bytes.length>0) {
			IResourceStream stream = new ByteArrayAsResourceStream(bytes);
			ResourceStreamRequestTarget target = new ResourceStreamRequestTarget(stream); 
			getRequestCycle().setRequestTarget(target);
		}	
	}
	
	
	/**
	 * Refreshes the link label.
	 *  
	 * @param target
	 */
	@SuppressWarnings("nls")
	void updateLinkLabel(AjaxRequestTarget target) {
		if(target!=null) {
			target.addComponent(downloadFileLink);
		}
		byte[] content = model.getObject()!=null ? model.getObject() : new byte[0];
		Bytes bytes = Bytes.bytes(content.length);
		linkLabel.setDefaultModelObject("[" + bytes.toString() + "]");
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
