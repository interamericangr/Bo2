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
package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.samples.archutil.po.User;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link SetModificationRecord}.
 */
public class TestSetModificationRecord {
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		ModificationRecord mr = Mockito.mock(ModificationRecord.class);
		SetModificationRecord<User> set = 
			new SetModificationRecord<User>(mr);
		Assert.assertEquals(mr, set.record);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute() {
		ModificationRecord mr = new ModificationRecordImpl();
		Date dt = new Date();
		mr.setLastModified(dt);
		String by = "BY"; //$NON-NLS-1$
		mr.setLastModifiedBy(by);		
		SetModificationRecord<User> set = 
			new SetModificationRecord<User>(mr);
		User user = new User();
		set.execute(user);
		Assert.assertEquals(dt, user.getLastModified());
		Assert.assertEquals(by, user.getLastModifiedBy());		
	}
	
	

}
