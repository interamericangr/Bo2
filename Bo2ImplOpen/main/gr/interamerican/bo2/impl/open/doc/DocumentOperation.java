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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

/** 
 * Abstract base class for operations on a {@link BusinessDocument}. <br/>
 * 
 * The operation's <code>init(provider)</code> method acquires a 
 * {@link DocumentEngine} which is the available for document
 * specific tasks. <br/>
 * The operation also would wrap any {@link DocumentEngineException}
 * thrown by the document procesing API into a {@link DataException}.
 */
public abstract class DocumentOperation 
extends AbstractOperation {
	
	/**
	 * Document engine.
	 */
	protected DocumentEngine documentEngine;
 
	@Override
	public void init(Provider parent) throws InitializationException {
        super.init(parent);
        DocumentEngineProvider docProvider = getResource(DocumentEngineProvider.class);
        documentEngine = docProvider.getDocumentEngine();
	}
	
	
	@Override
	public void execute() throws LogicException, DataException {
		try {
			work();
		} catch (DocumentEngineException e) {
			throw new DataException(e);
		}
	}
	
	/**
	 * Creates the documents.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws DocumentEngineException
	 */
	protected abstract void work() 
	throws DataException, LogicException, DocumentEngineException;

}
