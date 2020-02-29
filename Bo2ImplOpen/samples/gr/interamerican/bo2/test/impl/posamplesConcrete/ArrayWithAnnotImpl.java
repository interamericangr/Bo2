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
import gr.interamerican.bo2.test.def.posamples.ArrayWithAnnot;
import gr.interamerican.bo2.test.def.posamples.ArrayWithAnnotKey;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * The Class ArrayWithAnnotImpl.
 */
public class ArrayWithAnnotImpl 
extends AbstractModificationRecordPo<ArrayWithAnnotKey> 
implements ArrayWithAnnot {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** an array of objects. */
	@Child
	private Object[] arrayOfObjects;	
	

	/**
	 * ���������� arrayOfObjects.
	 *
	 * @return arrayOfObjects
	 */
	
	@Override
	public Object[] getArrayOfObjects() {
		return arrayOfObjects;
	}


	/**
	 * ��������� arrayOfObjects.
	 *
	 * @param arrayOfObjects the new array of objects
	 */
	@Override
	public void setArrayOfObjects(Object[] arrayOfObjects) {
		this.arrayOfObjects = arrayOfObjects;
	}


	@Override
	public String getInvoiceCustomerArrayNo() {		
		return key.getInvoiceCustomerArrayNo();
	}
	

	@Override
	public void setInvoiceCustomerArrayNo(String invoiceCustomerArrayNo) {
		key.setInvoiceCustomerArrayNo(invoiceCustomerArrayNo);
	}

  
	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public ArrayWithAnnotImpl() {
		super();
		this.key = Factory.create(ArrayWithAnnotKey.class);
	}
	

}
