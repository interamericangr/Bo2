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

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDefImpl;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link PickerPanelDef}.
 * 
 * @param <B> 
 */
public class PickerPanelDefImpl<B extends Serializable>
extends ListTablePanelDefImpl<B>
implements PickerPanelDef<B> {
	
	/** serial id. */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * item selected {@link CallbackAction}
	 */ 
	private CallbackAction itemSelectedAction;
	
	/**
	 * itemSelectedAction authorization flag.
	 */
	private Flag itemSelectedActionFlag;
	
	/** 
	 * Second item selected {@link CallbackAction}
	 */ 
	private CallbackAction secondItemSelectedAction;
	
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

	public CallbackAction getItemSelectedAction() {
		return this.itemSelectedAction;
	}

	public void setItemSelectedAction(CallbackAction itemSelectedAction) {
		this.itemSelectedAction = itemSelectedAction;		
	}

	public IModel<B> getBeanModel() {
		return model;
	}

	public void setBeanModel(IModel<B> model) {
		this.model = model;
	}

	public CallbackAction getSecondItemSelectedAction() {
		return secondItemSelectedAction;
	}

	public void setSecondItemSelectedAction(CallbackAction itemSelectedAction) {
		this.secondItemSelectedAction = itemSelectedAction;
	}

	public IModel<String> getSelectLabelModel() {
		return selectLabelModel;
	}

	public void setSelectLabelModel(IModel<String> label) {
		this.selectLabelModel = label;
	}

	public IModel<String> getSecondSelectLabelModel() {
		return secondSelectLabelModel;
	}

	public void setSecondSelectLabelModel(IModel<String> label) {
		this.secondSelectLabelModel = label;
	}

	public Flag getItemSelectedActionFlag() {
		return itemSelectedActionFlag;
	}

	public void setItemSelectedActionFlag(Flag itemSelectedActionFlag) {
		this.itemSelectedActionFlag = itemSelectedActionFlag;
	}

	public Flag getSecondItemSelectedActionFlag() {
		return secondItemSelectedActionFlag;
	}

	public void setSecondItemSelectedActionFlag(Flag secondItemSelectedActionFlag) {
		this.secondItemSelectedActionFlag = secondItemSelectedActionFlag;
	}

	public Boolean getRefreshListAfterPickAction() {
		return refreshListAfterPickAction;
	}

	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction) {
		this.refreshListAfterPickAction = refreshListAfterPickAction;
	}

}
