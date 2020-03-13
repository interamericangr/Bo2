package gr.interamerican.bo2.gui.components;

import javax.swing.JComboBox;

/**
 * Combo Box.
 * @param <T> 
 */
public class BComboBox<T> extends JComboBox<T> implements ValueComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Name.
	 */
	String componentName;

	/**
	 * Creates a new TextField object.
	 *
	 * @param componentName the component name
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