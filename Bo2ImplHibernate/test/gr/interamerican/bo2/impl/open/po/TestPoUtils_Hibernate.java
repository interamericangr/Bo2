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
package gr.interamerican.bo2.impl.open.po;

import static org.junit.Assert.*;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.test.def.posamples.ArrayWithAnnot;
import gr.interamerican.bo2.test.def.posamples.ArrayWithoutAnnot;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.ExtendedInvoice;
import gr.interamerican.bo2.test.def.posamples.ExtendedTwiceInvoice;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerList;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerSet;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.MoneyPo;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.def.posamples.TimestampPo;
import gr.interamerican.bo2.test.impl.posamples.ExtendedInvoiceImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.ArrayWithAnnotImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.ArrayWithoutAnnotImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceCustomerImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceCustomerListImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceCustomerSetImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.InvoiceImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.MoneyPoImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.TimestampPoImpl;
import gr.interamerican.bo2.test.impl.samples.PoPo;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.proxy.HibernateProxy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link PoUtils}.
 */
public class TestPoUtils_Hibernate {
	
	/**
	 * Sample invoice.
	 */
	private Invoice invoice;
	
	/**
	 * Sample invoiceNo.
	 */
	private String invoiceNo = "1"; //$NON-NLS-1$

	/**
	 * tests deepCopy with concrete objects.
	 */
	@Test
	public void testDeepCopy_WithConcrete() {
		testDeepCopy(SamplesFactory.getConcrete(),true);
	}
	
	/**
	 * tests deepCopy with factored objects.
	 */
	@Test
	public void testDeepCopy_WithFactored() {		
		testDeepCopy(SamplesFactory.getFactored(),true);
	}
	
	/**
	 * tests deepCopy with concrete objects.
	 */
	@Test
	public void testDeepCopyPreservingModificationRecord_WithConcrete() {
		testDeepCopy(SamplesFactory.getConcrete(),false);
	}
	
	/**
	 * tests deepCopy with factored objects.
	 */
	@Test
	public void testDeepCopyPreservingModificationRecord_WithFactored() {		
		testDeepCopy(SamplesFactory.getFactored(),false);
	}
	
	
	
	/**
	 * Tests deepCopy(p) and deepCopyPreservingModificationRecord(p).
	 *
	 * @param factory the factory
	 * @param resetMdfRec the reset mdf rec
	 */
	@SuppressWarnings("nls")
	public void testDeepCopy(SamplesFactory factory, boolean resetMdfRec) {
		Invoice one = factory.sampleInvoiceFull(4);
		one.setInvoiceNo("AAA"); 
		
		/*
		 * Set last modified properties for the test.
		 */
		one.setLastModified(DateUtils.today());
		one.setLastModifiedBy("X");		
		for (InvoiceLine invoiceLine : one.getLines()) {
			invoiceLine.setLastModified(DateUtils.today());
			invoiceLine.setLastModifiedBy("X");	
		}
		
		Invoice two;
		if (resetMdfRec) {
			two = PoUtils.deepCopy(one);			
		} else {
			two = PoUtils.deepCopyPreservingModificationRecord(one);
		}
		
		Set<InvoiceLine> oneLines = one.getLines();
		Set<InvoiceLine> twoLines = two.getLines();
		
		for (InvoiceLine oneLine : oneLines) {
			InvoiceLine twoLine = SelectionUtils.selectFirstByProperty(InvoiceLine::getKey, oneLine.getKey(), twoLines);
			assertFalse(oneLine==twoLine);
			assertTrue(PoUtils.deepEquals(oneLine, twoLine));
			assertTrue(oneLine.equals(twoLine));
			if (resetMdfRec) {
				assertNull(twoLine.getLastModified());
				assertNull(twoLine.getLastModifiedBy());				
			} else {
				assertEquals(oneLine.getLastModified(), twoLine.getLastModified());
				assertEquals(oneLine.getLastModifiedBy(), twoLine.getLastModifiedBy());
			}
		}
		
		assertFalse(one==two);
		assertFalse(one.getCustomer()==two.getCustomer());
		assertTrue(PoUtils.deepEquals(one.getCustomer(), two.getCustomer()));
		assertTrue(PoUtils.deepEquals(one, two));
		
		if (resetMdfRec) {
			assertNull(two.getLastModified());
			assertNull(two.getLastModifiedBy());				
		} else {
			assertEquals(one.getLastModified(), two.getLastModified());
			assertEquals(one.getLastModifiedBy(), two.getLastModifiedBy());
		}
	}
	
