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

import static org.junit.Assert.assertTrue;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.gui.listeners.RuntimeCommandContext;

/**
 * Test {@link UpdatePanel}.
 */
public class TestUpdatePanel {

	/**
	 * Test execute and get result.
	 *
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecuteAndGetResult() throws DataException {
		UpdatePanel panel = new UpdatePanel();
		panel.managersSelection = new JComboBox<>(new String[] {"LOCALDB"});
		panel.sqlArea = new JTextArea("delete from test.users where usr_id='Non existant user id'");
		
		RuntimeCommandContext.get().beginProcessing();
		String result = panel.executeAndGetResult();
		RuntimeCommandContext.get().endProcessing();
		
		assertTrue(result.startsWith("0"));
	}
}