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
import gr.interamerican.bo2.gui.components.BLabel;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.listeners.RuntimeCommandContext;
import gr.interamerican.bo2.gui.listeners.SwingMethodBasedActionListener;
import gr.interamerican.bo2.impl.open.jdbc.JdbcStaticCommand;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentInfoUtility;
import gr.interamerican.bo2.utils.StringConstants;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 * Panel that allows a user to execute a statement on an available manager. 
 */
public class UpdatePanel 
extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Displays message from statement execution.
	 */
	JTextArea resultArea;
	
	/**
	 * SQL query input area.
	 */
	JTextArea sqlArea;
	
	/**
	 * Available JDBC managers selection.
	 */
	JComboBox managersSelection;
	
	/**
	 * Creates a new QueryPanel object. 
	 */
	@SuppressWarnings("nls")
	public UpdatePanel() {
		setPreferredSize(Sizes.PANEL);
		
		BLabel queryLabel = new BLabel("statementLabel");
		queryLabel.setValue("Statement: ");
		
		sqlArea = new JTextArea(5,60);
		JScrollPane sqlScrollPane = new JScrollPane(sqlArea);
		
		resultArea = new JTextArea(2,60);
		
		JButton button = new JButton("Execute");
		button.addActionListener(new SwingMethodBasedActionListener("executeQuery", this));
		
		String[] managers = Bo2DeploymentInfoUtility.get().getJdbcManagers();
		
		managersSelection = new JComboBox(managers);
		
		add(queryLabel);
		add(resultArea);
		add(sqlScrollPane);
		add(button);
		add(managersSelection);
		
		//layout 
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, queryLabel, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, resultArea, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, sqlScrollPane, 5, SpringLayout.EAST, queryLabel);
		layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, sqlScrollPane);
		layout.putConstraint(SpringLayout.WEST, managersSelection, 5, SpringLayout.EAST, button);
		layout.putConstraint(SpringLayout.NORTH, resultArea, 25, SpringLayout.SOUTH, sqlScrollPane);
	}
	
	/**
	 * Action that executes the statement.
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void executeQuery() throws DataException, SQLException {
		resultArea.setText(executeAndGetResult());
		resultArea.repaint();
	}
	
	/**
	 * Executes the statement and gets message for modified rows.
	 * 
	 * @return message for modified rows.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings("nls")
	String executeAndGetResult() throws DataException {
		String sql = getSql();
		
		JdbcStaticCommand executor = new JdbcStaticCommand(sql);
		executor.setManagerName((String) managersSelection.getSelectedItem());
		RuntimeCommandContext.open(executor);
		executor.execute();
		
		return String.valueOf(executor.getRowsModified()) + " rows modified.";
	}
	
	/**
	 * Gets the SQL statement to execute.
	 * 
	 * @return Returns the SQL statement to execute.
	 */	
	String getSql() {
		String sql = sqlArea.getText();
		sql = sql.replace(StringConstants.NEWLINE, StringConstants.SPACE);
		return sql;
	}
	
	

}
