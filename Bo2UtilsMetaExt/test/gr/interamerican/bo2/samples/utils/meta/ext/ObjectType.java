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
package gr.interamerican.bo2.samples.utils.meta.ext;


import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;


/**
 * ObjectType.
 */
public enum ObjectType implements TranslatableEntryOwner<Long, Long, Long> {
	
    /**
     * 
     */
	OBJECT1 ,
	
    /**
     *
     */
	OBJECT2, 
    
    /**
     * 
     */
	OBJECT3, 
    
    /**
     * 
     */    
	OBJECT4;
	
	
	/**
	 * EnumElement
	 */
	private EnumElement entry = new EnumElement(1000L, this);
	
	public EnumElement getEntry() {
	return entry;
	}

}

	

	

	
	

	
	

