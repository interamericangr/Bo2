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
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * {@link Panel} that contains a radio button used to select
 * one of the rows of a {@link DataTable}
 * 
 * @param <T> type of model object.
 */
public class DataTableRadioButtonPanel<T extends Serializable> extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	private static final String RADIO_ID = "radioButton"; //$NON-NLS-1$
	
	/**
	 * Creates a new DataTableRadioButtonPanel object. 
	 *
	 * @param id
	 * @param model
	 */
	public DataTableRadioButtonPanel(String id, IModel<T> model) {
		super(id, model);
		Radio<T> radioButton = new Radio<T>(RADIO_ID);
		radioButton.setDefaultModel(model);
		add(radioButton);
	}
	
	@SuppressWarnings({ "nls", "serial" })
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		final StringBuffer builder = new StringBuffer();
		builder.append("<html><body><wicket:panel>");
		builder.append("<input type=\"radio\" wicket:id=\"" + RADIO_ID + "\"/>");
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