	/**
	 * Tests that a deep copy of a persistent object is also a persistent object
	 * when the modification record is preserved.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testDeepCopyPreserveMdfIsPersistent() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				Invoice inv1 = SamplesFactory.getBo2Factory().sampleInvoiceFull(1);
				Customer customer = SamplesFactory.getBo2Factory().sampleCustomer("foo"); //$NON-NLS-1$
				customer.setCustomerNo("whatever"); //$NON-NLS-1$
				customer = openPw(Customer.class).store(customer);
				inv1.setInvoiceNo(invoiceNo);
				for (InvoiceLine line : inv1.getLines()) {
					line.setCustomer(customer);
				}
				openPw(Invoice.class).store(inv1);
			}
		}.execute();

		new AbstractBo2RuntimeCmd() {

			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				Invoice inv1 = Factory.create(Invoice.class);
				inv1.setInvoiceNo(invoiceNo);
				inv1 = ipw.read(inv1);
				InvoiceLine line = inv1.getLines().iterator().next();
				InvoiceLine copy = PoUtils.deepCopyPreservingModificationRecord(line);
				assertTrue(line.getCustomer() instanceof HibernateProxy);
				assertFalse(copy.getCustomer() instanceof HibernateProxy);
				Assert.assertFalse(inv1.getLines().add(copy));
				inv1.getLines().clear();
				inv1.getLines().add(copy);
				copy.setAmount(200.0);
				
				inv1 = ipw.update(inv1);
			}
		}.execute();
	}
	
	/**
	 * Tests that a deep copy of a persistent object is not a persistent object
	 * when the modification record is not preserved.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test(expected=DataException.class)
	public void testDeepCopyIsNotPersistent() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				Invoice inv1 = SamplesFactory.getBo2Factory().sampleInvoiceFull(1);
				inv1.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				inv1 = ipw.store(inv1);
				
				InvoiceLine line = inv1.getLines().iterator().next();
				InvoiceLine copy = PoUtils.deepCopy(line);
				Assert.assertFalse(inv1.getLines().add(copy));
				inv1.getLines().clear();
				inv1.getLines().add(copy);
				copy.setAmount(200.0);
				
				inv1 = ipw.update(inv1);
			}
		}.execute();
	}
	
	/**
	 * tests deepEquals for a equal Pos.
	 */
	@Test
	public void testDeepEquals_ForEqualPos() {
		String field1 = "field1"; //$NON-NLS-1$
		
		PoPo one = new PoPo();
		one.setField1(field1);
		PoPo two = new PoPo();
		two.setField1(field1);
		assertTrue(PoUtils.deepEquals(one,two));
	}
	
	/**
	 * tests deepEquals for a equal Pos.
	 */
	@Test
	public void testDeepEquals_ForSameObject() {
		PoPo one = new PoPo();
		assertTrue(PoUtils.deepEquals(one,one));
	}
	
	/**
	 * tests deepEquals for a equal Pos.
	 */
	@Test
	public void testDeepEquals_ForDifferentClassObjects() {
		PoPo one = new PoPo();		
		@SuppressWarnings("serial")
		PoPo two = new PoPo(){
			@Override
			public String getField1() {
				return null;
			}
		};
		assertFalse(PoUtils.deepEquals(one,two));
	}
	
	/**
	 * tests deepEquals when one argument is null.
	 */
	@Test
	public void testDeepEquals_WithOneNull() {
		String field1 = "field1"; //$NON-NLS-1$
		
		PoPo one = new PoPo();
		one.setField1(field1);		
		assertFalse(PoUtils.deepEquals(one,null));
	}
	
	/**
	 * tests deepEquals when both arguments are null.
	 */
	@Test
	public void testDeepEquals_WithNulls() {		
		assertTrue(PoUtils.deepEquals(null,null));
	}
	
	/**
	 * tests equals for a simple Po.
	 */
	@Test
	public void testDeepEquals_ForNotEqualPos() {
		String field1 = "field1"; //$NON-NLS-1$
		String field2 = "field2"; //$NON-NLS-1$
		
		PoPo one = new PoPo();
		one.setField1(field1);
		PoPo two = new PoPo();
		two.setField1(field2);
		assertFalse(PoUtils.deepEquals(one,two));
	}
	
