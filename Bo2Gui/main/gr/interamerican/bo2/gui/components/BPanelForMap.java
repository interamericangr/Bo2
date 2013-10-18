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
package gr.interamerican.bo2.gui.components;

import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Utils;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Panel for invoking a batch process.
 * 
 * @param <T> 
 * 
 *
 */
public class BPanelForMap<T extends Map<Object,Object>> 
extends BPanel<T> {
	
	/**
	 * serial version id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array with the property names.
	 * Specifies the order of the fields in the panel.
	 */
	String[] propertyNames;
	
	/**
	 * Specifies if only panel contains only the fields specified by
	 * <code>propertyNames</code> or all properties. If this field
	 * is true, then the panel will contain only the fields specified by 
	 * propertyNames. Otherwise, the panel will contain all fields
	 * contained in the model properties object.
	 * 
	 */
	boolean onlyPredefined;
	
	/**
	 * Creates a new BatchProcessInputPanel object. 
	 *
	 * @param model
	 *        Model Map. 
	 * @param propertyNames 
	 *         Array with the property names.
	 *         Specifies the order of the fields in the panel.
	 * @param onlyPredefined 
	 *        Specifies if only panel contains only the fields specified by
	 *        <code>propertyNames</code> or all properties. If this field
	 *        is true, then the panel will contain only the fields specified by 
	 *        propertyNames. Otherwise, the panel will contain all fields
	 *        contained in the model properties object.
	 * @param paintParameter 
	 */
	protected BPanelForMap
	(T model, String[] propertyNames, boolean onlyPredefined, Object paintParameter) {
		super();
		this.paintParameter = paintParameter;
		this.model = model;		
		if (propertyNames==null) {
			this.propertyNames = new String[0];		
		} else {
			this.propertyNames = propertyNames;
		}
		this.onlyPredefined = onlyPredefined;		
		paint();
		model2panel();
	}
	
	/**
	 * Creates a new BatchProcessInputPanel object. 
	 *
	 * @param model
	 *        Model Map. 
	 * @param propertyNames 
	 *         Array with the property names.
	 *         Specifies the order of the fields in the panel.
	 * @param onlyPredefined 
	 *        Specifies if only panel contains only the fields specified by
	 *        <code>propertyNames</code> or all properties. If this field
	 *        is true, then the panel will contain only the fields specified by 
	 *        propertyNames. Otherwise, the panel will contain all fields
	 *        contained in the model properties object.
	 */
	public BPanelForMap(T model, String[] propertyNames, boolean onlyPredefined) {
		this(model,propertyNames,onlyPredefined,null);
	}
	
	/**
	 * Creates a new BatchProcessInputPanel object. 
	 *
	 * @param model
	 */
	public BPanelForMap(T model) {
		this(model,null,false);
	}
	
	


	/**
	 * Component properties.
	 * 
	 * @return Returns a ComponentProperties object.
	 */
	protected TextFieldProperties componentProperties() {
		TextFieldProperties properties = Factory.create(TextFieldProperties.class);
		properties.setEnabled(true);
		properties.setEditable(true);
		properties.setColumns(60);
		properties.setHasLabel(true);
		properties.setLabelLength(15);
		return properties;
	}
	
	
	/**
	 * Creates an array that contains all field names that must be drawn. <br/>
	 * 
	 * This array contains all fields specified by the <code>propertyNames</code> 
	 * array. If <code>onlyPredefined</code> is <code>false</code> then it also
	 * contains the rest properties of the model object.
	 * 
	 *  
	 * @return Returns an array that contains all field names that must be drawn.

	 */	
	protected String[] propertiesToDraw() {
		if (onlyPredefined) {
			return propertyNames;
		}		
		Set<String> keyset = Utils.cast(model.keySet());
		Set<String> additional = new HashSet<String>(keyset);
		Set<String> predefined = CollectionUtils.addAll(new HashSet<String>(), propertyNames);
		additional.removeAll(predefined);
		if (additional.isEmpty()) {
			return propertyNames;
		}
		int additionalCount = additional.size();
		int capacity = additionalCount + propertyNames.length;
		String[] all = ArrayUtils.enforceCapacity(propertyNames, capacity);
		int i=propertyNames.length;
		for (String string : additional) {
			all[i] = string;
			i++;
		}
		return all;
	}
	
	@Override
	public void paint() {
		super.paint();
		String[] fields = propertiesToDraw();		
		addModelBoundTextFields(fields, componentProperties());
		Dimension size = Sizes.square(80, fields.length+3);
		setPreferredSize(size);
		Layout.layAsStackOfLabeledFields(this, 5, 5);
	}
	
	@Override
	protected Object getPropertyFromModel(String propertyName) {
		if (model==null) {
			return null;
		} else {
			return model.get(propertyName);
		}		
	}
	
	@Override
	protected void setPropertyToModel(String propertyName, Object value) {
		model.put(propertyName, value);
	}
	
}
