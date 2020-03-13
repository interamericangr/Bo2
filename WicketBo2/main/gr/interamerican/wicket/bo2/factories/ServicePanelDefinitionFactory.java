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
package gr.interamerican.wicket.bo2.factories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.NumberCalculator;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.functions.SerializableBiConsumer;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.wicket.bo2.callbacks.DeleteAction;
import gr.interamerican.wicket.bo2.callbacks.QueryAction;
import gr.interamerican.wicket.bo2.callbacks.SaveAction;
import gr.interamerican.wicket.bo2.callbacks.SaveWithQuestionAction;
import gr.interamerican.wicket.bo2.callbacks.UpdateAction;
import gr.interamerican.wicket.callback.AddElementToCollectionAction;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.callback.RemoveElementFromSet;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDefImpl;

/**
 * Factory for {@link SearchFlowPanelDef} objects.
 */
public class ServicePanelDefinitionFactory {

	/**
	 * Creates a CrudPickerPanelDefinition for a CrudPickerPanel.<br>
	 * The definition created by this method does not have a back action, a pick
	 * action, a save action, an update action and a delete action. It also does
	 * not have any PanelCreator or DataTableCreator. These properties of the
	 * {@link SearchFlowPanelDef} must be filled manually. The same goes for the
	 * wicket id and the list of the table elements to be shown in the panel.
	 * 
	 * @param bean
	 *            Class of Bean.
	 * @param <B>
	 *            Type of Bean.
	 * 
	 * @return Returns a {@link CrudPickerPanelDef}.
	 */
	public static <B extends Serializable> CrudPickerPanelDef<B> crudPickerPanelDef(Class<B> bean) {
		CrudPickerPanelDef<B> def = new CrudPickerPanelDefImpl<B>();
		B po = Factory.create(bean);
		IModel<B> poModel = new CompoundPropertyModel<B>(po);
		def.setBeanModel(poModel);
		return def;
	}

	/**
	 * Creates a CrudPickerPanelDefinition.<br>
	 * 
	 * The definition is the same as on {@link #crudPickerPanelDef(Class)}, with
	 * the exception that it has a save, update and delete action based on the
	 * CrudCallbackActionsOwner passed as argument.
	 * 
	 * @param bean
	 *            Class of Bean.
	 * @param owner
	 *            The CrudCallbackActionsOwner
	 * @param <B>
	 *            Type of Bean.
	 * 
	 * @return Returns a {@link CrudPickerPanelDef}.
	 * @deprecated Deprecated due to changed interfaces of crud actions
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <B extends Serializable> CrudPickerPanelDef<B> crudPickerPanelDef(Class<B> bean,
			gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner owner) {
		CrudPickerPanelDef<B> def = crudPickerPanelDef(bean);
		gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock saveAction = new gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock(
				gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner.SAVE_ACTION, owner);
		def.setSaveAction(saveAction);
		gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock updateAction = new gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock(
				gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner.UPDATE_ACTION, owner);
		def.setUpdateAction(updateAction);
		gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock deleteAction = new gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock(
				gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner.DELETE_ACTION, owner);
		def.setDeleteAction(deleteAction);
		return def;
	}

	/**
	 * Creates a CrudPickerPanelDefinition.<br>
	 * The definition is the same as on {@link #crudPickerPanelDef(Class)}, with
	 * the exception that it has a {@link SaveWithQuestionAction} ,
	 * {@link UpdateAction} and {@link DeleteAction} with the use of the
	 * question class passed as argument.
	 *
	 * @param <K>
	 *            Type of persistent object key.
	 * @param <PO>
	 *            Type of persistent object.
	 * @param <KQ>
	 *            Type of next key question.
	 * @param poClass
	 *            Class of PersistentObject.
	 * @param questionClass
	 *            Type of next key question.
	 * @return Returns a {@link CrudPickerPanelDef}.
	 */
	public static <K extends Serializable & Comparable<? super K>, PO extends PersistentObject<K>, KQ extends Question<K> & PoDependent<PO>> CrudPickerPanelDef<PO> crudPickerPanelDef(
			Class<PO> poClass, Class<KQ> questionClass) {
		CrudPickerPanelDef<PO> def = crudPickerPanelDef(poClass);
		def.setUpdateAction(new UpdateAction<>(poClass));
		def.setDeleteAction(new DeleteAction<>(poClass));
		def.setSaveAction(new SaveWithQuestionAction<>(poClass, questionClass));
		return def;
	}

