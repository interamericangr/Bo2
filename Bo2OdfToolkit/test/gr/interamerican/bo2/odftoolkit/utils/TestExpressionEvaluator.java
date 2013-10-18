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
package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.samples.bean.Family;
import gr.interamerican.bo2.samples.bean.MapOwner;
import gr.interamerican.bo2.samples.bean.Samples;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ExpressionEvaluator}.
 */
@SuppressWarnings("nls")
public class TestExpressionEvaluator {
	
	/**
	 * Unit test for evaluateOgnlExpression
	 */	
	@Test
	public void testGetValue_NestedPropertyString() {		
		Family family = Samples.theAstaireFamily();
		String fatherName = "father.lastName";
		String actual = ExpressionEvaluator.getInstance().getValue(fatherName, family);
		String expected = family.getFather().getLastName();
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for evaluateOgnlExpression
	 */	
	@Test
	public void testGetValue_NestedPropertyDate() {		
		Family family = Samples.theAstaireFamily();
		String fatherBirth = "father.birthDay";
		String value = ExpressionEvaluator.getInstance().getValue(fatherBirth, family);
		Assert.assertEquals(family.getFather().getBirthDay().toString(), value);
	}
	
	/**
	 * Unit test for evaluateOgnlExpression
	 */	
	@Test
	public void testGetValue_MapAndNestedProperties() {		
		Family family = Samples.theAstaireFamily();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("family", family);
		map.put("familyName", "The family Astaire");
		
		String actual = ExpressionEvaluator.getInstance().getValue("family.father.lastName", map);
		String expected = family.getFather().getLastName();
		Assert.assertEquals(expected, actual);
		
		String familyName = ExpressionEvaluator.getInstance().getValue("familyName", map);
		Assert.assertEquals("The family Astaire", familyName);
	}
	
	
	/**
	 * Unit test for parseExpression
	 */	
	@Test
	public void testParseExpression_tolerating() {		
		Family family = Samples.theAstaireFamily();
		String fatherName = "who.nobody";
		ExpressionEvaluator.getInstance().setTolerateErrors(true);
		String errorInd = "X";
		ExpressionEvaluator.getInstance().setErrorIndicator(errorInd);		
		Object actual = ExpressionEvaluator.getInstance().
			parseExpression(fatherName, family);
		Assert.assertEquals(errorInd, actual);
		ExpressionEvaluator.getInstance().setTolerateErrors(false);
	}
	
	/**
	 * Unit test for parseExpression
	 */	
	@Test
	public void testParseExpression_withNull() {
		Map<String, Object> map = new HashMap<String, Object>();
		String exp = "key";
		map.put(exp, null);
		ExpressionEvaluator ee = ExpressionEvaluator.getInstance();
		String old = ee.getNullIndicator();		
		String expected = "NO";
		ee.setNullIndicator(expected);
		Object actual = ee.parseExpression(exp, map);
		Assert.assertEquals(expected, actual);
		ee.setNullIndicator(old);
	}
	
	/**
	 * Unit test for parseExpression
	 */	
	@Test(expected=RuntimeException.class)
	public void testParseExpression_throwing() {		
		Family family = Samples.theAstaireFamily();
		String fatherName = "who.nobody";
		ExpressionEvaluator.getInstance().setTolerateErrors(false);
		ExpressionEvaluator.getInstance().parseExpression(fatherName, family);
	}
	
	/**
	 * Unit test for parseExpression
	 */	
	@Test
	public void testSetErrorIndicator() {
		ExpressionEvaluator ee = ExpressionEvaluator.getInstance(); 
		String expected = "U";
		ee.setErrorIndicator(expected);
		String actual = ee.getErrorIndicator();
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for parseExpression
	 */	
	@Test
	public void testSetTolerateErrors_() {
		ExpressionEvaluator ee = ExpressionEvaluator.getInstance();
		boolean b = ee.isTolerateErrors();
		ee.setTolerateErrors(true);
		Assert.assertTrue(ee.isTolerateErrors());
		ee.setTolerateErrors(b);
	}
	
	/**
	 * Unit test for evaluateOgnlExpression
	 */	
	@Test
	public void testGetValue_MapOwner() {		
		MapOwner mapOwner = new MapOwner();
		String expected = "value1";
		mapOwner.getStringsMap().put("key1", expected);
		String exp = "stringsMap.key1";
		String actual = ExpressionEvaluator.getInstance().getValue(exp, mapOwner);		
		Assert.assertEquals(expected, actual);
	}
	
	
	

}
