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

import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

/**
 * Visitor to gather the table names that are joined.
 */
public class SelectAndJoinTablesNamesVisitor implements StatementVisitor, SelectVisitor, FromItemVisitor {

	/**
	 * 
	 */
	protected Set<String> tableNames = new HashSet<String>();

	@Override
	public void visit(Select select) {
		select.getSelectBody().accept(this);
	}

	@Override
	public void visit(Delete delete) {
		throw new RuntimeException("Delete not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Update update) {
		throw new RuntimeException("Update not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Insert insert) {
		throw new RuntimeException("Insert not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Replace replace) {
		throw new RuntimeException("Replace not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Drop drop) {
		throw new RuntimeException("Drop not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(Truncate truncate) {
		throw new RuntimeException("Truncate not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(CreateTable createTable) {
		throw new RuntimeException("CreateTable not supported"); //$NON-NLS-1$
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		plainSelect.getFromItem().accept(this);
		@SuppressWarnings("unchecked")
		List<Join> joins = Utils.notNull((List<Join>) plainSelect.getJoins(), new ArrayList<Join>());
		for (Join join : joins) {
			join.getRightItem().accept(this);
		}
	}

	@SuppressWarnings("unchecked")
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
		return tableNames;
	}

	@Override
	public void visit(Table tableName) {
		tableNames.add(tableName.getName());
		if (tableName.getAlias() != null) {
			tableNames.add(tableName.getAlias());
		}
	}

	@Override
	public void visit(SubSelect subSelect) {
		subSelect.accept(this);
	}

	@Override
	public void visit(SubJoin subjoin) {
		subjoin.accept(this);
	}
}
