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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

/**
 *  AjaxTabbedPanel that can keep the state of its tabs. 
 */
public class StatefulAjaxTabbedPanel 
extends AjaxTabbedPanel {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Form.
	 */
	Form<?> form;
	
	/**
	 * Button.
	 */
	Button button; 
	
	/**
	 * Creates a new StatefullAjaxTabbedPanel object. 
	 *
	 * @param id
	 * @param tabs
	 * @param announcementForm
	 * @param saveButton 
	 */
	public StatefulAjaxTabbedPanel(String id, List<ITab> tabs, Form<?> announcementForm, Button saveButton) {
		super(id, tabs);
		this.form = announcementForm;
		this.button = saveButton;
	}
	
	/**
	 * Expose onAjaxUpdate(AjaxRequestTarget) to package.
	 *  
	 * @param target
	 */	
	void callOnAjaxUpdate(AjaxRequestTarget target) {
		onAjaxUpdate(target);
	}
	
	@Override
	protected WebMarkupContainer newLink(String linkId, final int index) {
		return new StatefulAjaxTabbedPanelSubmitLink(linkId, this, index);
	}
	
}
