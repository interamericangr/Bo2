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
package gr.interamerican.wicket.bo2.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;

/**
 * Unit Test of {@link Bo2WicketUtils}.
 */
public class TestBo2WicketUtils extends Bo2WicketTest {

	/**
	 * Test method for {@link Bo2WicketUtils#addActionAfterSaveUpdateDelete(CrudPickerPanelDef, LegacyCallbackAction)}.
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddActionAfterSaveUpdateDelete() throws Exception {
		CrudPickerPanelDef<?> def = new CrudPickerPanelDefImpl<String>();
		MockedCallback saveAction = new MockedCallback();
		MockedCallback updateAction = new MockedCallback();
		MockedCallback deleteAction = new MockedCallback();
		MockedCallback nextAction = new MockedCallback();
		def.setSaveAction(saveAction);
		def.setUpdateAction(updateAction);
		def.setDeleteAction(deleteAction);
		Bo2WicketUtils.addActionAfterSaveUpdateDelete(def, nextAction);
		assertFalse(saveAction.isExecuted() || updateAction.isExecuted() || deleteAction.isExecuted()
				|| nextAction.isExecuted());
		// save
		def.getSaveAction().process(null);
		assertTrue(saveAction.isExecuted() && nextAction.isExecuted());
		nextAction.setExecuted(false);
		// update
		def.getUpdateAction().process(null);
		assertTrue(updateAction.isExecuted() && nextAction.isExecuted());
		nextAction.setExecuted(false);
		// delete
		def.getDeleteAction().consume(null);
		assertTrue(deleteAction.isExecuted() && nextAction.isExecuted());
	}
}