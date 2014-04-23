package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.samples.interfaces.ICriteriaDependent;

import java.util.Date;

/**
 * ICriteriaDependent implementation with covariant getter
 */
public abstract class CriteriaDependentImpl implements ICriteriaDependent<Object> {

	/**
	 * criteria
	 */
	@Property Object criteria;
	
	public Date getCriteria() {
		return (Date) criteria;
	}

}
