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
 * An object that can have translations in different
 * languages.
 * 
 * @param <R> Type of translation resource id.
 * @param <L> Type of language id. 
 */
public interface Translatable<R, L> {
	
	/**
	 * Gets the identifier of the resource related with
	 * this Translatable.
	 * 
	 * In order to be able to translate the object to different
	 * languages, a Translatable object is assigned a unique resource
	 * identifier. The resource identifier is associated with the
	 * translation for each supported languages. The resource id
	 * can be an object of any type. The type of the resource id
	 * depends on the implementation of the translation machine.
	 * 
	 *  
	 * @return Returns the resource id of this object.
	 */
	public R getTranslationResourceId();
	
	/**
	 * Gets the translation of this object to the specified language.
	 * 
	 * @param languageId Id of the language. 
	 * 
	 * @return Returns the translation to the specified language.
	 */
	public String getTranslation(L languageId);

}