	/**
	 * Creates a CrudPickerPanelDefinition.<br>
	 * 
	 * This definition is the same as on
	 * {@link #crudPickerPanelDef(Class, Class)}, with the question in use being
	 * the {@link JustCopyIdQuestion}.<br>
	 * This means that the responsibility to find the next id in case of a new
	 * entity is not being covered by a {@link Question}, but by other means
	 * (for example by the {@link PersistenceWorker} itself).
	 *
	 * @param <PO>
	 *            Type of persistent object.
	 * @param poClass
	 *            Class of PersistentObject.
	 * @return Returns a {@link CrudPickerPanelDef}.
	 */
	public static <PO extends PersistentObject<?>> CrudPickerPanelDef<PO> crudPickerPanelDefWithActions(
			Class<PO> poClass) {
		CrudPickerPanelDef<PO> def = crudPickerPanelDef(poClass);
		def.setUpdateAction(new UpdateAction<>(poClass));
		def.setDeleteAction(new DeleteAction<>(poClass));
		def.setSaveAction(new SaveAction<>(poClass));
		return def;
	}

	/**
	 * Creates a {@link CrudPickerPanelDef} that supports 'CRUD' over a given
	 * set.<br>
	 * The definition has the save,update,delete action, list and bean model
	 * filled by this. <br>
	 * Usually when this is used the {@link PersistentObject}s are not stored -
	 * updated or deleted directly on the DB, but rather updated directly on the
	 * parent set, and at some point all the changes are transfered on the
	 * DB.<br>
	 * Also this sets the indexPropertyName of each new element that is being
	 * added, in a special manner so that when we remove an element, and then
	 * add a new element, the new element does not use the id of the removed
	 * element, but instead allocates a new one.
	 *
	 * @param <CKP>
	 *            the generic type
	 * @param <PK>
	 *            the generic type
	 * @param <PO>
	 *            the generic type
	 * @param poClass
	 *            Class of the Managed {@link PersistentObject}
	 * @param set
	 *            Set to Modify - usually a {@link Child} set of a
	 *            {@link PersistentObject}
	 * @param indexPropertyName
	 *            Name of the property that defines the index
	 * @param parentKey
	 *            Key Properties always set on the new entity (usually Key
	 *            Properties of the Parent {@link PersistentObject}) (optional)
	 * @return New Definition
	 * @deprecated Use
	 *             {@link #crudPickerPanelDefL(Class, Set, Object, SerializableFunction, SerializableBiConsumer)}
	 *             or
	 *             {@link #crudPickerPanelDefI(Class, Set, Object, SerializableFunction, SerializableBiConsumer)}
	 *             or
	 *             {@link #crudPickerPanelDef(Class, Set, Object, SerializableFunction, SerializableBiConsumer, NumberCalculator)}
	 */
	@Deprecated
	public static <CKP, PK extends CKP, PO extends PersistentObject<? extends CKP>> CrudPickerPanelDef<PO> crudPickerPanelDef(
			Class<PO> poClass, Set<PO> set, String indexPropertyName, PK parentKey) {
		CrudPickerPanelDef<PO> def = crudPickerPanelDef(poClass);
		def.setSaveAction(new gr.interamerican.wicket.callback.AddElementToSet<PO>(set, indexPropertyName, parentKey));
		def.setUpdateAction(ProcessAction.identity());
		def.setDeleteAction(new RemoveElementFromSet<PO>(set));
		List<PO> list = new ArrayList<PO>(set);
		list = CollectionUtils.sort(list, poClass, indexPropertyName);
		def.setList(list);
		return def;
	}

	/**
	 * Creates a {@link CrudPickerPanelDef} that supports 'CRUD' over a given
	 * set.<br>
	 * The definition has the save,update,delete action, list and bean model
	 * filled by this. <br>
	 * Usually when this is used the {@link PersistentObject}s are not stored -
	 * updated or deleted directly on the DB, but rather updated directly on the
	 * parent set, and at some point all the changes are transfered on the
	 * DB.<br>
	 * Also this sets the indexPropertyName of each new element that is being
	 * added, in a special manner so that when we remove an element, and then
	 * add a new element, the new element does not use the id of the removed
	 * element, but instead allocates a new one.
	 *
	 * @param <PK>
	 *            Type of the parent key
	 * @param <PO>
	 *            Type of the {@link PersistentObject}
	 * @param <I>
	 *            Type of the Index Key
	 * @param poClass
	 *            Class of the Managed {@link PersistentObject}
	 * @param set
	 *            Set to Modify - usually a {@link Child} set of a
	 *            {@link PersistentObject}
	 * @param parentKey
	 *            Key Properties always set on the new entity (usually Key
	 *            Properties of the Parent {@link PersistentObject}) (optional)
	 * @param getter
	 *            Getter of the property that defines the index
	 * @param setter
	 *            Setter of the property that defines the index
	 * @param calculator
	 *            {@link NumberCalculator} for the index
	 * @return New Definition
	 */
	public static <PK, PO extends PersistentObject<? extends PK>, I extends Number & Comparable<I>> CrudPickerPanelDef<PO> crudPickerPanelDef(
			Class<PO> poClass, Set<PO> set, PK parentKey, SerializableFunction<PO, I> getter,
			SerializableBiConsumer<PO, I> setter, NumberCalculator<I> calculator) {
		CrudPickerPanelDef<PO> def = crudPickerPanelDef(poClass);
		def.setSaveAction(new AddElementToCollectionAction<>(set, getter, setter, parentKey, calculator));
		def.setUpdateAction(ProcessAction.identity());
		def.setDeleteAction(new RemoveElementFromSet<PO>(set));
		List<PO> list = new ArrayList<PO>(set);
		list = CollectionUtils.sort(list, getter);
		def.setList(list);
		return def;
	}

