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
import gr.interamerican.bo2.test.def.posamples.ArrayWithoutAnnot;
import gr.interamerican.bo2.test.def.posamples.ArrayWithoutAnnotKey;

/**
 * 
 */
public class ArrayWithoutAnnotImpl 
extends AbstractModificationRecordPo<ArrayWithoutAnnotKey> 
implements ArrayWithoutAnnot {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * an array of objetcs
	 */
	private Object[] arrayOfObjects;	
	

	/**
	 * ���������� arrayOfObjects.
	 *
	 * @return arrayOfObjects
	 */
	
	public Object[] getArrayOfObjects() {
		return arrayOfObjects;
	}

	/**
	 * ��������� arrayOfObjects.
	 *
	 * @param arrayOfObjects 
	 */
	public void setArrayOfObjects(Object[] arrayOfObjects) {
		this.arrayOfObjects = arrayOfObjects;
	}


	public String getInvoiceCustomerArrayWithoutAnnotNo() {		
		return key.getInvoiceCustomerArrayWithoutAnnotNo();
	}
	

	public void setInvoiceCustomerArrayWithoutAnnotNo(String invoiceCustomerArrayWithoutAnnotNo) {
		key.setInvoiceCustomerArrayWithoutAnnotNo(invoiceCustomerArrayWithoutAnnotNo);
	}

  
	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public ArrayWithoutAnnotImpl() {
		super();
		this.key = Factory.create(ArrayWithoutAnnotKey.class);
	}
	

}
