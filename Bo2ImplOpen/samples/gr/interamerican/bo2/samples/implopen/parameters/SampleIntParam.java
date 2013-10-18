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
package gr.interamerican.bo2.samples.implopen.parameters;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.properties.Parameter;
import gr.interamerican.bo2.impl.open.workers.StandAloneWorker;

/**
 * sample integer parameter
 */
public class SampleIntParam
extends StandAloneWorker
implements Parameter<Integer> {		
	/**
	 * Parameter value.
	 */
	public static final Integer VALUE = 77;
	public Integer getValue() throws DataException {			
		return VALUE;
	}
}
