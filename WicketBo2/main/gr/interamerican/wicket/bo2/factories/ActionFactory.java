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
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.arch.ext.CrudPerformer;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.wicket.bo2.callbacks.Bo2WicketBlock;
import gr.interamerican.wicket.bo2.callbacks.CrudAction;
import gr.interamerican.wicket.bo2.callbacks.QueryAction;
import gr.interamerican.wicket.callback.AddElementToCollectionI;
import gr.interamerican.wicket.callback.AddElementToCollectionL;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.callback.RemoveElementFromCollection;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;

import java.io.Serializable;
import java.util.Collection;

/**
 * Factory for Callback actions. 
 * 
 * This factory creates {@link Bo2WicketBlock}s.
 */
public class ActionFactory {
	
	/**
	 * Creates a CRUD action for a BeanPanel.
	 *
	 * @param definition
	 *        Panel definition.
	 * @param poClass
	 *        Class of PersistentObject.
	 * @param questionClass
	 *        Class of question that retrieves a new key for the persistent
	 *        object.
	 * @param performer
	 *        CRUD performer for the operation.
	 * @param <K>
	 *        Type of key. Type of answer for the key question.
	 * @param <P>
	 *        Type of persistent object.
	 * @param <Q>
	 *        Type of key question.
	 * 
	 * @return Returns the action.
	 */
	public static 
	<K extends Serializable & Comparable<? super K>, 
	 P extends PersistentObject<K>,
	 Q extends Question<K> & PoDependent<P>>
	 CallbackAction crudAction
	 (BeanPanelDef<P> definition, 
	  Class<P> poClass, 
	  Class<Q> questionClass, 
	  CrudPerformer performer) {
		return new CrudAction<K, P, Q>(definition, poClass, questionClass, performer);
	}
	
	
	/**
	 * Creates a query action for a SearchFlowPanel.
	 * 
	 * @param definition
	 *        SearchFlowPanel definition.
	 * @param queryClass
	 *        Class of query.
	 * @param <C>
	 *        Type of criteria.
	 * @param <B>
	 *        Type of entity searched by the search flow panel.
	 * @param <Q>
	 *        Type of query.
	 * 
	 * @return Returns the query action.
	 */
	public static 
	<C extends Serializable, 
	 B extends Serializable,
	 Q extends EntitiesQuery<B> & CriteriaDependent<C>>
	CallbackAction queryAction
	(SearchFlowPanelDef<C, B> definition, Class<Q> queryClass) {
		return new QueryAction<C, B, Q>(definition, queryClass);
	}

	
	/**
	 * Creates a {@link CallbackAction} that will add a new item
	 * to the appropriate set of an owner object.
	 * 
	 * This action is intended to be used by a CrudPickerPanel
	 * that modifies an object's Set. In the case that an object
	 * has a property that is of type Set<B>, and the model list 
	 * of the CrudPickerPanel is created by the elements of the 
	 * Set, then this CallBack action can be used in order to 
	 * add the new element in the Set. This action will synchronize
	 * the CrudPickerPanel's list with the object's set. 
	 * The new Object will be added to the document with the method
	 * <code>CollectionUtils.addNextL(set, object)</code>
	 * 
	 * 
	 * @param definition
	 *        Panel definition of the panel that uses this action. 
	 * @param owner
	 *        Object that contains the Set, that is displayed in the
	 *        CrudPickerPanel.
	 * @param collectionName
	 *        Name of the property that returns the Collection.
	 * @param <B>
	 *        Type of object in the Set.
	 * @param indexPropertyName 
	 *        Name of the index property of B.
	 * 
	 * @return Returns a CallBack action.
	 */
	public static <B extends Serializable> CallbackAction addNextLAction (
			CrudPickerPanelDef<B> definition, Object owner, 
			String collectionName, String indexPropertyName) {
		Collection<B> col = getCollection(owner, collectionName);
		B b = definition.getBeanModel().getObject();
		return new AddElementToCollectionL<B>(col, b, indexPropertyName);
	}
	
	/**
	 * Creates a {@link CallbackAction} that will add a new item
	 * to the appropriate set of an owner object.
	 * 
	 * This action is intended to be used by a CrudPickerPanel
	 * that modifies an object's Set. In the case that an object
	 * has a property that is of type Set<B>, and the model list 
	 * of the CrudPickerPanel is created by the elements of the 
	 * Set, then this CallBack action can be used in order to 
	 * add the new element in the Set. This action will synchronize
	 * the CrudPickerPanel's list with the object's set. 
	 * The new Object will be added to the document with the method
	 * <code>CollectionUtils.addNextI(set, object)</code>
	 * 
	 * 
	 * @param definition
	 *        Panel definition of the panel that uses this action. 
	 * @param owner
	 *        Object that contains the Set, that is displayed in the
	 *        CrudPickerPanel.
	 * @param collectionName
	 *        Name of the property that returns the Collection.
	 * @param <B>
	 *        Type of object in the Set.
	 * @param indexPropertyName 
	 *        Name of the index property of B.
	 * 
	 * @return Returns a CallBack action.
	 */
	public static <B extends Serializable> CallbackAction addNextIAction (
			CrudPickerPanelDef<B> definition, Object owner,
			String collectionName, String indexPropertyName) {
		Collection<B> col = getCollection(owner, collectionName);
		B b = definition.getBeanModel().getObject();
		return new AddElementToCollectionI<B>(col, b, indexPropertyName);
	}
	
	/**
	 * Creates a {@link CallbackAction} that will add a new item
	 * to the appropriate set of an owner object.
	 * 
	 * This action is intended to be used by a CrudPickerPanel
	 * that modifies an object's Set. In the case that an object
	 * has a property that is of type Set<B>, and the model list 
	 * of the CrudPickerPanel is created by the elements of the 
	 * Set, then this CallBack action can be used in order to 
	 * add the new element in the Set. This action will synchronize
	 * the CrudPickerPanel's list with the object's set. 
	 * The new Object will be added to the document with the method
	 * <code>CollectionUtils.addNextI(set, object)</code>
	 * 
	 * 
	 * @param definition
	 *        Panel definition of the panel that uses this action. 
	 * @param owner
	 *        Object that contains the Set, that is displayed in the
	 *        CrudPickerPanel.
	 * @param collectionName
	 *        Name of the property that returns the Collection.
	 * @param <B>
	 *        Type of object in the Set.
	 * 
	 * @return Returns a CallBack action.
	 */
	public static <B extends Serializable> CallbackAction removeAction 
		(CrudPickerPanelDef<B> definition, Object owner, String collectionName) {
		
		Collection<B> col = getCollection(owner, collectionName);
		B b = definition.getBeanModel().getObject();
		return new RemoveElementFromCollection<B>(col, b);
	}
	
	/**
	 * Gets a collection that is located as a property of an object.
	 * 
	 * @param owner
	 *        Owner of the collection
	 * @param collectionName
	 *        Nested property expression used to locate the collection.  
	 * @param <B>
	 *        Type of elements in the collection. 
	 * 
	 * @return Returns the collection.
	 */
	static <B> Collection<B> getCollection(Object owner, String collectionName) {
		Object c = JavaBeanUtils.getNestedProperty(owner, collectionName);
		@SuppressWarnings("unchecked") Collection<B> collection = (Collection<B>) c;
		return collection;
	}
	
	
}
