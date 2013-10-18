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
import gr.interamerican.bo2.utils.doc.PdfEngineEjb;
import gr.interamerican.bo2.utils.doc.PdfEngineException;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * {@link PdfEngine} that delegates the conversion to an EJB.
 */
public class EjbClientPdfEngine extends AbstractPdfEngine
implements PdfEngine {
	
	/**
	 * Creates a new ServiceClientPdfEngine object. 
	 *
	 * @param properties
	 */
	public EjbClientPdfEngine(Properties properties) {
		super(properties);
	}
	
	@Override
	public byte[] toPdf(byte[] odf) throws PdfEngineException {
		PdfEngineEjb proxy = null;
		try {
			proxy = getProxy();
		}catch (NamingException e) {
			throw new PdfEngineException(e);
		}
		return proxy.toPdf(odf);
	}
	
	/**
	 * @return Returns an ejb proxy.
	 * @throws NamingException
	 */
	private PdfEngineEjb getProxy() throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, initialCtxFactory);
		props.put(Context.PROVIDER_URL, ejbLookupUrl);
		Context context = new InitialContext(props);
		return (PdfEngineEjb) context.lookup(ejbName);
	}

}
