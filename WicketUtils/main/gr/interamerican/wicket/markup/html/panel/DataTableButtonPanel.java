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

import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;
import gr.interamerican.wicket.utils.ImageType;

import java.io.Serializable;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * {@link Panel} that contains an {@link AjaxButton} shown as an image
 * used to execute an action in a {@link DataTable}.
 * 
 * This is intended for actions that require transaction management. For
 * simpler cases, use a {@link DataTableAjaxLinkPanel}
 * 
 * @param <T> type of model object.
 * 
 */
public abstract class DataTableButtonPanel<T extends Serializable> extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * wicket id.
	 */
	private static final String BUTTON_ID = ImageType.WICKET_ID.toString();
	
	/**
	 * {@link CallbackAction}
	 */
	private CallbackAction action;
	
	/**
	 * type of action and associated image.
	 */
	private ImageType imageType;

	/**
	 * Creates a new DataTableButtonPanel object. 
	 *
	 * @param id
	 * @param model 
	 * @param imageType
	 * @param action
	 */
	public DataTableButtonPanel(String id, IModel<T> model, ImageType imageType, CallbackAction action) {
		super(id, model);
		this.imageType = imageType;
		this.action = action;
		add(new DataTableAjaxButton(BUTTON_ID));
	}
	
	/**
	 * Error handling on button.
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onButtonError(AjaxRequestTarget target, Form<?> form);
	
	
	@SuppressWarnings("nls")
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> clazz) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<html><body><wicket:panel>");
		builder.append(imageType.toString());
		builder.append("</wicket:panel></body></html>");
		return new StringAsResourceStream(builder.toString());
	}	
	
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null;
	}
	
	/**
	 * {@link AjaxButton} of this panel.
	 */
	private class DataTableAjaxButton extends CallbackAjaxButton {
		
		/**
		 * serial id.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new DataTableButtonPanel.DataTableAjaxButton object.
		 *  
		 * @param id wicket id.
		 */
		public DataTableAjaxButton(String id) {
			super(id,action);
		}
		
		@Override
		public void onError(AjaxRequestTarget target, Form<?> form) {			
			onButtonError(target, form);
		}
	}

}
