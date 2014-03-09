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
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;

import java.util.Properties;

/**
 * Panel for invoking a batch process.
 */
public class BatchProcessInputPanel extends BPanelForMap<Properties> {
	
	/**
	 * serial version id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new BatchProcessInputPanel object. 
	 *
	 * @param model
	 *        Properties model object.
	 * @param editable 
	 *        Specifies if the properties will be editable.
	 */
	public BatchProcessInputPanel(Properties model, boolean editable) {
		super(model,BatchProcessParmNames.FIELDS,false, editable);
	}
	
	/**
	 * Creates a new non editable BatchProcessInputPanel object. 
	 *
	 * @param model 
	 */
	public BatchProcessInputPanel(Properties model) {
		this(model,false);
	}
	
	@Override
	protected TextFieldProperties componentProperties() {
		boolean editable = isEditable();	
		TextFieldProperties tfp = super.componentProperties();
		tfp.setEditable(editable);
		tfp.setEnabled(editable);
		return tfp;
	}
	
	/**
	 * Indicates if the panel is editable.
	 * 
	 * @return Returns true if the panel is editable.
	 */
	boolean isEditable() {
		return (Boolean) paintParameter;
	}
}
