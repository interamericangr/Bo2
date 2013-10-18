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
package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.creation.annotations.Delegate2Map;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.samples.ibean.IBeanWith2Strings;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

import java.util.Map;

/**
 * Abstract base for the implementation of {@link IBeanWithIdAndName}.
 */
public abstract class AbstractBeanUsingMaps 
implements IBeanWithIdAndName, IBeanWith2Strings {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4L;
	
	/**
	 * Map for beanName and BeanId
	 */
	@Delegate2Map({"beanName","beanId"})
	private Map<String, Object> map1;
	
	/**
	 * Map for beanName and string1
	 */
	@Delegate2Map("string1")
	Map<String, Object> map2;

	/**
	 * string 1.
	 */
	@Property String string2;
	

	

}
