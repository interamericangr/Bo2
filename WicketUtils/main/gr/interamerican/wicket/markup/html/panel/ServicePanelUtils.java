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
package gr.interamerican.wicket.markup.html.panel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;
import gr.interamerican.wicket.utils.WicketUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Button;

/**
 * Utilities for {@link ServicePanel}s.
 */
public class ServicePanelUtils {
	
	/**
	 * The wicket ids of the buttons of a {@link CrudPickerPanel}.
	 */
	private static List<String> CRUD_PANEL_BUTTON_WICKET_IDS = new ArrayList<String>();
	
	static {
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.NEW_BUTTON_ID);
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.EDIT_BUTTON_ID);
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.DELETE_BUTTON_ID);
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.SELECT_BUTTON_ID);
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.SECOND_SELECT_BUTTON_ID);
		CRUD_PANEL_BUTTON_WICKET_IDS.add(CrudPickerPanel.BACK_BUTTON_ID);
	}

	/**
	 * Disables a button, based on the disableUnauthorizedButtons setting 
	 * of the panel definition and the flag for this button's action.
	 * 
	 * @param def
	 * @param flag
	 * @param button 
	 */
	public static void disableButton(ServicePanelDef def, Flag flag, Button button) {
		if(def.getDisableUnauthorizedButtons() && !authorizedByFlag(flag)) {
			button.setEnabled(false);
		}
	}
	
	/**
	 * Indicates if a {@link Flag} grants authorization to the user.
	 * 
	 * @param flag
	 * 
	 * @return True, if the flag.isUp is true.
	 */
	public static boolean authorizedByFlag(Flag flag) {
		if(flag == null) { return true; }
		return flag.isUp();
	}
	
	/**
	 * Hides the new, edit and delete buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform any CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 */
	public static <B extends Serializable> void hideCrudButtons(CrudPickerPanel<B> cpp) {
		String deletePath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.DELETE_BUTTON_ID);
		String editPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.EDIT_BUTTON_ID);
		String newPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.NEW_BUTTON_ID);
		WicketUtils.setVisible(false, cpp.get(deletePath), cpp.get(editPath), cpp.get(newPath));
	}
	
	/**
	 * Hides the specified CRUD buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform some CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 * @param modes
	 *        Modes for which to hide buttons.
	 */
	public static <B extends Serializable> void hideCrudButtons(CrudPickerPanel<B> cpp, PanelCreatorMode... modes) {
		for(PanelCreatorMode mode : modes) {
			if(mode==PanelCreatorMode.CREATE) {
				String newPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.NEW_BUTTON_ID);
				WicketUtils.setVisible(false, cpp.get(newPath));
			}
			if(mode==PanelCreatorMode.EDIT) {
				String editPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.EDIT_BUTTON_ID);
				WicketUtils.setVisible(false, cpp.get(editPath));
			}
			if(mode==PanelCreatorMode.DELETE) {
				String deletePath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.DELETE_BUTTON_ID);
				WicketUtils.setVisible(false, cpp.get(deletePath));
			}
 		}
	}
	
	/**
	 * Hides the specified CRUD buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform some CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 * @param buttonWicketIds for which to hide buttons
	 */
	public static <B extends Serializable> void hideCrudButtons(CrudPickerPanel<B> cpp, String... buttonWicketIds) {
		List<Component> components = new ArrayList<Component>(); 
		for (String id : buttonWicketIds) {
			if (CRUD_PANEL_BUTTON_WICKET_IDS.contains(id)) {
				String path = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, id);
				components.add(cpp.get(path));	
			}
		}
		Component[] cmpArray = new Component[components.size()];
		cmpArray = components.toArray(cmpArray);
		WicketUtils.setVisible(false, cmpArray);
	}
	
	/**
	 * Disables the new, edit and delete buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform any CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 */
	public static <B extends Serializable> void disableCrudButtons(CrudPickerPanel<B> cpp) {
		String deletePath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.DELETE_BUTTON_ID);
		String editPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.EDIT_BUTTON_ID);
		String newPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.NEW_BUTTON_ID);
		WicketUtils.setEnabled(false, cpp.get(deletePath), cpp.get(editPath), cpp.get(newPath));
	}
	
	/**
	 * Disables the specified CRUD buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform some CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 * @param modes
	 *        Modes for which to disable buttons.
	 */
	public static <B extends Serializable> void disableCrudButtons(CrudPickerPanel<B> cpp, PanelCreatorMode... modes) {
		for(PanelCreatorMode mode : modes) {
			if(mode==PanelCreatorMode.CREATE) {
				String newPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.NEW_BUTTON_ID);
				WicketUtils.setEnabled(false, cpp.get(newPath));
			}
			if(mode==PanelCreatorMode.EDIT) {
				String editPath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.EDIT_BUTTON_ID);
				WicketUtils.setEnabled(false, cpp.get(editPath));
			}
			if(mode==PanelCreatorMode.DELETE) {
				String deletePath = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, CrudPickerPanel.DELETE_BUTTON_ID);
				WicketUtils.setEnabled(false, cpp.get(deletePath));
			}
 		}
	}
	
	/**
	 * Disables the specified CRUD buttons of a {@link CrudPickerPanel}.
	 * This is useful when a nested crud picker panel in view mode must be able
	 * to show its beans, but not perform some CRUD operations on them.
	 * 
	 * @param <B>
	 *        Type of panel bean.
	 * @param cpp
	 *        CrudPickerPanel
	 * @param buttonWicketIds
	 *        Wickets ids of the buttons to disable.
	 */
	public static <B extends Serializable> void disableCrudButtons(CrudPickerPanel<B> cpp, String... buttonWicketIds) {
		List<Component> components = new ArrayList<Component>(); 
		for (String id : buttonWicketIds) {
			if (CRUD_PANEL_BUTTON_WICKET_IDS.contains(id)) {
				String path = WicketUtils.wicketPath(CrudPickerPanel.TABLE_FORM_ID, id);
				components.add(cpp.get(path));	
			}
		}
		Component[] cmpArray = new Component[components.size()];
		cmpArray = components.toArray(cmpArray);
		WicketUtils.setEnabled(false, cmpArray);
	}	
	
	/**
	 * Gets the form fields panel of the SingleBeanPanel of a CrudPickerPanel. You may invoke
	 * this in cases you know that the SingleBeanPanel is rendered, for example in save or
	 * update action implementation.
	 * 
	 * @param <B>
	 *        Type of bean.
	 * @param cpp
	 * 
	 * @return The form fields panel (showing a B) of the SingleBeanPanel of the CrudPickerPanel.
	 */
	public static <B extends Serializable> Component getFieldsPanel(CrudPickerPanel<B> cpp) {
		String path = WicketUtils.wicketPath(
			CrudPickerPanel.NEW_OR_EDIT_PANEL_ID, SingleBeanPanel.FORM_ID, SingleBeanPanel.FORM_FIELDS_PANEL_ID);
		return cpp.get(path);
	}
	
	/**
	 * Hidden. 
	 */
	public ServicePanelUtils() { /* empty */ }

}
