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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;

/**
 * {@link AbstractBo2RuntimeCmd} that will wrap any thrown exception 
 * to a RuntimeException.
 */
public abstract class AbstractUncheckedBo2RuntimeCmd 
extends AbstractBo2RuntimeCmd {
	
	@Override
	public void execute(){
		try {
			super.execute();
		} catch (UnexpectedException e) {
			throw new RuntimeException(e.getCause());
		} catch (DataException e) {
			throw new RuntimeException(e);
		} catch (LogicException e) {
			throw new RuntimeException(e);
		} 
	}
	
	

}
