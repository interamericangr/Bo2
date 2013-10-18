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
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Dummy web application for unit tests.
 * 
 * This is not a Bo2WicketApplication.
 */
public class MockApplicationForWicketBo2 
extends WicketTester.DummyWebApplication {
	
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new Bo2WicketRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new Bo2WicketSession<Object,Object>(request);
	}
}
