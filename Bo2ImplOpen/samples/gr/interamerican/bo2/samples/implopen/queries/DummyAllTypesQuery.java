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
package gr.interamerican.bo2.samples.implopen.queries;

import gr.interamerican.bo2.impl.open.jdbc.JdbcQuery;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * Queries Sysibm.Sysdummy1 and returns a row that contains 
 * all data types.
 */
public class DummyAllTypesQuery 
extends JdbcQuery {

	
	@SuppressWarnings("nls")
	@Override
	protected String sql() {
		return StringUtils.concat(
			"select 1 as integ , 1.0 as doub, 'character' as charact, ",
			"DATE('2011-06-14') as dt, 0.01 as flt, 10 as BigDec, 100000 as lng, ",
			"1 as shrt, 'obj' as object, 1 as bt ",
			"from SYSIBM.SYSDUMMY1");
	}
	
	@Override
	protected Object[] parameters() {
		return null;
	}

}
