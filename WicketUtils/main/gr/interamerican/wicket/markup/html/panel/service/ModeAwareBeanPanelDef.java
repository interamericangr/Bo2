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
package gr.interamerican.wicket.markup.html.panel.service;

import gr.interamerican.wicket.creators.PanelCreatorMode;

import java.io.Serializable;

/**
 * Definition of a service panel that requires a bean
 * and is aware of the mode the bean should be represented
 * with.
 * 
 * @param <B>
 *        Type of bean. 
 */
public interface ModeAwareBeanPanelDef<B extends Serializable> extends BeanPanelDef<B> {
	
	/**
	 * Gets the PanelCreatorMode of the beanFieldsPanel.
	 * 
	 * @see #setBeanFieldsPanelMode(PanelCreatorMode)
	 * 
	 * @return Returns the PanelCreatorMode of the beanFieldsPanel.
	 */
	public PanelCreatorMode getBeanFieldsPanelMode();
	
	/**
	 * [OPTIONAL]
	 * Sets the PanelCreatorMode of the beanFieldsPanel. In some cases,
	 * it is essential for the <code>beanFieldsPanelCreator</code> to be
	 * aware of the type of action this panel supports. For example, an
	 * edit bean action may require different UI than a new bean action.
	 * 
	 * @param mode 
	 *        The PanelCreatorMode to set.
	 */
	public void setBeanFieldsPanelMode(PanelCreatorMode mode);
	
}
