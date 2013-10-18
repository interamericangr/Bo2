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
package gr.interamerican.wicket.samples.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.workers.ArrayIteratorQuery;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;

/**
 * Query.
 */
public class BeanWithOneFieldQuery 
extends ArrayIteratorQuery<BeanWith1Field> 
implements EntitiesQuery<BeanWith1Field>, CriteriaDependent<BeanWith1Field> {
	
	public void setCriteria(BeanWith1Field criteria) {
		/* empty */
	}
	
	public BeanWith1Field getCriteria() {		
		return null;
	}

	/**
	 * Creates a new BeanQueryImpl object. 
	 */
	public BeanWithOneFieldQuery() {
		super(new BeanWith1Field[]{new BeanWith1Field(1L)});
	}
	
}
