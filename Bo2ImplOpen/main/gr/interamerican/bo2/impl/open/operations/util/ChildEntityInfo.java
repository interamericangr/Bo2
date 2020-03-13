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


import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.operations.CopyComplexEntityOperation;
import gr.interamerican.bo2.utils.adapters.Modification;

import java.io.Serializable;
import java.util.List;

/**
 * Dummy bean containing all info for a child entity that is gonna be copied
 * through {@link CopyComplexEntityOperation}, and that is added through
 * {@link CopyComplexEntityConfiguration}
 * 
 * @param <I>
 *            Input of the Operation
 * @param <K>
 *            Key of the {@link PersistentObject}
 * @param <PO>
 *            The Type of the {@link PersistentObject}
 */
public interface ChildEntityInfo<I, K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>> {

	/**
	 * Sets the Class of Additional Child Entity
	 * 
	 * @param poClz
	 *            Class of Additional Child Entity
	 */
	void setPoClz(Class<PO> poClz);

	/**
	 * Returns the class of Additional Child Entity
	 * 
	 * @return Class of Additional Child Entity
	 */
	Class<PO> getPoClz();

	/**
	 * Sets the Query to find Entities
	 * 
	 * @param query
	 *            Query to find Entities
	 */
	<Q extends EntitiesQuery<PO> & CriteriaDependent<? extends I>> void setQuery(Q query);

	/**
	 * Returns the Query to find Entities
	 * 
	 * @return Query to find Entities
	 */
	<Q extends EntitiesQuery<PO> & CriteriaDependent<? extends I>> Q getQuery();

	/**
	 * Sets the Query to find Keys of Entities
	 * 
	 * @param query
	 *            Query to find Keys of Entities
	 */
	<Q extends EntitiesQuery<K> & CriteriaDependent<? extends I>> void setQueryByKey(Q query);

	/**
	 * Returns the Query to find Keys of Entities
	 * 
	 * @return Query to find Keys of Entities
	 */
	<Q extends EntitiesQuery<K> & CriteriaDependent<? extends I>> Q getQueryByKey();

	/**
	 * Sets the Custom Modifications
	 * 
	 * @param modifications
	 *            Custom Modifications
	 */
	void setModifications(List<Modification<PO>> modifications);

	/**
	 * Returns the Custom Modifications
	 * 
	 * @return Custom Modifications
	 */
	List<Modification<PO>> getModifications();

	/**
	 * Sets the {@link CopyMode}.
	 * 
	 * @param copyMode
	 *            The Copy Mode
	 */
	void setCopyMode(CopyMode copyMode);

	/**
	 * Returns the {@link CopyMode}
	 * 
	 * @return The Copy Mode
	 */
	CopyMode getCopyMode();
}