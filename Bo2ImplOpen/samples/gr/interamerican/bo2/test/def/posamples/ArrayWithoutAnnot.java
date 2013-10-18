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

/**
 * 
 */
public interface ArrayWithoutAnnot 
extends PersistentObject<ArrayWithoutAnnotKey>, ModificationRecord {
	
	/**
	 * Gets InvoiceCustomerArrayWithoutAnnotNo
	 * @return InvoiceCustomerArrayWithoutAnnotNo
	 */
	public String getInvoiceCustomerArrayWithoutAnnotNo();
	
	/**
	 * Sets invoiceCustomerArrayWithoutAnnotNo
	 * @param invoiceCustomerArrayWithoutAnnotNo
	 */
	public void setInvoiceCustomerArrayWithoutAnnotNo(String invoiceCustomerArrayWithoutAnnotNo);
			
	/**
	 * Gets arrayOfObjects
	 * @return arrayOfObjects
	 */
	public Object[] getArrayOfObjects();
	
	/**
	 * Sets arrayOfObjects
	 * @param arrayOfObjects
	 */
	public void setArrayOfObjects(Object[] arrayOfObjects);
}
