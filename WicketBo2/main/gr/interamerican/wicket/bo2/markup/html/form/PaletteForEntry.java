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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.model.IModel;

/**
 * {@link Palette} extension for {@link TranslatableEntry} objects.
 * 
 * This implementation will print the translation for the language 
 * of the current session.
 * 
 * @param <L> 
 *        Type of language id.
 * @param <T> 
 *        Type of entry.
 */
public class PaletteForEntry<L, T extends TranslatableEntry<?, ?, L>> extends Palette<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new PalleteForEntry object. 
	 *
	 * @param id
	 * @param model
	 * @param choicesModel
	 * @param rows
	 * @param allowOrder
	 * @param session 
	 */
	public PaletteForEntry(String id, IModel<List<T>> model, 
	IModel<? extends Collection<? extends T>> choicesModel, 
	int rows, boolean allowOrder,Bo2WicketSession<?, L> session) {
		super(id, model, choicesModel, new ChoiceRendererForEntry<L, T>(session), rows, allowOrder);
	}

}
