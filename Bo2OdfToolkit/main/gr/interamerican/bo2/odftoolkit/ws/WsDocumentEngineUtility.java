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
package gr.interamerican.bo2.odftoolkit.ws;

import gr.interamerican.bo2.odftoolkit.AbstractDocumentEngineUtility;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentEngineUtility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Implementation of {@link DocumentEngineUtility} based on a WebService
 */
public class WsDocumentEngineUtility extends AbstractDocumentEngineUtility {
	
	/**
	 * {@link Service} instance.
	 */
	private static Service WS;
	
	/**
	 * Creates a new ServiceClientPdfEngine object. 
	 *
	 * @param properties
	 */
	public WsDocumentEngineUtility(Properties properties) {
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

	public String toHtml(byte[] odf) throws DocumentEngineException {
		throw new UnsupportedOperationException("not implemented"); //$NON-NLS-1$
	}

	public byte[] toPdf(byte[] odf) throws DocumentEngineException {
		throw new UnsupportedOperationException("not implemented"); //$NON-NLS-1$
	}

}
