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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.Utils;


/**
 * This class provides an implementation of {@link AbstractKey} that
 * is based on an array of values. The size of the array is dynamic
 * and can change during the runtime.
 * 
 * The <code>setElement(position,element)</code> method assigns a value to the
 * element on the position specified. <br/>
 */
public class ValuesBasedKey 
extends AbstractKey {
	
	/**
	 * Fields.
	 */
	private Object[] elements;
	
	@Override
	protected Object[] getElements() {		
		return elements;
	}
	
	
	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new DynamicKeyImpl object. 
	 *
	 */
	public ValuesBasedKey() {
		super();
	}
	
	/**
	 * Creates a new {@link ValuesBasedKey} object. 
	 *
	 * @param keyFields Fields of this key.
	 */
	public ValuesBasedKey(Object... keyFields) {
		super();
		this.elements = ArrayUtils.copyOf(keyFields, keyFields.length);
	}

	/**
	 * Sets a new field on a specified position.
	 * 
	 * First element is on position 1, second on 2 etc.
	 * 
	 * @param position Position to set the field.
	 * @param element New element to set to the <code>element()</code>
	 *        array.
	 */
	public void setElement(int position,Object element) {		
		elements = Utils.notNull(elements,new Object[position]);
		elements = ArrayUtils.ensureCapacity(elements, position);
		elements[position-1]=element;
	}
	
	/**
	 * Gets the field from the specified position.
	 * 
	 * First element is on position 1, second on 2 etc.
	 * 
	 * @param position Position of field.
	 * 
	 * @return Returns the field that has the specified position.
	 */
	public Object getElement(int position) {
		elements = Utils.notNull(elements,new Object[position]);
		elements = ArrayUtils.ensureCapacity(elements, position);
		return elements[position-1];
	}

	
}
