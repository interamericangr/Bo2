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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Locates SQL statements marked with an annotation.
 * 
 * A {@link StatementLocator} finds fields and methods of a class
 * that are annotated with the {@link Sql} annotation. The values 
 * returned by these fields and methods are returned by the 
 * StatementLocator.
 */
public class StatementLocator {
	
	/**
	 * SQL fields.
	 */
	private static Map<Class<?>, Field> sqlFields = new HashMap<Class<?>, Field>();
	
	/**
	 * Gets the one and only one field of the psecified class 
	 * that has the {@link Sql} annotation.
	 * 
	 * @param clazz
	 * 
	 * @return Returns the annotated field 
	 */
	@SuppressWarnings("nls")
	static Field getSqlField(Class<?> clazz) {
		Field field = sqlFields.get(clazz);
		if (field!=null) {
			return field;
		}
		
		TypeAnalysis analysis = TypeAnalysis.analyze(clazz);
		Set<Field> annotateds = analysis.getAnnotated(Sql.class);
		if (CollectionUtils.isNullOrEmpty(annotateds)) {
			String msg = "Couldn't find field with the @Sql annotation";
			throw new RuntimeException(msg);
		}
		if (annotateds.size()>1) {
			String msg = "More than one fields with the @Sql annotation";
			throw new RuntimeException(msg);			
		}

		field = annotateds.iterator().next();
		sqlFields.put(clazz, field);
		ReflectionUtils.setAccessible(field);
		return field;
	}
	
	
	
	/**
	 * SQL field.
	 */
	Field sqlField;
	
	/**
	 * Worker object.
	 */
	Object sqlObject;
	
	/**
	 * Creates a new StatementLocator object. 
	 * 
	 * @param sqlObject 
	 *        Object with the SQL statement.
	 */
	public StatementLocator(Object sqlObject) {
		super();
		this.sqlField = getSqlField(sqlObject.getClass());
		this.sqlObject = sqlObject;
	}

	
	/**
	 * SQL statement.
	 * 
	 * @return Returns the SQL statement.
	 */
	public String sql() {	
		return (String) ReflectionUtils.get(sqlField, sqlObject);
	}

}
