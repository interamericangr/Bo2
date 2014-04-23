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
import gr.interamerican.bo2.creation.ClassCreator;
import gr.interamerican.bo2.creation.annotations.ComparableThrough;
import gr.interamerican.bo2.creation.code.templatebean.CompareToCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.EmptyMethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.MethodCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.PropertyWithReflectiveAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.update.AbstractClassUpdater;
import gr.interamerican.bo2.creation.update.AddingFieldsClassUpdater;
import gr.interamerican.bo2.creation.update.AddingInterfacesClassUpdater;
import gr.interamerican.bo2.creation.update.AddingMethodsClassUpdater;
import gr.interamerican.bo2.creation.update.ClassCompiler;
import gr.interamerican.bo2.creation.update.SettingSuperTypeClassUpdater;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.creation.util.MethodsUtilities;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic class composer.
 * 
 * This composer can successfully create an implementation of
 * an interface only if the interface is a java bean interface.
 * If the interface has more methods than accessors, the method
 * will fail.
 * 
 */
public abstract class AbstractClassCreator 
implements ClassCreator {
		
	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Gets the suffix that is added to the new class name.
	 * 
	 * @return Gets the suffix that is added to the new class name.
	 */
	protected abstract String getSuffix();
	
	/**
	 * Code templates for the implementation of the {@link Comparable#compareTo(Object)} method.
	 */
	protected CompareToCodeTemplates methodsTemplates = new CompareToCodeTemplates();
	
	/**
	 * Code templates for mock implementation of methods.
	 */
	protected EmptyMethodCodeTemplates emptyMethodTemplates = new EmptyMethodCodeTemplates();
	
	/**
	 * Templates for property implementation with direct access.
	 */
	protected PropertyWithDirectAccessCodeTemplates directProperty = new PropertyWithDirectAccessCodeTemplates();
		
	
	/**
	 * Templates for property implementation with access through the java reflection API.
	 */
	protected PropertyWithReflectiveAccessCodeTemplates reflectiveProperty = new PropertyWithReflectiveAccessCodeTemplates();
		
	
	
	/**
	 * Analysis of the type to implement.
	 */
	protected TypeAnalysis analysis;
	
	/**
	 * CompareTo method.
	 */
	protected Method compareTo = null;
	
	/**
	 * Set that contains all methods that have to be implemented.
	 */
	Set<Method> allMethodsToImplement = new HashSet<Method>();
	
	/**
	 * Set that contains all abstract methods that have been already 
	 * implemented.
	 */
	Map<Method, String> methodsAlreadyImplemented = new HashMap<Method, String>();
	
	/**
	 * List with the code for the methods to add to the new class.
	 */
	List<String> methodsToAdd = new ArrayList<String>();
	
	/**
	 * List with the code for the fields to add to the new class.
	 */
	List<String> fieldsToAdd = new ArrayList<String>();
	
	/**
	 * List with the updaters that this class creator will use.
	 */
	List<AbstractClassUpdater> updaters = new ArrayList<AbstractClassUpdater>();
	
	/**
	 * Creates a new ClassCreationException for a class that could not
	 * be loaded.
	 * 
	 * @param typeName
	 *        Name of class.
	 * 
	 * @return Returns a new ClassCreationException with the appropriate 
	 *         message.
	 */
	ClassCreationException classNotFound(String typeName) {
		@SuppressWarnings("nls")
		String msg = "Could not load class " + typeName;
		return new ClassCreationException(msg);
	}
	
	/**
	 * Creates a {@link ClassCreationException} for the case that not all
	 * abstract methods can be implemented.
	 * 
	 * @param methods
	 *        Methods not possible to implement. 
	 * 
	 * @return Returns a ClassCreationException with the appropriate message.
	 */
	@SuppressWarnings("nls")
	ClassCreationException cantImplementAllMethods(Collection<Method> methods) {	
		String methodNames = AdapterUtils.concat
			(methods, "name", Method.class, ", ");
		String msg = StringUtils.concat(
			this.getClass().getName(), 	
			" can't implement the methods: ", methodNames,
			" of type ", analysis.getClazz().getName());
		return new ClassCreationException(msg);
	}	
	
	
	
	
	public String compileTimeClassName(String runTimeName) {
		return runTimeName.replace(getSuffix(), StringConstants.EMPTY);
	}

	public String runTimeClassName(String compileTimeName) {		
		return compileTimeName + getSuffix();
	}
	
	/**
	 * Decides if the implementation will be Serializable.
	 * 
	 * The default implementation of this method, returns true only 
	 * if the class being created and analyzed by the <code>analysis</code>
	 * field is serializable. <br/>
	 * Sub-classes can override this behavior.
	 * 
	 * @return Returns true if the implementation class
	 *         is going to be Serializable.
	 */
	protected boolean mustImplementSerializable() {
		return analysis.isSerializable();
	}
	
	
	/**
	 * Support for the type.
	 * 
	 * 1. add serial version Uid field.
	 * 2. Support properties.
	 * 3. Support methods.
	 * 4. Support the compareTo() method.
	 * 5. Support any remaining method if possible.
	 * 
	 * @throws ClassCreationException 
	 */
	protected void supportType() throws ClassCreationException	{
		addSerialVersionUid();		
		supportProperties();
		supportMethods();
		supportComparable();	
		supportRemainingMethods();
		addBasicUpdaters();		
	}
	
	public synchronized Class<?> create(Class<?> type) throws ClassCreationException {		
		initialize(type);
		validatePossibleImplementation();
		markSupertypeMethodsAsImplemented();
		supportType();
		return compile();	
	}
	
	/**
	 * Supports a method by adding an empty stub.
	 * 
	 * @param method
	 */
	protected void doSupportMethodWithMock(Method method) {
		MethodCodeTemplates templates = emptyMethodTemplates;						
		Class<?>[] args = method.getParameterTypes();
		String declarationParams=generateMethodDeclarationParameters(args);			
		Map<String, String> vars = Variables.variablesForEmptyMethod (
			method.getName(), method.getReturnType(), declarationParams);
		String code = templates.getMethod(vars, method.getReturnType());
		implementMethod(method, code);
	}
		
	
	/**
	 * Initialization
	 * 
	 * @param clazz
	 * 
	 * @throws ClassCreationException 
	 *         If the analysis of the specified type, identifies a problem.
	 */
	@SuppressWarnings("unused")
	protected void initialize(Class<?> clazz) throws ClassCreationException  {
		this.analysis = TypeAnalysis.analyze(clazz);
		allMethodsToImplement.clear();
		allMethodsToImplement.addAll(analysis.getAbstractGetters());
		allMethodsToImplement.addAll(analysis.getAbstractSetters());
		allMethodsToImplement.addAll(analysis.getAbstractMethods());
		
		methodsAlreadyImplemented.clear();
		fieldsToAdd.clear();
		methodsToAdd.clear();
		updaters.clear();
		
		if (Comparable.class.isAssignableFrom(clazz)) {
			compareTo = MethodsUtilities.compareTo(clazz);
		} else {
			compareTo = null;
		}
	}
	
	/**
	 * Finds the matching method.
	 * 
	 * @param m
	 * @param methods
	 * 
	 * @return the matching method.
	 */
	private Method getMatchingMethodFromSet(Method m, Set<Method> methods) {
		for (Method method : methods) {
			boolean sameName = method.getName().equals(m.getName());			
			boolean sameReturnType = ReflectionUtils.returnTypeIsCompatible(method, m);
			boolean sameArguments = ReflectionUtils.argumentTypesMatchParameterTypes
				(method.getParameterTypes(), m.getParameterTypes());
			boolean isMatch = sameName && sameReturnType && sameArguments;
			if (isMatch) {
				return method;
			}
		}
		return null;
	}
	
	/**
	 * Marks the concrete methods of the supertype as implemented.
	 * 
	 * @throws ClassCreationException
	 */
	void markSupertypeMethodsAsImplemented() throws ClassCreationException {
		TypeAnalysis superAnalysis = analysisOfSuper();
		if (superAnalysis!=null) {
			Set<Method> superMethods = new HashSet<Method>();
			superMethods.addAll(superAnalysis.getConcreteGetters());
			superMethods.addAll(superAnalysis.getConcreteSetters());
			superMethods.addAll(superAnalysis.getConcreteMethods());
			
			for (Method method : superMethods) {
				Method abstrct = getMatchingMethodFromSet(method, allMethodsToImplement);
				if (abstrct!=null) {
					methodsAlreadyImplemented.put(abstrct, "/*in supertype*/"); //$NON-NLS-1$
				}				
			}			
		}
	}
	
	
	
	
	
	
	/**
	 * Adds code for a field.
	 * 
	 * @param code
	 */
	protected void addFieldCode(String code) {
		if (code!=null) {
			fieldsToAdd.add(code);
		}		
	}
	
	/**
	 * Adds code for a method.
	 * 
	 * @param code
	 */
	void addMethodCode(String code) {
		if (code!=null) {
			methodsToAdd.add(code);
		}		
	}
	
	/**
	 * Gets an updated that adds the fields that have been added to 
	 * with <code>addFieldCode(string)</code>
	 * 
	 * @return Returns an {@link AddingFieldsClassUpdater} that will
	 *         add the fields that have been added with addFieldCode.
	 *         If no field is to be added, then returns null.
	 */
	AddingFieldsClassUpdater updaterForFields() {
		if (fieldsToAdd.isEmpty()) {
			return null;
		}
		return new AddingFieldsClassUpdater(fieldsToAdd.toArray(new String[0]));
	}
	
	/**
	 * Gets an updated that adds the methods that have been added to 
	 * with <code>addMethodCode(string)</code>
	 * 
	 * @return Returns an {@link AddingMethodsClassUpdater} that will
	 *         add the methods that have been added with addMethodCode.
	 *         If no method is to be added, then returns null.
	 */
	AddingMethodsClassUpdater updaterForMethods() {
		if (methodsToAdd.isEmpty()) {
			return null;
		}
		return new AddingMethodsClassUpdater(methodsToAdd.toArray(new String[0]));
	}
	
	/**
	 * Checks if a method is needs to be implemented.
	 * 
	 * @param method
	 * 
	 * @return Returns true if the specified method is not abstract,
	 *         or it has been marked as implemented by 
	 *         <code>markMethodAsImplemented(Method)</code>.
	 */
	protected boolean isNotImplementedMethod(Method method) {
		if (method==null) {
			return false;
		}
		if (methodsAlreadyImplemented.containsKey(method)) {
			return false;
		}
		return Modifier.isAbstract(method.getModifiers());
	}
	
	/**
	 * Adds an updater.
	 * 
	 * @param updater
	 */
	protected void addUpdater(AbstractClassUpdater updater) {
		if (updater!=null) {
			updaters.add(updater);			
		}
	}
	
	/**
	 * Adds the basic updaters. <br/>
	 * 
	 * The default basic updaters are:
	 * <li> One that sets the super type to according to the return value
	 *      of the method <code>supertype()</code></li>
	 * <li> One that adds the fields that have been added with  
	 *      <code>addFieldCode(strings)</code></li>
	 * <li> One that adds the methods added with  
	 *      <code>implementMethod(method, string)</code></li>
	 * <li> One that adds interfaces returned by the 
	 *      <code>interfaces()</code> method.</li>
	 * 
	 */
	protected void addBasicUpdaters() {
		String superName = supertype();
		if (!StringUtils.isNullOrBlank(superName)) {
			addUpdater(new SettingSuperTypeClassUpdater(superName));			
		}		
		addUpdater(updaterForFields());
		addUpdater(updaterForMethods());	
		String[] interfaces = interfaces();
		if (!ArrayUtils.isNullOrEmpty(interfaces)) {
			AddingInterfacesClassUpdater addInterfaces = 
				new AddingInterfacesClassUpdater(interfaces);	
			addUpdater(addInterfaces);
		}
		//addDefaultConstructor();
	}
	
	/**
	 * Gets a class compiler that contains the updaters that have been added.
	 * 
	 * @return Returns a class compiler
	 */
	protected ClassCompiler getClassCompiler() {
		return new ClassCompiler(updaters.toArray(new AbstractClassUpdater[0]));
	}
	
	/**
	 * Validates that an attempt to create a concrete class for the the class 
	 * that has been analyzed with <code>initialize(clazz)</code> is meaningful.
	 *  
	 * @throws ClassCreationException
	 */
	protected abstract void validatePossibleImplementation() 
	throws ClassCreationException;
	
	/**
	 * Validates the successful implementation of all methods.
	 * 
	 * @throws ClassCreationException
	 */
	protected void validateImplementation() throws ClassCreationException {
		ClassCreationException ex = couldntImplementAllMethods();
		if (ex!=null) {
			logMethods();
			throw ex;
		}
	}

	
	/**
	 * Creates a {@link ClassCreationException} in the case that not all
	 * abstract methods can be implemented.
	 * 
	 * @return Returns a ClassCreationException with the appropriate message.
	 */
	@SuppressWarnings({ "nls", "rawtypes" })
	protected ClassCreationException cantSupportOddProperties() {
		Set<BeanPropertyDefinition> odds = CollectionUtils.convert(analysis.getOddProperties());		
		String odd = AdapterUtils.concat (odds, "name", BeanPropertyDefinition.class, ", ");
		String msg = StringUtils.concat(
			"Class ", analysis.getClazz().getName(),
			" has the following odd properties: ", odd);
		return new ClassCreationException(msg);
	}
	
	/**
	 * Maybe odd properties can be supported after all
	 * 
	 * @see #covariant(BeanPropertyDefinition, BeanPropertyDefinition)
	 * 
	 * @return True, if we can support all the odd properties
	 */
	protected boolean canSupportOddProperties() {
		for(BeanPropertyDefinition<?> bpd : analysis.getOddProperties()) {
			List<BeanPropertyDefinition<?>> sameNamed = analysis.getPropertiesByName(bpd.getName());
			sameNamed.remove(bpd);
			for(BeanPropertyDefinition<?> bpd2 : sameNamed) {
				if(!covariant(bpd, bpd2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Two BeanPropertyDefinitions that: 
	 * <li>have the same name
	 * <li>one is readOnly and the other writeOnly
	 * <li>the getter retrun type is a sub-type of the setter argument type
	 * are covariant
	 * 
	 * @param one
	 * @param two
	 * 
	 * TODO: add this to BeanPropertyDefinition api?
	 * 
	 * @return True, if they are covariant.
	 */
	private boolean covariant(BeanPropertyDefinition<?> one, BeanPropertyDefinition<?> two) {
		boolean couldBeCov = one.getName().equals(two.getName()) && (one.isReadOnly() && two.isWriteOnly()) || (one.isWriteOnly() && two.isReadOnly());
		if(!couldBeCov) {
			return false;
		}
		
		Class<?> getterType = null;
		Class<?> setterType = null;
		
		if(one.isReadOnly()) {
			getterType = one.getGetter().getReturnType();
			setterType = two.getSetter().getParameterTypes()[0];
		} else {
			getterType = two.getGetter().getReturnType();
			setterType = one.getSetter().getParameterTypes()[0];
		}
		
		return setterType.isAssignableFrom(getterType);
	}
	
	/**
	 * Checks if all methods have been implemented and if not
	 * returns a ClassCreationException, otherwise returns null.
	 * 
	 * @return a ClassCreationException or null.
	 */
	protected ClassCreationException couldntImplementAllMethods() {
		Set<Method> missingMethods = getMethodsNotYetImplemented();
		if (missingMethods.isEmpty()) {
			return null;
		}
		return cantImplementAllMethods(missingMethods);
	}
	
	/**
	 * Logs all methods that have to be implemented and the code
	 * implemented so far for each one of them.
	 */
	@SuppressWarnings("nls")
	void logMethods() {
		StringBuilder sb = new StringBuilder();
		sb.append("Methods implementation for class ");
		sb.append(analysis.getClazz().getName());
		sb.append("\n");
		sb.append("Total methods to implement: ");
		sb.append(allMethodsToImplement.size());
		sb.append("\n");
		sb.append("Total methods implemented: ");
		sb.append(methodsAlreadyImplemented.size());
		sb.append("\n");
				
		for (Method m : allMethodsToImplement) {
			String code = methodsAlreadyImplemented.get(m);
			sb.append(m.getName());
			sb.append("\n");
			sb.append(StringUtils.toString(code));
			sb.append("\n/* end of method */\n");
		}
		
		Set<Method> unnecessary = new HashSet<Method>(methodsAlreadyImplemented.keySet());
		unnecessary.removeAll(allMethodsToImplement);
		if (!unnecessary.isEmpty()) {
			sb.append("The following unnecessary methods have been implemented: ");
			sb.append(AdapterUtils.concat(unnecessary, "name", Method.class, ","));
			sb.append("\n");
		}		
		logger.debug(sb.toString());
	}
		
	/**
	 * Validates the implementation and compiles the class using 
	 * a compiler that contains the added class updaters.
	 * 
	 * @return Returns the compiled class.
	 * 
	 * @throws ClassCreationException 
	 */
	protected Class<?> compile() throws ClassCreationException {
		validateImplementation();
		ClassCompiler compiler = getClassCompiler();		
		String newClassName = runTimeClassName(analysis.getClazz().getCanonicalName());		
		return compiler.createType(newClassName);	
	}
	
	
	/**
	 * Adds the implementation for a method and marks it as implemented.
	 * 
	 * @param method
	 *        Method to implement
	 * @param code
	 *        Method code.
	 */
	protected void implementMethod(Method method, String code) {
		if (code!=null) {
			String cleanCode = CodeGenerationUtilities.cleanJavaCode(code);
			methodsAlreadyImplemented.put(method, cleanCode);			
			addMethodCode(cleanCode);
		}					
	}
	
	/**
	 * Gets the code to support the serialVersionUID.
	 * 
	 * @return returns code for a serialVersionUID field declaration, 
	 *         if the class being processed is Serializable. 
	 *         Otherwize returns null.
	 */
	@SuppressWarnings("nls")
	private String serialVersionUidCode() {
		if (!analysis.isSerializable()) {
			return null;
		}
		Long svuid = analysis.getSerialVersionUniqueId();
		if (svuid==null) {
			svuid = 1L;
		}
		String code = StringUtils.concat(
			"private static final long serialVersionUID = ", 
			Long.toString(svuid), "L;");
		return code;
	}
	
	/**
	 * Adds field declaration code for serialVersionUID.
	 */
	protected void addSerialVersionUid() {
		addFieldCode(serialVersionUidCode());
	}
	
	/**
	 * Handles the properties of the class.
	 * 
	 * @throws ClassCreationException 
	 */
	protected abstract void supportProperties() throws ClassCreationException;
	
	/**
	 * Tries to implement all methods that haven't been implemented
	 * until now.
	 */
	protected abstract void supportMethods();
	
	
	/**
	 * This method provides an extension point.
	 * 
	 * It provides the place to implement any remaining method.
	 * This is the last attempt to implement methods.
	 */
	protected void supportRemainingMethods() {
		/* empty */		
	}	
	
	/**
	 * Gets all methods not yet implemented.
	 * 
	 * @return Returns all methods not yet implemented.
	 */
	protected Set<Method> getMethodsNotYetImplemented() {
		Set<Method> missingMethods = new HashSet<Method>(allMethodsToImplement);
		missingMethods.removeAll(methodsAlreadyImplemented.keySet());
		return missingMethods;
	}
	
	/**
	 * Finds names of properties of a type defined in a string.
	 * 
	 * @param type
	 *        Type to get the properties. 
	 * @param properties
	 *        Names of the properties.
	 * 
	 * @return Returns a set with the names of the properties in the
	 *         properties string. If the properties array is empty,
	 *         then returns all properties of the specified type.
	 */
	protected Set<String> findProperties(Class<?> type, String[] properties) {
		TypeAnalysis fieldAnalysis = TypeAnalysis.analyze(type);
		Set<String> names;
		if (properties.length==0) {
			names = fieldAnalysis.getNamesOfProperties();
		} else {
			names = new HashSet<String>(Arrays.asList(properties));				
		}
		return names;
	}
	
	/**
	 * Finds names of properties of a field defined in a string.
	 * 
	 * @param field
	 *        field to get the properties. 
	 * @param properties
	 *        Strings with the properties.
	 * 
	 * @return Returns a set with the names of the properties in the
	 *         properties string. If the propertiesString is empty,
	 *         then returns all properties of the specified type.
	 */
	protected Set<String> findProperties(Field field, String[] properties) {		
		return findProperties(field.getType(), properties);
	}
	
	/**
	 * Sets the supertype of the new class.
	 * 
	 * If this method returns null, then no supertype is set to
	 * the new class.
	 * 
	 * @return Returns the super type of the new class.
	 */
	protected String supertype() {
		return null;
	}
	
	/**
	 * Sets the interfaces of the new class.
	 * 
	 * If this method returns null, then no additional interface is added.
	 * 
	 * @return Returns the interfaces of the new class.
	 */
	protected String[] interfaces() {
		return null;
	}
	
	/**
	 * If this updater sets a supertype, returns the type analysis
	 * of the supertype, otherwise returns null.
	 * 
	 * @return Returns the analysis of the supertype.
	 * @throws ClassCreationException 
	 */
	TypeAnalysis analysisOfSuper() throws ClassCreationException {
		String superName = supertype();
		if (StringUtils.isNullOrBlank(superName)) {
			return null;
		}
		if (this.analysis.getClazz().getName().equals(superName)) {
			return null;
		}
		try {
			Class<?> superClass = Class.forName(superName);
			return TypeAnalysis.analyze(superClass);
		} catch (ClassNotFoundException e) {
			throw classNotFound(superName);
		}
	}
	
	/**
	 * Gets the type of a field.
	 * 
	 * @param field
	 * 
	 * @return Returns the type of the field.
	 */
	protected Class<?> findFieldType(Field field) {
		return field.getType();
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
	 * Gets the compareTo.
	 *
	 * @return Returns the compareTo
	 */
	protected Method getCompareTo() {
		return compareTo;
	}
	
	/**
	 * Support for comparable.
	 * 
	 * @return Returns true if the method was implemented.
	 * @throws ClassCreationException 
	 */
	protected boolean doSupportComparable() throws ClassCreationException {
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
	 * Support for comparable.
	 * 
	 * @return Returns true if the method was implemented.
	 * @throws ClassCreationException 
	 */
	protected boolean supportComparable() throws ClassCreationException {
		if (isNotImplementedMethod(compareTo)) {
			return doSupportComparable();
		}
		return true;
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
	
}
