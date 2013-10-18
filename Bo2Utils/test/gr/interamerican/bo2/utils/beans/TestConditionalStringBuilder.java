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

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.StringConstants;

import org.junit.Test;

/**
 * Unit tests for {@link ConditionalStringBuilder}.
 */
@SuppressWarnings("nls")
public class TestConditionalStringBuilder {
	/**
	 * Unit test for addFragment()
	 */
	@Test
	public void testAppendIf() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		sb.appendIf("this", true);
		String expected = "this";
		assertEquals(1, sb.fragments.size());
		assertEquals(expected, sb.fragments.get(0));
	}
	
	/**
	 * Unit test for addFragment()
	 */
	@Test
	public void testAddIfNotNull() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		sb.appendIfNotNull("this", new Object());
		String expected = "this";
		assertEquals(1, sb.fragments.size());		
		assertEquals(expected, sb.fragments.get(0));
		sb.appendIfNotNull("this", null);
		assertEquals(1, sb.fragments.size());
	}
	
	
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testToString_withoutSeparator() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		assertEquals("", sb.toString());
		sb.appendIf("this", true);
		assertEquals("this",sb.toString());
		sb.appendIf("this", false);
		assertEquals("this",sb.toString());
		sb.appendIf("that", true);
		assertEquals("thisthat",sb.toString());
	}
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testToString_withSeparator() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		sb.setSeparator("-");
		assertEquals("", sb.toString());
		sb.appendIf("this", true);
		assertEquals("this",sb.toString());
		sb.appendIf("this", false);
		assertEquals("this",sb.toString());
		sb.appendIf("that", true);
		assertEquals("this-that",sb.toString());
		sb.appendIf("we", true);
		assertEquals("this-that-we",sb.toString());
	}
	
	
	/**
	 * Unit test for setSeparator()
	 */
	@Test
	public void testSetSeparator() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		String sep = "A";
		sb.setSeparator(sep);
		assertEquals(sep, sb.separator);
	}
	
	/**
	 * Unit test for setSeparator()
	 */
	@Test
	public void testGetSeparator() {
		ConditionalStringBuilder sb = new ConditionalStringBuilder();
		assertEquals(sb.getSeparator(), StringConstants.EMPTY);
		String sep = "A";
		sb.setSeparator(sep);
		assertEquals(sep, sb.getSeparator());
	}
	
	
	/**
	 * Bean for test.
	 */
	@SuppressWarnings("unused")
	private class Bean {
		/**
		 * field 1.
		 */
		String field1;
		/**
		 * field 2.
		 */
		Integer field2;
		/**
		 * Gets the field1.
		 *
		 * @return Returns the field1
		 */
		public String getField1() {
			return field1;
		}
		/**
		 * Assigns a new value to the field1.
		 *
		 * @param field1 the field1 to set
		 */
		public void setField1(String field1) {
			this.field1 = field1;
		}
		/**
		 * Gets the field2.
		 *
		 * @return Returns the field2
		 */
		public Integer getField2() {
			return field2;
		}
		/**
		 * Assigns a new value to the field2.
		 *
		 * @param field2 the field2 to set
		 */
		public void setField2(Integer field2) {
			this.field2 = field2;
		}		
	}

}
