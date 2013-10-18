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
package gr.interamerican.wicket.bo2.creators;

import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.wicket.bo2.markup.html.panel.SelfDrawnPanel;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * {@link PanelCreator} implementation based on {@link SelfDrawnPanel}.
 * 
 * @param <B> type of Bean
 */
public class SelfDrawnPanelCreator
<B extends Serializable> 
implements PanelCreator<B> {
	
	/**
	 * B descriptor.
	 */
	 BusinessObjectDescriptor<B> beanDescriptor;
	
	/**
	 * Creates a new SelfDrawnPanelCreator object. 
	 * 
	 * @param beanDescriptor 
	 */
	public SelfDrawnPanelCreator(BusinessObjectDescriptor<B> beanDescriptor) {
		super();
		this.beanDescriptor = beanDescriptor;
	}

	public Panel createPanel(ModeAwareBeanPanelDef<B> definition) {
		if(!(definition.getBeanModel() instanceof CompoundPropertyModel)) {
			throw new RuntimeException("The bean model of ModeAwareBeanPanelDef is not a CompoundPropertyModel."); //$NON-NLS-1$
		}
		return new SelfDrawnPanel<B>(definition.getWicketId(), (CompoundPropertyModel<B>) definition.getBeanModel(), beanDescriptor);
	}

}
