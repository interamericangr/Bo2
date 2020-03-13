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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.bo2.utils.DownloadFileFromListBean;

/**
 * Unit test for {@link List2CsvAction}.
 */
@Deprecated
public class TestList2CsvAction extends Bo2WicketTest {
	
	/**
	 * Unit test for create(clazz).
	 */
	@SuppressWarnings("nls")
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
		
		DownloadFileFromListBean client = Factory.create(DownloadFileFromListBean.class);
		client.setList(list);
		client.setPropertiesToExport(properties);
		client.setColumnLabels(labels);
		client.setDownloadedFileName(filename);
				
		List2CsvAction action = new List2CsvAction(client);
		
		action.run();
		IRequestHandler target = rc.getRequestHandlerScheduledAfterCurrent();
		Assert.assertTrue(target instanceof ResourceStreamRequestHandler);
		ResourceStreamRequestHandler rt = (ResourceStreamRequestHandler) target;
		Assert.assertEquals(rt.getFileName(), filename);				
		Bo2WicketRequestCycle.endRequest(rc);		
	}
}