/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.arch;

/**
 * An object that depends by a persistent object.
 * 
 * @param <P> Type of persistent object. 
 */
public interface PoDependent<P extends PersistentObject<?>> {
	
	/**
	 * Gets the persistent object.
	 * 
	 * @return Returns the Persistent object.
	 */
	public P getPo();
	
	/**
	 * Sets the persistent object.
	 * 
	 * @param po New persistent object.
	 */
	public void setPo(P po);

}
