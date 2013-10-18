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
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * find table names in joins.
 */
@SuppressWarnings("unchecked")
public class TableNamesFinderInJoin extends AbstractTableNamesFinder {

	/**
	 * Map from joins and a set of tables used in the given join.
	 */
	private Map<Join, Set<String>> joinTables = new HashMap<Join, Set<String>>();
	
	/**
	 * set of tables used in select of a join.
	 */
	private Set<String> selectTables = new HashSet<String>();

	/**
	 * @param tables
	 */
	public void setSelectTables(Set<String> tables) {
		selectTables = tables;
	}

	/**
	 * @return the map of join tables.
	 */
	public Map<Join, Set<String>> getJoinTables() {
		return joinTables;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		List<Join> joins = Utils.notNull((List<Join>) plainSelect.getJoins(), new ArrayList<Join>());
		for (Join join : joins) {
			if (!CollectionUtils.isNullOrEmpty(join.getUsingColumns())) {
				joinTables.put(join, selectTables);
			} else {
				this.tableNames = new HashSet<String>();
				if (join.getOnExpression() != null) {
					join.getOnExpression().accept(this);
					joinTables.put(join, this.getTableNames());
				} else {
					joinTables.put(join, new HashSet<String>());
				}
			}
		}
	}
}
