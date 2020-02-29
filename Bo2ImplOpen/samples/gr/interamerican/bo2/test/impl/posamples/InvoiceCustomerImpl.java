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
package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;

/**
 * The Class InvoiceCustomerImpl.
 */
@DelegateKeyProperties("")
public abstract class InvoiceCustomerImpl 
extends AbstractModificationRecordPo<InvoiceKey> 
implements InvoiceCustomer {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * role id.
	 */
	@Property
	private Integer roleId;

	/**
	 * address no to send invoice.
	 */
	@Property
	private Integer addressNoForInvoice;
	
	/** customer info. */
	@Property
	private Customer customer;

}
