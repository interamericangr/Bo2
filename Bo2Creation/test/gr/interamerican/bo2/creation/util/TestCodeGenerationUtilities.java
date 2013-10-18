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
package gr.interamerican.bo2.creation.util;

import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.cleanJavaCode;
import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.cleanUpGenericFromType;
import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.clearJavaComments;
import gr.interamerican.bo2.creation.exception.ClassCreationException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CodeGenerationUtilities}.
 */
public class TestCodeGenerationUtilities {
	
	/**
	 * Unit test for cleanUpGenericFromType(string).
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCleanUpGenericFromType() {
		String[] types = {
			"com.foo.Bar",
			"Bar",
			"com.foo.Bar<java.lang.Integer>",
			"com.foo.Bar<?>",
			"Bar<Foo>",
			"Bar<com.bar.Foo>"
		};
		String[] cleaned = {
				"com.foo.Bar",
				"Bar",
				"com.foo.Bar",
				"com.foo.Bar",
				"Bar",
				"Bar"
		};
		for (int i = 0; i < types.length; i++) {
			Assert.assertEquals(cleaned[i], cleanUpGenericFromType(types[i]));
		}
		
	}
	
	/**
	 * Unit test for cleanUpGenericFromType(string).
	 */
	@Test
	@SuppressWarnings("nls")
	public void testClearJavaComments() {
		String[] codes = {
			"/**\n * comment\n */\npublic void doThat(){\n//print hello\nSystem.out.println(\"hello\");\n}/*end of method*/\n",
			"/**\n * comment\n */\npublic in myField; //integer field\n",
			"//comment\n//other comment\n",
		};
		String[] cleaned = {
				"\npublic void doThat(){\nSystem.out.println(\"hello\");\n}\n",
				"\npublic in myField; ",
				"",
			};
		for (int i = 0; i < codes.length; i++) {
			Assert.assertEquals(cleaned[i], clearJavaComments(codes[i]));
		}
		
	}
	
	/**
	 * Unit test for cleanUpGenericFromType(string).
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCleanJaveCode() {
		String[] codes = {
			"\n/**\n * comment\n */\npublic void doThat(){\n//print hello\nSystem.out.println(\"hello\");\n}/*end of method*/\n\n",
			"/**\n * comment\n */\n\npublic int myField; //integer field\n",
			"//comment\n//other comment\n",
		};
		String[] cleaned = {
				"public void doThat(){\nSystem.out.println(\"hello\");\n}",
				"public int myField;",
				""
			};
		for (int i = 0; i < codes.length; i++) {
			Assert.assertEquals(cleaned[i], cleanJavaCode(codes[i]));
		}
		
	}
	
	
	/**
	 * Unit test for typeNotSupported(clazz).
	 */
	@Test
	public void testTypeNotSupported() {
		ClassCreationException ex = CodeGenerationUtilities.typeNotSupported(String.class);
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}

}
