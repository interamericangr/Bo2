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

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

/**
 * This class exists solely for the proper implementation of the
 * self-drawn wicket components factory. 
 * 
 * @param <T> 
 *        Type of entry.
 * @param <C> 
 * 		  Type of cache code.
 */
public class PalleteCachedEntriesBoPropertyDescriptor
<T extends TypedSelectable<C>, C extends Comparable<? super C>> 
extends MultipleCachedEntriesBoPropertyDescriptor<T, C>{

	/**
	 * Creates a new MultipleCachedEntryForPalleteBoPropertyDescriptor object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param cache
	 * @param codeParser 
	 * @param codeFormatter 
	 */
	public PalleteCachedEntriesBoPropertyDescriptor(
	Long typeId, Long subTypeId, Cache<C> cache, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(typeId, subTypeId, cache, codeParser, codeFormatter);
	}

}
