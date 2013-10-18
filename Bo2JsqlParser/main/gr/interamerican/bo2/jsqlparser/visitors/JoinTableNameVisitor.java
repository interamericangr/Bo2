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

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;


/**
 * 
 */
public class JoinTableNameVisitor implements FromItemVisitor {

	/**
	 * table name
	 */
	private String tableName;

	/**
	 * @return the table name.
	 */
	public String getTableName() {
		return tableName;
	}

	@Override
	public void visit(Table tableName) {
		this.tableName = tableName.getName();
	}

	@Override
	public void visit(SubSelect subSelect) {
		throw new RuntimeException("subselects not yet supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(SubJoin subjoin) {
		throw new RuntimeException("subjoins not yet supported"); //$NON-NLS-1$
	}


}