	/**
	 * Invokes
	 * {@link #crudPickerPanelDef(Class, Set, Object, SerializableFunction, SerializableBiConsumer, NumberCalculator)}
	 * for an index Type of {@link Long}.
	 *
	 * @param <PK>
	 *            Type of the parent key
	 * @param <PO>
	 *            Type of the {@link PersistentObject}
	 * @param poClass
	 *            Class of the Managed {@link PersistentObject}
	 * @param set
	 *            Set to Modify - usually a {@link Child} set of a
	 *            {@link PersistentObject}
	 * @param parentKey
	 *            Key Properties always set on the new entity (usually Key
	 *            Properties of the Parent {@link PersistentObject}) (optional)
	 * @param getter
	 *            Getter of the property that defines the index
	 * @param setter
	 *            Setter of the property that defines the index
	 * @return New Definition
	 */
	public static <PK, PO extends PersistentObject<? extends PK>> CrudPickerPanelDef<PO> crudPickerPanelDefL(
			Class<PO> poClass, Set<PO> set, PK parentKey, SerializableFunction<PO, Long> getter,
			SerializableBiConsumer<PO, Long> setter) {
		return crudPickerPanelDef(poClass, set, parentKey, getter, setter, NumberCalculator.LONG);
	}

	/**
	 * Invokes
	 * {@link #crudPickerPanelDef(Class, Set, Object, SerializableFunction, SerializableBiConsumer, NumberCalculator)}
	 * for an index Type of {@link Integer}.
	 *
	 * @param <PK>
	 *            Type of the parent key
	 * @param <PO>
	 *            Type of the {@link PersistentObject}
	 * @param poClass
	 *            Class of the Managed {@link PersistentObject}
	 * @param set
	 *            Set to Modify - usually a {@link Child} set of a
	 *            {@link PersistentObject}
	 * @param parentKey
	 *            Key Properties always set on the new entity (usually Key
	 *            Properties of the Parent {@link PersistentObject}) (optional)
	 * @param getter
	 *            Getter of the property that defines the index
	 * @param setter
	 *            Setter of the property that defines the index
	 * @return New Definition
	 */
	public static <PK, PO extends PersistentObject<? extends PK>> CrudPickerPanelDef<PO> crudPickerPanelDefI(
			Class<PO> poClass, Set<PO> set, PK parentKey, SerializableFunction<PO, Integer> getter,
			SerializableBiConsumer<PO, Integer> setter) {
		return crudPickerPanelDef(poClass, set, parentKey, getter, setter, NumberCalculator.INTEGER);
	}

	/**
	 * Creates a SearchFlowPanelDefinition for a SearcFlowPanel that supports
	 * CRUD.
	 * 
	 * The definition created by this method does not have a back action or a
	 * pick action. It also does not have any PanelCreator or DataTableCreator.
	 * These properties of the {@link SearchFlowPanelDef} must be filled
	 * manually. The same goes for the wicket id.
	 *
	 * @param <K>
	 *            Type of persistent object key.
	 * @param <C>
	 *            Type of criteria.
	 * @param <PO>
	 *            Type of persistent object.
	 * @param <KQ>
	 *            Type of next key question.
	 * @param <Q>
	 *            Type of query.
	 * @param criteriaClass
	 *            Class of criteria bean.
	 * @param poClass
	 *            Class of PersistentObject.
	 * @param queryClass
	 *            Class of query.
	 * @param questionClass
	 *            Class of next key question.
	 * @return Returns a {@link SearchFlowPanelDef}.
	 */
	public static <K extends Serializable & Comparable<? super K>, C extends Serializable, PO extends PersistentObject<K>, KQ extends Question<K> & PoDependent<PO>, Q extends EntitiesQuery<PO> & CriteriaDependent<C>> SearchFlowPanelDef<C, PO> sfpDefCrud(
			Class<C> criteriaClass, Class<PO> poClass, Class<Q> queryClass, Class<KQ> questionClass) {

		final SearchFlowPanelDef<C, PO> def = new SearchFlowPanelDefImpl<C, PO>();
		def.setResultsHidesCriteria(null);
		def.setBackAction(null);
		def.setPickAction(null);
		def.setBeanFieldsPanelCreator(null);
		def.setCriteriaFieldsPanelCreator(null);
		def.setDataTableCreator(null);

		PO po = Factory.create(poClass);
		IModel<PO> poModel = new CompoundPropertyModel<PO>(po);
		def.setBeanModel(poModel);

		def.setUpdateAction(new UpdateAction<>(poClass));
		def.setDeleteAction(new DeleteAction<>(poClass));
		def.setSaveAction(new SaveWithQuestionAction<>(poClass, questionClass));

		C criteria = Factory.create(criteriaClass);
		IModel<C> criteriaModel = new CompoundPropertyModel<C>(criteria);
		def.setCriteriaModel(criteriaModel);

		def.setQueryAction(new QueryAction<>(queryClass));
		return def;

	}

