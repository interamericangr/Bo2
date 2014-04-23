package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.samples.interfaces.ICriteriaDependent;

import java.util.Date;

/**
 * 
 */
public interface ICriteriaDependent2 extends ICriteriaDependent<Object> {
	
	public Date getCriteria();

}
