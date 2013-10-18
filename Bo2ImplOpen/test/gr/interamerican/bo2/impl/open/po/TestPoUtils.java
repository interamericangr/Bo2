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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import gr.interamerican.bo2.impl.open.runtime.CrudCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.samples.archutil.po.UserProfile;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.test.def.posamples.ArrayWithAnnot;
import gr.interamerican.bo2.test.def.posamples.ArrayWithoutAnnot;
import gr.interamerican.bo2.test.def.posamples.ExtendedInvoice;
import gr.interamerican.bo2.test.def.posamples.ExtendedTwiceInvoice;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerList;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerSet;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
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
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link PoUtils}.
 */
public class TestPoUtils {
	
	/**
	 * User.
	 */
	private User user;
	
	/**
	 * User id.
	 */
	private Integer userId = 555;

	/**
	 * tests deepCopy with concrete objects
	 */
	@Test
	public void testDeepCopy_WithConcrete() {
		testDeepCopy(SamplesFactory.getConcrete(),true);
	}
	
	/**
	 * tests deepCopy with factored objects
	 */
	@Test
	public void testDeepCopy_WithFactored() {		
		testDeepCopy(SamplesFactory.getFactored(),true);
	}
	
	/**
	 * tests deepCopy with concrete objects
	 */
	@Test
	public void testDeepCopyPreservingModificationRecord_WithConcrete() {
		testDeepCopy(SamplesFactory.getConcrete(),false);
	}
	
	/**
	 * tests deepCopy with factored objects
	 */
	@Test
	public void testDeepCopyPreservingModificationRecord_WithFactored() {		
		testDeepCopy(SamplesFactory.getFactored(),false);
	}
	
	/**
	 * Tests deepCopy(p) and deepCopyPreservingModificationRecord(p).
	 * 
	 * @param factory
	 * @param resetMdfRec 
	 */
	@SuppressWarnings("nls")
	public void testDeepCopy(SamplesFactory factory, boolean resetMdfRec) {
		User one = factory.sampleUser(userId, 4);
		
		/*
		 * Set last modified properties for the test.
		 */
		one.setLastModified(DateUtils.today());
		one.setLastModifiedBy("X");		
		
		User two;
		if (resetMdfRec) {
			two = PoUtils.deepCopy(one);			
		} else {
			two = PoUtils.deepCopyPreservingModificationRecord(one);
		}
		
		Set<UserProfile> oneProfiles = one.getProfiles();
		Set<UserProfile> twoProfiles = two.getProfiles();
		
		Iterator<UserProfile> oneIter = oneProfiles.iterator();
		while(oneIter.hasNext()) {
			UserProfile oneLine = oneIter.next();
			UserProfile twoLine = null;
			
			Iterator<UserProfile> twoIter = twoProfiles.iterator();
			while (twoIter.hasNext()) {
				UserProfile temp = twoIter.next();
				if(oneLine.equals(temp)) {
					twoLine = temp;
				}
			}
			assertFalse(oneLine==twoLine);
			assertTrue(PoUtils.deepEquals(oneLine, twoLine));
			assertTrue(oneLine.equals(twoLine));
			if (resetMdfRec) {
				assertNull(two.getLastModified());
				assertNull(two.getLastModifiedBy());				
			} else {
				assertEquals(one.getLastModified(), two.getLastModified());
				assertEquals(one.getLastModifiedBy(), two.getLastModifiedBy());
			}
		}
		
		assertFalse(one==two);
		assertTrue(PoUtils.deepEquals(one, two));
	}
	
	/**
	 * tests deepEquals for a equal Pos
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
	 * tests deepEquals for a equal Pos
	 */
	@Test
	public void testDeepEquals_ForSameObject() {
		PoPo one = new PoPo();
		assertTrue(PoUtils.deepEquals(one,one));
	}
	
	/**
	 * tests deepEquals for a equal Pos
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
	 * tests deepEquals when one argument is null
	 */
	@Test
	public void testDeepEquals_WithOneNull() {
		String field1 = "field1"; //$NON-NLS-1$
		
		PoPo one = new PoPo();
		one.setField1(field1);		
		assertFalse(PoUtils.deepEquals(one,null));
	}
	
	/**
	 * tests deepEquals when both arguments are null
	 */
	@Test
	public void testDeepEquals_WithNulls() {		
		assertTrue(PoUtils.deepEquals(null,null));
	}
	
	/**
	 * tests equals for a simple Po
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
	 * tests equals for a simple Po
	 */
	@Test
	public void testCopy_forNull() {		
		PoPo one = null;
		PoPo two = PoUtils.deepCopy(one);
		assertNull(two);
	}
	
	
	/**
	 * Tests copy where object is instance of Timestamp 
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
	 * Tests copy where object is instance of Set without child annotation
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
	 * Tests copy where object is List and there is a child annotation
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
	 * Tests copy where object is array and there is a child annotation
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
	 * Tests copy where object is array and there is not a child annotation
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
	 * Tests copy where object is instance of Money 
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
	}
	
	/**
	 * Unit test for reattach.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattach() throws UnexpectedException, DataException, LogicException {
		PersistenceWorker<User> pw = new UserPwImpl();
		CrudCmd<User> crud = new CrudCmd<User>(pw,true);
		
		user = SamplesFactory.getBo2Factory().sampleUser(userId, 1);
		crud.delete(user);
		crud.store(user);
		
		/*
		 * re-attach
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(user, getProvider());
			}
		}.execute();
		
		crud.delete(user);
		
	}
	
	/**
	 * Unit test for serialize and deserialize.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
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
	 * Empty PO.
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	private abstract static class AbstractEmptyPo 
	implements PersistentObject {/* empty */}

}
