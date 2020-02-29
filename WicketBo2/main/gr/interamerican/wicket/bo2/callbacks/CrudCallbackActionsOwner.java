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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * An interface that has methods for panels or pages to implement that represent
 * the crud actions (except read) that are used by the {@link CrudPickerPanel}.
 * 
 * @deprecated Due to changed interfaces of actions
 */
@Deprecated
public interface CrudCallbackActionsOwner extends Serializable {

	/**
	 * Save Action method Name.
	 */
	public static final String SAVE_ACTION = "saveAction"; //$NON-NLS-1$

	/**
	 * Update Action method Name..
	 */
	public static final String UPDATE_ACTION = "updateAction"; //$NON-NLS-1$

	/**
	 * Delete Action method Name..
	 */
	public static final String DELETE_ACTION = "deleteAction"; //$NON-NLS-1$

	/**
	 * Action to handle the save action of the {@link CrudPickerPanel}.
	 *
	 * @param target            {@link AjaxRequestTarget} provided for this call
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	void saveAction(AjaxRequestTarget target)  throws LogicException, DataException;

	/**
	 * 	/**
	 * Action to handle the update action of the {@link CrudPickerPanel}.
	 *
	 * @param target            {@link AjaxRequestTarget} provided for this call
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	void updateAction(AjaxRequestTarget target) throws LogicException, DataException ;
	
	/**
	 * 	/**
	 * Action to handle the delete action of the {@link CrudPickerPanel}.
	 *
	 * @param target            {@link AjaxRequestTarget} provided for this call
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	void deleteAction(AjaxRequestTarget target) throws LogicException, DataException;
}