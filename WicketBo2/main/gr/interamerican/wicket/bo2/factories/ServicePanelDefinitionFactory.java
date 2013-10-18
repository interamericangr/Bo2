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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.enums.CrudOperations;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDefImpl;

import java.io.Serializable;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SearchFlowPanelDef} objects.
 */
public class ServicePanelDefinitionFactory {
	
	/**
	 * Creates a CrudPickerPanelDefinition for a CrudPickerPanel.
	 * 
	 * The definition created by this method does not have a back action or a pick action.
	 * It also does not have any PanelCreator or DataTableCreator. These properties 
	 * of the {@link SearchFlowPanelDef} must be filled manually. The same goes for the 
	 * wicket id and the list of the table elements to be shown in the panel.
	 * 
	 * @param poClass 
	 *        Class of PersistentObject.
	 * @param questionClass 
	 *        Type of next key question. 
	 * 
	 * @param <K>
	 *        Type of persistent object key.
	 * @param <PO> 
	 *        Type of persistent object.
	 * @param <KQ>
	 *        Type of next key question. 
	 * 
	 * @return Returns a {@link CrudPickerPanelDef}.
	 */
	public static <K extends Serializable & Comparable<? super K>,
			       PO extends PersistentObject<K>,
				   KQ extends Question<K> & PoDependent<PO> > 
	CrudPickerPanelDef<PO> crudPickerPanelDef(Class<PO> poClass, Class<KQ> questionClass) {
		final CrudPickerPanelDef<PO> def = new CrudPickerPanelDefImpl<PO>();
		def.setBackAction(null);
		def.setItemSelectedAction(null);
		def.setBeanFieldsPanelCreator(null);
		def.setDataTableCreator(null);
		def.setList(null);
		
		PO po = Factory.create(poClass);
		IModel<PO> poModel = new CompoundPropertyModel<PO>(po);
		def.setBeanModel(poModel);
		
		CallbackAction saveAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.STORE);
		def.setSaveAction(saveAction);
		CallbackAction updateAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.UPDATE);
		def.setUpdateAction(updateAction);
		CallbackAction deleteAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.DELETE); 
		def.setDeleteAction(deleteAction);
		
		def.setBackAction(null);
		def.setItemSelectedAction(null);
		
		return def;
	}
	
	/**
	 * Creates a SearchFlowPanelDefinition for a SearcFlowPanel that supports CRUD.
	 * 
	 * The definition created by this method does not have a back action or a pick action.
	 * It also does not have any PanelCreator or DataTableCreator. These properties 
	 * of the {@link SearchFlowPanelDef} must be filled manually. The same goes for the 
	 * wicket id.
	 * 
	 * @param criteriaClass
	 *        Class of criteria bean. 
	 * @param poClass
	 *        Class of PersistentObject.
	 * @param queryClass
	 *        Class of query.
	 * @param questionClass
	 *        Class of next key question.
	 *        
	 * @param <K> 
	 *        Type of persistent object key.
	 * @param <C>
	 *        Type of criteria.
	 * @param <PO>
	 *        Type of persistent object.
	 * @param <KQ>
	 *        Type of next key question. 
	 * @param <Q>
	 *        Type of query.
	 *        
	 * @return Returns a {@link SearchFlowPanelDef}.
	 */
	public static <K extends Serializable & Comparable<? super K>, 
	               C extends Serializable, 
	               PO extends PersistentObject<K>,
	               KQ extends Question<K> & PoDependent<PO>,
	               Q extends EntitiesQuery<PO> & CriteriaDependent<C>>
	SearchFlowPanelDef<C, PO> sfpDefCrud(Class<C> criteriaClass, Class<PO> poClass, 
			                             Class<Q> queryClass, Class<KQ> questionClass) {
		
		final SearchFlowPanelDef<C, PO> def = new SearchFlowPanelDefImpl<C, PO>();
		def.setAutoPickSingleResult(null);
		def.setResultsHidesCriteria(null);
		def.setBackAction(null);
		def.setPickAction(null);
		def.setBeanFieldsPanelCreator(null);
		def.setCriteriaFieldsPanelCreator(null);
		def.setDataTableCreator(null);		
		
		PO po = Factory.create(poClass);
		IModel<PO> poModel = new CompoundPropertyModel<PO>(po);
		def.setBeanModel(poModel);		
		
		CallbackAction saveAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.STORE);
		def.setSaveAction(saveAction);
		CallbackAction updateAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.UPDATE);
		def.setUpdateAction(updateAction);
		CallbackAction deleteAction = ActionFactory.crudAction(def, poClass, questionClass, CrudOperations.DELETE); 
		def.setDeleteAction(deleteAction);
		
		C criteria = Factory.create(criteriaClass);
		IModel<C> criteriaModel = new CompoundPropertyModel<C>(criteria);
		def.setCriteriaModel(criteriaModel);
		
		CallbackAction queryAction = ActionFactory.queryAction(def, queryClass);
		def.setQueryAction(queryAction);
		return def;
		
	}
	
	/**
	 * Creates a SearchFlowPanelDefinition for a SearcFlowPanel that does not support CRUD.
	 * 
	 * The definition created by this method does not have a back action or a pick action.
	 * It also does not have any PanelCreator or DataTableCreator. These properties of the
	 * {@link SearchFlowPanelDef} must be filled manually. The same goes for the wicket id.
	 * 
	 * @param criteriaClass
	 *        Class of criteria bean. 
	 * @param beanClass
	 *        Class of bean.
	 * @param queryClass
	 *        Class of query. 
	 * @param <C>
	 *        Type of criteria.
	 * @param <B>
	 *        Type of bean.
	 * @param <Q>
	 *        Type of query.
	 *        
	 * @return Returns a {@link SearchFlowPanelDef}.
	 */
	public static <C extends Serializable, B extends Serializable, 
	Q extends EntitiesQuery<B> & CriteriaDependent<C>> 
	SearchFlowPanelDef<C, B> sfpDefQuery(Class<C> criteriaClass, Class<B> beanClass, Class<Q> queryClass) {
		
		final SearchFlowPanelDef<C, B> def = new SearchFlowPanelDefImpl<C, B>();
		def.setAutoPickSingleResult(null);
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
		
		CallbackAction queryAction = ActionFactory.queryAction(def, queryClass); 	
		def.setQueryAction(queryAction);
		return def;
	}
	
	/**
	 * Creates a {@link ModeAwareBeanPanelDef} instance with the given input
	 * for its properties.
	 * 
	 * @param <B>
	 * @param wicketId
	 * @param beanModel
	 * @return BeanPanelDef
	 */
	public static <B extends Serializable> ModeAwareBeanPanelDef<B>
	createBeanPanelDef(String wicketId, IModel<B> beanModel) {
		ModeAwareBeanPanelDef<B> def = new ModeAwareBeanPanelDefImpl<B>();
		def.setWicketId(wicketId);
		def.setBeanModel(beanModel);
		return def;
	}
	
	/**
	 * Creates a {@link ModeAwareBeanPanelDef} instance with the given input
	 * for its properties.
	 * 
	 * @param <B>
	 * @param wicketId
	 * @param beanModel
	 * @param disableUnauthorizedButtons
	 * @param mode 
	 * @return BeanPanelDef
	 */
	public static <B extends Serializable> ModeAwareBeanPanelDef<B>	createBeanPanelDef(
	String wicketId, IModel<B> beanModel, Boolean disableUnauthorizedButtons, PanelCreatorMode mode) {
		ModeAwareBeanPanelDef<B> def = createBeanPanelDef(wicketId, beanModel);
		def.setDisableUnauthorizedButtons(disableUnauthorizedButtons);
		def.setBeanFieldsPanelMode(mode);
		return def;
	}
}
