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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.CollectionUtils;

/**
 * An {@link IModel} for a {@link List} of {@link FileUpload}'s (commonly used
 * on {@link FileUploadField}) that is based on a {@link FileDefinition}
 * object.<br>
 * The list is considered to contain only one {@link FileUpload} (others are
 * ignored) and is wrapped on the {@link FileUpload} within the FileDefinition .
 */
public class FileUploadModel implements IModel<List<FileUpload>> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Actual Backing Object
	 */
	private final FileDefinition fileDefinition;

	/**
	 * Public Constructor.
	 * 
	 * @param fileDefinition
	 *            Actual Backing Object
	 */
	public FileUploadModel(FileDefinition fileDefinition) {
		this.fileDefinition = fileDefinition;
	}

	@Override
	public void detach() {
		// empty
	}

	@Override
	public List<FileUpload> getObject() {
		List<FileUpload> result = new ArrayList<FileUpload>();
		FileUpload existing = fileDefinition.getUploadedFile();
		if (existing != null) {
			result.add(existing);
		}
		return result;
	}

	@Override
	public void setObject(List<FileUpload> object) {
		FileUpload upload = null;
		if (!CollectionUtils.isNullOrEmpty(object)) {
			upload = object.get(0);
		}
		fileDefinition.setUploadedFile(upload);
	}
}