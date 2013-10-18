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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * @param <T> 
 * 
 */
public abstract class CheckBoxPanel<T> extends Panel
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CheckBoxPanel object. 
	 *
	 * @param id
	 * @param model
	 * @param isSelected 
	 */
	public CheckBoxPanel(String id, IModel<T> model, boolean isSelected) {
		super(id, model);
		add(new CheckBox("checkboxId", new Model<Boolean>(isSelected)));  //$NON-NLS-1$
	}
	
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> clazz) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<html><body>"); //$NON-NLS-1$
		builder.append("<wicket:panel>"); //$NON-NLS-1$
			builder.append("<input type=\"checkbox\" wicket:id=\"checkboxId\"></input>"); //$NON-NLS-1$
		builder.append("</wicket:panel></body></html>"); //$NON-NLS-1$
		return new AbstractResourceStream() {
			
			/**
			 * UID
			 */
			private static final long serialVersionUID = 1L;

			public InputStream getInputStream() throws ResourceStreamNotFoundException {
				return new ByteArrayInputStream(builder.toString().getBytes());
			}

			public void close() throws IOException {
				return;
			}
		};
	}
	
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}
	
}
