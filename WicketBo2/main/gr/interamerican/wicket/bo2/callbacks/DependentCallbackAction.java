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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.wicket.callback.CallbackAction;

/**
 * A {@link DependentCallbackAction} is an action that depends
 * on a supplied object.
 * <p/>
 * Typically, the caller will redirect to a WebPage built with data
 * from the supplied object
 */
public interface DependentCallbackAction 
extends CallbackAction, CriteriaDependent<Object> {
	/* empty */
}
