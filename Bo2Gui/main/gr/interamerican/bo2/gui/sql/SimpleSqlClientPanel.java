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
package gr.interamerican.bo2.gui.sql;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Simple SQL client panel.
 */
public class SimpleSqlClientPanel extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SimpleSqlClientPanel object. 
	 *
	 */
	public SimpleSqlClientPanel() {
		JTabbedPane tabs = new JTabbedPane();
		add(tabs);
		
		tabs.addTab("Query data", new QueryPanel()); //$NON-NLS-1$
		tabs.addTab("Insert/Update data", new UpdatePanel()); //$NON-NLS-1$
	}

}
