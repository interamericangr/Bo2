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
package gr.interamerican.bo2.test.scenarios;

import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

/**
 * This operation always throws a {@link LogicException}.
 */
public class FailingOperation
extends AbstractOperation {

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.workers.AbstractOperation#execute()
	 */
	@Override
	public void execute() throws LogicException {
		throw new LogicException();
	}
}
