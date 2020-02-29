package gr.interamerican.wicket.markup.html.panel.service;

import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

/**
 * A {@link ServicePanel} extension that is empty.
 */
public class EmptyServicePanel extends ServicePanel {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new EmptyServicePanel object. 
	 *
	 * @param definition the definition
	 */
	public EmptyServicePanel(ServicePanelDef definition) {
		super(definition);
	}

	@Override
	protected void paint() {
		//EMPTY
	}

	@Override
	protected void init() {
		//EMPTY
	}
}