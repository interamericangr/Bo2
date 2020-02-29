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
package gr.interamerican.wicket.markup.html.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.AjaxLinkImagePickColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.AjaxLinkPickColumn;
import gr.interamerican.wicket.links.LegacyCallbackAjaxLink;
import gr.interamerican.wicket.panels.AjaxLinkPanel;
import gr.interamerican.wicket.panels.AjaxLinkWithImagePanel;
import gr.interamerican.wicket.utils.ImageType;

/**
 * Panel containing a link in text or icon form, for the execution of an action on a {@link DataTable}.
 * 
 * @param <T> The object type
 * @deprecated See Constructors for alternatives in each case
 */
@Deprecated
public abstract class DataTableAjaxLinkPanel<T> 
extends DataTableLinkPanel<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new {@link DataTableAjaxLinkPanel} object. 
	 *
	 * @param id the id
	 * @param model the model
	 * @param linkText the link text
	 * @deprecated Use {@link AjaxLinkPanel} or {@link LegacyCallbackAjaxLink} instead and
	 *             {@link LegacyCallbackAjaxLink#setBody(IModel)} for text. If this was used
	 *             inside a {@link AbstractColumn} then consider
	 *             {@link AjaxLinkPickColumn}
	 */
	public DataTableAjaxLinkPanel(String id, IModel<T> model, String linkText) {
		super(id, model, linkText);
	}
	
	/**
	 * Creates a new {@link DataTableAjaxLinkPanel} object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param imageType
	 *            the image type
	 * @deprecated Use {@link AjaxLinkWithImagePanel} instead. If this was used
	 *             inside a {@link AbstractColumn} then consider
	 *             {@link AjaxLinkImagePickColumn}
	 */
	public DataTableAjaxLinkPanel(String id, IModel<T> model, ImageType imageType) {
		super(id, model, imageType);
	}
	
	@Override
	public void onLinkClicked() { /* empty */ }

	/**
	 * Creates the link for this panel.
	 * 
	 * @param id
	 *        Wicket id of the link.
	 * @param model
	 *        Link model.
	 *        
	 * @return Returns created link.
	 */
	@Override
	protected AbstractLink getLink(String id, IModel<T> model) {
		return new AjaxLink<T>(id, model) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DataTableAjaxLinkPanel.this.onLinkClicked(target);			
			}
		};
	}
	
	/**
	 * Event handler for the click event of the link. 
	 * 
	 * @param target AjaxRequestTarget
	 */
	public abstract void onLinkClicked(AjaxRequestTarget target);

}