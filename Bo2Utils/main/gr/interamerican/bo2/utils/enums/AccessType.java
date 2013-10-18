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
package gr.interamerican.bo2.utils.enums;

/**
 * Access type.
 */
public enum AccessType {
	
	/**
	 * A property with a getter only.
	 */
	READ_ONLY(true, false),
	
	/**
	 * A property with a setter only.
	 */
	WRITE_ONLY(false, true),
	
	/**
	 * A property with a getter and a setter.
	 */
	READ_WRITE(true, true);
	
	/**
	 * Returns the appropriate access type.
	 * 
	 * @param readable
	 * @param modifiable
	 * 
	 * @return Returns the appropriate access type.
	 */
	public static AccessType accessType(boolean readable, boolean modifiable) {
		if (readable && modifiable) {
			return AccessType.READ_WRITE;
		}
		if (!readable && modifiable) {
			return WRITE_ONLY;
		}
		if (readable && !modifiable) {
			return READ_ONLY;
		}
		return null;
	}
	
	
	/**
	 * Indicates if the property has a getter.
	 */
	private boolean readable;
	
	/**
	 * Indicates if the property has a setter;
	 */
	private boolean modifiable;
	
	/**
	 * Creates a new AccessType object. 
	 *
	 * @param readable
	 * @param modifiable
	 */
	private AccessType(boolean readable, boolean modifiable) {
		this.readable = readable;
		this.modifiable = modifiable;
	}

	/**
	 * Gets the hasGetter.
	 *
	 * @return Returns true if the property has a getter
	 */
	public boolean isReadable() {
		return readable;
	}

	/**
	 * Gets the hasSetter.
	 *
	 * @return Returns true of the property has a setter
	 */
	public boolean isModifiable() {
		return modifiable;
	}
	
	

}
