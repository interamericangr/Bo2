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
package gr.interamerican.wicket.creators;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * 
 */
public class TestPropertiesBasedPickerDataTableCreator extends WicketTest {

	/**
	 * selectColumnLabel
	 */
	IModel<String> selectColumnLabel = new Model<String>("label"); //$NON-NLS-1$
	/**
	 * properties
	 */
	String [] properties = new String[]{"name", "description", "birthdate"};  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
	
	/**
	 * Creators
	 */
	static List<PropertiesBasedPickerDataTableCreator<ChildBean>> creators = new ArrayList<PropertiesBasedPickerDataTableCreator<ChildBean>>();
	
	/**
	 * Test constructor
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor(){
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties,selectColumnLabel));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, properties, selectColumnLabel));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, properties, selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, null, selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, selectColumnLabel, null));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, properties, selectColumnLabel, "name", null));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class,properties, properties, selectColumnLabel, "name", new Formatter<?>[]{null, null, null}));
	}
	
	/**
	 * Test constructors and createDataTable
	 */
	@Test
	public void testCreateDataTable() {
		List<ChildBean> elements = new ArrayList<ChildBean>();
		elements.add(new ChildBean());
		
		for(PropertiesBasedDataTableCreator<ChildBean> creator : creators) {
			creator.createDataTable(TestPage.TEST_ID, elements);
		}
	}

	/**
	 * Test CreateColumns
	 * 
	 * � ������� ��� colums ��� �������������� ����� ��� propeties ���� �� Bean
	 * ��� �������.
	 */
	@Test
	public void testCreateColumns() {
		for(PropertiesBasedPickerDataTableCreator<ChildBean> creator : creators) {
			assertEquals(4, creator.createColumns().size());
		}
	}
}
