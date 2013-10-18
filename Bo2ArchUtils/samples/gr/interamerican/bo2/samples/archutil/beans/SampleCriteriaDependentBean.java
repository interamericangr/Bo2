package gr.interamerican.bo2.samples.archutil.beans;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

/**
 * {@link CriteriaDependent} bean, who's criteria is an {@link Integer}.
 */
public class SampleCriteriaDependentBean 
implements CriteriaDependent<BeanWith2Fields> {
	
	/**
	 * Criteria.
	 */
	BeanWith2Fields criteria;
	
	/**
	 * Property.
	 */
	String property1;

	
	public BeanWith2Fields getCriteria() {
		return criteria;
	}

	
	public void setCriteria(BeanWith2Fields criteria) {
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
