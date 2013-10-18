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
package gr.interamerican.bo2.impl.open.doc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.doc.DocumentEngine;

import java.util.Properties;

/**
 * Implementation of {@link DocumentEngineProvider}.
 */
public class DocumentEngineProviderImpl 
implements DocumentEngineProvider {

	/**
	 * Parser.
	 */
	DocumentEngine documentEngine;

	/**
	 * Creates a new DocumentEngineProviderImpl object. 
	 *
	 * @param documentEngine
	 *        DocumentEngine for the new object.
	 */
	public DocumentEngineProviderImpl(DocumentEngine documentEngine) {
		super();
		this.documentEngine = documentEngine;
	}
	
	/**
	 * 
	 * Creates a new ParserProviderImpl object. 
	 *
	 * @param parserClass
	 *        Class of the parser.
	 */
	public DocumentEngineProviderImpl(String parserClass) {
		super();
		documentEngine = ReflectionUtils.newInstance(parserClass);
	}
	
	/**
	 * 
	 * Creates a new ParserProviderImpl object. 
	 *
	 * @param properties
	 *        Initialization properties. of the parser.
	 */
	public DocumentEngineProviderImpl(Properties properties) {
		super();
		String engineClass = CollectionUtils.getMandatoryProperty(properties, "documentEngineClass"); //$NON-NLS-1$
		
		try {
			Class<?> engineClazz = Class.forName(engineClass);
			documentEngine = (DocumentEngine) ReflectionUtils.newInstance(engineClazz, properties);
		}catch(ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}
	
	public void close() throws DataException {
		/* empty */
	}
	
	public DocumentEngine getDocumentEngine() {
		return documentEngine;
	}

}
