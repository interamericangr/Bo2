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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

/**
 * This class exists solely for the proper implementation of the
 * self-drawn wicket components factory. 
 * 
 * @param <T> 
 *        Type of entry Owners.
 * @param <C> 
 * 		  Type of cache code.
 */
public class PalleteCachedEntriesOwnersBoPropertyDescriptor 
<T extends TranslatableEntryOwner<C,?,?>,C extends Comparable<? super C>> 
extends MultipleCachedEntriesOwnersBoPropertyDescriptor<T, C>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new PalleteCachedEntriesOwnersBoPropertyDescriptor object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param cacheName
	 * @param codeParser 
	 * @param codeFormatter 
	 */
	public PalleteCachedEntriesOwnersBoPropertyDescriptor(
	Long typeId, Long subTypeId, String cacheName, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(typeId, subTypeId, cacheName, codeParser, codeFormatter);
	}

}
