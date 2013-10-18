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

import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.DataTableAjaxLinkPanel;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.utils.ImageType;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * A column that has an ajax link that executes a callback. 
 * 
 * @param <T> 
 */
public class CallbackAjaxLinkColumn<T> extends AbstractColumn<T>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Callback action.
	 */
	private CallbackAction action; 
	
	/**
	 * Image type.
	 */
	private ImageType imageType;
	
	/**
	 * Row T instance holder.
	 */
	BeanPanelDef<T> rowHolder;

	/**
	 * Creates a new CallbackAjaxLinkColumn object. 
	 * 
	 * @param model
	 *        Column label resource model. 
	 * @param action
	 *        Callback action.
	 * @param imageType
	 *        Type of image.
	 * @param rowHolder
	 *        Holder of current row model, that the callback action may use to gain access
	 *        to the current row T instance.
	 */
	public CallbackAjaxLinkColumn(IModel<String> model, CallbackAction action, ImageType imageType, BeanPanelDef<T> rowHolder) {
		super(model);
		this.action = action;
		this.imageType = imageType;
		this.rowHolder = rowHolder;
	}

	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, final IModel<T> rowModel) {
		
		Panel cell = new DataTableAjaxLinkPanel<T>(componentId, rowModel, imageType) {
			
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onLinkClicked(AjaxRequestTarget target) {
				rowHolder.getBeanModel().setObject(rowModel.getObject());
				action.callBack(target);
			}
			
		};
		
		cellItem.add(cell);
	}
	
}
