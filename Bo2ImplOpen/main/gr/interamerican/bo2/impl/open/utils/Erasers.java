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
package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.CrudCmd;
import gr.interamerican.bo2.utils.adapters.Modification;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that registers and returns {@link Modification}s 
 * that erase {@link PersistentObject}s.
 * 
 * This utility class is useful for cleanup after tests.
 */
public class Erasers {
	
	
	/**
	 * Maps classes to modifications that delete them.
	 */
	static final Map<Class<?>, Modification<?>> erasers = new HashMap<Class<?>, Modification<?>>();

	/**
	 * Gets a cleaner modification that deletes instances of the specified
	 * class.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return Returns a {@link Modification} that deletes a persistent object.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Modification<T> getEraser(Class<T> clazz) {
		Modification<T> eraser = (Modification<T>) erasers.get(clazz);
		if (eraser == null) {
			if (PersistentObject.class.isAssignableFrom(clazz)) {
				Class<? extends PersistentObject<?>> poClass = (Class<? extends PersistentObject<?>>) clazz;
				eraser = new CleanerImpl(poClass);
			} else {
				String msg = "Eraser not found for class " + clazz.getName(); //$NON-NLS-1$
				throw new RuntimeException(msg);
			}
		}
		return eraser;
	}

	/**
	 * Registers a cleaner for a class.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param eraser
	 *            the eraser
	 */
	public static <T extends PersistentObject<?>> void register(Class<T> clazz, Modification<T> eraser) {
		erasers.put(clazz, eraser);
	}

	/**
	 * Deletes the specified object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param o
	 *            the o
	 */
	public static <T> void erase(Class<T> clazz, T o) {
		if (o != null) {
			Modification<T> del = getEraser(clazz);
			del.execute(o);
		}
	}

	/**
	 * Modification that deletes a persistent object.
	 *
	 * @param <T>
	 *            the generic type
	 */
	static class CleanerImpl<T extends PersistentObject<?>> implements Modification<T> {
		/**
		 * Crud command.
		 */
		CrudCmd<T> crud;

		/**
		 * Creates a new SimpleCleaner object.
		 *
		 * @param poClass
		 *            the po class
		 */
		CleanerImpl(Class<T> poClass) {
			this.crud = new CrudCmd<T>(Factory.createPw(poClass), true);
		}

		@Override
		public T execute(T a) {
			if (a == null) {
				return null;
			}
			try {
				return crud.delete(a);
			} catch (UnexpectedException e) {
				throw new RuntimeException(e);
			} catch (DataException e) {
				throw new RuntimeException(e);
			} catch (LogicException e) {
				throw new RuntimeException(e);
			}
		}
	}
}