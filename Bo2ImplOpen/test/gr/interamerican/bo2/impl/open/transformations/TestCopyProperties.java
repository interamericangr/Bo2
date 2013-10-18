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

import gr.interamerican.bo2.arch.Named;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.Utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CopyProperties}.
 */
public class TestCopyProperties {
	
	/**
	 * test
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Long code = 1L;
		String name = "name";
		
		TypedSelectableImpl<Long> ts = new TypedSelectableImpl<Long>();
		ts.setCode(code);
		ts.setName(name);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		CopyProperties<TypedSelectable, Named> copy = 
			Utils.cast(new CopyProperties (TypedSelectable.class, Named.class));
		
		Named named = copy.execute(ts);
		Assert.assertNotNull(named);
		Assert.assertEquals(named.getName(), ts.getName());
	}
	
	
	
	
}
