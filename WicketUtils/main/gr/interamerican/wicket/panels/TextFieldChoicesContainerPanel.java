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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.components.OnChangeSelfUpdatingBehavior;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Panel meant to be contained on {@link AbstractChoicesPanel}, that will
 * display a {@link TextField} for adding new values.
 * 
 * @param <T>
 *            Type of Entity
 */
public class TextFieldChoicesContainerPanel<T extends Serializable> extends Panel {

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
	 *            Current Selected Values
	 * @param clz
	 *            Class of the entity
	 */
	@SuppressWarnings("nls")
	public TextFieldChoicesContainerPanel(String id, ListModel<T> currentValues, Class<T> clz) {
		super(id);
		TextField<T> textFd = new TextField<T>("newValue", new Model<T>(), clz);
		textFd.add(new OnChangeSelfUpdatingBehavior());
		Label msg = new Label("selectValueFromListMsgId", StringConstants.EMPTY);
		AjaxLinkWithImagePanel addNewLink = new AjaxLinkWithImagePanel("addNewLink",
				new AddNewAction<>(currentValues, textFd, msg), EmbeddedImage.ADD_NEW);
		add(textFd, addNewLink, msg);
	}
}