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

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistenceWorkerFactory;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.creation.Bo2Creation;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.ObjectFactoryImpl;
import gr.interamerican.bo2.creation.creators.NewJavabeanClassCreator;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.util.Properties;

import javassist.ClassClassPath;

/**
 * Utility class that serves as a facade hiding the default object factory.
 */
public class Factory {
	
	/**
	 * Active factory.
	 */
	static ObjectFactory currentFactory;
	
	/**
	 * Default factory for persistence workers.
	 */
	static PersistenceWorkerFactory defaultPwFactory; 

	
	static {
		try {
			String definitionPath = Bo2.getDefaultDeployment().
				getDeploymentBean().getPathToDefaultFactoryDefinition();
			Properties defaultFactoryDefinition = CollectionUtils.readProperties(definitionPath);			
			currentFactory = new ObjectFactoryImpl(defaultFactoryDefinition);
			defaultPwFactory =	currentFactory.create(PersistenceWorkerFactory.class);
		} catch (RuntimeException rtex) {
			throw new ExceptionInInitializerError(rtex);
		}
		Bo2Creation.getBo2ClassPool().appendClassPath(new ClassClassPath(Factory.class));
	}	

	/**
	 * Gets the current factory.
	 * 
	 * @return Returns the current factory.
	 */
	public static ObjectFactory getCurrentFactory() {
		return currentFactory;
	}

	/**
	 * Sets the current factory.
	 * 
	 * @param currentFactory New current factory.
	 */
	public static void setCurrentFactory(ObjectFactory currentFactory) {
		Factory.currentFactory = currentFactory;
	}
	
	/**
	 * Creates an implementation instance of the specified declaration type
	 * name.
	 * 
	 * @param declarationTypeName
	 * @return an instance of the implementation type
	 */
	public static Object create(String declarationTypeName) {
		return currentFactory.create(declarationTypeName);
	}

	/**
	 * Creates a {@link PersistenceWorker} for a type of 
	 * {@link PersistentObject}. <br/>
	 * 
	 * @param <M> Type of PersistentObject for which the the
	 *            PersistenceWorker will be created.
	 * @param type The PersistentObject class.
	 * 
	 * @return Returns an PersistenceWorker for the specified type
	 *         of PersistentObject.
	 */
	public static <M extends PersistentObject<?>> 
	PersistenceWorker<M> createPw(Class<M> type) {
		return defaultPwFactory.createPw(type);
	}

	/**
	 * Returns the declaration type name of the specified implementation type
	 * 
	 * @param type
	 *            Type of the implementation type
	 * @return the name of the corresponding declaration type
	 */
	public static String declarationTypeName(Class<?> type) {
		return currentFactory.getDeclarationTypeName(type);
	}
	
	/**
	 * returns the implementation type of the specified declaration type name.
	 * 
	 * @param declarationTypeName
	 * @return returns the implementation type of the specified declaration 
	 *         type name.
	 */
	public static Class<?> getType(String declarationTypeName){
		return currentFactory.getImplementationType(declarationTypeName);
	}
	
	/**
	 * Creates an object of the specified type.
	 * 
	 * @param type
	 *            Type of object to be created.
	 * @param <M> type of object.            
	 * 
	 * @return Returns an object of the specified type.
	 */
	public static <M> M create (Class<M> type) {
		return currentFactory.create(type);
	}
	
	/**
	 * Makes sure that a reference to an object is not null.
	 * 
	 * If value is null, then the method will create a new object,
	 * otherwise it will return the same value.
	 * 
	 * @param type
	 *            Type of object to be created.
	 * @param <M> type of object.            
	 * @param value Value
	 * 
	 * @return Returns an object of the specified type that is never null.
	 */
	public static <M> M nullSafe (M value, Class<M> type) {
		if (value==null) {
			return create(type);
		} else {
			return value;
		}
	}
	

	/**
	 * Gets the default detach strategy of objects of the specified class. <br/>.
	 *   
	 * @param clazz
	 *        Class 
	 *        
	 * @return Returns the detach strategy of the specified object.
	 */	
	public static DetachStrategy getDefaultDetachStrategy (Class<?> clazz) {
		if (!PersistentObject.class.isAssignableFrom(clazz)) {
			return null;
		}
		Class<?> declarationType = currentFactory.getDeclarationType(clazz);
		if (declarationType==null) {
			declarationType = clazz;
		}
		Class<? extends PersistentObject<?>> poClass = Utils.cast(clazz);
		return defaultPwFactory.getDetachStrategy(poClass);		
	}

	/**
	 * Hidden constructor of a utility class.
	 */
	private Factory() {
		/* empty */
	}
	
	/**
	 * Compiles a new java bean class and registers it as declaration
	 * class in the current factory. This method is synchronized. Waiting
	 * threads will find the class already existing.
	 * 
	 * @param className
	 *        Name of class.
	 * @param properties
	 *        Array of pairs containing property name left and property type name right.
	 *        These are the java bean's properties.
	 * @return Returns the class.
	 */
	public static synchronized Class<?> compileJavaBean(String className, VariableDefinition<?>[] properties) {		
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e1) {			
			return compileNewJavaBean(className, properties);
		}
	}
	
	/**
	 * Compiles a new java bean class and registers it as declaration
	 * class in the current factory.
	 * 
	 * @param className
	 *        Name of class.
	 * @param properties
	 *        Array of pairs containing property name left and property type name right.
	 *        These are the java bean's properties.
	 * @return Returns the class.
	 */
	private static Class<?> compileNewJavaBean(String className, VariableDefinition<?>[] properties) {
		NewJavabeanClassCreator creator = new NewJavabeanClassCreator(className, properties);
		try {
			Class<?> clazz = creator.create();
			Factory.currentFactory.registerImplementationAsDeclaration(clazz);
			return clazz;
		} catch (ClassCreationException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	

}
