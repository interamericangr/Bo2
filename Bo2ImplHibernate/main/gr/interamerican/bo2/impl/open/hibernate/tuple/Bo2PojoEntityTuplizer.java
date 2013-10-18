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

import gr.interamerican.bo2.impl.open.hibernate.tuple.instantiator.Bo2PojoEntityInstantiator;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

/**
 * Implementation of {@link PojoEntityTuplizer} for instantiating
 * pojo entities. If the entity class appearing in the hibernate mapping
 * is abstract (or an interface), the instantiation is delegated to
 * a class that knows how to resolve the appropriate concrete implementation.
 */
public class Bo2PojoEntityTuplizer extends PojoEntityTuplizer {

	/**
	 * Creates a new MyPojoEntityTuplizer object. 
	 *
	 * @param entityMetamodel
	 * @param mappedEntity
	 */
	public Bo2PojoEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
		super(entityMetamodel, mappedEntity);
	}
	
	@Override
	protected Instantiator buildInstantiator(PersistentClass persistentClass) {
		if(ReflectionUtils.isConcreteClass(persistentClass.getMappedClass())) {
			return super.buildInstantiator(persistentClass);
		}
		return new Bo2PojoEntityInstantiator(persistentClass);
	}

}
