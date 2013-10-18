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
package gr.interamerican.wicket.markup.html.panel.back;

import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import org.apache.wicket.model.IModel;


/**
 * {@link ServicePanelWithBackDef} is definition bean for of wicket
 * service panels that have a back (or cancel) button. <br/>
 *
 */
public interface ServicePanelWithBackDef extends ServicePanelDef {
	
	/**
	 * Gets the backAction.
	 *
	 * @return Returns the backAction
	 */
	CallbackAction getBackAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the goBackAction. If this is not
	 * set, the back button is not shown.
	 *
	 * @param backAction the backAction to set
	 */
	void setBackAction(CallbackAction backAction);
	
	/**
	 * Gets the back button label resource model.
	 * 
	 * @return label.
	 */
	IModel<String> getBackLabelModel();
	
	/**
	 * [OPTIONAL] Sets the back button label resource model.
	 * 
	 * @param labelModel
	 */
	void setBackLabelModel(IModel<String> labelModel);
	
}
