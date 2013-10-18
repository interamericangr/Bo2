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
package gr.interamerican.bo2.utils.sql.types;

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class TypeUtils {
	
	/**
	 * Maps {@link Class} with {@link Type}.
	 */
	private static TypeBasedSelection<Type<?>> types = 
		new TypeBasedSelection<Type<?>>();
	
	/**
	 * Maps SqlType with {@link Type}.
	 */
	private static Map<Integer, Type<?>> sqlTypes = 
		new HashMap<Integer, Type<?>>();
	
	static {
		initTypes();
		initSqlTypes();
	}
	
	/**
	 * Initializes the <code>types</code> selection.
	 */
	private static void initTypes() {
		types.registerSelection(BigDecimal.class, BigDecimalType.INSTANCE);
		types.registerSelection(byte[].class, BytesType.INSTANCE);
		types.registerSelection(Byte.class, ByteType.INSTANCE);
		types.registerSelection(Calendar.class, CalendarType.INSTANCE);
		types.registerSelection(Timestamp.class, TimestampType.INSTANCE);//Timestamp before Date.
		types.registerSelection(Time.class, TimeType.INSTANCE);//Time before Date.
		types.registerSelection(Date.class, DateType.INSTANCE);
		types.registerSelection(Double.class, DoubleType.INSTANCE);		
		types.registerSelection(Float.class, FloatType.INSTANCE);
		types.registerSelection(Integer.class, IntegerType.INSTANCE);
		types.registerSelection(Long.class, LongType.INSTANCE);		
		types.registerSelection(Short.class, ShortType.INSTANCE);
		types.registerSelection(String.class, StringType.INSTANCE);
		types.registerSelection(Boolean.class, BooleanType.INSTANCE);
		/*
		 * Object always last.
		 */
		types.registerSelection(Object.class, ObjectType.INSTANCE);
	}
	
	/**
	 * Initializes the <code>sqlTypes</code> map.
	 */
	private static void initSqlTypes() {
		sqlTypes.put(Types.ARRAY, ObjectType.INSTANCE);
		sqlTypes.put(Types.BIGINT, LongType.INSTANCE);
		sqlTypes.put(Types.BINARY, BytesType.INSTANCE);
		sqlTypes.put(Types.BIT, BooleanType.INSTANCE);
		sqlTypes.put(Types.BLOB, null);
		sqlTypes.put(Types.BOOLEAN, BooleanType.INSTANCE);
		sqlTypes.put(Types.CHAR, StringType.INSTANCE);
		sqlTypes.put(Types.CLOB, null);
		sqlTypes.put(Types.DATALINK, null);
		sqlTypes.put(Types.DATE, DateType.INSTANCE);
		sqlTypes.put(Types.DECIMAL, BigDecimalType.INSTANCE);
		sqlTypes.put(Types.DISTINCT, null);
		sqlTypes.put(Types.DOUBLE, DoubleType.INSTANCE);
		sqlTypes.put(Types.FLOAT, FloatType.INSTANCE);
		sqlTypes.put(Types.INTEGER, IntegerType.INSTANCE);
		sqlTypes.put(Types.JAVA_OBJECT, ObjectType.INSTANCE);
		sqlTypes.put(Types.LONGVARBINARY, BytesType.INSTANCE);
		sqlTypes.put(Types.LONGVARCHAR, StringType.INSTANCE);
		sqlTypes.put(Types.NULL, null);
		sqlTypes.put(Types.NUMERIC, null);
		sqlTypes.put(Types.OTHER, ObjectType.INSTANCE);
		sqlTypes.put(Types.REAL, null);
		sqlTypes.put(Types.REF, null);
		sqlTypes.put(Types.SMALLINT, ShortType.INSTANCE);
		sqlTypes.put(Types.STRUCT, null);
		sqlTypes.put(Types.TIME, TimeType.INSTANCE);
		sqlTypes.put(Types.TIMESTAMP, TimestampType.INSTANCE);
		sqlTypes.put(Types.TINYINT, ShortType.INSTANCE);
		sqlTypes.put(Types.VARBINARY, BytesType.INSTANCE);
		sqlTypes.put(Types.VARCHAR, StringType.INSTANCE);		
	}
	
	/**
	 * Gets the type that matches to the specified class.
	 * 
	 * @param clazz
	 *        
	 * @return Returns the type that matches to the specified class.
	 */
	public static Type<?> getTypeForClass(Class<?> clazz) {
		Type<?> type = types.selectionForType(clazz);		
		return type;
	}
	
	/**
	 * Gets the type that matches to the class of specified object.
	 * 
	 * @param object
	 *        
	 * @return Returns the type that matches to the specified object.
	 */
	public static Type<?> getType(Object object) {
		return types.select(object);
	}
	
	/**
	 * Gets the type that matches to the specified java.jql.Types constant.
	 * 
	 * @param sqlType
	 *        
	 * @return Returns the type that matches to the specified SQL Types value..
	 */
	public static Type<?> getTypeOfSqlType(int sqlType) {
		return sqlTypes.get(sqlType);
	}
	
	/**
	 * Gets the java type that matches to the specified java.jql.Types constant.
	 * 
	 * @param sqlType
	 *        
	 * @return Returns the Class<?> that the JDBC specification specifies that
	 *         will be used to store the contents of a column of the specified
	 *         sqlType.
	 */
	public static Class<?> getJavaTypeOfSqlType(int sqlType) {
		return sqlTypes.get(sqlType).getJavaType();
	}
	
	/**
	 * Private constructor of utility class.
	 */
	private TypeUtils() {
		/* empty */
	}

}
