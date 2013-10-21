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
package gr.interamerican.bo2.impl.open.transformations;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ObjectPropertiesAnalyzer}.
 */
public class TestObjectPropertiesAnalyzer {

	/**
	 * Test execute()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Bean b = new Bean();
		b.setName("name");
		b.setPass("pass");
		b.setPassword("password");
		b.setFoo(null);
		String output = new ObjectPropertiesAnalyzer().execute(b).toString();
		Assert.assertTrue(output.contains("name"));
		Assert.assertTrue(output.contains("null"));
		Assert.assertTrue(output.contains("foo"));
		Assert.assertFalse(output.contains("pass"));
		Assert.assertFalse(output.contains("password"));
	}
	
	@SuppressWarnings("all")
	class Bean {
		String name;
		String pass;
		String password;
		String foo;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFoo() {
			return foo;
		}
		public void setFoo(String string) {
			this.foo = string;
		}
	}

}
