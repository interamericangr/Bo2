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
package gr.interamerican.bo2.impl.open.creation;

import static gr.interamerican.bo2.utils.StringConstants.NEWLINE;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.creation.code.templatebean.FieldInitializationCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.creators.ImplementorForAbstractClasses;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.update.AddingCodeToDefaultConstructorClassUpdater;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;
import gr.interamerican.bo2.impl.open.creation.templatebean.ChildPropertyWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.impl.open.creation.templatebean.ChildPropertyWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.impl.open.creation.templatebean.PoCodeTemplates;
import gr.interamerican.bo2.impl.open.creation.templatebean.PropertyWithDelegateKeyCodeTemplates;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ClassCreator for abstract classes to support AbstractBasePo.
 */
public class Impl4Abstract extends ImplementorForAbstractClasses {
	/**
	 * Templates.
	 */
	private ChildPropertyWithDirectAccessCodeTemplates directChildProperty =
		new ChildPropertyWithDirectAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	private ChildPropertyWithReflectiveAccessCodeTemplates reflectiveChildProperty =
		new ChildPropertyWithReflectiveAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	private PropertyWithDelegateKeyCodeTemplates delegateKeyProperty =
		new PropertyWithDelegateKeyCodeTemplates();
	
	/**
	 * Templates.
	 */
	private FieldInitializationCodeTemplates initField =
		new FieldInitializationCodeTemplates();
	
	/**
	 * Po templates.
	 */
	private PoCodeTemplates poTemplates = new PoCodeTemplates();
		
	/**
	 * Key type.
	 */
	private Class<?> keyType = null;
	
	
	@Override
	protected PropertyCodeTemplates getPropertyCodeTemplates(Field field, boolean delegate) {
		boolean isDelegateKey = delegate && field.getName().equals("key") && keyType!=null; //$NON-NLS-1$
		if (isDelegateKey) {
				return delegateKeyProperty;						
		} 
		boolean isChild = (!delegate && field.isAnnotationPresent(Child.class));
		if (isChild) {
			boolean isPrivate = Modifier.isPrivate(field.getModifiers());
			return isPrivate ? reflectiveChildProperty : directChildProperty;			
		}
		return super.getPropertyCodeTemplates(field, delegate);
	}
	
	/**
	 * Finds the key type.
	 */
	void findKeyType() {
		Class<?> clazz = analysis.getClazz(); 
		if (isPo()) {
			Class<PersistentObject<?>> poClass = Utils.cast(clazz);
			keyType = PoUtils.getKeyType(poClass);
		} else {
			keyType = null;
		}
	}
	
	/**
	 * Analyzes {@link DelegateKeyProperties} annotation of the
	 * fields of the class being processed.
	 * 
	 * @throws ClassCreationException 
	 */
	void analyzeDelegateKeyProperties() throws ClassCreationException {
		Class<?> clazz = analysis.getClazz(); 
		DelegateKeyProperties anno = 
			clazz.getAnnotation(DelegateKeyProperties.class);
		if (anno!=null) {
			if (!PersistentObject.class.isAssignableFrom(clazz)) {
				throw invalidDelegateKeyProperties();
			}
			Field key = analysis.getFirstFieldByName("key"); //$NON-NLS-1$
			if (key==null) {
				throw invalidDelegateKeyProperties();
			}
			String[] value =  TokenUtils.tokenize(anno.value());
			Set<String> names = findProperties(keyType, value);
			specifyDelegatedProperties(key, names);
		}
	}
	
	/**
	 * Analyzes {@link DelegateKeyProperties} annotation of the
	 * fields of the class being processed.
	 * 
	 * @throws ClassCreationException 
	 */
	@SuppressWarnings("nls")
	void analyzeTypeSelectableProperties() throws ClassCreationException {
		Class<?> clazz = analysis.getClazz();
		if (TypedSelectable.class.isAssignableFrom(clazz)) {
			TypedSelectableProperties anno = 
				clazz.getAnnotation(TypedSelectableProperties.class);
			if (anno!=null) {
				markTsProperty("typeId", anno.type());
				markTsProperty("code", anno.code());
				markTsProperty("subTypeId", anno.subtype());
				markTsProperty("name", anno.name());
			}
		}
	}
	
