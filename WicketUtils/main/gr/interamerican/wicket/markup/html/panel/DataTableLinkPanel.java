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

import gr.interamerican.wicket.util.resource.StringAsResourceStream;
import gr.interamerican.wicket.utils.ImageType;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Panel containing a link in text or icon form, 
 * for the execution of an action on a {@link DataTable}.
 * The action is executed using a plain link, making this
 * implementation suitable for downloading a file.
 * 
 * @param <T> The object type
 */
public abstract class DataTableLinkPanel<T> extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Wicket id.
	 */
	public static final String LINK_ID = "editItemLink"; //$NON-NLS-1$

	/**
	 * AbstractLink, this may be a {@link Link} or an {@link AjaxLink}.
	 */
	protected AbstractLink dataTableLink;
	
	/**
	 * Link text.
	 */
	protected String linkText;
	
	/**
	 * Link icon.
	 */
	protected ImageType imageType;
	
	/**
	 * Creates a new {@link DataTableLinkPanel} object. 
	 *
	 * @param id
	 * @param model
	 * @param linkText 
	 */
	public DataTableLinkPanel(String id, IModel<T> model, String linkText) {
		super(id, model);
		this.linkText = linkText;
		this.imageType = null;
		dataTableLink = getLink(LINK_ID, model);
		if (linkText != null) {
			dataTableLink.add(new Label("linkText", new Model<String>(linkText))); //$NON-NLS-1$
		}
		add(dataTableLink);
	}
	
	/**
	 * Creates a new {@link DataTableLinkPanel} object. 
	 *
	 * @param id
	 * @param model
	 * @param imageType 
	 */
	public DataTableLinkPanel(String id, IModel<T> model, ImageType imageType) {
		super(id, model);
		this.imageType = imageType;
		this.linkText = null;
		dataTableLink = getLink("editItemLink", model); //$NON-NLS-1$
		add(dataTableLink);
	}
	
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> clazz) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<html><body>"); //$NON-NLS-1$
		builder.append("<wicket:panel>"); //$NON-NLS-1$
		if (linkText != null) {
			builder.append("<a wicket:id=\"editItemLink\"><label wicket:id=\"linkText\"></label></a>"); //$NON-NLS-1$
		} 
		else {
			String contextRelativeUrl = getRequestCycle().getUrlRenderer().renderContextRelativeUrl(imageType.getImage()) ;
			builder.append(imageType.toString(contextRelativeUrl));
		}
		builder.append("</wicket:panel></body></html>"); //$NON-NLS-1$
		return new StringAsResourceStream(builder.toString());
	}
	
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}
	
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
	protected AbstractLink getLink(String id, IModel<T> model) {
		return new Link<T>(id, model) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				DataTableLinkPanel.this.onLinkClicked();			
			}
		};
	}
	
	/**
	 * Event handler for the click event of the link. 
	 */
	public abstract void onLinkClicked();

}
