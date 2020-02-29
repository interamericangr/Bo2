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

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.bo2.utils.attributes.Output;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.callback.ProcessAction;

/**
 * A {@link ProcessAction} that is based on a bo2 {@link Operation} with
 * {@link Input} and {@link Output} of the type that is gonna be consumed.
 * 
 * @param <B>
 *            Type of processed item
 * @param <O>
 *            The Type of the Operation
 */
public class InputOutputOperationAction<B, O extends Operation & Input<B> & Output<B>>
implements ProcessAction<B> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class of the invoked Operation
	 */
	final Class<O> clz;

	/**
	 * Public Constructor.
	 * 
	 * @param clz
	 *            Class of the invoked Operation
	 */
	public InputOutputOperationAction(Class<O> clz) {
		this.clz = clz;
	}

	@Override
	public B process(B bean) throws Exception {
		O op = Bo2WicketRequestCycle.open(clz);
		op.setInput(bean);
		op.execute();
		return op.getOutput();
	}
}