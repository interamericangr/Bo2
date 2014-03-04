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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.enums.CrudOperations;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.CrudPerformer;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanel;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * CallbackAction factory for CRUD actions of a {@link SearchFlowPanel}. 
 * 
 * @param <K> 
 *        Type of key.
 * @param <P> 
 *        Type of object. 
 * @param <Q> 
 *        Type of next key {@link Question}.
 * 
 */
public class CrudAction
<K extends Serializable & Comparable<? super K>, 
 P extends PersistentObject<K>,
 Q extends Question<K> & PoDependent<P>> 
extends Bo2WicketBlock {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * PO class.
	 */
	Class<P> poClass;
	
	/**
	 * Crud performer.
	 */
	CrudPerformer performer;
	
	/**
	 * Panel definition.
	 */
	BeanPanelDef<P> definition;
	
	/**
	 * Next key Question class.
	 */
	Class<Q> questionClass;
	
	
	/**
	 * Creates a new CrudBlock object.
	 * 
	 * @param definition 
	 *        Panel definition.
	 * @param poClass 
	 *        PersistentObject class.
	 * @param questionClass 
	 *        Next key question type.
	 * @param performer 
	 *        CRUD operation performer.
	 */
	public CrudAction(
			BeanPanelDef<P> definition, 
			Class<P> poClass, 
			Class<Q> questionClass, 
			CrudPerformer performer) {
		super();
		this.poClass = poClass;
		this.performer = performer;
		this.definition = definition;
		this.questionClass = questionClass;
	}
	
	
	
	@Override
	public void work() 
	throws InitializationException, DataException, LogicException {
		AjaxRequestTarget target = getHandlerParameter(AjaxRequestTarget.class);
		IModel<P> model = null;
		
		target.add(definition.getServicePanel());
		model =definition.getBeanModel();
	
		P p = model.getObject();
		if(performer == CrudOperations.STORE) {
			Q keyQuestion = Bo2WicketRequestCycle.open(questionClass);
			keyQuestion.setPo(p);
			keyQuestion.ask();
			K key = keyQuestion.getAnswer();
			p.setKey(key);
		} 
		PersistenceWorker<P> pw = Bo2WicketRequestCycle.openPw(poClass);
		P newP = performer.perform(p, pw);
		model.setObject(newP);
	}

	
	
	 
}
