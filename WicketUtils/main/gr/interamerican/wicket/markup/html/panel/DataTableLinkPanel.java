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

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.LinkImagePickColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.LinkPickColumn;
import gr.interamerican.wicket.links.RunnableLink;
import gr.interamerican.wicket.panels.LinkPanel;
import gr.interamerican.wicket.panels.LinkWithImagePanel;
import gr.interamerican.wicket.utils.ImageType;

/**
 * Panel containing a link in text or icon form, 
 * for the execution of an action on a {@link DataTable}.
 * The action is executed using a plain link, making this
 * implementation suitable for downloading a file.
 * 
 * @param <T> The object type
 * @deprecated See Constructors for alternatives in each case
 */
@Deprecated
public abstract class DataTableLinkPanel<T> extends Panel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Wicket id.
	 */
	public static final String LINK_ID = "editItemLink"; //$NON-NLS-1$
	
	/**
	 * Link icon.
	 */
	protected ImageType imageType;
	
	/**
	 * Creates a new {@link DataTableLinkPanel} object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param linkText
	 *            the link text
	 * @deprecated Use {@link LinkPanel} or {@link RunnableLink} instead and
	 *             {@link RunnableLink#setBody(IModel)} for text. If this was
	 *             used inside a {@link AbstractColumn} then consider
	 *             {@link LinkPickColumn}
	 */
	public DataTableLinkPanel(String id, IModel<T> model, String linkText) {
		super(id, model);
		this.imageType = null;
		AbstractLink dataTableLink = getLink(LINK_ID, model);
		dataTableLink.setBody(Model.of(linkText));
		add(dataTableLink);
	}

	/**
	 * Creates a new {@link DataTableLinkPanel} object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param imageType
	 *            the image type
	 * @deprecated Use {@link LinkWithImagePanel} instead. If this was used
	 *             inside a {@link AbstractColumn} then consider
	 *             {@link LinkImagePickColumn}
	 */
	@SuppressWarnings("nls")
	public DataTableLinkPanel(String id, IModel<T> model, ImageType imageType) {
		super(id, model);
		this.imageType = imageType;
		AbstractLink dataTableLink = getLink(LINK_ID, model);
		String contextRelativeUrl = getRequestCycle().getUrlRenderer()
				.renderContextRelativeUrl(imageType.getImage());
		dataTableLink.setEscapeModelStrings(false);
		dataTableLink.setBody(Model.of("<img src=\""+contextRelativeUrl+"\"/>"));
		add(dataTableLink);
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