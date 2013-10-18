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
package gr.interamerican.bo2.impl.open.hibernate.types;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.convert.TwoWayConverter;
import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;
import gr.interamerican.bo2.utils.sql.types.TypeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * General purpose user type that is used when there is an entity <-> database table 
 * mismatch. This may happen when it is required to map an entity with a legacy table.
 * The configuration parameters are as follows:
 * <li> twoWayConverterClass: Class of the {@link TwoWayConverter}. It MUST have a 
 *      default constructor.
 * <li> propertyClass: java type of the entity property.
 * <li> sqlType: sql type of the column. See {@link Types}.
 * <br/>
 * Example configuration for this user type.
 * 
 * <typedef class="gr.interamerican.bo2.impl.open.hibernate.types.TwoWayConverterUserType" name="DamageCause">
 * 	<param name="twoWayConverterClass">gr.interamerican.converters.StringToCountryConverter</param>
 *	<param name="propertyClass">gr.interamerican.bo.def.pc.common.base.SystemListEntry</param>
 *	<param name="columnSqlType">1</param>
 * </typedef>
 * 
 * When writing a {@link TwoWayConverter} implementation for this user type, the developer
 * has to be aware of the java type that is created by the driver when retrieving a column
 * of the specified SQL type from the resultset. Use {@link TypeUtils#getJavaTypeOfSqlType(int)}
 * if you are not sure what the java type will be.
 * 
 * @see TypeUtils
 * @see TwoWayConverter
 * 
 * @param <L>
 *        Type to be inserted in / read from the database column. 
 * @param <R> 
 *        Type of the entity property.
 */
public class TwoWayConverterUserType<L,R> extends AbstractUserType implements EnhancedUserType, ParameterizedType {
	
	/**
	 * Parameter name for {@link TwoWayConverter} class name.
	 */
	private static final String TWO_WAY_CONVERTER_FQCN_PARAMETER = "twoWayConverterClass"; //$NON-NLS-1$
	
	/**
	 * Parameter name for java class of entity property.
	 */
	private static final String ENTITY_PROPERTY_FQCN_PARAMETER = "propertyClass"; //$NON-NLS-1$
	
	/**
	 * Parameter name for SQL type of column. 
	 */
	private static final String COLUMN_SQL_TYPE_PARAMETER = "columnSqlType"; //$NON-NLS-1$
	
	/**
	 * Cache for known classes. The key is the converter class fqcn.
	 */
	private static final Map<String, Class<?>> knownClasses = new HashMap<String, Class<?>>();
	
	/**
	 * Cached converter instances. The key is the converter class fqcn.
	 */
	private static final Map<String, TwoWayConverter<?, ?>> converters = new HashMap<String, TwoWayConverter<?,?>>();
	
	/**
	 * Class of the property.
	 */
	private Class<R> propertyClass;
	
	/**
	 * {@link TwoWayConverter} for L<-->R conversions.
	 */
	private TwoWayConverter<L, R> twoWayConverter;
	
	/**
	 * SQL type of column.
	 * @see Types
	 */
	int sqlType;
	
	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		String twoWayConverterFqcn = parameters.getProperty(TWO_WAY_CONVERTER_FQCN_PARAMETER);
		String propertyFqcn = parameters.getProperty(ENTITY_PROPERTY_FQCN_PARAMETER);
		
		sqlType = NumberUtils.string2Int(parameters.getProperty(COLUMN_SQL_TYPE_PARAMETER));
		
		propertyClass = (Class<R>) knownClasses.get(propertyFqcn);
		if(propertyClass == null) {
			propertyClass = (Class<R>) loadClass(propertyFqcn);
		}
		
		twoWayConverter = (TwoWayConverter<L, R>) converters.get(twoWayConverterFqcn);
		if(twoWayConverter == null){
			Class<?> twoWayconverterClass = knownClasses.get(twoWayConverterFqcn);
			if(twoWayconverterClass == null) {
				twoWayconverterClass = loadClass(twoWayConverterFqcn);
			}
			twoWayConverter = (TwoWayConverter<L, R>) ReflectionUtils.newInstance(twoWayconverterClass);
			converters.put(twoWayConverterFqcn, twoWayConverter);
		}
		
	}
	
	@SuppressWarnings({ "unchecked" })
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		Object column = TypeUtils.getTypeOfSqlType(sqlType).get(rs, names[0]);
		try {
			return twoWayConverter.convertL((L) column);
		} catch (ConversionException e) {
			throw new HibernateException(e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		R property = propertyClass.cast(value);
		try {
			L column = twoWayConverter.convertR(property);
			st.setObject(index, column, sqlType);
		} catch (ConversionException e) {
			throw new HibernateException(e);
		}
	}
	
	public int[] sqlTypes() {
		return new int[]{sqlType};
	}

	public Class<?> returnedClass() {
		return propertyClass;
	}

	public String objectToSQLString(Object value) {
		return StringUtils.toString(value);
	}

	public String toXMLString(Object value) {
		return StringUtils.toString(value);
	}

	public Object fromXMLString(String xmlValue) {
		return null;
	}
	
	/**
	 * Loads a class and updates the local cache.
	 * 
	 * @param className
	 * @return loaded class.
	 */
	private Class<?> loadClass(String className) {
		try{
			Class<?> clazz = Class.forName(className);
			knownClasses.put(className, clazz);
			return clazz;
		} catch(ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}

}
