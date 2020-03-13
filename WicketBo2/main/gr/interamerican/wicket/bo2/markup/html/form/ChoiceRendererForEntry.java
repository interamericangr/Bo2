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

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * {@link IChoiceRenderer} for {@link TranslatableEntry} objects.
 * 
 * This implementation of choice renderer will print the language
 * of the current session.
 * 
 * @param <L> Type of language id.
 * @param <T> Type of object the renderer will render.
 */
public class ChoiceRendererForEntry<L, T extends TranslatableEntry<?, ?, L>> 
implements IChoiceRenderer<T> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Language used by this renderer.
	 */
	protected L languageId;
	
	/**
	 * Creates a new ChoiceRendererForEnums object. 
	 * 
	 * @param session Current session.
	 */
	public ChoiceRendererForEntry(Bo2WicketSession<?, L> session){
		this.languageId = session.getLanguageId();
	}

	@Override
	public Object getDisplayValue(T object) {
		return object==null ? null : object.getTranslation(languageId); 
	}

	@Override
	public String getIdValue(T object, int index) {
		if(object==null || object.getCode()==null) {
			return null;
		}
		return object.getCode().toString();
	}

	@Override
	public T getObject(String id, IModel<? extends List<? extends T>> choices) {
		return WicketUtils.getObject(this, id, choices);
	}
}