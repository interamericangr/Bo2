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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.ext.Cache;

/**
 * Fills a {@link Cache}.
 * 
 * @param <C>
 *        Type of code in the cache.
 *        
 * @deprecated Use {@link gr.interamerican.bo2.impl.open.operations.AbstractCacheLoaderOperation}
 *             instead. 
 */
public abstract class AbstractCacheLoaderOperation<C extends Comparable<? super C>> 
extends gr.interamerican.bo2.impl.open.operations.AbstractCacheLoaderOperation<C> {

	/**
	 * Creates a new AbstractCacheLoaderOperation object. 
	 *
	 * @param cache
	 */
	public AbstractCacheLoaderOperation(Cache<C> cache) {
		super(cache);
	}
	
	
}
