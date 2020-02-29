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
package gr.interamerican.wicket.markup.html.panel.picker;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.MultiplePickAction;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDefImpl;

/**
 * Implementation of {@link MultipleSelectionsPanelDef}.
 *
 * @param <B> the generic type
 */
public class MultipleSelectionsPanelDefImpl<B extends Serializable>
extends ListTablePanelDefImpl<B>
implements MultipleSelectionsPanelDef<B> {
	
	/** serial id. */
	private static final long serialVersionUID = 1L;
	
	/**   item selected {@link MultiplePickAction}. */ 
	private MultiplePickAction<B> itemsSelectedAction;
	
	/**
	 * itemSelectedAction authorization flag.
	 */
	private Flag itemsSelectedActionFlag;
	
	/** second items selected {@link MultiplePickAction}. */
	private MultiplePickAction<B> secondItemsSelectedAction;
	
	/**
	 * secondItemsSelectedAction authorization flag.
	 */
	private Flag secondItemsSelectedActionFlag;
	
	/**
	 * Model.
	 */
	private IModel<ArrayList<B>> selectionsModel;
	
	/**
	 * Select button label.
	 */
	private IModel<String> selectLabelModel;
	
	/**
	 * 2nd select button label.
	 */
	private IModel<String> secondSelectLabelModel;
	
	/** Select/Deselect all checkbox label. */
	private IModel<String> checkGroupSelectorLabelModel;

	@Override
	public MultiplePickAction<B> getItemsSelectedAction() {
		return this.itemsSelectedAction;
	}

	@Override
	public void setItemsSelectedAction(MultiplePickAction<B> itemsSelectedAction) {
		this.itemsSelectedAction = itemsSelectedAction;		
	}

	@Override
	public IModel<String> getSelectLabelModel() {
		return selectLabelModel;
	}

	@Override
	public void setSelectLabelModel(IModel<String> label) {
		this.selectLabelModel = label;
	}

	@Override
	public Flag getItemsSelectedActionFlag() {
		return itemsSelectedActionFlag;
	}

	@Override
	public void setItemsSelectedActionFlag(Flag itemsSelectedActionFlag) {
		this.itemsSelectedActionFlag = itemsSelectedActionFlag;
	}

	@Override
	public MultiplePickAction<B> getSecondItemsSelectedAction() {
		return secondItemsSelectedAction;
	}

	@Override
	public void setSecondItemsSelectedAction(MultiplePickAction<B> secondItemsSelectedAction) {
		this.secondItemsSelectedAction = secondItemsSelectedAction;
	}

	@Override
	public Flag getSecondItemsSelectedActionFlag() {
		return secondItemsSelectedActionFlag;
	}

	@Override
	public void setSecondItemsSelectedActionFlag(Flag secondItemsSelectedActionFlag) {
		this.secondItemsSelectedActionFlag = secondItemsSelectedActionFlag;
	}

	@Override
	public IModel<ArrayList<B>> getSelectionsModel() {
		return selectionsModel;
	}

	@Override
	public void setSelectionsModel(IModel<ArrayList<B>> selectionsModel) {
		this.selectionsModel = selectionsModel;
	}

	@Override
	public IModel<String> getCheckGroupSelectorLabelModel() {
		return checkGroupSelectorLabelModel;
	}

	@Override
	public void setCheckGroupSelectorLabelModel(IModel<String> label) {
		this.checkGroupSelectorLabelModel = label;
	}

	@Override
	public IModel<String> getSecondSelectLabelModel() {
		return secondSelectLabelModel;
	}

	@Override
	public void setSecondSelectLabelModel(IModel<String> secondSelectLabelModel) {
		this.secondSelectLabelModel = secondSelectLabelModel;
	}
}