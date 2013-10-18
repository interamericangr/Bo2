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
package gr.interamerican.bo2.impl.open.runtime;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.operations.EmptyOperation;

import org.junit.Test;

/**
 * 
 */
public class TestAbstractBo2RuntimeCmd {
	
	/**
	 * Unit test for the constructor.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testConstructor() throws LogicException, DataException, UnexpectedException {
		AbstractBo2RuntimeCmd cmd = new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				/* empty */
			}
		};
		cmd.execute();
	}
	

	
	/**
	 * Unit test.
	 * 
	 * This unit test expects to find a logical file definition
	 * with the name SAMPLE_PATH5 in the deployment properties file.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testCreate() throws LogicException, DataException, UnexpectedException {
		AbstractBo2RuntimeCmd cmd = new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				User user = create(User.class);
				assertNotNull(user);
			}
		};
		cmd.execute();
	}
	
	/**
	 * Unit test.
	 * 
	 * This unit test expects to find a logical file definition
	 * with the name SAMPLE_PATH5 in the deployment properties file.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testOpen() throws LogicException, DataException, UnexpectedException {
		AbstractBo2RuntimeCmd cmd = new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				EmptyOperation op = open(EmptyOperation.class);
				assertNotNull(op);
			}
		};
		cmd.execute();
	}
	
	/**
	 * Unit test.
	 * 
	 * This unit test expects to find a logical file definition
	 * with the name SAMPLE_PATH5 in the deployment properties file.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testOpenPw() throws LogicException, DataException, UnexpectedException {
		AbstractBo2RuntimeCmd cmd = new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				User user = create(User.class);
				assertNotNull(user);
				user.setId(78945);
				
				user.setName("this"); //$NON-NLS-1$
				user.setUsrid("this");//$NON-NLS-1$
				user.setRoleId(1);
				
				PersistenceWorker<User> pw = openPw(User.class);
				assertNotNull(pw);
				try {
					user = pw.delete(user);
				} catch (PoNotFoundException pnfe) {
					/* ignore it */
				}				
				user = pw.store(user);			
				User user2 = pw.read(user);
				assertEquals(user, user2);
				user = pw.delete(user);
			}
		};
		cmd.execute();
	}

}
