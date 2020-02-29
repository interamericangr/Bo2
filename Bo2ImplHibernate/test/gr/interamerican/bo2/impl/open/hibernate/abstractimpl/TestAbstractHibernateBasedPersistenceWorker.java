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
package gr.interamerican.bo2.impl.open.hibernate.abstractimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;

import org.junit.Test;

/**
 * Unit test of {@link AbstractHibernateBasedPersistenceWorker}.
 */
public class TestAbstractHibernateBasedPersistenceWorker {

	/**
	 * Test method for {@link AbstractHibernateBasedPersistenceWorker#AbstractHibernateBasedPersistenceWorker(Class)}.
	 * @throws DataException 
	 */
	@Test
	public void testAbstractHibernateBasedPersistenceWorker() throws DataException {
		// setup
		AbstractHibernateBasedPersistenceWorker<Something> underTest = new AbstractHibernateBasedPersistenceWorker<Something>(
				Something.class) {
			/** empty */
		};
		@SuppressWarnings("unchecked")
		GenericHibernatePersistenceWorker<Something> mock = mock(GenericHibernatePersistenceWorker.class);
		underTest.setHibPw(mock);
		Something something = new Something();
		// tests
		// read
		underTest.read(something);
		verify(mock).read(eq(something));
		// store
		underTest.store(something);
		verify(mock).store(eq(something));
		// delete
		underTest.delete(something);
		verify(mock).delete(eq(something));
		// update
		underTest.update(something);
		verify(mock).update(eq(something));
		// ignoresSomething
		underTest.ignoresSomething();
		verify(mock).ignoresSomething();
		// getDetachStrategy
		underTest.getDetachStrategy();
		verify(mock).getDetachStrategy();
		// setManagerName
		underTest.setManagerName("mng"); //$NON-NLS-1$
		verify(mock).setManagerName(eq("mng")); //$NON-NLS-1$
		assertEquals("mng", underTest.getManagerName()); //$NON-NLS-1$
	}
}

/**
 * Something that is a {@link PersistentObject}.
 */
class Something implements PersistentObject<Double> {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3211L;

	@Override
	public Double getKey() {
		return null;
	}

	@Override
	public void setKey(Double key) {
		// empty
	}

	@Override
	public void tidy() {
		// empty
	}	
}