	/**
	 * Creates a SearchFlowPanelDefinition for a SearcFlowPanel that does not
	 * support CRUD.
	 * 
	 * The definition created by this method does not have a back action or a
	 * pick action. It also does not have any PanelCreator or DataTableCreator.
	 * These properties of the {@link SearchFlowPanelDef} must be filled
	 * manually. The same goes for the wicket id.
	 *
	 * @param <C>
	 *            Type of criteria.
	 * @param <B>
	 *            Type of bean.
	 * @param <Q>
	 *            Type of query.
	 * 
	 * @param criteriaClass
	 *            Class of criteria bean.
	 * @param beanClass
	 *            Class of bean.
	 * @param queryClass
	 *            Class of query.
	 * @return Returns a {@link SearchFlowPanelDef}.
	 */
	public static <C extends Serializable, B extends Serializable, Q extends EntitiesQuery<B> & CriteriaDependent<C>> SearchFlowPanelDef<C, B> sfpDefQuery(
			Class<C> criteriaClass, Class<B> beanClass, Class<Q> queryClass) {

		final SearchFlowPanelDef<C, B> def = new SearchFlowPanelDefImpl<C, B>();
		def.setResultsHidesCriteria(null);
		def.setBackAction(null);
		def.setPickAction(null);
		def.setBeanFieldsPanelCreator(null);
		def.setCriteriaFieldsPanelCreator(null);
		def.setDataTableCreator(null);

		B b = Factory.create(beanClass);
		IModel<B> poModel = new CompoundPropertyModel<B>(b);
		def.setBeanModel(poModel);

		def.setSaveAction(null);
		def.setUpdateAction(null);
		def.setDeleteAction(null);

		C criteria = Factory.create(criteriaClass);
		IModel<C> criteriaModel = new CompoundPropertyModel<C>(criteria);
		def.setCriteriaModel(criteriaModel);

		def.setQueryAction(new QueryAction<>(queryClass));
		return def;
	}

	/**
	 * Creates a {@link ModeAwareBeanPanelDef} instance with the given input for
	 * its properties.
	 *
	 * @param <B>
	 *            the generic type
	 * @param wicketId
	 *            the wicket id
	 * @param beanModel
	 *            the bean model
	 * @return BeanPanelDef
	 */
	public static <B extends Serializable> ModeAwareBeanPanelDef<B> createBeanPanelDef(String wicketId,
			IModel<B> beanModel) {
		ModeAwareBeanPanelDef<B> def = new ModeAwareBeanPanelDefImpl<B>();
		def.setWicketId(wicketId);
		def.setBeanModel(beanModel);
		return def;
	}

	/**
	 * Creates a {@link ModeAwareBeanPanelDef} instance with the given input for
	 * its properties.
	 *
	 * @param <B>
	 *            the generic type
	 * @param wicketId
	 *            the wicket id
	 * @param beanModel
	 *            the bean model
	 * @param disableUnauthorizedButtons
	 *            the disable unauthorized buttons
	 * @param mode
	 *            the mode
	 * @return BeanPanelDef
	 */
	public static <B extends Serializable> ModeAwareBeanPanelDef<B> createBeanPanelDef(String wicketId,
			IModel<B> beanModel, Boolean disableUnauthorizedButtons, PanelCreatorMode mode) {
		ModeAwareBeanPanelDef<B> def = createBeanPanelDef(wicketId, beanModel);
		def.setDisableUnauthorizedButtons(disableUnauthorizedButtons);
		def.setBeanFieldsPanelMode(mode);
		return def;
	}
}