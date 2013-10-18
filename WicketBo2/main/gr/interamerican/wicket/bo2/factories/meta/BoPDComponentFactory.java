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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Component factory based on {@link BoPropertyDescriptor}s.
 *
 * @param <D>
 *        Type of BoPropertyDescriptor.
 */
public interface BoPDComponentFactory<D extends BoPropertyDescriptor<?>> {
   
    /**
     * Draws the components necessary for a property.
     *
     * @param descriptor
     *        Property descriptor.
     * @param model
     *        Model bound to the CompoundPropertyModel of the container.
     * @param cmpWicketId
     *        WicketId for the component.
     * @param labelWicketId
     *           WicketId for the label
     * @return Returns a pair containing the label and the property component.
     */
    Pair<Component, Component> draw(IModel<?> model, D descriptor, String cmpWicketId, String labelWicketId);
   
    /**
     * Draws the components necessary for a property.
     *
     * @param descriptor
     *        Property descriptor.
     * @param cmpWicketId
     *        WicketId for the component.
     * @param labelWicketId
     *           WicketId for the label
     * @return Returns a pair containing the label and the property component.
     */
    Pair<Component, Component> draw(D descriptor, String cmpWicketId, String labelWicketId);
      
    /**
     * Draws the main component for the property.
     *
     * @param descriptor
     *        Property descriptor.
     * @param wicketId
     *        WicketId for the component.
     *
     * @return Returns the main component for the property.
     */
    Component drawMain(D descriptor, String wicketId);
   
    /**
     * Draws the main component for the property
     *
     * @param descriptor
     *        Property descriptor.
     * @param wicketId
     *        WicketId for the component.
     * @param model
     *        Model for the component (possibly bound to a CompoundPropertyModel of a container).
     *       
     * @return Returns the main component for the property.
     */
    Component drawMain(String wicketId, IModel<?> model, D descriptor);
     
    /**
     * Draws the label component for the property.
     *
     * @param descriptor
     *        Property descriptor.
     * @param labelWicketId
     *        WicketId for the label.
     *
     * @return Returns the label component for the property.
     */
    Component drawLabel(D descriptor, String labelWicketId);

}
