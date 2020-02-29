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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

/**
 * Unit tests for {@link ParserUtils}.
 */
public class TestParserUtils {
	
	
	
	/**
	 * Test remove null parameters.
	 *
	 * @throws SqlParseException the sql parse exception
	 */
	@Test
	public void testRemoveNullParameters() throws SqlParseException {			
		String sql = StringUtils.concat( 
				"select A.id, A.name, B.address, B.city ", //$NON-NLS-1$
				" from xxxx.TB01 A, xxxx.TB02 B ", //$NON-NLS-1$
				" where A.id = B.id ", //$NON-NLS-1$
				" and A.age > 18 ", //$NON-NLS-1$
				" and A.name = :field1 ", //$NON-NLS-1$
				" and B.type = :field2 ", //$NON-NLS-1$
				" and B.amnt = :field3 "); //$NON-NLS-1$
		
		BeanWith3Fields bean = new BeanWith3Fields();
		bean.setField1(null);
		bean.setField2(1);
		bean.setField3(2.0);

		SqlParser parser = Mockito.mock(SqlParser.class);
		
		ParserUtils.removeNullParameters(parser, bean, sql);
		
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
	}

	/**
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveNullParametersWithDot() throws SqlParseException{
		String sql = StringUtils.concat(
				"select A.id, A.name, B.address, B.city ", //$NON-NLS-1$
				" from xxxx.TB01 A, xxxx.TB02 B ", //$NON-NLS-1$
				" where A.id = B.id ", //$NON-NLS-1$
				" and A.age > 18 ", //$NON-NLS-1$
				" and A.name = :field1 ", //$NON-NLS-1$
				" and B.type = :sub.field2 ", //$NON-NLS-1$
				" and B.amnt = :sub.sub2.field3 "); //$NON-NLS-1$

		SqlParser parser = Mockito.mock(SqlParser.class);
		Mockito.doReturn(sql).when(parser).removeParameter(Mockito.anyString(), Mockito.anyString());
		ParserUtils.removeNullParameters(parser, null, sql);
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.field2", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.sub2.field3", sql); //$NON-NLS-1$

		parser = Mockito.mock(SqlParser.class);
		Mockito.doReturn(sql).when(parser).removeParameter(Mockito.anyString(), Mockito.anyString());
		Param bean = new Param();
		ParserUtils.removeNullParameters(parser, bean, sql);
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.field2", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.sub2.field3", sql); //$NON-NLS-1$

		bean.setSub(new SubParam());
		parser = Mockito.mock(SqlParser.class);
		Mockito.doReturn(sql).when(parser).removeParameter(Mockito.anyString(), Mockito.anyString());
		ParserUtils.removeNullParameters(parser, bean, sql);
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.field2", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.sub2.field3", sql); //$NON-NLS-1$

		bean.getSub().setSub2(new SubParam2());
		parser = Mockito.mock(SqlParser.class);
		Mockito.doReturn(sql).when(parser).removeParameter(Mockito.anyString(), Mockito.anyString());
		ParserUtils.removeNullParameters(parser, bean, sql);
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.field2", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.sub2.field3", sql); //$NON-NLS-1$

		bean.getSub().getSub2().setField3(StringConstants.EMPTY);
		parser = Mockito.mock(SqlParser.class);
		Mockito.doReturn(sql).when(parser).removeParameter(Mockito.anyString(), Mockito.anyString());
		ParserUtils.removeNullParameters(parser, bean, sql);
		Mockito.verify(parser, Mockito.times(1)).removeParameter("field1", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(1)).removeParameter("sub.field2", sql); //$NON-NLS-1$
		Mockito.verify(parser, Mockito.times(0)).removeParameter("sub.sub2.field3", sql); //$NON-NLS-1$
	}
	
	/**
	 * Unit test for invalid property
	 * @throws SqlParseException 
	 */
	@Test(expected=RuntimeException.class)
	public void testRemoveNullParametersWithWrongName() throws SqlParseException {
		ParserUtils.removeNullParameters(null, new Param(), "select * from foo where something = :wrongField"); //$NON-NLS-1$
	}
	/**
	 * Unit test for invalid sub property
	 * @throws SqlParseException 
	 */
	@Test(expected=RuntimeException.class)
	public void testRemoveNullParametersWithWrongName2() throws SqlParseException {
		Param param = new Param();
		param.setSub(new SubParam());
		ParserUtils.removeNullParameters(null, param, "select * from foo where something = :sub.wrongField"); //$NON-NLS-1$
	}

	/**
	 * Unit test for invalid sub property
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFindValue() {
		Map<String, Object> parametersMap = new HashMap<>();
		Param param = new Param();
		param.setField1("foo");
		parametersMap.put("somethingNull", null);
		parametersMap.put("parameter", param);
		assertNull(ParserUtils.findValue(parametersMap, "somethingNull"));
		assertEquals(param,ParserUtils.findValue(parametersMap, "parameter"));
		assertEquals("foo", ParserUtils.findValue(parametersMap, "parameter.field1"));
	}

	/**
	 * Unit test for invalid sub property
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testFindValue_Fail1() {
		Map<String, Object> parametersMap = new HashMap<>();
		ParserUtils.findValue(parametersMap, "somethingThatDoesNotExist");
	}

	/**
	 * Unit test for invalid sub property
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testFindValue_Fail2() {
		Map<String, Object> parametersMap = new HashMap<>();
		parametersMap.put("parameter", new Param());
		ParserUtils.findValue(parametersMap, "parameter.somethingThatDoesNotExist");
	}

	/**
	 *
	 */
	static class Param {

		/**
		 *
		 */
		private String field1;
		/**
		 *
		 */
		private SubParam sub;

		/**
		 * @return the field1
		 */
		public String getField1() {
			return field1;
		}

		/**
		 * @param field1
		 *            the field1 to set
		 */
		public void setField1(String field1) {
			this.field1 = field1;
		}

		/**
		 * @return the sub
		 */
		public SubParam getSub() {
			return sub;
		}

		/**
		 * @param sub the sub to set
		 */
		public void setSub(SubParam sub) {
			this.sub = sub;
		}
	}

	/**
	 *
	 */
	static class SubParam {

		/**
		 *
		 */
		private String field2;

		/**
		 *
		 */
		private SubParam2 sub2;
		/**
		 * @return the field2
		 */
		public String getField2() {
			return field2;
		}

		/**
		 * @param field2 the field2 to set
		 */
		public void setField2(String field2) {
			this.field2 = field2;
		}

		/**
		 * @return the sub2
		 */
		public SubParam2 getSub2() {
			return sub2;
		}

		/**
		 * @param sub2 the sub2 to set
		 */
		public void setSub2(SubParam2 sub2) {
			this.sub2 = sub2;
		}
	}

	/**
	 *
	 */
	static class SubParam2 {

		/**
		 *
		 */
		private String field3;

		/**
		 * @return the field2
		 */
		public String getField3() {
			return field3;
		}

		/**
		 * @param field3
		 *            the field3 to set
		 */
		public void setField3(String field3) {
			this.field3 = field3;
		}
	}
}
