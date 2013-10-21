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
package gr.interamerican.bo2.impl.open.modifications;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.utils.Bo2Utils;
import gr.interamerican.bo2.utils.adapters.CopyFromProperties;
import gr.interamerican.bo2.utils.adapters.Modification;

import java.util.Properties;

/**
 * {@link CriteriaAwareCopyFromProperties} is a sub-type of {@link CopyFromProperties}
 * intended for {@link CriteriaDependent} classes.
 * 
 * This transformation copies not only to the properties of the target object,
 * but also to its criteria properties.
 * 
 * @param <T> Type of CriteriaDependent object.
 * @param <C> Type of criteria.
 * 
 */
public class CriteriaAwareCopyFromProperties<C,T extends CriteriaDependent<C>> 
implements Modification<T>{
	
	/**
	 * Copies properties to the element.
	 */
	private CopyFromProperties<T> copyToElement;
	
	/**
	 * Copies properties to the element's criteria.
	 */
	private CopyFromProperties<C> copyToCriteria;
	
	
	/**
	 * Creates a new CriteriaAwareCopyFromProperties object. 
	 *
	 * @param properties
	 */
	public CriteriaAwareCopyFromProperties(Properties properties) {
		copyToElement = new CopyFromProperties<T>(properties);
		copyToCriteria = new CopyFromProperties<C>(properties);
	}	
	
	@Override
	public T execute(T a) {
		if (a==null) {
			return a;
		}		
		T t = copyToElement.execute(a);	
		C criteria1 = Bo2Utils.getCriteria(t);	
		C criteria2 = copyToCriteria.execute(criteria1);		
		t.setCriteria(criteria2);
		return t;
	}
	
	

}
