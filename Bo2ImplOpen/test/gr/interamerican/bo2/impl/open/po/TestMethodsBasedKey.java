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


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Test;

/**
 * unit tests for {@link MethodsBasedKey}.
 */
public class TestMethodsBasedKey {

	/**
	 * Tests a key operating on another object.
	 */
	@SuppressWarnings("nls")
	@Test
	public void otherObject() {
		User user = new User(1, "spy", 1, "ff");
		MethodsBasedKey key = new MethodsBasedKey(User.class, new String[]{"id", "name"}, user);
		Object[] expecteds = {user.getId(), user.getName() };
		assertArrayEquals(expecteds, key.getElementsCopy());
		
		user.setName("ny");
		Object[] expecteds2 = {user.getId(), user.getName() };
		assertArrayEquals(expecteds2, key.getElementsCopy());
	}
	
	/**
	 * Tests a key that extends the base class.
	 */	
	@Test
	public void sameObject() {
		Impl key = new Impl();		
		Object[] expecteds = {Impl.ID, Impl.CODE, Impl.NAME };
		assertArrayEquals(expecteds, key.getElementsCopy());

	}
	
	/**
	 * Tests equals.
	 */
	@Test
	public void testEqualsA(){
		Impl impl1 = new Impl();
		Impl impl2 = new Impl();
		Impl implNull = null;
		assertTrue(impl1.equals(impl2));
		assertFalse(impl1.equals(implNull));
	}
	
	/**
	 * Tests equals.
	 */
	@Test
	public void testEqualsB(){
		OtherImpl impl1 = new OtherImpl();
		OtherImpl impl2 = new OtherImpl();
		
		int id = 7;
		Long code = 3L;
		String name = "dd";		 //$NON-NLS-1$
		impl1.setId(id);
		impl2.setId(id);
		impl1.setCode(code);
		impl2.setCode(code);
		impl1.setName(name);
		impl2.setName(name);		
		assertTrue(impl1.equals(impl2));
		
	}	
	
	
	
	/**
	 * Sub-type of {@link MethodsBasedKey} for testing.
	 */
	private static class Impl extends MethodsBasedKey {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Id
		 */
		private static final int ID = 2;
		/**
		 * code.
		 */
		private static final Long CODE = 2L;
		/**
		 * name.
		 */
		@SuppressWarnings("nls")
		private static final String NAME = "Name";
		/**
		 * Key methods.
		 */


		/**
		 * Creates a new Impl object. 
		 *
		 */
		public Impl() {
			super(Impl.class, new String[]{"id","code","name"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			//super(keyMethods);
		}

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public int getId() {
			return ID;
		}	

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public Long getCode() {
			return CODE;
		}

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public String getName() {
			return NAME;
		}
		
	}
	

	
	
	/**
	 * Sub-type of {@link MethodsBasedKey} for tesintg.
	 */
	private static class OtherImpl extends MethodsBasedKey {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Id
		 */
		private int id;
		
		/**
		 * code.
		 */
		private Long code;
		
		/**
		 * name.
		 */		
		private String name;
		
		
		/**
		 * Creates a new Impl object. 
		 *
		 */
		@SuppressWarnings("nls")
		public OtherImpl() {
			super(OtherImpl.class, new String[]{"id","code","name"}); 
			//super(keyMethods);
		}

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public int getId() {
			return id;
		}	

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public Long getCode() {
			return code;
		}

		/**
		 * Key method.
		 * 
		 * @return Returns a value.
		 */
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		/**
		 * Sets the Id.
		 * @param id
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * Sets the code.
		 * @param code
		 */
		public void setCode(Long code) {
			this.code = code;
		}

		/**
		 * Sets the name.
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		
		
	}
	
	
	




}
