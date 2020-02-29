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

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.wicket.panels.LinkPanel;

/**
 * An {@link IColumn} that contains an {@link LinkPanel}.
 * 
 * @param <T>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sort property
 */
public class LinkPickColumn<T, S> extends AbstractColumn<T, S> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link's body model (displayed)
	 */
	private final IModel<?> bodyModel;

	/**
	 * Action Executed when the link is clicked
	 */
	private final SerializableConsumer<T> consumer;

	/**
	 * Behaviors Added to the {@link Link} Embedded in the Panel
	 */
	private final Behavior[] behaviors;

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param consumer
	 *            Action Executed when the link is clicked
	 * @param behaviors
	 *            Behaviors Added to the {@link Link} Embedded in the Panel
	 */
	public LinkPickColumn(IModel<String> displayModel, IModel<?> bodyModel, SerializableConsumer<T> consumer,
			Behavior... behaviors) {
		super(displayModel);
		this.bodyModel = bodyModel;
		this.consumer = consumer;
		this.behaviors = behaviors;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new LinkPanel(componentId, rowModel, consumer, bodyModel, behaviors));
	}
}