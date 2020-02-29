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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.expressions.ExpressionEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Set of properties that describes a bean.
 * 
 * This descriptor can't support properties of type collection.
 * 
 * @param <T> Type of bean being described by this BusinessObjectDescriptor.
 */
public class BasicBusinessObjectDescriptor<T> implements BusinessObjectDescriptor<T> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * label.
	 */
	private String label;
	
	/** name. */
	private String name;
	
	/**
	 * Expressions.
	 */
	private Set<BusinessObjectValidationExpression> expressions = new HashSet<BusinessObjectValidationExpression>();
	
	/**
	 * Descriptors of the bean's properties.
	 */
	private List<BoPropertyDescriptor<?>> propertyDescriptors = new ArrayList<BoPropertyDescriptor<?>>();

	@Override
	public List<BoPropertyDescriptor<?>> getPropertyDescriptors() {
		return propertyDescriptors;
	}

	@Override
	public void setPropertyDescriptors(
			List<BoPropertyDescriptor<?>> propertyDescriptors) {
		this.propertyDescriptors = propertyDescriptors;
	}

	@Override
	public Map<BoPropertyDescriptor<?>, Object> get(T object) {		
		return Mediator.getInstance().getValues(this, object);
	}

	@Override
	public void set(T object, Map<BoPropertyDescriptor<?>, Object> values)
	throws MultipleValidationsException {
		Mediator.getInstance().setValues(values, object);		
	}

	@Override
	public void validate(T bean) throws MultipleValidationsException {
		Mediator.getInstance().validate(this, bean);
		/*
		 * If no exception was thrown, we can evaluate against
		 * any T-wide expressions that cover checks that relate
		 * to more than one T properties.
		 */
		Map<String, Object> context = getExpressionContext(bean);
		if(expressions!=null) {
			Map<Object, String> messages = new HashMap<Object, String>();
			for(BusinessObjectValidationExpression e : expressions) {
				try {
					ExpressionEngine.INSTANCE.evaluate(e.getExpression(), context, e.getMessage());
				} catch (ValidationException ve) {
					messages.put(bean, ve.getMessage());
				}
				if(!messages.isEmpty()) {
					throw new MultipleValidationsException(messages);
				}
			}
		}
	}

	@Override
	public String getLabel() {
		return Utils.notNull(label, getName());
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setExpressions(Set<BusinessObjectValidationExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public Set<BusinessObjectValidationExpression> getExpressions() {
		return expressions;
	}
	
	/**
	 * Gets a Map<String, Object> with the property names of T and
	 * its values.
	 * 
	 * @param bean
	 *        T instance. 
	 * 
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getExpressionContext(T bean) {
		Map<BoPropertyDescriptor<?>, Object> map = Mediator.getInstance().getValues(this, bean);
		Map<String, Object> context = new HashMap<String, Object>();
		for(Map.Entry<BoPropertyDescriptor<?>, Object> entry : map.entrySet()) {
			context.put(entry.getKey().getName(), entry.getValue());
		}
		return context;
	}

	@Override
	public BoPropertyDescriptor<?> getDescriptorByName(String descriptorName) {
		for(BoPropertyDescriptor<?> bpd : getPropertyDescriptors()) {
			if(descriptorName.equals(bpd.getName())) {
				return bpd;
			}
		}
		return null;
	}

	@Override
	public BoPropertyDescriptor<?> whoAffectsMe(BoPropertyDescriptor<?> affected) {
		if(!getPropertyDescriptors().contains(affected)) {
			throw new RuntimeException(affected.getFullyQualifiedName() + " does not belong to " + getName()); //$NON-NLS-1$
		}
		BoPropertyDescriptor<?> result = SelectionUtils.selectFirstByProperty(BoPropertyDescriptor::getAffected, affected.getName(), getPropertyDescriptors());
		return result;
	}
}