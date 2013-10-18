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
package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxLink;
import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Factory of Bo2 enabled wicket components. 
 */
public class LinkFactory {

	/**
	 * Private empty constructor of a utility class.
	 */
	private LinkFactory() {/* empty */}
	
	
	/**
	 * creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param model
	 *        Link model.
	 * @param action
	 *        CallbackAction that will be executed on button press.
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static AjaxLink<String> createLink 
	(final String id, final IModel<String> model, final CallbackAction action) {
		AjaxLink<String> link = new CallbackAjaxLink<String>(id, action);
		link.setModel(model);
		return link;
	}
	
	/**
	 * Creates a new AjaxLink that executes a {@link CallbackAction}.
	 * 
	 * The button will be created with a ResourceModel for the expression
	 * link.id , where id is the id specified. 
	 * 
	 * @param id 
	 *        Button id.
	 * @param action
	 *        CallbackAction that will be executed on button press.
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static AjaxLink<String> createLink 
	(final String id, final CallbackAction action) {
		AjaxLink<String> link = new CallbackAjaxLink<String>(id, action);
		link.setModel(new ResourceModel("link."+id)); //$NON-NLS-1$
		return link;
	}
	
	/**
	 * Creates an AjaxLink that togles on/of the visibility of a component.
	 * 
	 * The Component must have <code>outputMarkUpPlaceholderId = true</code>.
	 * 
	 * @param componentName
	 *        Name of the panel who's visibility is toggled on/off.
	 * @param <T>
	 *        Type of link model object.          
	 *        
	 * @param container - O container that contains the component and the link.
	 * 
	 * @return Returns the link.  
	 */
	
	public static <T> AjaxLink<T> createTogleVisibleLink(final String componentName , final MarkupContainer container){
        AjaxLink<T> refreshLink =
            new AjaxLink<T>(componentName+"Link") { //$NON-NLS-1$

            /**
             * serialize.
             */
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(AjaxRequestTarget target) {
            	Component component = container.get(componentName);
            	boolean newVisibility = !component.isVisible();
            	component.setVisible(newVisibility);
                target.addComponent(component);
            }
        };
        return refreshLink;
       
    }
	
	
}
