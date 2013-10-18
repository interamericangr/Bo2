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
package gr.interamerican.bo2.arch.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

import org.junit.Before;
import org.junit.Test;



/**
 * TestCrudOperations
 */
public class TestCrudOperationsWithHibernate {

	
	/**
	 * CrudOperations
	 */
	private CrudOperations crudOperation;
	
	/**
	 * CompanyUser
	 */
	CompanyUser po = Factory.create(CompanyUser.class);
	
	/**
	 * Persistence worker.
	 */
	private PersistenceWorker<CompanyUser> pw = Factory.createPw(CompanyUser.class);
	
	/**
	 * Deletes the user with the specified id.
	 * @param id
	 * @throws DataException 
	 */
	private void delete(Long id) throws DataException {
		CompanyUser p = Factory.create(CompanyUser.class);
		p.setId(id);
		try {
			p = pw.read(p);
			pw.delete(p);
		} catch (PoNotFoundException e) {
			/* do nothing */
		}
		
	}
	
	/**
	 * Tests setup.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Before
	public void setup() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				pw.init(this.getProvider());
				pw.open();
				delete(10L);
				delete(11L);
				delete(12L);
				delete(13L);
				pw.close();
			}
		}.execute();
	}
	
	
	/**
	 * Test store
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testPerform_store() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setId(10L);
				po.setSex(Sex.MALE);
				pw.init(this.getProvider());
				pw.open();				
				crudOperation= CrudOperations.STORE;
				crudOperation.perform(po, pw);
				assertEquals(Sex.MALE,po.getSex());
				pw.close();
			}
		}.execute();
	}
	
	/**
	 * Test Read
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testPerform_read() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setId(11L);
				pw.init(this.getProvider());
				pw.open();								
				crudOperation= CrudOperations.STORE;
				crudOperation.perform(po, pw);
				crudOperation= CrudOperations.READ;
				assertNotNull(crudOperation.perform(po, pw));
				pw.close();
			}
		}.execute();
	}
	
	
	/**
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testPerform_update() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setId(12L);
				po.setSex(Sex.FEMALE);
				pw.init(this.getProvider());
				pw.open();							
				crudOperation= CrudOperations.STORE;
				crudOperation.perform(po, pw);
				po.setSex(Sex.MALE);
				crudOperation= CrudOperations.UPDATE;
				crudOperation.perform(po, pw);
				assertEquals(Sex.MALE,po.getSex());
				pw.close();
			}
		}.execute();
	}
	
	
	
	/**Tests delete
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected = PoNotFoundException.class)
	public void testPerform_delete() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setId(13L);
				pw.init(this.getProvider());
				pw.open();
				crudOperation= CrudOperations.STORE;
				crudOperation.perform(po, pw);
			    crudOperation= CrudOperations.DELETE;
				crudOperation.perform(po, pw);
			    crudOperation= CrudOperations.READ;
				crudOperation.perform(po, pw);
				pw.close();
			}
		}.execute();
	}
	
	/**
	 * Tests the count.
	 */
	@Test
	public void testCount() {
		assertEquals(4, CrudOperations.values().length);
	}
	
}

