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

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDefImpl;

/**
 * Implementation of {@link PickerPanelDef}.
 *
 * @param <B> the generic type
 */
public class PickerPanelDefImpl<B extends Serializable>
extends ListTablePanelDefImpl<B>
implements PickerPanelDef<B> {
	
	/** serial id. */
	private static final long serialVersionUID = 1L;
	
	/**   item selected {@link PickAction}. */ 
	private PickAction<B> itemSelectedAction;
	
	/**
	 * itemSelectedAction authorization flag.
	 */
	private Flag itemSelectedActionFlag;
	
	/**   Second item selected {@link PickAction}. */ 
	private PickAction<B> secondItemSelectedAction;
	
	/**
	 * secondItemSelectedActionFlag authorization flag.
	 */
	private Flag secondItemSelectedActionFlag;
	
	/**
	 * Label for the select button.
	 */
	private IModel<String> selectLabelModel;
	
	/**
	 * Label for the second select button.
	 */
	private IModel<String> secondSelectLabelModel;
	
	/**
	 * Model.
	 */
	private IModel<B> model;
	
	/**
	 * RefreshListAfterPickAction.
	 */
	private Boolean refreshListAfterPickAction;

	@Override
	public PickAction<B> getItemSelectedAction() {
		return this.itemSelectedAction;
	}

	@Override
	public void setItemSelectedAction(PickAction<B> itemSelectedAction) {
		this.itemSelectedAction = itemSelectedAction;		
	}

	@Override
	public IModel<B> getBeanModel() {
		return model;
	}

	@Override
	public void setBeanModel(IModel<B> model) {
		this.model = model;
	}

	@Override
	public PickAction<B> getSecondItemSelectedAction() {
		return secondItemSelectedAction;
	}

	@Override
	public void setSecondItemSelectedAction(PickAction<B> itemSelectedAction) {
		this.secondItemSelectedAction = itemSelectedAction;
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
	public IModel<String> getSecondSelectLabelModel() {
		return secondSelectLabelModel;
	}

	@Override
	public void setSecondSelectLabelModel(IModel<String> label) {
		this.secondSelectLabelModel = label;
	}

	@Override
	public Flag getItemSelectedActionFlag() {
		return itemSelectedActionFlag;
	}

	@Override
	public void setItemSelectedActionFlag(Flag itemSelectedActionFlag) {
		this.itemSelectedActionFlag = itemSelectedActionFlag;
	}

	@Override
	public Flag getSecondItemSelectedActionFlag() {
		return secondItemSelectedActionFlag;
	}

	@Override
	public void setSecondItemSelectedActionFlag(Flag secondItemSelectedActionFlag) {
		this.secondItemSelectedActionFlag = secondItemSelectedActionFlag;
	}

	@Override
	public Boolean getRefreshListAfterPickAction() {
		return refreshListAfterPickAction;
	}

	@Override
	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction) {
		this.refreshListAfterPickAction = refreshListAfterPickAction;
	}
}