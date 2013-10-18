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
package gr.interamerican.bo2.impl.open.hibernate.tuple;

import gr.interamerican.bo2.impl.open.hibernate.tuple.instantiator.Bo2PojoComponentInstantiator;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.hibernate.mapping.Component;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.component.PojoComponentTuplizer;

/**
 * Implementation of {@link PojoComponentTuplizer} for instantiating
 * pojo components. If the component class appearing in the hibernate mapping
 * is abstract (or an interface), the instantiation is delegated to
 * a class that knows how to resolve the appropriate concrete implementation.
 */
public class Bo2PojoComponentTuplizer extends PojoComponentTuplizer {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new MyPojoComponentTuplizer object. 
	 *
	 * @param component
	 */
	public Bo2PojoComponentTuplizer(Component component) {
		super(component);
	}

	@Override
	protected Instantiator buildInstantiator(Component component) {
		if(ReflectionUtils.isConcreteClass(component.getComponentClass())) {
			return super.buildInstantiator(component);
		}
		return new Bo2PojoComponentInstantiator(component);
	}
	
}
