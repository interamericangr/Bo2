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
package gr.interamerican.wicket.extensions.markup.html.repeater.util;


import static gr.interamerican.wicket.markup.html.BaseTestPage.TEST_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.samples.bean.Family;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.ITestPageSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for the {@link SortableDataProvider}.
 * 
 *
 */
public class TestSortableDataProvider extends WicketTest {
	
	/**
	 * Table ID.
	 */
	private static final String TABLEID = "datatable";
	
	/**
	 * Table for the tests.
	 */
	DefaultDataTable table;
	
	/**
	 * Creates a sample {@link Bean1}.
	 * 
	 * @return Returns the bean.
	 */
	@SuppressWarnings("nls")
	private Bean1 sample() {
		Bean1 bean1 = new Bean1();
		bean1.setAmount(new BigDecimal(10));
		bean1.setDescription("This is a test"); 
		bean1.setIssueDate(new Date());
		bean1.setTestTextArea("This is a test");
		bean1.setRenewalDate(new Date());
		bean1.setPercentage(new Double(10));
		return bean1;
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor(){
		List<Family> list = new ArrayList<Family>();		
		SortableDataProvider<Family> sdp = 
			new SortableDataProvider<Family>(list, Family.class);
		assertEquals(sdp.clazz, Family.class);
		assertEquals(sdp.rows, list);
	}

	/**
	 * Tests setRows.
	 */
	@Test
	public void testSetRows(){
		List<Family> list = new ArrayList<Family>();		
		SortableDataProvider<Family> sdp = 
			new SortableDataProvider<Family>(new ArrayList<Family>(), Family.class);
		sdp.setRows(list);		
		assertEquals(sdp.rows, list);
	}

	/**
	 * Tests getRows.
	 */
	@Test
	public void testGetRows(){
		List<Family> list = new ArrayList<Family>();		
		SortableDataProvider<Family> sdp = 
			new SortableDataProvider<Family>(new ArrayList<Family>(), Family.class);
		sdp.rows = list;		
		assertEquals(sdp.getRows(), list);
	}
	
	/**
	 * Test for the creation of a table.
	 */	
	@Test
	public void testSortableDataProvider(){
		Bean1[] beans = {sample(), sample()};
		tester.startPage(pageSource(beans));	
		tester.assertComponent(subjectPath(), DefaultDataTable.class);
		Component actual = tester.getComponentFromLastRenderedPage(subjectPath());
		assertSame(table, actual);		
		Assert.assertEquals(beans.length, table.getRowCount());
	}
	
	
	
	/**
	 * Creates a page source
	 * 
	 * @param beans
	 *        Beans to put to the table.
	 * 
	 * @return Returns a page source.
	 */
	@SuppressWarnings("serial")
	ITestPageSource pageSource(final Bean1... beans) {		
		return new ITestPageSource() {
			@SuppressWarnings("nls")
			public Page getTestPage() {
				List<Bean1> rows = Arrays.asList(beans);				
				SortableDataProvider<Bean1> sdp = new SortableDataProvider<Bean1>(rows,Bean1.class);
				@SuppressWarnings("unchecked") IColumn<Bean1>[] columns = new IColumn[2];
				columns[0] = new PropertyColumn<Bean1>(new Model<String>("Amount"), "amount", "amount");
				columns[1] = new PropertyColumn<Bean1>(new Model<String>("Description"), "description", "description");
				sdp.setSort("amount", true);
				
				table = new DefaultDataTable<Bean1>(TEST_ID, columns, sdp, 10);							
				return new TestPage(table);
			}
		};
	}

}
