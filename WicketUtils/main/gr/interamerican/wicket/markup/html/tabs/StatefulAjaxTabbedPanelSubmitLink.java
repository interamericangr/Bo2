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
package gr.interamerican.wicket.markup.html.tabs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * {@link AjaxSubmitLink} for a {@link StatefulAjaxTabbedPanel}.
 */
class StatefulAjaxTabbedPanelSubmitLink 
extends AjaxSubmitLink {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	/**
	 * Owner StatefulAjaxTabbedPanel.
	 */
	StatefulAjaxTabbedPanel owner;
	/**
	 * Tab index.
	 */
	int index;
	
	/**
	 * Creates a new StatefulAjaxTabbedPanelSubmitLink object. 
	 *
	 * @param id
	 *        Link id
	 * @param owner 
	 *        StatefulAjaxTabbedPanel
	 * @param index 
	 *        index of the selected tab
	 */
	public StatefulAjaxTabbedPanelSubmitLink
	(String id, StatefulAjaxTabbedPanel owner, int index) {
		super(id, owner.form);
		this.owner = owner;
		this.index = index;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form1) {		
		owner.setSelectedTab(index);
		if (target != null) {
			target.add(owner);
			if (owner.button != null){
				target.add(owner.form);
			}
		}
		owner.callOnAjaxUpdate(target);
	}
	
	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(owner);
		owner.callOnAjaxUpdate(target);
	}



}
