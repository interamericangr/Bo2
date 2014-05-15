package gr.interamerican.bo2.gui.components;

import javax.swing.JComboBox;

/**
 * Combo Box
 */
public class BComboBox extends JComboBox implements ValueComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name.
	 */
	String componentName;

	/**
	 * Creates a new TextField object.
	 * 
	 * @param componentName
	 */
	public BComboBox(String componentName) {
		super();
		this.componentName = componentName;
	}

	@Override
	public String getComponentName() {
		return componentName;
	}

	@Override
	public Object getValue() {
		return getSelectedItem();
	}

	@Override
	public void setValue(Object value) {
		setSelectedItem(value);
	}

	@Override
	public String getName() {
		return componentName;
	}

}
