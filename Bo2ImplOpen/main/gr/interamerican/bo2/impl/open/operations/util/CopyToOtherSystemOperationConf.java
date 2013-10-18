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
package gr.interamerican.bo2.impl.open.operations.util;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.annotations.KeyProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class that reads a configuration for migrating
 * a set of persistence objects between two systems or
 * performing a maintenance operation to specific POs of a
 * single system.
 * <br/>
 * Simple keys that do not extend {@link Key} are written as
 * comma-separated values, e.g.
 * <br/>
 * 1,2,12,33
 * <br/>
 * Keys that extend {@link Key} follow the convention below:
 * [1,a],[2,bc],[3,ad]
 * <br/>
 * Each bracket represents a Key instance. The comma-separated
 * values within the bracket represent the key instance property
 * values. The order of the key property values matches that
 * declared on the {@link KeyProperties} annotation on the class
 * of the Keys.
 * 
 * @param <K>
 *        Type of PO key.
 */
public class CopyToOtherSystemOperationConf<K extends Serializable & Comparable<? super K>> {

	/**
	 * Key for system of origin or system to perform maintenance operation on.
	 */
	static final String FROM = "FROM"; //$NON-NLS-1$
	/**
	 * Key for system of destination. This is optional.
	 */
	static final String TO = "TO"; //$NON-NLS-1$
	/**
	 * Key for class name of PO key.
	 */
	static final String KEY_CLASS = "KEY_CLASS"; //$NON-NLS-1$
	/**
	 * Key for keys to work on. This is optional.
	 */
	static final String FROM_KEYS = "FROM_KEYS"; //$NON-NLS-1$
	/**
	 * Key for keys to work on. This is optional.
	 */
	static final String TO_KEYS = "TO_KEYS"; //$NON-NLS-1$
	
	/**
	 * System of origin or system to perform maintenance operation on.
	 */
	String from;
	/**
	 * System of destination.
	 */
	String to;
	/**
	 * Class of key.
	 */
	Class<K> keyClass;
	/**
	 * Keys to work on. This is optional.
	 */
	List<K> fromKeys = new ArrayList<K>();
	/**
	 * Keys to work on. This is optional.
	 */
	List<K> toKeys = new ArrayList<K>();
	/**
	 * 
	 */
	Map<String, Class<?>> keyPropertiesTypes = new HashMap<String, Class<?>>();
	/**
	 * Properties to build this configuration with.
	 */
	Properties props;

	/**
	 * Construct.
	 * 
	 * @param props
	 *        Properties to build this configuration with.
	 *        
	 */
	@SuppressWarnings("unchecked")
	public CopyToOtherSystemOperationConf(Properties props) {
		this.props = props;
		from = props.getProperty(FROM);
		to = props.getProperty(TO);
		
		String keyClassName = props.getProperty(KEY_CLASS);
		if(!StringUtils.isNullOrBlank(keyClassName)) {
			try {
				keyClass = (Class<K>) Class.forName(keyClassName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		if(Key.class.isAssignableFrom(keyClass)) {
			TypeAnalysis analysis = TypeAnalysis.analyze(keyClass);
			for (BeanPropertyDefinition<?> def : analysis.getAllProperties()) {
				keyPropertiesTypes.put(def.getName(), def.getType());
			}
			resolveFromKeys();
			resolveToKeys();
		} else {
			resolveSimpleFromKeys();
			resolveSimpleToKeys();
		}
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the fromkeys
	 */
	public List<K> getFromKeys() {
		return fromKeys;
	}

	/**
	 * @return the tokeys
	 */
	public List<K> getToKeys() {
		return toKeys;
	}

	/**
	 * Gets a property from props.
	 * 
	 * @param key
	 * @return property value.
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}
	
	/**
	 * Resolves simple from keys values from the properties file.
	 */
	private void resolveSimpleFromKeys() {
		String keysText = props.getProperty(FROM_KEYS);
		resolveSimpleKeys(keysText, fromKeys);
	}
	
	/**
	 * Resolves simple to keys values from the properties file.
	 */
	private void resolveSimpleToKeys() {
		String keysText = props.getProperty(TO_KEYS);
		resolveSimpleKeys(keysText, toKeys);
	}
	
	/**
	 * Resolves simple to keys values from keys text.
	 * @param keysText 
	 * @param keys 
	 */
	private void resolveSimpleKeys(String keysText, List<K> keys) {
		if(StringUtils.isNullOrBlank(keysText)) {
			return;
		}
		String[] keyStrings = keysText.split(StringConstants.COMMA);
		for(String keyString : keyStrings) {
			try {
				K key = ParserResolver.getParser(keyClass).parse(keyString);
				keys.add(key);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Resolves the from {@link Key}s values from the property file.
	 */
	private void resolveFromKeys() {
		String[] keyStrings = keyStrings(FROM_KEYS);
		for (String keyString : keyStrings) {
			fromKeys.add(evaluateKeyString(keyString));
		}
	}
	
	/**
	 * Resolves the to {@link Key}s values from the property file.
	 */
	private void resolveToKeys() {
		String[] keyStrings = keyStrings(TO_KEYS);
		for (String keyString : keyStrings) {
			toKeys.add(evaluateKeyString(keyString));
		}
	}

	/**
	 * @param propertyName
	 * @return the key strings.
	 */
	private String[] keyStrings(String propertyName) {
		String keysText = props.getProperty(propertyName);
		String[] keyStrings;
		if(StringUtils.isNullOrBlank(keysText)) {
			return new String[]{};
		}
		if (keysText.contains(",[")) { // multiple keys //$NON-NLS-1$
			keyStrings = keysText.split(",\\["); //$NON-NLS-1$
			for (int i = 0; i < keyStrings.length; i++) {
				keyStrings[i] = keyStrings[i].replaceAll("\\[", StringConstants.EMPTY).replaceAll("\\]", StringConstants.EMPTY); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} else {
			keyStrings = new String[1];
			keyStrings[0] = keysText.replaceAll("\\[", StringConstants.EMPTY).replaceAll("\\]", StringConstants.EMPTY); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return keyStrings;
	}

	/**
	 * generates a {@link Key} from a string.
	 * 
	 * @param keyString
	 * @return a {@link Key}.
	 */
	private K evaluateKeyString(String keyString) {
		K key = Factory.create(keyClass);
		String[] keyProperties = Bo2AnnoUtils.getKeyProperties(keyClass);
		String[] keyTexts = keyString.split(StringConstants.COMMA);
		if (keyProperties.length != keyTexts.length) {
			throw new RuntimeException(keyString + " does not contain all properties of " + keyClass.getName()); //$NON-NLS-1$
		}
		for (int i = 0; i < keyProperties.length; i++) {
			Parser<?> parser = ParserResolver.getParser(keyPropertiesTypes.get(keyProperties[i]));
			Object keyPropertyValue;
			try {
				keyPropertyValue = parser.parse(keyTexts[i]);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			ReflectionUtils.setProperty(keyProperties[i], keyPropertyValue, key);
		}
		return key;
	}
	
	@Override
	public String toString() {
		String sep = StringConstants.COLON + StringConstants.SPACE;
		return StringUtils.concat(
			FROM + sep + from + StringConstants.NEWLINE,
			TO + sep + to + StringConstants.NEWLINE,
			KEY_CLASS + sep + keyClass + StringConstants.NEWLINE,
			FROM_KEYS + sep + fromKeys + StringConstants.NEWLINE,
			TO_KEYS + sep + toKeys);
	}

}
