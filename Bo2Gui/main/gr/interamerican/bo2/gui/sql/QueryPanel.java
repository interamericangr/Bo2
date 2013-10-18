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
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.components.BTable;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.listeners.RuntimeCommandContext;
import gr.interamerican.bo2.gui.listeners.SwingMethodBasedActionListener;
import gr.interamerican.bo2.impl.open.jdbc.JdbcStaticQuery;
import gr.interamerican.bo2.impl.open.jdbc.JdbcUtils;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentInfoUtility;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Panel that allows a user to execute a query on an available manager. <br>
 * TODO make me a {@link BPanel}
 */
public class QueryPanel 
extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Results table.
	 */
	BTable table;
	
	/**
	 * SQL query input area.
	 */
	JTextArea sqlArea;
	
	/**
	 * Available JDBC managers selection.
	 */
	JComboBox managersSelection;
	
	/**
	 * Number of rows to limit results to.
	 */
	JTextField limitField;
	/**
	 * field containing the number of results returned in the {@link ResultSet}
	 */
	JTextField numberOfResultsField;
	
	/**
	 * Should a row limit be applied?
	 */
	JCheckBox limitCheckBox;
	
	/**
	 * @return the initial text in the sql panel.
	 */
	String initText() {
		String t = StringConstants.EMPTY;
		for (String managers : Bo2DeploymentInfoUtility.get().getJdbcManagers()) {
			t += Bo2DeploymentInfoUtility.get().getInfoForManager(managers);
		}
		return t;
	}
	/**
	 * Creates a new QueryPanel object. 
	 */
	@SuppressWarnings("nls")
	public QueryPanel() {
		setPreferredSize(Sizes.PANEL);
		
		BLabel queryLabel = new BLabel("queryLabel");
		queryLabel.setValue("Query: ");
		
		BLabel limitLabel = new BLabel("limitLabel");
		limitLabel.setValue("Limit results");
		limitField = new JTextField(5);
		limitField.setText("100");
		limitCheckBox = new JCheckBox();
		limitCheckBox.setSelected(true);
		
		sqlArea = new JTextArea(5,60);
		sqlArea.setText(initText());
		JScrollPane sqlScrollPane = new JScrollPane(sqlArea);
		
		table = new BTable("results");
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(Sizes.TABLE_SIZE);
		
		JButton button = new JButton("Execute");
		button.addActionListener(new SwingMethodBasedActionListener("executeQuery", this));
		
		String[] managers = Bo2DeploymentInfoUtility.get().getJdbcManagers();
		
		managersSelection = new JComboBox(managers);
		BLabel numberOfResultsLabel = new BLabel("resultsLabel");
		numberOfResultsLabel.setValue("Total results");
		numberOfResultsField = new JTextField(15);
		numberOfResultsField.setText("-");
		numberOfResultsField.setEditable(false);

		add(queryLabel);
		add(limitLabel);
		add(limitField);
		add(limitCheckBox);
		add(sqlScrollPane);
		add(button);
		add(tableScrollPane);
		add(managersSelection);
		add(numberOfResultsField);
		add(numberOfResultsLabel);
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, queryLabel, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, tableScrollPane, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, sqlScrollPane, 5, SpringLayout.EAST, queryLabel);
		layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, sqlScrollPane);
		layout.putConstraint(SpringLayout.WEST, limitLabel, 5, SpringLayout.EAST, button);
		layout.putConstraint(SpringLayout.WEST, limitField, 5, SpringLayout.EAST, limitLabel);
		layout.putConstraint(SpringLayout.WEST, limitCheckBox, 5, SpringLayout.EAST, limitField);
		layout.putConstraint(SpringLayout.WEST, managersSelection, 5, SpringLayout.EAST, limitCheckBox);
		layout.putConstraint(SpringLayout.NORTH, numberOfResultsField, 5, SpringLayout.SOUTH, managersSelection);
		layout.putConstraint(SpringLayout.NORTH, numberOfResultsLabel, 5, SpringLayout.SOUTH, managersSelection);
		layout.putConstraint(SpringLayout.WEST, numberOfResultsLabel, 200, SpringLayout.EAST, sqlArea);
		layout.putConstraint(SpringLayout.WEST, numberOfResultsField, 5, SpringLayout.EAST, numberOfResultsLabel);
		layout.putConstraint(SpringLayout.NORTH, tableScrollPane, 25, SpringLayout.SOUTH, sqlScrollPane);		
	}
	
	
	
	/**
	 * Action that executes the query.
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void executeQuery() throws DataException, SQLException {
		table.setModel(createTableModel());
		table.repaint();
	}
	
	/**
	 * Creates the model for the table based on the query results.
	 * 
	 * @return table model.
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	TableModel createTableModel() throws DataException, SQLException {
		String sql = getSql();
		
		JdbcStaticQuery executor = new JdbcStaticQuery(sql);
		executor.setManagerName((String) managersSelection.getSelectedItem());
		RuntimeCommandContext.open(executor);
		executor.execute();
		String[] columnNames = executor.getColumnNames();		
		Object[][] contents = JdbcUtils.queryResultsAsMatrix(executor, true);		
		TableModel result = new DefaultTableModel(contents, columnNames);
		numberOfResultsField.setText(String.valueOf(contents.length));
		return result;
	}
	
	/**
	 * Gets the SQL statement to execute.
	 * 
	 * @return Returns the SQL statement to execute.
	 */
	@SuppressWarnings("nls")
	String getSql() {
		String sql = sqlArea.getText();
		sql = sql.replace(StringConstants.NEWLINE, StringConstants.SPACE);
		
		if(limitCheckBox.isSelected()) {
			String lines = Utils.notNull(limitField.getText(), "100");
			sql = sql + " fetch first " + lines + " rows only";
		}
		return sql;
	}
	
	

}
