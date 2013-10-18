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

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

/**
 * Abstract base for the implementation of {@link IBeanWithIdAndName}.
 */
public abstract class AbstractBeanWithIdAndName 
implements IBeanWithIdAndName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * name.
	 */
	@Property protected String beanName;
	
	/**
	 * id.
	 */	
	@Property private Integer beanId;
	

}
