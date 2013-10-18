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
import gr.interamerican.bo2.creation.annotations.DelegateToOtherProperty;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;
import gr.interamerican.bo2.samples.ibean.IBeanWithId;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

/**
 * 
 */
@SuppressWarnings("serial")
@TypedSelectableProperties(code="id",name="",subtype="null",type="")
@DelegateToOtherProperty({"beanName-name","beanId-id"})
public abstract class AbstractTs 
implements TypedSelectable<Integer>, IBeanWithIdAndName, IBeanWithId {
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
