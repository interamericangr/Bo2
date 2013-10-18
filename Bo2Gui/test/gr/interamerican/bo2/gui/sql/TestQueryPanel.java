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

import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link QueryPanel}.
 */
public class TestQueryPanel {
	
	/**
	 * Test subject.
	 */
	QueryPanel panel = new QueryPanel();
	
	/**
	 * @throws SQLException 
	 * @throws DataException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateTableModel() throws DataException, SQLException {
		JComboBox managersSelection = Mockito.mock(JComboBox.class);
		Mockito.when(managersSelection.getSelectedItem()).thenReturn("LOCALDB");
		panel.managersSelection = managersSelection;
		
		JTextArea sqlArea = Mockito.mock(JTextArea.class);
		Mockito.when(sqlArea.getText()).thenReturn("select * from test.users");
		panel.sqlArea = sqlArea;
		
		JCheckBox limitCheckBox = Mockito.mock(JCheckBox.class);
		Mockito.when(limitCheckBox.isSelected()).thenReturn(false);
		panel.limitCheckBox = limitCheckBox;
		
		RuntimeCommandContext.get().beginProcessing();
		
		TableModel tModel = panel.createTableModel();
		
		RuntimeCommandContext.get().endProcessing();
		
		Assert.assertNotNull(tModel);
		
	}

}
