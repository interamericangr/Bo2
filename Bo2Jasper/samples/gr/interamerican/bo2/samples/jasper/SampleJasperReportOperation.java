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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jasper.JasperReportOperation;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;

/**
 * Implementation of {@link JasperReportOperation}.
 */
public class SampleJasperReportOperation extends JasperReportOperation {
	
	/**
     * Stream of the input file where the csv is read from. 
     */
    private NamedStream<InputStream> csv;

	/**
	 * Creates a new JasperReportOperationImpl object. 
	 */
	public SampleJasperReportOperation() {
		super("sample_report_output", "sample_report_jasper"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		csv = (NamedStream<InputStream>) nsp.getStream("sample_report_input"); //$NON-NLS-1$
	}

	@Override
	protected Map<String, Object> parameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("RPT_TITLE",	"Sample Jasper Report"); //$NON-NLS-1$ //$NON-NLS-2$
		return parameters;
	}

	@Override
	protected JRDataSource datasource() throws DataException {
		JRCsvDataSource ds = new JRCsvDataSource(csv.getStream());
		ds.setColumnNames(new String[]{"name","surName","gender"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ds.setFieldDelimiter(';');
		ds.setRecordDelimiter("\n"); //$NON-NLS-1$
		return ds;
	}

}
