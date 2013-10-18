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
package gr.interamerican.bo2.gui.components;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * BTable.
 */
public class BTable extends JTable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;	
		
	/**
	 * Name.
	 */
	String name;
	
	/**
	 * Creates a new BTable object.
	 * 
	 * @param name
	 *            Name of the table.
	 */
	public BTable(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Size the table at its preferred size or the viewport size, whichever is greater.
	 */
	@Override
	public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width < getParent().getWidth();
    }
	
	@Override
	public void setModel(TableModel dataModel) {
		super.setModel(dataModel);
		setRowSorter(new TableRowSorter<TableModel>(getModel()));
	}

}
