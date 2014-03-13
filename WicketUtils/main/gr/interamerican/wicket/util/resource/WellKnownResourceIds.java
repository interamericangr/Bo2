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
package gr.interamerican.wicket.util.resource;

import gr.interamerican.wicket.markup.html.panel.FilePanel;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanel;


/**
 * Well-known resource IDs for components of ServicePanels that
 * can use them (e.g. buttons or labels).
 */
@SuppressWarnings("nls")
public class WellKnownResourceIds {
	
	/**
	 * Upload button of {@link FilePanel}.
	 */
	public static final String FP_UPLOAD_BTN_LABEL = "fp_uploadButtonLabel";
	
	/**
	 * Select column label of data table with radios
	 */
	public static final String PDT_SELECT_LABEL = "pdt_selectLabel";
	
	/**
	 * Select column label of data table with check boxes
	 */
	public static final String MSDT_SELECT_LABEL = "msdt_selectLabel";
	
	/**
	 * Back button label of {@link ServicePanelWithBack}.
	 */
	public static final String SPWB_BACK_BTN_LABEL = "spwb_backButtonLabel";
	
	/**
	 * Label on top of list table of a {@link ListTablePanel}.
	 */
	public static final String LTP_LIST_TABLE_LABEL = "ltp_listTableLabel";
	
	/**
	 * Select button label of {@link PickerPanel}.
	 */
	public static final String PP_SELECT_BTN_LABEL = "pp_selectButtonLabel";
	
	/**
	 * Second select button label of {@link PickerPanel}.
	 */
	public static final String PP_2ND_SELECT_BTN_LABEL = "pp_2ndSelectButtonLabel";
	
	/**
	 * Select button label of {@link MultipleSelectionsPanel}.
	 */
	public static final String MSP_SELECT_BTN_LABEL = "msp_selectButtonLabel";
	
	/**
	 * Second select button label of {@link MultipleSelectionsPanel}.
	 */
	public static final String MSP_2ND_SELECT_BTN_LABEL = "msp_2ndSelectButtonLabel";
	
	/**
	 * Label of the select/deselect all checkbox of {@link MultipleSelectionsPanel}.
	 */
	public static final String MSP_CHECKGROUP_SELECTOR_LABEL = "msp_checkGroupSelectorLabel";
	
	/**
	 * Execute button label of a {@link SingleBeanPanel}.
	 */
	public static final String SBP_EXECUTE_BTN_LABEL = "sbp_executeButtonLabel";
	
	/**
	 * Clear button label of a {@link SingleBeanPanel}.
	 */
	public static final String SBP_CLEAR_BTN_LABEL = "sbp_clearButtonLabel";
	
	/**
	 * Panel label of a {@link SingleBeanPanel}.
	 */
	public static final String SBP_PANEL_LABEL = "sbp_panelLabel";

	/**
	 * New bean button label of {@link CrudPickerPanel}.
	 */
	public static final String CPP_NEW_BTN_LABEL = "cpp_newButtonLabel";
	
	/**
	 * Edit bean button label of {@link CrudPickerPanel}.
	 */
	public static final String CPP_EDIT_BTN_LABEL = "cpp_editButtonLabel";
	
	/**
	 * Delete bean button label of {@link CrudPickerPanel}.
	 */
	public static final String CPP_DELETE_BTN_LABEL = "cpp_deleteButtonLabel";
	
	/**
	 * View bean button label of {@link CrudPickerPanel}.
	 */
	public static final String CPP_VIEW_BTN_LABEL = "cpp_viewButtonLabel";
	
	/**
	 * Back button label of {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String CPP_SBP_BACK_BTN_LABEL = "cpp_sbp_backButtonLabel";
	
	/**
	 * Panel label of {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String CPP_SBP_PANEL_LABEL = "cpp_sbp_panelLabel";
	
	/**
	 * Clear button label of {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String CPP_SBP_CLEAR_BTN_LABEL = "cpp_sbp_clearButtonLabel";
	
	/**
	 * Save button label of {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String CPP_SBP_SAVE_BTN_LABEL = "cpp_sbp_saveButtonLabel";
	
	/**
	 * Update button label of {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String CPP_SBP_UPDATE_BTN_LABEL = "cpp_sbp_updateButtonLabel";
	
	/**
	 * Execute query button label of {@link SearchFlowPanel} criteria {@link SingleBeanPanel}.
	 */
	public static final String SFP_SBP_EXECUTE_QUERY_BTN_LABEL = "sfp_sbp_executeQueryButtonLabel";
	