	/**
	 * tests equals for a simple Po.
	 */
	@Test
	public void testCopy_forNull() {		
		PoPo one = null;
		PoPo two = PoUtils.deepCopy(one);
		assertNull(two);
	}
	
	
	/**
	 * Tests copy where object is instance of Timestamp.
	 */
	@Test
	public void testCopy_Timestamp(){
		TimestampPo one = new TimestampPoImpl();
		Timestamp tms = new Timestamp(1984);
		one.setTms(tms);
		TimestampPo two =PoUtils.deepCopy(one);
		assertTrue(PoUtils.deepEquals(one, two));
	}
	
	
	/**
	 * Tests copy where object is instance of Set without child annotation.
	 */
	@Test
	public void testCopy_SetWithoutAnnotation(){
		InvoiceCustomerSet one = new InvoiceCustomerSetImpl();
		InvoiceCustomer cust = new InvoiceCustomerImpl();
		Set<InvoiceCustomer> custSet = new HashSet<InvoiceCustomer>();
		custSet.add(cust);
        one.setCustomers(custSet);
        InvoiceCustomerSet two = PoUtils.deepCopy(one);
        assertTrue(PoUtils.deepEquals(one, two));
	}
	
	/**
	 * Tests copy where object is List and there is a child annotation.
	 */
	@Test
	public void testCopy_ListWithAnnotation(){
		InvoiceCustomerList one = new InvoiceCustomerListImpl();
		InvoiceCustomer cust = new InvoiceCustomerImpl();
		List <InvoiceCustomer> custlist = new ArrayList<InvoiceCustomer>();
		custlist.add(cust);
		one.setInvoiceList(custlist);
		InvoiceCustomerList two = PoUtils.deepCopy(one);
		 assertTrue(PoUtils.deepEquals(one, two));
	}
	
	/**
	 * Tests copy where object is array and there is a child annotation.
	 */
	@Test
	public void testCopy_ArrayWithAnnotation(){
		ArrayWithAnnot one = new ArrayWithAnnotImpl();
		InvoiceCustomer cust = new InvoiceCustomerImpl();
		InvoiceCustomer[] ob = {cust};
		one.setArrayOfObjects(ob);
		ArrayWithAnnot two = PoUtils.deepCopy(one);
		assertTrue(PoUtils.deepEquals(one, two));
	}
	
	
	/**
	 * Tests copy where object is array and there is not a child annotation.
	 */
	@Test
	public void testCopy_ArrayWithoutAnnotation(){
		ArrayWithoutAnnot one = new ArrayWithoutAnnotImpl();
		InvoiceCustomer cust = new InvoiceCustomerImpl();
		InvoiceCustomer[] ob = {cust};
		one.setArrayOfObjects(ob);
		ArrayWithoutAnnot two = PoUtils.deepCopy(one);
		assertTrue(PoUtils.deepEquals(one, two));
	}


	/**
	 * Tests copy where object is instance of Money.
	 */
	@Test
	public void testCopy_Money(){
		MoneyPo one = new MoneyPoImpl();
        MoneyImpl m = new MoneyImpl();
        one.setMoney(m);
		MoneyPo two = PoUtils.deepCopy(one);
		PoUtils.deepEquals(one, two);
	}	
	
	/**
	 * Unit test for setDetachStrategy.
	 */
	@Test
	public void testSetDetachStrategy() {
		InvoiceImpl po = new InvoiceImpl();
		DetachStrategy strategy = Mockito.mock(DetachStrategy.class);
		PoUtils.setDetachStrategy(po, strategy);
		assertEquals(strategy, po.getDetachStrategy());
		
		Object o = new Object();
		PoUtils.setDetachStrategy(o, strategy); //Nothing bad should happen.
	}
	
