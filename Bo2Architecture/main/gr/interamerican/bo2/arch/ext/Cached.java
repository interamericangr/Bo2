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
package gr.interamerican.bo2.arch.ext;



/**
 * Cached object.
 * 
 * @param <M> Type of Metadata
 * @param <E> Type of Entry
 * @param <C> Type of entries code.
 */
public interface Cached 
<M extends Cached.Metadata<?>, 
 E extends TypedSelectable<C>,
 C extends Comparable<? super C>> {

	/**
	 * Gets the metadata.
	 * 
	 * @return Returns the metadata.
	 */
	public M getMetadata();
	
	/**
	 * Gets the entry.
	 * 
	 * @return Returns the entry.
	 */
	public E getEntry();
	
	/**
	 * Sets the code.
	 * 
	 * @param code New code.
	 */
	public void setCode(C code);
	
	/**
	 * Gets the code.
	 * 
	 * @return code.
	 */
	public C getCode();
	
	/**
	 * Gets the subset id of this object.
	 * 
	 * @return Gets the id of sub-set to which this object belongs. 
	 */
	public Long getSubsetId();
	
	/**
	 * Sets the subsetId.
	 * 
	 * This method is meaningless if the metadata of this
	 * object specify that it is not sub-typed.
	 * 
	 * @param subsetId
	 */
	public void setSubsetId(Long subsetId);
	
	
	/**
	 * Metadata about the type of an entry.
	 * 
	 * @param <MC> 
	 */
	public interface Metadata<MC extends Comparable<? super MC>> 
	extends TypedSelectable<MC> {
		
		/**
		 * Indication if the {@link TypedSelectable} objects
		 * described by this Metadata object take values from
		 * different sets that depend on the sub-type.
		 * 
		 * @return Returns true if the set of values described
		 *         with this Metadata object depends on the
		 *         sub-type.
		 */
		public boolean isDividedToSubsets(); 
		
	}
	


}
