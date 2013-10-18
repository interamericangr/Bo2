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

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.hibernate.utils.reflect.beans.VariableDefinitionForPersistentCollections;
import gr.interamerican.bo2.impl.open.hibernate.utils.reflect.beans.VariableDefinitionForPersistentObjects;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.VariableDefinitionFactory;
import gr.interamerican.bo2.utils.reflect.analyze.AbstractObjectStructureAnalyzer;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Analyzes all fields of an object structure that are {@link ModificationRecord}s.
 * {@link HibernateProxy} and {@link AbstractPersistentCollection} instances are not
 * explored further.
 * 
 * @deprecated Use {@link HibernateAwarePoAnalyzer} instead. This one is bugged.
 * 
 */
@Deprecated
public class ModificationRecordFieldsAnalyzer extends AbstractObjectStructureAnalyzer {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ModificationRecordFieldsAnalyzer.class);

	/**
	 * Creates a new ModificationRecordFieldsAnalyzer object.
	 */
	public ModificationRecordFieldsAnalyzer() {
		super();
		ignoreDuplicates = false;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<VariableDefinition<?>> whichFieldsToInclude(Object object) {
		List<VariableDefinition<?>> list = new ArrayList<VariableDefinition<?>>();
		if (object==null) {
			return list;
		}
		List<Field> fieldsToInclude = getFieldsToInclude(object.getClass());

		/*
		 * Create VariableDefinitions for all fields produced before, taking special care of
		 * field values that are either HibernateProxy or PersistentCollection instances.
		 * [*] Uninitialized HibernateProxy and PersistentCollections do not have a VariableDefinition created.
		 * [*] Initialized HibernateProxy objects are unwrapped if wrapped.
		 * [*] Initialized PersistentCollections have a special VariableDefinition sub-type created for them.
		 */
		for (Field field : fieldsToInclude) {
			field.setAccessible(true);
			Object value = ReflectionUtils.get(field, object);
			VariableDefinition vd = null;

			if(value instanceof HibernateProxy) {
				HibernateProxy proxy = (HibernateProxy) value;
				if(proxy.getHibernateLazyInitializer().isUninitialized()) {
					logger.debug("Ignoring uninitialized proxy of: " + proxy.getHibernateLazyInitializer().getEntityName()); //$NON-NLS-1$
					continue;
				}
				if(!proxy.getHibernateLazyInitializer().isUnwrap()) {
					logger.debug("Unwrapping initialized proxy of: " + proxy.getHibernateLazyInitializer().getEntityName()); //$NON-NLS-1$
					if(PersistentObject.class.isAssignableFrom(field.getType())) {
						vd = new VariableDefinitionForPersistentObjects(field.getName(), field.getType());
						vd.setValue(proxy.getHibernateLazyInitializer().getImplementation());
					} else {
						vd = VariableDefinitionFactory.create(field);
						vd.setValue(value);
					}
				}
			} else if(value instanceof PersistentCollection) {
				PersistentCollection coll = (PersistentCollection) value;
				if(!coll.wasInitialized()) {
					logger.debug("Ignoring uninitialized PersistentCollection of: " + coll.getRole()); //$NON-NLS-1$
					continue;
				}
				vd = new VariableDefinitionForPersistentCollections(field.getName(), field.getType());
				vd.setValue(value);
			} else if(value instanceof PersistentObject) {
				vd = new VariableDefinitionForPersistentObjects(field.getName(), field.getType());
				vd.setValue(value);
			}
			else {
				vd = VariableDefinitionFactory.create(field);
				vd.setValue(value);
			}
			list.add(vd);
		}
		return list;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected VariableDefinition<?> createVariableDefinition(Object object, String fieldName, Class<?> fieldType) {
		if(object instanceof PersistentObject) {
			VariableDefinition result = new VariableDefinitionForPersistentObjects(StringConstants.EMPTY, object.getClass());
			result.setValue(object);
			return result;
		}
		if(object instanceof PersistentCollection) {
			VariableDefinition result = new VariableDefinitionForPersistentCollections(StringConstants.EMPTY, object.getClass());
			result.setValue(object);
			return result;
		}
		return super.createVariableDefinition(object, "unknown", object.getClass()); //$NON-NLS-1$
	}

	/**
	 * Include all fields up-to ModificationRecord and all Collection/Array fields
	 * for further inspection. Ignore static fields.
	 * 
	 * @param clazz
	 * @return fields to include.
	 */
	private List<Field> getFieldsToInclude(Class<?> clazz) {
		List<Field> fieldsToInclude = new ArrayList<Field>();
		List<Field> allFields = ReflectionUtils.allFields(clazz, ModificationRecord.class);
		for (Field field : allFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			boolean isCollectionOrArray = ReflectionUtils.isArray(field.getType())
					|| ReflectionUtils.isCollection(field.getType());
			if(ModificationRecord.class.isAssignableFrom(field.getType()) || isCollectionOrArray) {
				fieldsToInclude.add(field);
			}
		}
		return fieldsToInclude;
	}

}
