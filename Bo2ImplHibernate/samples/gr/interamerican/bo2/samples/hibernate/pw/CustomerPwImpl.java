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
package gr.interamerican.bo2.samples.hibernate.pw;


import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.impl.open.hibernate.RefreshMode;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.GetAndRefresh;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.JustGet;
import gr.interamerican.bo2.test.def.posamples.Customer;


/**
 * 
 */
public class CustomerPwImpl 
extends GenericHibernatePersistenceWorker<Customer> {

	/**
	 * Creates a new CustomerPwImpl object. 
	 */
	public CustomerPwImpl() {
		super(Customer.class,new RefreshMode(JustGet.INSTANCE, JustGet.INSTANCE, GetAndRefresh.INSTANCE));		
	}
	
	
}
