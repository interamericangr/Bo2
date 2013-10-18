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
 * A translator can translate resource identifiers
 * to their descriptions.
 * 
 * @param <R> Type of translation resource id.
 * @param <L> Type of language id. 
 */
public interface Translator<R,L> {
	
	/**
	 * Gets the translation to the language specified by the
	 * <code>languageId</code> of the message identified by
	 * the specified <code>resourceId</code>
	 *  
	 * @param resourceId 
	 *        Identifier of translatable resource.
	 * @param languageId
	 *        Language identifier.
	 * 
	 * @return Returns the translation to the specified language
	 *         of the specified resource.
	 */
	public String translate(R resourceId, L languageId);	
	
	/**
	 * Learns a translation.
	 * 
	 * @param resourceId ResourceId related with the translation.
	 * @param languageId Id of the language of translation.
	 * @param translation Translation text.
	 */
	public void learn(R resourceId, L languageId, String translation);

}
