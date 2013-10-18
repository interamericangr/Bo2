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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.wicket.bo2.markup.html.panel.SelfDrawnPanel;
import gr.interamerican.wicket.bo2.validation.BusinessObjectFormValidator;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Self drawn form.
 * 
 * @param <T>
 *        Type of model object. 
 */
public class SelfDrawnForm<T extends Serializable> extends Form<T> {
	
	/**
	 * Form validator.
	 */
	BusinessObjectFormValidator boFormValidator;
	
	/**
	 * Self drawn panel.
	 */
	SelfDrawnPanel<T> selfDrawnPanel;
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Wicket id.
	 */
	public static final String PANEL_WICKET_ID = "selfDrawnPanel"; //$NON-NLS-1$

	/**
	 * Creates a new SelfDrawnForm object. 
	 *
	 * @param id
	 * @param model 
	 * @param beanDescriptor 
	 */
	public SelfDrawnForm(String id, CompoundPropertyModel<T> model, BusinessObjectDescriptor<T> beanDescriptor) {		
		super(id, model);				
		this.selfDrawnPanel = new SelfDrawnPanel<T>(PANEL_WICKET_ID, model, beanDescriptor); 
		add(selfDrawnPanel);
		this.boFormValidator = new BusinessObjectFormValidator(this, beanDescriptor);
		add(boFormValidator);
	}
	
}
