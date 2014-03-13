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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import org.junit.Test;

/**
 * Unit tests for {@link AbstractIntTypesBoPropertyDescriptor}.
 */
public class TestAbstractIntTypesBoPropertyDescriptor {
	
	/**
	 * Test setLengthOfDecimalPart
	 */
	@Test
	public void testSetLengthOfDecimalPart() {
		Descriptor descriptor = new Descriptor();
		assertEquals(0, descriptor.getLengthOfDecimalPart());
		descriptor.setLengthOfDecimalPart(1);
		assertEquals(0, descriptor.getLengthOfDecimalPart());
	}
	
	/**
	 * Implementation of descriptor for the tests.
	 */
	private class Descriptor extends AbstractIntTypesBoPropertyDescriptor<Integer> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new Descriptor object. 
		 */
		public Descriptor() {
			super(null);			
		}
		
		@Override
		protected Formatter<Integer> getFormatter() {
			return null;
		}
	}
	
}