	/**
	 * Marks typed selectable properties. 
	 * 
	 * @param property
	 * @param replacement
	 * @throws ClassCreationException
	 */
	void markTsProperty(String property, String replacement) throws ClassCreationException {
		boolean isEmpty = 
			StringUtils.isNullOrBlank(replacement) || "X".equalsIgnoreCase(replacement); //$NON-NLS-1$
		if (!isEmpty) {
			BeanPropertyDefinition<?> def = analysis.getFirstPropertyByName(property);
			if (StringConstants.NULL.equalsIgnoreCase(replacement)) {
				markPropertyAsMock(def);
			} else {
				BeanPropertyDefinition<?> supporting = analysis.getFirstPropertyByName(replacement);
				if (supporting==null) {
					throw invalidTypedSelectableProperties(replacement);
				}
				markPropertyToBeSupportedBy(def, supporting);
			}
		}
	}
	
	
	
	
	@Override
	protected void initialize(java.lang.Class<?> clazz) throws ClassCreationException {
		super.initialize(clazz);
		findKeyType();
		analyzeDelegateKeyProperties();
		analyzeTypeSelectableProperties();
	}
	
	/**
	 * Creates a new {@link ClassCreationException}.
	 * 
	 * @return Returns an exception with the appropriate message.
	 */
	ClassCreationException invalidDelegateKeyProperties() {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Class ", analysis.getClazz().getName(),
			" has the annotation DelegateKeyProperties, but is not a",
			" PersistentObject type or does not have a key field");
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a new {@link ClassCreationException}.
	 * 
	 * @param invalidName 
	 *        Name of invalid property
	 * 
	 * @return Returns an exception with the appropriate message.
	 */
	ClassCreationException invalidTypedSelectableProperties(String invalidName) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Class ", analysis.getClazz().getName(),
			" references the invalid property ", invalidName, 
			" in the TypeSelectableProperties annotation");
		return new ClassCreationException(msg);
	}
	
	@Override
	protected Class<?> findFieldType(Field field) {
		if (keyType!=null && field.getName().equals("key")) { //$NON-NLS-1$
			return keyType;
		}
		return super.findFieldType(field);
	}
	
	
	
	
	/**
	 * Shows if the type is po.
	 * 
	 * @return Return true if it is a po.
	 */
	boolean isPo() {
		return (PersistentObject.class.isAssignableFrom(analysis.getClazz()));
	}
	
	/**
	 * Support for po.
	 */
	@SuppressWarnings("nls")
	void supportPo() {
		String createKey = poTemplates.getFactoryCreate(keyType);
		StringBuilder sb = new StringBuilder();
		Map<String, String> initKeyVars = 
			Variables.variablesForInitialization(keyType, "key", createKey); 
		String initKey = initField.getDirect(initKeyVars);
		sb.append(initKey);
		sb.append(NEWLINE);
		
		Set<Field> children = analysis.getAnnotated(Child.class);		
		String value = "new " + HashSet.class.getName() + "()";
		
		if (children!=null) {
			for (Field field : children) {			
				if (Set.class.isAssignableFrom(field.getType())) {
					Map<String, String> vars = 
						Variables.variablesForInitialization(field.getType(), field.getName(), value);
					String initCode;
					String fixCode;
					if (Modifier.isPrivate(field.getModifiers())) {
						initCode = initField.getReflective(vars);
						fixCode = poTemplates.getFixChildReflective(field.getName());
					} else {
						initCode = initField.getDirect(vars);
						fixCode = poTemplates.getFixChildDirect(field.getName());
					}
					sb.append(initCode);
					sb.append(NEWLINE);
					sb.append(fixCode);
					sb.append(NEWLINE);				
				}
			}
		}
		
		String constructorCode = sb.toString();
		AddingCodeToDefaultConstructorClassUpdater updater = 
			new AddingCodeToDefaultConstructorClassUpdater(constructorCode);
		addUpdater(updater);
	}
	
	
	@Override
	protected void supportType() throws ClassCreationException {
		super.supportType();
		if (isPo()) {
			supportPo();
		}
	}
	
	@SuppressWarnings("nls")
	@Override
	protected boolean supportComparable() throws ClassCreationException {
		boolean ok = super.supportComparable();
		if (!ok) {
			if (TypedSelectable.class.isAssignableFrom(analysis.getClazz())) {
				BeanPropertyDefinition<?>[] defs = {
					analysis.getFirstPropertyByName("typeId"),
					analysis.getFirstPropertyByName("code")
				};
				implementCompareTo(TypedSelectable.class, defs);
				return true;
			} 
			if (Codified.class.isAssignableFrom(analysis.getClazz())) {
				BeanPropertyDefinition<?>[] defs = {
					analysis.getFirstPropertyByName("code")
				};
				implementCompareTo(Codified.class, defs);
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public synchronized Class<?> create(Class<?> type) throws ClassCreationException {
		Class<?> newType = super.create(type);
		Bo2AnnoUtils.copyManagerName(type, newType);
		return newType;
	}
	
	
	
	
}
