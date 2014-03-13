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
package gr.interamerican.bo2.utils.meta.descriptors;


import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.MultipleValidatorsValidator;
import gr.interamerican.bo2.utils.meta.validators.NotNullValidator;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Basic abstract implementation of {@link BoPropertyDescriptor}.
 * 
 * @param <T> 
 *        Type of property. 
 */
public abstract class AbstractBoPropertyDescriptor<T> 
implements BoPropertyDescriptor<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Validator}s associated with this descriptor instance.
	 */
	protected Map<Class<?>, Validator<T>> validators = new HashMap<Class<?>, Validator<T>>();
	
	/**
	 * index.
	 */
	Integer index;
	/**
	 * package name.
	 */
	String packageName;
	/**
	 * property name.
	 */
	String name;
	/**
	 * class name.
	 */
	String className;
	/**
	 * indication if this is a read only property.
	 */	
	boolean readOnly=false;
	/**
	 * indication if this property can take a null value.
	 */
	boolean nullAllowed=true;	
	/**
	 * indicator if this property has a default value.
	 */
	boolean hasDefault=false;
	/**
	 * default value.
	 */
	T defaultValue;	
	/**
	 * parser
	 */
	Parser<T> parser;
	/**
	 * label.
	 */
	String label;
	/**
	 * length
	 */
	int maxLength;
	/**
	 * affected.
	 */
	private String affected;

	/**
	 * Creates a new AbstractBoPropertyDescriptor object. 
	 *
	 * @param parser
	 */
	public AbstractBoPropertyDescriptor(Parser<T> parser) {
		super();
		this.parser = parser;
		if(!nullAllowed) {
			validators.put(NotNullValidator.class, NotNullValidator.<T>getInstance());
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getClassName() {
		return className;
	}
	
	@Override
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	@Override
	public boolean isNullAllowed() {
		return nullAllowed;
	}
	
	@Override
	public void setNullAllowed(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
		if (!nullAllowed) {
			validators.put(NotNullValidator.class, NotNullValidator.<T>getInstance());
		} else {
			validators.remove(NotNullValidator.class);
		}
	}
	
	@Override
	public boolean isHasDefault() {
		return hasDefault;
	}
	
	@Override
	public void setHasDefault(boolean hasDefault) {
		this.hasDefault = hasDefault;
	}
	
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}
	
	@Override
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public void validate(T value) throws ValidationException {
		getValidator().validate(value);
	}
	
	@Override
	public String getFullyQualifiedName() {		
		return StringUtils.concat(getFullyQualifiedClassName(), StringConstants.DOT, getName());
	}
	
	@Override
	public String getFullyQualifiedClassName() {
		return StringUtils.concat(getPackageName(), StringConstants.DOT, getClassName());
	}
	
	@Override
	public T parseAndValidate(String value) throws ParseException, ValidationException {
		T t = parse(value);
		validate(t);
		return t;
	}
	
	@Override
	public String getPackageName() {
		return packageName;
	}
	
	@Override
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	/**
	 * Checks if a value can and must be replaced by the default value.
	 * 
	 * @param value Value being checked.
	 * 
	 * @return Returns a valid value, either the value specified or the
	 *         default.
	 */	
	protected T checkDefault(T value) {
		if ((value==null) && (!isNullAllowed()) && (isHasDefault())) {
			return defaultValue;
		} 
		return value;
	}

	@Override
	public T parse(String value) throws ParseException {		
		return getParser().parse(value);
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		String nm = this.getFullyQualifiedName();
		if (nm==null) {
			return false;
		}
		if (obj instanceof BoPropertyDescriptor) {
			BoPropertyDescriptor<?> that = (BoPropertyDescriptor<?>) obj;
			return nm.equals(that.getFullyQualifiedName());
		}
		return false;
	}	
	
	@Override
	public int hashCode() {
		String nm = getFullyQualifiedClassName();
		if (nm==null) {
			return super.hashCode();
		}
		return nm.hashCode();
	}
	
	@Override
	public String format(T value) {
		return getFormatter().format(value);
	}
	
	@Override
	public Integer getIndex() {
		return index;
	}

	@Override
	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String getLabel() {
		return(label==null ? getName() : label);
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @return Returns the appropriate formatter.
	 */
	protected abstract Formatter<T> getFormatter();
	
	/**
	 * Convenience method that allows sub-classes to get relevant
	 * {@link Validator}s that may be already registered.
	 * 
	 * @param <V>
	 *        Type of Validator.
	 * @param type
	 *        Class of Validator.
	 *        
	 * @return Returns the Validator instance, if one is registered.
	 */
	@SuppressWarnings("unchecked")
	protected <V extends Validator<T>> V getRegisteredValidatorWithType(Class<V> type) {
		return (V) validators.get(type);
	}

	@Override
	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public void setMaxLength(int formatLength) {
		this.maxLength = formatLength;
	}
	
	@Override
	public T valueOf(Number value) {	
		return null;
	}
	
	public String getAffected() {
		return affected;
	}

	public void setAffected(String affected) {
		this.affected = affected;
	}
	
	public Parser<T> getParser() {
		return parser;
	}
	
	public Validator<T> getValidator() {
		return new MultipleValidatorsValidator<T>(new HashSet<Validator<T>>(validators.values()), getLabel());
	}

}
