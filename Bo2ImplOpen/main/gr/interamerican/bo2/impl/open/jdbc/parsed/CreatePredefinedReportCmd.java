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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcCommand;
import gr.interamerican.bo2.utils.sql.PsMetadataUtils;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * {@link CreatePredefinedReportCmd} is a factory that creates a 
 * {@link PredefinedReport} from an SQL statement.
 * 
 * This command will create a {@link PreparedStatement} from the
 * specified SQL statement, it will parse its metadata and it will
 * create the statement.
 */
public class CreatePredefinedReportCmd 
extends JdbcCommand {
	/**
	 * Sql statement.
	 */
	String sql;
	
	/**
	 * Report.
	 */
	PredefinedReport report;

	/**
	 * Gets the sql.
	 *
	 * @return Returns the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * Assigns a new value to the sql.
	 *
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * Gets the report.
	 *
	 * @return Returns the report
	 */
	public PredefinedReport getReport() {
		return report;
	}

	@Override
	public void work() throws DataException {
		try {
			List<String> parameterNames = SqlUtils.getParameterNames(sql);
			String stmt = SqlUtils.replaceParametersWithMarkers(sql);
			PreparedStatement ps = getPreparedStatement(stmt);
			Column[] columns = PsMetadataUtils.getColumns(ps);
			Parameter[] params = PsMetadataUtils.getParameters(ps);		
			int i = 0;
			for (String paramName : parameterNames) {
				params[i].setName(paramName);
				i++;
			}	
			report = new PredefinedReport();
			report.setSql(sql);
			report.setParameters(params);
			report.setColumns(columns);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

}
