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
public class TestFactorySupportedPoDeleter {
	
	
	
	
	/**
	 * Tests delete(k).
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testDelete() throws DataException {
		FactorySupportedPoDeleter<UserKey, User> deleter = 
			new FactorySupportedPoDeleter<UserKey, User>(User.class);
				
		User expected = new User();
		PersistenceWorker<User> pw = mock(PersistenceWorker.class);		
		when(pw.read(any(User.class))).thenReturn(expected);
		deleter.pw = pw;
		UserKey key = new UserKey();		
		deleter.delete(key);
		verify(pw, times(1)).delete(any(User.class));
	}
	
	
	/**
	 * Tests deleteByProperties(k).
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetByProperties() throws DataException {
		FactorySupportedPoDeleter<UserKey, User> deleter = 
			new FactorySupportedPoDeleter<UserKey, User>(User.class);
		Identified<Integer> identifier = Factory.create(Identified.class);		
		User expected = new User();
		PersistenceWorker<User> pw = mock(PersistenceWorker.class);
		when(pw.read(any(User.class))).thenReturn(expected);				
		deleter.pw = pw;
		deleter.deleteByProperties(identifier);
		verify(pw, times(1)).delete(any(User.class));
	}
	
	
}
