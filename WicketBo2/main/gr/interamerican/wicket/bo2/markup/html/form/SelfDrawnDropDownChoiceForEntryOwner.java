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

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Self-drawn {@link DropDownChoiceForEntryOwner}.
 * 
 * @param <L> 
 *        Type of language id.
 * @param <T>
 *        Type of object the renderer will render.
 */
public class SelfDrawnDropDownChoiceForEntryOwner
<L, T extends TranslatableEntryOwner<?, ?, L>> 
extends DropDownChoiceForEntryOwner<L, T>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new SelfDrawnDropDownChoiceForEntryOwner object. 
	 *
	 * @param id
	 * @param descriptor 
	 * @param choices
	 * @param session
	 */
	public SelfDrawnDropDownChoiceForEntryOwner(String id, 
	CachedEntryOwnerBoPropertyDescriptor<T, ?> descriptor, List<? extends T> choices, Bo2WicketSession<?, L> session) {
		super(id, new Model<T>(), choices, session);
		SelfDrawnUtils.<T>standardSelfDrawnFormComponentStuff(this, descriptor);
	}
	
	/**
	 * Creates a new SelfDrawnDropDownChoiceForEntryOwner object. 
	 *
	 * @param id Drop down choice id.
	 * @param descriptor 
	 * @param model
	 * @param choices List of choices.
	 * @param session Current session.
	 */
	public SelfDrawnDropDownChoiceForEntryOwner(String id, IModel<T> model,
	CachedEntryOwnerBoPropertyDescriptor<T, ?> descriptor, List<? extends T> choices, Bo2WicketSession<?, L> session) {
		super(id, model, choices, session);
		SelfDrawnUtils.<T>standardSelfDrawnFormComponentStuff(this, descriptor);
	}

	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SELECT);
		 tag.put(MarkupConstants.STYLE, MarkupConstants.WIDTH);
		super.onComponentTag(tag);
	}
	
}
