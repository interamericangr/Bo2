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
package gr.interamerican.wicket.util.resource;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * Utilities for localized String resources.
 */
public class StringResourceUtils {
	
	/**
	 * If {@code userSuppliedResource} is not null, then returns a model with its value.
	 * In any other case, returns a {@link StringResourceModel} for the specified
	 * {@code resourceKey}, {@code component} and {@code defaultValue}.
	 * 
	 * @see StringResourceModel
	 * 
	 * @param resourceKey
	 *        Resource key for resource bundle lookup,
	 * @param component 
	 *        Component whose resource bundle hierarchy will be looked up. 
	 * @param userSuppliedResource
	 *        Resource model supplied explicitly by the user.
	 * @param defaultValue
	 *        Default value, if everything else fails.
	 * 
	 * @return Returns an {@link IModel} of the resource.
	 */
	public static IModel<String> getResourceModel(
	String resourceKey, Component component, IModel<String> userSuppliedResource, String defaultValue) {
		if(userSuppliedResource != null) {
			return userSuppliedResource;
		}
		return new StringResourceModel(resourceKey, component, null, defaultValue);
	}
	
	/**
	 * Hidden constructor of utility class.
	 */
	private StringResourceUtils() { /* hidden, empty */ }

}
