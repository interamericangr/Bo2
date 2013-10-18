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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ConditionalMethod} pairs conditions that check an object of
 * type T with methods of a class of P.
 * 
 * The ConditionalMethod is a command object, that is executed taking 
 * as arguments an object of type T, and an object of type P. The 
 * conditions will be checked against the T type argument. For each
 * condition that evaluates to true for the T argument, the method that
 * pairs with it will be invoked on the P type argument. <br/>
 * The methods, paired with the conditions, must accept no arguments.
 * Return values of the methods are ignored. <br/>
 * The constructor argument <code>invokeFirstTrueOnly</code> specifies if the
 * {@link ConditionalMethod} will continue checking all conditions that 
 * it contains, or if it will stop after the first condition that evaluates
 * to true. 
 * 
 * @param <T>
 *        Type of object being checked. 
 * @param <P> 
 *        Type of object on which the methods are invoked.
 */
public class ConditionalMethod<T, P> {
	
	/**
	 * List that contains the pairs of Condition - Method.
	 */
	List<Pair<Condition<T>,Pair<Method, Boolean>>> pairs = 
		new ArrayList<Pair<Condition<T>,Pair<Method, Boolean>>>();
	/**
	 * Specifies if conditions are being processed after the first that
	 * evaluates to true.
	 */
	boolean invokeFirstTrueOnly;
	
	/**
	 * Class of the object being checked.
	 */
	Class<T> clazzT;
	
	/**
	 * Class of the object that contains the methods.
	 */
	Class<P> clazzP;
	
	/**
	 * Executes the method(s) paired with conditions that evaluate
	 * to true for the specified argument <code>t</code> on the 
	 * specified argument <code>p</code>.
	 * 
	 * @param t
	 *        Object on which the conditions are applied.
	 * @param p
	 *        Object on which the methods are invoked.
	 */
	public void execute(T t, P p) {
		Object[] args = {t};
		Object[] noargs = {};
		for (Pair<Condition<T>,Pair<Method, Boolean>> pair : pairs) {
			Condition<T> condition = pair.getLeft();
			if (condition.check(t)) {
				Pair<Method, Boolean> methodPair = pair.getRight();
				Method method = methodPair.getLeft();
				Boolean hasArgs = methodPair.getRight();
				Object[] arguments = SelectionUtils.selection(hasArgs, args, noargs); 
				ReflectionUtils.invoke(method, p, arguments);
				if (invokeFirstTrueOnly) {
					break;
				}
			}
		}
	}
	
	/**
	 * Creates a new ConditionalMethod object. 
	 *
	 * @param invokeFirstTrueOnly
	 * @param clazzT
	 * @param clazzP
	 */
	public ConditionalMethod(boolean invokeFirstTrueOnly, Class<T> clazzT, Class<P> clazzP) {
		super();
		this.invokeFirstTrueOnly = invokeFirstTrueOnly;
		this.clazzT = clazzT;
		this.clazzP = clazzP;
	}

	/**
	 * Registers a pair of condition and name.
	 * 
	 * @param condition
	 *        Condition.
	 * @param methodName
	 *        Name of the method.
	 */
	public void register(Condition<T> condition, String methodName) {
		Method method = ReflectionUtils.getMethodByUniqueName(methodName, clazzP);
		Boolean hasArgs = false;
		@SuppressWarnings("rawtypes")
		Class[] argTypes = method.getParameterTypes();
		if (argTypes.length>1) {
			@SuppressWarnings("nls") 
			String msg = StringUtils.concat
				("Method ", methodName, " has more than one arguments");
			throw new RuntimeException(msg);
		}
		if (argTypes.length==1) {
			Class<?> argType = argTypes[0];
			if (!argType.isAssignableFrom(clazzT)) {
				@SuppressWarnings("nls") 
				String msg = StringUtils.concat
					("The argument of method ", methodName, " can't be assigned to ", clazzT.getCanonicalName());
				throw new RuntimeException(msg);
			}
			hasArgs = true;
		}
		Pair<Method, Boolean> methodPair = new Pair<Method, Boolean>(method, hasArgs);
		Pair<Condition<T>,Pair<Method, Boolean>> pair = 
			new Pair<Condition<T>, Pair<Method,Boolean>>(condition, methodPair);
		pairs.add(pair);
	}

}
