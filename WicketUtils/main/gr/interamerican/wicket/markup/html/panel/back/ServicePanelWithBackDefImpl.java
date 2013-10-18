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
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDefImpl;

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link ServicePanelWithBackDef}
 */
public class ServicePanelWithBackDefImpl 
extends ServicePanelDefImpl
implements ServicePanelWithBackDef {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Back {@link CallbackAction}
	 */
	private CallbackAction backAction;
	
	/**
	 * Back button label resource model.
	 */
	private IModel<String> backLabel;
	
			
	public CallbackAction getBackAction() {
		return backAction;
	}
	
	public void setBackAction(CallbackAction backAction) {
		this.backAction = backAction;
	}

	public IModel<String> getBackLabelModel() {
		return backLabel;
	}

	public void setBackLabelModel(IModel<String> label) {
		this.backLabel = label;
	}

}
