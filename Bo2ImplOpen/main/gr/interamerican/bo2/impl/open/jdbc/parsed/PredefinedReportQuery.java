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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.def.OrderedFieldsContainer;
import gr.interamerican.bo2.arch.def.OrderedNamedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.TransformationSpec;
import gr.interamerican.bo2.utils.beans.AssociationTable;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;
import gr.interamerican.bo2.utils.sql.VariableDefinitionFactory;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Query based on a {@link PredefinedReport}.
 * 
 * 
 */
public class PredefinedReportQuery 
extends DynamicJdbcQuery<Object> 
implements EntitiesQuery<Object>, OrderedFieldsContainer, 
OrderedNamedFieldsContainer, TransformationSpec {
	
	/**
	 * Package of synthetic row classes.
	 */
	public static final String ROW_CLASS_PKG = 
		"gr.interamerican.synthetic.row"; //$NON-NLS-1$
	
	/**
	 * Package of synthetic criteria classes.
	 */
	public static final String CRITERIA_CLASS_PKG = 
		"gr.interamerican.synthetic.criteria"; //$NON-NLS-1$
	
	/**
	 * Prefix for synthetic row classes.
	 */
	public static final String ROW_CLASS_PREFIX = 
		"Row"; //$NON-NLS-1$
	
	/**
	 * Prefix for synthetic criteria classes.
	 */
	public static final String CRITERIA_CLASS_PREFIX = 
		"Crit"; //$NON-NLS-1$
		
	/**
	 * Report.
	 */
	PredefinedReport report;
	
	/**
	 * Class of the criteria bean.
	 */
	Class<?> criteriaClass;
	
	/**
	 * Class of the row bean.
	 */
	Class<?> rowClass;
	
	/**
	 * Associates column name with column order.
	 */
	AssociationTable<String, Integer> columnNames = 
		new AssociationTable<String, Integer>();
	
	/**
	 * Creates a new ParsedQuery object. 
	 * 
	 * @param report 
	 *        PredefinedReport.
	 *
	 */
	public PredefinedReportQuery(PredefinedReport report) {
		super();
		this.report = report;
		VariableDefinition<?>[] columnDefs = 
			VariableDefinitionFactory.create(report.getColumns());
		for (int i = 0; i < columnDefs.length; i++) {
			columnNames.associate(columnDefs[i].getName(), i+1);
		}
		rowClass = Factory.compileJavaBean(rowClassName(), columnDefs);
		Parameter[] params = removeDuplicates(report.getParameters());
		VariableDefinition<?>[] paramDefs = 
			VariableDefinitionFactory.create(params);
		criteriaClass = Factory.compileJavaBean(criteriaClassName(), paramDefs);
		this.criteria = Factory.create(criteriaClass);
	}
	
	/**
	 * Name of the class of the criteria bean. 
	 * 
	 * @return Returns the name of the class of the criteria bean. 
	 */
	String criteriaClassName() {
		return CRITERIA_CLASS_PKG+StringConstants.DOT+CRITERIA_CLASS_PREFIX+report.getUniqueId();
	}
	/**
	 * Name of the class of the row bean. 
	 * 
	 * @return Returns the name of the class of the row bean. 
	 */
	String rowClassName() {
		return ROW_CLASS_PKG+StringConstants.DOT+ROW_CLASS_PREFIX+report.getUniqueId();
	}	
	
	public Object getEntity() throws DataAccessException {
		try {
			Object row = Factory.create(rowClass);
			Column[] columns = report.getColumns();
			for (int i = 0; i < columns.length; i++) {
				Object val = columns[i].getColumnType().get(rs, i+1, true);
				VariableDefinition<?> vd = VariableDefinitionFactory.create(columns[i]);
				ReflectionUtils.set(vd.getName(), val, row);
			} 
			return row;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public String baseSql() {
		return report.getSql();
	}
	
	public int getFieldOrder(String field) {		
		Integer order = Utils.notNull(columnNames.getRight(field), -1);
		return order.intValue();
	}
	
	public String getFieldName(int field) {
		return columnNames.getLeft(field); 
	}
	
	public int getFieldsCount() {	
		return columnNames.size();
	}
	
	public Class<?> getArgumentType() {
		return criteriaClass;
	}
	
	public Class<?> getResultType() {		
		return rowClass;
	}
	
	/**
	 * Removes duplicate parameters. A parameter may appear in a
	 * where clause multiple times.
	 * 
	 * @param params Original parameters array as parsed from the
	 *               SQL statement.
	 * 
	 * @return An array that contains each parameter only once.
	 */
	private Parameter[] removeDuplicates(Parameter[] params) {
		List<Parameter> clean = new ArrayList<Parameter>();
		List<String> names = new ArrayList<String>();
		for(Parameter param : params) {
			if(!names.contains(param.getName())) {
				clean.add(param);
				names.add(param.getName());
			}
		}
		return clean.toArray(new Parameter[]{});
	}	

}
