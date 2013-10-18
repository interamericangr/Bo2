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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;

/**
 * A dummy worker is a worker that has a worker interface,
 * however it doesn't really need any resources.
 */
public class StandAloneWorker implements Worker {
	
	/**
	 * Provider;
	 */
	private Provider provider;
	
	public void init(Provider parent) {
		this.provider = parent;	
	}	
	
	public void open() {
		/* empty */	
	}	
	
	public void close() {
		/* empty */	
	}	
	
	public Provider getProvider() {		
		return provider;
	}	
	

}
