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

import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

/**
 * Wrapper around a {@link BoPropertyDescriptor} that allows the caption to
 * change.
 * 
 * @param <T>
 *            Type of descriptor.
 * @param <R>
 *            Type of translation resource id.
 * @param <L>
 *            Type of language id.
 * 
 */
public class TranslatableBoPropertyDescriptorWrapper<T, R, L> 
implements BoPropertyDescriptor<T> {
	/**
	 * Descriptor.
	 */
	BoPropertyDescriptor<T> descriptor;
	/**
	 * translator.
	 */
	Translator<R, L> translator;
	/**
	 * translation resource id.
	 */
	R resourceId;

	/**
	 * Creates a new TranslatableBoPropertyDescriptor object. 
	 * 
	 * @param descriptor
	 *            BoPropertyDescriptor that describes the property.
	 * @param resourceId
	 *            Translation resource id.
	 * @param translator
	 *            Translator used to translate the label.
	 */
	public TranslatableBoPropertyDescriptorWrapper(
			BoPropertyDescriptor<T> descriptor,
			R resourceId,
			Translator<R, L> translator) {
		super();
		this.descriptor = descriptor;
		this.translator = translator;
		this.resourceId = resourceId;
	}

	public String getLabel() {
		/*
		 * The unchecked conversion assumes that the Translator 
		 * and Bo2WicketSession languageId are of the same type.
		 */
		@SuppressWarnings("unchecked")
		L languageId = (L) Bo2WicketSession.get().getLanguageId();
		String label = translator.translate(resourceId, languageId);
		label = Utils.notNull(label, descriptor.getLabel());
		return label;
	}

	public void setLabel(String label) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public Integer getIndex() {
		return descriptor.getIndex();
	}

	public void setIndex(Integer index) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public String getName() {
		return descriptor.getName();
	}

	public void setName(String name) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public String getPackageName() {
		return descriptor.getPackageName();
	}

	public void setPackageName(String packageName) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public String getClassName() {
		return descriptor.getClassName();
	}

	public void setClassName(String ownerName) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public String getFullyQualifiedName() {
		return descriptor.getFullyQualifiedName();
	}

	public String getFullyQualifiedClassName() {
		return descriptor.getFullyQualifiedClassName();
	}

	public boolean isReadOnly() {
		return descriptor.isReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public boolean isNullAllowed() {
		return descriptor.isNullAllowed();
	}

	public void setNullAllowed(boolean nullAllowed) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public boolean isHasDefault() {
		return descriptor.isHasDefault();
	}

	public void setHasDefault(boolean hasDefault) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	public T parse(String value) throws ParseException {
		return descriptor.parse(value);
	}

	public String format(T value) {
		return descriptor.format(value);
	}

	public T parseAndValidate(String value) throws ParseException,
			ValidationException {
		return descriptor.parseAndValidate(value);
	}

	public void validate(T value) throws ValidationException {
		descriptor.validate(value);
	}

	public T getDefaultValue() {
		return descriptor.getDefaultValue();
	}

	public void setDefaultValue(T defaultValue) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}
	
	/**
	 * Gets the descriptor.
	 * 
	 * @return Returns the descriptor.
	 */
	public BoPropertyDescriptor<T> getDescriptor() {
		return descriptor;
	}

	public int getMaxLength() {
		return descriptor.getMaxLength();
	}

	public void setMaxLength(int maxLength) {
		descriptor.setMaxLength(maxLength);
	}
	
	

}
