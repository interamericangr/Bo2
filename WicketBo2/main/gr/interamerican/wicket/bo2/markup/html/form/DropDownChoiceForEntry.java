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
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * {@link DropDownChoice} for {@link TranslatableEntry} objects.
 * 
 * This implementation will print the translation for the language 
 * of the current session.
 * 
 * @param <L> Type of language id.
 * @param <T> Type of object the renderer will render.
 */
public class DropDownChoiceForEntry
<L,T extends TranslatableEntry<?, ?, L>>
extends DropDownChoice<T>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new DropDownChoiceForEntryOwner object. 
	 *
	 * @param id Drop down choice id.
	 * @param model Model
	 * @param choices List of choices.
	 * @param session Current session.
	 */
	public DropDownChoiceForEntry
	(String id, IModel<T> model, List<? extends T> choices, Bo2WicketSession<?, L> session) {
		super(id, model, choices, new ChoiceRendererForEntry<L, T>(session));
		this.setOutputMarkupPlaceholderTag(true);
		setNullValid(true);
		SelfDrawnUtils.sortCachedEntries(this);
	}

	/**
	 * Creates a new DropDownChoiceForEntryOwner object. 
	 *
	 * @param id Drop down choice id.
	 * @param choices List of choices.
	 * @param session Current session.
	 */
	public DropDownChoiceForEntry
	(String id, List<? extends T> choices, Bo2WicketSession<?, L> session) {
		super(id, choices, new ChoiceRendererForEntry<L, T>(session));
		this.setOutputMarkupPlaceholderTag(true);
		setNullValid(true);
		SelfDrawnUtils.sortCachedEntries(this);
	}
	
	

}
