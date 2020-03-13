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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.beans.FromToManagerOwner;
import gr.interamerican.bo2.impl.open.beans.ResultKey;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.operations.util.ChildEntityInfo;
import gr.interamerican.bo2.impl.open.operations.util.CopyComplexEntityChildConfiguration;
import gr.interamerican.bo2.impl.open.operations.util.CopyComplexEntityConfiguration;
import gr.interamerican.bo2.impl.open.operations.util.CopyMode;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.utils.Bo2Utils;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.bo2.utils.beans.Pair;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Generic Operation to copy a Complex Entity from one environment to another.<br>
 * The environments are defined by the manager names.<br>
 * All the Entity specific Configuration is done with
 * {@link CopyComplexEntityConfiguration}.<br>
 * The Input usually represents the fields that consist the {@link Key} of the
 * root entity.<br>
 * Optionally, we may define the result key that will be used as the entity's
 * key in the target environment. If we don't do so, the key of the entity in
 * the source environment will be preserved.
 * 
 * @param <I>
 *            Input of the Operation
 */
public class CopyComplexEntityOperation<I>
extends AbstractOperation
implements Input<I>, ResultKey<I>, FromToManagerOwner {

	/**
	 * Input of the operation
	 */
	I input;

	/**
	 * From Manager
	 */
	String fromManager;

	/**
	 * To Manager
	 */
	String toManager;

	/**
	 * Configuration of the Operation
	 */
	CopyComplexEntityConfiguration<I, ?> configuration;

	/**
	 * Optional key of the resulting entity
	 */
	I resultKey;

	/**
	 * Use {@link CopyMode#OVERWRITE} as default {@link CopyMode} instead of {@link CopyMode#DEFAULT}
	 */
	boolean useOverwriteAsDefault = false;

	/**
	 * Public Constructor.
	 * 
	 * @param configuration
	 *            Configuration to use
	 */
	public CopyComplexEntityOperation(CopyComplexEntityConfiguration<I, ?> configuration) {
		this.configuration = configuration;
	}

	@Override
	public void execute() throws LogicException, DataException {
		if(Utils.equals(fromManager, toManager) && resultKey == null) {
			throw new DataException("For same managers resultKey cannot be null"); //$NON-NLS-1$
		}
		processChildConfiguration(configuration.getBefore());
		processRootEntity();
		processChildConfiguration(configuration.getAfter());
	}

	/**
	 * Processes a {@link CopyComplexEntityChildConfiguration}.
	 * 
	 * @param childConfiguration The {@link CopyComplexEntityChildConfiguration}
	 * @throws DataException
	 */
	void processChildConfiguration(CopyComplexEntityChildConfiguration<I> childConfiguration) throws DataException {
		if (childConfiguration == null) {
			return;
		}
		for (ChildEntityInfo<?, ?, ?> child : childConfiguration.getChildEntities()) {
			processChild(child);
		}
	}

	/**
	 * Copies the Root Entity.
	 * 
	 * @throws DataException
	 */
	<PO extends PersistentObject<? extends I>> void processRootEntity() throws DataException {
		CopyComplexEntityConfiguration<I, PO> casted = Utils.cast(configuration);
		Class<PO> rootClz = casted.getRootEntity();
		PO rootEntity = Factory.create(rootClz);
		ReflectionUtils.copyProperties(input, rootEntity.getKey());
		rootEntity = getWorker(rootClz, fromManager).read(rootEntity);
		copyEntity(rootClz, rootEntity, casted.getRootCopyMode(), casted.getRootModifications());
	}

	/**
	 * Copies A Child Entity.<br>
	 * Depending if a Query or Query by key was given - this calls the correct
	 * method.
	 * 
	 * @param child
	 *            The Info for that Child Copy
	 * @throws DataException
	 */
	<K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>> void processChild(
			ChildEntityInfo<?,?,?> child) throws DataException {
		ChildEntityInfo<I, K, PO> casted = Utils.cast(child);
		if (child.getQuery() != null) {
			readChilds(casted.getQuery(), casted.getPoClz(), casted.getCopyMode(), casted.getModifications());
		} else {
			readChildsByKey(casted.getQueryByKey(), casted.getPoClz(), casted.getCopyMode(), casted.getModifications());
		}
	}

	/**
	 * Copies a Child with a use of an {@link EntitiesQuery} that returns the
	 * {@link PersistentObject}s themselves.
	 * 
	 * @param query
	 *            {@link EntitiesQuery}
	 * @param poClz
	 *            Class Of Child {@link PersistentObject}
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            {@link Modification}s to apply
	 * @throws DataException
	 */
	<Q extends EntitiesQuery<PO> & CriteriaDependent<? extends I>, K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>> void readChilds(
			Q query, Class<PO> poClz, CopyMode copyMode, List<Modification<PO>> modifications) throws DataException {
		initializeQuery(query);
		while (query.next()) {
			copyEntity(poClz, query.getEntity(), copyMode, modifications);
		}
	}

	/**
	 * Copies a Child with a use of an {@link EntitiesQuery} that returns the
	 * Key of the {@link PersistentObject}s to copy.
	 * 
	 * @param query
	 *            {@link EntitiesQuery}
	 * @param poClz
	 *            Class Of Child {@link PersistentObject}
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            {@link Modification}s to apply
	 * @throws DataException
	 */
	<Q extends EntitiesQuery<K> & CriteriaDependent<? extends I>, K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>> void readChildsByKey(
			Q query, Class<PO> poClz, CopyMode copyMode, List<Modification<PO>> modifications) throws DataException {
		initializeQuery(query);
		while (query.next()) {
			PO po = Factory.create(poClz);
			po.setKey(query.getEntity());
			copyEntity(poClz, getWorker(poClz, fromManager).read(po), copyMode, modifications);
		}
	}

	/**
	 * Copies an entity.
	 * 
	 * @param poClz
	 *            Class of the entity to copy
	 * @param entity
	 *            Entity to copy
	 * @param copyMode
	 *            Desired {@link CopyMode}
	 * @param modifications
	 *            Modification to apply
	 * @throws DataException
	 */
	<PO extends PersistentObject<?>> void copyEntity(Class<PO> poClz, PO entity, CopyMode copyMode,
			List<Modification<PO>> modifications) throws DataException {
		PO newEntity = PoUtils.deepCopy(entity);
		if (resultKey != null) {
			ReflectionUtils.copyProperties(resultKey, newEntity);
		}
		newEntity = applyModification(newEntity, modifications);
		if (newEntity == null) {
			return;
		}
		PersistenceWorker<PO> worker = getWorker(poClz, toManager);
		try {
			PO targetEntity = Factory.create(poClz);
			ReflectionUtils.copyProperties(newEntity.getKey(), targetEntity.getKey());
			targetEntity = worker.read(targetEntity);
			CopyMode finalCopyMode = Utils.notNull(copyMode, useOverwriteAsDefault ? CopyMode.OVERWRITE
					: CopyMode.DEFAULT);
			finalCopyMode.handle(newEntity, targetEntity, worker, poClz, toManager);
		} catch (PoNotFoundException pnfe) {
			worker.store(newEntity);
		}
	}

	/**
	 * Applies the modifications on an Entity.
	 * 
	 * @param entity
	 *            Copied Entity
	 * @param modifications
	 *            Modification to apply
	 * @return The Entity 'modified'
	 */
	@SuppressWarnings("unchecked")
	<PO extends PersistentObject<?>> PO applyModification(PO entity, List<Modification<PO>> modifications) {
		if (CollectionUtils.isNullOrEmpty(modifications)) {
			return entity;
		}
		PO result = entity;
		Iterator<Modification<PO>> it = modifications.iterator();
		while (it.hasNext() && result != null) {
			Modification<PO> modification = it.next();
			if (modification instanceof Input) {
				((Input<I>) modification).setInput(input);
			}
			if (modification instanceof ResultKey) {
				((ResultKey<I>) modification).setResultKey(resultKey);
			}
			result = modification.execute(result);
		}
		return result;
	}

	/**
	 * cache to be used during runtime in order to keep track of the created
	 * workers this has
	 */
	Map<Pair<Class<? extends PersistentObject<?>>, String>, PersistenceWorker<?>> workerCache = new HashMap<Pair<Class<? extends PersistentObject<?>>, String>, PersistenceWorker<?>>();

	/**
	 * Fetches a {@link PersistenceWorker} for a specific
	 * {@link PersistentObject} and manager.<br>
	 * In order to avoid having multiple instances of {@link PersistenceWorker}s
	 * in this, we use this method and {@link #workerCache} to keep track of
	 * what we have created so far.
	 * 
	 * @param poClz
	 *            Class of the {@link PersistentObject}
	 * @param manager
	 *            Desired Manager
	 * @return {@link PersistenceWorker} ready for use
	 * @throws DataException
	 */
	<PO extends PersistentObject<?>> PersistenceWorker<PO> getWorker(Class<PO> poClz, String manager)
			throws DataException {
		Pair<Class<? extends PersistentObject<?>>, String> pair = new Pair<Class<? extends PersistentObject<?>>, String>(
				poClz, manager);
		PersistenceWorker<PO> returned = Utils.cast(workerCache.get(pair));
		if (returned == null) {
			returned = Factory.createPw(poClz);
			initialize(manager, returned);
			workerCache.put(pair, returned);
		}
		return returned;
	}

	/**
	 * cache to be used during runtime in order to keep track of the created
	 * workers this has
	 */
	List<Worker> managed = new ArrayList<Worker>();

	/**
	 * Initializes a Query and Executes it.<br>
	 * This does :
	 * <ul>
	 * <li>Sets the correct criteria to it
	 * <li>Calls {@link #initialize(String, Worker)} if it's not managed
	 * <li>Executes it
	 * </ul>
	 * 
	 * @param query
	 *            Query to configure
	 * @throws DataException
	 */
	<T extends I, Q extends CriteriaDependent<? extends I> & Query> void initializeQuery(Q query) throws DataException {
		CriteriaDependent<T> casted = Utils.cast(query);
		T criteria = Bo2Utils.getCriteria(casted);
		ReflectionUtils.copyProperties(input, criteria);
		casted.setCriteria(criteria);
		if (!managed.contains((Worker) query)) {
			initialize(fromManager, query);
			managed.add(query);
		}
		query.execute();
	}

	/**
	 * Initializes a Worker and sets it on a specific Manager.
	 * 
	 * @param manager
	 *            Manager Name
	 * @param worker
	 *            Worker to initialize
	 * @throws DataException
	 */
	void initialize(String manager, Worker worker) throws DataException {
		markAsChild(worker);
		if (worker instanceof AbstractResourceConsumer) {
			setManagerName((AbstractResourceConsumer) worker, manager);
		} else {
			throw new DataException("Cannot copy entities that do not allow manager configuration " + worker.getClass()); //$NON-NLS-1$
		}
		try {
			worker.init(getProvider());
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		worker.open();
	}

	/**
	 * Sets the desired manager name on a {@link AbstractResourceConsumer} and
	 * any {@link Worker}'s that are also implemented with
	 * {@link AbstractResourceConsumer} within it.
	 * 
	 * @param consumer
	 *            {@link AbstractResourceConsumer} to modify
	 * @param manager
	 *            Desired Manager Name
	 */
	void setManagerName(AbstractResourceConsumer consumer, String manager) {
		consumer.setManagerName(manager);
		for (Field field : ReflectionUtils.allFields(consumer.getClass(), AbstractResourceConsumer.class)) {
			if (Worker.class.isAssignableFrom(field.getType())) {
				Object fieldObject = ReflectionUtils.get(field.getName(), consumer);
				if (fieldObject instanceof AbstractResourceConsumer) {
					setManagerName((AbstractResourceConsumer) fieldObject, manager);
				}
			}
		}
	}

	@Override
	public void setInput(I input) {
		this.input = input;
	}

	@Override
	public void setResultKey(I resultKey) {
		this.resultKey = resultKey;
	}

	@Override
	public void setToManager(String toManager) {
		this.toManager = toManager;
	}

	@Override
	public void setFromManager(String fromManager) {
		this.fromManager = fromManager;
	}

	/**
	 * Sets whether we want to use {@link CopyMode#OVERWRITE} as default
	 * {@link CopyMode} instead of {@link CopyMode#DEFAULT}.<br>
	 * This is by default 'false'.
	 * 
	 * @param useOverwriteAsDefault
	 *            If we want to use {@link CopyMode#OVERWRITE} as default
	 *            {@link CopyMode} instead of {@link CopyMode#DEFAULT}
	 */
	public void setUseOverwriteAsDefault(boolean useOverwriteAsDefault) {
		this.useOverwriteAsDefault = useOverwriteAsDefault;
	}
}