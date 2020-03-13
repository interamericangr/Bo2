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
 * An {@link Invoice} owns many {@link InvoiceLine}s.
 * This is a one-to-many relationship.
 */
@SuppressWarnings("all")
public interface InvoiceLine 
extends InvoiceLineKP, PersistentObject<InvoiceLineKey>, ModificationRecord {
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType();
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Integer type);
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount();
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount);
	
	/**
	 * Sets the sub lines.
	 *
	 * @param subLines the new sub lines
	 */
	public void setSubLines(Set<InvoiceSubLine> subLines);
	
	/**
	 * Gets the sub lines.
	 *
	 * @return the sub lines
	 */
	public Set<InvoiceSubLine> getSubLines();
	
	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer();
	
	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public void setCustomer(Customer customer);

}
