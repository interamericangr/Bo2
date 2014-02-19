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

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.beans.Pair;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
/**
 * Unit test for {@link StringUtils}.
 */
/**
 * 
 */
@SuppressWarnings("nls")
public class TestStringUtils {
	
	/**
	 * Samples for firstCapital.
	 */
	String[][] firstCapitalSamples = {
			{"00asa", "00asa"},
			{"archimedes", "Archimedes"},
			{" archimedes", " archimedes"},			
			{"αρχιμηδης", "Αρχιμηδης"},
			{"εψιλον", "Εψιλον"},
			{"ηττα ", "Ηττα "},
			{"θήτα ", "Θήτα "},
			{"ομικρον", "Ομικρον"},
			{"υψιλον", "Υψιλον"},
			{"ωμέγα", "Ωμέγα"},				
			{"άλφα", "Άλφα"},				
			{"έψιλον", "Έψιλον"},
			{"ήττα", "Ήττα"},				
			{"όμικρον", "Όμικρον"},
			{"ύψιλον", "Ύψιλον"},
			{"ώρος", "Ώρος"}
	};
	
	/**
	 * tests showArguments
	 */

	@Test
	public void testShowArguments() {		
		String actual;
		String expected;		
		actual = StringUtils.showArguments("this", 1, 'c', 4.12, null);
		expected = "this, 1, c, 4.12, null";
		assertEquals(expected, actual);
	}
	
	/**
	 * tests showArguments
	 */
	
	@Test
	public void testArray2String() {		
		String actual;
		String expected;		
		Object[] array = {"this", 1, 'c', 4.12, null};
		String delimiter = "__";
		actual = StringUtils.array2String(array, delimiter);
		expected = "this__1__c__4.12__null";
		assertEquals(expected, actual);
		
		Object[] array2 = null;
		String actual2 = StringUtils.array2String(array2, delimiter);
		String expected2 = "null";
		assertEquals(expected2, actual2);
	}
	
	
	/**
	 * tests leftJustify
	 */	
	@Test
	public void testLeftJustify() {		
	
		Object[][] data = {
				{"papaki", 4, ' ', "papaki"},
				{"papaki", 10, ' ', "papaki    "},
				{"paparaki", 10, '0', "paparaki00"},
				{"aster", 5, '0', "aster"},
				{" kamia", 6, ' ', " kamia"},
				{" kamia", 5, ' ', " kamia"}
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Integer len = (Integer) data[i][1];
			Character c = (Character) data[i][2];
			String expected = (String) data[i][3];				
			String actual = StringUtils.leftJustify(s, len, c);				
			String msg = StringUtils.showArguments("leftJustify",s,len,c);			
			assertEquals(msg, expected, actual);
		}
		
	}
	
	/**
	 * tests leftJustify
	 */	
	@Test
	public void testRightJustify() {		
	
		Object[][] data = {
				{"papaki", 4, ' ', "papaki"},
				{"papaki", 10, ' ', "    papaki"},
				{"paparaki", 10, '0', "00paparaki"},
				{"aster", 5, '0', "aster"},
				{" kamia", 6, ' ', " kamia"},
				{" kamia", 5, ' ', " kamia"}
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Integer len = (Integer) data[i][1];
			Character c = (Character) data[i][2];
			String expected = (String) data[i][3];				
			String actual = StringUtils.rightJustify(s, len, c);				
			String msg = StringUtils.showArguments("leftJustify",s,len,c);			
			assertEquals(msg, expected, actual);
		}		
	}
	

	
	
	/**
	 * tests string2Bool
	 */	
	@Test
	public void testString2Bool() {		
	
		Object[][] data = {
				{" ", false },
				{"0", false },
				{"1", true },
				{"true", true },
				{"false", false },
				{"something", false },
				{" 1  ", true },
				{" TRUE  ", true },
				{" dd  ", false },
				{null, false }
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Boolean expected = (Boolean) data[i][1];						
			Boolean actual = StringUtils.string2Bool(s);
			String msg = StringUtils.showArguments("string2Bool", s);
			assertEquals(msg, expected, actual);
		}		
	}
	
