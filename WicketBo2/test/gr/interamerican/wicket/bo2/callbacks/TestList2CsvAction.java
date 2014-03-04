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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.wicket.bo2.callbacks.clients.List2CsvActionClient;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link List2CsvAction}.
 */
public class TestList2CsvAction {
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester =
		new WicketTester(new MockApplicationForWicketBo2());
	
	/**
	 * Unit test for create(clazz).
	 */
	@SuppressWarnings({ "nls", "rawtypes", "unchecked" })
	@Test
	public void testAction(){
		RequestCycle rc = RequestCycle.get();
		Bo2WicketRequestCycle.beginRequest(rc);
		
		BeanWith3Fields[] beans = {
			new BeanWith3Fields("one", 1, 1.0),
			new BeanWith3Fields("two", 2, 2.0)
		};
		List<BeanWith3Fields> list = Arrays.asList(beans);
		String[] properties = {"field1", "field2"};
		String[] labels = {"Field 1", "Field 2"};
		String filename = "TestCsvFile.csv";
		
		List2CsvActionClient client = Mockito.mock(List2CsvActionClient.class);
		Mockito.when(client.getList()).thenReturn((List)list);
		Mockito.when(client.getPropertiesToExport()).thenReturn(properties);
		Mockito.when(client.getColumnLabels()).thenReturn(labels);
		Mockito.when(client.getFileName()).thenReturn(filename);
				
		List2CsvAction action = new List2CsvAction(client);
		
		action.execute();
		IRequestHandler target = rc.getRequestHandlerScheduledAfterCurrent();
		Assert.assertTrue(target instanceof ResourceStreamRequestHandler);
		ResourceStreamRequestHandler rt = (ResourceStreamRequestHandler) target;
		Assert.assertEquals(rt.getFileName(), client.getFileName());				
		Bo2WicketRequestCycle.endRequest(rc);		
	}
	
	

}
