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
package gr.interamerican.bo2.samples.archutil.conditions;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.samples.archutil.beans.MoneyBean;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.math.BigDecimal;

/**
 * Checks if the money property of a {@link MoneyBean} is greater than zero.
 */
public class IsGreaterThanZero
implements Condition<MoneyBean>{
	
	public boolean check(MoneyBean t) {	
		Money m = t.getMoneyAmount1();
		if (m==null) {
			return false;
		}
		return BigDecimal.ZERO.compareTo(m.getAmount()) < 0;
	}

}
