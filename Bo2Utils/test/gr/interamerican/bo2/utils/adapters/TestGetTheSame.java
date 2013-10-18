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
package gr.interamerican.bo2.utils.adapters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetTheSame}.
 */
public class TestGetTheSame {
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		GetTheSame<Object> adapter = new GetTheSame<Object>();
		Object in = new Object();
		Object out = adapter.execute(in);
		Assert.assertSame(in, out);
	}

}
