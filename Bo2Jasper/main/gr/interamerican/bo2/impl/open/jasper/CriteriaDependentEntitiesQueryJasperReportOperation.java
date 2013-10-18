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
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import net.sf.jasperreports.engine.JRDataSource;


/**
 * Operation that creates a JasperReport based on an {@link EntitiesQuery} 
 * that is also {@link CriteriaDependent}.
 * 
 * @param <C>
 *        Type of criteria.
 * @param <Q> 
 *        Type of EntitiesQuery that provides the data.
 */
public abstract class CriteriaDependentEntitiesQueryJasperReportOperation 
<C, Q extends EntitiesQuery<?> & CriteriaDependent<C>>
extends EntitiesQueryJasperReportOperation<Q> {
	
	/**
	 * Criteria.
	 */
	protected C criteria;

	/**
	 * Creates a new CriteriaDependentEntitiesQueryJasperReportOperation object. 
	 *
	 * @param reportName
	 *        Logical name of the report stream.
	 * @param templateName
	 *        Logical name of the report stream.
	 * @param query 
	 *        EntitiesQuery that provides the data for the report.
	 * @param criteria 
	 *        Criteria.
	 */
	public CriteriaDependentEntitiesQueryJasperReportOperation
	(String reportName, String templateName, Q query, C criteria) {
		super(reportName, templateName, query);
		this.criteria = criteria;
	}
	
	@Override
	protected JRDataSource datasource() throws DataException {
		query.setCriteria(criteria);
		return super.datasource();
	}

	/**
	 * Gets the criteria.
	 *
	 * @return Returns the criteria
	 */
	public C getCriteria() {
		return criteria;
	}

	/**
	 * Assigns a new value to the criteria.
	 *
	 * @param criteria the criteria to set
	 */
	public void setCriteria(C criteria) {
		this.criteria = criteria;
	}	

}
