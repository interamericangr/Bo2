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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;

import org.apache.wicket.request.cycle.RequestCycle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link TranslatableBusinessObjectValidationExpression}.
 */
public class TestTranslatableBusinessObjectValidationExpression 
extends Bo2WicketTest {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Object resourceId = new Object();		
		BusinessObjectValidationExpression expression = 
			mock(BusinessObjectValidationExpression.class);		
		TranslatableBusinessObjectValidationExpression<Object, Object> exp = 
			new TranslatableBusinessObjectValidationExpression<Object, Object>
		    (expression, resourceId, "translatorName"); //$NON-NLS-1$
		assertEquals(resourceId, exp.resourceId);
		assertEquals(expression, exp.expression);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testGetExpression() {
		Object resourceId = new Object();
		String expressionString = "string"; //$NON-NLS-1$
		
		BusinessObjectValidationExpression expression = 
			mock(BusinessObjectValidationExpression.class);
		when(expression.getExpression()).thenReturn(expressionString);
		
		TranslatableBusinessObjectValidationExpression<Object, Object> exp = 
			new TranslatableBusinessObjectValidationExpression<Object, Object>
		    (expression, resourceId, "translatorName"); //$NON-NLS-1$
			
		String actual = exp.getExpression();
		Assert.assertEquals(expressionString, actual);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testGetMessage() {
		RequestCycle cycle = RequestCycle.get();
		Bo2WicketRequestCycle.beginRequest(cycle);
		
		String expected = "Message"; //$NON-NLS-1$
		Object resourceId = new Object();
		
		BusinessObjectValidationExpression expression = 
			mock(BusinessObjectValidationExpression.class);		
		@SuppressWarnings("unchecked")
		Translator<Object, Object> translator = mock(Translator.class);
		
		Object languageId = Bo2Session.getSession().getLanguageId();		
		when(translator.translate(resourceId, languageId)).thenReturn(expected);
		TranslatorRegistry.registerTranslator("translatorName", translator); //$NON-NLS-1$
		
		TranslatableBusinessObjectValidationExpression<Object, Object> exp = 
			new TranslatableBusinessObjectValidationExpression<Object, Object>
		    (expression, resourceId, "translatorName");		 //$NON-NLS-1$
		String actual = exp.getMessage();		
		assertEquals(expected, actual);
		
		Bo2WicketRequestCycle.endRequest(cycle);
		
		
	}

}
