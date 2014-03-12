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
package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntry;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.bean.BeanWith2TranslatableEntries;
import gr.interamerican.wicket.samples.bean.TranslatableEntryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link SelfDrawnPanel}.
 */
@SuppressWarnings("nls")
public class TestSelfDrawnPanel_AffectedDdcs extends Bo2WicketTest {
	
	/**
	 * Model of SelfDrawnPanel.
	 */
	private CompoundPropertyModel<BeanWith2TranslatableEntries> model;
	
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testCreation() {
		tester.startPage(getTestPage());
		
		tester.assertComponent(path("field1"), SelfDrawnDropDownChoiceForEntry.class);
		tester.assertComponent(path("field2"), SelfDrawnDropDownChoiceForEntry.class);
		
		SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl> field1Ddc = 
			(SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl>) tester.getComponentFromLastRenderedPage(path("field1"));
		SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl> field2Ddc = 
				(SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl>) tester.getComponentFromLastRenderedPage(path("field2"));
		
		Assert.assertNull(model.getObject().getField1());
		Assert.assertNull(model.getObject().getField2());
		Assert.assertNull(field1Ddc.getModelObject());
		Assert.assertNull(field2Ddc.getModelObject());
		
		model.getObject().setField1(new TranslatableEntryImpl(1L, 1L, 1L, ""));
		Assert.assertTrue(model.getObject().getField1() == field1Ddc.getModelObject());
		
		commonAssertions_noError();
	}
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testOnChangeBehavior() {
		tester.startPage(getTestPage());
		
		tester.assertComponent(path("field1"), SelfDrawnDropDownChoiceForEntry.class);
		tester.assertComponent(path("field2"), SelfDrawnDropDownChoiceForEntry.class);
		
		SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl> field1Ddc = 
			(SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl>) tester.getComponentFromLastRenderedPage(path("field1"));
		Assert.assertTrue(field1Ddc.getChoices().size()==2);
		
		/*
		 * dependent value code is null. This list is therefore empty.
		 */
		SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl> field2Ddc = 
			(SelfDrawnDropDownChoiceForEntry<Long, TranslatableEntryImpl>) tester.getComponentFromLastRenderedPage(path("field2"));
		Assert.assertTrue(field2Ddc.getChoices().isEmpty()); 
		
		
		FormTester formTester = tester.newFormTester(formPath());
		formTester.select(TestPage.TEST_ID + ":field1", 1); //$NON-NLS-2$
		tester.executeAjaxEvent(field1Ddc, "onchange");
		
		Assert.assertTrue(field2Ddc.getChoices().size()==2); 
		commonAssertions_noError();
	}
	
	
	@Override
	protected Component initializeComponent() {
		model = new CompoundPropertyModel<BeanWith2TranslatableEntries>(new BeanWith2TranslatableEntries(null, null));
		return new SelfDrawnPanel<BeanWith2TranslatableEntries>(TestPage.TEST_ID, model, createBod());
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for BeanWith2Selectables.
	 */
	BusinessObjectDescriptor<BeanWith2TranslatableEntries> createBod() {
		BusinessObjectDescriptor<BeanWith2TranslatableEntries> bod = new BasicBusinessObjectDescriptor<BeanWith2TranslatableEntries>();
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(getField1Decriptor());
		list.add(getField2Decriptor());
		bod.setPropertyDescriptors(list);
		return bod;
	}
	
	/**
	 * @return Returns the CachedEntryBoPropertyDescriptor for field1 of BeanWith2Selectables.
	 */
	CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long> getField1Decriptor(){
		CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long> sbpd = 
		new CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long>(1L, 1L, mockCache(), null, null);
		sbpd.setClassName(BeanWith2TranslatableEntries.class.getName());
		sbpd.setHasDefault(false);
		sbpd.setName("field1");
		sbpd.setPackageName(BeanWith2TranslatableEntries.class.getPackage().getName());
		sbpd.setNullAllowed(true);
		sbpd.setIndex(1);
		sbpd.setAffected("field2");
		
		sbpd = Mockito.spy(sbpd);
		Mockito.doReturn(field1Values()).when(sbpd).getValues();
		
		return sbpd;
    }
	
	/**
	 * @return Returns the CachedEntryBoPropertyDescriptor for field2 of BeanWith2Selectables.
	 */
	CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long> getField2Decriptor(){
		CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long> ibpd = 
		new CachedEntryBoPropertyDescriptor<TranslatableEntryImpl, Long>(1L, 1L, mockCache(), null, null);
		ibpd.setClassName(BeanWith2TranslatableEntries.class.getName());
		ibpd.setHasDefault(false);
		ibpd.setName("field2");
		ibpd.setPackageName(BeanWith2TranslatableEntries.class.getPackage().getName());
		ibpd.setNullAllowed(false);
		ibpd.setIndex(2);
		
		ibpd = Mockito.spy(ibpd);
		
		Mockito.doReturn(new HashSet<TranslatableEntryImpl>()).when(ibpd).getValues();
		
        return ibpd;
    }
	
	/**
	 * @return Mocked cache.
	 */
	@SuppressWarnings("unchecked")
	String mockCache() {
		String cacheName = "mockCache";
		if(CacheRegistry.getRegisteredCache(cacheName)!=null) {
			return cacheName;
		}
		Cache<Long> cache = Mockito.mock(Cache.class);
		Mockito.when(cache.get(Mockito.anyLong(), Mockito.anyLong())).thenReturn(new TranslatableEntryImpl(1L, 1L, 1L, ""));
		CacheRegistry.registerCache(cacheName, cache, Long.class);
		Mockito.doReturn(field2Values()).when(cache).getSubCache(1L, 2L); //selection of field1 on #testOnChangeBehavior()
		return cacheName;
	}
	
	/**
	 * @return All field1 values
	 */
	Set<TranslatableEntryImpl> field1Values() {
		Set<TranslatableEntryImpl> values = new HashSet<TranslatableEntryImpl>();
		values.add(new TranslatableEntryImpl(1L, 1L, 1L, "field1:1.1.1"));
		values.add(new TranslatableEntryImpl(1L, 1L, 2L, "field1:1.1.2"));
		return values;
	}
	
	/**
	 * @return 2 field2 values dependent on field1 value
	 */
	Set<TranslatableEntryImpl> field2Values() {
		Set<TranslatableEntryImpl> values = new HashSet<TranslatableEntryImpl>();
		values.add(new TranslatableEntryImpl(2L, 1L, 1L, "field2:2.1.1"));
		values.add(new TranslatableEntryImpl(2L, 1L, 2L, "field2:2.1.2"));
		return values;
	}
	
}
