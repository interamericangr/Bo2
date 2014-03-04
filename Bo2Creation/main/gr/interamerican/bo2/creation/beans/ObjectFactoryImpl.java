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
package gr.interamerican.bo2.creation.beans;

import gr.interamerican.bo2.creation.ClassCreator;
import gr.interamerican.bo2.creation.NameResolver;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.AssociationTable;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ObjectFactory}.
 * 
 * <p> 
 * The most important features of this implementation of ObjectFactory are: 
 * <li> Association of names of declaration and implementation type. </li>
 * <li> Creation or enhancement of implementation types that are not concrete. </li>
 * <li> Constructor selection </li> </p> 
 * <br/>
 * <p>
 * <b> Associating names between declaration and implementation types </b> <br/>
 * Association of declaration and implementation type can be defined in properties 
 * files. A simple text file that has the paths to these properties files is read
 * by this factory's constructor. The constructor will then load all those properties
 * files on a map that is used for the matching. <br/>
 * This map is always searched first when trying to resolve the implementation class
 * for a declaration class or vice versa. In case the class being searched does not
 * exist in the above mentioned map, then a {@link NameResolver} will try to resolve the
 * names. </p> 
 * <br/>
 * <p>
 * Creation or enhancement of implementation types is delegated to the 
 * {@link ClassCreator}. This {@link ObjectFactoryImpl} uses a {@link ClassCreator}
 * that is specified in the properties object that is the argument to its
 * constructor. 
 * <br/>
 * <p> 
 * <b> Constructor selection </b> <br/>
 * The constructor of this factory, takes a Properties object as argument.
 * If this object is provided to the constructor, namely if the argument put 
 * to the constructor is not null, then this factory will try to use this 
 * properties object as argument for the creation of objects. If the 
 * <code>properties</code> field of this factory is not null, then the factory 
 * will search each implementation type for a constructor that takes an argument 
 * of type Properties. If the implementation type has such a constructor, and 
 * the factory's properties is not null, then this constructor will be used, 
 * with the same properties object as argument. If this factory has its 
 * properties field null, or the implementation class does not have the before 
 * mentioned constructor, then the factory will use the implementation class' 
 * <code>newInstance()</code> method.
 * </p> <br/>
 */
public class ObjectFactoryImpl implements ObjectFactory {
	
	/**
	 * Prefix for type replacement file indication. <br/>
	 * 
	 * The object factory initialization file contains paths to properties
	 * files where type mappings are defined. There are to types of type
	 * mappings files. One type is a mapping file that maps declaration 
	 * types to implementation types. The other type is a mapping file
	 * that maps declaration to other declaration types. This prefix
	 * marks the second type of file.
	 */
	public static final String REPLACEMENT_IND = "Replace:"; //$NON-NLS-1$
	
	/**
	 * Logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ObjectFactoryImpl.class);
	
	/**
	 * Assistant.
	 */
	ObjectFactoryAssistant assistant;
	
	/**
	 * association table between declaration and implementation type name.
	 */
	AssociationTable<String, String> decl2ImplNames =
		new AssociationTable<String, String>();
	
	/**
	 * association table between declaration and implementation type.
	 */
	AssociationTable<Class<?>, Class<?>> decl2ImplTypes =
		new AssociationTable<Class<?>, Class<?>>();
	
	/**
	 * Maps names of interfaces that are not treated as declaration classes 
	 * with the names of their sub-interface that is treated as declaration 
	 * a classes.
	 * 
	 * This map support the following feature. During setup, it is possible
	 * to define that a declaratio type (usually an interface) will be always
	 * substituted by one of its subtypes. For example, if type Type2 is  a 
	 * sub type of type Type1 it is possible to define that a call to the
	 * method <code>create(Type1.class)</code> will return the same type that
	 * returns the call <code>create(Type2.class)</code>. So instances declared
	 * as Type1, will be always Type2. Type1 and Type 2 can be interfaces, 
	 * abstract or concrete classes.
	 * 
	 */
	HashMap<String, String> replacements = new HashMap<String, String>();	
		
	/**
	 * Properties, used as argument in constructors that take an argument
	 * of type Properties.
	 */
	Properties properties;
	
	/**
	 * Creates a new Factory object.
	 *	  
	 * @param assistant
	 *        Object factory assistant. 
	 * 
	 * 
	 */
	public ObjectFactoryImpl(ObjectFactoryAssistant assistant) {
		super();
		this.assistant = assistant;
		setMappings(assistant.getMappingsFilePath());
	}
	
