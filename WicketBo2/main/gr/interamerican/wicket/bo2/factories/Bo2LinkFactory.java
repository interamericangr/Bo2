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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.wicket.bo2.callbacks.Bo2WicketBlock;
import gr.interamerican.wicket.bo2.markup.html.form.Bo2WicketBlockLink;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Factory of Bo2 enabled links components. 
 */
public class Bo2LinkFactory {
	
	/**
	 * Creates a new Link that executes a {@link Bo2WicketBlock}.
	 * 
	 * @param id 
	 *        Wicket id.
	 * @param model
	 *        Link model.
	 * @param block
	 *        Bo2WicketBlock that will be executed on link click.
	 *        
	 * @return Returns a new Link that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static Link<String> createLink 
	(final String id, final IModel<String> model, final Bo2WicketBlock block) {
		Link<String> link = new Bo2WicketBlockLink<String>(id, block);
		link.setModel(model);
		return link;
	}
	
	/**
	 * Creates a new Link that executes a {@link Bo2WicketBlock}.
	 * 
	 * The button will be created with a ResourceModel for the expression
	 * link.id , where id is the id specified. 
	 * 
	 * @param id 
	 *        Wicket id.
	 * @param block
	 *        Bo2WicketBlock that will be executed on link click.
	 *        
	 * @return Returns a new Link that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static Link<String> createLink 
	(final String id, final Bo2WicketBlock block) {
		Link<String> link = new Bo2WicketBlockLink<String>(id, block);
		link.setModel(new ResourceModel("link."+id)); //$NON-NLS-1$
		return link;
	}
	
	/**
	 * Private empty constructor of a utility class.
	 */
	private Bo2LinkFactory() {/* empty */}

}
