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

import java.io.Serializable;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.callback.ProcessAction;

/**
 * Action that will just store a {@link PersistentObject} with the use of it's
 * {@link PersistenceWorker} and a {@link Question} to determine the id of the
 * new entity.
 * 
 * @param <K>
 *            Type of key
 * @param <P>
 *            Type of {@link PersistentObject}
 * @param <Q>
 *            Type of next key {@link Question}
 */
public class SaveWithQuestionAction
<K extends Serializable & Comparable<? super K>, 
P extends PersistentObject<K>, 
Q extends Question<K> & PoDependent<P>>
implements ProcessAction<P> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * PO class.
	 */
	Class<P> poClass;

	/**
	 * Next key Question class.
	 */
	Class<Q> questionClass;

	/**
	 * Public Constructor
	 * 
	 * @param poClass
	 *            PersistentObject class.
	 * @param questionClass
	 *            Next key question type.
	 */
	public SaveWithQuestionAction(Class<P> poClass, Class<Q> questionClass) {
		this.poClass = poClass;
		this.questionClass = questionClass;
	}

	@Override
	public P process(P bean) throws InitializationException, DataException, LogicException {
		Q keyQuestion = Bo2WicketRequestCycle.open(questionClass);
		keyQuestion.setPo(bean);
		keyQuestion.ask();
		bean.setKey(keyQuestion.getAnswer());
		PersistenceWorker<P> pw = Bo2WicketRequestCycle.openPw(poClass);
		return pw.store(bean);
	}
}