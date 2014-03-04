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
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.creators.DefaultFixtureResolver;
import gr.interamerican.bo2.creation.creators.ImplementorForInterfaces;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.resolvers.PredefinedSuffixNameResolver;
import gr.interamerican.bo2.samples.creators.MockClassCreator;
import gr.interamerican.bo2.samples.empty.Empty1;
import gr.interamerican.bo2.samples.empty.IEmpty1Impl1;
import gr.interamerican.bo2.samples.empty.IEmpty2Impl;
import gr.interamerican.bo2.samples.empty.IEmpty3Impl;
import gr.interamerican.bo2.samples.empty.ImplementationDefault0;
import gr.interamerican.bo2.samples.empty.ImplementationDefault0Impl;
import gr.interamerican.bo2.samples.iandi.Type1;
import gr.interamerican.bo2.samples.iandi.Type1Impl;
import gr.interamerican.bo2.samples.iandi.Type1Impl2;
import gr.interamerican.bo2.samples.iandi.Type2;
import gr.interamerican.bo2.samples.iandi.Type2Impl;
import gr.interamerican.bo2.samples.iandi.Type3;
import gr.interamerican.bo2.samples.ibean.IBeanWith2Strings;
import gr.interamerican.bo2.samples.iempty.IEmpty1;
import gr.interamerican.bo2.samples.iempty.IEmpty2;
import gr.interamerican.bo2.samples.iempty.IEmpty3;
import gr.interamerican.bo2.samples.iempty.IEmpty4;
import gr.interamerican.bo2.samples.pib.SamplePib;
import gr.interamerican.bo2.samples.resolvers.MockNameResolver;
import gr.interamerican.bo2.utils.Assertions;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;



/**
 * Unit tests for {@link ObjectFactoryImpl}.
 */
public class TestObjectFactoryImpl {

	/**
	 * Path to properties file for object factory.
	 */
	private static final String OF_WITH_PROPERTIES =
			"/gr/interamerican/rsrc/factories/testbo2creation/of1.properties"; //$NON-NLS-1$

	/**
	 * Path to properties file for object factory.
	 */
	private static final String OF_WITHOUT_PROPERTIES =
			"/gr/interamerican/rsrc/factories/testbo2creation/of2.properties"; //$NON-NLS-1$

	/**
	 * Path to empty mappings file.
	 */
	private static final String EMPTY =
			"/gr/interamerican/rsrc/factories/testbo2creation/empty.txt"; //$NON-NLS-1$

	/**
	 * Properties for mock factory.
	 */
	private static Properties mock;

	/**
	 * Properties.
	 */
	private static Properties with;

	/**
	 * Properties.
	 */
	private static Properties without;

	/**
	 * Object factory for nost tests.
	 */
	ObjectFactoryImpl factory;

	/**
	 * Interface types.
	 */
	@SuppressWarnings("unused")
	private static Class<?>[] interfaceTypes = {
		IEmpty1.class,
		IEmpty2.class,
		IEmpty3.class,
		ImplementationDefault0.class
	};

	/**
	 * Expected implementation types.
	 */
	@SuppressWarnings("unused")
	private static Class<?>[] implementationTypes = {
		IEmpty1Impl1.class, //Implementation0 overrides implementation1.
		IEmpty2Impl.class,
		IEmpty3Impl.class,
		ImplementationDefault0Impl.class
	};





	/**
	 * Test class setup.
	 */
	@SuppressWarnings("nls")
	@BeforeClass
	public static void setupClass() {
		PredefinedSuffixNameResolver.setPredefinedSuffix("Impl");

		with = CollectionUtils.readProperties(OF_WITH_PROPERTIES);
		without = CollectionUtils.readProperties(OF_WITHOUT_PROPERTIES);
		mock = new Properties();
		mock.setProperty("nameResolverClass", MockNameResolver.class.getName());
		mock.setProperty("interfaceImplementor", MockClassCreator.class.getName());
		mock.setProperty("abstractClassImplementor", MockClassCreator.class.getName());
		mock.setProperty("concreteClassEnhancer", MockClassCreator.class.getName());
	}

