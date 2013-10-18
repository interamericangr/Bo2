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
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

/**
 * Self-drawn {@link Palette}.
 * 
 * @param <L>
 *        Type of language id. 
 * @param <T> 
 *        Type of entry.
 */
public class SelfDrawnPalleteForEntries
<L, T extends TranslatableEntry<?, ?, L>> 
extends PaletteForEntry<L,T>{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Style
	 */
	private static final String WIDTH_STYLE ="width:200px"; //$NON-NLS-1$
	
	/**
	 * Number of choices to be visible on the screen with out scrolling.
	 */
	private static final int rows = 10;
	
	/**
	 * Allow user to move selections up and down.
	 */
	private static boolean allowOrder = false;
	
	/**
	 * Creates a new SelfDrawnPalleteForEntries object. 
	 * 
	 * @param id 
	 * @param descriptor  
	 * @param model 
	 * @param choicesModel 
	 * @param session 
	 */

	public SelfDrawnPalleteForEntries(
	String id, IModel<List<T>> model, PalleteCachedEntriesBoPropertyDescriptor<T, ?> descriptor,
	IModel<? extends Collection<? extends T>> choicesModel, Bo2WicketSession<?, L> session) {
		super(id, model, choicesModel, rows, allowOrder, session);
		setOutputMarkupPlaceholderTag(true);
    	setEnabled(!descriptor.isReadOnly());
	}
	
	/**
	 * Creates a new SelfDrawnPalleteForEntries object.
	 * 
	 * @param id 
	 * @param descriptor 
	 * @param choicesModel 
	 * @param session 
	 */
	public SelfDrawnPalleteForEntries(
	String id, PalleteCachedEntriesBoPropertyDescriptor<T, ?> descriptor, 
	IModel<? extends Collection<? extends T>> choicesModel , Bo2WicketSession<?, L> session) {
		this(id, new ListModel<T>(new ArrayList<T>()), descriptor, choicesModel, session);
	}
	
	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SPAN);
		tag.put(MarkupConstants.STYLE, WIDTH_STYLE);
		super.onComponentTag(tag);
	}
		
}
