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
import java.util.Set;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * find table names in joins.
 */
public class TableNamesFinderInHaving extends AbstractTableNamesFinder {

	/**
	 * Map from joins and a set of tables used in the given join.
	 */
	private Set<String> havingTables = new HashSet<String>();
	

	/**
	 * @return the map of tables in having.
	 */
	public Set<String> getHavingTables() {
		return havingTables;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		Expression having = plainSelect.getHaving();
		if (having != null) {
			having.accept(this);
		}
		havingTables = this.tableNames;
	}
}
