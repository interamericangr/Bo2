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

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * A custom abstract implementation of {@link PersistenceWorker} that is based
 * on a {@link GenericHibernatePersistenceWorker} .<br>
 * This class is also an {@link AbstractResourceConsumer}<br>
 * The intention of this class is to be extended by custom
 * {@link PersistenceWorker}'s that are based on
 * {@link GenericHibernatePersistenceWorker}, but also wish to include some
 * logic into one or more of their CRUD actions.<br>
 * The implementor has to extend this and use the {@link #hibPw} whenever
 * needed.
 * 
 * @param <PO>
 *            Class that is persisted by the PersistenceWorker.
 */
public abstract class AbstractHibernateBasedPersistenceWorker<PO extends PersistentObject<?>>
extends AbstractResourceConsumer
implements PersistenceWorker<PO> {

	/** Hibernate impl. */
	@Child protected GenericHibernatePersistenceWorker<PO> hibPw;

	/**
	 * Creates a new AbstractHibernateBasedPersistenceWorker object.
	 * 
	 * @param poClass
	 *            Class of PO
	 */
	public AbstractHibernateBasedPersistenceWorker(Class<PO> poClass) {
		hibPw = new GenericHibernatePersistenceWorker<PO>(poClass);
	}

	@Override
	public PO read(PO o) throws DataException, PoNotFoundException {
		return hibPw.read(o);
	}

	@Override
	public PO store(PO o) throws DataException, PoNotFoundException {
		return hibPw.store(o);
	}

	@Override
	public PO delete(PO o) throws DataException, PoNotFoundException {
		return hibPw.delete(o);
	}

	@Override
	public PO update(PO o) throws DataException, PoNotFoundException {
		return hibPw.update(o);
	}

	@Override
	public boolean ignoresSomething() {
		return hibPw.ignoresSomething();
	}

	@Override
	public DetachStrategy getDetachStrategy() {
		return hibPw.getDetachStrategy();
	}

	@Override
	public void setManagerName(String managerName) {
		super.setManagerName(managerName);
		hibPw.setManagerName(managerName);
	}

	/**
	 * Assigns the {@link GenericHibernatePersistenceWorker} we are using.<br>
	 * This is for unit test purposes mostly.
	 * 
	 * @param hibPw
	 *            The {@link GenericHibernatePersistenceWorker} under use
	 */
	public void setHibPw(GenericHibernatePersistenceWorker<PO> hibPw) {
		this.hibPw = hibPw;
	}
}