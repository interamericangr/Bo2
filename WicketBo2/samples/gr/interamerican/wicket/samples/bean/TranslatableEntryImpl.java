package gr.interamerican.wicket.samples.bean;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;

/**
 * 
 */
public class TranslatableEntryImpl extends TypedSelectableImpl<Long>
implements TranslatableEntry<Long, Long, Long> {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new TranslatableEntryImpl object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param code
	 * @param name
	 */
	public TranslatableEntryImpl(Long typeId, Long subTypeId, Long code, String name) {
		super(typeId, subTypeId, code, name);
	}
	
	public String getTranslation(Long languageId) {			
		return getName();
	}
	
	public Long getTranslationResourceId() {			
		return getCode();
	}
	
}
