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

import gr.interamerican.bo2.utils.attributes.NamedDescribed;
import gr.interamerican.wicket.callback.ChainedCallbackAction;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

/**
 * {@link ServicePanelDef} of {@link SimpleFilesPanel}
 */
public interface SimpleFilesPanelDef extends ServicePanelDef {
	
	/**
	 * [REQUIRED]<br/>
	 * Gets the fileDefinitions.
	 *
	 * @return Returns the fileDefinitions
	 */
	List<NamedDescribed> getFileDefinitions();
	
	/**
	 * Assigns a new value to the fileDefinitions.
	 * For each file definition a file input component is created.
	 *
	 * @param fileDefinitions the fileDefinitions to set
	 */
	void setFileDefinitions(List<NamedDescribed> fileDefinitions);
	
	/**
	 * Gets the uploadedFiles.
	 *
	 * @return Returns the uploadedFiles
	 */
	List<FileUpload> getUploadedFiles();
	
	/**
	 * [DO NOT SET THIS]<br/>
	 * Assigns a new value to the uploadedFiles.
	 * When the submitAction is executed, the uploaded files can
	 * be found in this property. The order is the same as the supplied
	 * fileDefinitions.
	 *
	 * @param uploadedFiles the uploadedFiles to set
	 */
	void setUploadedFiles(List<FileUpload> uploadedFiles);
	
	/**
	 * Gets the submitAction.
	 *
	 * @return Returns the submitAction
	 */
	ChainedCallbackAction getSubmitAction();
	
	/**
	 * [REQUIRED]<br/>
	 * Assigns a new value to the submitAction.
	 *
	 * @param submitAction the submitAction to set
	 */
	void setSubmitAction(ChainedCallbackAction submitAction);
	
	/**
	 * Gets the fileEncoding.
	 *
	 * @return Returns the fileEncoding
	 */
	String getFileEncoding();
	
	/**
	 * [DO NOT SET THIS]<br/>
	 * Assigns a new value to the fileEncoding.
	 * If showEncodingsMemu is true, when the submitAction is executed,
	 * this property will contain the selected file encoding.
	 *
	 * @param fileEncoding the fileEncoding to set
	 */
	void setFileEncoding(String fileEncoding);
	
	/**
	 * Gets the showEncodingsMenu.
	 *
	 * @return Returns the showEncodingsMenu
	 */
	public boolean getShowEncodingsMenu();

	/**
	 * [OPTIONAL] defaults to false.<br/>
	 * Assigns a new value to the showEncodingsMenu.
	 * If it is true, a menu that shows supported encodings is shown.
	 *
	 * @param showEncodingsMenu the showEncodingsMenu to set
	 */
	public void setShowEncodingsMenu(boolean showEncodingsMenu);
	
}
