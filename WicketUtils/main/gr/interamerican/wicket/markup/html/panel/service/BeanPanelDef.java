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

import org.apache.wicket.model.IModel;


/**
 * Definition for a ServicePanel that requires a bean.  
 * 
 * @param <B> 
 *        Type of bean.
 */
public interface BeanPanelDef<B> extends ServicePanelDef {
	
	/**
	 * [MANDATORY]
	 * Sets the model of the panel.
	 * 
	 * @see #getBeanModel()
	 * 
	 * @param beanModel
	 *        the model of the panel.
	 */
	void setBeanModel(IModel<B> beanModel);
	
	/**
	 * Gets the model of the panel. This property intends to allow
	 * the users to specify whichever model implementation they prefer.
	 * The model object cannot be null. Users can use this to retrieve
	 * the object of this panel, for example at the implementation of the
	 * <code>beanAction</code>.
 	 *
	 * @return the model of the panel.
	 */
	IModel<B> getBeanModel();
	
}
