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
package gr.interamerican.bo2.samples.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.samples.workers.EmptyWorker;

/**
 * query implementation for the test. 
 */

public class TsEntitiesQueryImpl 
extends EmptyWorker 
implements EntitiesQuery<TypedSelectable<Long>> {
	
	/**
	 * Type id for type selectable implementation.
	 */
	public static final Long TYPEID = 1000L;
	
	/**
	 * 
	 */
	@SuppressWarnings("nls")
	private String[] rows = {"ena", "dyo", "tria"};
	/**
	 * Creates a new QueryImpl object. 
	 *
	 * @param rows String array with the rows.
	 */
	public TsEntitiesQueryImpl(String[] rows) {
		super();
		this.rows = rows;
	}
	/**
	 * Creates a new QueryImpl object. 
	 */
	public TsEntitiesQueryImpl() {
		super();		
	}
	
	/**
	 * current row.
	 */
	int i=0;		
			
	public void execute() throws DataException {
		i=0;
	}
	public boolean next() throws DataAccessException {
		i++;
		return i<=rows.length;
	}
	public int getRow() throws DataAccessException {		
		return i;
	}
	public void setAvoidLock(boolean avoidLock) {/*empty*/}
	public boolean isAvoidLock() {return true;}
	/** 
	 * description.
	 * @return returns a field
	 */
	public String getDescription() {
		return rows[i-1];
	}
	public TypedSelectable<Long> getEntity() throws DataAccessException {
		TypedSelectableImpl<Long> impl = new TypedSelectableImpl<Long>();
		impl.setTypeId(1000L);
		impl.setCode(new Long(getRow()));
		impl.setName(getDescription());
		return impl;
	}
	
	
}
