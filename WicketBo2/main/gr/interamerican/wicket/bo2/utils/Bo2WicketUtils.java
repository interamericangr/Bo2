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
package gr.interamerican.wicket.bo2.utils;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.Debug;

import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for Bo2Wicket.
 */
public class Bo2WicketUtils {
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2WicketUtils.class);
	
	/**
	 * hidden constructor. 
	 * 
	 */
	private Bo2WicketUtils() {
		/* empty */
	}
	/**
	 * Returns a new CompoundPropertyModel with a new model object.
	 * 
	 * @param <T> 
	 * @param className  
	 * @return CompoundPropertyModel<T>  
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public static  <T> CompoundPropertyModel<T> returnModel(Class<T> className){
		CompoundPropertyModel<T> cpm = null;
		/*
		 * if it is an interface, act normally.
		 */
		if(className.isInterface()) {
			cpm = new CompoundPropertyModel<T>(Factory.create(className));
		}
		/*
		 * if it is an implementation, then attempt to find the
		 * interface first, then create it with the Factory.
		 * If the type has not been created in the past, null
		 * will be returned. However, this can only happen by
		 * explicitly calling this method with an implementation class,
		 * e.g. FooImpl.class.
		 */
		else {
			Debug.debug(logger, "Argument class was not an interface: " + className.getName());
			Class<?> decl = Factory.getCurrentFactory().getDeclarationType(className);
			if(decl != null) {
				cpm = new CompoundPropertyModel<T>((T) Factory.create(decl));
			} else {
				cpm = null;
			}
			
		}
		return cpm;	
	}

}
