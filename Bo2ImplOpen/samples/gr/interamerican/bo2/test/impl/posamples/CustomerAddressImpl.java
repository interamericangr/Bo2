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
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.CustomerAddressKey;

/**
 * 
 */
@DelegateKeyProperties("")
public abstract class CustomerAddressImpl 
extends AbstractModificationRecordPo<CustomerAddressKey> 
implements CustomerAddress {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * street
	 */
	@Property
	@SuppressWarnings("unused")
	private String street;
	
	/**
	 * number
	 */
	@Property
	@SuppressWarnings("unused")
	private String streetNo;

}
