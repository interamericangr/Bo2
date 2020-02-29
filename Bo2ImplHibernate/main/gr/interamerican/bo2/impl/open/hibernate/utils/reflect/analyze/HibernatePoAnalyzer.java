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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.analysis.CompositeProperty;
import gr.interamerican.bo2.impl.open.po.analysis.OneToManyProperty;
import gr.interamerican.bo2.impl.open.po.analysis.OneToOneProperty;
import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;
import gr.interamerican.bo2.impl.open.po.analysis.PoProperty;
import gr.interamerican.bo2.impl.open.po.analysis.SimpleProperty;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.collection.OneToManyPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.CompositeType;
import org.hibernate.type.OneToOneType;
import org.hibernate.type.Type;

/**
 * A {@link PoAnalyzer} that is aware of Hibernate API.
 */
public class HibernatePoAnalyzer implements PoAnalyzer {

	/**
	 * Hibernate Session that we expect it will contain information about the
	 * objects we need to analyze
	 */
	Session session;

	/**
	 * Cache for this
	 */
	Map<Class<?>, PoAnalysis> cache = new HashMap<Class<?>, PoAnalysis>();

	/**
	 * Public Constructor.
	 * 
	 * @param session
	 *            Hibernate Session that we expect it will contain information
	 *            about the objects we need to analyze
	 */
	public HibernatePoAnalyzer(Session session) {
		this.session = session;
	}

	@Override
	public PoAnalysis getAnalysis(Class<?> class1) {
		PoAnalysis analysis = cache.get(class1);
		if (analysis != null) {
			return analysis;
		}
		analysis = Factory.create(PoAnalysis.class);
		ClassMetadata meta = findClassMetadata(class1);
		List<PoProperty> properties = new ArrayList<PoProperty>();
		analysis.setKeyProperty(createPoProperty(meta.getIdentifierPropertyName(), meta.getIdentifierType()));
		for (int i = 0; i < meta.getPropertyNames().length; i++) {
			PoProperty property = createPoProperty(meta.getPropertyNames()[i], meta.getPropertyTypes()[i]);
			if (i == meta.getVersionProperty()) {
				analysis.setVersionProperty(property);
			} else {
				properties.add(property);
			}
		}
		analysis.setProperties(properties);
		cache.put(class1, analysis);
		return analysis;
	}

	/**
	 * Finds the correct {@link ClassMetadata}.<br>
	 * This is the one closer in hierarchy to clz from all the available ones.
	 * 
	 * @param clz
	 *            Class of the Entity
	 * @return Correct {@link ClassMetadata}
	 */
	ClassMetadata findClassMetadata(Class<?> clz) {
		ClassMetadata meta = null;
		for (ClassMetadata candidate : session.getSessionFactory().getAllClassMetadata().values()) {
			Class<?> mappedClass = candidate.getMappedClass(session.getEntityMode());
			if (mappedClass.isAssignableFrom(clz)) {
				meta = Utils.notNull(meta, candidate);
				Class<?> metaClz = meta.getMappedClass(session.getEntityMode());
				if (metaClz.isAssignableFrom(mappedClass)) {
					meta = candidate;
				}
			}
		}
		if (meta == null) {
			throw new RuntimeException("ClassMetadata not found"); //$NON-NLS-1$
		}
		return meta;
	}

	/**
	 * Returns a new {@link PoProperty} from the input arguments.
	 * 
	 * @param name
	 *            Name of Property
	 * @param type
	 *            Hibernate {@link Type}
	 * @return A New {@link PoProperty}
	 */
	PoProperty createPoProperty(String name, Type type) {
		PoProperty result = null;
		if (type.isComponentType()) {
			result = Factory.create(CompositeProperty.class);
			CompositeType compType = (CompositeType) type;
			List<PoProperty> compProperties = new ArrayList<PoProperty>();
			for (int i = 0; i < compType.getPropertyNames().length; i++) {
				compProperties.add(createPoProperty(compType.getPropertyNames()[i], compType.getSubtypes()[i]));
			}
			((CompositeProperty) result).setProperties(compProperties);

		}
		if (type instanceof OneToOneType) {
			result = Factory.create(OneToOneProperty.class);
		}
		if (type.isCollectionType()) {
			CollectionType cType = (CollectionType) type;
			CollectionMetadata colMetadata = session.getSessionFactory().getCollectionMetadata(cType.getRole());
			if (colMetadata instanceof OneToManyPersister) {
				result = Factory.create(OneToManyProperty.class);
				((OneToManyProperty) result).setAnalysis(getAnalysis(colMetadata.getElementType().getReturnedClass()));
			}
		}
		if (result == null) {
			result = Factory.create(SimpleProperty.class);
		}
		result.setName(name);
		result.setType(type.getReturnedClass());
		return result;
	}
}