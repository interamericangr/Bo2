package gr.interamerican.bo2.legacy;

import interamerican.architecture.PersistentObject;


/**
 * Adapter to a legacy Bo Persistent object.
 * 
 * @param <P> Type of legacy bo Persistent object. 
 * 
 */
public interface LegacyPoAdapter
<P extends PersistentObject>{

	/**
	 * Gets the legacyPo.
	 *
	 * @return Returns the legacyPo
	 */
	public P getLegacyPo();

	/**
	 * Assigns a new value to the legacyPo.
	 *
	 * @param legacyPo the legacyPo to set
	 */
	public void setLegacyPo(P legacyPo);
	

}