	/**
	 * Creates a new Factory object using the information defined
	 * in a properties file.
	 * 
	 * The properties file should contain the following properties: 
	 * <li><code>mappingsFilePath</code>: Path to the mappings file</li>
	 * <li><code>nameResolverClass</code>: Class of the nameResolver</li>
	 * <li><code>hasProperties</code>: Indication if the new factory will 
	 * have properties. If this property is true, then 
	 * This constructor will use these properties in order set the 
	 * type mappings and the nameResolver. The properties passed 
	 * as argument to the constructor will be the properties of this
	 * object. </li>
	 * 
	 * @param properties 
	 *        Properties object used as argument for object creation, 
	 *        wherever there is a constructor that receives this argument.
	 *        
	 */
	public ObjectFactoryImpl(Properties properties) {
		this(new ObjectFactoryAssistant(properties));
		String hasProperties = properties.getProperty("hasProperties"); //$NON-NLS-1$
		boolean isHasProperties = StringUtils.string2Bool(hasProperties);
		if (isHasProperties) {
			this.properties = properties;			
		}
	}
	
	public <M> M create(Class<M> type) {
		M fixture = assistant.getFixtureResolver().resolveFixture(type);
		if(fixture != null) {
			return fixture;
		}
		@SuppressWarnings("unchecked")
		Class<M> implementation = (Class<M>)getImplementationType(type);
		return createInstance(implementation);
	}

	public Object create(String declarationTypeName) {
		Class<?> clazz = getImplementationType(declarationTypeName);
		return createInstance(clazz);		
	}
	
	public Class<?> getDeclarationType(Class<?> implementationType) {
		Class<?> decl = decl2ImplTypes.getLeft(implementationType);
		if (decl!=null) {
			return decl;
		}
		decl = decl2ImplTypes.getRight(implementationType);
		if (decl!=null) {
			return implementationType;
		}
		String implementationName = implementationType.getName(); 
		String replacement = replacements.get(implementationName);
		if (replacement!=null) {
			return implementationType;
		}
		String mapped = decl2ImplNames.getLeft(implementationName);
		if (mapped!=null) {
			decl = loadClassThatMustExist(mapped);
			associate(decl, implementationType);
			return decl;
		}		
		String mappedImpl = decl2ImplNames.getRight(implementationName);
		if (mappedImpl!=null) {
			return implementationType;
		}
		
		NameResolver resolver = assistant.getNameResolver();
		if (resolver!=null) {
			String expectedDeclarationName = 
				resolver.getDeclarationName(implementationName);
			decl = loadClass(expectedDeclarationName);
			if (decl!=null) {
				return decl;
			}			
		}
		return implementationType;
	}
	
	public String getDeclarationTypeName(Class<?> implementationType) {
		Class<?> declarationType = getDeclarationType(implementationType);
		return declarationType.getName();		
	}
	
	public Class<?> getImplementationType(Class<?> declarationType) {
		/*
		 * 1. Search if there is already an association of this 
		 *    declaration type with an implementation type.
		 */
		Class<?> implementationType = decl2ImplTypes.getRight(declarationType);
		if (implementationType != null) {
			return implementationType;
		}
		
		/*
		 * 2. Now check if the specified declaration type is registered
		 *    as an implementation type. In this case, the method will
		 *    return the method argument as implementation type.
		 *    This is the case when declaration class A is associated
		 *    with implementation class B. Then a client calls the method
		 *    create(clazz) with argument the class B. In this case,
		 *    B is in reality an implementation class, since A has been
		 *    registered as declaration class.
		 */
		Class<?> possibleDeclarationType = decl2ImplTypes.getLeft(declarationType);
		if (possibleDeclarationType!=null) {
			return declarationType;
		}
		
		/*
		 * 3. There is no association for this type yet, so try to see
		 *    if there has been defined a replacement for this type.
		 *    If there has been defined a replacement, then ignore this
		 *    declaration type, and return the implementation type of the
		 *    replacement.
		 */
		String declarationTypeName = declarationType.getName();
		String replacementTypeName = replacements.get(declarationTypeName);
		if (replacementTypeName!=null) {
			Class<?> replacementType = loadClass(replacementTypeName);
			if (replacementType==null) {
				throw couldNotLoadMappedClass(declarationTypeName, replacementTypeName);
			}
			return getImplementationType(replacementType); 
		}
		
		/*
		 * 4. We didn't find an existing association for the specified 
		 *    declaration type. This means that this is the first time 
		 *    that this ObjectFactory is trying to find the implementation
		 *    type for the specified declaration type. 
		 *    So the implementation type will have to be returned from the
		 *    class composers. 
		 *    Before that, we have to find the intermediate type that will be
		 *    given as input to the class composers.
		 *    If the declaration type is concrete, then this is the intermediate.
		 *    Otherwise, try to find the name of the intermediate class and then
		 *    load it. 
		 */
		Class<?> intermediateType;		
		if (ReflectionUtils.isConcreteClass(declarationType)) {
			intermediateType = declarationType;			
		} else {
			intermediateType = getIntermediateType(declarationTypeName);				
		}
		
		/*
		 * 5. If up to now, we could not find the intermediateType, then use the
		 *    declarationType as intermediateType.
		 *    In case there is a mapped type name, this will be the intermediate type.
		 */
		if (intermediateType==null) {
			intermediateType = declarationType;
		}
		
		/*
		 * 7. Now try to get the implementation class from the composers.
		 */		
		implementationType = newType(intermediateType);
		associate(declarationType, implementationType);
		return implementationType;

	}	
	
