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
 * 
 */
public class PoPo extends AbstractBasePo<PoPoKey> {

	
	
	public PoPo() {
		super();
		this.key = (PoPoKey) Factory.create(PoPoKey.class);
	}
	
	/*
	 * delegate properties ��� ��� �� properties ��� ��������,
	 * �� ����:
	 */	
	public void setField1(String field1) {
		key.setField1(field1);
		fixKeysOfChildren();
	}
	
	
	public String getField1() {
		return key.getField1();
	}
	
	
	
	

}