	/**
	 * Clear query criteria button label of {@link SearchFlowPanel} criteria {@link SingleBeanPanel}.
	 */
	public static final String SFP_SBP_CLEAR_CRITERIA_BTN_LABEL = "sfp_sbp_clearCriteriaButtonLabel";
	
	/**
	 * Panel label of {@link SearchFlowPanel} criteria {@link SingleBeanPanel}.
	 */
	public static final String SFP_SBP_CRITERIA_PANEL_LABEL = "sfp_sbp_criteriaPanelLabel";
	
	/**
	 * Label of buck button of {@link SearchFlowPanel} {@link ListTablePanel}.
	 */
	public static final String  SFP_LTP_BACK_BTN_LABEL = "sfp_ltp_backButtonLabel";
	
	/**
	 * Label on top of list table of {@link SearchFlowPanel} {@link ListTablePanel}.
	 */
	public static final String  SFP_LTP_LIST_TABLE_LABEL = "sfp_ltp_listTableLabel";
	
	/**
	 * Select button label of {@link SearchFlowPanel} {@link PickerPanel}.
	 */
	public static final String  SFP_PP_SELECT_BTN_LABEL = "sfp_pp_selectButtonLabel";
	
	/**
	 * Second select button label of {@link SearchFlowPanel} {@link PickerPanel}.
	 */
	public static final String  SFP_PP_2ND_SELECT_BTN_LABEL = "sfp_pp_2ndSelectButtonLabel";
	
	/**
	 * Select button label of {@link SearchFlowPanel} {@link MultipleSelectionsPanel}.
	 */
	public static final String  SFP_MSP_SELECT_BTN_LABEL = "sfp_msp_selectButtonLabel";
	
	/**
	 * Label of the select/deselect all checkbox of {@link SearchFlowPanel} {@link MultipleSelectionsPanel}.
	 */
	public static final String SFP_MSP_CHECKGROUP_SELECTOR_LABEL = "sfp_msp_checkGroupSelectorLabel";

	/**
	 * New bean button label of {@link SearchFlowPanel} {@link CrudPickerPanel}.
	 */
	public static final String SFP_CPP_NEW_BTN_LABEL = "sfp_cpp_newButtonLabel";
	
	/**
	 * Edit bean button label of {@link SearchFlowPanel} {@link CrudPickerPanel}.
	 */
	public static final String SFP_CPP_EDIT_BTN_LABEL = "sfp_cpp_editButtonLabel";
	
	/**
	 * Delete bean button label of {@link SearchFlowPanel} {@link CrudPickerPanel}.
	 */
	public static final String SFP_CPP_DELETE_BTN_LABEL = "sfp_cpp_deleteButtonLabel";
	
	/**
	 * View bean button label of {@link SearchFlowPanel} {@link CrudPickerPanel}.
	 */
	public static final String SFP_CPP_VIEW_BTN_LABEL = "sfp_cpp_viewButtonLabel";
	
	/**
	 * Back button label of {@link SearchFlowPanel} {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String SFP_CPP_SBP_BACK_BTN_LABEL = "sfp_cpp_sbp_backButtonLabel";
	
	/**
	 * Panel label of {@link SearchFlowPanel} {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String SFP_CPP_SBP_PANEL_LABEL = "sfp_cpp_sbp_panelLabel";
	
	/**
	 * Clear button label of {@link SearchFlowPanel} {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String SFP_CPP_SBP_CLEAR_BTN_LABEL = "sfp_cpp_sbp_clearButtonLabel";
	
	/**
	 * Save button label of {@link SearchFlowPanel} {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String SFP_CPP_SBP_SAVE_BTN_LABEL = "sfp_cpp_sbp_saveButtonLabel";
	
	/**
	 * Update button label of {@link SearchFlowPanel} {@link CrudPickerPanel} {@link SingleBeanPanel}.
	 */
	public static final String SFP_CPP_SBP_UPDATE_BTN_LABEL = "sfp_cpp_sbp_updateButtonLabel";

}
