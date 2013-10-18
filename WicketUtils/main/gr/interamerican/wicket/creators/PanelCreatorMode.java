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
package gr.interamerican.wicket.creators;

import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

/**
 * PanelCreator mode. This information is used in a 
 * {@link ModeAwareBeanPanelDef} that is used as input
 * in a {@link PanelCreator}.
 */
public enum PanelCreatorMode {
	
	/**
	 * PanelCreator that shows the contents of a
	 * new bean that hasn't been stored yet.
	 */
	CREATE,
	
	/**
	 * PanelCreator that shows the contents of a 
	 * bean and is not supposed to have edit capabilities.
	 */
	VIEW,
	
	/**
	 * PanelCreator that shows the contents of a
	 * bean and allows the user to edit it.
	 */
	EDIT,
	
	/**
	 * There is no panel created for this mode, but the
	 * element is added in order to represent all CRUD states.
	 */
	DELETE;

}
