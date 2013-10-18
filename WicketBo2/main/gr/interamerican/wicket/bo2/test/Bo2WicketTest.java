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
package gr.interamerican.wicket.bo2.test;

import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.protocol.http.WebApplication;


/**
 * Base class for wicket tests.
 */
public class Bo2WicketTest extends WicketTest{

	/**
	 * Creates a new Bo2WicketTest object. 
	 *
	 */
	public Bo2WicketTest() {
		this(new MockApplicationForWicketBo2());
	}
	
	/**
	 * Creates a new WicketTest object.
	 * 
	 * Protected constructor to be used by sub-classes.
	 * A sub-class should call this this constructor, from a 
	 * no arguments constructor.
	 *  
	 * @param application
	 *        Web application to test.
	 */
	protected Bo2WicketTest(WebApplication application) {
		super(application);
	}
	
	/**
	 * Casts the result of newRequestCycle() to a {@link Bo2WicketRequestCycle}.
	 * 
	 * @return Returns a new Bo2WicketRequestCycle.
	 */
	protected Bo2WicketRequestCycle newBo2RequestCycle() {
		return (Bo2WicketRequestCycle) newRequestCycle(); 
	}
	

	
	
	
	

}
