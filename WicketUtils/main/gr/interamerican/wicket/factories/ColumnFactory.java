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
package gr.interamerican.wicket.factories;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.EnhancedPropertyColumn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

/**
 * Factory for {@link DataTable} columns.
 */
public class ColumnFactory {
	
	/**
	 * Suffix for translation resources keys.
	 */
	private static final String LABEL = "Label"; //$NON-NLS-1$
	
	/**
	 * Creates a {@link PropertyColumn}.
	 * 
	 * @param propertyExpression
	 *        Property expression to evaluate against the column object.
	 * @param propertyLabel
	 *        Label to put on table header for this column. Defaults to propertyExpression if null.
	 * @param sortProperty 
	 *        Property expression whose object column value is used for default sorting.
	 * @param faultyExpressionTolerant 
	 *        Indicates if a faulty expression is to be tolerated.
	 * @param formatter
	 *        Formatter of column Object.
	 * 
	 * @param <T>
	 *        Type of column model object.
	 *        
	 * @return Returns a {@link PropertyColumn} for the specified input.
	 */
	public static <T extends Serializable> PropertyColumn<T>
	createPropertyColumn(String propertyExpression, String propertyLabel, String sortProperty, boolean faultyExpressionTolerant, Formatter<?> formatter) {
		if(propertyExpression==null) {
			throw new RuntimeException("Cannot create a PropertyColumn with null property expression"); //$NON-NLS-1$
		}
		
		String label = Utils.notNullOrEmpty(propertyLabel, propertyExpression);
		String labelRsrc = label + LABEL;
		
		IModel<String> displayModel = null;
		try {
			displayModel = new ResourceModel(labelRsrc);
			displayModel.getObject();
		} catch(MissingResourceException mre) {
			displayModel = new Model<String>(label);
		}
		
		if(needToEnhance(faultyExpressionTolerant, formatter)) {
			return new EnhancedPropertyColumn<T>(displayModel, sortProperty, propertyExpression, formatter, faultyExpressionTolerant);
		}
		return new PropertyColumn<T>(displayModel, sortProperty, propertyExpression);
	}
	
	/**
	 * Creates a list of property columns.
	 * 
	 * @param propertyExpressions
	 *        Names of properties.
	 * @param propertyLabels
	 *        Labels to put on table header of each column. Defaults to propertyExpressions if null.
	 * @param sortProperties 
	 *        Property expressions whose object column value is used for default sorting.
	 * @param faultyExpressionTolerant 
	 *        Indicates if a faulty expression is to be tolerated.
	 * @param formatters
	 *        Formatters of column Objects.
	 *        
	 * @param <T>
	 *        Type of column model object.
	 *        
	 * @return Returns a list of {@link PropertyColumn} objects for the 
	 *         specified properties.
	 */
	public static <T extends Serializable> List<IColumn<T>>
	createPropertyColumns(String[] propertyExpressions, String[] propertyLabels, String[] sortProperties, boolean faultyExpressionTolerant, Formatter<?>[] formatters) {
		String[] labels = Utils.notNullOrEmpty(propertyLabels, propertyExpressions);
		
		if(propertyExpressions.length != labels.length) {
			String msg ="Size mismatch between property and label matrices."; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		if(sortProperties != null && propertyExpressions.length != sortProperties.length) {
			String msg ="Size mismatch between property and sort matrices."; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		if(formatters != null && propertyExpressions.length != formatters.length) {
			String msg ="Size mismatch between property and formatter matrices."; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		
		List<IColumn<T>> list = new ArrayList<IColumn<T>>();
		for (int i=0; i<propertyExpressions.length; i++) {
			String sortProperty = null;
			if(sortProperties != null ) {
				sortProperty = sortProperties[i];
			}
			Formatter<?> formatter = null;
			if(formatters != null) {
				formatter = formatters[i];
			}
			PropertyColumn<T> column = createPropertyColumn(propertyExpressions[i], labels[i], sortProperty, faultyExpressionTolerant, formatter);
			list.add(column);
		}
		return list;
	}
	
	/**
	 * Indicates if {@link EnhancedPropertyColumn} is needed.
	 * @param faultyExpressionTolerant
	 * @param formatter
	 * @return True, if the column should be faultyExpressionTolerant or have a special formatter.
	 */
	private static boolean needToEnhance(boolean faultyExpressionTolerant, Formatter<?> formatter) {
		return formatter!=null || faultyExpressionTolerant;
	}
	
	/**
	 * Creates a list of property columns.
	 * 
	 * @param propertyNames
	 *        String containing the property names, separated by commas.
	 * 
	 * @param <T>
	 *        Type of column model object.
	 *        
	 * @return Returns a list of {@link PropertyColumn} objects for the 
	 *         specified properties.
	 */
	public static <T extends Serializable> List<IColumn<T>>
	createPropertyColumns(String propertyNames) {
		String[] properties = 
			TokenUtils.splitTrim(propertyNames, StringConstants.COMMA, false);
		return createPropertyColumns(properties, properties, properties, false, null);
	}

}
