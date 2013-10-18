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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Panel containing a textField.
 * 
 * @param <T> The object type
 */
public class DataTableTextFieldPanel<T> extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	private static final String TEXTFIELD_ID = "textField"; //$NON-NLS-1$
	
	/**
	 * Creates a new {@link DataTableTextFieldPanel} object. 
	 *
	 * @param id
	 * @param model
	 * @param property 
	 */
	public DataTableTextFieldPanel(String id, IModel<T> model, String property) {
		super(id, model);
		TextField<T> field = new TextField<T>(TEXTFIELD_ID, new PropertyModel<T>(model.getObject(), property));
		add(field);
	}
	
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> clazz) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<html><body>"); //$NON-NLS-1$
		builder.append("<wicket:panel>"); //$NON-NLS-1$
		builder.append("<input type=\"text\" wicket:id=\"" + TEXTFIELD_ID + "\"/>"); //$NON-NLS-1$ //$NON-NLS-2$
		builder.append("</wicket:panel></body></html>"); //$NON-NLS-1$
		return new StringAsResourceStream(builder.toString());
	}
	
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}

}
