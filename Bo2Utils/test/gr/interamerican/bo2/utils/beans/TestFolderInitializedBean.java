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
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for {@link FolderInitializedBean}.
 */
@SuppressWarnings("nls")
public class TestFolderInitializedBean {
	
	/**
	 * package for bean initialization.
	 */
	private String pack1 = "/gr/interamerican/bo2/utils/beans/fib1/"; 

	
	/**
	 * Tests a bean that can be initialized.
	 */
	@Test
	public void testInitialized_fromClassPath() {
		Fib fib = new Fib(pack1, true);
		assertEquals("field1", fib.getField1());
		assertEquals("field2", fib.getField2());
	}
	
	/**
	 * Tests a bean that can be initialized.
	 */
	@Test
	public void testInitialized_fromFolderPath() {
		String deploymentPropertiesPath = "/gr/interamerican/bo2/deployparms/deployment.properties";
		Properties p = CollectionUtils.readProperties(deploymentPropertiesPath);
		String workDir = p.getProperty("streamsWorkDirectory");
		String path = workDir + "fib1/";
		Fib fib = new Fib(path, false);
		assertEquals("field1", fib.getField1());
		assertEquals("field2", fib.getField2());
	}
	
	/**
	 * Tests failure of initialiazation.
	 */	
	@Test
	public void testNonInitialized() {
		Fib fib = new Fib("/com/foo/", true);
		assertNull(fib.getField1());
		assertNull(fib.getField2());
	}

	
	
	/**
	 * Sample bean for testing.
	 */
	private static class Fib extends FolderInitializedBean {
		/**
		 * field 1
		 */
		private String field1;
		
		/**
		 * field 2
		 */
		private String field2;		

		/**
		 * Creates a new Fib object. 
		 *
		 * @param path
		 * @param inClassPath
		 */
		private Fib(String path, boolean inClassPath) {
			super(path, inClassPath);
		}
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
		@SuppressWarnings("unused")
		public void setField1(String field1) {
			this.field1 = field1;
		}
		/**
		 * Gets the field2.
		 *
		 * @return Returns the field2
		 */
		public String getField2() {
			return field2;
		}
		
		
	}

}
