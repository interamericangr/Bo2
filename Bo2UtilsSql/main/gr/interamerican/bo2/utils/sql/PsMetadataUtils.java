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

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.types.Type;
import gr.interamerican.bo2.utils.sql.types.TypeUtils;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utilities about PreparedStatement metadata.
 */
public class PsMetadataUtils {
	
	/**
	 * Creates a new PsMetadataUtils object. 
	 *
	 */
	private PsMetadataUtils() {
		super();
	}

	/**
	 * Gets the columns of a prepared query statement.
	 * 
	 * @param ps
	 *        Prepared statement.
	 *        
	 * @return Returns the columns.
	 * 
	 * @throws SQLException 
	 */
	public static Column[] getColumns(PreparedStatement ps) throws SQLException {
		ResultSetMetaData rsMeta = ps.getMetaData();
		Column[] columns = new Column[rsMeta.getColumnCount()];
		for (int i = 0; i < columns.length; i++) {
			Column col = new Column();
			int idx = i+1;
			String name = rsMeta.getColumnName(idx);
			name = StringUtils.uScore2camelCase(name);
			String label = rsMeta.getColumnLabel(idx);
			int length = rsMeta.getColumnDisplaySize(idx);
			int scale = rsMeta.getScale(idx);
			String typeName = rsMeta.getColumnTypeName(idx);
			int sqlType = rsMeta.getColumnType(idx);
			Type<?> type = TypeUtils.getTypeOfSqlType(sqlType);
			if (type==null) {
				notSupported(typeName);
			}
			String tbSchema = rsMeta.getSchemaName(idx);
			String tbName = rsMeta.getTableName(idx);
			//String className = rsMeta.getColumnClassName(idx);
			col.setAlias(null);
			col.setName(name);
			col.setLabel(label);
			col.setColumnNo(idx);
			col.setColumnType(type);
			col.setLength(length);
			col.setScale(scale);
			col.setTbCreator(tbSchema);
			col.setTbName(tbName);
			columns[i] = col;
		}
		return columns;
	}
	
	/**
	 * Gets the parameters of a prepared statement.
	 * 
	 * @param ps
	 *        Prepared statement.
	 *        
	 * @return Returns the parameters of the statement.
	 * 
	 * @throws SQLException
	 */
	public static Parameter[] getParameters(PreparedStatement ps) throws SQLException {
		ParameterMetaData parmMeta = ps.getParameterMetaData();
		Parameter[] params = new Parameter[parmMeta.getParameterCount()];
		for (int i = 0; i < params.length; i++) {
			int idx = i+1;
			Parameter param = new Parameter();
			String name = "parm" + Integer.toString(idx); //$NON-NLS-1$
			//String className = parmMeta.getParameterClassName(param);
			String typeName = parmMeta.getParameterTypeName(idx);
			int sqlType = parmMeta.getParameterType(idx);
			Type<?> type = TypeUtils.getTypeOfSqlType(sqlType);
			if (type==null) {
				notSupported(typeName);
			}
			param.setName(name);
			param.setType(type);
			params[i] = param;
		}
		return params;
	}
	
	/**
	 * Throws a RuntimeException when a SQL type is not supported.\
	 *
	 * @param typeName
	 *        Name of SQL type.
	 */
	static void notSupported(String typeName) {
		String msg = typeName + " SQL type is not supported"; //$NON-NLS-1$
		throw new RuntimeException(msg);
	}

}
