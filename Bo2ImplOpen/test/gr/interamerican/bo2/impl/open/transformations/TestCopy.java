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

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CopyProperties}.
 */
public class TestCopy {
	
	/**
	 * test
	 */
	@SuppressWarnings({"nls"})	
	@Test
	public void testCopy() {
		BeanWith2Fields one = new BeanWith2Fields();
		one.setField1("one");
		one.setField2(2);
		
		Copy<BeanWith2Fields> copy = new Copy<BeanWith2Fields> (BeanWith2Fields.class);
		
		BeanWith2Fields copied = copy.copy(one);
		
		Assert.assertEquals(one.getField1(), copied.getField1());
		Assert.assertEquals(one.getField2(), copied.getField2());
		
	}
	
	
	
	
}
