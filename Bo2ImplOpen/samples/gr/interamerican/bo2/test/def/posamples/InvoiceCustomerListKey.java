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

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.impl.open.annotations.KeyProperties;

/**
 * The Interface InvoiceCustomerListKey.
 */
@KeyProperties("invoiceCustomerListNo")
public interface InvoiceCustomerListKey 
extends Key {
	
	/**
	 * Gets invoiceCustomerListNo.
	 *
	 * @return invoiceCustomerListNo
	 */
	public String getInvoiceCustomerListNo();
	
	/**
	 * Sets invoiceCustomerListNo.
	 *
	 * @param invoiceCustomerListNo the new invoice customer list no
	 */
	public void setInvoiceCustomerListNo(String invoiceCustomerListNo);

}
