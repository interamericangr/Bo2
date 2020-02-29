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
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnLabel;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Abstract base implementation for {@link BoPDComponentFactory}.
 *
 * @param <D>
 *        Type of {@link BoPropertyDescriptor}
 */
public abstract class AbstractBoPDComponentFactory<D extends BoPropertyDescriptor<?>>
implements BoPDComponentFactory<D> {
   
    @Override
	public Pair<Component, Component> draw(D descriptor, String componentId, String labelId) {
        Component label = drawLabel(descriptor, labelId);
        Component main = drawMain(descriptor, componentId);
        return new Pair<Component, Component>(label,main);
    }
   
    @Override
	public Pair<Component, Component> draw(IModel<?> model, D descriptor, String wicketId, String labelWicketId) {
        Component label = drawLabel(descriptor, labelWicketId);
        Component main = drawMain(wicketId, model, descriptor);
        return new Pair<Component, Component>(label, main);
    }
   
    @Override
	public Component drawLabel(D descriptor, String labelWicketId) {       
    	return new SelfDrawnLabel(labelWicketId,descriptor.getLabel());
    }
}