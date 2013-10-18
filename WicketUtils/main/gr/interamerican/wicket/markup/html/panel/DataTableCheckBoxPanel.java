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
import java.io.Serializable;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * {@link Panel} that contains a check box used to select
 * one of the rows of a {@link DataTable}
 * 
 * @param <T> type of model object.
 */
public class DataTableCheckBoxPanel<T extends Serializable> extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	private static final String CHECKBOX_ID = "checkBox"; //$NON-NLS-1$

	/**
	 * Creates a new DataTableCheckBoxPanel object. 
	 *
	 * @param id
	 * @param model
	 */
	public DataTableCheckBoxPanel(String id, IModel<T> model) {
		super(id, model);
		Check<T> checkBox = new Check<T>(CHECKBOX_ID);
		checkBox.setDefaultModel(model);
		add(checkBox);
	}


	@SuppressWarnings({ "nls", "serial" })
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		final StringBuffer builder = new StringBuffer();
		builder.append("<html><body><wicket:panel>");
		builder.append("<input type=\"checkbox\" wicket:id=\"" + CHECKBOX_ID + "\"/>");
		builder.append("</wicket:panel></body></html>");
		return new AbstractResourceStream() {
			public InputStream getInputStream() throws ResourceStreamNotFoundException {
				return new ByteArrayInputStream(builder.toString().getBytes());
			}
			public void close() throws IOException { /* empty */ }
		}; 
	}
	
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null;
	}

}
