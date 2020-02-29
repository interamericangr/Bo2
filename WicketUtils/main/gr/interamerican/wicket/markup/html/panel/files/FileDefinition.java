package gr.interamerican.wicket.markup.html.panel.files;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.upload.FileUpload;

import gr.interamerican.bo2.utils.attributes.Named;

/**
 * Describes a single {@link FileUpload} component of the
 * {@link MultipleFilesPanel}.
 */
public interface FileDefinition extends Named, Serializable {

	/**
	 * Gets the uploadedFile
	 *
	 * @return Returns the uploadedFile.
	 */
	FileUpload getUploadedFile();

	/**
	 * [DO NOT SET THIS]<br>
	 * Assigns a new value to the uploadedFile. When the submitAction is
	 * executed, the uploaded files can be found in this property.
	 *
	 * @param uploadedFile
	 *            the uploadedFile to set
	 */
	void setUploadedFile(FileUpload uploadedFile);

	/**
	 * Returns if the File is required.
	 * 
	 * @return If the file is required.
	 */
	boolean isRequired();

	/**
	 * Sets if the File is required.
	 * 
	 * @param required
	 *            If the File is required.
	 */
	void setRequired(boolean required);
}