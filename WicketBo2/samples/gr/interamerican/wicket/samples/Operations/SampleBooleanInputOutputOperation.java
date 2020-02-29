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
package gr.interamerican.wicket.samples.Operations;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.bo2.utils.attributes.InputOutput;
import gr.interamerican.bo2.utils.attributes.Output;

/**
 * Dummy {@link Operation} that has a boolean {@link Input}/{@link Output}.
 */
public class SampleBooleanInputOutputOperation extends AbstractOperation implements Operation,InputOutput<Boolean, Boolean>{
	
	/**
	 * Operation Input.
	 */
	Boolean input;
	
	/**
	 * Operation Input.
	 */
	Boolean output = false;

	@Override
	public Boolean getOutput() {
		return output;
	}

	@Override
	public void setInput(Boolean input) {
		this.input = input;
	}

	@Override
	public void execute() throws LogicException, DataException {
		output = true;
	}

}
