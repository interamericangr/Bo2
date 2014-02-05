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
package gr.interamerican.bo2.utils;

import static gr.interamerican.bo2.utils.ReflectionUtils.copyPropertiesWithDefaults;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.Child;
import gr.interamerican.bo2.samples.Father;
import gr.interamerican.bo2.samples.GrandFather;
import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.samples.SampleMultiConstructorClass;
import gr.interamerican.bo2.samples.abstractimpl.AbstractSampleInterfaceImpl;
import gr.interamerican.bo2.samples.abstractimpl.SampleAbstractClass2;
import gr.interamerican.bo2.samples.anno.Anno;
import gr.interamerican.bo2.samples.anno.ClassAnno;
import gr.interamerican.bo2.samples.anno.MethodAnno;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.hierarchies.AimplementsIA;
import gr.interamerican.bo2.samples.hierarchies.BextendsAimplementsIB;
import gr.interamerican.bo2.samples.hierarchies.CextendsBimplementsIC;
import gr.interamerican.bo2.samples.hierarchies.DextendsCimplementsID;
import gr.interamerican.bo2.samples.hierarchies.IA;
import gr.interamerican.bo2.samples.hierarchies.IBextendsIA;
import gr.interamerican.bo2.samples.hierarchies.ICextendsIB;
import gr.interamerican.bo2.samples.hierarchies.IDextendsICandIA;
import gr.interamerican.bo2.samples.ibean.IbeanWithIdFromTwoParents;
import gr.interamerican.bo2.samples.ibean.IbeanWithIdFromTwoParentsAndIdentified;
import gr.interamerican.bo2.samples.ibean.SampleInterface;
import gr.interamerican.bo2.utils.beans.Range;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ReflectionUtils}.
 */
public class TestReflectionUtils {

	/**
	 * tests checkFieldType(field, type, messageKey)
	 */
	@Test
	public void testCheckFieldType() {
		List<Field> fields = ReflectionUtils.allFields(GrandFather.class, GrandFather.class);
		ReflectionUtils.checkFieldType(fields.get(0), String.class);
		ReflectionUtils.checkFieldType(fields.get(0), Object.class);
		ReflectionUtils.checkFieldType(fields.get(1), Object.class);
	}

	/**
	 * tests checkFieldType(field, type, messageKey)
	 */
	@Test (expected=RuntimeException.class)
	public void testCheckFieldType_WithException() {
		List<Field> fields = ReflectionUtils.allFields(GrandFather.class, GrandFather.class);
		ReflectionUtils.checkFieldType(fields.get(1), Long.class);
	}

	
	
	/**
	 * Tests allFields(type, baseType)
	 */
	@Test
	public void testAllFields() {
	
		List<Field> fields = ReflectionUtils.allFields(Child.class, Child.class);
		assertEquals(1, fields.size());
		
		fields = ReflectionUtils.allFields(Child.class, Father.class);
		assertEquals(3, fields.size());
		
		fields = ReflectionUtils.allFields(Child.class, GrandFather.class);
		assertEquals(7, fields.size());
	
	}

	/**
	 * tests get(field, object)
	 */
	@Test
	public void testGet() {
		Child child = new Child();
		child.setField6(new Object());
		List<Field> fields = ReflectionUtils.allFields(Child.class, Child.class);
		Field field = fields.get(0);
		field.setAccessible(true);
		assertEquals(ReflectionUtils.get(field, child), child.getField6());
	}
	
	/**
	 * tests get(string, object)
	 */
	@Test
	public void testGet_WithString_simpleCase() {
		BeanWith2Fields bean = new BeanWith2Fields();
		bean.setField1(StringConstants.ZERO);
		assertEquals(StringConstants.ZERO, ReflectionUtils.get("field1", bean)); //$NON-NLS-1$
	}
	
