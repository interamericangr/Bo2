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
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.def.PanelDependent;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * The ChangeAwareSelfDrawnPanelCreator creates a self-drawn panel
 * that allows some of its components to execute a {@link CallbackAction}
 * when their "onchange" event is triggered that may perform an update
 * on the panel itself.
 * 
 * @param <B> 
 *        Type of the business object the self-drawn panel represents. 
 * @param <C> 
 *        Type of {@link CallbackAction}.
 */
public class ChangeAwareSelfDrawnPanelCreator
<B extends Serializable, 
 C extends CallbackAction & PanelDependent> 
extends SelfDrawnPanelCreator<B> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket ids of components that get a onChange updating behavior.
	 */
	HashSet<String> onChangeUpdatingComponents;
	
	/**
	 * Action that is executed onChange
	 */
	C onChangeAction;
	
	/**
	 * Creates a new ChangeAwareSelfDrawnPanelCreator object. 
	 *
	 * @param beanDescriptor
	 * @param onChangeUpdatingComponents 
	 * @param onChangeAction 
	 */
	public ChangeAwareSelfDrawnPanelCreator(BusinessObjectDescriptor<B> beanDescriptor, 
			Set<String> onChangeUpdatingComponents, C onChangeAction) {
		super(beanDescriptor);
		this.onChangeUpdatingComponents = new HashSet<String>(onChangeUpdatingComponents);
		this.onChangeAction = onChangeAction;
	}
	
	@Override
	public Panel createPanel(ModeAwareBeanPanelDef<B> definition) {
		Panel panel = super.createPanel(definition);
		onChangeAction.setPanel(panel);
		SelfDrawnUtils.addUpdatingBehavior(panel, onChangeUpdatingComponents, onChangeAction);		
		return panel;
	}

}
