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
package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.exception.ClassCreationException;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CreatorForConcreteClasses}.
 */
public class TestCreatorForConcreteClasses {
	/**
	 * object to test.
	 */
	CreatorForConcreteClasses creator = new CreatorForConcreteClasses();
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 */
	@Test
	public void testCreate() throws ClassCreationException {
		Class<?> clazz = creator.create(Integer.class);
		Assert.assertEquals(Integer.class, clazz);
	}
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testCompileTimeClassName()  {
		String rt = "com.foo.Bar"; //$NON-NLS-1$
		String ct = creator.compileTimeClassName(rt);
		Assert.assertEquals(rt, ct);
	}
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testRunTimeClassName() {
		String ct = "com.foo.Bar"; //$NON-NLS-1$
		String rt = creator.runTimeClassName(ct);
		Assert.assertEquals(ct, rt);
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withInvalidType() 
	throws ClassCreationException {
		creator.create(List.class);		
	}

}
