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

import gr.interamerican.bo2.impl.open.jasper.EntitiesQueryJasperReportOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample EntitiesQueryJasperReportOperation.
 */
public class SampleEntitiesQueryJasperReportOperation 
extends EntitiesQueryJasperReportOperation<SimplePersonQuery>{

	/**
	 * Creates a new SampleEntitiesQueryJasperReportOperation object.
	 */
	@SuppressWarnings("nls")
	public SampleEntitiesQueryJasperReportOperation () {
		super("sample_entities_report_output", "sample_report_jasper", new SimplePersonQuery());
	}
	
	@Override
	protected Map<String, Object> parameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("RPT_TITLE",	"Sample Entities Jasper Report"); //$NON-NLS-1$ //$NON-NLS-2$
		return parameters;
	}

}
