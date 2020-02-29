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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.panels.AjaxLinkWithImagePanel;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * An {@link IColumn} that contains an {@link AjaxLinkWithImagePanel}.
 * 
 * @param <T>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sort property
 */
public class AjaxLinkImagePickColumn<T, S> extends AbstractColumn<T, S> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Image used on the column
	 */
	private final ResourceReference reference;

	/**
	 * Action Executed when the image is clicked
	 */
	private final PickAction<T> pickAction;

	/**
	 * Creates a new AjaxLinkImagePickColumn object.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param image
	 *            Image used on the column
	 * @param pickAction
	 *            Action Executed when the image is clicked
	 */
	public AjaxLinkImagePickColumn(IModel<String> displayModel, EmbeddedImage image, PickAction<T> pickAction) {
		this(displayModel, image.getReference(), pickAction);
	}

	/**
	 * Creates a new AjaxLinkImagePickColumn object.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param reference
	 *            Reference for the Image used on the column
	 * @param pickAction
	 *            Action Executed when the image is clicked
	 */
	public AjaxLinkImagePickColumn(IModel<String> displayModel, ResourceReference reference, PickAction<T> pickAction) {
		super(displayModel);
		this.reference = reference;
		this.pickAction = pickAction;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new AjaxLinkWithImagePanel(componentId, rowModel, pickAction, reference));
	}
}