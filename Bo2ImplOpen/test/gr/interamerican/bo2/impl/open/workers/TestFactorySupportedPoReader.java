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
package gr.interamerican.bo2.impl.open.workers;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.samples.ibean.Identified;

/**
 * Unit test for {@link FactorySupportedPoHandler}.
 */
@SuppressWarnings("unchecked")
public class TestFactorySupportedPoReader {

	/**
	 * Tests get(k).
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testGet() throws DataException {
		FactorySupportedPoReader<UserKey, User> reader = 
			new FactorySupportedPoReader<UserKey, User>(User.class);
				
		User expected = new User();
		PersistenceWorker<User> pw = mock(PersistenceWorker.class);		
		when(pw.read(any(User.class))).thenReturn(expected);
		reader.pw = pw;
		UserKey key = new UserKey();		
		User actual = reader.get(key);
		Assert.assertEquals(expected, actual);
	}
	
	
	/**
	 * Tests getByProperties(k).
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetByProperties() throws DataException {
		FactorySupportedPoReader<UserKey, User> reader = 
			new FactorySupportedPoReader<UserKey, User>(User.class);
		Identified<Integer> identifier = Factory.create(Identified.class);		
		User expected = new User();
		PersistenceWorker<User> pw = mock(PersistenceWorker.class);
		when(pw.read(any(User.class))).thenReturn(expected);				
		reader.pw = pw;
		User actual = reader.getByProperties(identifier);
		Assert.assertEquals(expected, actual);
	}
}