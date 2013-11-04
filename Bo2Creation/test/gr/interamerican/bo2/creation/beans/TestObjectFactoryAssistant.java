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
import gr.interamerican.bo2.creation.creators.MockFixtureResolver;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithIdAndName;
import gr.interamerican.bo2.samples.creators.MockClassCreator;
import gr.interamerican.bo2.samples.iempty.IEmpty1;
import gr.interamerican.bo2.samples.resolvers.MockNameResolver;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ObjectFactoryAssistant}.
 */
public class TestObjectFactoryAssistant {
	/**
	 * Test for the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor() {
		Properties p = new Properties();
		p.setProperty("nameResolverClass", MockNameResolver.class.getName());
		p.setProperty("interfaceImplementorClass", MockClassCreator.class.getName());
		p.setProperty("mappingsFilePath", "/com/foo.txt");
		ObjectFactoryAssistant assistant = new ObjectFactoryAssistant(p);
		Assert.assertTrue(assistant.getNameResolver() instanceof MockNameResolver);
		Assert.assertTrue(assistant.getInterfaceImplementor() instanceof MockClassCreator);
		Assert.assertTrue(assistant.getFixtureResolver() instanceof MockFixtureResolver);
		Assert.assertEquals(assistant.getMappingsFilePath(), "/com/foo.txt");
		Assert.assertNull(assistant.getConcreteClassEnhancer());
		Assert.assertNull(assistant.getAbstractClassImplementor());
	}
	
	/**
	 * Test for the constructor.
	 */	
	@Test
	public void testGetCreatorFor() {		
		ObjectFactoryAssistant assistant = new ObjectFactoryAssistant(new Properties());
		assistant.concreteClassEnhancer = new MockClassCreator();
		assistant.interfaceImplementor = new MockClassCreator();
		assistant.abstractClassImplementor = new MockClassCreator();
		
		ClassCreator creatorForConcrete = assistant.getCreatorFor(String.class);
		Assert.assertEquals(assistant.concreteClassEnhancer, creatorForConcrete);
		
		ClassCreator creatorForAbstract = assistant.getCreatorFor(AbstractBeanWithIdAndName.class);
		Assert.assertEquals(assistant.abstractClassImplementor, creatorForAbstract);
		
		ClassCreator creatorForInterface = assistant.getCreatorFor(IEmpty1.class);
		Assert.assertEquals(assistant.interfaceImplementor, creatorForInterface);		
	}
	
	/**
	 * Test for the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetters() {		
		ObjectFactoryAssistant assistant = new ObjectFactoryAssistant(new Properties());
		assistant.concreteClassEnhancer = new MockClassCreator();
		assistant.interfaceImplementor = new MockClassCreator();
		assistant.abstractClassImplementor = new MockClassCreator();
		assistant.nameResolver = new MockNameResolver();
		assistant.mappingsFilePath = "path.txt";
		
		Assert.assertEquals(assistant.concreteClassEnhancer, assistant.getConcreteClassEnhancer());		
		Assert.assertEquals(assistant.abstractClassImplementor, assistant.getAbstractClassImplementor());
		Assert.assertEquals(assistant.interfaceImplementor, assistant.getInterfaceImplementor());		
		Assert.assertEquals(assistant.nameResolver, assistant.getNameResolver());
		Assert.assertEquals(assistant.mappingsFilePath, assistant.getMappingsFilePath());
	}
	
	/**
	 * Test for the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetters() {		
		ObjectFactoryAssistant assistant = new ObjectFactoryAssistant(new Properties());
		ClassCreator concreteClassEnhancer = new MockClassCreator();
		ClassCreator interfaceImplementor = new MockClassCreator();
		ClassCreator abstractClassImplementor = new MockClassCreator();
		NameResolver nameResolver = new MockNameResolver();
		String mappingsFilePath = "path.txt";
		
		assistant.setConcreteClassEnhancer(concreteClassEnhancer);
		assistant.setInterfaceImplementor(interfaceImplementor);
		assistant.setAbstractClassImplementor(abstractClassImplementor);
		assistant.setNameResolver(nameResolver);
		assistant.setMappingsFilePath(mappingsFilePath);
		
		Assert.assertEquals(concreteClassEnhancer,assistant.concreteClassEnhancer);		
		Assert.assertEquals(abstractClassImplementor,assistant.abstractClassImplementor);
		Assert.assertEquals(interfaceImplementor,assistant.interfaceImplementor);		
		Assert.assertEquals(nameResolver,assistant.nameResolver);
		Assert.assertEquals(mappingsFilePath,assistant.mappingsFilePath);
	}

}
