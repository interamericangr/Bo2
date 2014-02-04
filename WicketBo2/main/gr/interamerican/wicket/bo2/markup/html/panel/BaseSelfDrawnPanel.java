package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Base class for panels that create their components dynamically on runtime.
 * @param <T>
 *        Type of model object.
 */
public abstract class BaseSelfDrawnPanel<T extends Serializable> extends Panel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket id of Panel's label. TODO: is this necessary?
	 */
	protected static final String TITLE_LABEL_WICKET_ID = "titleLabel"; //$NON-NLS-1$
	
	/**
	 * Wicket id for the repeater.
	 */
	protected static final String REPEATER_WICKET_ID = "repeater"; //$NON-NLS-1$
	
	/**
	 * Wicket id of label.
	 */
	protected static final String LABEL_WICKET_ID = "label"; //$NON-NLS-1$

	/**
	 * Wicket id of component.
	 */
	protected static final String COMPONENT_WICKET_ID = "component"; //$NON-NLS-1$

	/**
	 * Name of index property of {@link BoPropertyDescriptor}. This is used to
	 * sort the components within the panel.
	 */
	protected static final String INDEX_PROPERTY_NAME = "index"; //$NON-NLS-1$
	
	/**
	 * Creates a new BaseSelfDrawnPanel object. 
	 *
	 * @param id
	 * @param model
	 */
	public BaseSelfDrawnPanel(String id, CompoundPropertyModel<T> model) {
		super(id, model);
	}

	/**
	 * Creates an empty invisible label.
	 * 
	 * @param id
	 *            Id of the label.
	 * 
	 * @return Returns an empty invisible label.
	 */
	protected Label empty(String id) {
		Label l = new Label(id);
		l.setVisible(false);
		return l;
	}
	
}
