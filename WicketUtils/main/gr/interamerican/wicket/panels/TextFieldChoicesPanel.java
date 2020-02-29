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
package gr.interamerican.wicket.panels;

import java.io.Serializable;
import java.util.Set;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * 
 * An {@link AbstractChoicesPanel} that uses a
 * {@link TextFieldChoicesContainerPanel}. This makes the assumption we want to
 * allow the user to input values on the set with a {@link TextField}.
 * 
 * @param <T>
 *            Type of Entity
 */
public class TextFieldChoicesPanel<T extends Serializable> extends AbstractChoicesPanel<T> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param currentValues
	 *            Set to be updated on submit
	 * @param labelValue
	 *            Label on top of the panel
	 * @param clz
	 *            Class of the entity
	 */
	public TextFieldChoicesPanel(String id, IModel<Set<T>> currentValues, String labelValue, Class<T> clz) {
		super(id, labelValue, currentValues, (i, l) -> new TextFieldChoicesContainerPanel<>(i, l, clz));
	}
}