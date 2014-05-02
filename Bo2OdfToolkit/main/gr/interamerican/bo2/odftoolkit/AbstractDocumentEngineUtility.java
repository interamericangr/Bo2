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
package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.doc.DocumentEngineUtility;

import java.util.Properties;

/**
 * Abstract base class of {@link DocumentEngineUtility} implementations.
 */
public abstract class AbstractDocumentEngineUtility implements DocumentEngineUtility {
	
	/**
	 * Host running the open office process.
	 */
	protected String host;
	
	/**
	 * Port on which the open office process runs.
	 */
	protected String port;
	
	/**
	 * Wsdl of a web service facade to the open office process.
	 */
	protected String wsdlLocation;
	
	/**
	 * Name of web service facade to the open office process.
	 */
	protected String serviceName;
	
	/**
	 * JNDI lookup service for EJBs.
	 */
	protected String ejbLookupUrl;
	
	/**
	 * Ejb JNDI name.
	 */
	protected String ejbName;
	
	/**
	 * Initial context factory class.
	 */
	protected String initialCtxFactory;
	
	/**
	 * Creates a new AbstractPdfEngine object. 
	 *
	 * @param properties
	 */
	public AbstractDocumentEngineUtility(Properties properties) {
		host = CollectionUtils.getOptionalProperty(properties, "host"); //$NON-NLS-1$
		port = CollectionUtils.getOptionalProperty(properties, "port"); //$NON-NLS-1$
		wsdlLocation = CollectionUtils.getOptionalProperty(properties, "wsdlLocation"); //$NON-NLS-1$
		serviceName = CollectionUtils.getOptionalProperty(properties, "serviceName"); //$NON-NLS-1$
		ejbLookupUrl = CollectionUtils.getOptionalProperty(properties, "ejbLookupUrl"); //$NON-NLS-1$
		ejbName = CollectionUtils.getOptionalProperty(properties, "ejbName"); //$NON-NLS-1$
		initialCtxFactory = CollectionUtils.getOptionalProperty(properties, "initialCtxFactory"); //$NON-NLS-1$
	}

}
