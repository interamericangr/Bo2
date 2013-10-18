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

import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

/**
 * this visitor looks for static values inside a table in join statement.
 * 
 */
@SuppressWarnings("unchecked")
public class JoinRightItemStaticLocator implements FromItemVisitor, SelectVisitor, SelectItemVisitor {

	/**
	 * flag for static values.
	 */
	private boolean staticValue = false;

	/**
	 * @return static value.
	 */
	public boolean getStaticValue() {
		return staticValue;
	}
	@Override
	public void visit(Table tableName) {
		// do nothing
	}

	@Override
	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(this);
	}

	@Override
	public void visit(SubJoin subjoin) {
		// do nothing
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		for (SelectItem i : (List<SelectItem>) plainSelect.getSelectItems()) {
			i.accept(this);
		}
		TableNamesFinderInExpression finder = new TableNamesFinderInExpression();
		if (plainSelect.getWhere() != null) {
			plainSelect.getWhere().accept(finder);
		}
		staticValue = staticValue || finder.getStaticValue();
	}

	@Override
	public void visit(Union union) {
		for (PlainSelect s : (List<PlainSelect>) union.getPlainSelects()) {
			s.accept(this);
		}

	}

	@Override
	public void visit(AllColumns allColumns) {
		// do nothing
	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		// do nothing
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		TableNamesFinderInExpression finder = new TableNamesFinderInExpression();
		selectExpressionItem.getExpression().accept(finder);
		staticValue = staticValue || finder.getStaticValue();
	}
}
