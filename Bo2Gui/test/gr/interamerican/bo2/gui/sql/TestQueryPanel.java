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

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.gui.listeners.RuntimeCommandContext;

/**
 * Test {@link QueryPanel}.
 */
public class TestQueryPanel {
	
	/**
	 * Test subject.
	 */
	
	/**
	 * Test create table model.
	 *
	 * @throws DataException the data exception
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateTableModel() throws DataException, SQLException {
		QueryPanel panel = new QueryPanel();
		panel.managersSelection = new JComboBox<>(new String[] {"LOCALDB"});
		panel.sqlArea = new JTextArea("select * from test.users");
		panel.limitCheckBox = new JCheckBox();
		
		RuntimeCommandContext.get().beginProcessing();
		TableModel tModel = panel.createTableModel();
		RuntimeCommandContext.get().endProcessing();
		assertNotNull(tModel);
	}
}