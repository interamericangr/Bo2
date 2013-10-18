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
 * Generic self-drawn component factory that auto-selects the appropriate
 * self-drawn component factory sub-type based on the supplied BoPropertyDescriptor
 * type.
 */
public class GenericBoPDComponentFactory
implements BoPDComponentFactory<BoPropertyDescriptor<?>> {
	
	/**
	 * Singleton.
	 */
	public static final GenericBoPDComponentFactory INSTANCE = new GenericBoPDComponentFactory();
	
    /**
     * Gets the matching BoPDComponentFactory for the specified descriptor.
     *
     * @param descriptor
     *
     * @return Returns the BoPDComponentFactory.
     */
    @SuppressWarnings("unchecked")
    BoPDComponentFactory<BoPropertyDescriptor<?>> getFactory(BoPropertyDescriptor<?> descriptor) {
        return (BoPDComponentFactory<BoPropertyDescriptor<?>>) BoPDTypeBasedFactorySelection.INSTANCE.select(descriptor);
    }    
    public Pair<Component, Component> draw(IModel<?> model, BoPropertyDescriptor<?> descriptor, String cmpWicketId, String labelWicketId) {
        return getFactory(descriptor).draw(model, descriptor, cmpWicketId, labelWicketId);
    }
   
    public Pair<Component, Component> draw(BoPropertyDescriptor<?> descriptor, String wicketId,String labelWicketId) {
        return getFactory(descriptor).draw(descriptor, wicketId,labelWicketId);
    }

    public Component drawMain(String cmpWicketId, IModel<?> model, BoPropertyDescriptor<?> descriptor) {
        return getFactory(descriptor).drawMain(cmpWicketId,model,descriptor);
    }
    
    public Component drawMain(BoPropertyDescriptor<?> descriptor, String wicketId) {
        return getFactory(descriptor).drawMain(descriptor, wicketId);
    }
    
    public Component drawLabel(BoPropertyDescriptor<?> descriptor, String wicketId) {
        return getFactory(descriptor).drawLabel(descriptor, wicketId);
    }

}
