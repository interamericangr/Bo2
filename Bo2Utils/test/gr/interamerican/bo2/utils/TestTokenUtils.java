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

import static gr.interamerican.bo2.utils.TokenUtils.split;
import static gr.interamerican.bo2.utils.TokenUtils.splitTrim;
import static gr.interamerican.bo2.utils.TokenUtils.toLines;
import static gr.interamerican.bo2.utils.TokenUtils.tokenize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for TokenUtils.
 */
@SuppressWarnings("nls")
public class TestTokenUtils {
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit() {		
		String str = "abc?def?ghi";
		String separator = "?";
		String [] result = TokenUtils.split(str, separator);
		String expected = "abc";
		String actual = result[0];
		assertEquals(expected,actual);
		
		String expected2 = "def";
		String actual2 = result[1];
		assertEquals(expected2,actual2);
		
		String expected3 = "ghi";
		String actual3 = result[2];
		assertEquals(expected3,actual3);
	}
	
	/**
	 * tests splitTrim
	 */	
	@Test
	public void testSplitTrim() {		
		String[] inputs = {
				"papaki,12 ,  , keno, 2",
				"papaki!12 !  ! keno! 2",
				"papaki 12 keno 2",
				"papaki,. 12. .  keno,:f, 2",
				"",
				"1,.2,.3,,4"
		};
		
		String[][] expectedsWithTrue = {
				{"papaki","12","","keno","2"},
				{"papaki","12","","keno","2"},
				{"papaki","12","keno","2"},
				{"papaki","","12","","keno",":f","2"},
				{},
				{"1","","2","","3","","4"}
		};	
		
		String[][] expectedsWithFalse = {
				{"papaki","12","keno","2"},
				{"papaki","12","keno","2"},
				{"papaki","12","keno","2"},
				{"papaki","12","keno",":f","2"},
				{},
				{"1","2","3","4"}
		};	
		
		Object[] delimiter = { //comma, exclam, space, dot+comma, minus, dot+comma
				',', 
				'!', 
				' ', 
				".,",
				"-", 
				".,"}; 
		
		for (int i = 0; i < inputs.length; i++) {
			String[] actualsWithTrue;
			String[] actualsWithFalse;
			String msg = StringUtils.showArguments("splitTrim",inputs[i],delimiter[i]);
			System.out.println(msg);
			if (delimiter[i] instanceof Character) {
				Character c = (Character)delimiter[i]; 
				actualsWithFalse = splitTrim(inputs[i], c, false);
				actualsWithTrue = splitTrim(inputs[i], c);
			} else {
				String dlm = (String) delimiter[i];
				actualsWithFalse = splitTrim(inputs[i], dlm, false);
				actualsWithTrue = splitTrim(inputs[i], dlm);				
			}			 
			assertArrayEquals(msg, expectedsWithTrue[i], actualsWithTrue);
			assertArrayEquals(msg, expectedsWithFalse[i], actualsWithFalse);
		}		
		
	}
	
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit_charSeperator() {		
		String str = "abc?def?ghi";
		char separator = '?';
		String [] result = split(str, separator);
		String expected = "abc";
		String actual = result[0];
		assertEquals(expected,actual);
		
		String expected2 = "def";
		String actual2 = result[1];
		assertEquals(expected2,actual2);
		
		String expected3 = "ghi";
		String actual3 = result[2];
		assertEquals(expected3,actual3);
	}

	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit_emptyTokens() {		
		String str = "abc??ghi";
		char separator = '?';
		String [] result = split(str, separator,true);
		String expected = "abc";
		String actual = result[0];
		assertEquals(expected,actual);
		
		String expected2 = "";
		String actual2 = result[1];
		assertEquals(expected2,actual2);
		
		String expected3 = "ghi";
		String actual3 = result[2];
		assertEquals(expected3,actual3);
	}
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit_emptyTokens2() {		
		String str = "abc???ghi";
		char separator = '?';
		String [] result = split(str, separator,true);
		String expected = "abc";
		String actual = result[0];
		assertEquals(expected,actual);
		
		String expected2 = "";
		String actual2 = result[1];
		assertEquals(expected2,actual2);
		
		String expected3 = "";
		String actual3 = result[2];
		assertEquals(expected3,actual3);
		
		String expected4 = "ghi";
		String actual4 = result[3];
		assertEquals(expected4,actual4);
	}
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit_emptyTokens3() {		
		String str = "??ghi";
		char separator = '?';
		String [] result = split(str, separator,true);
		String expected = "";
		String actual = result[0];
		assertEquals(expected,actual);
		
		String expected2 = "";
		String actual2 = result[1];
		assertEquals(expected2,actual2);
		
		String expected3 = "ghi";
		String actual3 = result[2];
		assertEquals(expected3,actual3);
	}
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testSplit_emptyTokens4() {		
		String str = ",,,,, ";
		char separator = ',';
		String [] result = split(str, separator,true);
		
		assertEquals(6,result.length);
		
		for(String s : result) {
			assertEquals("",s);
		}
	}
	
	/**
	 * Unit
	 */
	@Test
	public void testToLines() {
		String s = StringUtils.concat(
			"This is a nice day\n",
			"\n", "\n", 
			"for testing\n");
		String[] all = toLines(s, false);
		String[] expecteds = {"This is a nice day", "", "", "for testing"};
		Assert.assertArrayEquals(expecteds, all);
		
		String[] noEmpty = toLines(s, true);
		String[] noEmptyExpecteds = {"This is a nice day", "for testing"};
		Assert.assertArrayEquals(noEmptyExpecteds, noEmpty);		
	}
	
	/**
	 * Unit test for tokenize.
	 */	
	@Test
	public void testTokenize_string() {
		String[] array = {"this,that"};
		String[] expecteds = {"this", "that"};
		String[] actuals = tokenize(array);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Unit test for tokenize.
	 */	
	@Test
	public void testTokenize_array() {
		String[] array = {"this","that"};
		String[] expecteds = {"this", "that"};
		String[] actuals = tokenize(array);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Unit test for tokenize.
	 */	
	@Test
	public void testTokenize_emptystring() {
		String[] array = {""};
		String[] expecteds = {};
		String[] actuals = tokenize(array);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Unit test for tokenize.
	 */	
	@Test
	public void testTokenize_emptyarray() {
		String[] array = {};
		String[] expecteds = {};
		String[] actuals = tokenize(array);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	
}
