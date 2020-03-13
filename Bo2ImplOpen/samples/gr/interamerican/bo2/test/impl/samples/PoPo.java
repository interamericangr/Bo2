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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;

/**
 * The Class PoPo.
 */
public class PoPo extends AbstractBasePo<PoPoKey> {

	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Public Constructor 
	 *
	 */
	public PoPo() {
		this.key = Factory.create(PoPoKey.class);
	}
	
	/**
	 * delegate properties για όλα τα properties του κλειδιού,
	 * ως εξής:
	 * @param field1 
	 */	
	public void setField1(String field1) {
		key.setField1(field1);
		fixKeysOfChildren();
	}
	
	
	/**
	 * @return field 1
	 */
	public String getField1() {
		return key.getField1();
	}
	
	
	
	

}
