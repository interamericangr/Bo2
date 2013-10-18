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
package gr.interamerican.bo2.impl.open.jasper;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.annotations.Child;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Operation that creates a JasperReport based on an EntitiesQuery.
 * 
 * @param <Q> 
 *        Type of EntitiesQuery that provides the data. 
 */
public abstract class EntitiesQueryJasperReportOperation<Q extends EntitiesQuery<?>> 
extends JasperReportOperation {
	
	/**
	 * Entities query for the report.
	 */
	@Child
	protected Q query;

	/**
	 * Creates a new EntitiesQueryJasperReportOperation object. 
	 *
	 * @param reportName
	 *        Logical name of the report stream.
	 * @param templateName
	 *        Logical name of the report stream.
	 * @param query 
	 *        EntitiesQuery that provides the data for the report.
	 */
	public EntitiesQueryJasperReportOperation(String reportName, String templateName, Q query) {
		super(reportName, templateName);
		this.query = query;		
	}
	
	@Override
	protected JRDataSource datasource() throws DataException {
		query.execute();
		return new EntitiesQueryJrDatasource(query);
	}

}
