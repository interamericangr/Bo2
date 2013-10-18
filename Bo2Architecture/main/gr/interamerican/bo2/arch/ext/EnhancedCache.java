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
 * {@link EnhancedCache} provides a cache and a {@link Translator}
 * for this cache.
 * 
 * @param <C> Type of cached entries code. 
 * @param <R> Type of translation resource id.
 * @param <L> Type of language id. 
 * 
 */
public interface EnhancedCache<C extends Comparable<? super C>,R,L> {
	
	/**
	 * Gets the {@link Cache} of this control panel.
	 *  
	 * @return Gets the {@link Cache} of this control panel.
	 */
	public Cache<C> getCache();
	
	/**
	 * Gets the {@link Cache} of this control panel.
	 *  
	 * @return Gets the {@link Cache} of this control panel.
	 */
	public Translator<R,L> getTranslator();	

}
