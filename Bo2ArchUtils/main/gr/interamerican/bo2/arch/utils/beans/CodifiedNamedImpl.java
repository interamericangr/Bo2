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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.Named;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.utils.Utils;

/**
 * Basic implementation of {@link Codified}.
 * @param <C> Type of code.
 */
public class CodifiedNamedImpl<C extends Comparable<? super C>> 
implements Codified<C>, Named {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code.
	 */
	private C code;
	
	/**
	 * name.
	 */
	private String name;
	
	
	/**
	 * Creates a new CodifiedImpl object. 
	 *
	 */
	public CodifiedNamedImpl() {
		super();
	}		
	
	/**
	 * Creates a new CodifiedImpl object. 
	 *
	 * @param code
	 * @param name
	 */
	public CodifiedNamedImpl(C code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return Returns the code
	 */
	public C getCode() {
		return code;
	}
	/**
	 * Assigns a new value to the code.
	 *
	 * @param code the code to set
	 */
	public void setCode(C code) {
		this.code = code;
	}
	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Codified<C> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), this.getCode());
	}
	
}
