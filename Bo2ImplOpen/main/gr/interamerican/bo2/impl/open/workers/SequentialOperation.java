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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

/**
 * SequentialOperation executes a sequence of {@link Operation}
 * objects.
 * 
 * The sequence is defined by an array of {@link Operation} that
 * is provided in the constructor. 
 */
public class SequentialOperation 
extends AbstractOperation {
	
	/**
	 * Child operations. 
	 */
	protected Operation[] operations;
	

	/**
	 * Creates a new SequentialOperation object.
	 * 
	 * @param operations Operations of the sequence. 
	 *
	 */
	public SequentialOperation(Operation[] operations) {
		super();
		this.operations = operations;
	}
	
	/**
	 * Creates a new SequentialOperation object.
	 * 
	 */
	public SequentialOperation() {
		super();
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {	
		super.init(parent);
		for (int i = 0; i < operations.length; i++) {
			operations[i].init(parent);
		}
	}
	
	@Override
	public void open() throws DataException {		
		super.open();
		for (int i = 0; i < operations.length; i++) {
			Operation op = operations[i];
			op.open();
		}
	}
	
	@Override
	public void close() throws DataException {	
		for (int i = 0; i < operations.length; i++) {
			Operation op = operations[i]; 
			op.close();
		}
		super.close();		
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		for (int i = 0; i < operations.length; i++) {
			Operation op = operations[i]; 
			op.execute();
		}
	}
	
}
