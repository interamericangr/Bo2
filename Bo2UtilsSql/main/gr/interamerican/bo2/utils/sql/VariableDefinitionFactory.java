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
package gr.interamerican.bo2.utils.sql;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;

/**
 * {@link VariableDefinition} factory.
 */
public class VariableDefinitionFactory {
	
	/**
	 * Creates a {@link VariableDefinition} from a {@link Column}.
	 * 
	 * @param column 
	 *        Column.
	 * @param <T> 
	 *        Variable type.
	 *        
	 * @return Returns the VariableDefinition.
	 */	
	public static <T> VariableDefinition<T> create(Column column) {
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) column.getColumnType().getJavaType();
		String name = Utils.notNull(column.getAlias(), column.getName());
		return new VariableDefinition<T>(name, type);
	}
	
	/**
	 * Creates an array of {@link VariableDefinition}s from 
	 * an array of {@link Column}s.
	 * 
	 * @param columns 
	 *        Array of Column objects.
	 *        
	 * @return Returns the VariableDefinition.
	 */	
	public static VariableDefinition<?>[] create(Column[] columns) {
		VariableDefinition<?>[] defs = new VariableDefinition<?>[columns.length];
		for (int i = 0; i < defs.length; i++) {
			defs[i] = create(columns[i]);
		}
		return defs;
	}
	
	
	/**
	 * Creates a {@link VariableDefinition} from a {@link Parameter}.
	 * 
	 * @param parameter 
	 *        Parameter.
	 * @param <T> 
	 *        Variable type.
	 *        
	 * @return Returns the VariableDefinition.
	 */	
	public static <T> VariableDefinition<T> create(Parameter parameter) {
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) parameter.getType().getJavaType();
		String name = parameter.getName();
		return new VariableDefinition<T>(name, type);
	}
	
	/**
	 * Creates an array of {@link VariableDefinition}s from 
	 * an array of {@link Parameter}s.
	 * 
	 * @param parameters 
	 *        Array of parameters objects.
	 *        
	 * @return Returns the VariableDefinition.
	 */	
	public static VariableDefinition<?>[] create(Parameter[] parameters) {
		VariableDefinition<?>[] defs = new VariableDefinition<?>[parameters.length];
		for (int i = 0; i < defs.length; i++) {
			defs[i] = create(parameters[i]);
		}
		return defs;
	}
	

}
