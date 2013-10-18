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

import java.util.List;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * Finds the table names involved in the expected ResultSet columns of an 
 * SQL statement. If the statement is 'select *...', then an empty List is 
 * returned.
 */
public class TableNamesFinderInSelectElements extends AbstractTableNamesFinder {

	/**
	 * Entry point of this visitor.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void visit(PlainSelect plainSelect) {
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		for (SelectItem selectItem : selectItems) {
			selectItem.accept(this);
		}
	}
	
}
