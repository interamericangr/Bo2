package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.CompanyUserKey;

/**
 * 
 */
public class CompanyUserKeyImpl extends AbstractKey implements CompanyUserKey {

	Long id;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected Object[] getElements() {
		return new Object[]{id};
	}

}
