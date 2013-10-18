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
package gr.interamerican.bo2.gui.sql;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.gui.listeners.RuntimeCommandContext;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link QueryPanel}.
 */
public class TestUpdatePanel {
	
	/**
	 * Test subject.
	 */
	UpdatePanel panel = new UpdatePanel();
	
	/**
	 * @throws DataException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecuteAndGetResult() throws DataException {
		JComboBox managersSelection = Mockito.mock(JComboBox.class);
		Mockito.when(managersSelection.getSelectedItem()).thenReturn("LOCALDB");
		panel.managersSelection = managersSelection;
		
		JTextArea sqlArea = Mockito.mock(JTextArea.class);
		Mockito.when(sqlArea.getText()).thenReturn("delete from test.users where usr_id='Non existant user id'");
		panel.sqlArea = sqlArea;
		
		RuntimeCommandContext.get().beginProcessing();
		
		String result = panel.executeAndGetResult();
		
		RuntimeCommandContext.get().endProcessing();
		
		Assert.assertTrue(result.startsWith("0"));
		
	}

}
