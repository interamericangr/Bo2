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
package gr.interamerican.bo2.utils.reflect.analyze;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import gr.interamerican.bo2.samples.abstractimpl.AbstractExtendedSampleInterfaceImpl;
import gr.interamerican.bo2.samples.abstractimpl.AbstractSampleInterfaceImpl;
import gr.interamerican.bo2.samples.anno.Anno;
import gr.interamerican.bo2.samples.bean.Address;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWithReadOnlyAndWriteOnly;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.enums.Vehicle;
import gr.interamerican.bo2.samples.ibean.IBeanWithAllTypesOfPropertyNames;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.samples.ibean.IdentifiedBean;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.beans.Array;
import gr.interamerican.bo2.utils.beans.Range;
import gr.interamerican.bo2.utils.reflect.AccessorUtils;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

/**
 * Unit tests for {@link TypeAnalysis}.
 */
@SuppressWarnings("nls")
public class TestTypeAnalysis {
	/**
	 * Count of non private instance methods on class object.
	 */
	private static final int OBJECT_METHODS_COUNT = 11;
	
	/**
	 * Count of methods on class object.
	 */
	private static final int COMPARABLE_METHODS_COUNT = Comparable.class.getMethods().length;
	
	/**
	 * Unit test for handleGetter.
	 */	
	@Test
	public void testHandleMethod_abstractGetter() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = IBeanWithIdAndName.class;
		Method getter = AccessorUtils.getGetter("beanName", String.class, analysis.clazz);
		
		analysis.handleMethod(getter);
		assertTrue(analysis.abstractGetters.contains(getter));
		assertTrue(analysis.concreteGetters.isEmpty());
		assertTrue(analysis.abstractSetters.isEmpty());
		assertTrue(analysis.concreteSetters.isEmpty());
		assertTrue(analysis.abstractMethods.isEmpty());
		assertTrue(analysis.concreteMethods.isEmpty());
		
