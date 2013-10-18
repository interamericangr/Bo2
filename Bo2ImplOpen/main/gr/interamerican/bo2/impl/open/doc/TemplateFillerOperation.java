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

import gr.interamerican.bo2.utils.doc.BusinessDocument;

/**
 * Operation that fills a document template.
 * 
 * This operation takes its data from a model object. The variable
 * fields in the template should have been defined in a manner that 
 * makes it possible for the TemplateFillerOperation to bind them
 * with the model object's fields. 
 * 
 * @param <T> 
 *        Type of model object.
 */
public abstract class TemplateFillerOperation<T> 
extends DocumentOperation {
	
	/**
	 * model object.
	 */
	T model;
	
	/**
	 * Document template.
	 */
	BusinessDocument document;

	/**
	 * Gets the model.
	 *
	 * @return Returns the model
	 */
	public T getModel() {
		return model;
	}

	/**
	 * Assigns a new value to the model.
	 *
	 * @param model the model to set
	 */
	public void setModel(T model) {
		this.model = model;
	}

	/**
	 * Gets the document.
	 *
	 * @return Returns the document
	 */
	public BusinessDocument getDocument() {
		return document;
	}

	/**
	 * Assigns a new value to the document.
	 *
	 * @param document the document to set
	 */
	public void setDocument(BusinessDocument document) {
		this.document = document;
	}

}
