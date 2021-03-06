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
package gr.interamerican.wicket.callback;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Action Performed when a {@link List} of items are picked.
 * 
 * @param <B>
 *            Type of picked items
 */
@FunctionalInterface
public interface MultiplePickAction<B> extends Serializable {

	/**
	 * The Action
	 * 
	 * @param target
	 *            Current {@link AjaxRequestTarget}
	 * @param bean
	 *            The Picked Items
	 */
	void doPick(AjaxRequestTarget target, List<B> bean);
}