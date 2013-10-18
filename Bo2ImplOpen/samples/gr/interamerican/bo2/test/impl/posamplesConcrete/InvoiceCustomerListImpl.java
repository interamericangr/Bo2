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
package gr.interamerican.bo2.test.impl.posamplesConcrete;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerList;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerListKey;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.List;

/**
 * 
 */
public class InvoiceCustomerListImpl 
extends AbstractModificationRecordPo<InvoiceCustomerListKey> 
implements InvoiceCustomerList {
	
	/**
	 * uid.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * list with invoice customers
	 */
	@Child
	private List<InvoiceCustomer> invoiceList;
	
	/**
	 * ���������� invoiceList.
	 *
	 * @return invoiceList
	 */
	
	public List<InvoiceCustomer> getInvoiceList() {
		return invoiceList;
	}


	/**
	 * ��������� invoiceList.
	 *
	 * @param invoiceList 
	 */
	public void setInvoiceList(List<InvoiceCustomer> invoiceList) {
		this.invoiceList = invoiceList;
	}


	public String getInvoiceCustomerListNo() {		
		return key.getInvoiceCustomerListNo();
	}
	

	public void setInvoiceCustomerListNo(String invoiceNo) {
		key.setInvoiceCustomerListNo(invoiceNo);
	}

  
	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public InvoiceCustomerListImpl() {
		super();
		this.key = Factory.create(InvoiceCustomerListKey.class);
	}
	

}
