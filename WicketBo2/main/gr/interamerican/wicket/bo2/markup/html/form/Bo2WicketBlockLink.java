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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.wicket.bo2.callbacks.Bo2WicketBlock;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * A {@link Link} that executes a {@link Bo2WicketBlock}.
 * 
 * @param <T> 
 *        Type of model object. 
 */
public class Bo2WicketBlockLink<T> extends Link<T>{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Block.
	 */
	private Bo2WicketBlock block;

	/**
	 * Creates a new Bo2WicketBlockLink object. 
	 *
	 * @param id
	 * @param block 
	 */
	public Bo2WicketBlockLink(String id, Bo2WicketBlock block) {
		super(id);
		this.block = block;
	}
	
	/**
	 * Creates a new Bo2WicketBlockLink object. 
	 *
	 * @param id
	 * @param model 
	 * @param block 
	 */
	public Bo2WicketBlockLink(String id, IModel<T> model, Bo2WicketBlock block) {
		super(id, model);
		this.block = block;
	}

	@Override
	public void onClick() {
		if(block!=null){
			block.execute();
		}
	}

}
