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
package gr.interamerican.bo2.arch.utils.copiers;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.ext.Copier;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

/**
 * Copies {@link Money}.
 */
public class MoneyCopier implements Copier<Money>{

	public Money copy(Money objectToCopy) {
		if(objectToCopy == null) {
			return null;
		}
		
		Money newMoney;
		
		Constructor<?> constructor;
		
		constructor = ReflectionUtils.getConstructor(objectToCopy.getClass(), BigDecimal.class);
		if(constructor != null) {
			newMoney = (Money) ReflectionUtils.newInstance(constructor, objectToCopy.getAmount());
			newMoney.setCurrencyCode(objectToCopy.getCurrencyCode());
			return newMoney;
		}
		
		/*
		 * Using the above constructor, assuming it exists, is preferable.
		 * If it does not exist, this is kept as a failsafe.
		 */
		constructor = ReflectionUtils.getNoArgConstructor(objectToCopy.getClass());
		if(constructor != null) {
			newMoney = (Money) ReflectionUtils.newInstance(constructor, new Object[0]);
			newMoney.setCurrencyCode(objectToCopy.getCurrencyCode());
			newMoney.setAmount(objectToCopy.getAmount());
			return newMoney;
		}
		String msg = "Could not create instance of " + objectToCopy.getClass(); //$NON-NLS-1$
		throw new RuntimeException(msg);
	}

}
