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
package gr.interamerican.bo2.creation.update;

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.samples.empty.IEmpty1Impl1;
import gr.interamerican.bo2.samples.iempty.IEmpty1;
import gr.interamerican.bo2.samples.iempty.IEmpty4;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AddingInterfacesClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestAddingInterfacesClassUpdater {	
	/**
	 * code for fields.
	 */	
	String[] interfaces = {
		Serializable.class.getName(),
		IEmpty4.class.getName()
	};	
	
	/**
	 * Unit test for update.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassCreationException 
	 */	
	@Test
	public void testUpdate() 
	throws InstantiationException, IllegalAccessException, ClassCreationException {
		String newName = "gr.interamerican.synth.AddingInterfacesClassUpdaterTesterClass1";		
		AddingInterfacesClassUpdater updater = new AddingInterfacesClassUpdater(interfaces);
		Class<?> newType = updater.update(IEmpty1Impl1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();		
		Assert.assertTrue(object instanceof Serializable);
		Assert.assertTrue(object instanceof IEmpty4);
		Assert.assertTrue(object instanceof IEmpty1);
	}
	
	/**
	 * Unit test for update.
	 * 
	 * @throws ClassCreationException 
	 */	
	@Test(expected=ClassCreationException.class)
	public void testUpdate_withClass() 
	throws ClassCreationException {
		String newName = "gr.interamerican.synth.AddingInterfacesClassUpdaterTesterClass2";	
		String[] invalids = {"java.lang.Integer"};
		AddingInterfacesClassUpdater updater = new AddingInterfacesClassUpdater(invalids);
		updater.update(IEmpty1Impl1.class, newName);
	}
	
	/**
	 * Unit test for update.
	 * 
	 * @throws ClassCreationException 
	 */	
	@Test(expected=ClassCreationException.class)
	public void testUpdate_withNoInterface() 
	throws ClassCreationException {
		String newName = "gr.interamerican.synth.AddingInterfacesClassUpdaterTesterClass2";	
		String[] invalids = {"com.foo.Bar"};
		AddingInterfacesClassUpdater updater = new AddingInterfacesClassUpdater(invalids);
		updater.update(IEmpty1Impl1.class, newName);
	}
	
	

}
