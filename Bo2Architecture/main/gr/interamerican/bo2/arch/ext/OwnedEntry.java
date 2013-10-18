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
 * An entry that knows its owner.
 * 
 * @param <C> Type of code. 
 * @param <R> Type of translation resource id.
 * @param <L> Type of language id.
 */
public interface OwnedEntry<C extends Comparable<? super C>, R, L> 
extends TranslatableEntry<C, R, L> {
	
	/**
	 * Gets the owner of this entry.
	 * 
	 * @return Returns the owner of this entry.
	 */
	public TranslatableEntryOwner<C, R, L> getOwner();

}
