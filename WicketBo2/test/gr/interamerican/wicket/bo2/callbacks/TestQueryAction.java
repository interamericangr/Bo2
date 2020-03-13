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

import java.util.List;

import org.apache.wicket.request.cycle.RequestCycle;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.samples.queries.BeanWithOneFieldQuery;

/**
 * Unit test for {@link QueryAction}.
 */
public class TestQueryAction extends Bo2WicketTest {

	/**
	 * Unit test for the action.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testAction() throws DataException {
		RequestCycle rc = RequestCycle.get();
		Bo2WicketRequestCycle.beginRequest(rc);
		BeanWith1Field criterion = new BeanWith1Field(2L);
		QueryAction<BeanWith1Field, BeanWith1Field, BeanWithOneFieldQuery> action = new QueryAction<BeanWith1Field, BeanWith1Field, BeanWithOneFieldQuery>(BeanWithOneFieldQuery.class);
		List<BeanWith1Field> results = action.search(criterion);
		Assert.assertEquals(1, results.size());
		Bo2WicketRequestCycle.endRequest(rc);
	}
}