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
package gr.interamerican.bo2.utils.sql.types;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Double {@link Type}.
 */
public class BigDecimalType 
extends NonQuotedType<BigDecimal> {
	
	/**
	 * Instance.
	 */
	public static final BigDecimalType INSTANCE = new BigDecimalType();
	
	/**
	 * Creates a new BigDecimalType object. 
	 *
	 */
	private BigDecimalType() {
		super();		
	}
	
	public BigDecimal get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getBigDecimal(columnIndex);
	}
	
	public BigDecimal get(ResultSet rs, int columnIndex) throws SQLException {		
		return rs.getBigDecimal(columnIndex);
	}
	
	public Class<BigDecimal> getJavaType() {		
		return BigDecimal.class;
	}	

}
