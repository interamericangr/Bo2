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
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * This visitor is used in expressions in order to find tables used. If the join has static value in equality, static
 * value boolean is set.
 * 
 */
@SuppressWarnings("unchecked")
public class TableNamesFinderInExpression implements ExpressionVisitor, ItemsListVisitor {

	/**
	 * set of table names.
	 */
	protected Set<String> tableNames = new HashSet<String>();
	/**
	 * flag for static value.
	 */
	private boolean staticValue = false;

	/**
	 * @return table names
	 */
	public Set<String> getTableNames() {
		return tableNames;
	}

	/**
	 * @return static value flag.
	 */
	public boolean getStaticValue() {
		return staticValue;
	}

	/**
	 * generic handler for binary expressions
	 * 
	 * @param b
	 */
	private void handleBinaryExpression(BinaryExpression b) {
		b.getLeftExpression().accept(this);
		b.getRightExpression().accept(this);
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
	public void visit(JdbcParameter jdbcParameter) {
		// do nothing
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		staticValue = true;
	}

	@Override
	public void visit(LongValue longValue) {
		staticValue = true;
	}

	@Override
	public void visit(DateValue dateValue) {
		staticValue = true;
	}

	@Override
	public void visit(TimeValue timeValue) {
		staticValue = true;
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		staticValue = true;
	}

	@Override
	public void visit(Parenthesis parenthesis) {
		parenthesis.getExpression().accept(this);
	}

	@Override
	public void visit(StringValue stringValue) {
		staticValue = true;
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
		this.handleBinaryExpression(likeExpression);
	}

	@Override
	public void visit(MinorThan minorThan) {
		this.handleBinaryExpression(minorThan);
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		this.handleBinaryExpression(minorThanEquals);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		this.handleBinaryExpression(notEqualsTo);
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
		// do nothing
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		// do nothing
	}

	@Override
	public void visit(Concat concat) {
		this.handleBinaryExpression(concat);
	}

	@Override
	public void visit(Matches matches) {
		this.handleBinaryExpression(matches);
	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		this.handleBinaryExpression(bitwiseAnd);
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		this.handleBinaryExpression(bitwiseOr);
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		this.handleBinaryExpression(bitwiseXor);
	}

	@Override
	public void visit(ExpressionList expressionList) {
		for (Expression e : (List<Expression>) expressionList.getExpressions()) {
			e.accept(this);
		}
	}
}
