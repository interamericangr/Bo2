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
package gr.interamerican.wicket.utils;

import gr.interamerican.bo2.utils.beans.Pair;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;

/**
 * 
 */
public class LinkUtils {

	/**
	 * Private empty constructor of a utility class.
	 */
	private LinkUtils() {
		/* empty */
	}
	
	/**
	 * Creates a link to a page.
	 *  
	 * @param id 
	 *        Id of the link.
	 * @param page
	 *        Class of the page where the link links. 
	 * 
	 * @return Returns the link.
	 */
	public static Link<String> createLink(final String id, final Class<? extends Page> page) {
		Link<String> link = new Link<String>(id){
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void onClick() {
				 setResponsePage(page);
		     }
		};
		return link;
	}
	
	/**
	 * Creates a link.
	 * 
	 * The name of the page class is used as id of the link.
	 *
	 * @param page
	 *        Class of the page where the link links.
	 * 
	 * @return Returns the link.
	 */
	public static Link<String> createLink(final Class<? extends Page> page) {
		String id = page.getName();
		return createLink(id, page);
	}
	
	/**
	 * Creates a link.
	 * 
	 * @param linkDesc Link description.
	 * 
	 * @return Returns the link.
	 */
	public static Link<String> createLink(final Pair<String,Class<? extends Page>> linkDesc) {
		return createLink(linkDesc.getLeft(),linkDesc.getRight());
	}
	
	

}
