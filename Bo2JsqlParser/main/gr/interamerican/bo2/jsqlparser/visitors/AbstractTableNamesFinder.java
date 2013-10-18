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

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

/**
 * abstract names finder. General idea for this visitor is put all table name in the tableNames set.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractTableNamesFinder implements SelectVisitor, SelectItemVisitor, ExpressionVisitor,
ItemsListVisitor, StatementVisitor {

	@Override
	abstract public void visit(PlainSelect plainSelect);

	/**
	 * 
	 */
	protected Set<String> tableNames = new HashSet<String>();
	/**
	 * 
	 */
	private boolean allColumns = false;

	/**
	 * method to handle binary expressions.
	 * 
	 * @param b
	 */
	private void handleBinaryExpression(BinaryExpression b) {
		b.getLeftExpression().accept(this);
		b.getRightExpression().accept(this);
	}

	/**
	 * @return return if the sql is running for all columns
	 */
	public boolean getAllColumns() {
		return allColumns;
	}

	@Override
	public void visit(Union union) {
		for (PlainSelect ps : (List<PlainSelect>) union.getPlainSelects()) {
			ps.accept(this);
		}
	}

	/**
	 * @return the tables names.
	 */
	public Set<String> getTableNames() {
		if (allColumns) {
			tableNames = new HashSet<String>();
		}
		return tableNames;
	}

	@Override
	public void visit(AllColumns allColumns) {
		this.allColumns = true;
	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		tableNames.add(allTableColumns.getTable().getName());
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(this);
	}

	@Override
	public void visit(NullValue nullValue) {
		// do nothing
	}

	@Override
	public void visit(Function function) {
		ExpressionList list = function.getParameters();
		for (Expression l : (List<Expression>) list.getExpressions()) {
			l.accept(this);
		}
	}

	@Override
	public void visit(InverseExpression inverseExpression) {
		inverseExpression.getExpression().accept(this);
	}

	@Override
	public void visit(Addition addition) {
		handleBinaryExpression(addition);
	}

	@Override
	public void visit(Division division) {
		handleBinaryExpression(division);
	}

	@Override
	public void visit(Multiplication multiplication) {
		handleBinaryExpression(multiplication);
	}

	@Override
	public void visit(Subtraction subtraction) {
		handleBinaryExpression(subtraction);
	}

	@Override
	public void visit(AndExpression andExpression) {
		handleBinaryExpression(andExpression);
	}

	@Override
	public void visit(OrExpression orExpression) {
		handleBinaryExpression(orExpression);
	}

	@Override
	public void visit(Between between) {
		between.getLeftExpression().accept(this);
		between.getBetweenExpressionStart().accept(this);
		between.getBetweenExpressionEnd().accept(this);
	}

	@Override
	public void visit(InExpression inExpression) {
		inExpression.getLeftExpression().accept(this);
		ItemsList list = inExpression.getItemsList();
		list.accept(this);
	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		isNullExpression.getLeftExpression().accept(this);
	}

	@Override
	public void visit(LikeExpression likeExpression) {
		handleBinaryExpression(likeExpression);
	}

	@Override
	public void visit(Column tableColumn) {
		tableNames.add(tableColumn.getTable().getName());
	}

	@Override
	public void visit(SubSelect subSelect) {
		// do nothing
	}

	@Override
	public void visit(CaseExpression caseExpression) {
		if (caseExpression.getElseExpression() != null) {
			caseExpression.getElseExpression().accept(this);
		}
		if (caseExpression.getSwitchExpression() != null) {
			caseExpression.getSwitchExpression().accept(this);
		}
		if (caseExpression.getWhenClauses() != null) {
			for (WhenClause l : (List<WhenClause>) caseExpression.getWhenClauses()) {
				l.accept(this);
			}
		}
	}

	@Override
	public void visit(WhenClause whenClause) {
		whenClause.getThenExpression().accept(this);
		whenClause.getWhenExpression().accept(this);
	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		existsExpression.getRightExpression().accept(this);
	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {
		allComparisonExpression.GetSubSelect().accept((FromItemVisitor) this);
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		anyComparisonExpression.GetSubSelect().accept((FromItemVisitor) this);
	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		handleBinaryExpression(bitwiseAnd);
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		handleBinaryExpression(bitwiseOr);
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		handleBinaryExpression(bitwiseXor);
	}

	@Override
	public void visit(ExpressionList expressionList) {
		for (Expression e : (List<Expression>) expressionList.getExpressions()) {
			e.accept(this);
		}
	}

	@Override
	public void visit(Select select) {
		select.getSelectBody().accept(this);
		if (!CollectionUtils.isNullOrEmpty(select.getWithItemsList())) {
			throw new RuntimeException("WITH not yet supported"); //$NON-NLS-1$
		}
	}

	@Override
	public void visit(Delete delete) {
		throw new RuntimeException("DELETE not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Update update) {
		throw new RuntimeException("Update not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Insert insert) {
		throw new RuntimeException("Insert not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Replace replace) {
		throw new RuntimeException("Replace not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Drop drop) {
		throw new RuntimeException("Drop not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Truncate truncate) {
		throw new RuntimeException("Truncate not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(CreateTable createTable) {
		throw new RuntimeException("CreateTable not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(MinorThan minorThan) {
		handleBinaryExpression(minorThan);
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		handleBinaryExpression(minorThanEquals);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		handleBinaryExpression(notEqualsTo);
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		handleBinaryExpression(equalsTo);
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		handleBinaryExpression(greaterThan);
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		handleBinaryExpression(greaterThanEquals);
	}

	@Override
	public void visit(Parenthesis parenthesis) {
		parenthesis.getExpression().accept(this);
	}

	@Override
	public void visit(Concat concat) {
		handleBinaryExpression(concat);
	}

	@Override
	public void visit(Matches matches) {
		handleBinaryExpression(matches);
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		// do nothing
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		// do nothing
	}

	@Override
	public void visit(LongValue longValue) {
		// do nothing
	}

	@Override
	public void visit(DateValue dateValue) {
		// do nothing
	}

	@Override
	public void visit(TimeValue timeValue) {
		// do nothing
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		// do nothing
	}

	@Override
	public void visit(StringValue stringValue) {
		// do nothing
	}
}
