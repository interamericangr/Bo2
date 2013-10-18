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

import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.ButtonProperties;
import gr.interamerican.bo2.gui.properties.CheckBoxProperties;
import gr.interamerican.bo2.gui.properties.LabelOption;
import gr.interamerican.bo2.gui.properties.LabelProperties;
import gr.interamerican.bo2.gui.properties.TextAreaProperties;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.gui.util.Bo2GuiUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.attributes.Refreshable;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Panel with a model object.
 * 
 * The panel can have components bound to the model object. <br/>
 * The panel is {@link Refreshable}. The default implementation of 
 * <code>refresh()</code> in a BPanel puts the values of its model
 * object attributes to its model bound components. 
 * 
 * @param <T>
 *        Type of model.
 */
public class BPanel<T> extends ButtonPanel 
implements Refreshable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model.
	 */
	protected T model;
	
	/**
	 * Value components map.
	 */
	Map<String, ValueComponent> valueComponents = new HashMap<String, ValueComponent>();
	
	
	
	
	/**
	 * This field can be used by sub-classes that override
	 * the paint method in order to pass a parameter to it.
	 */
	protected Object paintParameter;
	


	/**
	 * Creates a new Panel object.
	 * 
	 * @param model
	 */
	public BPanel(T model) {
		this(model,null);
	}
	
	/**
	 * Creates a new Panel object.
	 * 
	 * @param model
	 * @param paintParameter 
	 */
	protected BPanel(T model, Object paintParameter) {
		super();
		this.model = model;
		this.paintParameter = paintParameter;
		paint();
		model2panel();
	}
	
	/**
	 * Creates a new Panel object.
	 * 
	 * Protected default constructor that does not initialize the panel.
	 * This constructor is provided for use in cases where panel initialization
	 * of a sub-class requires additional information that is known only to 
	 * the sub-class.
	 */
	protected BPanel() {
		super();
	}
	
	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		super.addImpl(comp, constraints, index);
		Bo2GuiUtils.refreshStaticContent(comp);
	}

	/**
	 * Adds the specified component.
	 * 
	 * @param component
	 */
	void addModelBoundComponent(ValueComponent component) {
		Component c = (Component) component;
		add(c);
		valueComponents.put(component.getComponentName(), component);
	}

	

	/**
	 * Adds a label.
	 * 
	 * @param name
	 * @param properties
	 */
	public void addStaticLabel(String name, LabelProperties properties) {
		BStaticLabel label = new BStaticLabel(name);		
		Dimension size = Sizes.fieldSize(properties.getLabelLength());
		label.setPreferredSize(size);
		add(label);
	}
	
	/**
	 * Adds labels.
	 * 
	 * @param names
	 * @param properties
	 */
	public void addStaticLabels(String[] names, LabelProperties properties) {
		for (String string : names) {
			addStaticLabel(string, properties);
		}
	}
	
	

	/**
	 * Updates the model with the values filled in the components.
	 */
	public void model2panel() {
		for (Entry<String, ValueComponent> entry : valueComponents.entrySet()) {
			String property = entry.getKey();
			Object value = getPropertyFromModel(property);
			ValueComponent bc = Utils.cast(entry.getValue());
			bc.setValue(value);
		}
	}

	/**
	 * Updates the components of the panel with the property values of the model object.
	 */
	public void panel2model() {
		for (Entry<String, ValueComponent> entry : valueComponents.entrySet()) {
			String property = entry.getKey();
			ValueComponent bc = entry.getValue();
			Object value = bc.getValue();
			setPropertyToModel(property, value);			
		}
	}

	/**
	 * Set panel component values to the model properties.
	 */
	public void clear() {
		for (Entry<String, ValueComponent> entry : valueComponents.entrySet()) {
			ValueComponent bc = entry.getValue();
			bc.setValue(null);
		}
	}

	/**
	 * Gets the model object.
	 * 
	 * @return Returns the model.
	 */
	public T getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 * 
	 * @param model
	 */
	public void setModel(T model) {
		this.model = model;
		for (BButton button : buttons.values()) {
			setActionListener(button, model);
		}
		model2panel();
	}

	/**
	 * Adds a button that invokes a method of this panel.
	 * 
	 * @param methodName
	 *            Name of method that will be triggered. The same will be also the name of the new BButton that is
	 *            created.
	 * @param properties
	 *            Properties for the new button. Can be null.
	 * 
	 * @return Returns the new button.
	 */
	public BButton addModelBoundButton(String methodName, ButtonProperties properties) {
		return addButton(methodName, properties, model);
	}

	/**
	 * Adds a button that invokes a method of this panel.
	 * 
	 * @param methodName
	 *            Name of method that will be triggered. The same will be also the name of the new BButton that is
	 *            created.
	 * @param properties
	 *            Component properties.
	 * 
	 * @return Returns the new button.
	 */
	public BButton addSimpleButton(String methodName, ButtonProperties properties) {
		return addButton(methodName, properties, this);
	}

	/**
	 * Adds a model bound text area.
	 * 
	 * @param field
	 * @param properties
	 */
	public void addModelBoundTextArea(String field, TextAreaProperties properties) {
		BTextArea text = new BTextArea(field);
		text.setRows(properties.getRows());
		text.setColumns(properties.getColumns());
		text.setEnabled(properties.isEnabled());
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		if (properties.isHasLabel()) {
			addStaticLabel(field, properties);
		}		
		addModelBoundComponent(text);
	}

	/**
	 * Adds a model bound text fields.
	 * 
	 * @param field
	 *            Field name.
	 * @param properties
	 *            properties of the field.
	 */
	public void addModelBoundTextField(String field, TextFieldProperties properties) {
		BTextField text = new BTextField(field);
		text.setColumns(properties.getColumns());
		text.setEnabled(properties.isEnabled());
		text.setEditable(properties.isEditable());
		addOptionalLabel(field, properties);		
		addModelBoundComponent(text);
	}
	
	/**
	 * Adds a model bound text fields.
	 * 
	 * @param field
	 *            Field name.
	 * @param properties
	 *            properties of the field.
	 */
	public void addModelBoundLabel(String field, LabelProperties properties) {
		BLabel label = new BLabel(field);
		Dimension size = Sizes.fieldSize(properties.getLabelLength());
		label.setPreferredSize(size);
		addModelBoundComponent(label);
	}
	
	/**
	 * Optionally adds a label.
	 * 
	 * @param field
	 *            Field name.
	 * @param option
	 *            Label option.
	 */
	void addOptionalLabel(String field, LabelOption option) {
		if (option.isHasLabel()) {
			addStaticLabel(field, option);
		}
	}
	
	/**
	 * Adds a series of model bound text fields.
	 * 
	 * @param field
	 *            Field name.
	 * @param properties
	 *            properties of the field.
	 */
	public void addModelBoundCheckBox(String field, CheckBoxProperties properties) {
		BCheckBox check = new BCheckBox(field);
		Dimension size = Sizes.fieldSize(properties.getLength());
		check.setPreferredSize(size);
		check.setEnabled(properties.isEnabled());
		addOptionalLabel(field, properties);
		addModelBoundComponent(check);
		
	}

	/**
	 * Adds a series of model bound text areas.
	 * 
	 * @param fields
	 * @param properties
	 */
	public void addModelBoundTextAreas(String[] fields, TextAreaProperties properties) {
		for (String field : fields) {
			addModelBoundTextArea(field, properties);
		}
	}
	
	
	
	/**
	 * Adds a series of model bound text fields.
	 * 
	 * @param fields
	 *            Field names.
	 * @param properties
	 *            properties of the fields.
	 */
	public void addModelBoundTextFields(String[] fields, TextFieldProperties properties) {
		for (String field : fields) {
			addModelBoundTextField(field, properties);
		}
	}

	/**
	 * Adds a series of model bound text fields.
	 * 
	 * @param fields
	 *            Field names.
	 * @param properties
	 *            properties of the field.
	 */
	public void addModelBoundCheckBoxes(String[] fields, CheckBoxProperties properties) {
		for (String field : fields) {
			addModelBoundCheckBox(field, properties);
		}
	}
	
	/**
	 * Adds a series of model bound text fields.
	 * 
	 * @param fields
	 *            Field names.
	 * @param properties
	 *            properties of the field.
	 */
	public void addModelBoundLabels(String[] fields, LabelProperties properties) {
		for (String field : fields) {
			addModelBoundLabel(field, properties);
		}
	}

	/**
	 * Place holder for any painting code.
	 */
	public void paint() {
		/*
		 * Empty method
		 */
	}

	/**
	 * Gets the text field with the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the text field.
	 */
	public BTextField getTextField(String name) {
		ValueComponent v = valueComponents.get(name);
		if (v instanceof BTextField) {
			return (BTextField) v;
		}
		return null;
	}

	/**
	 * Gets the text field with the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the text field.
	 */
	public BTextArea getTextArea(String name) {
		ValueComponent v = valueComponents.get(name);
		if (v instanceof BTextArea) {
			return (BTextArea) v;
		}
		return null;
	}
	
	/**
	 * Gets the text field with the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the text field.
	 */
	public BLabel getLabel(String name) {
		ValueComponent v = valueComponents.get(name);
		if (v instanceof BLabel) {
			return (BLabel) v;
		}
		return null;
	}

	/**
	 * Gets the text field with the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the text field.
	 */
	public BCheckBox getCheckBox(String name) {
		ValueComponent v = valueComponents.get(name);
		if (v instanceof BCheckBox) {
			return (BCheckBox) v;
		}
		return null;		
	}
	
	/**
	 * Gets the button field with the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the text field.
	 */
	public BButton getButton(String name) {		
		return buttons.get(name);		
	}
	
	
	/**
	 * Gets the value of the specified property from the model. <br/>
	 * 
	 * This method is called by the <code>model2panel()</code> method.
	 * This method and {@link #setPropertyToModel(String, Object)} define
	 * the binding between the model object and the components. The
	 * binding key is the propertyName.
	 *   
	 * @param propertyName
	 *        Name of the property to get from the model object.
	 * 
	 * @return Returns the property of the model object.
	 */
	protected Object getPropertyFromModel(String propertyName) {
		if (model==null) {
			return null;
		} else {
			return JavaBeanUtils.getProperty(propertyName, model);
		}		 	
	}
	
	/**
	 * Sets the specified value to the specified property of the model. <br/>
	 * 
	 * This method is called by the <code>panel2model()</code> method.
	 * This method and {@link #getPropertyFromModel(String)} define
	 * the binding between the model object and the components. The
	 * binding key is the propertyName. 
	 * 
	 * @param propertyName
	 *        Name of the property to set.
	 * @param value
	 *        Value to set to the specified property.
	 */
	protected void setPropertyToModel(String propertyName, Object value) {
		try {
			BeanUtils.setProperty(model, propertyName, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void refresh() {
		model2panel();		
	}
}
