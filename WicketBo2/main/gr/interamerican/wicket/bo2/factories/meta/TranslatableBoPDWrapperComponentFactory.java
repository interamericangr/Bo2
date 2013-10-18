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

import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.wicket.bo2.descriptors.TranslatableBoPropertyDescriptorWrapper;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Generic self-drawn component factory that auto-selects the appropriate
 * self-drawn component factory sub-type based on the BoPropertyDescriptor
 * type wrapped by the supplied TranslatableBoPropertyDescriptorWrapper.
 * 
 * @param <D>
 *        Type of wrapped BoPropertyDescriptor.
 */
@SuppressWarnings("unchecked")
public class TranslatableBoPDWrapperComponentFactory<D extends BoPropertyDescriptor<?>> 
extends AbstractBoPDComponentFactory<TranslatableBoPropertyDescriptorWrapper<?, ?, ?>> {	
	
	public Component drawMain(TranslatableBoPropertyDescriptorWrapper<?, ?, ?> descriptor,String wicketId) {		
		D wrapped = (D) descriptor.getDescriptor();
		BoPDComponentFactory<D> factory = (BoPDComponentFactory<D>) BoPDTypeBasedFactorySelection.INSTANCE.select(wrapped);
		return factory.drawMain(wrapped, wicketId);
	}
	
	public Component drawMain(String wicketId, IModel<?> model,
			TranslatableBoPropertyDescriptorWrapper<?, ?, ?> descriptor) {
		D wrapped = (D) descriptor.getDescriptor();
		BoPDComponentFactory<D> factory = (BoPDComponentFactory<D>) BoPDTypeBasedFactorySelection.INSTANCE.select(wrapped);
		return factory.drawMain(wicketId,model,wrapped);		
	}

}
