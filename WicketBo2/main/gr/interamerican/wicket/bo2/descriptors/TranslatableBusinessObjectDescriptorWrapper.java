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
package gr.interamerican.wicket.bo2.descriptors;

import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper around a {@link BusinessObjectDescriptor} that allows the caption to
 * change.
 * 
 * @param <T>
 *            Type of descriptor.
 * @param <R>
 *            Type of translation resource id.
 * @param <L>
 *            Type of language id.
 */
public class TranslatableBusinessObjectDescriptorWrapper<T, R, L> 
implements BusinessObjectDescriptor<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Descriptor.
	 */
	BusinessObjectDescriptor<T> descriptor;
	/**
	 * translator name.
	 */
	String translatorName;
	/**
	 * translation resource id.
	 */
	R resourceId;
	
	/**
	 * Creates a new TranslatableBusinessObjectDescriptorWrapper object.
	 * The unchecked conversion assumes that the Translator and Bo2WicketSession
	 * languageId are of the same type.
	 * 
	 * @param descriptor
	 *            BusinessObjectDescriptor that describes the object.
	 * @param resourceId
	 *            Translation resource id.
	 * @param translatorName
	 *            Name of the translator used to translate the label.
	 */
	public TranslatableBusinessObjectDescriptorWrapper(
			BusinessObjectDescriptor<T> descriptor,
			R resourceId,
			String translatorName) {
		super();
		this.descriptor = descriptor;
		this.translatorName = translatorName;
		this.resourceId = resourceId;
	}

	public String getLabel() {
		/*
		 * The unchecked conversion assumes that the Translator 
		 * and Bo2WicketSession languageId are of the same type.
		 */
		@SuppressWarnings("unchecked")
		L languageId = (L) Bo2WicketSession.get().getLanguageId();
		String label = TranslatorRegistry.getRegisteredTranslator(translatorName).translate(resourceId, languageId);
		label = Utils.notNull(label, descriptor.getLabel());
		return label;
	}
	
	public List<BoPropertyDescriptor<?>> getPropertyDescriptors() {
		return descriptor.getPropertyDescriptors();
	}

	public Map<BoPropertyDescriptor<?>, Object> get(T object) {
		return descriptor.get(object);
	}

	public void validate(T object) throws MultipleValidationsException {
		descriptor.validate(object);	
	}

	public String getName() {
		return descriptor.getName();
	}
	
	public void setPropertyDescriptors(List<BoPropertyDescriptor<?>> propertyDescriptors) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}
	public void set(T object, Map<BoPropertyDescriptor<?>, Object> values)
	throws MultipleValidationsException {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}
	public void setLabel(String label) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public void setName(String name) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public void setExpressions(Set<BusinessObjectValidationExpression> expressions) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public Set<BusinessObjectValidationExpression> getExpressions() {
		return descriptor.getExpressions();
	}

	public BoPropertyDescriptor<?> getDescriptorByName(String descriptorName) {
		return descriptor.getDescriptorByName(descriptorName);
	}

	public BoPropertyDescriptor<?> whoAffectsMe(BoPropertyDescriptor<?> affected) {
		return descriptor.whoAffectsMe(affected);
	}

}
