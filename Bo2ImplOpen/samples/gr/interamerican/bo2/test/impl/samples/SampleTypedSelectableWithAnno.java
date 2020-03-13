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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;
import gr.interamerican.bo2.samples.ibean.IBeanWithId;

/**
 * Sample Class that implements the {@link TypedSelectable} interface and has its delegates mapped to this class properties.
 */
@TypedSelectableProperties(code="id",name="",type="",subtype="null")
public abstract class SampleTypedSelectableWithAnno implements TypedSelectable<Integer>, IBeanWithId {
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TypeId.
	 */
	@Property Long typeId;
	/**
	 * Id.
	 */
	@Property Integer id;
	/**
	 * Id.
	 */
	@Property String name;	
}
