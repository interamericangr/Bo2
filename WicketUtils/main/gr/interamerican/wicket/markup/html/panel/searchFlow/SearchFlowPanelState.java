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
package gr.interamerican.wicket.markup.html.panel.searchFlow;


/**
 * Possible states of a {@link SearchFlowPanel}.
 */
public enum SearchFlowPanelState {
	
	/**
	 * Only the criteria panel is visible.
	 */
	CRITERIA {
		@Override
		void paint(SearchFlowPanel<?, ?> panel) {			
			panel.criteriaPanel.setVisible(true);
			panel.resultsPanel.setVisible(false);
		}
	},
	
	/**
	 * Only the results panel is visible.
	 */
	RESULTS {
		@Override
		void paint(SearchFlowPanel<?, ?> panel) {			
			panel.criteriaPanel.setVisible(false);
			panel.resultsPanel.setVisible(true);
		}
	},
	
	/**
	 * Both panels are visible.
	 */
	BOTH {
		@Override
		void paint(SearchFlowPanel<?, ?> panel) {
			panel.criteriaPanel.setVisible(true);
			panel.resultsPanel.setVisible(true);
		}
	};
	
	
	/**
	 * Paints the panel.
	 * 
	 * @param panel
	 */
	abstract void paint(SearchFlowPanel<?, ?> panel);
	
	

}
