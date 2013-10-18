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
package gr.interamerican.bo2.samples.hibernate.queries;

import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.hibernate.AbstractHqlQuery;
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * Hibernate HQL query to read a user based on a userId.
 */
public class ReadUserHQLQueryImpl 
extends AbstractHqlQuery<User> {
	
	/**
	 * user id.
	 */
	private @Parameter Integer userId;

	@Override
	protected String hql() {
		return "select u from User u where u.key.userId = :userId"; //$NON-NLS-1$
	}
	
	/**
	 * Gets the user id.
	 * 
	 * @return Returns the user id.
	 */
	public Integer getUserId() {
		return userId;
	}
	
	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
