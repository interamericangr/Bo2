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
package gr.interamerican.bo2.samples.jasper;

import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.workers.ArrayIteratorQuery;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.Map;

/**
 * This query is criteria dependent. <br/>
 * 
 * Setting the criteria has the following effect.
 * All entities fetched by the query, will have its
 * properties updated 
 */
public class SimplePersonQuery 
extends ArrayIteratorQuery<SimplePerson>
implements CriteriaDependent<SimplePerson> {
	
	/**
	 * Array that contains the elements. 
	 */
	@SuppressWarnings("nls")
	static final SimplePerson[] PERSONS = {
			new SimplePerson("John", "Doe", "Male"),
			new SimplePerson("Jane", "Doe", "Female"),
			new SimplePerson("Jessica", "Rabbit", "Female")
	};
	
	/**
	 * criteria.
	 */
	SimplePerson criteria;

	/**
	 * Creates a new MockUserQuery object. 
	 *
	 */
	public SimplePersonQuery() {
		super(PERSONS);
	}

	/**
	 * Gets the criteria.
	 *
	 * @return Returns the criteria
	 */
	public SimplePerson getCriteria() {
		return criteria;
	}

	/**
	 * Assigns a new value to the criteria.
	 *
	 * @param criteria the criteria to set
	 */
	public void setCriteria(SimplePerson criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public SimplePerson getEntity() throws DataAccessException {
		SimplePerson entity = super.getEntity();
		if (criteria!=null) {
			Map<String, Object> criteriaMap = ReflectionUtils.getProperties(criteria);
			for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
				if (entry.getValue()!=null) {
					ReflectionUtils.setProperty(entry.getKey(), entry.getValue(), entity);
				}
			}
		}
		return entity;
	}

}
