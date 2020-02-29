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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link SelfDrawnDataTableCreator}.
 */
@Deprecated
public class TestSelfDrawnDataTableCreator extends WicketTest {

	/**
	 * Creates test samples.
	 *
	 * @param creatorType the creator type
	 * @return sample
	 */
	@SuppressWarnings({ "nls", "rawtypes" })
	SelfDrawnDataTableCreator<ChildBean> createSample(Class<? extends DataTableCreator> creatorType) {
		BusinessObjectDescriptor<ChildBean> objectDescriptor = new BasicBusinessObjectDescriptor<ChildBean>();
		List<BoPropertyDescriptor<?>> propertyDescriptors = new ArrayList<BoPropertyDescriptor<?>>();
		StringBoPropertyDescriptor pd1 = new StringBoPropertyDescriptor();
		pd1.setName("name");
		StringBoPropertyDescriptor pd2 = new StringBoPropertyDescriptor();
		pd1.setName("description");
		
		propertyDescriptors.add(pd1);
		propertyDescriptors.add(pd2);
		
		return new SelfDrawnDataTableCreator<ChildBean>(ChildBean.class, objectDescriptor, creatorType);
	}

	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor() {
		SelfDrawnDataTableCreator<ChildBean> creator = createSample(PropertiesBasedDataTableCreator.class);
		Assert.assertTrue(creator.creatorType==PropertiesBasedDataTableCreator.class);
		Assert.assertTrue(creator.creator() instanceof PropertiesBasedDataTableCreator);
		
		creator = createSample(PropertiesBasedPickerDataTableCreator.class);
		Assert.assertTrue(creator.creatorType==PropertiesBasedPickerDataTableCreator.class);
		Assert.assertTrue(creator.creator() instanceof PropertiesBasedPickerDataTableCreator);
		
		creator = createSample(PropertiesBasedMultipleSelectionsDataTableCreator.class);
		Assert.assertTrue(creator.creatorType==PropertiesBasedMultipleSelectionsDataTableCreator.class);
		Assert.assertTrue(creator.creator() instanceof PropertiesBasedMultipleSelectionsDataTableCreator);
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		List<ChildBean> elements = new ArrayList<ChildBean>();
		elements.add(new ChildBean());
		return createSample(PropertiesBasedDataTableCreator.class).createDataTable(wicketId, elements);
	}
	
	/**
	 * Test data table creation and rendering.
	 */
	@Test
	public void testCreateDataTable() {
		startAndTestComponent(DataTable.class, Markup.table);
	}
}