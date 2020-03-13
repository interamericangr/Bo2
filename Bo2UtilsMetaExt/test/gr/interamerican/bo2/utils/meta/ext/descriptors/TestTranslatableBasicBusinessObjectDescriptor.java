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

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.arch.utils.beans.SessionImpl;
import gr.interamerican.bo2.arch.utils.beans.TranslatorImpl;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.samples.utils.meta.Bean1;

import org.junit.Test;

/**
 * Unit tests of {@link TranslatableBasicBusinessObjectDescriptor}.
 */
public class TestTranslatableBasicBusinessObjectDescriptor {

	/**
	 * resourceId
	 */
	private String resourceId = "resourceId"; //$NON-NLS-1$

	/**
	 * NAME
	 */
	private static final String NAME = "descriptorName"; //$NON-NLS-1$
	
	/**
	 * TRANSLATION
	 */
	private static final String TRANSLATION = "translatedValue"; //$NON-NLS-1$

	/**
	 * Test getLabel
	 */
	@Test
	public void testGetLabel() {
		// Session
		Session<Object, Long> session = new SessionImpl<Object, Long>();
		long languageId = 62151L;
		session.setLanguageId(languageId);
		Bo2Session.setSession(null);
		// Translation
		TranslatorImpl<String,Long> translator = new TranslatorImpl<String,Long>();
		translator.learn(resourceId, languageId, TRANSLATION);
		TranslatorRegistry.registerTranslator(this.getClass().getName(), translator);
		TranslatableBasicBusinessObjectDescriptor<Bean1, String, Long> wrapper = new TranslatableBasicBusinessObjectDescriptor<Bean1, String, Long>(
				resourceId, this.getClass().getName());
		wrapper.setName(NAME);
		assertEquals(NAME, wrapper.getLabel());
		Bo2Session.setSession(session);
		assertEquals(TRANSLATION, wrapper.getLabel());
		translator.learn(resourceId, languageId, null);
		assertEquals(NAME, wrapper.getLabel());
	}
}