	/**
	 * convenience class for testing getImplementationType.
	 * 
	 * @param declarationType input declaration type
	 * @param implementationType expected implementation type output.
	 */
	private void testGetImplementationType(Class<?> declarationType, Class<?> implementationType) {
		Class<?> impl = factory.getImplementationType(declarationType);
		Assert.assertEquals(implementationType, impl);
	}

	/**
	 * convenience class for testing getImplementationType.
	 * 
	 * @param declarationType input declaration type
	 * @param implementationType expected implementation type output.
	 */
	private void testGetDeclarationType(Class<?> declarationType, Class<?> implementationType) {
		Class<?> decl = factory.getDeclarationType(implementationType);
		Assert.assertEquals(declarationType, decl);
	}

	/**
	 * convenience class for testing getImplementationType.
	 * 
	 * @param declarationType input declaration type
	 * @param implementationType expected implementation type output.
	 */
	private void testGetImplementationTypeStr(Class<?> declarationType, Class<?> implementationType) {
		Class<?> impl = factory.getImplementationType(declarationType.getName());
		Assert.assertEquals(implementationType, impl);
	}

	/**
	 * Test setup.
	 */
	@Before
	public void setup() {
		factory = new ObjectFactoryImpl(with);
	}



	/**
	 * Tests the constructor that takes three
	 */
	@Test
	public void testConstructor_withAssistant() {
		ObjectFactoryAssistant assistant = new ObjectFactoryAssistant(mock);
		ObjectFactoryImpl of = new ObjectFactoryImpl(assistant);
		Assert.assertEquals(assistant, of.assistant);
		Assert.assertNull(of.properties);
	}

	/**
	 * Tests the constructor that takes a properties object
	 */
	@Test
	public void testConstructor_withPropertiesAndHasPropertiesFalse() {
		ObjectFactoryImpl of = new ObjectFactoryImpl(mock);
		Assert.assertNotNull(of.assistant);
		Assert.assertNull(of.properties);
	}

	/**
	 * Tests the constructor that takes a properties object
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor_withPropertiesAndHasPropertiesTrue() {
		Properties p = new Properties();
		p.putAll(mock);
		p.setProperty("hasProperties", "true");
		p.setProperty("amount", "1000");
		ObjectFactoryImpl of = new ObjectFactoryImpl(p);
		Assert.assertNotNull(of.assistant);
		Assert.assertNotNull(of.properties);
	}

	/**
	 * Tests the constructor that takes a properties object
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor_withPropertiesAndEmptyFile() {
		Properties p = new Properties();
		p.putAll(mock);
		p.setProperty("mappingsFilePath", EMPTY);
		ObjectFactoryImpl of = new ObjectFactoryImpl(p);
		Assert.assertNotNull(of.assistant);
		Assert.assertNull(of.properties);
		Assert.assertEquals(0, of.decl2ImplNames.size());
		Assert.assertEquals(0, of.decl2ImplTypes.size());
		Assert.assertEquals(0, of.replacements.size());
	}

	/**
	 * Tests the constructor that takes a properties object
	 */
	@Test
	public void testConstructor_withPropertiesAndFullMappings() {
		ObjectFactoryImpl of = new ObjectFactoryImpl(with);
		Assert.assertNotNull(of.assistant);
		Assert.assertNotNull(of.properties);
		Assert.assertEquals(3, of.decl2ImplNames.size());
		Assert.assertEquals(0, of.decl2ImplTypes.size());
		Assert.assertEquals(1, of.replacements.size());
		Assert.assertTrue(of.assistant.getFixtureResolver() instanceof DefaultFixtureResolver);
	}

