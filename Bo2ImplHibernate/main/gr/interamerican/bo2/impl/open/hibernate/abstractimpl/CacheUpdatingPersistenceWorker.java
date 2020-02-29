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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.hibernate.abstractimpl.AbstractHibernateBasedPersistenceWorker;

/**
 * An {@link AbstractHibernateBasedPersistenceWorker} that does persistence
 * operation on objects both in DB and on the input {@link Cache}.
 * 
 * @param <PO>
 *            Class that is persisted by the PersistenceWorker.
 * @param <C>
 *            Type of entries code.
 */
public class CacheUpdatingPersistenceWorker<C extends Comparable<? super C>, PO extends PersistentObject<?> & TypedSelectable<C>>
extends AbstractHibernateBasedPersistenceWorker<PO>
implements PersistenceWorker<PO> {

	/**
	 * Cache to be updated
	 */
	final Cache<C> cache;

	/**
	 * Public Constructor.
	 *
	 * @param poClass
	 * @param cache
	 *            Cache to be updated
	 */
	public CacheUpdatingPersistenceWorker(Cache<C> cache,Class<PO> poClass) {
		super(poClass);
		this.cache = cache;
	}

	@Override
	public PO update(PO o) throws DataException, PoNotFoundException {
		cache.remove(o);
		PO updated = super.update(o);
		cache.put(updated);
		return updated;
	}

	@Override
	public PO delete(PO o) throws DataException, PoNotFoundException {
		cache.remove(o);
		return super.delete(o);
	}

	@Override
	public PO store(PO o) throws DataException, PoNotFoundException {
		PO stored = super.store(o);
		cache.put(stored);
		return stored;
	}
}