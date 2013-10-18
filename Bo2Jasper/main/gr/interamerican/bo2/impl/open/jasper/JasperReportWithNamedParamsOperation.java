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

import java.util.Map;

/**
 * 
 */
public abstract class JasperReportWithNamedParamsOperation 
extends JasperReportOperation {

	/**
	 * Creates a new JasperReportWithNamedParamsOperation object. 
	 *
	 * @param reportName
	 * @param templateName
	 */
	public JasperReportWithNamedParamsOperation(String reportName, String templateName) {
		super(reportName, templateName);
	}
	
	
	@Override
	protected Map<String, Object> parameters() {
		Map<String,Object> map = getNamedParameters();		
		map.put("REPORT_LOCALE", new java.util.Locale("el", "GR")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return map;
	}

}
