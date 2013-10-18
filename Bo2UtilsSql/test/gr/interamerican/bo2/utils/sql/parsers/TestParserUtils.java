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
package gr.interamerican.bo2.utils.sql.parsers;

import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link ParserUtils}.
 */
public class TestParserUtils {
	
	
	
	/**
	 * @throws SqlParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveNullParameters() throws SqlParseException {			
		String sql = StringUtils.concat( 
			"select A.id, A.name, B.address, B.city ",
			" from xxxx.TB01 A, xxxx.TB02 B ",
			" where A.id = B.id ",
			" and A.age > 18 ",
			" and A.name = :field1 ",
		    " and B.type = :field2 ",
		    " and B.amnt = :field3 "); 
		
		BeanWith3Fields bean = new BeanWith3Fields();
		bean.setField1(null);
		bean.setField2(1);
		bean.setField3(2.0);

		SqlParser parser = Mockito.mock(SqlParser.class);
		
		ParserUtils.removeNullParameters(parser, bean, sql);
		
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql);
	}
	
	/**
	 * Unit test for invalidProperty().
	 */
	@Test(expected=RuntimeException.class)
	public void testInvalidProperty() {
		ParserUtils.invalidProperty(new Object(), "foo"); //$NON-NLS-1$
	}

}
