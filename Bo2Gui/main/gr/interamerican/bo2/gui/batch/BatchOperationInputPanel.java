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
package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.gui.components.BPanelForMap;
import gr.interamerican.bo2.gui.components.BTextField;
import gr.interamerican.bo2.impl.open.runtime.PropertiesLauncherParamsNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Panel for invoking a batch operation
 */
public class BatchOperationInputPanel
extends BPanelForMap<Properties> {

	/**
	 * serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All Disabled Fields
	 */
	static List<String> disabledFields;
	static {
		disabledFields = new ArrayList<String>();
		disabledFields.add(PropertiesLauncherParamsNames.CLASSNAME);
		disabledFields.add(PropertiesLauncherParamsNames.POST_PROCESSING_CLASS);
		disabledFields.add(PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS);
		disabledFields.add(PropertiesLauncherParamsNames.WITHGUI);
		disabledFields.addAll(Arrays.asList(BatchOperationParmNames.FIELDS));
	}

	/**
	 * Public Constructor
	 * 
	 * @param model
	 *            Properties model object
	 */
	public BatchOperationInputPanel(Properties model) {
		super(model, disabledFields.toArray(new String[disabledFields.size()]), false);
		for (String field : disabledFields) {
			BTextField textField = getTextField(field);
			if (textField != null) {
				textField.setEnabled(false);
			}
		}
	}
}