		Array key = new Array("beanName", String.class);
		BeanPropertyDefinition<?> bpd = analysis.allProperties.get(key);
		assertNotNull(bpd);
		assertNotNull(bpd.getGetter());
	}
	
	/**
	 * Unit test for handleGetter.
	 */	
	@Test
	public void testHandleMethod_concreteGetter() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = IBeanWithIdAndNameImpl.class;
		Method getter = AccessorUtils.getGetter("beanName", String.class, analysis.clazz);
		analysis.handleMethod(getter);
		assertTrue(analysis.abstractGetters.isEmpty());
		assertTrue(analysis.concreteGetters.contains(getter));
		assertTrue(analysis.abstractSetters.isEmpty());
		assertTrue(analysis.concreteSetters.isEmpty());
		assertTrue(analysis.abstractMethods.isEmpty());
		assertTrue(analysis.concreteMethods.isEmpty());
		Array key = new Array("beanName",  String.class);
		BeanPropertyDefinition<?> bpd = analysis.allProperties.get(key);
		assertNotNull(bpd);
		assertNotNull(bpd.getGetter());
	}
	
	/**
	 * Unit test for handleSetter.
	 */	
	@Test
	public void testHandleMethod_abstractSetter() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = IBeanWithIdAndName.class;
		Method setter = AccessorUtils.getSetter("beanName", String.class, analysis.clazz);
		analysis.handleMethod(setter);
		assertTrue(analysis.abstractGetters.isEmpty());
		assertTrue(analysis.concreteGetters.isEmpty());		
		assertTrue(analysis.abstractSetters.contains(setter));
		assertTrue(analysis.concreteSetters.isEmpty());
		assertTrue(analysis.abstractMethods.isEmpty());
		assertTrue(analysis.concreteMethods.isEmpty());
		Array key = new Array("beanName", String.class); // ???
		BeanPropertyDefinition<?> bpd = analysis.allProperties.get(key);
		assertNotNull(bpd);
		assertNotNull(bpd.getSetter());

	}
	
	/**
	 * Unit test for handleSetter.
	 */	
	@Test
	public void testHandleMethod_concreteSetter() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = IBeanWithIdAndNameImpl.class;
		Method setter = AccessorUtils.getSetter("beanName", String.class, analysis.clazz);
		analysis.handleMethod(setter);
		assertTrue(analysis.abstractGetters.isEmpty());
		assertTrue(analysis.concreteGetters.isEmpty());		
		assertTrue(analysis.abstractSetters.isEmpty());
		assertTrue(analysis.concreteSetters.contains(setter));
		assertTrue(analysis.abstractMethods.isEmpty());
		assertTrue(analysis.concreteMethods.isEmpty());
		Array key = new Array("beanName", String.class);
		BeanPropertyDefinition<?> bpd = analysis.allProperties.get(key);
		assertNotNull(bpd);
		assertNotNull(bpd.getSetter());

	}
	
	/**
	 * Unit test for handleSetter.
	 */	
	@Test
	public void testHandleMethod_GetterAndSetter() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = IBeanWithIdAndNameImpl.class;
		Method setter = AccessorUtils.getSetter("beanName", String.class, analysis.clazz);
		analysis.handleMethod(setter);	
		Method getter = AccessorUtils.getGetter("beanName", String.class, analysis.clazz);
		analysis.handleMethod(getter);	
		Array key = new Array("beanName", String.class);
		BeanPropertyDefinition<?> bpd = analysis.allProperties.get(key);
		assertNotNull(bpd);
		assertNotNull(bpd.getGetter());
		assertNotNull(bpd.getSetter());
		

	}
	
	/**
	 * Unit test for handleSetter.
	 */	
	@Test
	public void testHandleMethod_abstractNoAccessor() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = AbstractSampleInterfaceImpl.class;
		Method method = ReflectionUtils.getMethodByUniqueName("someMethod", analysis.clazz);
		analysis.handleMethod(method);
		assertTrue(analysis.abstractGetters.isEmpty());
		assertTrue(analysis.concreteGetters.isEmpty());		
		assertTrue(analysis.abstractSetters.isEmpty());
		assertTrue(analysis.concreteSetters.isEmpty());
		assertTrue(analysis.abstractMethods.contains(method));
		assertTrue(analysis.concreteMethods.isEmpty());
	}
	
	/**
	 * Unit test for handleSetter.
	 */	
	@Test
	public void testHandleMethod_concreteNoAccessor() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = AbstractSampleInterfaceImpl.class;
		Method method = ReflectionUtils.getMethodByUniqueName("toString", analysis.clazz);
		analysis.handleMethod(method);
		assertTrue(analysis.abstractGetters.isEmpty());
		assertTrue(analysis.concreteGetters.isEmpty());		
		assertTrue(analysis.abstractSetters.isEmpty());
		assertTrue(analysis.concreteSetters.isEmpty());
		assertTrue(analysis.abstractMethods.isEmpty());
		assertTrue(analysis.concreteMethods.contains(method));
	}
	
	
	/**
	 * Unit test for constructor on concrete class.
	 */	
	@Test
	public void testConstructor_onConcreteClass() {
		TypeAnalysis analysis = new TypeAnalysis(BeanWithReadOnlyAndWriteOnly.class);
		assertEquals(BeanWithReadOnlyAndWriteOnly.class, analysis.clazz);
		assertEquals(0, analysis.getAbstractGetters().size());
		assertEquals(0,analysis.getAbstractSetters().size());
		assertEquals(0,analysis.getAbstractMethods().size());
		
		assertEquals(3, analysis.getConcreteGetters().size());	
		assertEquals(4, analysis.getConcreteSetters().size());
		assertEquals(OBJECT_METHODS_COUNT + 1, analysis.concreteMethods.size());		
		
		assertEquals(1,analysis.getOddProperties().size());		
		assertEquals(1, analysis.getOddPropertiesNames().size());
		
		assertEquals(5,analysis.allProperties.size());
		assertEquals(1, analysis.oddPropertiesByName.size());
		
		BeanPropertyDefinition<?> odd = analysis.oddProperties.iterator().next();
		assertEquals("name", odd.getName());
		assertNull(odd.getGetter());
		
		assertEquals(1, analysis.getAllFieldsByName("name").size());
		
		assertNotNull(analysis.annotatedFields);
		assertEquals(3, analysis.annotatedFields.size());		
	}
	
	/**
	 * Unit test for constructor on interface.
	 */	
	@Test
	public void testConstructor_onInterface() {
		TypeAnalysis analysis = new TypeAnalysis(IBeanWithIdAndName.class);
		assertEquals(IBeanWithIdAndName.class, analysis.clazz);
		assertEquals(2, analysis.getAbstractGetters().size());
		assertEquals(2,analysis.getAbstractSetters().size());
		assertEquals(0,analysis.getAbstractMethods().size());
		
		assertEquals(0, analysis.getConcreteGetters().size());	
		assertEquals(0, analysis.getConcreteSetters().size());
		assertEquals(0, analysis.concreteMethods.size());		
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		
		assertNull(analysis.getAllFieldsByName("beanName"));
		
		assertEquals(2,analysis.allProperties.size());
		assertEquals(0, analysis.oddPropertiesByName.size());
		
		assertNotNull(analysis.annotatedFields);
		assertEquals(0, analysis.annotatedFields.size());
	}
	
	/**
	 * Unit test for constructor on interface.
	 */	
	@Test
	public void testConstructor_onInterfaceWithAllTypesOfPropertyNames() {
		TypeAnalysis analysis = new TypeAnalysis(IBeanWithAllTypesOfPropertyNames.class);
		assertEquals(IBeanWithAllTypesOfPropertyNames.class, analysis.clazz);
		assertEquals(4, analysis.getAbstractGetters().size());
		assertEquals(4,analysis.getAbstractSetters().size());
		assertEquals(0,analysis.getAbstractMethods().size());
		
		assertEquals(0, analysis.getConcreteGetters().size());	
		assertEquals(0, analysis.getConcreteSetters().size());
		assertEquals(0, analysis.concreteMethods.size());		
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		
		assertNull(analysis.getAllFieldsByName("beanName"));
		
		assertEquals(4,analysis.allProperties.size());
		assertEquals(0, analysis.oddPropertiesByName.size());
		
		assertNotNull(analysis.annotatedFields);
		assertEquals(0, analysis.annotatedFields.size());
	}
	
	
	
	/**
	 * Unit test for constructor on concrete class.
	 */	
	@Test
	public void testConstructor_onAbstractClass() {
		TypeAnalysis analysis = new TypeAnalysis(AbstractSampleInterfaceImpl.class);
		assertEquals(AbstractSampleInterfaceImpl.class, analysis.clazz);
		assertEquals(5, analysis.getAbstractGetters().size());
		assertEquals(6,analysis.getAbstractSetters().size());
		assertEquals(2,analysis.getAbstractMethods().size());
		
		assertEquals(1, analysis.getConcreteGetters().size());	
		assertEquals(0, analysis.getConcreteSetters().size());
		assertEquals(OBJECT_METHODS_COUNT + 1, analysis.concreteMethods.size());		
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		
		assertNull(analysis.getAllFieldsByName("field1"));
		
		assertEquals(6,analysis.allProperties.size());
		assertEquals(0, analysis.oddPropertiesByName.size());
		
	}
	
	/**
	 * Unit test for constructor on concrete class.
	 */	
	@Test
	public void testConstructor_onExtendedAbstractClass() {
		TypeAnalysis analysis = new TypeAnalysis(AbstractExtendedSampleInterfaceImpl.class);
		assertEquals(AbstractExtendedSampleInterfaceImpl.class, analysis.clazz);
		assertEquals(5, analysis.getAbstractGetters().size());
		assertEquals(5,analysis.getAbstractSetters().size());
		assertEquals(2,analysis.getAbstractMethods().size());
		
		assertEquals(1, analysis.getConcreteGetters().size());	
		assertEquals(1, analysis.getConcreteSetters().size());
		assertEquals(OBJECT_METHODS_COUNT + 1, analysis.concreteMethods.size());		
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		
		assertNull(analysis.getAllFieldsByName("field1"));
		
		assertEquals(6,analysis.allProperties.size());
		assertEquals(0, analysis.oddPropertiesByName.size());
		
	}
	
	/**
	 * Unit test for analyzeFields() and for the get methods
	 * that retrieve information generated by analyzeFields()..
	 */	
	@Test
	public void testAnalyzeFields() {
		TypeAnalysis analysis = new TypeAnalysis();
		analysis.clazz = BeanWithReadOnlyAndWriteOnly.class;

		analysis.analyzeFields();
		assertEquals(3, analysis.annotatedFields.size());
		Set<Field> allSupw = analysis.getAnnotated(Anno.class);
		assertEquals(1, allSupw.size());
		
		List<Field> stateSupw = analysis.getAnnotated(Anno.class, "state");
		assertEquals(1, stateSupw.size());
		
		Field state1 = analysis.getFirstAnnotated(Anno.class, "state");
		assertNotNull(state1);
		
		Field state2 = analysis.getFirstFieldByName("state");
		assertNotNull(state2);
		
		List<Field> allState = analysis.getAllFieldsByName("state");
		assertEquals(1, allState.size());
		
		Set<Field> all = analysis.getAllFields();
		assertEquals(4, all.size());
	}
	
	/**
	 * Unit test for isSerializable.
	 */
	@Test
	public void testIsSerializable() {
		TypeAnalysis analysis1 = new TypeAnalysis();
		analysis1.clazz = ArrayList.class;
		assertTrue(analysis1.isSerializable());	
		
		TypeAnalysis analysis2 = new TypeAnalysis();
		analysis2.clazz = InputStream.class;
		assertFalse(analysis2.isSerializable());	
		
	}
	
	/**
	 * Unit test for getSerialVersionUniqueId.
	 */
	@Test
	public void testGetSerialVersionUniqueId() {
		TypeAnalysis analysis1 = new TypeAnalysis();
		analysis1.clazz = Address.class;
		Long l = analysis1.getSerialVersionUniqueId();
		assertEquals(Long.valueOf(3L), l);	
		
		TypeAnalysis analysis2 = new TypeAnalysis();
		analysis2.clazz = IBeanWithIdAndNameImpl.class;
		Long n = analysis2.getSerialVersionUniqueId();
		assertNull(n);
	}
	
	
	/**
	 * Unit test for getNamesOfProperties.
	 */
	@Test
	public void testGetNamesOfProperties() {
		TypeAnalysis analysis = new TypeAnalysis(BeanWith1Field.class);
		Set<String> properties = analysis.getNamesOfProperties();
		assertEquals(1, properties.size());
		assertTrue(properties.contains("field2"));
	}
	
	/**
	 * Unit test for getNamesOfReadWriteProperties.
	 */
	@Test
	public void testGetNamesOfReadWriteProperties() {
		TypeAnalysis analysis = new TypeAnalysis(BeanWith1Field.class);
		Set<String> properties = analysis.getNamesOfReadWriteProperties();
		assertEquals(1, properties.size());
		assertTrue(properties.contains("field2"));
	}
	
	/**
	 * Unit test for constructor on concrete class.
	 */	
	@Test
	public void testConstructor_onConcreteTypeThatOverridesItsSupertype() {
		TypeAnalysis analysis = new TypeAnalysis(Range.class);
		
		assertEquals(0, analysis.getAbstractGetters().size());
		assertEquals(0,analysis.getAbstractSetters().size());
		assertEquals(0,analysis.getAbstractMethods().size());

		assertEquals(3, analysis.getConcreteGetters().size()); //getLeft(), getRight(), isPoint()
		assertEquals(2,analysis.getConcreteSetters().size());  //setLeft(l), setRight(r)
		int methodsCount = OBJECT_METHODS_COUNT + COMPARABLE_METHODS_COUNT + 6; //contains x 2, overlapsWith x 2, intersection, remainder
		
		for(Method m : analysis.concreteMethods) {
			System.out.println(m);
		}
		
		assertEquals(methodsCount, analysis.concreteMethods.size());		
		
		assertEquals(3,analysis.allProperties.size()); //left,right,point
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		assertEquals(0, analysis.oddPropertiesByName.size());
	}
	
	/**
	 * The type is an interface that has the id property declared by more
	 * than one interfaces.
	 */
	@Test
	public void testConstructor_samePropertyDeclaredInDifferentInterfaces() {
		TypeAnalysis analysis = new TypeAnalysis(IdentifiedBean.class);
		
		assertEquals(3, analysis.getAbstractGetters().size());
		assertEquals(3,analysis.getAbstractSetters().size());
		assertEquals(1,analysis.getAbstractMethods().size());

		assertEquals(0, analysis.getConcreteGetters().size());
		assertEquals(0,analysis.getConcreteSetters().size());	
		assertEquals(0, analysis.concreteMethods.size());		
		
		assertEquals(3,analysis.allProperties.size());
		
		assertEquals(0,analysis.getOddProperties().size());		
		assertEquals(0, analysis.getOddPropertiesNames().size());
		assertEquals(0, analysis.oddPropertiesByName.size());
		
	}

	/**
	 * Tests a corner case that happens on non eclipse compiler
	 */
	@Test
	public void testCornerCaseDefaultCompiler() {
		TypeAnalysis analysis = new TypeAnalysis(Vehicle.CAR.getClass());
		BeanPropertyDefinition<?> property = analysis.getFirstPropertyByName("id");
		assertNotNull(property);
		assertNotNull(property.getGetter());
	}
}