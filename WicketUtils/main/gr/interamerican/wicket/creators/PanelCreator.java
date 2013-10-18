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
package gr.interamerican.wicket.creators;

import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Class that creates a {@link DataTable}.
 * 
 * @param <B> 
 *        Type of bean presented in the panel.
 */
public interface PanelCreator<B extends Serializable> {
	
	/**
	 * Creates the {@link Panel}. This may be used for the creation
	 * of a {@link ServicePanel} sub-class or just a plain {@link Panel}
	 * sub-class. In the first case the constructor should call:
	 * <blockquote><pre>
	 * super(definition)
	 * </pre></blockquote>
	 * while in the second case:
	 * <blockquote><pre>
	 * super(definition.getWicketId(), definition.getBeanModel())
	 * </pre></blockquote>
	 * 
	 * The method signature provides the wicketId, the model and the
	 * {@link PanelCreatorMode}. You can provide additional info for
	 * the creation of the panel by using a custom constructor for 
	 * the PanelCreator implementation or by providing setters etc.
	 * However, the wicketId, the bean model and the panelCreatorMode
	 * MUST be taken from the input ModeAwareBeanPanelDef, if they are
	 * needed for the construction of the Panel.
	 * <br/>
	 * The created panels will, in most cases, require a specific concrete 
	 * IModel implementation in order for their components to bind correctly
	 * with the model object and will not work properly with any other IModel
	 * implementation. The property <code>beanModel</code> of the definition
	 * is declared as an IModel. The user should either (a) Declare a concrete
	 * IModel implementation in the constructor of the Panel to be created or
	 * (b) Declare IModel in the panel constructor and check against the
	 * provided runtime object instance type for the IModel implementation
	 * the panel works with. In case (a) the check should be performed in
	 * the PanelCreator implementation and a subsequent cast should be made
	 * when invoking the Panel constructor. In both cases, if the model instance
	 * is inappropriate a RuntimeException should be thrown.  
	 * <br/>
	 * It should be stressed that it is never appropriate to create new model
	 * instances in the constructor of a Panel that is created with a PanelCreator.
	 * The provided model should be used. It is OK to create a new instance of B
	 * but, in this case, the model must be updated.
	 * 
	 * @param definition 
	 *        definition of the panel.
	 *	  
	 * @return Returns the {@link Panel}
	 */
	public Panel createPanel(ModeAwareBeanPanelDef<B> definition);

}
