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

import java.io.Serializable;
import java.util.Date;

/**
 * {@link ModificationRecord} contains information about the
 * modification of an object. PersistentObjects that implement
 * this interface may store data that can be used for optimistic
 * conversation scope locking.
 */
public interface ModificationRecord extends Serializable {
	
	/**
	 * Timestamp of last modification.
	 *
	 * @return Returns the timestamp of the last modification.
	 */
	public Date getLastModified();
	
	/**
	 * Sets the timestamp of last modification.
	 *
	 * @param lastModified New Timestamp of last modification.
	 */
	public void setLastModified(Date lastModified);
	
	/**
	 * Gets the identifier of the performer of the last
	 * modification.
	 *
	 * @return Returns the identifier of the performar of the
	 *         las modification on this object.
	 */
	public String getLastModifiedBy();
	
	/**
	 * Sets the identifier of the performer of the last modification.
	 *
	 * @param lastModifiedBy
	 *        New identifier for performer of last modification.
	 */
	public void setLastModifiedBy(String lastModifiedBy);

}