	public Class<?> getImplementationType(String declarationTypeName) {
		Class<?> declarationType = loadClass(declarationTypeName);
		if (declarationType==null) {
			throw couldNotLoadClass(declarationTypeName);
		}
		return getImplementationType(declarationType);
	}
	
	public void registerImplementationAsDeclaration(Class<?> declaration) {
		associate(declaration, declaration);
	}
	
	@Override
	public <M> void registerFixture(Class<M> declarationType, M fixture) {
		assistant.getFixtureResolver().registerFixture(declarationType, fixture);
	}
	
	@Override
	public <M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory) {
		assistant.getFixtureResolver().registerFixture(declarationType, fixtureFactory);
	}

	@Override
	public void resetFixtures() {
		assistant.getFixtureResolver().clearFixturesCache();
	}
	
	/**
	 * Creates an instance of the specified class.
	 * 
	 * The method first tries to create the new instance using a
	 * constructor that takes as argument one properties object.
	 * If the class does not have such a constructor, then the
	 * method tries to use a no argument constructor. 
	 * 
	 * @param <M>
	 * @param clazz
	 * 
	 * @return Returns a new instance of the specified class.
	 */
	<M> M createInstance(Class<M> clazz) {
		Constructor<M> constructor=null;
		if (properties!=null) {//Try to use the properties argument constructor.
			try {				
				constructor = clazz.getConstructor(Properties.class);				
			} catch (NoSuchMethodException nse) {
				/* constructor remains null */
			}
		}
		if (constructor!=null) {
			/*
			 * Use the properties arg constructor
			 */
			return ReflectionUtils.newInstance(constructor, properties);
		} else {
			/*
			 * This factory has no properties, or the class does not have 
			 * a properties arg constructor
			 */
			return ReflectionUtils.newInstance(clazz);
		}
	}	
	
	/**
	 * Loads the intermediate type that corresponds to the specified
	 * declaration type name.
	 * 
	 * @param declarationTypeName
	 * 
	 * @return The intermediate type if found, otherwise null.
	 */
	private Class<?> getIntermediateType (String declarationTypeName) {		
		String typeName = decl2ImplNames.getRight(declarationTypeName);
		if (typeName!=null) {
			return loadClassThatMustExist(typeName);
		}
		NameResolver resolver = assistant.getNameResolver();
		if (resolver==null) {
			return null;
		}		
		typeName = resolver.getImplementationName(declarationTypeName);
		return loadClass(typeName);
	}
	
	
	/**
	 * Tries to load a class.
	 *	  
	 * @param typeName 
	 *        Class name.
	 * 
	 * @return Returns the class with the specified name, if a class
	 *         with this name can't be found in the local classpath
	 *         the method will return null. 
	 */
	private static Class<?> loadClass(String typeName) {		
		try {
			return Class.forName(typeName);
		} catch (ClassNotFoundException e) {				
			return null;
		}
	}	
	
	/**
	 * Tries to load a class that must exist.
	 *	  
	 * @param typeName 
	 *        Class name.
	 * 
	 * @return Returns the class with the specified name, if a class
	 *         with this name can't be found in the local classpath
	 *         the method will return null. 
	 */
	Class<?> loadClassThatMustExist(String typeName) {		
		try {
			return Class.forName(typeName);
		} catch (ClassNotFoundException e) {				
			throw couldNotLoadClass(typeName);
		}
	}	
	
	/**
	 * Initializes the Factory with the type associations that
	 * are retrieved from a mappings file.
	 * 
	 * The mappings file contains a list of paths to properties files that
	 * can be found inside the local classpath. Each properties file contains
	 * associations between a declaration type and an implementation type. 
	 * Declaration type is usually an interface and implementation type is the
	 * concrete class that implements it. <br/>
	 * Implementation type can also be a concrete class. In this case, the
	 * corresponding implementation class has to be the same class, or a 
	 * subclass of the declaration class. <br/>
	 * Different properties files can provide different mapping for the same
	 * type. In this case, the last mapping will overwrite any other. This
	 * implies that the order in which the properties files are placed in 
	 * the mappings files catalog, is important.
	 * 
	 * @param mappingsFilePath 
	 *        Path to the file that contains the paths to the mapping files.
	 *         
	 * @throws IOException 
	 * 
	 */
	protected void readMappings(String mappingsFilePath) 
	throws IOException {
		String[] paths = StreamUtils.readResourceFile(mappingsFilePath, true, true);
		if (paths==null) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"Resource ", mappingsFilePath,
					" Could not be found in classpath");
			throw new RuntimeException(msg);
		}
		for (int i = 0; i < paths.length; i++) {
			String path = paths[i].trim();
			if (path.startsWith(REPLACEMENT_IND)) {
				path = path.replaceFirst(REPLACEMENT_IND, StringConstants.EMPTY);
				path = path.trim();
				loadReplacements(path);
			} else {
				loadMappings(path);
			}
		}		
	}
	
	/**
	 * Sets the explicitly defined mappings for this factory.
	 * 
	 * @param mappingsFilePath
	 *        Path to a resource file that contains a list of paths
	 *        to properties files that contain mappings of declaration
	 *        types to implementation types.
	 */
	private void setMappings(String mappingsFilePath) {
		if (!StringUtils.isNullOrBlank(mappingsFilePath)) {
			try {
				readMappings(mappingsFilePath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Loads a mappings file.
	 * 
	 * @param path Path to the mappings file.
	 */
	private void loadMappings(String path) {
		if (path.length()>0) {
			Properties p = loadPropertiesIfAvailable(path);
			CollectionUtils.putPropertiesToAssociationTable	(p, decl2ImplNames);
		}
	}
	
	/**
	 * Loads a replacements file.
	 * 
	 * @param path Path to the replacements file.
	 */
	private void loadReplacements(String path) {
		if (path.length()>0) {
			Properties p = loadPropertiesIfAvailable(path);
			for (Map.Entry<Object, Object> entry : p.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				replacements.put(key.trim(), value.trim());
			}
		}
	}
	
	/**
	 * Loads a properties file resource, if it is available. If unavailable,
	 * a warning is printed. This is not normally acceptable and should be 
	 * considered a fatal error when in production. 
	 * 
	 * @param path
	 *        Resource path.
	 * 
	 * @return loaded properties.
	 */
	@SuppressWarnings("nls")
	private Properties loadPropertiesIfAvailable(String path) {
		try {
			Properties p = CollectionUtils.readProperties(path);
			return p;
		} catch(RuntimeException rtex) {
			if(ExceptionUtils.isCausedBy(rtex, IOException.class)) {
				String msg = StringUtils.concat(
						"Non existant mappings file: ",
						path,
						". This is acceptable for unit tests, but FATAL in every other case and should be investigated.");
				LOGGER.error(msg);
			} else {
				throw rtex;
			}
		}
		return new Properties();
	}
	
	/**
	 * Associates the declaration class with the implementation class.
	 * 
	 * This method is synchronized in order to prevent different threads
	 * from changing existing an association that hasn't yet finished.
	 * 
	 * @param declaration
	 * @param implementation
	 */
	synchronized void associate(Class<?> declaration, Class<?> implementation) {
		Class<?> associated = decl2ImplTypes.getRight(declaration);
		boolean error = associated!=null && associated!=implementation;
		boolean redundant = associated!=null && associated==implementation;
		
		if (error) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
				"Declaration class ", declaration.getName(), 
				" is already associated with class ", associated.getName(),
				" will not associate it with ", implementation.getName());
			throw new RuntimeException(msg);
		}
		
		if(redundant) {
			if (LOGGER.isInfoEnabled()) {
				String msg = "Associating declaration type " //$NON-NLS-1$
					       + declaration.getName()
					       + " with implementation type "  //$NON-NLS-1$
					       + implementation.getName()
					       + " is redundant, as the association exists."; //$NON-NLS-1$
				LOGGER.info(msg);
			}
		} else {
			checkForIllegalMappingOverrideAttempt(decl2ImplTypes.getLeft(implementation), declaration);
			checkForIllegalMappingOverrideAttempt(decl2ImplTypes.getRight(declaration), implementation);
			decl2ImplTypes.associate(declaration, implementation);
			decl2ImplNames.associate(declaration.getName(), implementation.getName());

			if (LOGGER.isInfoEnabled()) {
				String msg = "Associating declaration type " //$NON-NLS-1$
					       + declaration.getName()
					       + " with implementation type "  //$NON-NLS-1$
					       + implementation.getName();
				LOGGER.info(msg);
			}
		}
	}
		
	/**
	 * If an attempt to register a Class with a type name that is already
	 * associated with another Class, this method will restore the initial 
	 * association and throw a RuntimeException.
	 * 
	 * @param existing
	 *        Initial Class associated with the <code>key</code>
	 * @param overriding
	 *        Class attempted to override the association.
	 */
	private void checkForIllegalMappingOverrideAttempt(Class<?> existing, Class<?> overriding) {
		if(existing!=null && existing!=overriding) {
			String msg = overriding.getName() + " type attempted to override "  //$NON-NLS-1$
			+ existing.getName() + ". You may need to declare a type replacement."; //$NON-NLS-1$
			LOGGER.warn(msg);
		}
	}
	
	/**
	 * Actual class creation is performed here. This method is static and synchronized.
	 * All ObjectFactoryImpl instances create new types here. A waiting thread will
	 * find the type already created and return early.
	 * 
	 * @param type
	 * @param ccreator
	 * @return New type.
	 */
	private synchronized static Class<?> createNewType(Class<?> type, ClassCreator ccreator) {
		/*
		 * first check if the type has already been created by the creator.
		 */
		String newClassName = ccreator.runTimeClassName(type.getName());
		Class<?> possibleExistingType = loadClass(newClassName);
		if (possibleExistingType!=null) {
			return possibleExistingType;
		}
		/*
		 * now create the new type.
		 */
		try {
			return ccreator.create(type);			
		} catch (ClassCreationException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Creates a new type using the appropriate creator.
	 * 
	 * @param type
	 * 
	 * @return Returns the new type.
	 */
	private Class<?> newType(Class<?> type) {
		ClassCreator creator = assistant.getCreatorFor(type);
		if (creator==null) {
			throw noSuitableClassCreator(type.getName());
		}
		return createNewType(type, creator);
	}
	
	/**
	 * Creates a new {@link RuntimeException} that indicates that the class
	 * that has been mapped in the mappings file could not be loaded.
	 * 
	 * @param declaration
	 * @param implementation
	 * 
	 * @return Returns a new RuntimeException.
	 */
	@SuppressWarnings("nls")
	RuntimeException couldNotLoadMappedClass(String declaration, String implementation) {
		String msg = StringUtils.concat(
			"Class ", declaration, " is mapped to the class ", 
			implementation, " that could not be loaded ");
		return new RuntimeException(msg);
	}
	
	/**
	 * Creates a new {@link RuntimeException} that indicates that the 
	 * class with the specified name could not be loaded.
	 * 
	 * @param declaration
	 * 
	 * @return Returns a new RuntimeException.
	 */
	@SuppressWarnings("nls")
	RuntimeException couldNotLoadClass(String declaration) {
		String msg = StringUtils.concat(
			"Class ", declaration, " could not be loaded ");
		return new RuntimeException(msg);
	}
	
	/**
	 * Creates a new {@link RuntimeException} that indicates that the 
	 * class with the specified name could not be loaded.
	 * 
	 * @param declaration
	 * 
	 * @return Returns a new RuntimeException.
	 */
	@SuppressWarnings("nls")
	RuntimeException noSuitableClassCreator(String declaration) {
		String msg = StringUtils.concat(
			"There is no suitable class creator to create an implementation for class ", 
			declaration);
		return new RuntimeException(msg);
	}
	
	/**
	 * Logs the factory.
	 * @param title 
	 */
	@SuppressWarnings("nls")
	public void log(String title) {
		if (LOGGER.isDebugEnabled()) {
			String names = "decl2ImplNames:" + decl2ImplNames.toString();
			String types = "decl2ImplTypes:" + decl2ImplTypes.toString();
			String assistantStr = assistant.toString();
			LOGGER.debug("---------");
			LOGGER.debug(title);
			LOGGER.debug(this.getClass().getName());
			LOGGER.debug(assistantStr);
			LOGGER.debug(names);
			LOGGER.debug(types);
			LOGGER.debug("---------");	
		}
	}

}
