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

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.utils.WicketUtils;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * Custom ChoiceRenderer for non translatable Entry.
 * 
 * @param <T>
 *            Type of typeSelectable object.
 * 
 */
public class ChoiceRendererForNonTranslatableEntry<T extends TypedSelectable<Long>>
implements IChoiceRenderer<T> {

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getIdValue(T typeSelectable, int index) {
		if (typeSelectable == null || typeSelectable.getCode() == null) {
			return null;
		}
		return typeSelectable.getCode().toString();
	}

	@Override
	public Object getDisplayValue(T typeSelectable) {
		return typeSelectable == null ? null
				: typeSelectable.getCode() + StringConstants.SPACE + StringConstants.MINUS + StringConstants.SPACE
						+ typeSelectable.getName();
	}

	@Override
	public T getObject(String id, IModel<? extends List<? extends T>> choices) {
		return WicketUtils.getObject(this, id, choices);
	}
}