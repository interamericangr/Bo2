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
package gr.interamerican.wicket.bo2.validation;

import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;
import gr.interamerican.bo2.utils.meta.DescriptorUtils;
import gr.interamerican.bo2.utils.meta.MetaSession;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.expressions.ExpressionEngine;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnForm;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

/**
 * Validates a {@link Form} given the {@link BusinessObjectDescriptor} of
 * its model object. If the BusinessObjectDescriptor has any associated
 * {@link BusinessObjectValidationExpression}s, then the model object of
 * the Form will be evaluated against these. This is useful when some
 * check that involves the values of more than one properties has to be
 * tested.
 * 
 * NOTE: This implementation will only validate {@link SelfDrawnForm}s.
 * 
 * TODO: Work with components that are not {@link FormComponent}s
 * 
 * @see BusinessObjectValidationExpression
 * @see ExpressionEngine
 */
public class BusinessObjectFormValidator implements IFormValidator {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link FormComponent}s added to the form.
	 */
	private FormComponent<?>[] components;
	
	/**
	 * Indexes the {@link FormComponent}s with their property name.
	 */
	private Map<String, FormComponent<?>> index = new HashMap<String, FormComponent<?>>();
	
	/**
	 * Validation expressions.
	 */
	private Set<BusinessObjectValidationExpression> expressions;

	/**
	 * Creates a new BusinessObjectFormValidator object.
	 * @param formToValidate 
	 * @param descriptor 
	 */
	public BusinessObjectFormValidator(Form<?> formToValidate, BusinessObjectDescriptor<?> descriptor) {
		this.expressions = descriptor.getExpressions();
		if(!(formToValidate instanceof SelfDrawnForm)) {
			throw new RuntimeException("BusinessObjectFormValidator will not work for a Form that is not a SelfDrawnForm"); //$NON-NLS-1$
		}
		MarkupContainer selfDrawnPanel = (MarkupContainer) formToValidate.get(SelfDrawnForm.PANEL_WICKET_ID);
		List<FormComponent<?>> componentsList = new ArrayList<FormComponent<?>>();
		for(String property : DescriptorUtils.getPropertyNames(descriptor)) {
			Component cmp = SelfDrawnUtils.getComponentFromSelfDrawnPanel(selfDrawnPanel, property);
			if(cmp instanceof FormComponent) {
				FormComponent<?> fc = (FormComponent<?>) cmp;
				componentsList.add(fc);
				index.put(property, fc);
			}
		}
		components = componentsList.toArray(new FormComponent[0]);
	}

	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}

	public void validate(Form<?> form) {
		MetaSession.setLocale(Bo2Session.getLocale());
		Map<String, Object> context = new HashMap<String, Object>();
		for(Map.Entry<String, FormComponent<?>> entry : index.entrySet()) {
			Object value = null;
			if(entry.getValue()!=null) {
				value = entry.getValue().getConvertedInput();
			}
			context.put(entry.getKey(), value);
		}
		
		String msg = StringConstants.EMPTY;
		for(BusinessObjectValidationExpression expression : expressions) {
			try {
				ExpressionEngine.INSTANCE.evaluate(
					expression.getExpression(), context, expression.getMessage());
			} catch (ValidationException ve) {
				msg += ve.getMessage() + StringConstants.SPACE + StringConstants.SHARP + StringConstants.SPACE;
			}
		}
		if(!StringConstants.EMPTY.equals(msg)) {
			form.error(StringUtils.truncateCharsFromEnd(msg, 3));
		}
	}
	
}
