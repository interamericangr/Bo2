package gr.interamerican.bo2.samples.abstractimpl.pkg2;

import gr.interamerican.bo2.samples.abstractimpl.MyCriteriaKeeper;

/**
 * 
 */
public abstract class MyCriteriaKeeperSub extends MyCriteriaKeeper {
	
	/**
	 * 
	 */
	public static final int ID = 2;

	/**
	 * Creates a new MyCriteriaKeeperSub object. 
	 *
	 */
	public MyCriteriaKeeperSub() {
		setCriteria(ID);
	}

}
