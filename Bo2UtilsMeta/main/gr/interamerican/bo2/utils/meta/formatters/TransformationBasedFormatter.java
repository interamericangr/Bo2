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
package gr.interamerican.bo2.utils.meta.formatters;

import gr.interamerican.bo2.utils.adapters.AnyOperation;

/**
 * Formatter that is based on a transformation to String.
 * 
 * @param <T> 
 *        Type of object being transformed.
 * 
 */
public class TransformationBasedFormatter<T>
implements Formatter<T> {
	/**
	 * Transformation to String.
	 */
	AnyOperation<T, String> transformation;

	/**
	 * Creates a new TransformationBasedFormatter object. 
	 *
	 * @param transformation
	 */
	public TransformationBasedFormatter(AnyOperation<T, String> transformation) {
		super();
		this.transformation = transformation;
	}
	
	public String format(T t) {
		return transformation.execute(t);
	}

}
