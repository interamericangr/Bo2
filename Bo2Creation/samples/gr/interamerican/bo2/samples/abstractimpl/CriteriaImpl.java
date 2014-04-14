package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.samples.interfaces.ICriteriaDependent;

import java.util.Date;

/**
 * 
 */
public abstract class CriteriaImpl implements ICriteriaDependent<Object> {

	@Property Object criteria;
	
	public Date getCriteria() {
		return (Date) criteria;
	}

}
