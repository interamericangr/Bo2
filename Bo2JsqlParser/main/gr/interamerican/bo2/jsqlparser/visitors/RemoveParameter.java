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

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import static gr.interamerican.bo2.utils.StringConstants.LEFT_PARENTHESIS;
import static gr.interamerican.bo2.utils.StringConstants.QUOTE;
import static gr.interamerican.bo2.utils.StringConstants.RIGHT_PARENTHESIS;
import static gr.interamerican.bo2.utils.StringConstants.SPACE;
import gr.interamerican.bo2.jsqlparser.exceptions.NotSupportedByVisitorException;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
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
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

/**
 * Rebuilds the an SQL statement by omitting the specified named 
 * parameter from its where clause(s).
 * 
 * Queries with BETWEEN are not supported at the moment. Note that
 * they can easily be written equivalently as two comparisons.
 */
@SuppressWarnings({"unchecked", "nls"})
public class RemoveParameter 
extends EmptyVisitor {
	
	/**
	 * Stores all where clauses found in the query.
	 */
	private List<String> allWhereClauses;
	
	/**
	 * Where clause builder.
	 */
	private StringBuilder sb;
	
	/**
	 * Parameter name to remove.
	 */
	private String paramToRemove; 
	
	/**
	 * Result.
	 */
	private String result;
	
	/**
	 * Creates a new RemoveParameter object. 
	 * @param paramToRemove 
	 */
	public RemoveParameter(String paramToRemove) {
		super();
		this.paramToRemove = QUOTE+SqlParser.PARM_CHAR+paramToRemove+QUOTE;
	}
	
	/*
	 * These are the elementary expressions found in a where clause.
	 */
	@Override
	public void visit(EqualsTo equalsTo) {
		handleElementaryExpression(equalsTo);
	}
	@Override
	public void visit(GreaterThan greaterThan) {
		handleElementaryExpression(greaterThan);
	}
	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		handleElementaryExpression(greaterThanEquals);
	}
	@Override
	public void visit(MinorThan minorThan) {
		handleElementaryExpression(minorThan);
	}
	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		handleElementaryExpression(minorThanEquals);
	}
	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		handleElementaryExpression(notEqualsTo);
	}
	@Override
	public void visit(LikeExpression likeExpression) {
		handleElementaryExpression(likeExpression);
	}
	
	@Override
	public void visit(Between between) {
		String msg = "BETWEEN is not supported. Try writing it as" +
				" two separate comparison expressions instead.";
		throw new NotSupportedByVisitorException(msg);
	}
	
	@Override
	public void visit(ExistsExpression existsExpression) {
		sb.append(SPACE+existsExpression.getStringExpression()+SPACE+LEFT_PARENTHESIS);
		if(existsExpression.getRightExpression() instanceof SubSelect) {
			sb.append(SPACE+processSubSelect((SubSelect) existsExpression.getRightExpression())+SPACE+RIGHT_PARENTHESIS);
		} else {
			sb.append(SPACE+existsExpression.getRightExpression().toString()+SPACE+RIGHT_PARENTHESIS);
		}
		
	}
	
	@Override
	public void visit(IsNullExpression isNullExpression) { 
		sb.append(SPACE+isNullExpression.toString());
	}
	
	@Override
	public void visit(InExpression inExpression) {
		ItemsList itemsList = inExpression.getItemsList();
		if(itemsList instanceof SubSelect) {
			sb.append(SPACE + inExpression.getLeftExpression().toString());
			if(inExpression.isNot()) {
				sb.append(SPACE + "NOT IN " + LEFT_PARENTHESIS);
			} else {
				sb.append(SPACE + "IN " + LEFT_PARENTHESIS);
			}
			sb.append(SPACE+processSubSelect((SubSelect) itemsList)+SPACE+RIGHT_PARENTHESIS);
		}
		if(itemsList instanceof ExpressionList) {
			ExpressionList expressionList = (ExpressionList) itemsList;
			Object[] items = expressionList.getExpressions().toArray();
			List<Object> toRemain = new ArrayList<Object>();
			for(Object item : items) {
				if(!item.toString().contains(paramToRemove)) {
					toRemain.add(item);
				}
			}
			String s = StringUtils.array2String(toRemain.toArray(), COMMA);
			if(!StringUtils.isNullOrBlank(s)) {
				sb.append(SPACE + inExpression.getLeftExpression().toString());
				if(inExpression.isNot()) {
					sb.append(SPACE + "NOT IN");
				} else {
					sb.append(SPACE + "IN");
				}
				sb.append(SPACE + LEFT_PARENTHESIS + SPACE + s + SPACE + RIGHT_PARENTHESIS);
			}
		}

	}
	
	/*
	 * End of BinaryExpressions.
	 */
	
	/*
	 * AND in the where statement. This could be inside a parenthesis or not.
	 */
	@Override
	public void visit(AndExpression andExpression) {
		Expression left = andExpression.getLeftExpression();
		left.accept(this);
		Expression right = andExpression.getRightExpression();
		if (isSimple(right)) {
			if(!matchesNamedParameter(right.toString())) {
				sb.append(SPACE + andExpression.getStringExpression() + SPACE + right.toString());
			}
		} else {
			if(!containsParamFirst(right)) {
				sb.append(SPACE + andExpression.getStringExpression());
			}
			right.accept(this);
		}
	}
	
	/*
	 * OR in the where statement. This could be inside a parenthesis or not.
	 */
	@Override
	public void visit(OrExpression orExpression) {
		Expression left = orExpression.getLeftExpression();
		left.accept(this);
		Expression right = orExpression.getRightExpression();
		if (isSimple(right)) {
			if(!matchesNamedParameter(right.toString())) {
				sb.append(SPACE + orExpression.getStringExpression() + SPACE + right.toString());
			}
		} else {
			if(!containsParamFirst(right)) {
				sb.append(SPACE + orExpression.getStringExpression());
			}
			right.accept(this);
		}
	}
	
	/*
	 * A parenthesis in the where statement.
	 */
	@Override
	public void visit(Parenthesis parenthesis) {
		Expression expr = parenthesis.getExpression();
		if(isSimple(expr)) {
			handleElementaryExpression(expr);
		} else {
			sb.append(SPACE + LEFT_PARENTHESIS);
			parenthesis.getExpression().accept(this);
			sb.append(SPACE + RIGHT_PARENTHESIS);
		}
	}
	
	/*
	 * This is where the WHERE statement is found.
	 */
	@Override
	public void visit(PlainSelect plainSelect) {
		Expression where = plainSelect.getWhere();
		if (where != null) {
			where.accept(this);
		}
	}
	
	/*
	 * A union exists, multiple where clauses to handle.
	 */
	@Override
	public void visit(Union union) {
		List<PlainSelect> plainSelects = union.getPlainSelects();
		allWhereClauses = new ArrayList<String>();
		for(PlainSelect ps : plainSelects) {
			/*
			 * If there is a union, the where clauses, after removals,
			 * will be stored in the List.
			 */
			sb = new StringBuilder();
			ps.accept(this);
			allWhereClauses.add(repair(sb.toString()));
		}
	}
	
	@Override
	public void visit(Select select) {
		/*
		 * If there is no union, this will store the single
		 * where clause, after removals.
		 */
		sb = new StringBuilder();
		SelectBody selectBody = select.getSelectBody();
		selectBody.accept(this);
		
		if(allWhereClauses==null) {
			allWhereClauses = new ArrayList<String>();
			allWhereClauses.add(repair(sb.toString()));
		}
		RebuildQueryWithNewWhereClause rqapr = new RebuildQueryWithNewWhereClause(allWhereClauses);
		select.accept(rqapr);
		result = rqapr.getResult();
	}
	
	/**
	 * Processes a subSelect removing any occurrences of the
	 * <code>paramToRemove</code>
	 * @param subSelect
	 * @return Returns the string representation of the corrected subSelect.
	 */
	private String processSubSelect(SubSelect subSelect) {
		Select select = new Select();
		select.setSelectBody(subSelect.getSelectBody());
		/*
		 * Convert the paramToRemove back to original form.
		 * e.g. ':name' --> name
		 */
		String param = paramToRemove.replace(QUOTE, EMPTY).replace(SqlParser.PARM_CHAR.toString(), EMPTY);
		RemoveParameter rp = new RemoveParameter(param);
		select.accept(rp);
		return rp.getResult();
	}
	
	/**
	 * Repairs the produced where clause using fixWhereClause() as many times as necessary.
	 * 
	 * @param where
	 *        The built where clause after parameter removal.
	 *        
	 * @return Returns the repaired where clause.
	 */
	private String repair(String where) {
		String current = where;
		boolean finished = false;
		while(!finished) {
			String old = current;
			current = fixWhereClause(current);
			if(old.equals(current)) {
				finished = true;
			}
		}
		return current;
	}
	
	/**
	 * The removal algorithm has some weaknesses. 
	 * It is possible, that after where or after an opening parenthesis the keywords 
	 * AND or OR will exist. This happens if the parameter that is removed is the first 
	 * of the where clause or the first in a parenthesis.
	 * It is also possible, that an empty parenthesis '()' will exist. 
	 * 
	 * This method corrects these occurrences.
	 * 
	 * @param where
	 *        The built where clause after parameter removal.
	 *        
	 * @return Returns the where clause after fixing the above bugs.
	 */
	private String fixWhereClause(String where) {
		final String AND = "and";
		final String OR = "or";
		List<String> tokens = Arrays.asList(TokenUtils.splitTrim(where, SPACE));
		tokens = removeGarbageFromEnd(tokens);
		StringBuilder buff = new StringBuilder();
		boolean ignoreNext = true;
		for(String token : tokens){
			if(ignoreNext) {
				boolean ignore = token.equalsIgnoreCase(AND) || token.equalsIgnoreCase(OR);
				boolean delete = token.equals(")");
				if(!ignore) {
					buff.append(token+SPACE);
				}
				if(delete) {
					/*
					 * two parentheses and two spaces, hence -4.
					 */
					buff.setLength(buff.length()-4);
				}
			} else {
				buff.append(token+SPACE);
			}
			if(token.equalsIgnoreCase("(") || token.equalsIgnoreCase(AND) || token.equalsIgnoreCase(OR)) {
				ignoreNext = true;
			} else {
				ignoreNext = false;
			}
		}
		return buff.toString();
	}
	
	/**
	 * Remove AND or OR from the end of the where clause. This may
	 * happen when removing an entire parenthesis as last element.
	 * e.g. 
	 * where a = :a and (b=:b and c=:b)
	 * if b is removed the where clause is left like this:
	 * where a = :a and
	 * 
	 * @param tokens
	 * 
	 * @return Return the fixed tokens.
	 */
	private List<String> removeGarbageFromEnd(List<String> tokens) {
		boolean problem = false;
		if(tokens.lastIndexOf("AND")==tokens.size()-1) {
			problem = true;
		}
		if(tokens.lastIndexOf("OR")==tokens.size()-1) {
			problem = true;
		}
		if(problem) {
			List<String> newList = new ArrayList<String>();
			for(int i=0; i<tokens.size()-1; i++) {
				newList.add(tokens.get(i));
			}
			return newList;
		}else {
			return tokens;
		}
		
	}
	
	/**
	 * Returns true, if the named parameter is contained in the first
	 * expression of the supplied expression. This information is required
	 * when we must decide whether to include or not the operator that is
	 * to be included before the next element. If this element is to be 
	 * removed, the operator must not be added.
	 * 
	 * @param expression
	 * 
	 * @return Returns true, if the leftmost expression of the supplied
	 *         expression contains the parameter that is to be removed.
	 */
	private boolean containsParamFirst(Expression expression) {
		if(expression instanceof BinaryExpression) {
			BinaryExpression be = (BinaryExpression) expression;
			if(!isSimple(be.getLeftExpression())) {
				return containsParamFirst(be.getLeftExpression());
			} else {
				return be.getLeftExpression().toString().contains(paramToRemove);
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether an expression is a simple expression.
	 * 
	 * @param e
	 * @return Returns true, if the expression is simple.
	 */
	private boolean isSimple(Expression e) {
		return (e instanceof LikeExpression)
			|| (e instanceof NotEqualsTo)
			|| (e instanceof MinorThanEquals)
			|| (e instanceof MinorThan)
			|| (e instanceof GreaterThanEquals)
			|| (e instanceof GreaterThan)
			|| (e instanceof EqualsTo);
	}
	
	/**
	 * Handles an elementary expression. If this expression does not include
	 * the named parameter that is to be removed, add it to the where clause
	 * being built.
	 * 
	 * @param e
	 */
	private void handleElementaryExpression(Expression e) {
		String expr = e.toString();
		if(!matchesNamedParameter(expr)) {
			sb.append(SPACE + expr);
		}
	}
	
	/**
	 * Checks whether a string contains the named parameter that is to
	 * be removed.
	 * 
	 * @param candidate
	 *        The string to check against.
	 *        
	 * @return True if the candidate string contains the named parameter.
	 */
	private boolean matchesNamedParameter(String candidate) {
		return candidate.contains(paramToRemove);
	}
	
	/**
	 * Gets the result.
	 *
	 * @return Returns the result
	 */
	public String getResult() {
		return result;
	}

}
