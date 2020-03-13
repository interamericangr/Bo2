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
import gr.interamerican.bo2.impl.open.beans.ResultKey;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.attributes.Input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of {@link CopyComplexEntityConfiguration} that refers on child entities
 * that have to be copied apart the root entity.<br>
 * Each additional set of entities inserted via
 * {@link #addEntityToCopy(Class, Class, Class...)} or
 * {@link #addEntityToCopy(Class, Class, CopyMode, Class...)} or
 * {@link #addEntityToCopyByKey(Class, Class, Class...)} or
 * {@link #addEntityToCopyByKey(Class, Class, CopyMode, Class...)} will be
 * copied in the order that it was declared when creating the configuration.
 * 
 * @param <I>
 *            Input of the Operation
 */
public class CopyComplexEntityChildConfiguration<I> {

	/**
	 * All the Added Child Entities in the order they were added
	 */
	List<ChildEntityInfo<I, ?, ?>> childEntities = new ArrayList<ChildEntityInfo<I, ?, ?>>();

	/**
	 * Adds an additional relevant referenced entity to be copied.<br>
	 * The query is used in order to identify all the relevant entities.<br>
	 * It is assumed that the entities returned by the Query have been read, and
	 * are ready to be copied.<br>
	 * {@link CopyMode} is not set.<br>
	 * Also if required - you can register custom modification(s) to run on
	 * each entity. The modification will run just before persisting the
	 * entity on the target environment. If for some reason you want to skip an
	 * entry from being copied - the modification has to return null. These
	 * modifications can implement the {@link Input} or {@link ResultKey} if the
	 * corresponding bean of the operation is required for some reason on the
	 * modification as well.
	 * 
	 * @param poClz
	 *            Class of Additional Child Entity
	 * @param queryClz
	 *            Class of Query to find Entities
	 * @param modifications
	 *            Custom Modifications
	 */
	@SafeVarargs
	public final <PO extends PersistentObject<?>, Q extends EntitiesQuery<PO> & CriteriaDependent<? extends I>> void addEntityToCopy(
			Class<PO> poClz, Class<Q> queryClz, Class<? extends Modification<PO>>... modifications) {
		addEntityToCopy(poClz, queryClz, null, modifications);
	}

	/**
	 * Adds an additional relevant referenced entity to be copied.<br>
	 * The query is used in order to identify all the relevant entities.<br>
	 * It is assumed that the entities returned by the Query have been read, and
	 * are ready to be copied.<br>
	 * Also if required - you can register custom modification(s) to run on
	 * each entity. The modification will run just before persisting the
	 * entity on the target environment. If for some reason you want to skip an
	 * entry from being copied - the modification has to return null. These
	 * modifications can implement the {@link Input} or {@link ResultKey} if the
	 * corresponding bean of the operation is required for some reason on the
	 * modification as well.
	 * 
	 * @param poClz
	 *            Class of Additional Child Entity
	 * @param queryClz
	 *            Class of Query to find Entities
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            Custom Modifications
	 */
	@SafeVarargs
	public final <PO extends PersistentObject<?>, Q extends EntitiesQuery<PO> & CriteriaDependent<? extends I>> void addEntityToCopy(
			Class<PO> poClz, Class<Q> queryClz, CopyMode copyMode, Class<? extends Modification<PO>>... modifications) {
		ChildEntityInfo<I, ?, PO> info = commonAdd(poClz, copyMode, modifications);
		info.setQuery(Factory.create(queryClz));
	}

	/**
	 * Adds an additional relevant referenced entity to be copied.<br>
	 * The query is used in order to identify all the relevant entities.<br>
	 * {@link CopyMode} is not set.<br>
	 * Also if required - you can register a custom modification(s) to run on
	 * each entity. The modification will be run just before persisting the
	 * entity on the target environment. If for some reason you want to skip an
	 * entry from being copied - the modification has to return null. These
	 * modifications can implement the {@link Input} or {@link ResultKey} if the
	 * corresponding bean of the operation is required for some reason on the
	 * modification as well.
	 * 
	 * @param poClz
	 *            Class of Additional Child Entity
	 * @param queryClz
	 *            Class of Query to find Entities
	 * @param modifications
	 *            Custom Modifications
	 */
	@SafeVarargs
	public final <K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>, Q extends EntitiesQuery<K> & CriteriaDependent<? extends I>> void addEntityToCopyByKey(
			Class<PO> poClz, Class<Q> queryClz, Class<? extends Modification<PO>>... modifications) {
		addEntityToCopyByKey(poClz, queryClz, null, modifications);
	}

	/**
	 * Adds an additional relevant referenced entity to be copied.<br>
	 * The query is used in order to identify all the relevant entities.<br>
	 * Also if required - you can register custom modification(s) to run on
	 * each entity. The modification will run just before persisting the
	 * entity on the target environment. If for some reason you want to skip an
	 * entry from being copied - the modification has to return null. These
	 * modifications can implement the {@link Input} or {@link ResultKey} if the
	 * corresponding bean of the operation is required for some reason on the
	 * modification as well.
	 * 
	 * @param poClz
	 *            Class of Additional Child Entity
	 * @param queryClz
	 *            Class of Query to find Entities
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            Custom Modifications
	 */
	@SafeVarargs
	public final <K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>, Q extends EntitiesQuery<K> & CriteriaDependent<? extends I>> void addEntityToCopyByKey(
			Class<PO> poClz, Class<Q> queryClz, CopyMode copyMode, Class<? extends Modification<PO>>... modifications) {
		@SuppressWarnings("unchecked")
		ChildEntityInfo<I, K, PO> info = (ChildEntityInfo<I, K, PO>) commonAdd(poClz, copyMode, modifications);
		info.setQueryByKey(Factory.create(queryClz));
	}

	/**
	 * Creates a {@link ChildEntityInfo} with the given arguments and adds it to
	 * {@link #childEntities}.
	 * 
	 * @param poClz
	 *            Class of Child Entity
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            Modifications if the Child Entity
	 * @return New {@link ChildEntityInfo}
	 */
	<PO extends PersistentObject<?>> ChildEntityInfo<I, ?, PO> commonAdd(Class<PO> poClz, CopyMode copyMode,
			Class<? extends Modification<PO>>[] modifications) {
		@SuppressWarnings("unchecked")
		ChildEntityInfo<I, ?, PO> info = Factory.create(ChildEntityInfo.class);
		info.setPoClz(poClz);
		info.setModifications(new ArrayList<Modification<PO>>());
		for (Class<? extends Modification<PO>> modification : modifications) {
			info.getModifications().add(Factory.create(modification));
		}
		info.setCopyMode(copyMode);
		childEntities.add(info);
		return info;
	}

	/**
	 * Returns all the Added Child Entities in the order they were added.
	 * 
	 * @return {@link List} of {@link ChildEntityInfo}
	 */
	public List<ChildEntityInfo<I, ?, ?>> getChildEntities() {
		return childEntities;
	}
}