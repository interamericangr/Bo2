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
import gr.interamerican.bo2.creation.FixtureResolver;
import gr.interamerican.bo2.creation.NameResolver;
import gr.interamerican.bo2.creation.creators.MockFixtureResolver;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.PropertiesInitializedBean;

import java.util.Properties;

/**
 * Assistant object of an {@link ObjectFactoryImpl}.
 */
public class ObjectFactoryAssistant 
extends PropertiesInitializedBean {
	
	/**
	 * ClassCreator to create implementation for an interface. 
	 */
	ClassCreator interfaceImplementor;
	
	/**
	 * ClassCreator to create a concrete class that extends an abstract class.
	 */
	ClassCreator abstractClassImplementor;
	
	/**
	 * ClassCreator to update concrete classes.
	 */
	ClassCreator concreteClassEnhancer;
	
	/**
	 * Fixture resolver.
	 */
	FixtureResolver fixtureResolver;
	
	/**
	 * Name resolver.
	 */
	NameResolver nameResolver;
	
	/**
	 * Path to the file that contains the list of mappings files.
	 * If this argument is null or blank, then the new object will not 
	 * have any explicitly defined type mappings, so its mappings will
	 * be based only to the nameResolver.
	 */
	String mappingsFilePath;
	
	/**
	 * Class of interfaceImplementor. 
	 */
	String interfaceImplementorClass;
	
	/**
	 * Class of abstractClassImplementor. 
	 */
	String abstractClassImplementorClass;
	
	/**
	 * Instrumentor to update concrete classes.
	 */
	String concreteClassEnhancerClass;
	
	/**
	 * Name resolver.
	 */
	String nameResolverClass;
	
	/**
	 * Fixture resolver.
	 */
	String fixtureResolverClass;
	
	/**
	 * Creates a new ObjectFactoryAssistant object. 
	 *
	 * @param properties
	 */
	public ObjectFactoryAssistant(Properties properties) {
		super(properties);		
		this.nameResolver = ReflectionUtils.attemptNewInstance(nameResolverClass);
		this.interfaceImplementor = ReflectionUtils.attemptNewInstance(interfaceImplementorClass);
		this.abstractClassImplementor = ReflectionUtils.attemptNewInstance(abstractClassImplementorClass);
		this.concreteClassEnhancer = ReflectionUtils.attemptNewInstance(concreteClassEnhancerClass);
		this.fixtureResolver = ReflectionUtils.attemptNewInstance(fixtureResolverClass);
		this.fixtureResolver = Utils.notNull(this.fixtureResolver, new MockFixtureResolver());
	}
	
	/**
	 * Gets the mappingsFilePath.
	 *
	 * @return Returns the mappingsFilePath
	 */
	public String getMappingsFilePath() {
		return mappingsFilePath;
	}

	/**
	 * Assigns a new value to the mappingsFilePath.
	 *
	 * @param mappingsFilePath the mappingsFilePath to set
	 */
	public void setMappingsFilePath(String mappingsFilePath) {
		this.mappingsFilePath = mappingsFilePath;
	}

	/**
	 * Gets the interfaceImplementor.
	 *
	 * @return Returns the interfaceImplementor
	 */
	public ClassCreator getInterfaceImplementor() {
		return interfaceImplementor;
	}

	/**
	 * Assigns a new value to the interfaceImplementor.
	 *
	 * @param interfaceImplementor the interfaceImplementor to set
	 */
	public void setInterfaceImplementor(ClassCreator interfaceImplementor) {
		this.interfaceImplementor = interfaceImplementor;
	}

	/**
	 * Gets the abstractClassImplementor.
	 *
	 * @return Returns the abstractClassImplementor
	 */
	public ClassCreator getAbstractClassImplementor() {
		return abstractClassImplementor;
	}

	/**
	 * Assigns a new value to the abstractClassImplementor.
	 *
	 * @param abstractClassImplementor the abstractClassImplementor to set
	 */
	public void setAbstractClassImplementor(ClassCreator abstractClassImplementor) {
		this.abstractClassImplementor = abstractClassImplementor;
	}

	/**
	 * Gets the concreteClassEnhancer.
	 *
	 * @return Returns the concreteClassEnhancer
	 */
	public ClassCreator getConcreteClassEnhancer() {
		return concreteClassEnhancer;
	}

	/**
	 * Gets the nameResolver.
	 *
	 * @return Returns the nameResolver
	 */
	public NameResolver getNameResolver() {
		return nameResolver;
	}
	
	/**
	 * Gets the fixtureResolver.
	 *
	 * @return Returns the fixtureResolver
	 */
	public FixtureResolver getFixtureResolver() {
		return fixtureResolver;
	}

	/**
	 * Assigns a new value to the nameResolver.
	 *
	 * @param nameResolver the nameResolver to set
	 */
	public void setNameResolver(NameResolver nameResolver) {
		this.nameResolver = nameResolver;
	}

	/**
	 * Assigns a new value to the concreteClassEnhancer.
	 *
	 * @param concreteClassEnhancer the concreteClassEnhancer to set
	 */
	public void setConcreteClassEnhancer(ClassCreator concreteClassEnhancer) {
		this.concreteClassEnhancer = concreteClassEnhancer;
	}
	
	/**
	 * Gets the appropriate {@link ClassCreator} for the specified class.
	 * 
	 * @param clazz
	 * 
	 * @return if the specified class is concrete, then returns the 
	 *         <code>concreteClassEnhancer</code> else if the specified
	 *         class is abstract returns the <code>abstractClassImplementor</code>
	 *         else returns the <code>interfaceImplementor</code>
	 */
	public ClassCreator getCreatorFor(Class<?> clazz) {
		if (ReflectionUtils.isConcreteClass(clazz)) {
			return concreteClassEnhancer;
		} else if (ReflectionUtils.isAbstractClass(clazz)) {
			return abstractClassImplementor;
		} else {
			return interfaceImplementor;
		}
	}

	

}