	/**
	 * Unit test for getDetachStrategy.
	 */
	@Test
	public void testGetDetachedStrategy() {
		InvoiceImpl po = new InvoiceImpl();
		DetachStrategy strategy = Mockito.mock(DetachStrategy.class);
		po.setDetachStrategy(strategy);
		DetachStrategy actual = PoUtils.getDetachStrategy(po);
		assertEquals(strategy, actual);
		
		actual = PoUtils.getDetachStrategy(new Object());
		assertNull(actual);
		
		po = new InvoiceImpl();
		actual = PoUtils.getDetachStrategy(po);
		Assert.assertNotNull(actual);
		DetachStrategy expected = ((AbstractBasePo<?>) po).getDetachStrategy();
		assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for reattach.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testReattach() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				invoice = SamplesFactory.getBo2Factory().sampleInvoiceFull(1);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				invoice = pw.store(invoice);
			}
		}.execute();
		
		/*
		 * re-attach and initialize some proxies.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				invoice.getLines().iterator().next().getSubLines().size();
			}
		}.execute();
		
		/*
		 * clear the database.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				invoice = pw.read(invoice);
				pw.delete(invoice);
			}
		}.execute();
		
	}
	
	/**
	 * Unit test for serialize and deserialize.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	@Test
	public void testSerialize_Deserialize() 
	throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String directory = UtilityForBo2Test.getStreamTestWorkDirectory();
		String filePath = directory + "po.out"; //$NON-NLS-1$
		PoPo po = Factory.create(PoPo.class);
		PoUtils.serialize(po, filePath);
		PoPo deserialized = PoUtils.deserialize(filePath);
		/*
		 * hack to initialize childFields on the deserialized instance
		 * so that deepEquals does not fail there.
		 */
		Method resolveChildFieldsMethod = ReflectionUtils.getMethodByUniqueName("resolveChildFields", AbstractBasePo.class); //$NON-NLS-1$
		resolveChildFieldsMethod.setAccessible(true);
		resolveChildFieldsMethod.invoke(deserialized);
		Assert.assertTrue(PoUtils.deepEquals(po, deserialized));
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withConcreteClass() {
		Class<? extends PersistentObject<?>> po = User.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(UserKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withNoKeyType() {
		Class<? extends PersistentObject<?>> po = Utils.cast(AbstractEmptyPo.class);
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertNull(key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withInterface() {
		Class<? extends PersistentObject<?>> po = Invoice.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(InvoiceKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withAbstractClass() {
		Class<? extends PersistentObject<?>> po = InvoiceImpl.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(InvoiceKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withExtendedPoInterface() {
		Class<? extends PersistentObject<?>> po = ExtendedInvoice.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(InvoiceKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withExtendedTwicePoInterface() {
		Class<? extends PersistentObject<?>> po = ExtendedTwiceInvoice.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(InvoiceKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType.
	 * 
	 */
	@Test
	public void testGetKeyType_withExtendedAbstractClass() {
		Class<? extends PersistentObject<?>> po = ExtendedInvoiceImpl.class;
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertEquals(InvoiceKey.class, key);
	}
	
	/**
	 * Unit test for GetKeyType when the exact key type is not defined.
	 * 
	 */
	@Test
	public void testGetKeyType_withAbstractBasePo() {
		Class<? extends PersistentObject<?>> po = Utils.cast(AbstractBasePo.class);
		Class<?> key = PoUtils.getKeyType(po);
		Assert.assertNull(key);
	}
	
	/**
	 * Unit test for GetKeyProperties.
	 */
	@Test
	public void testGetKeyProperties() {
		Class<? extends PersistentObject<?>> po = User.class;
		String[] kp = PoUtils.getKeyProperties(po);
		String[] expecteds = {"id"}; //$NON-NLS-1$
		Assert.assertArrayEquals(expecteds, kp);
	}
	
	/**
	 * Unit test for
	 * {@link PoUtils#merge(PersistentObject, PersistentObject, MergeMode, Class, String)}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMerge() throws Exception {
		final Invoice source = SamplesFactory.getBo2Factory().sampleInvoiceFull(5);
		source.setInvoiceDate(DateUtils.today());
		final Invoice target = SamplesFactory.getBo2Factory().sampleInvoiceFull(2);
		target.setInvoiceDate(DateUtils.getDay1AD());
		target.getLineByNo(2).setLineNo(99);
		String no = ((Long) new Random().nextLong()).toString();
		source.setInvoiceNo(no);
		target.setInvoiceNo(no);
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				Invoice stored = openPw(Invoice.class).store(target);
				assertEquals(2, stored.getLines().size());
				// 1st merging - target is kept as is - only extra child
				// entities are added
				PoUtils.merge(source, stored, MergeMode.FAVOR_TARGET, Invoice.class, "LOCALDB"); //$NON-NLS-1$
				stored = openPw(Invoice.class).update(stored);
				assertEquals(6, stored.getLines().size());
				assertNotNull(stored.getLineByNo(99));
				assertEquals(DateUtils.getDay1AD(), stored.getInvoiceDate());
				// 2nd merging - we will override target values - but keep any
				// extra entities it has
				PoUtils.merge(source, stored, MergeMode.FAVOR_SOURCE, Invoice.class, "LOCALDB"); //$NON-NLS-1$
				stored = openPw(Invoice.class).update(stored);
				assertEquals(6, stored.getLines().size());
				assertEquals(DateUtils.today(), stored.getInvoiceDate());
				// 3rd merging - target entity is completely overwritten
				PoUtils.merge(source, stored, MergeMode.OVERWRITE, Invoice.class, "LOCALDB"); //$NON-NLS-1$
				stored = openPw(Invoice.class).update(stored);
				assertEquals(5, stored.getLines().size());
				assertNull(stored.getLineByNo(99));
				assertTrue(PoUtils.deepEquals(source, stored));
			}
		}.execute();
	}

	/**
	 * Empty PO.
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	private abstract static class AbstractEmptyPo 
	implements PersistentObject {/* empty */}

}
