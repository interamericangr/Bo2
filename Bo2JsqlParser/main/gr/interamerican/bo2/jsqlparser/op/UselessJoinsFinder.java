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
package gr.interamerican.bo2.jsqlparser.op;

import gr.interamerican.bo2.jsqlparser.visitors.JoinRightItemStaticLocator;
import gr.interamerican.bo2.jsqlparser.visitors.TableNamesFinderInExpression;
import gr.interamerican.bo2.jsqlparser.visitors.TableNamesFinderInHaving;
import gr.interamerican.bo2.jsqlparser.visitors.TableNamesFinderInJoin;
import gr.interamerican.bo2.jsqlparser.visitors.TableNamesFinderInSelectElements;
import gr.interamerican.bo2.jsqlparser.visitors.TableNamesFinderInWhereClause;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Select;

/**
 * This operation gets a select statements and returns a set of 
 * {@link Join}s that are not required for the sql to run.
 */
public class UselessJoinsFinder {
	
	/**
	 * Singleton.
	 */
	public static final UselessJoinsFinder INSTANCE = new UselessJoinsFinder();
	
	/**
	 * Finds the useless Joins of an SQL statement.
	 * 
	 * @param sql
	 * 
	 * @return useless Joins
	 * 
	 * @throws SqlParseException
	 */
	public Set<Join> find(String sql) throws SqlParseException {
		Set<Join> uselessJoins = new HashSet<Join>();
		
		if (StringUtils.isNullOrBlank(sql)) {
			return uselessJoins;
		}
		
		JSqlParser parser = new CCJSqlParserManager();
		Statement st;
		try {
			st = parser.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			throw new SqlParseException(e);
		}
		
		if (!(st instanceof Select)) {
			throw new SqlParseException("Sql statement type not supported"); //$NON-NLS-1$
		}
		
		Select plain = (Select) st;
		
		TableNamesFinderInSelectElements selectFinder = new TableNamesFinderInSelectElements();
		plain.accept(selectFinder);
		if (selectFinder.getAllColumns()) {
			return uselessJoins;
		}
		
		TableNamesFinderInWhereClause whereFinder = new TableNamesFinderInWhereClause();
		plain.accept(whereFinder);
		
		TableNamesFinderInJoin joinFinder = new TableNamesFinderInJoin();
		joinFinder.setSelectTables(selectFinder.getTableNames());
		plain.accept(joinFinder);
		
		TableNamesFinderInHaving havingFinder = new TableNamesFinderInHaving();
		plain.accept(havingFinder);

		Set<Join> usefullJoins = new HashSet<Join>();
		for (int i = 0; i < joinFinder.getJoinTables().keySet().size(); i++) {
			usefullJoins = getUsefullJoins(selectFinder.getTableNames(), whereFinder.getTableNames(),
					havingFinder.getTableNames(), usefullJoins, joinFinder.getJoinTables());
			if (usefullJoins.containsAll(joinFinder.getJoinTables().keySet())) {
				return uselessJoins;
			}
		}
		uselessJoins.addAll(joinFinder.getJoinTables().keySet());
		uselessJoins.removeAll(usefullJoins);
		
		return uselessJoins;
	}
	
	/**
	 * Creates the list of {@link Join}s that are not required for the analyzed sql.
	 * 
	 * @param selectTables
	 *            Tables required for the ResultSet.
	 * @param whereTables
	 *            Tables required for the Where clause.
	 * @param havingTables
	 *            Tables required for the having clause.
	 * @param usefullJoins
	 *            Useful joins found so far.
	 * @param joinTablesMap
	 *            Tables involved in each Join.
	 * 
	 * @return Returns useful joins.
	 */
	private Set<Join> getUsefullJoins(Set<String> selectTables, Set<String> whereTables, Set<String> havingTables,
			Set<Join> usefullJoins, Map<Join, Set<String>> joinTablesMap) {
		Set<String> joinTables = new HashSet<String>();
		Set<Join> result = new HashSet<Join>();
		result.addAll(usefullJoins);
		for (Join j : usefullJoins) {
			joinTables.add(getJoinTable(j));
			joinTables.addAll(getJoinedTables(j));
		}
		for (Join j : joinTablesMap.keySet()) {
			String joinTable = getJoinTable(j);
			if ((hasJoinStaticValues(j)) || (selectTables.contains(joinTable)) || (whereTables.contains(joinTable))
					|| (havingTables.contains(joinTable)) || (joinTables.contains(joinTable))) {
				result.add(j);
			}
		}
		return result;
	}
	
	/**
	 * Returns the join table of a {@link Join}
	 * 
	 * @param j
	 * @return the join table of a {@link Join}.
	 */
	private String getJoinTable(Join j) {
		return Utils.notNull(j.getRightItem().getAlias(), j.getRightItem().toString());
	}

	/**
	 * Gets the joined tables on a {@link Join}
	 * 
	 * @param j
	 * @return joined tables.
	 */
	private Set<String> getJoinedTables(Join j) {
		TableNamesFinderInExpression finder = new TableNamesFinderInExpression();
		if (j.getOnExpression() != null) {
			j.getOnExpression().accept(finder);
		}
		return finder.getTableNames();
	}
	
	/**
	 * checks whether a join has static values or not. If it has it won't be removed.
	 * 
	 * @param j
	 * @return
	 */
	private boolean hasJoinStaticValues(Join j) {
		TableNamesFinderInExpression finder = new TableNamesFinderInExpression();
		if (j.getOnExpression() != null) {
			j.getOnExpression().accept(finder);
		}
		JoinRightItemStaticLocator joinRightItemStaticLocator=new JoinRightItemStaticLocator();
		j.getRightItem().accept(joinRightItemStaticLocator);
		return finder.getStaticValue() || joinRightItemStaticLocator.getStaticValue();
	}
	/**
	 * Hidden.
	 */
	private UselessJoinsFinder() {/*empty*/}

}
