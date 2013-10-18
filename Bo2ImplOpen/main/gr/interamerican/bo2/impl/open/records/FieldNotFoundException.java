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
package gr.interamerican.bo2.impl.open.records;

/**
 * Runtime Exception thrown when a field 
 * is not found in the fields spec of the 
 * record.
 */
public class FieldNotFoundException
extends RuntimeException {
	/**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * missing field.
	 */
	private String field;
	
	/**
	 * Creates a new FieldNotFoundException object. 
	 *
	 * @param field
	 */
	FieldNotFoundException(String field) {
		this.field = field;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {			
		return super.toString() + 
		" - Missing field: " + field; //$NON-NLS-1$
	}
}
