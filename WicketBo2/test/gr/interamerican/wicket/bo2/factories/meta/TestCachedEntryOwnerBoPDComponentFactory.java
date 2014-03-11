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

import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectTypeBean;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntryOwner;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * 
 */
public class TestCachedEntryOwnerBoPDComponentFactory extends BaseClassForTestingComponentFactory
{	
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	/**
	 * CODE
	 */
	private static final Long CODE = 1L;
	/**
	 * PARSER
	 */
	private static final Parser<Long> PARSER = new LongParser();
	/**
	 * FORMATTER
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private ObjectTypeBean objectTypeBean = new ObjectTypeBean();
	
	/**
	 * 
	 */
	private CachedEntryOwnerBoPDComponentFactory cachedEntryOwnerBoPDFactory = 
		new CachedEntryOwnerBoPDComponentFactory();
	
	/**
	 * 
	 */
	private CachedEntryOwnerBoPropertyDescriptor<?,?> multipleChoiceDesc = createMultipleChoiceDescriptor();
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = cachedEntryOwnerBoPDFactory.drawMain(multipleChoiceDesc, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForEntryOwner.class); 
	}

	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		Model<Long> model = new Model<Long>(bean1.getId());
		Component component = cachedEntryOwnerBoPDFactory.drawMain(TestPage.TEST_ID ,model, multipleChoiceDesc);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForEntryOwner.class); 
	}
	
	/**
	 * Create MultipleChoiceCachedEntryOwnerBoPropertyDescriptor
	 * @return MultipleChoiceCachedEntryOwnerBoPropertyDescriptor
	 * 
	 */
	public CachedEntryOwnerBoPropertyDescriptor<?,?> createMultipleChoiceDescriptor(){

		 EnumElement value_1 = new EnumElement(TYPE, ObjectType.OBJECT1);
		 value_1.setCode(CODE);
		 value_1.setSubTypeId(SUBTYPE);
		 cache().put(value_1);
		 
		 EnumElement value_2 = new EnumElement(TYPE, ObjectType.OBJECT2);
		 value_2.setCode(CODE);
		 value_2.setSubTypeId(SUBTYPE);
		 cache().put(value_2);
		 
		 EnumElement value_3 = new EnumElement(TYPE, ObjectType.OBJECT3);
		 value_3.setCode(CODE);
		 value_3.setSubTypeId(SUBTYPE);
		 cache().put(value_3);
		
		 CachedEntryOwnerBoPropertyDescriptor<?,?> cd = new CachedEntryOwnerBoPropertyDescriptor<ObjectType,Long>(
        		 1000L, 1L,TEST_CACHE_NAME, PARSER, FORMATTER);
		 cd.setName("id"); //$NON-NLS-1$
		 return cd;
	}

}