	/**
	 * tests bool2String
	 */	
	@Test
	public void testBool2String() {
	
		String actual;
		String expected;
		
		actual = StringUtils.bool2String(true);
		expected = "1";
		assertEquals(expected, actual);
		
		actual = StringUtils.bool2String(false);
		expected = "0";
		assertEquals(expected, actual);
	}
	
	/**
	 * tests removeLeadingZeros
	 */	
	@Test
	public void testRemoveLeadingZeros() {
	
		String[][] data = {
				{"00asa", "asa"},
				{"0014", "14"},
				{"0015", "15"},
				{"00.16", ".16"}
		};
		for (int i = 0; i < data.length; i++) {
			String input = data[i][0];
			String actual = StringUtils.removeLeadingZeros(input);
			String expected = data[i][1]; 
			assertEquals(expected, actual);
		}		
	}
	

	/**
	 * tests FirstCapital
	 */	
	@Test
	public void testFirstCapital() {	
		for (int i = 0; i < firstCapitalSamples.length; i++) {
			String input = firstCapitalSamples[i][0];
			String actual = StringUtils.firstCapital(input);
			String expected = firstCapitalSamples[i][1]; 
			assertEquals(expected, actual);
		}	
		assertEquals("Asad", StringUtils.firstCapital("Asad"));
	}
	
	/**
	 * tests FirstLowerCase
	 */	
	@Test
	public void testFirstLowerCase() {	
		for (int i = 0; i < firstCapitalSamples.length; i++) {
			String input = firstCapitalSamples[i][1];
			String actual = StringUtils.firstLowerCase(input);
			String expected = firstCapitalSamples[i][0]; 
			assertEquals(expected, actual);
		}	
		assertEquals("asad", StringUtils.firstLowerCase("Asad"));
	}
	
