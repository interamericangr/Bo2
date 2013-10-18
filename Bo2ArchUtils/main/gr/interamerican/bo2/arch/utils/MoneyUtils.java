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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyCalculator;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

/**
 * Utility class for money.
 */
public class MoneyUtils {
	
	/**
	 * Private constructor of utility class.
	 *
	 */
	private MoneyUtils() {/* empty hidden constructor of utility class */}
	
	/**
	 * Iterates a collection, and sums the Money elements contained in 
	 * the specified property of each object.
	 * 
	 * Nulls are excluded from the addition.
	 *  
	 * @param <T>
	 *        Type of elements in the collection.
	 * @param collection
	 *        Collection to iterate.
	 * @param clazz
	 *        Type of elements in the collection.
	 * @param property
	 *        Name of the property that contains the money element.
	 * @param currency
	 *        Currency of the money elements.
	 * 
	 * @return Returns the sum.
	 */
	public static <T> Money sum
	(Collection<T> collection, Class<T> clazz, String property, Currency currency) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);		
		MoneyCalculator calc = new MoneyCalculator(currency, false);
		for (T t : collection) {
			Money money = (Money) JavaBeanUtils.getProperty(pd, t);
			if (money!=null) {
				calc.add(money);
			}
		}
		return calc.getResult();
	}
	
	/**
	 * Iterates a collection, checks a condition against each element of
	 * the collection and if the condition is fulfilled, then sums the Money 
	 * elements contained in the specified property of each object.
	 * 
	 * Nulls are excluded from the addition.
	 *  
	 * @param <T>
	 *        Type of elements in the collection.
	 * @param collection
	 *        Collection to iterate.
	 * @param clazz
	 *        Type of elements in the collection.
	 * @param property
	 *        Name of the property that contains the money element.
	 * @param currency
	 *        Currency of the money elements.
	 * @param condition 
	 *        Condition to check against each element.
	 * 
	 * @return Returns the sum.
	 */
	public static <T> Money sum (
			Collection<T> collection, Class<T> clazz, String property, 
			Currency currency, Condition<T> condition) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);		
		MoneyCalculator calc = new MoneyCalculator(currency, false);
		for (T t : collection) {
			if (condition.check(t)) {
				Money money = (Money) JavaBeanUtils.getProperty(pd, t);
				if (money!=null) {
					calc.add(money);
				}
			}			
		}
		return calc.getResult();
	}
	
	/**
	 * Calculates a {@link Money} field that is calculated as a percentage 
	 * of another money field in a collection of elements . <br/>
	 * 
	 * This method iterates a collection and sets a new value to a
	 * specified property of each element. The new value is the result
	 * of a calculation. <br/>
	 * The calculation of each row takes into account the remainder 
	 * that was created by the rounding that took place during the
	 * previous calculation, due to rounding the result to the decimal 
	 * digits defined by the currency. <br/>
	 *        
	 * @param collection
	 *        Collection with the elements.
	 * @param clazz
	 *        Class of each element in the collection.
	 * @param prc
	 *        Percentage being applied on the calculation.
	 * @param amountProperty
	 *        Name of the property that produces the base amount for
	 *        the calculation.
	 * @param calculatedProperty
	 *        Name of the property that is filled with the result of the
	 *        calculation.
	 * @param currency
	 *        Currency.
	 * @param <T>
	 *        Type of elements in the collection.
	 */
	public static <T> void calcPercentage (
			Collection<T> collection, Class<T> clazz, BigDecimal prc,
			String amountProperty, String calculatedProperty, Currency currency) {
		PropertyDescriptor amountPd = 
			JavaBeanUtils.getPropertyDescriptor(clazz, amountProperty);		
		PropertyDescriptor calculatedPd = 
			JavaBeanUtils.getPropertyDescriptor(clazz, calculatedProperty);
		MoneyCalculator calc = new MoneyCalculator(currency, true);
		for (T t : collection) {
			Money amount = (Money) JavaBeanUtils.getProperty(amountPd, t);
			if (amount!=null) {
				calc.set(amount);
				Money calculated = calc.multiply(prc);
				JavaBeanUtils.setProperty(calculatedPd, calculated, t);
			}
		}
	}
	
	/**
	 * Calculates a {@link Money} field that is calculated as a percentage 
	 * of another money field in a collection of elements . <br/>
	 * 
	 * This method iterates a collection and sets a new value to a
	 * specified property of each element. The new value is the result
	 * of a calculation. <br/>
	 * The calculation of each row takes into account the remainder 
	 * that was created by the rounding that took place during the
	 * previous calculation, due to rounding the result to the decimal 
	 * digits defined by the currency. <br/>
	 *        
	 * @param collection
	 *        Collection with the elements.
	 * @param clazz
	 *        Class of each element in the collection.
	 * @param prcProperty
	 *        Name of the property that produces the percentage that is
	 *        being applied on the calculation.
	 * @param amountProperty
	 *        Name of the property that produces the base amount for
	 *        the calculation.
	 * @param calculatedProperty
	 *        Name of the property that is filled with the result of the
	 *        calculation.
	 * @param currency
	 *        Currency.
	 * @param <T>
	 *        Type of elements in the collection.
	 */
	public static <T> void calcPercentage (
			Collection<T> collection, Class<T> clazz, String prcProperty,
			String amountProperty, String calculatedProperty, Currency currency) {
		PropertyDescriptor amountPd = 
			JavaBeanUtils.getPropertyDescriptor(clazz, amountProperty);		
		PropertyDescriptor calculatedPd = 
			JavaBeanUtils.getPropertyDescriptor(clazz, calculatedProperty);
		PropertyDescriptor prcPd = 
			JavaBeanUtils.getPropertyDescriptor(clazz, prcProperty);		
		
		MoneyCalculator calc = new MoneyCalculator(currency, true);
		for (T t : collection) {
			Money amount = (Money) JavaBeanUtils.getProperty(amountPd, t);
			BigDecimal prc = (BigDecimal) JavaBeanUtils.getProperty(prcPd, t);
			if (amount!=null && prc!=null) {
				calc.set(amount);
				Money calculated = calc.multiply(prc);
				JavaBeanUtils.setProperty(calculatedPd, calculated, t);
			}
		}
	}
	
	


}
