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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.PersistentObject;

import org.junit.Test;

/**
 * Unit tests for {@link PoKeyFormatter}.
 */
public class TestPoKeyFormatter {

	/**
	 * PoKeyWriter to test
	 */
	PoKeyFormatter formatter = new PoKeyFormatter();
	
	/**
	 * test
	 */
	@Test
	public void testFormat_withNonNullKey(){
		Sample m = new Sample();
		m.setKey(1);
		String expected = m.getKey().toString(); 
		String actual = formatter.format(m);
		assertEquals(expected,actual);
	}
	
	/**
	 * test
	 */
	@Test
	public void testFormat_withNullKey(){
		Sample m = new Sample();		
		String actual = formatter.format(m);
		assertNull(actual);
	}
	
	
	/**
	 * sample class
	 */
	private class Sample implements PersistentObject<Integer>{

		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Key.
		 */
		Integer key;

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;			
		}

		public void tidy() {
			// empty			
		}

	}

	
}
