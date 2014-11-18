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
package gr.interamerican.bo2.arch.utils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;

import java.util.Iterator;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Utilities for mocking.
 */
public class MockUtils {
	
	/**
	 * Mocks an {@link EntitiesQuery} sub-class so that next() and getEntity() work on an Iterator.
	 * 
	 * @param <P>
	 * @param <T>
	 * @param clazz
	 * @param iterator
	 * @return Mock.
	 */
	public static <P, T extends EntitiesQuery<P>> T mockEntitiesQuery(Class<T> clazz, final Iterator<P> iterator) {
		T result = mock(clazz);
		try {
			when(result.next()).thenAnswer(new Answer<Boolean>() {
				public Boolean answer(InvocationOnMock invocation) throws Throwable {
					return iterator.hasNext();
				}
			});
			when(result.getEntity()).thenAnswer(new Answer<P>() {
				public P answer(InvocationOnMock invocation) throws Throwable {
					return iterator.next();
				}
			});
		} catch (DataAccessException daex) {
			//EMPTY
		}
		return result;
	}
	
	/**
	 * Mocks an {@link EntitiesQuery} sub-class so that next() and getEntity() work on a List.
	 * 
	 * @param <P>
	 * @param <T>
	 * @param clazz
	 * @param list
	 * @return Mock.
	 */
	public static <P, T extends EntitiesQuery<P>> T mockEntitiesQuery(Class<T> clazz, List<P> list) {
		return mockEntitiesQuery(clazz, list.iterator());
	}
	
	/**
	 * Mocks a {@link PersistenceWorker} 
	 * 
	 * @param onRead
	 * @param onUpdate
	 * @param onSave
	 * @return Mock.
	 */
	@SuppressWarnings("unchecked")
	public static <P extends PersistentObject<?>>
	PersistenceWorker<P> mockPw(P onRead, P onUpdate, P onSave) {
		PersistenceWorker<P> pw = mock(PersistenceWorker.class);
		try {
			when(pw.read((P) any())).thenReturn(onRead);
			when(pw.update((P) any())).thenReturn(onUpdate);
			when(pw.store((P) any())).thenReturn(onSave);
		} catch (DataException e) {
			//EMPTY
		}
		return pw;
	}

}
