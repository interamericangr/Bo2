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
package gr.interamerican.bo2.utils.adapters.cmd;

import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * SingleSubjectOperation adapts a {@link VoidOperation} to a
 * {@link SimpleCommand} by executing the VoidOperation to a
 * predefined subject.
 * 
 * @param <T>
 *        Type of subject. 
 */
public class SingleSubjectOperation<T> 
implements SimpleCommand {
	
	/**
	 * Operation.
	 */
	VoidOperation<T> operation;
	
	/**
	 * Subject on which the operation is executed.
	 */
	T subject;
	
	/**
	 * Creates a new SingleSubjectOperation object. 
	 *
	 * @param operation the operation
	 * @param subject the subject
	 */
	@SuppressWarnings("unchecked")
	public SingleSubjectOperation(VoidOperation<? extends T> operation, T subject) {
		super();
		this.operation = (VoidOperation<T>) operation;
		this.subject = subject;
	}

	@Override
	public void execute() {		
		operation.execute(subject);
	}

	/**
	 * Gets the operation.
	 *
	 * @return Returns the operation
	 */
	public VoidOperation<T> getVoidOperation() {
		return operation;
	}

	/**
	 * Gets the subject.
	 *
	 * @return Returns the subject
	 */
	public T getSubject() {
		return subject;
	}
	
	

}
