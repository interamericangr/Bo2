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
 * 
 */
public interface InvoiceCustomerSet 
extends PersistentObject<InvoiceCustomerSetKey>, ModificationRecord {
	
    /**
     * Gets invoiceCustomerSetNo
     * @return invoiceCustomerSetNo
     */
    public String getInvoiceCustomerSetNo();
	
	/**
	 * Sets invoiceCustomerSetNo
	 * @param invoiceCustomerSetNo
	 */
	public void setInvoiceCustomerSetNo(String invoiceCustomerSetNo);
	
	/**
	 * Gets customers
	 * @return customers
	 */
	public Set<InvoiceCustomer> getCustomers();
	
	/**
	 * Sets customers
	 * @param customers
	 */
	public void setCustomers(Set<InvoiceCustomer> customers);
		

}
