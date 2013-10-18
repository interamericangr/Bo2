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
package gr.interamerican.bo2.creation.creators;

import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.generateMethodDeclarationParameters;
import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.generateMethodInvocationParameters;
import gr.interamerican.bo2.creation.ClassCreator;
import gr.interamerican.bo2.creation.annotations.ComparableThrough;
import gr.interamerican.bo2.creation.annotations.Delegate2Map;
import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.creation.annotations.DelegateToOtherProperty;
import gr.interamerican.bo2.creation.annotations.MockMethods;
import gr.interamerican.bo2.creation.annotations.MockProperties;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.creation.code.templatebean.Delegate2MapWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Delegate2MapWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.DelegateMethodWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.DelegateMethodWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.DelegatePropertyWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.DelegatePropertyWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.DelegateToOtherPropertyCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.EmptyMethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.MethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.MethodsImplCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyMockCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.creation.util.MethodsUtilities;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.adapters.GetProperty;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link ClassCreator} that creates a concrete subclass of an abstract 
 * class. <br/>
 * 
 * This composer can create a concrete class that extends a
 * specified abstract class. This class composer supports
 * the following annotations:
 * <li> {@link Property} </li>
 * <li> {@link DelegateProperties} </li>
 * <li> {@link Delegate2Map} </li>
 * <li> DelegateMethods </li>
 * The new class will not have any new field, it will only
 * have the methods necessary to implement the interfaces
 * declared by the abstract class. 
 * 
 */
