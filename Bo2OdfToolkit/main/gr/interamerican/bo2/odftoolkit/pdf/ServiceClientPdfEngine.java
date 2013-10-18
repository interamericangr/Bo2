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
package gr.interamerican.bo2.odftoolkit.pdf;

import gr.interamerican.bo2.utils.doc.PdfEngine;
import gr.interamerican.bo2.utils.doc.PdfEngineException;
import gr.interamerican.bo2.utils.doc.PdfEngineWebService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * {@link PdfEngine} that delegates the conversion to a web service.
 */
public class ServiceClientPdfEngine extends AbstractPdfEngine
implements PdfEngine {
	
	/**
	 * {@link Service} instance.
	 */
	private static Service WS;
	
	/**
	 * Creates a new ServiceClientPdfEngine object. 
	 *
	 * @param properties
	 */
	public ServiceClientPdfEngine(Properties properties) {
		super(properties);
		try {
			initService();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Initializes {@link Service} instance.
	 * @throws MalformedURLException
	 */
	void initService() throws MalformedURLException {
		if(WS==null) {
			URL wsdlURL = new URL(wsdlLocation);
			QName serviceQName = new QName("http://doc.utils.bo2.interamerican.gr/", serviceName); //$NON-NLS-1$
			WS = Service.create(wsdlURL, serviceQName);
		}
	}
	
	@Override
	public byte[] toPdf(byte[] odf) throws PdfEngineException {
		PdfEngineWebService pdfEngineWebService = WS.getPort(PdfEngineWebService.class);
		return pdfEngineWebService.toPdf(odf);
	}

}
