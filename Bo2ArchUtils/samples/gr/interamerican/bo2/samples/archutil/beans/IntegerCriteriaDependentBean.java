package gr.interamerican.bo2.samples.archutil.beans;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;

/**
 * {@link CriteriaDependent} bean, who's criteria is an {@link Integer}.
 */
public class IntegerCriteriaDependentBean 
implements CriteriaDependent<Integer> {
	
	/**
	 * Criteria.
	 */
	Integer criteria;
	
	/**
	 * Property.
	 */
	String property1;

	
	public Integer getCriteria() {
		return criteria;
	}

	
	public void setCriteria(Integer criteria) {
		this.criteria = criteria;
	}

	/**
	 * Gets the property1.
	 *
	 * @return Returns the property1
	 */
	public String getProperty1() {
		return property1;
	}

	/**
	 * Assigns a new value to the property1.
	 *
	 * @param property1 the property1 to set
	 */
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	
	
	

}