	///////////////////////////////////////////////////////////////////////////////////////



	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetImplementationType_argClass_mappedConcreteImplementation() {
		testGetImplementationType(IEmpty1.class, IEmpty1Impl1.class);
		testGetImplementationType(IEmpty2.class, IEmpty2Impl.class);
		testGetImplementationType(IEmpty3.class, IEmpty3Impl.class);

		testGetImplementationType(IEmpty1Impl1.class, IEmpty1Impl1.class);
		testGetImplementationType(IEmpty2Impl.class, IEmpty2Impl.class);
		testGetImplementationType(IEmpty3Impl.class, IEmpty3Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetImplementationType_argClass_withConcreteClass() {
		testGetImplementationType(Empty1.class, Empty1.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the declaration type
	 * is being replaced by another declaration type.
	 */
	@Test
	public void testGetImplementationType_argClass_withReplace() {
		testGetImplementationType(IEmpty4.class, IEmpty2Impl.class);
		testGetImplementationType(IEmpty2Impl.class, IEmpty2Impl.class);
		testGetImplementationType(IEmpty2.class, IEmpty2Impl.class);
		testGetImplementationType(IEmpty4.class, IEmpty2Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argClass_resolvedConcreteImplementation() {
		testGetImplementationType(Type1.class, Type1Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argClass_resolvedAbstractImplementation() {
		Class<?> impl = factory.getImplementationType(Type2.class);
		Assert.assertTrue(ReflectionUtils.isConcreteClass(impl));
		Assert.assertTrue(Type2Impl.class.isAssignableFrom(impl));

		Class<?> impl2 = factory.getImplementationType(Type2.class);
		Assert.assertSame(impl, impl2);

	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argClass_factoredImplementation() {
		Class<?> impl = factory.getImplementationType(Type3.class);
		Assert.assertTrue(ReflectionUtils.isConcreteClass(impl));
		Assert.assertTrue(Type3.class.isAssignableFrom(impl));

		Class<?> impl2 = factory.getImplementationType(Type3.class);
		Assert.assertSame(impl, impl2);
	}


	/////////////////////////////////////////////////////////////////////////////////////



	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetImplementationType_argStr_mappedConcreteImplementation() {
		testGetImplementationTypeStr(IEmpty1.class, IEmpty1Impl1.class);
		testGetImplementationTypeStr(IEmpty2.class, IEmpty2Impl.class);
		testGetImplementationTypeStr(IEmpty3.class, IEmpty3Impl.class);

		testGetImplementationTypeStr(IEmpty1Impl1.class, IEmpty1Impl1.class);
		testGetImplementationTypeStr(IEmpty2Impl.class, IEmpty2Impl.class);
		testGetImplementationTypeStr(IEmpty3Impl.class, IEmpty3Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetImplementationType_argStr_withConcreteClass() {
		testGetImplementationTypeStr(Empty1.class, Empty1.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the declaration type
	 * is being replaced by another declaration type.
	 */
	@Test
	public void testGetImplementationType_argStr_withReplace() {
		testGetImplementationTypeStr(IEmpty4.class, IEmpty2Impl.class);
		testGetImplementationTypeStr(IEmpty2Impl.class, IEmpty2Impl.class);
		testGetImplementationTypeStr(IEmpty2.class, IEmpty2Impl.class);
		testGetImplementationTypeStr(IEmpty4.class, IEmpty2Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argStr_resolvedConcreteImplementation() {
		testGetImplementationType(Type1.class, Type1Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argStr_resolvedAbstractImplementation() {
		Class<?> impl = factory.getImplementationType(Type2.class.getName());
		Assert.assertTrue(ReflectionUtils.isConcreteClass(impl));
		Assert.assertTrue(Type2Impl.class.isAssignableFrom(impl));

		Class<?> impl2 = factory.getImplementationType(Type2.class.getName());
		Assert.assertSame(impl, impl2);

		Class<?> impl3 = factory.getImplementationType(Type2.class);
		Assert.assertSame(impl, impl3);

	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetImplementationType_argStr_factoredImplementation() {
		Class<?> impl = factory.getImplementationType(Type3.class.getName());
		Assert.assertTrue(ReflectionUtils.isConcreteClass(impl));
		Assert.assertTrue(Type3.class.isAssignableFrom(impl));

		Class<?> impl2 = factory.getImplementationType(Type3.class.getName());
		Assert.assertSame(impl, impl2);

		Class<?> impl3 = factory.getImplementationType(Type3.class);
		Assert.assertSame(impl, impl3);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////





	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetDeclarationType_argClass_mappedConcreteImplementation() {
		testGetDeclarationType(IEmpty1.class, IEmpty1Impl1.class);
		testGetDeclarationType(IEmpty2.class, IEmpty2Impl.class);
		testGetDeclarationType(IEmpty3.class, IEmpty3Impl.class);

		testGetDeclarationType(IEmpty1.class, IEmpty1.class);
		testGetDeclarationType(IEmpty2.class, IEmpty2.class);
		testGetDeclarationType(IEmpty3.class, IEmpty3.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been defined in a types
	 * mapping file.
	 */
	@Test
	public void testGetDeclarationType_argClass_withConcreteClass() {
		testGetDeclarationType(Empty1.class, Empty1.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the declaration type
	 * is being replaced by another declaration type.
	 */
	@Test
	public void testGetDeclarationType_argClass_withReplace() {
		testGetDeclarationType(IEmpty4.class, IEmpty4.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is a concrete class that has been found by the resolver.
	 */
	@Test
	public void testGetDeclarationType_argClass_resolvedConcreteImplementation() {
		testGetDeclarationType(Type1.class, Type1Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetDeclarationType_argClass_resolvedAbstractImplementation() {
		testGetDeclarationType(Type2.class, Type2Impl.class);
	}

	/**
	 * Tests <code>getImplementationType(clazz)</code> when the implementation type
	 * for clazz is an abstract class that has been found by the resolver.
	 */
	@Test
	public void testGetDeclarationType_argClass_factoredImplementation() {
		Class<?> impl3 = factory.getImplementationType(Type3.class);
		testGetDeclarationType(Type3.class, impl3);

		Class<?> impl2 = factory.getImplementationType(Type2.class);
		testGetDeclarationType(Type2.class, impl2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Tests associate.
	 */
	@Test
	public void testAssociate() {
		factory.associate(Type1.class, Type1Impl2.class);
		Class<?> impl = factory.getImplementationType(Type1.class);
		Assert.assertEquals(Type1Impl2.class, impl);
	}

	/**
	 * Tests associate.
	 */
	@Test(expected=RuntimeException.class)
	public void testAssociate_invalid() {
		factory.associate(Type1.class, Type1Impl2.class);
		factory.associate(Type1.class, Type1Impl.class);
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test
	public void testGetDeclarationTypeName() {
		Class<?> decl = factory.getDeclarationType(Type1Impl.class);
		String name = factory.getDeclarationTypeName(Type1Impl.class);
		Assert.assertEquals(decl.getName(), name);
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test
	public void testCouldNotLoadClass() {
		@SuppressWarnings("nls")
		RuntimeException ex = factory.couldNotLoadClass("MyClass");
		Assertions.assertException(ex);
	}

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test
	public void testCouldNotLoadMappedClass() {
		@SuppressWarnings("nls")
		RuntimeException ex = factory.couldNotLoadMappedClass("MyDecl", "MyImpl");
		Assertions.assertException(ex);
	}

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test
	public void testNoSuitableClassCreator() {
		@SuppressWarnings("nls")
		RuntimeException ex = factory.noSuitableClassCreator("MyDecl");
		Assertions.assertException(ex);
	}

	////////////////////////////////////////////////

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testLoadClassThatMustExist() {
		Class<?> type = factory.loadClassThatMustExist("java.lang.String");
		Assert.assertEquals(String.class, type);
	}

	/**
	 * Tests getDeclarationTypeName.
	 */
	@Test(expected=RuntimeException.class)
	@SuppressWarnings({ "nls", "unused" })
	public void testLoadClassThatMustExist_throw() {
		Class<?> type = factory.loadClassThatMustExist("com.foo.bar");
	}

	////////////////////////////////////////////////

	/**
	 * Runs log.
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testLog() {
		factory.log("factory");
	}

	/**
	 * Tests registerImplementationAsDeclaration.
	 * @throws ClassCreationException
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testRegisterImplementationAsDeclaration() throws ClassCreationException {
		ClassCreator creator = new ImplementorForInterfaces() {
			@Override
			protected String getSuffix() {
				return "_specificImplForTest";
			}
		};
		Class<?> clazz = creator.create(IBeanWith2Strings.class);
		factory.registerImplementationAsDeclaration(clazz);

		Class<?> impl = factory.getImplementationType(clazz);
		Assert.assertEquals(clazz, impl);
		Class<?> decl = factory.getDeclarationType(clazz);
		Assert.assertEquals(clazz, decl);

	}

	////////////////////////////////////////////////

	/**
	 * Tests create.
	 */
	@Test()
	public void testCreate_argClass() {
		IBeanWith2Strings bean = factory.create(IBeanWith2Strings.class);
		Assert.assertNotNull(bean);
	}

	/**
	 * Tests create.
	 */
	@Test()
	public void testCreate_argString() {
		IBeanWith2Strings bean = (IBeanWith2Strings)
				factory.create(IBeanWith2Strings.class.getName());
		Assert.assertNotNull(bean);
	}

	////////////////////////////////////////////////

	/**
	 * Tests createInstance.
	 */
	@Test(expected=RuntimeException.class)
	public void testCreateInstance_noAppropriateConstructor() {
		@SuppressWarnings("unused")
		Double d = factory.createInstance(Double.class);
	}

	/**
	 * Tests createInstance.
	 */
	@Test()
	public void testCreateInstance_withAndProperties() {
		ObjectFactoryImpl impl = new ObjectFactoryImpl(with);
		SamplePib bean = impl.createInstance(SamplePib.class);
		Assert.assertNotNull(bean);
		String sample = impl.properties.getProperty("sample"); //$NON-NLS-1$
		Assert.assertEquals(sample, bean.getSample());
	}

	/**
	 * Tests createInstance.
	 */
	@Test()
	public void testCreateInstance_withAndNoProperties() {
		ObjectFactoryImpl impl = new ObjectFactoryImpl(with);
		Empty1 obj = impl.createInstance(Empty1.class);
		Assert.assertNotNull(obj);
	}

	/**
	 * Tests createInstance.
	 */
	@Test()
	public void testCreateInstance_withoutAndNoProperties() {
		ObjectFactoryImpl impl = new ObjectFactoryImpl(without);
		Empty1 obj = impl.createInstance(Empty1.class);
		Assert.assertNotNull(obj);
	}

	/**
	 * Tests createInstance.
	 */
	@Test(expected=RuntimeException.class)
	public void testCreateInstance_withoutAndProperties() {
		ObjectFactoryImpl impl = new ObjectFactoryImpl(without);
		impl.createInstance(SamplePib.class);
	}

	/**
	 * test registerFixture
	 */
	@Test
	public void testRegisterFixture() {
		factory.registerFixture(String.class, StringConstants.EMPTY);
		Assert.assertEquals(StringConstants.EMPTY, factory.create(String.class));
		Assert.assertEquals(StringConstants.EMPTY, factory.assistant.getFixtureResolver().resolveFixture(String.class));
		Assert.assertTrue(factory.create(String.class) == factory.assistant.getFixtureResolver().resolveFixture(String.class));
		
		factory.resetFixtures();
	}

	/**
	 * test resetFixtures
	 */
	@Test
	public void testResetFixtures() {
		ObjectFactoryAssistant fixture = Mockito.mock(ObjectFactoryAssistant.class);
		factory.registerFixture(ObjectFactoryAssistant.class, fixture);
		Assert.assertEquals(fixture, factory.assistant.getFixtureResolver().resolveFixture(ObjectFactoryAssistant.class));

		JavabeanDefinition fixture2 = Mockito.mock(JavabeanDefinition.class);
		ObjectFactory fixtureFactory = Mockito.mock(ObjectFactory.class);
		Mockito.when(fixtureFactory.create(JavabeanDefinition.class)).thenReturn(fixture2);
		factory.registerFixture(JavabeanDefinition.class, fixtureFactory);
		
		Assert.assertEquals(fixture2, factory.assistant.getFixtureResolver().resolveFixture(JavabeanDefinition.class));
		
		factory.resetFixtures();
		Assert.assertNull(factory.assistant.getFixtureResolver().resolveFixture(ObjectFactoryAssistant.class));
		Assert.assertNull(factory.assistant.getFixtureResolver().resolveFixture(JavabeanDefinition.class));
	}

}
