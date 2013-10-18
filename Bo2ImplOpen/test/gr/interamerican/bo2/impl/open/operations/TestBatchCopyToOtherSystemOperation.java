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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.CrudCmd;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This test works because the object graph that is to be
 * copied does not include many to one references.
 */
public class TestBatchCopyToOtherSystemOperation {
	/**
	 * User No 1.
	 */
	private static final Integer UserNO1 = 555;
	
	/**
	 * User No 2.
	 */
	private static final Integer UserNO2 = 556;
	
	/**
	 * From manager.
	 */
	private static final String FROM = "LOCALDB"; //$NON-NLS-1$
	
	/**
	 * To manager.
	 */
	private static final String TO = "OTHERDB"; //$NON-NLS-1$
	
	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<User> crudFrom;
	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<User> crudTo;	
	
	/**
	 * Test sample.
	 */
	User sample1;
	
	/**
	 * Test sample.
	 */
	User sample2;
	
	/**
	 * Creates a new TestCopyToOtherSystemOperation object. 
	 *
	 */
	public TestBatchCopyToOtherSystemOperation() {
		super();
		PersistenceWorker<User> from = Factory.createPw(User.class);
		AbstractResourceConsumer rcFrom = (AbstractResourceConsumer) from;
		rcFrom.setManagerName(FROM);
		crudFrom = new CrudCmd<User>(from,true);
		
		PersistenceWorker<User> to = Factory.createPw(User.class);
		AbstractResourceConsumer rcTo = (AbstractResourceConsumer) to;
		rcTo.setManagerName(TO);
		crudTo = new CrudCmd<User>(to,true);
		
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		sample1 = factory.sampleUser(UserNO1, 3);
		sample1.setId(UserNO1);
		
		sample2 = factory.sampleUser(UserNO2, 2);
		sample2.setId(UserNO2);
	}

	/**
	 * Test setup.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Before
	public void setup() throws UnexpectedException, DataException, LogicException {		
		crudFrom.delete(sample1);
		crudFrom.delete(sample2);
		crudTo.delete(sample1);
		crudTo.delete(sample2);
		crudFrom.store(sample1);
		crudFrom.store(sample2);
	}
	
	/**
	 * Test tear down.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@After
	public void teardown() throws UnexpectedException, DataException, LogicException {
		crudFrom.delete(sample1);
		crudFrom.delete(sample2);
		crudTo.delete(sample1);
		crudTo.delete(sample2);
	}	
	
	/**
	 * Unit test for execute.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {				
				BatchCopyToOtherSystemOperation<User, UserKey> op =
					new BatchCopyToOtherSystemOperation<User, UserKey>(User.class, FROM, TO);
				
				Set<UserKey> keys = new HashSet<UserKey>();
				keys.add(sample1.getKey());
				keys.add(sample2.getKey());
				op.setKeys(keys);
				
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
			}
		}.execute();
		
		User copied = crudTo.read(sample1);
		Assert.assertEquals(sample1, copied);
		
		User copied2 = crudTo.read(sample2);
		Assert.assertEquals(sample2, copied2);
	}

}