	/**
	 * tests int2Str
	 */	
	@Test
	public void testInt2Str() {		
		Object[][] data = {
				{12, 2, "12"},
				{13, 4, "0013"},
				{14, 1, "1"},
				{125, 4, "0125"},
				{-125, 4, "-125"},
				{-125, 5, "-0125"}
		};
		for (int i = 0; i < data.length; i++) {
			Integer input = (Integer) data[i][0];
			Integer digits = (Integer) data[i][1];
			String expected = (String) data[i][2];
			String actual = StringUtils.int2str(input, digits);
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * tests sameCharacterString
	 */	
	@Test
	public void testsSameCharacterString() {
		String actual;
		String expected;
		
		actual = StringUtils.sameCharacterString(10, '0');
		expected = "0000000000";
		assertEquals(expected, actual);		
	}
	
	/**
	 * tests fixedLengthPadLeft
	 */	
	@Test
	public void testFixedLengthPadLeft() {		
		Object[][] data = {
				{"papaki", 10, "papaki    "},
				{"papaki", 6, "papaki"},
				{"papaki", 4, "papa"},
				{" papaki", 10, "papaki    "},
				{" papaki", 6, "papaki"},
				{" papaki", 4, "papa"},
				{" papaki ", 10, "papaki    "},
				{" papaki ", 6, "papaki"},
				{" papaki ", 4, "papa"}
		};
		for (int i = 0; i < data.length; i++) {
			String input = (String) data[i][0];
			Integer length = (Integer) data[i][1];
			String expected = (String) data[i][2];
			String actual = StringUtils.fixedLengthPadLeft(input, length);
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * tests fixedLengthPadLeft
	 */	
	@Test
	public void testFixedLengthPadRight() {		
		Object[][] data = {
				{"papaki", 10, "    papaki"},
				{"papaki", 6, "papaki"},
				{"papaki", 4, "paki"},
				{" papaki", 10, "    papaki"},
				{" papaki", 6, "papaki"},
				{" papaki", 4, "paki"},
				{" papaki ", 10, "    papaki"},
				{" papaki ", 6, "papaki"},
				{" papaki ", 4, "paki"}
		};
		for (int i = 0; i < data.length; i++) {
			String input = (String) data[i][0];
			Integer length = (Integer) data[i][1];
			String expected = (String) data[i][2];
			String actual = StringUtils.fixedLengthPadRight(input, length);
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * tests isNullOrBlank
	 */
	@Test
	public void testIsNullOrBlank() {
		assertFalse(StringUtils.isNullOrBlank("!null && !blank"));
		assertTrue(StringUtils.isNullOrBlank(""));
		assertTrue(StringUtils.isNullOrBlank(null));
	}
	
	/**
	 * tests insertAfter
	 */
	@Test
	public void testInsertAfter() {
		String input = "if(1){doSomething();}";
		String actual = StringUtils.insertAfter(input, "{", "doSomethingElseFirst();");
		String expected = "if(1){doSomethingElseFirst();doSomething();}";
		assertEquals(expected, actual);
		actual = StringUtils.insertAfter(input, "();", "doSomethingElseAfter();");
		expected = "if(1){doSomething();doSomethingElseAfter();}";
		assertEquals(expected, actual);
		//non-existing mark
		actual = StringUtils.insertAfter(input, "xx", "doSomethingElseFirst();");
		expected = input;
		assertEquals(expected, actual);
	}
	
	/**
	 * tests removeEmpty
	 */
	@Test
	public void testRemoveEmpty() {
		String[] inputs = {"this", null, "that", "  ", "", "or"};
		String[] expecteds = {"this", "that", "or"};
		assertArrayEquals(expecteds, StringUtils.removeEmpty(inputs));		
	}
	
	/**
	 * Unit test for addPostfix
	 */
	@Test
	public void testAddPrefix() {
		assertEquals("pre string", StringUtils.addPrefix("string", "pre "));
		assertEquals("string", StringUtils.addPrefix("string", null));
		assertEquals(null, StringUtils.addPrefix(null, "pre "));		
	}

	/**
	 * Unit test for addPostfix
	 */
	@Test
	public void testAddPostfix() {
		assertEquals("string post", StringUtils.addPostfix("string", " post"));
		assertEquals("string", StringUtils.addPostfix("string", null));
		assertEquals(null, StringUtils.addPostfix(null, "pre "));		
	}
	
	/**
	 * Unit test for surround
	 */
	@Test
	public void testSurround() {
		assertEquals("*string*", StringUtils.surround("string", "*"));
		assertEquals("string", StringUtils.surround("string", null));
		assertEquals(null, StringUtils.surround(null, "pre "));		
	}
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testToString_withOneArg() {		
		Object[] inputs = {"this", 5, null};
		String[] expecteds = {"this", "5", "null"};
		for (int i = 0; i < inputs.length; i++) {
			assertEquals(expecteds[i], StringUtils.toString(inputs[i]));
		}
		assertNotNull(StringUtils.toString(new Date()));
	}
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testToString_withTwoArgs() {		
		Object[] inputs = {"this", 5, null};
		String[] expecteds = {"this", "5", ""};
		for (int i = 0; i < inputs.length; i++) {
			String expected = expecteds[i];
			String actual = StringUtils.toString(inputs[i],"");
			assertEquals(expected,actual);
		}
	}
	
	/**
	 * Unit test for testFindNextParameter.
	 */
	@Test
	public void testFindNextParameter() {		
		doTestFindNextParameter("no parameter", ":", null, null);
		doTestFindNextParameter(":param is the :param", ":", "param", "is the :param");		
		doTestFindNextParameter(" is the :param", ":", "param", null);				
		doTestFindNextParameter(" is the :X:param", ":X:", "param", null);
		doTestFindNextParameter(" this :param is the :param ", ":", "param", "is the :param");
		doTestFindNextParameter(null, ":", null, null);
		
	}
	
	/**
	 * Tests expected FindNextParameter.
	 * 
	 * @param str Input string
	 * @param prf Parameter prefix
	 * @param expectedparm Expected parameter name.
	 * @param expectedRemainder Expected remainder.
	 */
	private void doTestFindNextParameter
	(String str, String prf, String expectedparm, String expectedRemainder) {
		Pair<String, String> pair = 
			StringUtils.findNextParameter(str, prf);
		if (expectedparm==null) {
			assertNull(pair.getLeft());
		} else {
			assertEquals(expectedparm, pair.getLeft());			
		}
		if (expectedRemainder==null) {
			assertNull(pair.getRight());
		} else {
			assertEquals(expectedRemainder, pair.getRight());			
		}		
	}
	
	
	/**
	 * Unit test for testFindNextParameter.
	 */
	@Test
	public void testFindParameters() {
		String string = ":in this is the :in parametrican statement :of :this metric";
		Set<String> parms = StringUtils.findParameters(string, ":");
		assertNotNull(parms);
		assertEquals(3, parms.size());
		assertTrue(parms.contains("in"));
		assertTrue(parms.contains("of"));
		assertTrue(parms.contains("this"));
		
		Set<String> empty = StringUtils.findParameters("other", ":");
		assertNotNull(empty);
		assertEquals(0, empty.size());

	}

	/**
	 * test Concat
	 */
	@Test
	public void testConcat() {
		String [] array = {"A","B","C"};
		String expected ="ABC";
		String actual = StringUtils.concat(array);
		assertEquals(expected,actual);
	}
	
	/**
	 * test ConcatSeparated
	 */
	@Test
	public void testConcatSeparated() {
		String [] array = {"A","B","C"};
		String expected ="A,B,C";
		String actual = StringUtils.concatSeparated(",", array);
		assertEquals(expected,actual);
		
		String actual2 = StringUtils.concatSeparated(",", array[0], array[1]);
		String expected2 ="A,B";
		assertEquals(expected2,actual2);
		
		String expected3 = StringConstants.EMPTY;
		String actual3 = StringUtils.concatSeparated(",", new String[0]);
		assertEquals(expected3,actual3);
	}
	
	
	/**
	 * test trim
	 */
	@Test
	public void testTrim() {
		String str = "   ABC   ";
		String expected = "ABC";
		String actual = StringUtils.trim(str);
		assertEquals(expected,actual);
		
		String str2 = null;
		String expected2 = null;
		String actual2 = StringUtils.trim(str2);
		assertEquals(expected2,actual2);
		
	}
	
	
	/**
	 * test quotes
	 */
	@Test
	public void testQuotes() {
		String str = "ABC";
		String expected = "'ABC'";
		String actual = StringUtils.quotes(str);
		assertEquals(expected,actual);
	}
	
	/**
	 * test parenthesis
	 */
	@Test
	public void testParenthesis() {
		String str = "ABC";
		String expected = "(ABC)";
		String actual = StringUtils.parenthesis(str);
		assertEquals(expected,actual);
	}
	
	/**
	 * test squareBrackets
	 */
	@Test
	public void testSquareBrackets() {
		String str = "ABC";
		String expected = "[ABC]";
		String actual = StringUtils.squareBrackets(str);
		assertEquals(expected,actual);
	}
	
	/**
	 * test curlyBrackets
	 */
	@Test
	public void testCurlyBrackets() {
		String str = "ABC";
		String expected = "{ABC}";
		String actual = StringUtils.curlyBrackets(str);
		assertEquals(expected,actual);
	}
	
	/**
	 * test quotes
	 */
	@Test
	public void testGenerics() {
		String str = "ABC";
		String expected = "<ABC>";
		String actual = StringUtils.generics(str);
		assertEquals(expected,actual);
	}
	
	
	
	/**
	 * test startsWithUpperCase
	 */
	@Test
	public void testStartsWithUpperCase() {
		String str = "Abc";
		assertTrue(StringUtils.startsWithUpperCase(str));
		
		String str2 = "abc";
		assertFalse(StringUtils.startsWithUpperCase(str2));
		
		String str3 = null;
		assertFalse(StringUtils.startsWithUpperCase(str3));
	}
	
	/**
	 * test arrayContainsString
	 */
	@Test
	public void testArrayContainsString() {
		String [] array ={"abc", "def", "ghi"};
		String str = "abc";
		Integer expected = 0;
		Integer actual = StringUtils.arrayContainsString(array,str);
		assertEquals(expected,actual);
		
		String str2 = "def";
		Integer expected2 = 1;
		Integer actual2 = StringUtils.arrayContainsString(array,str2);
		assertEquals(expected2,actual2);
		
		String str3 = "xyz";
		Integer expected3 = -1;
		Integer actual3 = StringUtils.arrayContainsString(array,str3);
		assertEquals(expected3,actual3);
	}
	
	

	/**
	 * test mid
	 */
	@Test
	public void testMid() {
	
		String oldString = "helloNewWorld";
		Integer start = 5;
		Integer length = 3;
		String newPart = "All";	
		String expected = "helloAllWorld";
		String actual = StringUtils.mid(oldString, start, length, newPart);
		assertEquals(expected,actual);
		
		String oldString2 = "helloNewWorld";
		Integer start2 = 6;
		Integer length2 = 11;
		String newPart2 = "ToAllPeople";	
		String expected2 = "helloToAllPeo";
		String actual2 = StringUtils.mid(oldString2, start2, length2, newPart2);
		assertEquals(expected2,actual2);
		
		String oldString3 = "onetwothree";
		Integer start3 = 0;
		Integer length3 = oldString3.length()-5;
		String expected3 = "onetwo";
		String actual3 = StringUtils.mid(oldString3, start3, length3);
		assertEquals(expected3,actual3);
	}
	
	/**
	 * test mid
	 */
	@Test
	public void testMid_EmptyString() {
	
		String str = "";
		Integer start = 5;
		Integer length = 3;
		String expected = "";
		String actual = StringUtils.mid(str, start, length);
		assertEquals(expected,actual);		
	}
	
	/**
	 * test mid
	 */
	@Test
	public void testMid_TwoParameters() {
		String str = "";
		Integer start = 5;
		String expected = "";
		String actual = StringUtils.mid(str, start);
		assertEquals(expected,actual);		
	}
	
	
	/**
	 * test firstChar when value is null
	 */
	@Test
	public void testFirstChar_nullValue() {
		assertNull(StringUtils.firstChar(null));
	}
	
	
	/**
	 * test firstChar when string's length is zero
	 */
	@Test
	public void testFirstChar_zeroLength() {
		assertNull(StringUtils.firstChar(""));
	}
	
	/**
	 * test firstChar when string's length is zero
	 */
	@Test
	public void testFirstChar() {
		Character expected = new Character('h');
		Character actual = StringUtils.firstChar("hello");
		assertEquals(expected,actual);
	}
	
	/**
	 * test addSpaceBeforeChar
	 */
	@Test
	public void testAddSpaceBeforeChar() {
		char c = 'l';
	    String expected = "he l lo";
	    String actual = StringUtils.addSpaceBeforeChar("hello", c);
	    assertEquals(expected,actual);
	}
	
	/**
	 * test addSpaceAfterChar
	 */
	@Test
	public void testAddSpaceAfterChar() {
		char c = 'l';
	    String expected = "hel l o";
	    String actual = StringUtils.addSpaceAfterChar("hello", c);
	    assertEquals(expected,actual);
	}
	
	
	/**
	 * test removeParenthesis
	 */
	@Test
	public void testRemoveParenthesis() {
		String s = "this (is) (it)";
	    String expected = "this is it";
	    String actual = StringUtils.removeParenthesis(s);
	    assertEquals(expected,actual);
	}
	
	/**
	 * test removeParenthesis
	 */
	@Test
	public void testRemoveCharacter() {
		String s = "this (is) (it)";
	    String expected = "thi (i) (it)";
	    String actual = StringUtils.removeCharacter(s, 's');
	    assertEquals(expected,actual);
	}
	
	/**
	 * test removeParenthesis
	 */
	@Test
	public void testRemoveCharacters() {
		String s = "this (is) (it)";
	    String expected = "hi (i) (i)";
	    String actual = StringUtils.removeCharacters(s, 's', 't');
	    assertEquals(expected,actual);
	}
	
	/**
	 * tests normalizeSpaces.
	 */
	@Test
	public void testNormalizeSpaces() {
		String input = "s a";
		String expected = "s a";
		String actual = StringUtils.normalizeSpaces(input);
		assertEquals(expected,actual);
		
		input = "s a  ";
		expected = "s a";
		actual = StringUtils.normalizeSpaces(input);
		assertEquals(expected,actual);
		
		input = "s  a ";
		expected = "s a";
		actual = StringUtils.normalizeSpaces(input);
		assertEquals(expected,actual);
		
		input = "  s a  b e";
		expected = "s a b e";
		actual = StringUtils.normalizeSpaces(input);
		assertEquals(expected,actual);
		
	}
	
	/**
	 * tests replaceIgnoringCase()
	 */
	@Test
	public void testReplaceIgnoringCase() {
		String original = "where A.name like ? AND A.age > ?";
		String toReplace = " and A.age > ?";
		String expected = "where A.name like ?";
		String actual = StringUtils.replaceIgnoringCase(original, toReplace, StringConstants.EMPTY);
		assertEquals(expected, actual);
	}
	
	/**
	 * tests uScore2camelCase
	 */	
	@Test
	public void testUScore2camelCase() {
		String[][] data = {
				{"papaki", "papaki"},
				{"Papaki", "papaki"},
				{"papa_raki", "papaRaki"},
				{"big_5", "big5"},
				{"invoice_no_of_that", "invoiceNoOfThat"},
				{"invoice___no_of__that", "invoiceNoOfThat"},
		};		
		for (int i = 0; i < data.length; i++) {			
			String expected = data[i][1];				
			String actual = StringUtils.uScore2camelCase(data[i][0]);			
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * tests cutTo
	 */
	@Test
	public void testCutTo() {
		assertEquals("To", StringUtils.cutTo("To papaki", " "));
		assertEquals("This", StringUtils.cutTo("ThisIsIt", "Is"));
		assertEquals("To papaki", StringUtils.cutTo("To papaki", "X"));
		assertEquals("", StringUtils.cutTo("To papaki", "T"));
	}
	
	/**
	 * tests xmlEndTag
	 */
	@Test
	public void testXmlEndTag() {
		assertEquals("</this>", StringUtils.xmlEndTag("this"));
	}

	/**
	 * tests xmlEndTag
	 */
	@Test
	public void testXmlStartTag() {
		assertEquals("<this>", StringUtils.xmlStartTag("this"));
	}
	
	/**
	 * tests startsWith
	 */
	@Test
	public void testStartsWith() {
		Set<String> prefixes = new HashSet<String>();
		prefixes.add("this");
		prefixes.add("that");
		assertTrue(StringUtils.startsWith("this is not me", prefixes));
		assertTrue(StringUtils.startsWith("that is not me", prefixes));
		assertFalse(StringUtils.startsWith("I am not there", prefixes));
	}
	
	/**
	 * tests startsWith
	 */
	@Test
	public void testRemovePartBetweenElements() {
		String[][] data = {
			{"this START is not END ok", "this  ok"}, 
			{"this is not END ok", "this is not END ok"},
			{"this is not END START ok", "this is not END START ok"},
			{"this END is START is not END ok", "this END is  ok"}, 
		};
		for (int i = 0; i < data.length; i++) {			
			String expected = data[i][1];				
			String actual = StringUtils.removePartBetweenElements(data[i][0], "START", "END");			
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * tests containsGreekLatinNumbersSpaces
	 */
	@Test
	public void testContainsOnlyLettersNumbersSpaces() {
        System.out.println("\u0428 XZ 2525"); //"\u0428 is a cyrillic letter. 
		
		String s = "\u0428 XZ 2525";
		assertTrue(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = "asdf9101";
		assertTrue(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = "asdfΑΣΔ123";
		assertTrue(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = "##asdf";
		assertFalse(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = " a s";
		assertTrue(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = "as92.";
		assertFalse(StringUtils.containsOnlyLettersNumbersSpaces(s));
		
		s = "as92\"";
		assertFalse(StringUtils.containsOnlyLettersNumbersSpaces(s));

	}
	
	/**
	 * test IgnoreCaseValueOf()
	 */
	@Test
	public void testIgnoreCaseValueOf() {
		String[] strings = {"a", "de", "B"};
		String actual = StringUtils.ignoreCaseValueOf(strings, "DE");
		Assert.assertEquals(strings[1], actual);
		
		String noMatch = StringUtils.ignoreCaseValueOf(strings, "aDE");
		Assert.assertNull(noMatch);
		
	}
	
	
	
	/**
	 * tests truncateCharsFromEnd
	 */
	@Test
	public void testTruncateCharsFromEnd() {
		String sample = "lala";
		assertEquals("l", StringUtils.truncateCharsFromEnd(sample, 3));
		assertEquals(StringConstants.EMPTY, StringUtils.truncateCharsFromEnd(sample, 4));
		assertEquals(StringConstants.EMPTY, StringUtils.truncateCharsFromEnd(sample, 5));
		sample = null;
		assertNull(StringUtils.truncateCharsFromEnd(sample, 5));
	}
	
	/**
	 * tests splitByLength
	 */
	@Test
	public void testSplitByLength() {
		String sample = "lala";
		
		for(String s : StringUtils.splitByLength(sample, 2)){
			assertEquals("la", s);
		}		
		assertEquals(0, StringUtils.splitByLength(StringConstants.EMPTY, 2).length);
		assertEquals(sample, StringUtils.splitByLength(sample, 0)[0]);
		sample = null;
		assertEquals(0, StringUtils.splitByLength(sample, 2).length);
	}
	
	/**
	 * Tests length. 
	 */
	@Test
	public void testLength() {
		Assert.assertEquals(0, StringUtils.length(null));
		Assert.assertEquals(0, StringUtils.length(""));
		Assert.assertEquals("XXX".length(), StringUtils.length("XXX"));
	}
	
	/**
	 * Tests length. 
	 */
	@Test
	public void testSquareBracketsWithMandatoryContent() {
		Assert.assertEquals(EMPTY, StringUtils.squareBracketsWithMandatoryContent(null));
		Assert.assertEquals(EMPTY, StringUtils.squareBracketsWithMandatoryContent(EMPTY));
		Assert.assertEquals("[XXXX]", StringUtils.squareBracketsWithMandatoryContent("XXXX"));
	}
	
	/**
	 * Tests StripPkgFromFqcn(s). 
	 */
	@Test	
	public void testStripPkgFromFqcn() {
		String actual = StringUtils.stripPkgFromFqcn("gr.com.gr.Foo");
		Assert.assertEquals("Foo", actual);
		
		String zara = "Zaratustra";
		String tustra = StringUtils.stripPkgFromFqcn(zara);
		Assert.assertEquals(zara, tustra);
	}
	
	/**
	 * Tests removeAllButLetters(string).
	 */
	@Test
	public void testRemoveAllButLetters() {
		String string = " +* Xx δεν 21 πάω! *";
		String expected = "Xxδενπάω";
		String actual = StringUtils.removeAllButLetters(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Tests removeAllButDigits(string).
	 */
	@Test
	public void testRemoveAllButDigits() {
		String string = "+* Xx δεν 21 πάω! *";
		String expected = "21";
		String actual = StringUtils.removeAllButDigits(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Tests removeAllButLettersAndDigits(string).
	 */
	@Test
	public void testRemoveAllButLettersAndDigits() {
		String string = " Xx δεν 21 πάω! **";
		String expected = "Xxδεν21πάω";
		String actual = StringUtils.removeAllButLettersAndDigits(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Tests removeAllButLettersAndDigits(string).
	 */
	@Test
	public void testDoubleQuotes() {
		String string = "Xx!";
		String expected = "\"Xx!\"";
		String actual = StringUtils.doubleQuotes(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Tests removeAllButLettersAndDigits(string).
	 */
	@Test
	public void testTruncateToLength() {
		String string = "Xx δεν 21 πάω!";
		String expected = "Xx δε";
		String actual = StringUtils.truncateToLength(string, 5);
		Assert.assertEquals(expected, actual);
	}
	
}
