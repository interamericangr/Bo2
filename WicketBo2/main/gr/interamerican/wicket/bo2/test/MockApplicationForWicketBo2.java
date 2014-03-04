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

import gr.interamerican.wicket.bo2.protocol.http.Bo2RequestCycleListener;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;

/**
 * Dummy web application for unit tests.
 * 
 * This is not a Bo2WicketApplication.
 */
public class MockApplicationForWicketBo2 
extends WebApplication {
	
	@Override
	public Session newSession(Request request, Response response) {
		return new Bo2WicketSession<Object, Object>(request);
	}
	
	@Override
	protected void init() {
		super.init();
		getRequestCycleListeners().add(new PageRequestHandlerTracker());
		getRequestCycleListeners().add(new Bo2RequestCycleListener());
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return TestPage.class;
	}
	
}
