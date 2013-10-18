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
package gr.interamerican.bo2.samples.jasper;

import gr.interamerican.bo2.impl.open.jasper.CriteriaDependentEntitiesQueryJasperReportOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample EntitiesQueryJasperReportOperation.
 */
public class SampleCriteriaDependentEntitiesQueryJasperReportOperation 
extends CriteriaDependentEntitiesQueryJasperReportOperation<SimplePerson, SimplePersonQuery>{

	/**
	 * Creates a new SampleEntitiesQueryJasperReportOperation object.
	 */
	@SuppressWarnings("nls")
	public SampleCriteriaDependentEntitiesQueryJasperReportOperation () {
		super("sample_criteria_entities_report_output", "sample_report_jasper", 
				new SimplePersonQuery(), new SimplePerson());
	}
	
	@Override
	protected Map<String, Object> parameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("RPT_TITLE",	"Sample Entities Jasper Report With Criteria"); //$NON-NLS-1$ //$NON-NLS-2$
		return parameters;
	}

}
