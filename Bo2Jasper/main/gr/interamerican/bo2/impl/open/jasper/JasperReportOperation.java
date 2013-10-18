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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Abstract operation that creates a Jasper report.
 */
public abstract class JasperReportOperation extends AbstractOperation {
	
	/**
	 * Logical name of the report stream.
	 */
	private String reportName;
	
	/**
	 * Logical name of the report stream.
	 */
	private String templateName;
	
    /**
	 * Creates a new JasperReportOperation object. 
	 *
	 * @param reportName
	 *        Logical name of the report stream.
	 * @param templateName
	 *        Logical name of the report stream.
	 */
	public JasperReportOperation(String reportName, String templateName) {
		super();
		this.reportName = reportName;
		this.templateName = templateName;
	}

	/** 
     * Stream of the output file. The report is saved in this stream.
     */
    protected NamedStream<OutputStream> report;
    
    /**
     * Stream of the input file where the jrxml report template file is read from. 
     */
    protected NamedStream<InputStream> template;
    
    /**
     * Gets the parameters of the report.
     * 
     * @return Returns the parameters of the report.
     */
    protected abstract Map<String,Object> parameters();
    
    /**
     * Gets the datasource of the report.
     * 
     * @return Returns the datasource of the report.
     * 
     * @throws DataException 
     */
    protected abstract JRDataSource datasource() throws DataException;
    
    
    @SuppressWarnings("unchecked")
	@Override
    public void init(Provider parent) throws InitializationException {
    	super.init(parent);
    	NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
    	report = (NamedStream<OutputStream>) nsp.getStream(reportName);
    	template = (NamedStream<InputStream>) nsp.getStream(templateName);
    }
        
	@Override
	public void execute() throws LogicException, DataException {
		try {
			Map<String, Object> params = parameters();
			JRDataSource ds = datasource();		
			JasperPrint jp = JasperFillManager.fillReport(template.getStream(), params, ds);
			JasperExportManager.exportReportToPdfStream(jp, report.getStream());			
		} catch (JRException jrex) {
			throw new DataException(jrex);
		}
	}
	

}
