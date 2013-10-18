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

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
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
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Table;
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
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

/**
 * dummy empty visitor.
 */
public class EmptyVisitor 
implements StatementVisitor, SelectVisitor, 
FromItemVisitor, SelectItemVisitor, ExpressionVisitor {

	/**
	 * Select.
	 */
	protected static final String SELECT = "select"; //$NON-NLS-1$
	/**
	 * From.
	 */
	protected static final String FROM = "from"; //$NON-NLS-1$
	/**
	 * Where.
	 */
	protected static final String WHERE = "where"; //$NON-NLS-1$
	/**
	 * Group by.
	 */
	protected static final String GROUP_BY = "group by"; //$NON-NLS-1$
	/**
	 * Union.
	 */
	protected static final String UNION = "union"; //$NON-NLS-1$
	/**
	 * all.
	 */
	protected static final String ALL = "all"; //$NON-NLS-1$
	/**
	 * Having.
	 */
	protected static final String HAVING = "having"; //$NON-NLS-1$
	/**
	 * Order by.
	 */
	protected static final String ORDER_BY = "order by"; //$NON-NLS-1$
	
	@Override
	public void visit(net.sf.jsqlparser.schema.Column tableColumn) {/* empty */}

	@Override
	public void visit(Function function) {/* empty */}		

	@Override
	public void visit(AllColumns allColumns) {/* empty */}

	@Override
	public void visit(AllTableColumns allTableColumns) {/* empty */}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {/* empty */}

	@Override
	public void visit(Table tableName) {/* empty */}

	@Override
	public void visit(PlainSelect plainSelect) {/* empty */}	

	@Override
	public void visit(Union union) {/* empty */}	

	@Override
	public void visit(Select select) {/* empty */}

	@Override
	public void visit(SubSelect subSelect) { /* empty */ }

	@Override
	public void visit(SubJoin subjoin) { /* empty */ }	

	@Override
	public void visit(Delete delete) { /* empty */ }

	@Override
	public void visit(Update update) { /* empty */ }

	@Override
	public void visit(Insert insert) { /* empty */ }

	@Override
	public void visit(Replace replace) { /* empty */ }

	@Override
	public void visit(Drop drop) { /* empty */ }

	@Override
	public void visit(Truncate truncate) { /* empty */ }

	@Override
	public void visit(CreateTable createTable) { /* empty */ }

	@Override
	public void visit(NullValue nullValue) { /* empty */ }	

	@Override
	public void visit(InverseExpression inverseExpression) { /* empty */ }

	@Override
	public void visit(JdbcParameter jdbcParameter) { /* empty */ }

	@Override
	public void visit(DoubleValue doubleValue) { /* empty */ }

	@Override
	public void visit(LongValue longValue) { /* empty */ }

	@Override
	public void visit(DateValue dateValue) { /* empty */ }

	@Override
	public void visit(TimeValue timeValue) { /* empty */ }

	@Override
	public void visit(TimestampValue timestampValue) { /* empty */ }

	@Override
	public void visit(Parenthesis parenthesis) { /* empty */ }

	@Override
	public void visit(StringValue stringValue) { /* empty */ }

	@Override
	public void visit(Addition addition) { /* empty */ }

	@Override
	public void visit(Division division) { /* empty */ }

	@Override
	public void visit(Multiplication multiplication) { /* empty */ }

	@Override
	public void visit(Subtraction subtraction) { /* empty */ }

	@Override
	public void visit(AndExpression andExpression) { /* empty */ }

	@Override
	public void visit(OrExpression orExpression) { /* empty */ }

	@Override
	public void visit(Between between) { /* empty */ }

	@Override
	public void visit(EqualsTo equalsTo) { /* empty */ }

	@Override
	public void visit(GreaterThan greaterThan) { /* empty */ }

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) { /* empty */ }

	@Override
	public void visit(InExpression inExpression) { /* empty */ }

	@Override
	public void visit(IsNullExpression isNullExpression) { /* empty */ }

	@Override
	public void visit(LikeExpression likeExpression) { /* empty */ }

	@Override
	public void visit(MinorThan minorThan) { /* empty */ }

	@Override
	public void visit(MinorThanEquals minorThanEquals) { /* empty */ }

	@Override
	public void visit(NotEqualsTo notEqualsTo) { /* empty */ }

	@Override
	public void visit(CaseExpression caseExpression) { /* empty */ }

	@Override
	public void visit(WhenClause whenClause) { /* empty */ }

	@Override
	public void visit(ExistsExpression existsExpression) { /* empty */ }

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) { /* empty */ }

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) { /* empty */ }

	@Override
	public void visit(Concat concat) { /* empty */ }

	@Override
	public void visit(Matches matches) { /* empty */ }

	@Override
	public void visit(BitwiseAnd bitwiseAnd) { /* empty */ }

	@Override
	public void visit(BitwiseOr bitwiseOr) { /* empty */ }

	@Override
	public void visit(BitwiseXor bitwiseXor) { /* empty */ }

}
