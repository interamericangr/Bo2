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
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;

/**
 * A {@link BasicBusinessObjectDescriptor} that uses the
 * {@link TranslatorRegistry} api to change the caption (label).
 * 
 * @param <T>
 *            Type of descriptor.
 * @param <R>
 *            Type of translation resource id.
 * @param <L>
 *            Type of language id.
 */
public class TranslatableBasicBusinessObjectDescriptor<T, R, L>
extends BasicBusinessObjectDescriptor<T>
implements BusinessObjectDescriptor<T> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * translator name.
	 */
	String translatorName;
	/**
	 * translation resource id.
	 */
	R resourceId;

	/**
	 * Public Constructor.<br>
	 * This assumes that the Translator and Bo2Session languageId are of the
	 * same type.
	 * 
	 * @param resourceId
	 *            Translation resource id.
	 * @param translatorName
	 *            Name of the translator used to translate the label.
	 */
	public TranslatableBasicBusinessObjectDescriptor(R resourceId, String translatorName) {
		this.translatorName = translatorName;
		this.resourceId = resourceId;
	}

	@Override
	public String getLabel() {
		if (Bo2Session.getSession() == null) {
			return super.getLabel();
		}
		L languageId = Bo2Session.<Object, L> getSession().getLanguageId();
		Translator<Object, Object> translator = TranslatorRegistry.getRegisteredTranslator(translatorName);
		return Utils.notNull(translator.translate(resourceId, languageId), super.getLabel());
	}
}