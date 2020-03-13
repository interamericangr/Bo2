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
package gr.interamerican.bo2.impl.open.operations.util;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.operations.CopyComplexEntityOperation;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.PoUtils;

/**
 * This defines the behavior of {@link CopyComplexEntityOperation} when it tries
 * to copy an entity that already exists on the target environment.
 */
public enum CopyMode {

	/**
	 * Default Mode. Will throw an exception.
	 */
	DEFAULT {
		@Override
		public <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
				Class<PO> poClz, String toManager) throws DataException {
			throw new DataException("Entity target already exists " + target.getKey()); //$NON-NLS-1$
		}
	},

	/**
	 * Overwrite.<br>
	 * It will overwrite the target entity with the values the source entity
	 * has.<br>
	 * Also see the restrictions of
	 * {@link PoUtils#merge(PersistentObject, PersistentObject, MergeMode, Class, String)}
	 * when using this.
	 */
	OVERWRITE {
		@Override
		public <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
				Class<PO> poClz, String toManager) throws DataException {
			PoUtils.merge(source, target, MergeMode.OVERWRITE, poClz, toManager);
			worker.update(target);
		}
	},

	/**
	 * Keep Target Mode.<br>
	 * No changes will be done on the target entity.
	 */
	KEEP_TARGET {
		@Override
		public <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
				Class<PO> poClz, String toManager) throws DataException {
			return;
		}
	},

	/**
	 * Merge Favor Source.<br>
	 * The target entity will be overwritten by the source entity, but any extra
	 * child elements that existed on the target entity will be retained.<br>
	 * Also see the restrictions of
	 * {@link PoUtils#merge(PersistentObject, PersistentObject, MergeMode, Class, String)}
	 * when using this.
	 */
	MERGE_FAVOR_SOURCE {
		@Override
		public <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
				Class<PO> poClz, String toManager) throws DataException {
			PoUtils.merge(source, target, MergeMode.FAVOR_SOURCE, poClz, toManager);
			worker.update(target);
		}
	},

	/**
	 * Merge Favor Target.<br>
	 * The target entity will be kept as is, but any extra child entities that
	 * exist on the source entity will be added to it.<br>
	 * Also see the restrictions of
	 * {@link PoUtils#merge(PersistentObject, PersistentObject, MergeMode, Class, String)}
	 * when using this.
	 */
	MERGE_FAVOR_TARGET {
		@Override
		public <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
				Class<PO> poClz, String toManager) throws DataException {
			PoUtils.merge(source, target, MergeMode.FAVOR_TARGET, poClz, toManager);
			worker.update(target);
		}
	};

	/**
	 * Does the handling per copy mode.<br>
	 * The main responsibility of this is to call worker#update method,
	 * according to each case.
	 * 
	 * @param source
	 *            Source entity
	 * @param target
	 *            Target Entity
	 * @param worker
	 *            Worker to Use
	 * @param poClz
	 *            Class of the Entity
	 * @param toManager
	 *            Manager of the target entity
	 * @throws DataException
	 */
	public abstract <PO extends PersistentObject<?>> void handle(PO source, PO target, PersistenceWorker<PO> worker,
			Class<PO> poClz, String toManager) throws DataException;
}