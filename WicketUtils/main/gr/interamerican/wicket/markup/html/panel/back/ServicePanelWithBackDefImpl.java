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

import org.apache.wicket.model.IModel;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDefImpl;

/**
 * Implementation of {@link ServicePanelWithBackDef}.
 */
public class ServicePanelWithBackDefImpl 
extends ServicePanelDefImpl
implements ServicePanelWithBackDef {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/** Back {@link LegacyCallbackAction}. */
	private LegacyCallbackAction backAction;
	
	/**
	 * Back button label resource model.
	 */
	private IModel<String> backLabel;

	@Override
	public LegacyCallbackAction getBackAction() {
		return backAction;
	}

	@Override
	public void setBackAction(LegacyCallbackAction backAction) {
		this.backAction = backAction;
	}

	@Override
	public IModel<String> getBackLabelModel() {
		return backLabel;
	}

	@Override
	public void setBackLabelModel(IModel<String> label) {
		this.backLabel = label;
	}
}