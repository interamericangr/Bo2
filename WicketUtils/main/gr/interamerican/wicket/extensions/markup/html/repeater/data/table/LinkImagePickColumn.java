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

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.wicket.panels.LinkWithImagePanel;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * An {@link IColumn} that contains an {@link LinkWithImagePanel}.
 * 
 * @param <T>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sort property
 */
public class LinkImagePickColumn<T, S> extends AbstractColumn<T, S> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Image used on the column
	 */
	final ResourceReference reference;

	/**
	 * Action Executed when the image is clicked
	 */
	private final SerializableConsumer<T> consumer;

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param image
	 *            Image used on the column
	 * @param consumer
	 *            Action Executed when the image is clicked
	 */
	public LinkImagePickColumn(IModel<String> displayModel, EmbeddedImage image, SerializableConsumer<T> consumer) {
		this(displayModel, image.getReference(), consumer);
	}

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param reference
	 *            Reference for the Image used on the column
	 * @param consumer
	 *            Action Executed when the image is clicked
	 */
	public LinkImagePickColumn(IModel<String> displayModel, ResourceReference reference, SerializableConsumer<T> consumer) {
		super(displayModel);
		this.reference = reference;
		this.consumer = consumer;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new LinkWithImagePanel(componentId, rowModel, consumer, reference));
	}
}