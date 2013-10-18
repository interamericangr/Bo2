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
package gr.interamerican.bo2.impl.open.hibernate.tuple.instantiator;

import gr.interamerican.bo2.impl.open.creation.Factory;

import java.io.Serializable;

import org.hibernate.mapping.Component;
import org.hibernate.tuple.Instantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link Instantiator} that knows how to resolve concrete pojo component
 * implementations given their abstract or interface representation. 
 */
public class Bo2PojoComponentInstantiator implements Instantiator {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2PojoComponentInstantiator.class);

	/**
	 * Mapped component class.
	 */
	private Class<?> componentClass;

	/**
	 * Creates a new Bo2PojoInstantiator object.
	 * 
	 * @param component
	 */
	public Bo2PojoComponentInstantiator(Component component) {
		this.componentClass = component.getComponentClass();
	}

	@SuppressWarnings("nls")
	public Object instantiate(Serializable id) {
		String s = id == null ? "null" : id.toString();
		String msg = "Attempted to initialize a Component of type" + componentClass.getName() + "with id " + s;
		throw new RuntimeException(msg);
	}

	public boolean isInstance(Object object) {
		return componentClass.isInstance(object);
	}

	@SuppressWarnings("nls")
	public Object instantiate() {
		if(logger.isDebugEnabled()) {
			logger.debug("Instantiating " + componentClass.getName());
		}
		return Factory.create(componentClass);
	}

}
