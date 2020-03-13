package gr.interamerican.wicket.markup.html.panel.files;

import org.apache.wicket.markup.html.form.upload.FileUpload;

/**
 * 
 */
public class FileDefinitionImpl implements FileDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String name;
	/**
	 * 
	 */
	FileUpload uploadedFile;
	/**
	 * 
	 */
	boolean required;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public FileUpload getUploadedFile() {
		return uploadedFile;
	}

	@Override
	public void setUploadedFile(FileUpload uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}

}