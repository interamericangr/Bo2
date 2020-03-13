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
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

/**
 * AjaxTabbedPanel that can keep the state of its tabs.<br>
 * In essence, this replaces the {@link #newLink(String, int)} button with a
 * Custom {@link AjaxSubmitLink}, which submits the form when we switch
 * tabs.<br>
 * Because of this customization - this panel is required to be inside a form.
 */
public class StatefulAjaxTabbedPanel 
extends AjaxTabbedPanel<ITab> {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new StatefullAjaxTabbedPanel object.
	 *
	 * @param id
	 *            the id
	 * @param tabs
	 *            the tabs
	 */
	public StatefulAjaxTabbedPanel(String id, List<ITab> tabs) {
		super(id, tabs);
	}

	/**
	 * Creates a new StatefullAjaxTabbedPanel object.
	 *
	 * @param id
	 *            the id
	 * @param tabs
	 *            the tabs
	 * @param announcementForm
	 *            the announcement form
	 * @param saveButton
	 *            the save button
	 * 
	 * @deprecated Use the Other Constructor - some fields are not being used
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public StatefulAjaxTabbedPanel(String id, List<ITab> tabs, Form<?> announcementForm, Button saveButton) {
		super(id, tabs);
	}

	/**
	 * Expose onAjaxUpdate(AjaxRequestTarget) to package.
	 * 
	 *
	 * @param target
	 *            the target
	 */
	void callOnAjaxUpdate(AjaxRequestTarget target) {
		onAjaxUpdate(target);
	}

	@Override
	protected WebMarkupContainer newLink(String linkId, final int index) {
		return new StatefulAjaxTabbedPanelSubmitLink(linkId, this, index);
	}
}