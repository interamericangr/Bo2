package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.samples.interfaces.ICriteriaDependent;

import java.util.Date;

/**
 * 
 */
public interface ICriteriaDependent3 extends ICriteriaDependent<Object> {
	
	@Override
	void setCriteria(Object criteria);
	
	@Override
	public Date getCriteria();

}
