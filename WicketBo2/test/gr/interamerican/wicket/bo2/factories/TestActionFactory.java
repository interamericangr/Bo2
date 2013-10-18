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
package gr.interamerican.wicket.bo2.factories;

import static gr.interamerican.wicket.bo2.factories.ActionFactory.addNextIAction;
import static gr.interamerican.wicket.bo2.factories.ActionFactory.addNextLAction;
import static gr.interamerican.wicket.bo2.factories.ActionFactory.queryAction;
import static gr.interamerican.wicket.bo2.factories.ActionFactory.removeAction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.owners.CollectionOwner;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDefImpl;
import gr.interamerican.wicket.samples.queries.BeanWithOneFieldQuery;

import java.util.Collection;
import java.util.HashSet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link ActionFactory}.
 */
public class TestActionFactory {
	
	/**
	 * Test for addNextIAction().
	 */
	@Test
	public void testAddNextIAction() {
		String indexProperty = "third"; //$NON-NLS-1$
		String collectionProperty = "collection"; //$NON-NLS-1$
		BeanWithOrderedFields bean = new BeanWithOrderedFields();
		CrudPickerPanelDef<BeanWithOrderedFields> def = 
			new CrudPickerPanelDefImpl<BeanWithOrderedFields>();
		IModel<BeanWithOrderedFields> model = new CompoundPropertyModel<BeanWithOrderedFields>(bean);
		def.setBeanModel(model);
		
		CollectionOwner<BeanWithOrderedFields> owner = new CollectionOwner<BeanWithOrderedFields>();
		Collection<BeanWithOrderedFields> collection = new HashSet<BeanWithOrderedFields>();
		owner.setCollection(collection);
		
		CallbackAction action = addNextIAction(def, owner, collectionProperty, indexProperty);		
		assertNotNull(action);
		
		AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);		
		action.callBack(target);
		
		assertTrue(owner.getCollection().contains(bean));
		assertEquals(Integer.valueOf(1), bean.getThird());
	}
	
	/**
	 * Test for addNextIAction().
	 */
	@Test
	public void testAddNextLAction() {
		String indexProperty = "fourth"; //$NON-NLS-1$
		String collectionProperty = "collection"; //$NON-NLS-1$
		BeanWithOrderedFields bean = new BeanWithOrderedFields();
		CrudPickerPanelDef<BeanWithOrderedFields> def = 
			new CrudPickerPanelDefImpl<BeanWithOrderedFields>();
		IModel<BeanWithOrderedFields> model = new CompoundPropertyModel<BeanWithOrderedFields>(bean);
		def.setBeanModel(model);
		
		CollectionOwner<BeanWithOrderedFields> owner = new CollectionOwner<BeanWithOrderedFields>();
		Collection<BeanWithOrderedFields> collection = new HashSet<BeanWithOrderedFields>();
		owner.setCollection(collection);
		
		CallbackAction action = addNextLAction(def, owner, collectionProperty, indexProperty);		
		assertNotNull(action);
		
		AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);		
		action.callBack(target);
		
		assertTrue(owner.getCollection().contains(bean));
		assertEquals(Long.valueOf(1L), bean.getFourth());
	}
	
	/**
	 * Test for addNextIAction().
	 */
	@Test
	public void testRemoveAction() {		
		String collectionProperty = "collection"; //$NON-NLS-1$
		BeanWithOrderedFields bean = new BeanWithOrderedFields();
		CrudPickerPanelDef<BeanWithOrderedFields> def = 
			new CrudPickerPanelDefImpl<BeanWithOrderedFields>();
		IModel<BeanWithOrderedFields> model = new CompoundPropertyModel<BeanWithOrderedFields>(bean);
		def.setBeanModel(model);
		
		CollectionOwner<BeanWithOrderedFields> owner = new CollectionOwner<BeanWithOrderedFields>();
		Collection<BeanWithOrderedFields> collection = new HashSet<BeanWithOrderedFields>();
		owner.setCollection(collection);
		owner.getCollection().add(bean);
		
		CallbackAction action = removeAction(def, owner, collectionProperty);		
		assertNotNull(action);
		
		AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);		
		action.callBack(target);
		
		assertFalse(owner.getCollection().contains(bean));
	}
	
	/**
	 * Test for queryAction().
	 */
	@Test
	public void testQueryAction() {		
		SearchFlowPanelDef<BeanWith1Field, BeanWith1Field> def = 
			new SearchFlowPanelDefImpl<BeanWith1Field, BeanWith1Field>();
		CallbackAction action = queryAction(def, BeanWithOneFieldQuery.class);				
		assertNotNull(action);		
	}

}
