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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ExpressionBasedSelection}.
 */
@SuppressWarnings("nls")
public class TestExpressionBasedSelection {	
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor() {
		List<Pair<String, Object>> pairs = new ArrayList<Pair<String,Object>>();
		Integer expectedValue = 12;
		ExpressionBasedSelection<Integer, Object> ebs = 
			new ExpressionBasedSelection<Integer, Object>(pairs, expectedValue);
		Assert.assertEquals(ebs.expectedValue, expectedValue);
		Assert.assertEquals(ebs.pairs, pairs);		
	}
	
	/**
	 * Unit test for select.
	 */	
	@Test
	public void testSelect_withMatch() {
		Integer expectedValue = 12;
		
		BeanWithNestedBean bean = new BeanWithNestedBean("f1", expectedValue);
		bean.setProperty1(13);		
		
		List<Pair<String, String>> pairs = getSamplePairs();	
		
		ExpressionBasedSelection<Integer, String> ebs = 
			new ExpressionBasedSelection<Integer, String>(pairs, expectedValue);
		
		String actual = ebs.select(bean);
		String expected = "bean.nested.field2";
		Assert.assertEquals(expected, actual);
		
	}
	
	/**
	 * Unit test for select.
	 */
	@Test
	public void testSelect_withNullMatch() {
		Integer expectedValue = null;
		
		BeanWithNestedBean bean = new BeanWithNestedBean("f1", expectedValue);
		bean.setProperty1(13);		
		
		List<Pair<String, String>> pairs = getSamplePairs();	
		
		ExpressionBasedSelection<Integer, String> ebs = 
			new ExpressionBasedSelection<Integer, String>(pairs, expectedValue);
		
		String actual = ebs.select(bean);
		String expected = "bean.nested.field2";
		Assert.assertEquals(expected, actual);
		
	}
	
	/**
	 * Unit test for select.
	 */
	@Test
	public void testSelect_withNoMatch() {
		Integer expectedValue = 20;
		
		BeanWithNestedBean bean = new BeanWithNestedBean("f1", 14);
		bean.setProperty1(13);		
		
		List<Pair<String, String>> pairs = getSamplePairs();		
		
		ExpressionBasedSelection<Integer, String> ebs = 
			new ExpressionBasedSelection<Integer, String>(pairs, expectedValue);
		
		String actual = ebs.select(bean);		
		Assert.assertNull(actual);
		
	}
	
	/**
	 * Creates a list of pairs.
	 * 
	 * @return a list of pairs.
	 */
	private List<Pair<String, String>> getSamplePairs() {
		List<Pair<String, String>> pairs = new ArrayList<Pair<String,String>>();
		pairs.add(new Pair<String, String>("property1","bean.property1"));
		pairs.add(new Pair<String, String>("nested.field1","bean.nested.field1"));
		pairs.add(new Pair<String, String>("nested.field2","bean.nested.field2"));
		return pairs;
	}

}
