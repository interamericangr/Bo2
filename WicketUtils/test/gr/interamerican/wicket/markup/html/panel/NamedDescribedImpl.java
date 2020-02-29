package gr.interamerican.wicket.markup.html.panel;

import java.io.Serializable;

import gr.interamerican.bo2.utils.attributes.NamedDescribed;

/**
 * The Class NamedDescribedImpl.
 */
class NamedDescribedImpl implements NamedDescribed, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The name. */
	String name;
	
	/** The description. */
	String description;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
}