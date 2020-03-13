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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptorWrapper;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

/**
 * Wrapper around a {@link BoPropertyDescriptor} that allows the caption to
 * change.
 * 
 * @param <T>
 *            Type of descriptor
 * @param <R>
 *            Type of translation resource id
 * @param <L>
 *            Type of language id
 */
public class TranslatableBoPropertyDescriptorWrapper<T, R, L>
implements BoPropertyDescriptor<T>, BoPropertyDescriptorWrapper<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Descriptor.
	 */
	BoPropertyDescriptor<T> descriptor;
	/**
	 * translator name.
	 */
	String translatorName;
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
	 * @param translatorName
	 *            Name of the translator used to translate the label.
	 */
	public TranslatableBoPropertyDescriptorWrapper(
			BoPropertyDescriptor<T> descriptor,
			R resourceId,
			String translatorName) {
		this.descriptor = descriptor;
		this.translatorName = translatorName;
		this.resourceId = resourceId;
	}

	@Override
	public String getLabel() {
		if (Bo2Session.getSession() == null) {
			return descriptor.getLabel();
		}
		L languageId = Bo2Session.<Object, L> getSession().getLanguageId();
		Translator<Object, Object> translator = TranslatorRegistry.getRegisteredTranslator(translatorName);
		return Utils.notNull(translator.translate(resourceId, languageId), descriptor.getLabel());
	}

	@Override
	public void setLabel(String label) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public Integer getIndex() {
		return descriptor.getIndex();
	}

	@Override
	public void setIndex(Integer index) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return descriptor.getName();
	}

	@Override
	public void setName(String name) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public String getPackageName() {
		return descriptor.getPackageName();
	}

	@Override
	public void setPackageName(String packageName) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public String getClassName() {
		return descriptor.getClassName();
	}

	@Override
	public void setClassName(String ownerName) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public String getFullyQualifiedName() {
		return descriptor.getFullyQualifiedName();
	}

	@Override
	public String getFullyQualifiedClassName() {
		return descriptor.getFullyQualifiedClassName();
	}

	@Override
	public boolean isReadOnly() {
		return descriptor.isReadOnly();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public boolean isNullAllowed() {
		return descriptor.isNullAllowed();
	}

	@Override
	public void setNullAllowed(boolean nullAllowed) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public boolean isHasDefault() {
		return descriptor.isHasDefault();
	}

	@Override
	public void setHasDefault(boolean hasDefault) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public T parse(String value) throws ParseException {
		return descriptor.parse(value);
	}

	@Override
	public String format(T value) {
		return descriptor.format(value);
	}

	@Override
	public T parseAndValidate(String value) throws ParseException,
			ValidationException {
		return descriptor.parseAndValidate(value);
	}

	@Override
	public void validate(T value) throws ValidationException {
		descriptor.validate(value);
	}

	@Override
	public T getDefaultValue() {
		return descriptor.getDefaultValue();
	}

	@Override
	public void setDefaultValue(T defaultValue) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}
	
	/**
	 * Gets the descriptor.
	 * 
	 * @return Returns the descriptor.
	 */
	@Override
	public BoPropertyDescriptor<T> getDescriptor() {
		return descriptor;
	}

	@Override
	public int getMaxLength() {
		return descriptor.getMaxLength();
	}

	@Override
	public void setMaxLength(int maxLength) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}
	
	@Override
	public T valueOf(Number value) {	
		return descriptor.valueOf(value);
	}

	@Override
	public void setAffected(String affected) {
		throw new RuntimeException("Not allowed"); //$NON-NLS-1$
	}

	@Override
	public String getAffected() {
		return descriptor.getAffected();
	}
	
	@Override
	public Parser<T> getParser() {
		return descriptor.getParser();
	}
	
	@Override
	public Validator<T> getValidator() {
		return descriptor.getValidator();
	}
}