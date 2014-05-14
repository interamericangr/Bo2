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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.samples.providers.EmptyProvider;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit tests for {@link AbstractBaseWorker}.
 *
 */
public class TestAbstractBaseWorker {
	
	
	/**
	 * After calling init(p), the worker and its children
	 * workers are initialized.
	 */
	@Test
	public void testInit() {
		try {
			BaseWorkerBasicImpl father=new BaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			assertTrue(father.isInitialized());
			assertTrue(father.getChild().isInitialized());
			assertTrue(father.otherChildren[0].isInitialized());
			assertTrue(father.otherChildren[1].isInitialized());
						
		} catch (InitializationException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling init(p), the worker and its children
	 * workers are initialized, even if they inherit from 
	 * another AbstractBaseWorker.
	 */
	@Test
	public void testInitInheritanceLevelOne() {
		try {
			SubtypeOfBaseWorkerBasicImpl father = 
				new SubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			assertTrue(father.isInitialized());
			assertTrue(father.getChild().isInitialized());
			assertTrue(father.getGrandChild().isInitialized());
		} catch (InitializationException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling init(p), the worker and its children
	 * workers are initialized, even if they inherit from 
	 * another AbstractBaseWorker.
	 */
	@Test
	public void testInitInheritanceLevelTwo() {
		try {
			SubSubtypeOfBaseWorkerBasicImpl father = 
				new SubSubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			assertTrue(father.isInitialized());
			assertTrue(father.getChild().isInitialized());
			assertTrue(father.getGrandChild().isInitialized());
		} catch (InitializationException e) {
			fail(e.toString());
		}
	}
	

	/**
	 * After calling open(), the worker and its children
	 * workers are open.
	 */
	@Test
	public void testOpen() {
		try {
			BaseWorkerBasicImpl father=new BaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			assertTrue(father.isOpen());
			assertTrue(father.getChild().isOpen());
			assertTrue(father.otherChildren[0].isOpen());
			assertTrue(father.otherChildren[1].isOpen());
		
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling open(), the worker and its children
	 * workers are open even if they are derived from other 
	 * AbstractBaseWorker.
	 */
	@Test
	public void testOpenInheritanceLevelOne() {
		try {
			SubtypeOfBaseWorkerBasicImpl father = 
				new SubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			assertTrue(father.isOpen());
			assertTrue(father.getChild().isOpen());
			assertTrue(father.getGrandChild().isOpen());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling open(), the worker and its children
	 * workers are open even if they are derived from other 
	 * AbstractBaseWorker.
	 */
	@Test
	public void testOpenInheritanceLevelTwo() {
		try {
			SubSubtypeOfBaseWorkerBasicImpl father = 
				new SubSubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			assertTrue(father.isOpen());
			assertTrue(father.getChild().isOpen());
			assertTrue(father.getGrandChild().isOpen());
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	
	/**
	 * After calling close(), the worker and its children
	 * workers are not open.
	 */
	@Test
	public void testClose() {
		try {
			BaseWorkerBasicImpl father=new BaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			father.close();
			assertFalse(father.isOpen());
			assertFalse(father.getChild().isOpen());
			assertFalse(father.otherChildren[0].isOpen());
			assertFalse(father.otherChildren[1].isOpen());
			
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling close(), the worker and its children
	 * workers are not open.
	 */
	@Test
	public void testCloseInheritanceLevelOne() {
		try {
			SubtypeOfBaseWorkerBasicImpl father = 
				new SubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			father.close();
			assertFalse(father.isOpen());
			assertFalse(father.getChild().isOpen());
			assertFalse(father.getGrandChild().isOpen());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling close(), the worker and its children
	 * workers are not open.
	 */
	@Test
	public void testCloseInheritanceLevelTwo() {
		try {
			SubSubtypeOfBaseWorkerBasicImpl father = 
				new SubSubtypeOfBaseWorkerBasicImpl();
			father.init(new EmptyProvider());
			father.open();
			father.close();
			assertFalse(father.isOpen());
			assertFalse(father.getChild().isOpen());
			assertFalse(father.getGrandChild().isOpen());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * After calling init(p), the worker and its children
	 * workers have the provider p.
	 */
	@Test
	public void testProvider() {
		try {
			BaseWorkerBasicImpl father=new BaseWorkerBasicImpl();
			Provider p = new EmptyProvider();
			father.init(p);
			assertTrue(father.getProvider()==p);
			assertTrue(father.getChild().getProvider()==p);
		} catch (InitializationException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Unit test for getNamedParameters.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetNamedParameters() {
		BaseWorkerBasicImpl b = new BaseWorkerBasicImpl();
		Map<String, Object> parms = b.getNamedParameters();
		assertEquals(1, parms.size());
		Object obj = parms.get("integer");
		assertEquals(obj, b.integer);
	}
	
	/**
	 * Unit test for getNamedParameters.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetNamedParameters_withSubclass() {
		SubtypeOfBaseWorkerBasicImpl b = new SubtypeOfBaseWorkerBasicImpl();
		Map<String, Object> parms = b.getNamedParameters();
		assertEquals(2, parms.size());
		Object obj1 = parms.get("integer");
		assertEquals(obj1, b.integer);
		Object obj2 = parms.get("string");
		assertEquals(obj2, b.string);
	}
	
	/**
	 * Unit test for getNamedParameters.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetNamedParameters_withBeanParam() {
		WorkerWithBeanParms w = new WorkerWithBeanParms();
		
		String occupation = "Lazy";
		String description = "Unstable";
		Long code = 2L;
		String name = "Unknown";
		String field1 = "1";
		Integer field2 = 4;
		Double field3 = 3.14;
		
		w.setOccupation(occupation);
		w.setDescription(description);
		
		TypedSelectableImpl<Long> selection = new TypedSelectableImpl<Long>();
		selection.setCode(code);
		selection.setTypeId(null);
		selection.setSubTypeId(null);
		selection.setName(name);
		w.setSelection(selection);
		BeanWith3Fields bean = new BeanWith3Fields(field1,field2,field3);
		w.setBean(bean);
		
		Map<String, Object> params = w.getNamedParameters();
		Assert.assertEquals(7, params.size());
		Assert.assertEquals(occupation, params.get("occupation"));
		Assert.assertEquals(description, params.get("description"));		
		Assert.assertEquals(code, params.get("code"));
		Assert.assertEquals(name, params.get("name"));
		Assert.assertEquals(field1, params.get("field1"));
		Assert.assertEquals(field2, params.get("field2"));
		Assert.assertEquals(field3, params.get("field3"));
		
	}
	
	
	/**
	 * Unit test for getParamsFromNamedParams.
	 */
	@Test	
	public void testGetParamsFromNamedParams() {
		SubtypeOfBaseWorkerBasicImpl b = new SubtypeOfBaseWorkerBasicImpl();
		Object[] parms = b.getParamsFromNamedParams();
		assertEquals(3, parms.length);
		assertEquals(b.integer, parms[0]);
		assertEquals(b.string, parms[1]);
		assertEquals(b.string, parms[2]);
	}
	
		
	
	/**
	 * Basic implementation of {@link AbstractBaseWorker} used in this test.
	 *
	 */	
	private static class BaseWorkerBasicImpl extends AbstractBaseWorker {
		
		/**
		 * Child worker
		 */
		@Child
		private BaseWorkerEmptyImpl child = new BaseWorkerEmptyImpl();
		
		/**
		 * parameter field.
		 */
		@Parameter Integer integer = 12;
		
		/**
		 * Other children.
		 */
		BaseWorkerEmptyImpl[] otherChildren =
			{new BaseWorkerEmptyImpl(), new BaseWorkerEmptyImpl()};
		
		/**
		 * Creates a new TestAbstractBaseWorker.BaseWorkerBasicImpl object. 
		 *
		 */
		public BaseWorkerBasicImpl() {
			markAsChildren(otherChildren);
		}
		

		/**
		 * @return the child
		 */
		public BaseWorkerEmptyImpl getChild() {
			return child;
		}
	}

	/**
	 * Empty implementation of {@link AbstractBaseWorker} used in this test.
	 *
	 */
	private static class BaseWorkerEmptyImpl extends AbstractBaseWorker {
		/* empty */		
	}
	
	/**
	 * Derived class of BaseWorkerBasicImpl used in this test.
	 */
	@ParametersOrder("integer,string,string")
	private static class SubtypeOfBaseWorkerBasicImpl extends BaseWorkerBasicImpl {
		/**
		 * parameter field.
		 */		
		@Parameter String string = "string"; //$NON-NLS-1$		
		
		/**
		 * Child worker
		 */
		@Child
		private BaseWorkerEmptyImpl grandChild = new BaseWorkerEmptyImpl();

		/**
		 * @return the grandChild
		 */
		public BaseWorkerEmptyImpl getGrandChild() {
			return grandChild;
		}
	}
	
	/**
	 * Derived class from a derived class of BaseWorkerBasicImpl used in this test.
	 *
	 */
	private static class SubSubtypeOfBaseWorkerBasicImpl extends SubtypeOfBaseWorkerBasicImpl {
		/* empty */	
	}
	
	/**
	 * Derived class from a derived class of BaseWorkerBasicImpl used in this test.
	 *
	 */
	@SuppressWarnings("unused")
	private static class WorkerWithBeanParms extends AbstractBaseWorker {
		/**
		 * Parameteter.
		 */
		@Parameter String occupation;
		/**
		 * Parameteter.
		 */
		@Parameter String description;
		/**
		 * Bean parameter 1.
		 */
		@Parameter(isBean=true) BeanWith3Fields bean;
		
		/**
		 * Bean parameter 2.
		 */
		@Parameter(isBean=true) Selectable<Long> selection;

		/**
		 * Gets the occupation.
		 *
		 * @return Returns the occupation
		 */		
		public String getOccupation() {
			return occupation;
		}

		/**
		 * Assigns a new value to the occupation.
		 *
		 * @param occupation the occupation to set
		 */
		public void setOccupation(String occupation) {
			this.occupation = occupation;
		}

		/**
		 * Gets the description.
		 *
		 * @return Returns the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Assigns a new value to the description.
		 *
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * Gets the bean.
		 *
		 * @return Returns the bean
		 */
		public BeanWith3Fields getBean() {
			return bean;
		}

		/**
		 * Assigns a new value to the bean.
		 *
		 * @param bean the bean to set
		 */
		public void setBean(BeanWith3Fields bean) {
			this.bean = bean;
		}

		/**
		 * Gets the selection.
		 *
		 * @return Returns the selection
		 */
		public Selectable<Long> getSelection() {
			return selection;
		}

		/**
		 * Assigns a new value to the selection.
		 *
		 * @param selection the selection to set
		 */
		public void setSelection(Selectable<Long> selection) {
			this.selection = selection;
		}
	}
	


}
