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

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * {@link IChoiceRenderer} for {@link TranslatableEntryOwner} objects.<br>
 * This implementation of choice renderer will print the language of the current
 * session.
 * 
 * @param <L>
 *            Type of language id.
 * @param <T>
 *            Type of object the renderer will render.
 */
public class ChoiceRendererForEntryOwner<L, T extends TranslatableEntryOwner<?, ?, L>> implements IChoiceRenderer<T> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Language used by this renderer.
	 */
	protected L languageId;

	/**
	 * Creates a new ChoiceRendererForEntryOwner object.
	 *
	 * @param session
	 *            the session
	 */
	public ChoiceRendererForEntryOwner(Bo2WicketSession<?, L> session) {
		this.languageId = session.getLanguageId();
	}

	@Override
	public Object getDisplayValue(T object) {
		if (object == null || object.getEntry() == null) {
			return null;
		}
		return object.getEntry().getTranslation(languageId);
	}

	@Override
	public String getIdValue(T object, int index) {
		if (object == null || object.getEntry() == null || object.getEntry().getCode() == null) {
			return null;
		}
		return object.getEntry().getCode().toString();
	}

	@Override
	public T getObject(String id, IModel<? extends List<? extends T>> choices) {
		return WicketUtils.getObject(this, id, choices);
	}
}