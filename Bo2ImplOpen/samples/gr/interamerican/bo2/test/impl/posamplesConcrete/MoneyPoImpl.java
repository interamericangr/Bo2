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

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.MoneyKey;
import gr.interamerican.bo2.test.def.posamples.MoneyPo;

/**
 * 
 */
public class MoneyPoImpl 
extends AbstractModificationRecordPo<MoneyKey> 
implements MoneyPo {
	
	
	/**
	 * uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * money
	 */
	private Money money;
	
	/**
	 * ���������� money.
	 *
	 * @return money
	 */
	
	public Money getMoney() {
		return money;
	}

	/**
	 * ��������� money.
	 *
	 * @param money 
	 */
	public void setMoney(Money money) {
		this.money = money;
	}

	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public MoneyPoImpl() {
		super();
		this.key = Factory.create(MoneyKey.class);
	}

	public String getMoneyNo() {		
		return key.getMoneyNo();
	}
	

	public void setMoneyNo(String moneyNo) {
		key.setMoneyNo(moneyNo);
	}
	

}
