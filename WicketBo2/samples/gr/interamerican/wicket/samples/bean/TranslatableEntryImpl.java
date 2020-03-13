package gr.interamerican.wicket.samples.bean;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;

/**
 * The Class TranslatableEntryImpl.
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
	 * @param typeId the type id
	 * @param subTypeId the sub type id
	 * @param code the code
	 * @param name the name
	 */
	public TranslatableEntryImpl(Long typeId, Long subTypeId, Long code, String name) {
		super(typeId, subTypeId, code, name);
	}
	
	@Override
	public String getTranslation(Long languageId) {			
		return getName();
	}
	
	@Override
	public Long getTranslationResourceId() {			
		return getCode();
	}
	
}
