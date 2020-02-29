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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.impl.open.operations.util.ChildEntityInfo;
import gr.interamerican.bo2.impl.open.operations.util.CopyComplexEntityChildConfiguration;
import gr.interamerican.bo2.impl.open.operations.util.CopyComplexEntityConfiguration;
import gr.interamerican.bo2.impl.open.operations.util.CopyMode;
import gr.interamerican.bo2.impl.open.workers.AbstractBaseWorker;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.samples.implopen.queries.DummyCustomerKeyQuery;
import gr.interamerican.bo2.samples.implopen.queries.DummyCustomerQuery;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerKP;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.mod.GetTheSame;

/**
 * Unit test of {@link CopyComplexEntityOperation}.
 */
public class TestCopyComplexEntityOperation extends Bo2BaseTest{

	/**
	 * Configuration used in tests.
	 */
	CopyComplexEntityConfiguration<CustomerKP, Customer> config;

	/**
	 * The tested object.
	 */
	CopyComplexEntityOperation<CustomerKP> tested;

	/**
	 * Method that initializes objects that will be tested.
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void buildUp() {
		config = Factory.create(CopyComplexEntityConfiguration.class);
		tested = new CopyComplexEntityOperation<CustomerKP>(config);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#CopyComplexEntityOperation(CopyComplexEntityConfiguration)}.
	 */
	@Test
	public void testConstructor() {
		config.setRootCopyMode(CopyMode.KEEP_TARGET);
		tested = new CopyComplexEntityOperation<CustomerKP>(config);
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#execute()}.
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() throws DataException, LogicException {
		CopyComplexEntityChildConfiguration<CustomerKP> childConf = new CopyComplexEntityChildConfiguration<CustomerKP>();
		config = spy(Factory.create(CopyComplexEntityConfiguration.class));
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		tested.fromManager = StringConstants.EIGHT;
		tested.toManager = StringConstants.SEVEN;
		
		doReturn(childConf).when(config).getBefore();
		doReturn(childConf).when(config).getAfter();
		doNothing().when(tested).processChildConfiguration(childConf);
		doNothing().when(tested).processRootEntity();
		
		tested.execute();
		
		verify(tested,times(2)).processChildConfiguration(childConf);
		verify(tested).processRootEntity();
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#processRootEntity()}.
	 * @throws DataException 
	 */
	@Test
	public void testProcessRootEntity() throws DataException {
		Customer sampleCustomer = Factory.create(Customer.class);
		PersistenceWorker<Customer> sampleCustomerPw = MockUtils.mockPw(sampleCustomer, null, null);

		config.setRootEntity(Customer.class);
		config.setRootModifications(null);
		config.setRootCopyMode(CopyMode.KEEP_TARGET);
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		tested.input = Factory.create(CustomerKey.class);
		
		doReturn(sampleCustomerPw).when(tested).getWorker(Customer.class, null);
		doNothing().when(tested).copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET, null);
		
		tested.processRootEntity();
		verify(tested).copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET, null);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#processChild(ChildEntityInfo)}.
	 * @throws DataException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessChild() throws DataException {
		DummyCustomerQuery sampleQuery = MockUtils.mockEntitiesQuery(DummyCustomerQuery.class, Factory.create(Customer.class));
		DummyCustomerKeyQuery sampleKeyQuery = Factory.create(DummyCustomerKeyQuery.class);
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));

		ChildEntityInfo<CustomerKP, CustomerKey, Customer> child = spy(Factory.create(ChildEntityInfo.class));
		child.setQueryByKey(sampleKeyQuery);
		child.setPoClz(Customer.class);
		child.setCopyMode(CopyMode.KEEP_TARGET);
		
		when(child.getQuery()).thenReturn(null, sampleQuery);
		doNothing().when(tested).readChildsByKey(sampleKeyQuery,Customer.class, CopyMode.KEEP_TARGET, null);
		doNothing().when(tested).readChilds(sampleQuery,Customer.class, CopyMode.KEEP_TARGET, null);
		
		tested.processChild(child);
		verify(tested).readChildsByKey(sampleKeyQuery,Customer.class, CopyMode.KEEP_TARGET, null);
		
