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
package gr.interamerican.bo2.jsqlparser.visitors;

import gr.interamerican.bo2.jsqlparser.exceptions.NotSupportedByVisitorException;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.Union;

/**
 * Finds the columns of a ResultSet given a select SQL query.
 * Each column is packaged in a {@link Column}.
 * 
 * TODO: see subselects and subjoins?
 */
@SuppressWarnings("unchecked")
public class ColumnsFinder 
extends EmptyVisitor {
	
	/**
	 * Columns list.
	 */
	private List<Column> columns = new ArrayList<Column>();
	
	/**
	 * Stores the table aliases.
	 * The mapping is: alias  <--> schema.tableName
	 */
	private Map<String, String> tableAliases = new HashMap<String, String>();
	
	/**
	 * Stores the select aliases.
	 * The mapping is: select expression <--> alias
	 */
	private Map<String, String> selectAliases = new HashMap<String, String>();
	
	@Override
	public void visit(net.sf.jsqlparser.schema.Column tableColumn) {
		Table table = tableColumn.getTable();
		Column column = new Column();
		column.setColumnNo(columns.size());
		column.setName(tableColumn.getColumnName());
		String tableWholeName = tableAliases.get(table.getName());
		if(tableWholeName == null) {
			column.setTbName(table.getName());
			column.setTbCreator(table.getSchemaName());
		} else {
			String[] split = TokenUtils.splitTrim(tableWholeName, StringConstants.DOT, false);
			column.setTbCreator(split[0]);
			column.setTbName(split[1]);
		}
		String alias = selectAliases.get(tableColumn.toString());
		column.setAlias(alias);
		
		columns.add(column);
	}
	
	@Override
	@SuppressWarnings("nls")
	public void visit(Function function) {
		Column column = new Column();
		column.setColumnNo(columns.size());
		String name = selectAliases.get(function.toString());
		if(name == null) {
			String msg = "Found function: " + function.toString() + " with no alias.";
			throw new NotSupportedByVisitorException(msg);
		}
		column.setName(name);
		column.setAlias(name);
		
		columns.add(column);
	}
	
	@Override
	@SuppressWarnings("nls")
	public void visit(AllColumns allColumns) {
		if("*".equals(allColumns.toString())) {
			String msg = "[Select * from...] is not supported. Define the result set columns explicitly.";
			throw new NotSupportedByVisitorException(msg);
		}
		allColumns.accept(this);
	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		Table table = allTableColumns.getTable();
		table.accept(this);
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		Expression expression = selectExpressionItem.getExpression();
		if(selectExpressionItem.getAlias() != null) {
			String key = selectExpressionItem.getExpression().toString();
			selectAliases.put(key, selectExpressionItem.getAlias());
		}
		expression.accept(this);
	}
	


	@Override
	public void visit(Table tableName) { 
		tableAliases.put(tableName.getAlias(), tableName.getWholeTableName());
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		List<Join> joins = plainSelect.getJoins();
		if(joins!=null) {
			for(Join join : joins) {
				FromItem joinItem = join.getRightItem();
				joinItem.accept(this);
			}
		}
		FromItem fromItem = plainSelect.getFromItem();
		fromItem.accept(this);
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		for(SelectItem selectItem : selectItems) {
			selectItem.accept(this);
		}
	}
	
	@Override
	public void visit(Union union) {
		List<PlainSelect> plainSelects = union.getPlainSelects();
		for(PlainSelect ps : plainSelects) {
			ps.accept(this);
		}
	}
	
	@Override
	public void visit(Select select) {
		SelectBody selectBody = select.getSelectBody();
		selectBody.accept(this);
	}

	/**
	 * Gets the columns.
	 *
	 * @return Returns the columns
	 */
	public List<Column> getColumns() {
		return columns;
	}

}
