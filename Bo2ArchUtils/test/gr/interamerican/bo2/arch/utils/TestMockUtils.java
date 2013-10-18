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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MockUtils}
 */
public class TestMockUtils {
	
	/**
	 * Unit test for mockEntitiesQuery()
	 * @throws DataAccessException
	 */
	@Test
	public void testMockEntitiesQuery() throws DataAccessException {
		List<String> list = new ArrayList<String>();
		list.add("a"); //$NON-NLS-1$
		list.add("b"); //$NON-NLS-1$
		
		@SuppressWarnings("unchecked")
		EntitiesQuery<String> mock = MockUtils.mockEntitiesQuery(EntitiesQuery.class, list);
		List<String> mockList = new ArrayList<String>();
		while(mock.next()) {
			mockList.add(mock.getEntity());
		}
		
		Assert.assertEquals(list.size(), mockList.size());
		Assert.assertEquals(list.get(0), mockList.get(0));
		Assert.assertEquals(list.get(1), mockList.get(1));
	}

}
