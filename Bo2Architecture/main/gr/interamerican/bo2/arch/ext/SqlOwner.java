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
package gr.interamerican.bo2.arch.ext;

import java.io.Serializable;

/**
 * Interface for types that encapsulate an sql statement in a property called sql.
 */
public interface SqlOwner extends Serializable {

	/**
	 * Sets the sql.
	 * 
	 * @param sql
	 */
	void setSql(String sql);

	/**
	 * @return the sql.
	 */
	String getSql();
}
