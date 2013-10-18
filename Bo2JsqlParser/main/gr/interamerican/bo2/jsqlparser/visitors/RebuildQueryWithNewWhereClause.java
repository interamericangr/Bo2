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
import static gr.interamerican.bo2.utils.StringConstants.SPACE;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.Union;

/**
 * Rebuilds a query (SELECT SQL statement) given one or more (in case of a UNION)
 * where clauses that are to replace the original where clauses.
 */
public class RebuildQueryWithNewWhereClause 
extends EmptyVisitor {
	
	/**
	 * Ordered list of where clause replacements.
	 */
	private List<String> newWhereClauses;
	
	/**
	 * The current where clause replacement.
	 */
	private String currentWhereClause;
	
	/**
	 * Buffer for the newly built query.
	 */
	private StringBuilder sb = new StringBuilder();
	
	/**
	 * Creates a new RebuildQueryAfterParameterRemoval object. 
	 * @param newWhereClauses 
	 */
	public RebuildQueryWithNewWhereClause(List<String> newWhereClauses) {
		super();
		this.newWhereClauses = newWhereClauses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(PlainSelect plainSelect) {
		sb.append(SPACE+SELECT);
		Distinct distinct = plainSelect.getDistinct();
		sb.append(SPACE + StringUtils.toString(distinct, EMPTY));
		if(!CollectionUtils.isNullOrEmpty(plainSelect.getSelectItems())) {
			Object[] items = plainSelect.getSelectItems().toArray();
			sb.append(SPACE+StringUtils.array2String(items, COMMA));
		}
		sb.append(SPACE+FROM);
		sb.append(SPACE+plainSelect.getFromItem().toString());
		if(!CollectionUtils.isNullOrEmpty(plainSelect.getJoins())) {
			for (Join j : (List<Join>) plainSelect.getJoins()) {
				if (j.isSimple()) {
					sb.append(StringConstants.COMMA + j.toString());
				} else {
					sb.append(SPACE + j.toString());
				}
			}
		}
		if(!StringUtils.isNullOrBlank(currentWhereClause)) {
			sb.append(SPACE + WHERE);
			sb.append(SPACE + currentWhereClause);
		}
		if(!CollectionUtils.isNullOrEmpty(plainSelect.getGroupByColumnReferences())) {
			sb.append(SPACE+GROUP_BY);
			Object[] gbColumns = plainSelect.getGroupByColumnReferences().toArray();
			sb.append(SPACE+StringUtils.array2String(gbColumns, COMMA));
		}
		Expression having = plainSelect.getHaving();
		if(having!=null) {
			sb.append(SPACE + HAVING);
			sb.append(SPACE + having.toString());
		}
		if(!CollectionUtils.isNullOrEmpty(plainSelect.getOrderByElements())) {
			sb.append(SPACE + ORDER_BY);
			Object[] obElements = plainSelect.getOrderByElements().toArray();
			sb.append(SPACE+StringUtils.array2String(obElements, COMMA));
		}
		Limit limit = plainSelect.getLimit();
		if(limit!=null) {
			sb.append(SPACE + limit.toString());
		}
	}

	@Override
	public void visit(Union union) {
		@SuppressWarnings("unchecked")
		List<PlainSelect> plainSelects = union.getPlainSelects();
		for(PlainSelect ps : plainSelects) {
			int idx = plainSelects.indexOf(ps);
			currentWhereClause = newWhereClauses.get(idx);
			ps.accept(this);
			if(idx < plainSelects.size()-1) {
				sb.append(SPACE+UNION);
				if (union.isAll()) {
					sb.append(SPACE + ALL);
				}
			}
		}
	}

	@Override
	public void visit(Select select) {
		SelectBody selectBody = select.getSelectBody();
		currentWhereClause = newWhereClauses.get(0);
		selectBody.accept(this);
	}
	
	/**
	 * @return Returns the re-built query.
	 */
	public String getResult() {
		return sb.toString();
	}

}
