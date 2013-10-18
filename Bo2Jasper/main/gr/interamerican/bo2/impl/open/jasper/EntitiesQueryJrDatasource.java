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
package gr.interamerican.bo2.impl.open.jasper;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.utils.ReflectionUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * {@link JRDataSource} based on an {@link EntitiesQuery}.
 * 
 * This JRDataSource iterates the EntitiesQuery. The entity's properties  
 * provide the values of the JRDatasource's fields.
 */
public class EntitiesQueryJrDatasource 
implements JRDataSource {
	/**
	 * Query.
	 */
	private EntitiesQuery<?> query;

	/**
	 * Creates a new QueryJrDatasource object. 
	 *
	 * @param query
	 */
	public EntitiesQueryJrDatasource(EntitiesQuery<?> query) {
		super();
		this.query = query;
	}

	
	@Override
	public boolean next() throws JRException {		
		try {
			return query.next();
		} catch (DataAccessException e) {
			throw new JRException(e);
		}
	}
	
	

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		try {
			Object entity = query.getEntity();			
			Object value = ReflectionUtils.getProperty(jrField.getName(), entity);
			/*
			 * value.getClass() is not validated against jrField.getValueClass()
			 * in favor of better performance. 
			 */
			return value;			
		} catch (DataAccessException e) {
			throw new JRException(e);
		}
	}

}