public class ImplementorForAbstractClasses 
extends AbstractClassCreator {
	/**
	 * property.
	 */
	private static final String PROPERTY = "property"; //$NON-NLS-1$
	/**
	 * method.
	 */
	@SuppressWarnings("unused")
	private static final String METHOD = "method"; //$NON-NLS-1$
	
	
	/**
	 * Templates.
	 */
	protected PropertyWithDirectAccessCodeTemplates directProperty =
		new PropertyWithDirectAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected PropertyWithReflectiveAccessCodeTemplates reflectiveProperty =
		new PropertyWithReflectiveAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected DelegatePropertyWithDirectAccessCodeTemplates directDelegateProperty =
		new DelegatePropertyWithDirectAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected DelegatePropertyWithReflectiveAccessCodeTemplates reflectiveDelegateProperty =
		new DelegatePropertyWithReflectiveAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected Delegate2MapWithDirectAccessCodeTemplates directDelegate2Map =
		new Delegate2MapWithDirectAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected Delegate2MapWithReflectiveAccessCodeTemplates reflectiveDelegate2Map =
		new Delegate2MapWithReflectiveAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected PropertyMockCodeTemplates mockProperty = new PropertyMockCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected DelegateToOtherPropertyCodeTemplates toOther = 
		new DelegateToOtherPropertyCodeTemplates();
	
	
	/**
	 * Templates.
	 */
	protected DelegateMethodWithDirectAccessCodeTemplates directDelegateMethod =
		new DelegateMethodWithDirectAccessCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected DelegateMethodWithReflectiveAccessCodeTemplates reflectiveDelegateMethod =
		new DelegateMethodWithReflectiveAccessCodeTemplates();	
	
	/**
	 * Templates.
	 */
	protected MethodsImplCodeTemplates methodsTemplates = new MethodsImplCodeTemplates();
	
	/**
	 * Templates.
	 */
	protected EmptyMethodCodeTemplates emptyMethodTemplates = new EmptyMethodCodeTemplates();
	
	/**
	 * Maps names of properties to delegate to the fields that will
	 * support them.
	 */
	Map<String, Field> delegateProperties = new HashMap<String, Field>();
	
	/**
	 * Maps names of properties to delegate to the fields that will
	 * support them.
	 */
	Map<String, Field> delegate2Map = new HashMap<String, Field>();
	
	/**
	 * Maps names of methods to fields that have been annotated with the
	 * {@link DelegateMethods} annotation.
	 */
	Map<String, List<Field>> delegateMethods = new HashMap<String, List<Field>>();
	
	/**
	 * Maps each property being supported by another to the property
	 * that is being used for its support.
	 */
	Map<BeanPropertyDefinition<?>, BeanPropertyDefinition<?>> propertyReplacements = 
		new HashMap<BeanPropertyDefinition<?>, BeanPropertyDefinition<?>>();
	
	/**
	 * Set with the properties being marked as mocks.
	 */
	Set<BeanPropertyDefinition<?>> mockProperties = new HashSet<BeanPropertyDefinition<?>>();
	
	/**
	 * Set with the properties being marked as mocks.
	 */
	Set<String> mockMethods = new HashSet<String>();
	
	/**
	 * CompareTo method.
	 */
	private Method compareTo = null;
	


	
	/**
	 * Analyzes {@link DelegateProperties} annotations of the
	 * fields of the class being processed.
	 */
	void analyzeDelegateProperties() {
		delegateProperties.clear();
		Set<Field> dp = analysis.getAnnotated(DelegateProperties.class);
		if (dp!=null) {
			for (Field field : dp) {
				DelegateProperties anno = field.getAnnotation(DelegateProperties.class);			
				String[] value = TokenUtils.tokenize(anno.value());	
				Set<String> names = findProperties(field, value);
				specifyDelegatedProperties(field, names);
			}	
		}		
	}
	
	/**
	 * Analyzes {@link DelegateProperties} annotations of the
	 * fields of the class being processed.
	 */
	void analyzeDelegate2Map() {
		delegate2Map.clear();
		Set<Field> dp = analysis.getAnnotated(Delegate2Map.class);
		if (dp!=null) {
			for (Field field : dp) {				
				Delegate2Map anno = field.getAnnotation(Delegate2Map.class);			
				String[] names = TokenUtils.tokenize(anno.value());	
				for (int i = 0; i < names.length; i++) {
					delegate2Map.put(names[i], field);
				}				
			}	
		}		
	}
	
	/**
	 * Analyzes {@link DelegateProperties} annotations of the
	 * fields of the class being processed.
	 */
	void analyzeMockProperties() {		
		MockProperties anno = 
			analysis.getClazz().getAnnotation(MockProperties.class);
		if (anno!=null) {
			String[] names = TokenUtils.tokenize(anno.value());
			for (int i = 0; i < names.length; i++) {
				BeanPropertyDefinition<?> bpd = analysis.getFirstPropertyByName(names[i]);
				mockProperties.add(bpd);
			}
		}
	}
	
	/**
	 * Analyzes {@link DelegateProperties} annotations of the
	 * fields of the class being processed.
	 */
	void analyzeMockMethods() {		
		MockMethods anno = 
			analysis.getClazz().getAnnotation(MockMethods.class);
		if (anno!=null) {
			String[] names = TokenUtils.tokenize(anno.value());
			CollectionUtils.addAll(mockMethods, names);
		}
	}
	
	/**
	 * Analyzes {@link DelegateMethods} annotations of the
	 * fields of the class being processed.
	 */
	void analyzeDelegateMethods() {
		delegateMethods.clear();
		Set<Field> dp = analysis.getAnnotated(DelegateMethods.class);
		if (dp!=null) {
			for (Field field : dp) {
				DelegateMethods anno = field.getAnnotation(DelegateMethods.class);			
				String[] methodNames = TokenUtils.tokenize(anno.value());
				if (methodNames.length==0) {
					Method[] methods = field.getType().getMethods();
					AnyOperation<Method, String> getname = 
						new GetProperty<Method, String>("name", Method.class); //$NON-NLS-1$
					methodNames = AdapterUtils.apply(methods, new String[0], getname);
				}
				for (String methodName : methodNames) {
					delegateProperties.put(methodName, field);
					List<Field> list = delegateMethods.get(methodName);
					if (list==null) {
						list = new ArrayList<Field>();
						delegateMethods.put(methodName, list);
					}
					list.add(field);
				}
			}	
		}		
	}	
	
	
	
	/**
	 * Analyzes {@link DelegateToOtherProperty} annotation of the
	 * class being processed.
	 * 
	 * @throws ClassCreationException
	 */
	void analyzeDelegateToOther() throws ClassCreationException {
		propertyReplacements.clear();		
		DelegateToOtherProperty anno = 
			analysis.getClazz().getAnnotation(DelegateToOtherProperty.class);
		if (anno!=null) {
			String[] pairs = TokenUtils.tokenize(anno.value());
			for (String string : pairs) {
				String[] pair = TokenUtils.split(string, DelegateToOtherProperty.SEPARATOR);
				if (pair.length!=2) {
					throw wrongDelegatePropertyToOtherAnno(string);
				}
				BeanPropertyDefinition<?> supported = analysis.getFirstPropertyByName(pair[0]);
				if (supported==null) {
					throw wrongDelegatePropertyToOtherAnno(string);
				}
				if (pair[1].equalsIgnoreCase(StringConstants.NULL)) {
					markPropertyAsMock(supported);
				} else {
					BeanPropertyDefinition<?> supporting = analysis.getFirstPropertyByName(pair[1]);
					if (supporting==null) {
						throw wrongDelegatePropertyToOtherAnno(string);
					}
					markPropertyToBeSupportedBy(supported, supporting);
				}
			}			
		}
	}
	
	/**
	 * Gets the field that can support the specified property.
	 * 
	 * @param def
	 *        Property to be supported by the field.
	 * 
	 * @return Returns the field that can support the specified property.
	 *         If there is no field to support the specified property, 
	 *         returns null.
	 */
	Field getFieldForProperty(BeanPropertyDefinition<?> def) {
		Field field = analysis.getFirstAnnotated(Property.class, def.getName());
		if (field!=null) {
			if (def.isCanBeSupportedBy(field.getType())) {
				return field;
			}			
		}		
		return null;
	}
	
	/**
	 * Gets the field that can support the specified property.
	 * 
	 * @param def
	 *        Property to be supported by the field.
	 * 
	 * @return Returns the field that can support the specified property.
	 *         If there is no field to support the specified property, 
	 *         returns null.
	 */
	Field getFieldForDelegateProperty(BeanPropertyDefinition<?> def) {
		return delegateProperties.get(def.getName());
	}
	
	/**
	 * Gets the field that can support the specified property.
	 * 
	 * @param def
	 *        Property to be supported by the field.
	 * 
	 * @return Returns the field that can support the specified property.
	 *         If there is no field to support the specified property, 
	 *         returns null.
	 */
	Field getFieldForDelegate2Map(BeanPropertyDefinition<?> def) {
		return delegate2Map.get(def.getName());
	}
	
	/**
	 * Selects from a list the best matching BeanPropertyDefinition that can
	 * support with delegation the specified property.
	 * 
	 * @param candidates
	 * @param property
	 * 
	 * @return Returns 
	 */
	BeanPropertyDefinition<?> selectDelegate
	(List<BeanPropertyDefinition<?>> candidates, BeanPropertyDefinition<?> property) {
		BeanPropertyDefinition<?> match = selectDelegateStrict(candidates, property);
		if (match!=null) {
			return match;
		}
		return selectDelegateLoose(candidates, property);
	}
	
	/**
	 * Selects from a list the appropriate BeanPropertyDefinition that matches
	 * exactly to the type of the specified property.
	 * 
	 * @param candidates
	 * @param property
	 * 
	 * @return Returns 
	 */
	BeanPropertyDefinition<?> selectDelegateStrict
	(List<BeanPropertyDefinition<?>> candidates, BeanPropertyDefinition<?> property) {
		for (BeanPropertyDefinition<?> def : candidates) {
			if (def.getType().equals(property.getType())) {
				return def;
			}			
		}
		return null;
	}
	
	/**
	 * Selects from a list the appropriate BeanPropertyDefinition that can
	 * support with delegation the specified property.
	 * 
	 * @param candidates
	 * @param property
	 * 
	 * @return Returns 
	 */
	BeanPropertyDefinition<?> selectDelegateLoose
	(List<BeanPropertyDefinition<?>> candidates, BeanPropertyDefinition<?> property) {
		for (BeanPropertyDefinition<?> def : candidates) {
			if (property.isCanBeSupportedBy(def.getType())) {
				return def;
			}			
		}
		return null;
	}
	
	/**
	 * Selects from a list the appropriate field and method that matches
	 * exactly to the return type and argument types of the specified method.
	 * 
	 * @param candidates
	 * @param method
	 * 
	 * @return Returns 
	 */
	Field selectDelegateStrict (List<Field> candidates, Method method) {
		for (Field field : candidates) {
			Method methodToDelegate = ReflectionUtils.getPublicMethod
				(method.getName(), field.getType(), method.getParameterTypes());
			if (methodToDelegate!=null
			&& ReflectionUtils.returnTypeIsCompatible(method, methodToDelegate)) {
				return field;					
			}
		}
		return null;
	}
		
	/**
	 * Selects from a list the appropriate field and method that matches
	 * exactly to the return type and argument types of the specified method.
	 * 
	 * @param candidates
	 * @param method
	 * 
	 * @return Returns 
	 */
	Field selectDelegateLoose (List<Field> candidates, Method method) {
		Class<?>[] args = method.getParameterTypes();
		
		for (Field field : candidates) {
			List<Method> candidateMethods = 
				ReflectionUtils.getPublicMethodsByName(method.getName(), field.getType());
			if (candidateMethods!=null) {
				for (Method candidateMethod : candidateMethods) {
					Class<?>[] candidateParams = candidateMethod.getParameterTypes();
					if (ReflectionUtils.argumentTypesMatchParameterTypes(candidateParams, args) 
					&&  ReflectionUtils.returnTypeIsCompatible(method, candidateMethod)) {
						return field;	
					}
				}
			}			
		}
		return null;
	}
	
	/**
	 * Selects from a list the appropriate field and method that matches
	 * exactly to the return type and argument types of the specified method.
	 * 
	 * @param candidates
	 * @param method
	 * 
	 * @return Returns 
	 */
	Field selectDelegate (List<Field> candidates, Method method) {
		Field field = selectDelegateStrict(candidates, method);
		if (field!=null) {
			return field;
		}
		return selectDelegateLoose(candidates, method);
	}
	
	
	
	
	
	/**
	 * Creates a new ClassCreationException for fields with a wrong
	 * {@link DelegateProperties} annotation.
	 * 
	 * @param fieldName
	 *        Name of field.
	 * @param type 
	 *        property or method
	 * @param entity 
	 *        name of property or method
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException wrongDelegateToField(String fieldName, String type, String entity) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat	
			("Field ", fieldName, " can't delegate the ", type, " ", entity);
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a new ClassCreationException for a wrong pair defined in a
	 * {@link DelegateToOtherProperty} annotation.
	 * 
	 * @param pair
	 *        Name of field.
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException wrongDelegatePropertyToOtherAnno(String pair) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat	
			("The annotation DelegateToOtherProperty of class ", 
			 analysis.getClazz().getName(),
			 " defines a wrong pair of properties: ", pair); 
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a new ClassCreationException for fields with a wrong
	 * {@link DelegateProperties} annotation.
	 * 
	 * @param fieldName
	 *        Name of field.
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException wrongDelegateToMap(String fieldName) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat	
			("Field ", fieldName, " is not a map and can't have the @Delegate2Map annotation");
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a new ClassCreationException for the case that a property was
	 * attempted to be replaces by another, but this was not possible.
	 * 
	 * @param supporting 
	 *        Property that could not support the other property.
	 * @param supported 
	 *        Property that couldn't be supported.
	 * 
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException propertyCantSupportOther(String supporting, String supported) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat	
			("Property ", supporting, " can't replace property ", supported);
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a new ClassCreationException for the case when a class
	 * does not have a property.
	 * 
	 * @param type
	 *        Type.
	 * @param property 
	 *        name of property
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException propertyNotFound(String type, String property) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat	
			("Property ", property, " not found in type ", type);
		return new ClassCreationException(msg);
	}
	

	/**
	 * Creates a ClassCreation exception for cases that a wrong annotation
	 * {@link ComparableThrough} has been set.
	 * 
	 * @return retuns a ClassCreation.
	 */
	ClassCreationException wrongComparableThroughAnno() {
		@SuppressWarnings("nls")
		String msg = "Invalid value of ComparableThrough annotation in class " 
			       + analysis.getClazz().getName();
		return new ClassCreationException(msg);
	}
		
	@Override
	protected String getSuffix() {
		return "_AbstractClassImplementationBybo2"; //$NON-NLS-1$
	}
	
	
	
	
	@Override
	protected void initialize(Class<?> clazz) throws ClassCreationException  {	
		super.initialize(clazz);
		if (Comparable.class.isAssignableFrom(clazz)) {
			compareTo = MethodsUtilities.compareTo(clazz);
		} else {
			compareTo = null;
		}
		mockProperties.clear();
		mockMethods.clear();
		analyzeDelegateProperties();
		analyzeDelegate2Map();
		analyzeDelegateMethods();
		analyzeDelegateToOther();
		analyzeMockProperties();
		analyzeMockMethods();
	}
	
	
	@Override
	protected void validatePossibleImplementation() throws ClassCreationException {
		Class<?> type = analysis.getClazz();
		if (!ReflectionUtils.isAbstractClass(type)) {
			throw CodeGenerationUtilities.typeNotSupported(type);
		}
		if (analysis.isContainsOddProperties()) {
			throw cantSupportOddProperties();
		}
	}
	
	@Override
	protected void supportType() throws ClassCreationException {
		addSerialVersionUid();		
		supportProperties();
		supportMethods();	
		if (isNotImplementedMethod(compareTo)) {
			supportComparable();
		}
		addBasicUpdaters();		
	}
	
	@Override
	protected String supertype() {
		return analysis.getClazz().getCanonicalName();
	}
	
	
	/**
	 * Selects the appropriate PropertyCodeTemplates.
	 * 
	 * @param field
	 * @param delegate
	 * 
	 * @return Returns the appropriate PropertyCodeTemplates.
	 */
	protected PropertyCodeTemplates getPropertyCodeTemplates(Field field, boolean delegate) {
		boolean isPrivate = Modifier.isPrivate(field.getModifiers());
		if (delegate) {
			return isPrivate ? reflectiveDelegateProperty : directDelegateProperty;
		} else {
			return isPrivate ? reflectiveProperty : directProperty;
		}
	}
	
	/**
	 * Selects the appropriate PropertyCodeTemplates.
	 * 
	 * @param field
	 * 
	 * @return Returns the appropriate PropertyCodeTemplates.
	 */
	protected PropertyCodeTemplates getDelegate2MapCodeTemplates(Field field) {
		boolean isPrivate = Modifier.isPrivate(field.getModifiers());
		return isPrivate ? reflectiveDelegate2Map : directDelegate2Map;
	}
	
	/**
	 * Supports the specified property.
	 * 
	 * @param def
	 *        Property to support.
	 * 
	 * @return Returns true if the specified property could be supported.
	 */
	protected boolean supportPropertyWithFieldOrMock(BeanPropertyDefinition<?> def) {
		PropertyCodeTemplates templates;
		Class<?> fieldType;
		if (mockProperties.contains(def)) {
			templates = mockProperty;
			fieldType = def.getType();
		} else {
			Field field = getFieldForProperty(def);
			if (field==null) {
				return false;
			}
			templates = getPropertyCodeTemplates(field, false);
			fieldType = field.getType();
		}				
		String name = def.getName();
		Class<?> type = def.getType();
		
		Map<String, String> vars = Variables.variablesForProperty(name, type, name, fieldType);
		
		Method getter = def.getGetter();
		if (isNotImplementedMethod(getter)) {
			String code = templates.getPropertyGetter(vars, getter.getName());
			implementMethod(getter, code);
		}	
		
		Method setter = def.getSetter();
		if (isNotImplementedMethod(setter)) {
			String code = templates.getPropertySetter(vars);
			implementMethod(setter, code);
		}		
		return true;
	}
	
	/**
	 * Supports the specified property.
	 * 
	 * @param def
	 *        Property to support.
	 * 
	 * @return Returns true if the specified property could be supported.
	 * @throws ClassCreationException 
	 */
	protected boolean supportPropertyWithDelegation(BeanPropertyDefinition<?> def) 
	throws ClassCreationException {
		Field field = getFieldForDelegateProperty(def);
		if (field==null) {
			return false;
		}
		Class<?> fieldType = findFieldType(field);
		TypeAnalysis fieldAnalysis = TypeAnalysis.analyze(fieldType);
		List<BeanPropertyDefinition<?>> candidates = 
			fieldAnalysis.getPropertiesByName(def.getName());
		if (candidates==null) {
			throw wrongDelegateToField(field.getName(), PROPERTY, def.getName());
		}
		BeanPropertyDefinition<?> delegate = selectDelegate(candidates, def);
		if (delegate==null) {
			throw wrongDelegateToField(field.getName(), PROPERTY, def.getName());
		}
		Map<String, String> vars = Variables.variablesForProperty (
				def.getName(), def.getType(), field.getName(), fieldType);
		
		PropertyCodeTemplates templates = getPropertyCodeTemplates(field, true);
		
		Method getter = def.getGetter();
		if (isNotImplementedMethod(getter) && delegate.getAccessType().isReadable()) {
			String code = templates.getPropertyGetter(vars,getter.getName());
			implementMethod(getter, code);
		}	
		
		Method setter = def.getSetter();
		if (isNotImplementedMethod(setter)  && delegate.getAccessType().isModifiable()) {
			String code = templates.getPropertySetter(vars);
			implementMethod(setter, code);
		}
		return true;
	}
	
	/**
	 * Supports the specified property.
	 * 
	 * @param def
	 *        Property to support.
	 * 
	 * @return Returns true if the specified property could be supported.
	 * @throws ClassCreationException 
	 */
	protected boolean supportPropertyWithDelegate2Map(BeanPropertyDefinition<?> def) 
	throws ClassCreationException {
		Field field = getFieldForDelegate2Map(def);
		if (field==null) {
			return false;
		}		
		Class<?> fieldType = findFieldType(field);
		if (!Map.class.isAssignableFrom(fieldType)) {
			throw wrongDelegateToMap(field.getName());
		}
		
		Map<String, String> vars = Variables.variablesForProperty (
				def.getName(), def.getType(), field.getName(), fieldType);
		
		PropertyCodeTemplates templates = getDelegate2MapCodeTemplates(field);
		
		Method getter = def.getGetter();
		if (isNotImplementedMethod(getter)) {
			String code = templates.getPropertyGetter(vars, getter.getName());
			implementMethod(getter, code);
		}	
		
		Method setter = def.getSetter();
		if (isNotImplementedMethod(setter)) {
			String code = templates.getPropertySetter(vars);
			implementMethod(setter, code);
		}
		return true;
	}
	
	
	/**
	 * Specifies properties to delegate to the specified field.
	 * 
	 * @param field
	 * @param names
	 */
	protected void specifyDelegatedProperties(Field field, Collection<String> names) {
		for (String name : names) {
			delegateProperties.put(name, field);
		}
	}
	
	
	
	
	/**
	 * Handles the properties of the class.
	 * 
	 * @throws ClassCreationException 
	 */
	protected void supportProperties() throws ClassCreationException {
		Set<BeanPropertyDefinition<?>> properties = analysis.getAllProperties();
		for (BeanPropertyDefinition<?> bpd : properties) {
			supportProperty(bpd);
		}
	}
	
	/**
	 * Tries to implement all methods that haven't been implemented
	 * until now.
	 */
	protected void supportMethods() {
		Set<Method> notImplemented = getMethodsNotYetImplemented();
		for (Method method : notImplemented) {
			supportMethod(method);
		}
	}
	
	/**
	 * Support the specified method.
	 * 
	 * @param method
	 *        Not implemented method to support.
	 */
	protected void supportMethod(Method method) {
		boolean ok = supportMethodWithDelegate(method);
		if (!ok) {
			supportMethodWithMock(method);
		}
	}
	
	/**
	 * Try to support a not implemented method by creating a mock
	 * implementation.
	 * 
	 * @param method
	 *        Method to implement with mock.
	 *        
	 * @return Returns true if the method is implemented, otherwise
	 *         returns false.
	 */
	protected boolean supportMethodWithMock(Method method) {
		if (mockMethods.contains(method.getName())) {
			MethodCodeTemplates templates = emptyMethodTemplates;						
			Class<?>[] args = method.getParameterTypes();
			String declarationParams=generateMethodDeclarationParameters(args);			
			Map<String, String> vars = Variables.variablesForEmptyMethod (
				method.getName(), method.getReturnType(), declarationParams);
			String code = templates.getMethod(vars, method.getReturnType());
			implementMethod(method, code);
			return true;
		}		
		return false;
	}
	
	/**
	 * Try to support a not implemented method by delegating it to a
	 * field's method.
	 * 
	 * @param method
	 *        Method to implement with delegation.
	 *        
	 * @return Returns true if the method is implemented, otherwise
	 *         returns false.
	 */
	protected boolean supportMethodWithDelegate(Method method) {
		List<Field> candidates = delegateMethods.get(method.getName());
		if (candidates!=null) {
			Field field = selectDelegate(candidates, method);
			if (field!=null) {
				MethodCodeTemplates templates;
				if (Modifier.isPrivate(field.getModifiers())) {
					templates = reflectiveDelegateMethod;
				} else {
					templates = directDelegateMethod;
				}
				Class<?>[] args = method.getParameterTypes();
				String declarationParams=generateMethodDeclarationParameters(args);
				String invocationParams=generateMethodInvocationParameters(args.length);
				
				Map<String, String> vars = Variables.variablesForDelegateMethod (
					method.getName(), method.getReturnType(), declarationParams, 
					field.getType(), field.getName(), invocationParams);
				String code = templates.getMethod(vars, method.getReturnType());
				implementMethod(method, code);
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * Handles a property.
	 * 
	 * @param def
	 *        Property.
	 *         
	 * @return Returns true if the accessors for the property have
	 *         been implemented.
	 *         
	 * @throws ClassCreationException 
	 */
	protected boolean supportProperty(BeanPropertyDefinition<?> def) 
	throws ClassCreationException {
		boolean ok = supportPropertyWithFieldOrMock(def);
		if (!ok) {
			ok = supportPropertyWithDelegation(def);
		}
		if (!ok) {
			ok = supportPropertyWithOther(def);
		}
		if (!ok) {
			ok = supportPropertyWithDelegate2Map(def);
		}
		return ok;
	}
	
	/**
	 * Support comparable by delegating the comparison to properties.
	 * 
	 * @param type 
	 * @param properties 
	 */	
	protected void implementCompareTo(Class<?> type, BeanPropertyDefinition<?>[] properties) {		
		StringBuilder sb = new StringBuilder();
		for (BeanPropertyDefinition<?> def : properties) {
			Map<String, String>  vars = Variables.variablesForProperty(def);
			String codepart = methodsTemplates.getCompareToFragment(vars);
			sb.append(codepart);
			sb.append(StringConstants.NEWLINE);
		}
		Map<String, String> variables = new HashMap<String, String>();
		Variables.setType(variables, type);
		Variables.setFragment(variables, sb.toString());
		String code = methodsTemplates.getCompareToBody(variables);
		implementMethod(compareTo, code);		
	}	
	
	
	
	/**
	 * Support for comparable.
	 * 
	 * @return Returns true if the method was implemented.
	 * @throws ClassCreationException 
	 */
	protected boolean supportComparable() throws ClassCreationException {
		Class<?> clazz = analysis.getClazz();
		ComparableThrough anno = clazz.getAnnotation(ComparableThrough.class);
		if (anno!=null) {
			String[] properties = TokenUtils.tokenize(anno.value());
			BeanPropertyDefinition<?>[] defs = new BeanPropertyDefinition<?>[properties.length];
			for (int i = 0; i < defs.length; i++) {
				defs[i] = analysis.getFirstPropertyByName(properties[i]);
				if (defs[i]==null) {
					throw wrongComparableThroughAnno();
				}
			}
			implementCompareTo(clazz, defs);
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * Tries to support the specified property by delegating it to another
	 * property of the same class.
	 * 
	 * @param def
	 *        Property to support.
	 * 
	 * @return Returns true if the specified property could be supported.
	 * @throws ClassCreationException 
	 */
	protected boolean supportPropertyWithOther (BeanPropertyDefinition<?> def) 
	throws ClassCreationException {
		BeanPropertyDefinition<?> replacement = propertyReplacements.get(def);
		if (replacement==null) {
			return false;
		}
		
		if (!def.isCanBeSupportedBy(replacement.getType())) {
			throw propertyCantSupportOther(replacement.getName(), def.getName());
		}
		Map<String, String> vars = Variables.variablesForProperty (
				def.getName(), def.getType(), replacement.getName(), replacement.getType());
		
		Method getter = def.getGetter();
		if (isNotImplementedMethod(getter) && replacement.getAccessType().isReadable()) {
			String code = toOther.getPropertyGetter(vars,getter.getName());
			implementMethod(getter, code);
		}	
		
		Method setter = def.getSetter();
		if (isNotImplementedMethod(setter)  && replacement.getAccessType().isModifiable()) {
			String code = toOther.getPropertySetter(vars);
			implementMethod(setter, code);
		}
		return true;
	}
	
	/**
	 * Marks a property for being supported by another.
	 * 
	 * @param supported
	 * @param supporting
	 */
	protected void markPropertyToBeSupportedBy
	(BeanPropertyDefinition<?> supported, BeanPropertyDefinition<?> supporting) {
		propertyReplacements.put(supported, supporting);
	}
		
	/**
	 * Marks a property for being a mock.
	 * 
	 * @param mock 
	 */
	protected void markPropertyAsMock (BeanPropertyDefinition<?> mock) {
		mockProperties.add(mock);
	}

	/**
	 * Gets the compareTo.
	 *
	 * @return Returns the compareTo
	 */
	protected Method getCompareTo() {
		return compareTo;
	}
		
	
	
	
	

}