		tested.processChild(child);
		verify(tested).readChilds(sampleQuery,Customer.class, CopyMode.KEEP_TARGET, null);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#readChilds(EntitiesQuery, Class, CopyMode, List)}.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadChilds() throws DataException {
		DummyCustomerQuery sampleQuery = spy(new DummyCustomerQuery());
		Customer sampleCustomer = Factory.create(Customer.class);
		ArrayList<Modification<Customer>> sampleModifications = new ArrayList<Modification<Customer>>();
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));

		doNothing().when(tested).initializeQuery(sampleQuery);
		when(sampleQuery.next()).thenReturn(true).thenReturn(false);
		doReturn(sampleCustomer).when(sampleQuery).getEntity();
		doNothing().when(tested).copyEntity(any(Class.class), any(Customer.class), any(CopyMode.class),any(ArrayList.class));

		tested.readChilds(sampleQuery, Customer.class, CopyMode.KEEP_TARGET, sampleModifications);
		verify(tested).copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET, sampleModifications);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#readChildsByKey(EntitiesQuery, Class, CopyMode, List)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testReadChildsByKey() throws DataException {
		tested = spy(tested = new CopyComplexEntityOperation<CustomerKP>(config));

		DummyCustomerKeyQuery sampleQuery = MockUtils.mockEntitiesQuery(DummyCustomerKeyQuery.class, Factory.create(CustomerKey.class));
		Customer sampleCustomer = Factory.create(Customer.class);
		PersistenceWorker<Customer> sampleCustomerPw = MockUtils.mockPw(sampleCustomer, null, null);

		doNothing().when(tested).initializeQuery(sampleQuery);
		when(sampleQuery.next()).thenReturn(true).thenReturn(false);
		doReturn(sampleCustomerPw).when(tested).getWorker(Customer.class, null);
		doNothing().when(tested).copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET, null);

		tested.readChildsByKey(sampleQuery, Customer.class, CopyMode.KEEP_TARGET, null);

		verify(tested).copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET, null);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#copyEntity(Class, PersistentObject, CopyMode, List)}.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCopyEntity() throws DataException {
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		tested.resultKey = Factory.create(CustomerKP.class);

		Customer sampleCustomer = Factory.create(Customer.class);
		sampleCustomer.setCustomerName("John");
		sampleCustomer.setCustomerNo("123");
		sampleCustomer.setTaxId("321");
		PersistenceWorker<Customer> sampleWorker = MockUtils.mockPw(sampleCustomer, null, null);

		doReturn(sampleCustomer).when(tested).applyModification(sampleCustomer, null);
		doReturn(sampleWorker).when(tested).getWorker(Customer.class, null);

		tested.copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET,	new ArrayList<Modification<Customer>>());
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#copyEntity(Class, PersistentObject, CopyMode, List)}
	 * where the Entity is not found.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCopyEntity_poNotFound() throws DataException {
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		tested.resultKey = Factory.create(CustomerKP.class);

		Customer sampleCustomer = Factory.create(Customer.class);
		sampleCustomer.setCustomerName("John");
		sampleCustomer.setCustomerNo("123");
		sampleCustomer.setTaxId("321");
		PersistenceWorker<Customer> sampleWorker = MockUtils.mockPw(sampleCustomer, null, sampleCustomer);

		doReturn(sampleCustomer).when(tested).applyModification(any(Customer.class), isNull());
		doReturn(sampleWorker).when(tested).getWorker(Customer.class, null);
		doThrow(PoNotFoundException.class).when(sampleWorker).read(any(Customer.class));

		tested.copyEntity(Customer.class, sampleCustomer, CopyMode.KEEP_TARGET,null);
		verify(sampleWorker).store(sampleCustomer);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#applyModification(PersistentObject, List)}.
	 */
	@Test
	public void testApplyModification() {
		Customer result;
		Customer sample = null;
		List<Modification<Customer>> modifications = spy(new ArrayList<Modification<Customer>>());
		result = tested.applyModification(sample, modifications);
		assertSame(result, sample);

		sample = Factory.create(Customer.class);
		sample.setCustomerName("Test"); //$NON-NLS-1$
		modifications.add(new GetTheSame<Customer>());
		result = tested.applyModification(sample, modifications);
		assertEquals(result.getCustomerName(), "Test"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#getWorker(Class, String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetWorker() throws DataException {
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		doNothing().when(tested).initialize(Mockito.anyString(), Mockito.any(Worker.class));

		assertTrue(tested.workerCache.isEmpty());
		PersistenceWorker<User> result = tested.getWorker(User.class, "Test"); //$NON-NLS-1$

		assertNotNull(result);
		assertNotSame(result, tested.getWorker(User.class, "Test1")); //$NON-NLS-1$
		assertSame(result, tested.getWorker(User.class, "Test")); //$NON-NLS-1$
		assertEquals(2, tested.workerCache.size());
		verify(tested, times(2)).initialize(anyString(), any(Worker.class));
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#initializeQuery(CriteriaDependent)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testInitializeQuery() throws DataException {
		tested = spy(new CopyComplexEntityOperation<CustomerKP>(config));
		doNothing().when(tested).initialize(isNull(), Mockito.any(Worker.class));
		DummyCustomerQuery sampleQuery = spy(new DummyCustomerQuery());
		tested.initializeQuery(sampleQuery);

		verify(sampleQuery).execute();
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#initializeQuery(CriteriaDependent)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testInitializeQuery_isManaged() throws DataException {
		DummyCustomerQuery sampleQuery = spy(new DummyCustomerQuery());
		tested.managed.add(sampleQuery);
		tested.initializeQuery(sampleQuery);

		verify(sampleQuery).execute();
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#initialize(String, Worker)}.
	 * 
	 * @throws DataException
	 * @throws InitializationException
	 */
	@SuppressWarnings({ "nls", "deprecation" })
	@Test
	public void testInitialize() throws DataException, InitializationException {
		CopyToOtherSystemOperation<User, UserKey> sampleOp = spy(new CopyToOtherSystemOperation<User, UserKey>(User.class, "A", "B"));
		doNothing().when(sampleOp).init(isNull());
		doNothing().when(sampleOp).open();

		tested.initialize("Test", sampleOp); //$NON-NLS-1$
		verify(sampleOp).init(isNull());
		verify(sampleOp).open();
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#initialize(String, Worker)}
	 * that fails due to worker not being an {@link AbstractResourceConsumer}.
	 * 
	 * @throws DataException
	 */
	@Test(expected = DataException.class)
	public void testInitialize_NotAbstractConsumer() throws DataException {
		AbstractBaseWorker sampleWorker = new AbstractBaseWorker() {
			// empty
		};
		tested.initialize("Test", sampleWorker); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#initialize(String, Worker)}
	 * that fails due to worker init failure.
	 * 
	 * @throws DataException
	 * @throws InitializationException
	 */
	@SuppressWarnings({ "nls", "deprecation" })
	@Test(expected = DataException.class)
	public void testInitialize_initFail() throws DataException, InitializationException {
		CopyToOtherSystemOperation<User, UserKey> sampleOp = spy(
				new CopyToOtherSystemOperation<User, UserKey>(User.class, "A", "B"));

		doThrow(InitializationException.class).when(sampleOp).init(Mockito.isNull());
		tested.initialize("Test", sampleOp); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#setManagerName(AbstractResourceConsumer, String)}.
	 */
	@SuppressWarnings({ "nls", "deprecation" })
	@Test
	public void testSetManagerNameAbstractResourceConsumerString() {
		CopyToOtherSystemOperation<User, UserKey> sampleOp = new CopyToOtherSystemOperation<User, UserKey>(User.class,
				"A", "B");
		sampleOp.prepareForCopy = new AbstractPoOperation<User>() {
			@Override
			public void execute() throws LogicException, DataException {
				// empty
			}
		};

		tested.setManagerName(sampleOp, "Test");
		assertTrue(sampleOp.prepareForCopy.getManagerName() == "Test");
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#setInput(Object)}.
	 */
	@Test
	public void testSetInput() {
		tested.setInput(Factory.create(CustomerKP.class));
		assertNotNull(tested.input);
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#setResultKey(Object)}.
	 */
	@Test
	public void testSetResultKey() {
		tested.setResultKey(Factory.create(CustomerKP.class));
		assertNotNull(tested.resultKey);
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#setToManager(String)}.
	 */
	@Test
	public void testSetToManager() {
		tested.setToManager(StringConstants.SIX);
		assertEquals(tested.toManager, StringConstants.SIX);
	}

	/**
	 * Test method for {@link CopyComplexEntityOperation#setFromManager(String)}.
	 */
	@Test
	public void testSetFromManager() {
		tested.setFromManager(StringConstants.SIX);
		assertEquals(tested.fromManager, StringConstants.SIX);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityOperation#setUseOverwriteAsDefault(boolean)}.
	 */
	@Test
	public void testSetUseOverwriteAsDefault() {
		tested.setUseOverwriteAsDefault(true);
		assertTrue(tested.useOverwriteAsDefault);
	}

}
