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
package gr.interamerican.bo2.test.def.posamples;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;

import java.util.Set;

/**
 * An {@link InvoiceCustomer} has a reference to an actual Customer.
 * This is a many-to-one relationship.
 */
@SuppressWarnings("all")
public interface Customer 
extends CustomerKP, PersistentObject<CustomerKey>, ModificationRecord {
	
	public String getCustomerName();
	
	public void setCustomerName(String customerName);
	
	public String getTaxId();
	
	public void setTaxId(String taxId);
	
	public Set<CustomerAddress> getAddresses();
	
	public void setAddresses(Set<CustomerAddress> addresses); 
	
}
