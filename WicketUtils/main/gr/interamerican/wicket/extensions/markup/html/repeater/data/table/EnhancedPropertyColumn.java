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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * This class deals with two concerns. 
 * <br/>
 * Firstly, in case of a property expression that fails to evaluate against 
 * the given Object instance may be shown as a user specified faulty value.
 * This may be useful when the user knowingly represents instances of different
 * types in the rows (e.g. 2 sub-classes of a base type) and the properties
 * that need to be displayed do not exist in both types.
 * <br/>
 * Secondly, the user may specify a Formatter with which to format
 * the column objects.
 * 
 * @param <T>
 *        Model Object type. 
 */
public class EnhancedPropertyColumn<T> extends PropertyColumn<T>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Property expression.
	 */
	private final String propertyExpression;
	
	/**
	 * Default value for empty cells.
	 */
	private String faultyValue = "N/A"; //$NON-NLS-1$
	
	/**
	 * Formatter of column object.
	 */
	@SuppressWarnings("rawtypes")
	Formatter formatter;
	
	/**
	 * Tolerate faulty expressions.
	 */
	boolean faultyExpressionTolerant;

	/**
	 * Creates a new FaultyExpressionTolerantPropertyColumn object. 
	 * 
	 * @param displayModel 
	 * @param sortProperty 
	 * @param propertyExpression 
	 * @param formatter 
	 * @param faultyExpressionTolerant 
	 */
	public EnhancedPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression, Formatter<?> formatter, boolean faultyExpressionTolerant) {
		super(displayModel, sortProperty, propertyExpression);
		this.propertyExpression = propertyExpression;
		this.formatter = formatter;
		this.faultyExpressionTolerant = faultyExpressionTolerant;
	}
	
	@Override
	protected IModel<?> createLabelModel(IModel<T> rowModel) {
		if(!faultyExpressionTolerant && formatter==null) {
			return super.createLabelModel(rowModel);
		}
		
		Object faultyExprTolerant = null;
		if(faultyExpressionTolerant) {
			faultyExprTolerant = getFaultyExprTolerant(rowModel);
			if(faultyValue.equals(faultyExprTolerant)) {
				return new Model<String>(faultyValue);
			}
		} else {
			faultyExprTolerant = new PropertyModel<T>(rowModel, propertyExpression).getObject();
		}
		
		if(formatter!=null) {
			String formatted = getFormatted(faultyExprTolerant);
			return new Model<String>(formatted);
		} else {
			return super.createLabelModel(rowModel);
		}
		
	}

	/**
	 * If the property expression does not evaluate against the given instance
	 * a default user specified value will be placed in the cell instead.
	 * 
	 * @param rowModel
	 * @return Evaluated Object or a default value.
	 */
	protected Object getFaultyExprTolerant(IModel<T> rowModel) {
		Object columnObject = null;
		try {
			columnObject = BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(rowModel.getObject(), propertyExpression);
		} catch (NestedNullException e) {
			return faultyValue;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			return faultyValue;
		}
		return columnObject;
	}
	
	/**
	 * Formats column Object with the user specified Formatter.
	 * 
	 * @param object
	 * @return Formatted value
	 */
	@SuppressWarnings("unchecked")
	protected String getFormatted(Object object) {
		if(object==null) {
			return StringConstants.EMPTY;
		}
		return formatter.format(object);
	}

	/**
	 * Assigns a new value to the faultyValue. Default is 'N/A'.
	 *
	 * @param faultyValue the faultyValue to set
	 */
	public void setFaultyValue(String faultyValue) {
		this.faultyValue = faultyValue;
	}
	
}

