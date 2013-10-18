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
package gr.interamerican.wicket.markup.html.panel.service;


import org.apache.wicket.markup.html.panel.Panel;

/**
 * Base type for all service panels.
 * 
 * The constructor of this class takes only one argument, a 
 * {@link ServicePanelDef} object. Any information necessary for the creation
 * of this panel should be passed via this service panel definition object. 
 * <br/>
 * 
 * Sub-classes of this class should only call <code>super(definition)</code>
 * on their constructor and provide a suitable implementation for methods
 * paint() and initialize().
 * 
 * The creation of a service panel is done in three steps. 
 * 
 * First the super constructor is called. 
 * 
 * Then the <code>initialize()</code> method is called. This method is the 
 * place where the creation of any component should take place. In case
 * of class hierarchies of {@link ServicePanel}s, each sub-class should
 * call <code>super.initialize()</code>, in order to initialize any
 * components declared in its super-class. 
 * 
 * Finally the <code>paint()</code> method is called. This method is
 * supposed to combine components and set other properties of them. In case
 * of class hierarchies of {@link ServicePanel}s, each sub-class should
 * paint all of its components, including the inherited ones, explicitly,
 * because there is no guarantee that the component hierarchy assumed in
 * its super-class is appropriate. This means that the use of the 
 * <code>super.paint()</code> call is discouraged.
 */
public abstract class ServicePanel 
extends Panel {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ServicePanel definition.
	 */
	protected ServicePanelDef definition;
	
	/**
	 * Gets the definition.
	 *
	 * @return Returns the definition
	 */
	public ServicePanelDef getDefinition() {
		return definition;
	}

	/**
	 * Creates a new ServicePanel object. 
	 * 
	 * @param definition 
	 */
	public ServicePanel(ServicePanelDef definition) {
		super(definition.getWicketId());
		this.definition = definition;
		setOutputMarkupPlaceholderTag(true);
		validateDef();
		init();
		paint();
		definition.setServicePanel(this);
	}
	
	/**
	 * Paints this panel.
	 * 
	 * This method should be called by the constructors of concrete 
	 * sub-classes of {@link ServicePanel}. This method is the place
	 * to put the code that draws any visual components of this panel.
	 * Usually, this method should be one of the last things done by
	 * the class' constructor. <br/>
	 * 
	 */
	protected abstract void paint();
	
	/**
	 * Initializes the panel.
	 * 
	 * This method should be called by the constructors of concrete 
	 * sub-classes of {@link ServicePanel}. This method is the place
	 * to put the code that creates any visual components of this panel.
	 * This method should be called before calling <code>paint()</code>.
	 * Any call to <code>super.initialize()</code> should be the last
	 * statement of this method.
	 * 
	 */
	protected abstract void init();
	
	/**
	 * Validates the panel definition.
	 * 
	 * This is the place to validate the panel definition
	 * and set defaults to any properties the user has not
	 * set and should not be null. This method is called
	 * before calling <code>initialize()</code>. Sub-classes should
	 * call <code>super.validateDef()</code> as a first statement.
	 */
	@SuppressWarnings("nls")
	protected void validateDef() {
		if(getDefinition().getWicketId() == null) {
			String msg = "Cannot instantiate a service panel with null wicket:id." ;
			throw new RuntimeException(msg);
		}
		if(getDefinition().getDisableUnauthorizedButtons() == null) {
			getDefinition().setDisableUnauthorizedButtons(false);
		}
	}
	
}