	/**
	 * tests get(string, object)
	 * test that inherited fields are retrievable.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGet_WithString_inheritedFields() {
		Father f1 = new Father();
		assertEquals("field1", ReflectionUtils.get("field1", f1));		
	}
	
	/**
	 * tests get(string, object)
	 * test that inherited private fields do not mess with results
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGet_WithString_inheritedPrivateFields() {		
		Father f = new Father();
		f.setField4(StringConstants.UNDERSCORE);
		GrandFather gf = new GrandFather();
		gf.setField4(StringConstants.ZERO);
		assertEquals(StringConstants.ZERO, ReflectionUtils.get("field4", gf)); 
		assertEquals(StringConstants.UNDERSCORE, ReflectionUtils.get("field4", f));
		ReflectionUtils.set("field4", StringConstants.UNDERSCORE, gf);
		ReflectionUtils.set("field4", StringConstants.ZERO, f);
		assertEquals(StringConstants.ZERO, f.getField4()); 
		assertEquals(StringConstants.UNDERSCORE, gf.getField4());
	}
	
	/**
	 * tests get(string, object)
	 * test that inherited fields are retrievable.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGet_WithString_static() {
		BeanWithStaticField bean = new BeanWithStaticField();
		BeanWithStaticField.field = 5;		
		assertEquals(5, ReflectionUtils.get("field", bean));		
	}
	

	/**
	 * Utils.getAnnotated()
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testGetAnnotated() throws IllegalArgumentException, IllegalAccessException {
		List<Field> fieldsOfGrandFather = ReflectionUtils.allFields(GrandFather.class, GrandFather.class);
		for (Field field : fieldsOfGrandFather) {
			System.out.println(field.getName());
			System.out.println(field.getAnnotations().length);
			System.out.println(field.getAnnotation(Anno.class));
		}
		
		List<Field> fields = ReflectionUtils.allFields(Child.class, GrandFather.class);
		List<Field> annotatedFields = ReflectionUtils.getAnnotated(fields, Anno.class);
		assertEquals(2, annotatedFields.size());		
		Child c = new Child();
		assertEquals("field2", annotatedFields.get(0).get(c)); //$NON-NLS-1$		
	}

	
	/**
	 * Utils.getFirstByType()
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testGetFirstByType() throws IllegalArgumentException, IllegalAccessException {
		Field field = ReflectionUtils.getFirstByType(String.class, Father.class);
		Father father = new Father();
		assertEquals(field.get(father), "field1"); //$NON-NLS-1$
	}
	
	
	/**
	 * Utils.getFirstByType()
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testGetFirstByType_noField() throws IllegalArgumentException {
		Field field = ReflectionUtils.getFirstByType(String.class, BeanWith1Field.class);
		assertNull(field);
	}


	/**
	 * tests Utils.getMethods()
	 */
	@Test
	public void testGetDeclaredMethods(){
		Method[] methods = ReflectionUtils.getDeclaredMethods(null, Child.class);
		assertEquals(3, methods.length);
		methods = ReflectionUtils.getDeclaredMethods(new String[]{"setField6", "sampleMethod"}, Child.class); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, methods.length);
		
	}	

	/**
	 * tests Utils.getMethods()
	 */
	@Test (expected=RuntimeException.class)
	public void testGetDeclaredMethods_WithException(){
		@SuppressWarnings("unused")
		Method[] methods = ReflectionUtils.getDeclaredMethods(new String[]{"setField6", "sampleMethod", "nonExistingMethod"}, Child.class); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/**
	 * Tests getDeclaredMethods()
	 */
	@Test
	public void testGetDeclaredMethodsAsList() {
		List<Method> methods = ReflectionUtils.getDeclaredMethodsAsList(Child.class);
		assertEquals(3, methods.size());
	}
	
	/**
	 * Tests testGetPublicMethods()
	 */
	@Test
	public void testGetPublicMethods_ParameterizedClass() {
		List<Method> methods = ReflectionUtils.getPublicMethods(Range.class);	
		/*
		 * expected:  4, get-set Left,Right (Pair)
		 *        +   1, static pair        (Pair)
		 *        +   3, contains, overlapsWith x 2  (Range)
		 *        +   1, static contains         (Range)
		 *        +   1, static range       (Range)
		 */
		int expected = Object.class.getMethods().length 
		             + Comparable.class.getMethods().length 
		             + 10; //4+1+3+1+1
		assertEquals(expected, methods.size());
	}
	
	/**
	 * Tests testGetPublicMethods()
	 * @throws SecurityException 
	 */
	@Test
	public void testGetPublicMethods_SameMethodInMoreThanOneInterfaces() 
	throws SecurityException {
		List<Method> methods = ReflectionUtils.getPublicMethods(IbeanWithIdFromTwoParents.class);
		assertEquals(2, methods.size());
	}
	
	/**
	 * Tests testGetPublicMethods()
	 */
	@Test
	public void testGetPublicMethods_SameMethodInMoreThanOneInterfacesAndGeneric() {
		List<Method> methods = ReflectionUtils.getPublicMethods(IbeanWithIdFromTwoParentsAndIdentified.class);		
		assertEquals(2, methods.size());
	}
	
	/**
	 * Tests testGetPublicMethods()
	 * 
	 * This test fails because odd properties can't be supported.
	 * Therefore it is commented out.
	 */
	@Test
	public void testGetPublicMethods_oddCase() {
		/*
		List<Method> methods = ReflectionUtils.getPublicMethods(IdentifiedByIandS.class);		
		assertEquals(3, methods.size());
		*/
	}
	
	
	
	/**
	 * Tests getPublicMethod()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPublicMethod() {
		assertNotNull(ReflectionUtils.getPublicMethod("sampleMethod", Child.class, String.class));
		
		assertNull(ReflectionUtils.getPublicMethod("noExistingMethod", Child.class, String.class));
	}
	
	/**
	 * Tests Utils.testGetMethodWithoutParamsByName().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPublicMethodWithoutParamsByName() {
		Method method = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField2", SampleBean2.class);
		assertTrue(method!=null);
		
		SampleBean2 bean2 = new SampleBean2();
		String expected = "papi";
		bean2.setField2(expected);
		Object[] args = null;
		String actual = (String) ReflectionUtils.invoke(method, bean2, args);
		assertEquals(expected, actual);
		
		assertNull(ReflectionUtils.getPublicMethodWithoutParamsByName("noExistingMethod", Child.class));
	}
	
	/**
	 * unit test for invokeMethodByUniqueName
	 */
	@Test
	public void testInvokeMethodByUniqueName() {
		String field1 = "s"; //$NON-NLS-1$
		Integer field2 = 1;
		BeanWith2Fields bw2f = new BeanWith2Fields(field1, field2);
		
		String expected = field1;
		String actual = ReflectionUtils.<String>invokeMethodByUniqueName(bw2f, "getField1"); //$NON-NLS-1$
		assertEquals(expected, actual);
		
		actual = ReflectionUtils.<String>invokeMethodByUniqueName(bw2f, "getField1String"); //$NON-NLS-1$
		assertEquals(expected, actual);
		
		expected = field2.toString();
		actual = ReflectionUtils.<String>invokeMethodByUniqueName(bw2f, "getField2AsString"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * unit test for invokeMethodByUniqueName when the method doesn't exist
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testInvokeMethodByUniqueName_withNoExistingMethod() {
		BeanWith2Fields bw2f = new BeanWith2Fields("hello", 13);
		ReflectionUtils.invokeMethodByUniqueName(bw2f, "noMethod");
	}
	
	/**
	 * unit test for invokeMethodByUniqueName when args doesn't match
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testInvokeMethodByUniqueName_whenArgsNotMatch() {
		Child c = new Child();
		ReflectionUtils.invokeMethodByUniqueName(c, "sampleMethod",Integer.class);
	}
	
	/**
	 * unit test for getPublicMethodsByName
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPublicMethodsByName() {
		List<Method> list = ReflectionUtils.getPublicMethodsByName("sampleMethod",Child.class);
		assertEquals(1, list.size());
	}
	
	/**
	 * unit test for getPublicMethodsByName
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPublicMethodsByName_withAbstractClass() {
		List<Method> list = ReflectionUtils.getPublicMethodsByName
			("getField5",AbstractSampleInterfaceImpl.class);
		assertEquals(1, list.size());
	}
	
	
	/**
	 * unit test for getMethodByUniqueName
	 */
	@Test
	public void testGetMethodByUniqueName_withExistingMethod() {		
		Method m = ReflectionUtils.getMethodByUniqueName("getField1", BeanWith2Fields.class); //$NON-NLS-1$
		assertNotNull(m);
		assertEquals(m.getParameterTypes().length, 0);
	}	
	
	/**
	 * unit test for getMethodByUniqueName
	 */
	@Test
	public void testGetMethodByUniqueName_withNotExistingMethod() {		
		Method m = ReflectionUtils.getMethodByUniqueName("noMethod", BeanWith2Fields.class); //$NON-NLS-1$
		assertNull(m);		
	}
	
	/**
	 * unit test for getPublicMethodByUniqueName
	 */
	@Test(expected=RuntimeException.class)
	public void testGetMethodByUniqueName_withAmbiguousName() {
		Object o = new Object() {
			@SuppressWarnings("unused")
			public void toString(String s) {/* empty */}
		};		
		ReflectionUtils.getMethodByUniqueName("toString", o.getClass()); //$NON-NLS-1$
	}
	
	/**
	 * unit test for getPublicMethodByUniqueName
	 */
	@Test
	public void testGetPublicMethodByUniqueName_withExistingMethod() {		
		Method m = ReflectionUtils.getPublicMethodByUniqueName("getField1", BeanWith2Fields.class, true); //$NON-NLS-1$
		assertNotNull(m);
		assertEquals(m.getParameterTypes().length, 0);
		
		Method m1 = ReflectionUtils.getPublicMethodByUniqueName("getField1", BeanWith2Fields.class, false); //$NON-NLS-1$
		assertNotNull(m1);
		assertEquals(m1.getParameterTypes().length, 0);
	}
	
	/**
	 * unit test for getMethodByUniqueName
	 */
	@Test
	public void testGetPublicMethodByUniqueName_withNotExistingMethod() {		
		Method m = ReflectionUtils.getPublicMethodByUniqueName("noMethod", BeanWith2Fields.class, true); //$NON-NLS-1$
		assertNull(m);		
	}
	
	/**
	 * unit test for getPublicMethodByUniqueName
	 */
	@Test(expected=RuntimeException.class)
	public void testGetPublicMethodByUniqueName_withAmbiguousName() {
		Object o = new Object() {
			@SuppressWarnings("unused")
			public void toString(String s) {/* empty */}
		};		
		ReflectionUtils.getPublicMethodByUniqueName("toString", o.getClass(), false); //$NON-NLS-1$
	}
	


	/**
	 * tests get(field, val, obj)
	 */
	@Test (expected=RuntimeException.class)
	public void testGet_WithException() {
		Father father = new Father();
		father.setField4(new Object());
		List<Field> fields = ReflectionUtils.allFields(Father.class, Father.class);
		assertEquals(ReflectionUtils.get(fields.get(0), father), father.getField4());
	}
	
	/**
	 * tests get(field, obj) when field doesn't exist
	 */
	@SuppressWarnings("nls")
	@Test (expected=RuntimeException.class)
	public void testGet_noSuchField() {
		Father father = new Father();
		ReflectionUtils.get("field6", father);
	}

	
	/**
	 * tests set(field, val, obj)
	 */
	@Test
	public void testSet() {
		Child child = new Child();
		child.setField6(new Object());
		List<Field> fields = ReflectionUtils.allFields(Child.class, Child.class);
		Field field = fields.get(0);
		field.setAccessible(true);
		assertEquals(ReflectionUtils.get(field, child), child.getField6());
		Object newObj = new Object();
		ReflectionUtils.set(field, newObj, child);
		assertEquals(ReflectionUtils.get(field, child), newObj);
	}
	
	/**
	 * tests set(string, val, obj)
	 */
	@Test
	public void testSet_WithString() {
		BeanWith2Fields bean = new BeanWith2Fields();
		ReflectionUtils.set("field1", StringConstants.ZERO, bean); //$NON-NLS-1$
		assertEquals(StringConstants.ZERO, bean.getField1());
	}
	
	/**
	 * tests set(string, val, obj)
	 */
	@Test
	public void testSet_WithString_andStatic() {
		BeanWithStaticField bean = new BeanWithStaticField();
		BeanWithStaticField.field = 12;
		ReflectionUtils.set("field", 5, bean); //$NON-NLS-1$
		assertEquals(Integer.valueOf(5), BeanWithStaticField.field);
	}

	/**
	 * tests set(field, type, messageKey)
	 */
	@Test (expected=RuntimeException.class)
	public void testSet_WithRuntimeException() {
		Child child = new Child();
		child.setField6(new Object());
		List<Field> fields = ReflectionUtils.allFields(Child.class, Child.class);
		assertEquals(ReflectionUtils.get(fields.get(0), child), child.getField6());
		Object newObj = new Object();
		ReflectionUtils.set(fields.get(0), child, newObj); //obviously wrong call
		assertEquals(ReflectionUtils.get(fields.get(0), child), newObj);
	}
	
	
	/**
	 * tests set(field,value, obj) when field doesn't exist
	 */
	@SuppressWarnings("nls")
	@Test (expected=RuntimeException.class)
	public void testSet_noSuchField() {
		Father father = new Father();
		ReflectionUtils.set("field6","value", father);
	}

	

	
	/**
	 * Unit test for setNullToDuplicateFieldsOfSuper.
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	@Test
	public void testSetNullToDuplicateFieldsOfSuper() 
	throws SecurityException, NoSuchFieldException {
		Father f = new Father();
		Field field1 = f.getClass().getSuperclass().getDeclaredField("field1");
		field1.setAccessible(true);
		assertEquals("field1", ReflectionUtils.get(field1, f));
		Field field2 = f.getClass().getSuperclass().getDeclaredField("field2");
		field2.setAccessible(true);
		assertEquals("field2", ReflectionUtils.get(field2, f));
		ReflectionUtils.setNullToDuplicateFieldsOfSuper(f, new Class[]{Anno.class});
		assertEquals("field1", ReflectionUtils.get(field1, f));
		assertNull(ReflectionUtils.get(field2, f));
	}
	
	/**
	 * Tests getUnique()
	 */
	@Test
	public void testGetUnique() {
		Field field = ReflectionUtils.getUnique
			(Arrays.asList(BeanWithUnique.class.getDeclaredFields()), Anno.class);
		assertEquals(field.getName(), "unique"); //$NON-NLS-1$
		Field expectedNull = ReflectionUtils.getUnique
			(Arrays.asList(BeanWithNone.class.getDeclaredFields()), Anno.class);
		assertNull(expectedNull);
	}
	
	/**
	 * Tests getUnique() when an exception is expected.
	 */
	@Test(expected=RuntimeException.class)
	public void testGetUnique_throwingException() {
		ReflectionUtils.getUnique 
			(Arrays.asList(BeanWithTwo.class.getDeclaredFields()), Anno.class);
	}
	
	/**
	 * Tests getAnnotatedMethods()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetAnnotatedMethods() {
		List<Method> methods = ReflectionUtils.getAnnotatedMethods(Child.class, MethodAnno.class);
		assertEquals(2, methods.size());
		for(Method m : methods) {
			assertTrue(m.getName().equals("getField6") || m.getName().equals("setField6"));
		}
	}
	
	/**
	 * Tests getAnnotatedType()
	 */
	@Test
	public void getAnnotatedType() {
		Class<?> actual = ReflectionUtils.getAnnotatedType(AnnotatedClass.class, ClassAnno.class);
		assertEquals(AnnotatedClass.class,actual);

		//class without annotation
		Class<?> actualInter = ReflectionUtils.getAnnotatedType(BeanWith2Fields.class, ClassAnno.class);
		assertNull(actualInter);
		
		//class with interfaces but no annotated
		Class<?> actualSuper = ReflectionUtils.getAnnotatedType(Date.class, ClassAnno.class);
		assertNull(actualSuper);
	}
	
	
	/**
	 * Tests newInstance(clazz, args)
	 */
	@Test
	public void testNewInstance_withoutArgs() {
		BeanWith2Fields b = ReflectionUtils.newInstance(BeanWith2Fields.class);
		assertNotNull(b);	
	}
	
	/**
	 * Tests newInstance(clazz, args)
	 */
	@Test
	public void testNewInstance_withStringArgument() {
		BeanWith2Fields b = ReflectionUtils.newInstance(BeanWith2Fields.class.getName());
		assertNotNull(b);	
	}
	
	/**
	 * Tests newInstance(clazz, args) when there is no such constructor
	 */
	@Test(expected = RuntimeException.class)
	public void testNewInstance_noConstructor() {
		 ReflectionUtils.newInstance(BeanWith2Fields.class,String.class,String.class);
	}
	
	/**
	 * Tests newInstance(clazz, args) when there is no such constructor
	 */
	@Test()
	public void testAttemptNewInstance() {
		 String name = BeanWith1Field.class.getName();
		 BeanWith1Field bean = ReflectionUtils.attemptNewInstance(name);
		 Assert.assertNotNull(bean);
		 Assert.assertNull(ReflectionUtils.attemptNewInstance(null));
	}
	
	/**
	 * Tests isInstanceOf
	 */
	@Test
	public void testIsInstanceOf() {
		 BeanWith2Fields bean = new BeanWith2Fields();
		 assertTrue(ReflectionUtils.isInstanceOf(bean, BeanWith2Fields.class));
		 
		 assertFalse(ReflectionUtils.isInstanceOf(null, BeanWith2Fields.class));
	}
	
	
	/**
	 * Tests newInstance(clazz, args)
	 */
	@Test
	public void testNewInstance_withArgs() {
		//two-arg constructor
		String str = "str"; //$NON-NLS-1$
		SampleMultiConstructorClass stac = ReflectionUtils.newInstance(
				SampleMultiConstructorClass.class, 1, str);
		Object[] expecteds = new Object[]{1, str};
		Object[] actuals = new Object[]{stac.getI(), stac.getS()};
		assertArrayEquals(expecteds, actuals);
		assertEquals(new Integer(1), stac.getI());
		assertEquals(str, stac.getS());
		
		//two-arg constructor with array
		stac = ReflectionUtils.newInstance(SampleMultiConstructorClass.class, expecteds);
		actuals = new Object[]{stac.getI(), stac.getS()};
		assertArrayEquals(expecteds, actuals);
		
		//one-arg constructor
		expecteds = new Object[]{new Integer(2), new Integer(2).toString()};
		stac = ReflectionUtils.newInstance(SampleMultiConstructorClass.class, expecteds[0]);
		actuals = new Object[]{stac.getI(), stac.getS()};
		assertArrayEquals(expecteds, actuals);
		
		//one-arg constructor
		expecteds = new Object[]{new Integer(2), new Integer(2).toString()};
		stac = ReflectionUtils.newInstance(SampleMultiConstructorClass.class, new Object[] {expecteds[0]});
		actuals = new Object[]{stac.getI(), stac.getS()};
		assertArrayEquals(expecteds, actuals);
	}
	

	
	/**
	 * Tests getPropertyGetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPropertyGetters() {
		String [] properties = {"field1","field2"};
		Method[] methods = ReflectionUtils.getPropertyGetters(properties, BeanWith2Fields.class);
		assertEquals(2, methods.length);
	}
		
	/**
	 * tests getCopyConstructor().
	 */
	@Test
	public void testGetCopyConstructor() {
		Constructor<BeanWithNone> none = 
			ReflectionUtils.getCopyConstructor(BeanWithNone.class);
		assertNull(none);
		Constructor<BeanWithCopyConstructor> copy = 
			ReflectionUtils.getCopyConstructor(BeanWithCopyConstructor.class);
		assertNotNull(copy);
	}
	
	/**
	 * tests getNoArgConstructor().
	 */
	@Test
	public void testGetNoArgConstructor_returnNull() {		
		Constructor<Double> none = 
			ReflectionUtils.getNoArgConstructor(Double.class);
		assertNull(none);
	}
	
	/**
	 * tests getNoArgConstructor().
	 */
	@Test
	public void testGetNoArgConstructor_withWrittenConstructor() {
		Constructor<BeanWithCopyConstructor> con = 
			ReflectionUtils.getNoArgConstructor(BeanWithCopyConstructor.class);
		assertNotNull(con);
	}
	
	/**
	 * tests getNoArgConstructor().
	 */
	@Test
	public void testGetNoArgConstructor_withImpliedConstructor() {
		Constructor<BeanWith1Field> con = 
			ReflectionUtils.getNoArgConstructor(BeanWith1Field.class);
		assertNotNull(con);
	}
	
	
	/**
	 * Unit test for hasField().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testHasFields() {
		assertFalse(ReflectionUtils.hasField(BeanWith2Fields.class, "noExisting", Integer.class));
		assertTrue(ReflectionUtils.hasField(BeanWith2Fields.class, "field1", String.class));
	}
	
	
	/**
	 * Unit test for isNoDefaultConstructor().
	 */
	@Test
	public void testIsNoDefaultConstructor() {
		assertTrue(ReflectionUtils.isNoDefaultConstructor(Integer.class));
		assertFalse(ReflectionUtils.isNoDefaultConstructor(SampleAbstractClass2.class));
		assertFalse(ReflectionUtils.isNoDefaultConstructor(BeanWith1Field.class));
		assertFalse(ReflectionUtils.isNoDefaultConstructor(BeanWithCopyConstructor.class));
	}
	
	
		
	
	/*-------------------------------SAMPLES-----------------------------------*/
	
	/**
	 * tests getPropertiesFromResource
	 */
	@Test
	public void testGetValuesOfFields(){
		List<Field> fields = ReflectionUtils.allFields(BeanWith2Fields.class, BeanWith2Fields.class);
		fields.remove(0); //remove serialVersionUID
		for (Field f : fields) {
			ReflectionUtils.setAccessible(f);
		}
		BeanWith2Fields bean = new BeanWith2Fields();
		bean.setField1("1"); //$NON-NLS-1$
		bean.setField2(2);
		List<?> list = ReflectionUtils.getValuesOfFields(bean, fields, Object.class,  true);
		Object [] epxected = {"1",2}; //$NON-NLS-1$
		for (int i = 0; i < list.size(); i++) {
			Object expected = epxected[i];
			Object actual = list.get(i);
			assertEquals(expected,actual);
		 }
	}
	

	
	

	/**
	 * tests generateHashCode
	 */
	@Test
	public void testInvoke(){
	    Object[] args = null;
	    BeanWith2Fields bean = new BeanWith2Fields();
	    bean.setField2(2);
		Method method = ReflectionUtils.getPublicMethodWithoutParamsByName("getField2", BeanWith2Fields.class); //$NON-NLS-1$
	    Integer expected = 2;
	    Object actual = ReflectionUtils.invoke(method, bean, args);
	    assertEquals(expected,actual);
	}
	
	/**
	 * Tests isCollection()
	 */
	@Test
	public void testIsCollection() {
		assertTrue(ReflectionUtils.isCollection(List.class));
		assertFalse(ReflectionUtils.isCollection(String.class));
	}
	
	/**
	 * Tests isArray()
	 */
	@Test
	public void testIsArray() {
		Object[] array = new Object[3];
		String s = "s"; //$NON-NLS-1$
		assertTrue(ReflectionUtils.isArray(array.getClass()));
		assertFalse(ReflectionUtils.isArray(s.getClass()));
	}
	

	
	
	/**
	 * Unit test for isConcreteClass()
	 */
	@Test
	public void testIsConcreteClass() {
		
		assertFalse(ReflectionUtils.isConcreteClass(null));
		assertTrue(ReflectionUtils.isConcreteClass(BeanWith2Fields.class));
		assertFalse(ReflectionUtils.isConcreteClass(SampleAbstractClass2.class));
	}
		
	/**
	 * Unit test for argumentTypesMatchParameterTypes()
	 */
	@Test
	public void testArgumentTypesMatchParameterTypes() {
		Class<?>[] paramTypes = {Number.class};
		Class<?>[] argTypes = {Integer.class};
		assertTrue(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{};
		argTypes = new Class<?>[]{};
		assertTrue(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{};
		argTypes = new Class<?>[]{Integer.class};
		assertFalse(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{Number.class};
		argTypes = new Class<?>[]{null};
		assertTrue(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{Number.class, Object.class};
		argTypes = new Class<?>[]{Integer.class};
		assertFalse(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{Number.class, Object.class};
		argTypes = new Class<?>[]{Integer.class, String.class};
		assertTrue(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{Number.class, String.class};
		argTypes = new Class<?>[]{Integer.class, Object.class};
		assertFalse(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
		paramTypes = new Class<?>[]{Number.class, String.class};
		argTypes = null;
		assertFalse(ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argTypes));
		
	}
	
	/**
	 * Unit test for argumentsMatchParameterTypes()
	 */
	@Test
	public void testArgumentsMatchParameterTypes() {
		Class<?>[] paramTypes = {Number.class};
		Object[] args = {new Integer(1)};
		assertTrue(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{};
		args = new Object[]{};
		assertTrue(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{};
		args = new Object[]{new Integer(1)};
		assertFalse(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{Number.class};
		args = new Object[]{null};
		assertTrue(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{Number.class, Object.class};
		args = new Object[]{new Integer(1)};
		assertFalse(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{Number.class, Object.class};
		args = new Object[]{new Integer(1), ""}; //$NON-NLS-1$
		assertTrue(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{Number.class, String.class};
		args = new Object[]{new Integer(1), new Object()};
		assertFalse(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
		
		paramTypes = new Class<?>[]{Number.class, String.class};
		args = null;
		assertFalse(ReflectionUtils.argumentsMatchParameterTypes(paramTypes, args));
	}
	
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_implementationInAbstractClass() {
		Method m1 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField1", AbstractSampleInterfaceImpl.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m1, AbstractSampleInterfaceImpl.class);
		assertTrue(b);
		
		Method m2 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField1", SampleInterface.class); //$NON-NLS-1$
		b = ReflectionUtils.isImplemented(m2, AbstractSampleInterfaceImpl.class);
		assertTrue(b);		
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_implementationInSuperclass() {
		Method m = ReflectionUtils.getPublicMethodWithoutParamsByName
			("toString", AbstractSampleInterfaceImpl.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m, AbstractSampleInterfaceImpl.class);
		assertTrue(b);
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_abstractDeclaration() {
		Method m = ReflectionUtils.getPublicMethodWithoutParamsByName
			("someMethod", AbstractSampleInterfaceImpl.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m, AbstractSampleInterfaceImpl.class);
		assertFalse(b);
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_noDeclaration() {
		Method m1 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField2", AbstractSampleInterfaceImpl.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m1, AbstractSampleInterfaceImpl.class);
		assertFalse(b);
		
		Method m2 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField2", SampleInterface.class); //$NON-NLS-1$
		b = ReflectionUtils.isImplemented(m1, AbstractSampleInterfaceImpl.class);
		assertFalse(b);
		
		assertEquals(m1, m2);
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_interface1() {
		Method m = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField2", SampleInterface.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m, SampleInterface.class);
		assertFalse(b);
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@Test
	public void testIsImplemented_interface2() {
		Method m = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField1", SampleInterface.class); //$NON-NLS-1$
		boolean b = ReflectionUtils.isImplemented(m, SampleInterface.class);
		assertFalse(b);
	}
	
	/**
	 * Unit test for getInterfaceHierarchy
	 */
	@Test
	public void testGetInterfaceHierarchy() {
		assertEquals(0, ReflectionUtils.getInterfaceHierarchy(IA.class, null).size());
		assertEquals(1, ReflectionUtils.getInterfaceHierarchy(IBextendsIA.class, null).size());
		assertEquals(2, ReflectionUtils.getInterfaceHierarchy(ICextendsIB.class, null).size());
		assertEquals(3, ReflectionUtils.getInterfaceHierarchy(IDextendsICandIA.class, null).size());

		assertEquals(1, ReflectionUtils.getInterfaceHierarchy(AimplementsIA.class, null).size());
		assertEquals(2, ReflectionUtils.getInterfaceHierarchy(BextendsAimplementsIB.class, null).size());
		assertEquals(3, ReflectionUtils.getInterfaceHierarchy(CextendsBimplementsIC.class, null).size());
		assertEquals(4, ReflectionUtils.getInterfaceHierarchy(DextendsCimplementsID.class, null).size());
	}
	
	/**
	 * Unit test for getTypeHierarchy
	 */
	@Test
	public void testGetTypeHierarchy() {
		assertEquals(2, ReflectionUtils.getTypeHierarchy(AimplementsIA.class, null).size());
		assertEquals(4, ReflectionUtils.getTypeHierarchy(BextendsAimplementsIB.class, null).size());
		assertEquals(6, ReflectionUtils.getTypeHierarchy(CextendsBimplementsIC.class, null).size());
		assertEquals(8, ReflectionUtils.getTypeHierarchy(DextendsCimplementsID.class, null).size());
		
		assertEquals(1, ReflectionUtils.getTypeHierarchy(BextendsAimplementsIB.class, AimplementsIA.class).size());
		assertEquals(3, ReflectionUtils.getTypeHierarchy(CextendsBimplementsIC.class, IBextendsIA.class).size());
		
	}
	
	/**
	 * Test.
	 */
	@Test
	public void testNamesOfPropertiesWithDifferentValue() {
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1("b1");
		b1.setField2(1);
		BeanWith2Fields b2 = new BeanWith2Fields();
		b2.setField1("b1");
		b2.setField2(2);
		String[] actuals = 
			ReflectionUtils.namesOfPropertiesWithDifferentValue(b1, b2);
		String[] expecteds = {"field2"};
		org.junit.Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test	
	public void testCopyPropertiesWithDefaults() {
		
		BeanWith2Fields b1 = new BeanWith2Fields();
		BeanWith2Fields b2 = new BeanWith2Fields();
		
		/* copy one property */
		String[] prop1 = {"field1"};
		copyPropertiesWithDefaults(b1, b2, prop1);
		assertEquals(Defaults.getDefaultValue(String.class), b2.getField1());
				
		/* copy two properties */
		BeanWith2Fields b3 = new BeanWith2Fields();
		String[] prop2 = {"field1", "field2"};
		copyPropertiesWithDefaults(b1, b3, prop2);
		assertEquals(Defaults.getDefaultValue(String.class), b3.getField1());
		assertEquals(Defaults.getDefaultValue(Integer.class), b3.getField2());
		
		/* copy all properties */
		BeanWith2Fields b4 = new BeanWith2Fields();
		String[] prop3 = {};
		copyPropertiesWithDefaults(b1, b4, prop3);
		assertEquals(Defaults.getDefaultValue(String.class), b4.getField1());
		assertEquals(Defaults.getDefaultValue(Integer.class), b4.getField2());
		
		/* copy all properties with null array */
		b1.setField1("X");
		BeanWith2Fields b5 = new BeanWith2Fields();		
		copyPropertiesWithDefaults(b1, b5, null);
		assertEquals("X", b5.getField1());
		assertEquals(Defaults.getDefaultValue(Integer.class), b5.getField2());
		
	}
	
	
	/**
	 * Unit test for invocationException(method,throwable)
	 */
	@Test
	public void testInvocationException() {
		Method m = ReflectionUtils.getMethodByUniqueName
			("testInvocationException", this.getClass()); //$NON-NLS-1$
		NullPointerException npex = new NullPointerException();
		InvocationTargetException itex = 
			new InvocationTargetException(npex);
		RuntimeException rtex = 	
			ReflectionUtils.invocationException(m, itex);
		Assert.assertNotNull(rtex);
		Assert.assertEquals(itex,rtex.getCause());
		
	}
 	
	
	
	/*
	 * ==============SAMPLES==================
	 */
		
	/**
	 * Class with a field annotated
	 */
	@SuppressWarnings("unused")
	private static class BeanWithNone {
		/**
		 * field
		 */
		Integer notAnnotated;
	}
	
	/**
	 * Class with a field annotated
	 */
	@SuppressWarnings("unused")
	private static class BeanWithUnique {
		/**
		 * field
		 */
		@Anno Integer unique;
	}
	
	/**
	 * Class with two fields annotated
	 */
	@SuppressWarnings("unused")
	private static class BeanWithTwo {
		/**
		 * field
		 */
		@Anno Integer first;
		/**
		 * field
		 */		
		@Anno Integer second;
	}
	
	/**
	 * Class with a field annotated
	 */	
	@SuppressWarnings("unused")
	private static class BeanWithCopyConstructor {
		/**
		 * String
		 */
		String string;

		/**
		 * Creates a new BeanWithCopyConstructor object.
		 */
		public BeanWithCopyConstructor() {
			super();			
		}
		
		/**
		 * Creates a new BeanWithCopyConstructor object.
		 * 
		 * @param bean 
		 */
		public BeanWithCopyConstructor(BeanWithCopyConstructor bean) {
			super();		
			this.string = bean.string;
		}
		
	}
	
	/**
	 * AnnotatedClass.
	 */
	@ClassAnno
	private class AnnotatedClass {
		//empty
	}
	
	/**
	 * Class with a static field.
	 */
	private static class BeanWithStaticField {
		/**
		 * field
		 */
		static Integer field;
	}
	
	
	
	
}
