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
package gr.interamerican.wicket.bo2.factories.meta;

import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntryOwner;
import gr.interamerican.wicket.markup.html.TestPage;

/**
 * A factory for creating TestCachedEntryOwnerBoPDComponent objects.
 */
public class TestCachedEntryOwnerBoPDComponentFactory
extends BaseClassForTestingComponentFactory {

	/**
	 * Test draw main first.
	 */
	@Test
	public void testDrawMain_First() {
		startAndTestComponent(SelfDrawnDropDownChoiceForEntryOwner.class);
	}

	/**
	 * Test draw main second.
	 */
	@Test
	public void testDrawMain_Second() {
		CachedEntryOwnerBoPDComponentFactory cachedEntryOwnerBoPDFactory = new CachedEntryOwnerBoPDComponentFactory();
		Model<Long> model = new Model<Long>(bean1.getId());
		Component component = cachedEntryOwnerBoPDFactory.drawMain(TestPage.TEST_ID, model,
				createMultipleChoiceDescriptor());
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForEntryOwner.class);
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		CachedEntryOwnerBoPDComponentFactory cachedEntryOwnerBoPDFactory = new CachedEntryOwnerBoPDComponentFactory();
		return cachedEntryOwnerBoPDFactory.drawMain(createMultipleChoiceDescriptor(), wicketId);
	}

	/**
	 * Create MultipleChoiceCachedEntryOwnerBoPropertyDescriptor.
	 *
	 * @return MultipleChoiceCachedEntryOwnerBoPropertyDescriptor
	 */
	public CachedEntryOwnerBoPropertyDescriptor<?, ?> createMultipleChoiceDescriptor() {
		Long type = 1000L;
		Long subType = 1L;
		EnumElement value_1 = new EnumElement(type, ObjectType.OBJECT1);
		value_1.setCode(1L);
		value_1.setSubTypeId(subType);
		cache().put(value_1);

		EnumElement value_2 = new EnumElement(type, ObjectType.OBJECT2);
		value_2.setCode(2L);
		value_2.setSubTypeId(subType);
		cache().put(value_2);

		EnumElement value_3 = new EnumElement(type, ObjectType.OBJECT3);
		value_3.setCode(3L);
		value_3.setSubTypeId(subType);
		cache().put(value_3);

		CachedEntryOwnerBoPropertyDescriptor<?, ?> cd = new CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long>(type,
				subType, TEST_CACHE_NAME, new LongParser(), ObjectFormatter.<Long> getInstance());
		cd.setName("id"); //$NON-NLS-1$
		return cd;
	}

}
