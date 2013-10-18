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

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * 
 */
public class TestQuotedType {

	/**
	 * NonQuotedTypeImpl
	 */
	QuotedTypeImpl impl = new QuotedTypeImpl();
	
	/**
	 * Tests QuotedTypeImpl
	 */
	@Test
	public void testQuotedType(){
		Integer value = 10;
		String expected = "'10'"; //$NON-NLS-1$
		String actual = impl.sqlString(value);
		assertEquals(expected,actual);
	}
	
	
	/**
	 * implementation to test
	 */
	private class QuotedTypeImpl extends QuotedType<Integer>{

		@Override
		public Integer get(ResultSet rs, String columnIndex)
				throws SQLException {
			return null;
		}

		@Override
		public Integer get(ResultSet rs, int columnIndex) throws SQLException {
			return null;
		}
		
		@Override
		public Class<Integer> getJavaType() {			
			return Integer.class;
		}

		@Override
		public Integer get(ResultSet rs, String columnIndex, boolean returnNullValues) throws SQLException {
			return null;
		}

		@Override
		public Integer get(ResultSet rs, int columnIndex, boolean returnNullValues) throws SQLException {
			return null;
		}
		
	}
	
}
