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

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link ServicePanelDef}
 * 
 * @param <B>
 *        Type of model object. 
 */
public class ModeAwareBeanPanelDefImpl<B extends Serializable> 
extends ServicePanelDefImpl implements ModeAwareBeanPanelDef<B>{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The model of the panel.
	 */
	private IModel<B> beanModel;
	
	/**
	 * Mode 
	 */
	private PanelCreatorMode mode;

	public void setBeanModel(IModel<B> beanModel) {
		this.beanModel = beanModel;
	}

	public IModel<B> getBeanModel() {
		return beanModel;
	}

	public PanelCreatorMode getBeanFieldsPanelMode() {
		return mode;
	}

	public void setBeanFieldsPanelMode(PanelCreatorMode mode) {
		this.mode = mode;
	}

}
