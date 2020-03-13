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
package gr.interamerican.wicket.callback;

import java.io.Serializable;

import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * An {@link AbstractCallbackAction} that simply takes a {@link BeanPanelDef} as
 * an argument and puts it for rendering.
 * 
 * @param <B>
 *            Type of Property the {@link BeanPanelDef} associates to
 * @deprecated Not Relevant anymore
 */
@Deprecated
public class RenderBeanPanel<B extends Serializable>
extends AbstractCallbackAction {

	/** Version UID. */
	private static final long serialVersionUID = 1L;
	
	/** {@link BeanPanelDef} to Render. */
	BeanPanelDef<B> def;

	/**
	 * Creates a new RenderBeanPanel object.
	 * 
	 * @param def
	 *            {@link BeanPanelDef} to Render
	 */
	public RenderBeanPanel(BeanPanelDef<B> def) {
		this.def = def;
	}

	@Override
	public void callBack(AjaxRequestTarget target) {
		work(target);
	}

	@Override
	public void callBack(AjaxRequestTarget target, Form<?> form) {
		work(target);
	}

	/**
	 * Main method of the RenderBeanPanel.
	 * @param target {@link AjaxRequestTarget}
	 */
	void work(AjaxRequestTarget target) {
		target.add(def.getServicePanel());
	}
}