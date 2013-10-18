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
package gr.interamerican.bo2.samples.bean;

import gr.interamerican.bo2.samples.anno.Anno;
import gr.interamerican.bo2.samples.ibean.IBeanWithReadOnlyAndWriteOnly;

/**
 * Implementation of BeanWithReadOnlyAndWriteOnly, but it has
 * additionally one static method and one setter.
 */
public class BeanWithReadOnlyAndWriteOnly 
implements IBeanWithReadOnlyAndWriteOnly {
	
	/**
	 * accessible.
	 */
	private static boolean accessible = true;
	
	/**
	 * state.
	 */
	@Anno
	private String state; 
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * id.
	 */
	private Long id;

	
	public boolean isAccessible() {	
		return accessible;
	}
	
	public void setState(String state) {
		this.state = state;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Assigns a new value to the accessible.
	 *
	 * @param accessible the accessible to set
	 */
	public static void setAccessible(boolean accessible) {
		BeanWithReadOnlyAndWriteOnly.accessible = accessible;
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(Integer name) {
		this.name = Integer.toString(name);
	}
	
	/**
	 * Initializes.
	 */
	public void intialize() {/* empty */}
	
}
