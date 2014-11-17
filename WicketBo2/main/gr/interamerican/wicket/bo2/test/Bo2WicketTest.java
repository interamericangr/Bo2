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

import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.junit.Assert;


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
	 * Tests the creation of a component that requires resources (e.g. executes a Worker) 
	 * on its constructor and that a Page can be started with it.
	 * To use this, override {@link #initializeComponent()} and call this method from a unit test.
	 * 
	 * This is hacky, but there is no other way to test such components. 
	 */
	protected void testComponentCreationInTestPage() {
		RequestCycle cycle = RequestCycle.get();
		Bo2WicketRequestCycle.beginRequest(cycle);
		
		Page testPage = null;
		try {
			testPage = getTestPage();
			Bo2WicketRequestCycle.provider().getTransactionManager().commit();
		} catch (Exception e) {
			try {
				Bo2WicketRequestCycle.provider().getTransactionManager().rollback();
			} catch (CouldNotRollbackException cnre) {
				//do nothing
			}
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			Bo2WicketRequestCycle.endRequest(cycle);
		}
		
		tester.startPage(testPage);
	}

}
