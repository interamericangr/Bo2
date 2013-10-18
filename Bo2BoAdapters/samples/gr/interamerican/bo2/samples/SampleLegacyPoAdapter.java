package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.legacy.LegacyPoAdapter;

/**
 * Sample LegacyPoAdapter
 */
public class SampleLegacyPoAdapter 
implements LegacyPoAdapter<SampleLegacyPo>, SamplePo {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * legacy po.
	 */
	private SampleLegacyPo legacyPo=new SampleLegacyPo();

	public SampleLegacyPo getLegacyPo() {		
		return legacyPo;
	}	
	public void setLegacyPo(SampleLegacyPo legacyPo) {
		this.legacyPo = legacyPo;		
	}	
	public Integer getId() {
		return legacyPo.getId();
	}	
	public void setId(Integer id) {
		legacyPo.setId(id);
	}	
	public String getString1() {
		return legacyPo.getString1();
	}	
	public void setString1(String string1) {
		legacyPo.setString1(string1);
	}	
	public String getString2() {
		return legacyPo.getString2();
	}
	public void setString2(String string2) {
		legacyPo.setString2(string2);
	}
	public Integer getKey() {		
		return getId();
	}	
	public void setKey(Integer key) {
		setId(key);
	}	
	public void tidy() {
		/* empty */		
	}
}
