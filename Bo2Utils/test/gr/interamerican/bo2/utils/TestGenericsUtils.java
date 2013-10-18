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

import static gr.interamerican.bo2.utils.GenericsUtils.isParameterizedParameterType;
import static gr.interamerican.bo2.utils.GenericsUtils.isParameterizedReturnType;
import static gr.interamerican.bo2.utils.GenericsUtils.isVariableParameterType;
import static gr.interamerican.bo2.utils.GenericsUtils.isVariableReturnType;
import static gr.interamerican.bo2.utils.ReflectionUtils.getPublicMethodByUniqueName;
import gr.interamerican.bo2.samples.generics.BimplementsIB;
import gr.interamerican.bo2.samples.generics.CimplementsIA;
import gr.interamerican.bo2.samples.generics.DextendsB;
import gr.interamerican.bo2.samples.generics.IntegeredIBextendsNumberedIA;
import gr.interamerican.bo2.samples.generics.NumberedIA;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.beans.Range;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GenericsUtils}.
 */
@SuppressWarnings("nls")
public class TestGenericsUtils {
	
	/**
	 * Tests getCollectionTypeOfField()
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testGetCollectionTypeOfField() 
	throws SecurityException, NoSuchFieldException {
		Field rawList = CollectionFields.class.getDeclaredField("rawList");
		Assert.assertEquals(Object.class, GenericsUtils.getCollectionTypeOfField(rawList));
		Field stringList = CollectionFields.class.getDeclaredField("stringList");
		Assert.assertEquals(String.class, GenericsUtils.getCollectionTypeOfField(stringList));
		Field string = CollectionFields.class.getDeclaredField("string");
		Assert.assertNull(GenericsUtils.getCollectionTypeOfField(string));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_staticMethodReturnsGeneric() {
		Method addAll = getPublicMethodByUniqueName("addAll", CollectionUtils.class, false);
		Assert.assertTrue(isVariableReturnType(addAll));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_staticMethodReturnsGenericCollection() {
		Method addAll = getPublicMethodByUniqueName("upCast", CollectionUtils.class, false);
		Assert.assertFalse(isVariableReturnType(addAll));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_methodReturnsGeneric() {
		Method getLeft = getPublicMethodByUniqueName("getLeft", Pair.class, true);
		Assert.assertTrue(isVariableReturnType(getLeft));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_methodReturnsGenericInParameterizedSubtype() {
		Method getLeft = getPublicMethodByUniqueName("getLeft", Range.class, true);
		Assert.assertTrue(isVariableReturnType(getLeft));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_methodReturnsGenericInSubtype() {
		Method getLeft = getPublicMethodByUniqueName("getLeft", IntegerRange.class, true);
		Assert.assertTrue(isVariableReturnType(getLeft));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsVariableReturnType_methodReturnsString() {
		Method toString = getPublicMethodByUniqueName("toString", IntegerRange.class, true);
		Assert.assertFalse(isVariableReturnType(toString));
	}
	
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * Unit test for isVariableParameterType()
	 */
	@Test
	public void testIsVariableParameterType_genericArgInStaticMethod() {
		Method addAll = getPublicMethodByUniqueName("addAll", CollectionUtils.class, false);
		Assert.assertTrue(isVariableParameterType(addAll));
	}
	
	/**
	 * Unit test for isVariableParameterType()
	 */
	@Test
	public void testIsVariableParameterType_genericCollectionArg() {
		Method addAll = getPublicMethodByUniqueName("upCast", CollectionUtils.class, false);
		Assert.assertFalse(isVariableParameterType(addAll));
	}
	
	/**
	 * Unit test for isVariableParameterType()
	 */
	@Test
	public void testIsVariableParameterType_genericArg() {
		Method setLeft = getPublicMethodByUniqueName("setLeft", Pair.class, true);
		Assert.assertTrue(isVariableParameterType(setLeft));
	}
	
	/**
	 * Unit test for isVariableParameterType()
	 */
	@Test
	public void testIsVariableParameterType_genericArgInSubtype() {
		Method setLeft = getPublicMethodByUniqueName("setLeft", IntegerRange.class, true);
		Assert.assertTrue(isVariableParameterType(setLeft));
	}
	
	///////////////////////////////////////////////////////////
	
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsParameterizedReturnType_staticMethodReturnsGeneric() {
		Method addAll = getPublicMethodByUniqueName("addAll", CollectionUtils.class, false);
		Assert.assertFalse(isParameterizedReturnType(addAll));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsParameterizedReturnType_staticMethodReturnsGenericCollection() {
		Method addAll = getPublicMethodByUniqueName("upCast", CollectionUtils.class, false);
		Assert.assertTrue(isParameterizedReturnType(addAll));
	}
	
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsParameterizedReturnType_methodReturnsGeneric() {
		Method getLeft = getPublicMethodByUniqueName("getLeft", Pair.class, true);
		Assert.assertFalse(isParameterizedReturnType(getLeft));
	}
		
	/**
	 * Unit test for isVariableReturnType()
	 */
	@Test
	public void testIsParameterizedReturnType_methodReturnsString() {
		Method toString = getPublicMethodByUniqueName("toString", IntegerRange.class, true);
		Assert.assertFalse(isParameterizedReturnType(toString));
	}
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * Unit test for isParameterizedParameterType()
	 */
	@Test
	public void testIsParameterizedParameterType_genericCollectionArg() {
		Method addAll = getPublicMethodByUniqueName("addAll", Set.class, false);
		Assert.assertTrue(isParameterizedParameterType(addAll));
	}
		
	/**
	 * Unit test for isParameterizedParameterType()
	 */
	@Test
	public void testIsParameterizedParameterType_genericArg() {
		Method add = getPublicMethodByUniqueName("add", Set.class, false);
		Assert.assertFalse(isParameterizedParameterType(add));
	}
	
	/**
	 * Unit test for isVariableParameterType()
	 */
	@Test
	public void testIsParameterizedParameterType_noArg() {
		Method toString = getPublicMethodByUniqueName("toString", Object.class, false);
		Assert.assertFalse(isParameterizedParameterType(toString));
	}
	
	/**
	 * Unit test form getGenericInterfaces()
	 */
	@Test
	public void testFindTypeParameter() {
		Class<?> clazz =GenericsUtils.findTypeParameter(IntegerRange.class, Range.class, Object.class);
		Assert.assertEquals(Integer.class, clazz);
		clazz = GenericsUtils.findTypeParameter(
			IntegeredIBextendsNumberedIA.class, NumberedIA.class, null);
		Assert.assertEquals(Integer.class, clazz);
		clazz = GenericsUtils.findTypeParameter(BimplementsIB.class, NumberedIA.class, null);
		Assert.assertEquals(Integer.class, clazz);
		clazz = GenericsUtils.findTypeParameter(CimplementsIA.class, NumberedIA.class, null);
		Assert.assertEquals(Integer.class, clazz);
		clazz = GenericsUtils.findTypeParameter(DextendsB.class, NumberedIA.class, null);
		Assert.assertEquals(Integer.class, clazz);
	}
	
	///////////////////////////////////////////////////////////	
	
	
	static class IntegerRange extends Range<Integer> {/*empty*/}
	
	class CollectionFields {
		List rawList;
		List<String> stringList;
		String string;
	}

}
