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
package gr.interamerican.bo2.jsqlparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;
import gr.interamerican.bo2.utils.sql.parsers.AbstractParserTest;

import java.util.List;

import org.junit.Test;


/**
 * Unit tests for {@link GenericParser}.
 */
@SuppressWarnings("nls")
public class TestGenericParser extends AbstractParserTest {
	
	/**
	 * Parser for the tests.
	 */
	private GenericParser parser = new GenericParser();
	
	/**
	 * SQL 1
	 */	
	private String sql1 = StringUtils.concat( 
		"select A.id, A.name, A.doy, B.doyNm ",
		" from xxxx.PEOPLE A ",
		" inner join xxxx.DOY B ",
		" on A.doy = B.doy ",
		" where A.name not like :name ",
		" and A.surname like :surname",
		" and A.age > :minAge ",
		" and A.doyNm > :minDoyNm ",
		" and B.doyNm > :minDoyNm");
	
	/**
	 * SQL 2
	 */
	private String sql2 = StringUtils.concat(
		"select I.INVOICE_NO as ino, I.INVOICE_DATE as idate, L.LINE_NO as lno, L.AMOUNT as a ", 
		"from TEST.INVOICE I ", 
		"join TEST.INVOICELINE L on I.INVOICE_NO=L.INVOICE_NO ", 
		"where I.INVOICE_DATE >= :dateMin or L.LINE_NO = :lineNo ",
		"and I.INVOICE_DATE <= :dateMax ",
		"or L.AMOUNT > :maxAmount");
	
	/**
	 * Not supported SQL statement.
	 */
	private String updateSql = 
		"update MYTABLE set col1 = :var1 where cel2 = :var2 "; 
	
	/**
	 * Not supported SQL statement.
	 */
	private String invalidSql = 
		"this is not a valid sql statement"; 
	
	
	/**
	 * Unit test for getParameters().
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testGetParameters() throws SqlParseException {
		List<Parameter> parameters1 = parser.getParameters(sql1);
		assertEquals(5, parameters1.size());
		checkParam(parameters1, "name");
		checkParam(parameters1, "minAge");
		checkParam(parameters1, "surname");
		
		List<Parameter> params = SelectionUtils.selectByProperty("name", "minDoyNm", parameters1, Parameter.class); //$NON-NLS-1$
		assertEquals(2, params.size());
		Parameter parm = params.get(0); 
		assertNotNull(parm);
		parm = params.get(1);
		assertNotNull(parm);
		
		
		List<Parameter> parameters2 = parser.getParameters(sql2);
		assertEquals(4, parameters2.size());
		checkParam(parameters2, "dateMin");
		checkParam(parameters2, "dateMax");
		checkParam(parameters2, "maxAmount");
		checkParam(parameters2, "lineNo");
	}	
	
	/**
	 * Unit test for getParameters().
	 * 
	 * @throws SqlParseException
	 */
	@Test(expected=SqlParseException.class)
	public void testGetColumns_onNotSupportedSql() throws SqlParseException {
		parser.getColumns(updateSql);
	}
	
	/**
	 * Unit test for getParameters().
	 * 
	 * @throws SqlParseException
	 */
	@Test(expected=SqlParseException.class)
	public void testGetColumns_onInvalid() throws SqlParseException {
		parser.getColumns(invalidSql);
	}

	/**
	 * Unit test for getParameters().
	 * 
	 * @throws SqlParseException
	 */
	@Test(expected=SqlParseException.class)
	public void testRemoveParameter_onNotSupportedSql() throws SqlParseException {
		parser.removeParameter("var2", updateSql);
	}
	
	/**
	 * Unit test for getColumns().
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testGetColumns() throws SqlParseException {
		List<Column> columns1 = parser.getColumns(sql1);
		checkColumn(columns1, 0, "id", "xxxx", "PEOPLE", null);
		checkColumn(columns1, 1, "name", "xxxx", "PEOPLE", null);
		checkColumn(columns1, 2, "doy", "xxxx", "PEOPLE", null);
		checkColumn(columns1, 3, "doyNm", "xxxx", "DOY", null);
		
		List<Column> columns2 = parser.getColumns(sql2);
		checkColumn(columns2, 0, "INVOICE_NO", "TEST", "INVOICE", "ino");
		checkColumn(columns2, 1, "INVOICE_DATE", "TEST", "INVOICE", "idate");
		checkColumn(columns2, 2, "LINE_NO", "TEST", "INVOICELINE", "lno");
		checkColumn(columns2, 3, "AMOUNT", "TEST", "INVOICELINE", "a");
	}
	
	/**
	 * Unit test for getColumns() when the resultset contains a function.
	 * @throws SqlParseException 
	 */
	@Test
	public void testGetColumns_Function() throws SqlParseException {
		String sql = "select system, module, status, count(*) as count " +
				"from X__X.tb1stsrp where system = :system group by system, module, status";
		List<Column> columns = parser.getColumns(sql);
		checkColumn(columns, 0, "system", "X__X", "tb1stsrp", null);
		checkColumn(columns, 1, "module", "X__X", "tb1stsrp", null);
		checkColumn(columns, 2, "status", "X__X", "tb1stsrp", null);
		checkColumn(columns, 3, "count", null, null, "count");
	}
	
	/**
	 * Unit test for getColumns() when the resultset contains a function
	 * with no alias.
	 * @throws SqlParseException 
	 */
	@Test (expected = SqlParseException.class)
	public void testGetColumns_Function_noAlias() throws SqlParseException {
		String sql = "select system, module, status, count(*) " +
				"from X__X.tb1stsrp where system = :system group by system, module, status";
		List<Column> columns = parser.getColumns(sql);
		checkColumn(columns, 0, "system", "X__X", "tb1stsrp", null);
		checkColumn(columns, 1, "module", "X__X", "tb1stsrp", null);
		checkColumn(columns, 2, "status", "X__X", "tb1stsrp", null);
		checkColumn(columns, 3, "count", null, null, "count");
	}
	
	/**
	 * Unit test for getColumns() when select * is used.
	 * 
	 * @throws SqlParseException 
	 */
	@Test (expected = SqlParseException.class)
	public void testGetColumns_withWildCard() throws SqlParseException {
		String sql = "select * from X__X.tb1stsrp where system = :system";
		parser.getColumns(sql);
	}
	
	/**
	 * Unit test for getParameters().
	 * 
	 * @throws SqlParseException
	 */
	@Test(expected=SqlParseException.class)
	public void testRemoveParameter_onInvalid() throws SqlParseException {
		parser.removeParameter("parm", invalidSql);
	}
	
	/**
	 * Remove the only parameter in the where clause.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_singleParameter() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname");
		
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy");
		checkStatement(expected, actual);
		
		/*
		 * Aliases in columns.
		 */
		sql = StringUtils.concat( 
				"select A.id as id, A.name as name, A.doy as doy, B.doyNm as doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname");
		
		actual = parser.removeParameter("surname", sql);
		expected = StringUtils.concat( 
				"select A.id as id,A.name as name,A.doy as doy,B.doyNm as doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy");
		checkStatement(expected, actual);
	}
	
	
	/**
	 * Try a statement that is not supported.
	 * 
	 * @throws SqlParseException
	 */
	@Test(expected=SqlParseException.class)
	public void testRemoveParameter_onNotSupportedExpression() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select id, name from xxxx.PEOPLE ", 
				"where birthDay between :from and :to ");
		parser.removeParameter("from", sql);
	}
	
	/**
	 * Remove some parameter.
	 * 
	 * @throws SqlParseException
	 */
	@Test()
	public void testRemoveParameter_onceMore() throws SqlParseException {
		String sql0 = StringUtils.concat( 
				"select id, name from xxxx.PEOPLE ", 
				"where birthDay <= :from ",
				" and birthDay >= :to ",
				" and birthDay != :then ");
		String result1 = parser.removeParameter("from", sql0);
		String result2 = parser.removeParameter("to", result1);
		String actual = parser.removeParameter("then", result2);
		String expected = "select id,name from xxxx.PEOPLE";
		checkStatement(expected, actual);
	}
	
	
	/**
	 * Remove the first and last parameter in a where clause that
	 * has only AND.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_allAnd() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.name like :name ",
				"and A.birthDt >= :minDate");
		
		/*
		 * Remove first parameter.
		 */
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE AS A ",
				"inner join xxxx.DOY AS B ",
				"on A.doy = B.doy ",
				"where A.name LIKE :name ",
				"and A.birthDt >= :minDate");
		checkStatement(expected, actual);
		
		/*
		 * Remove last parameter.
		 */
		actual = parser.removeParameter("minDate", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname LIKE :surname ",
				"and A.name LIKE :name");
		checkStatement(expected, actual);
		
		/*
		 * Remove middle parameter.
		 */
		actual = parser.removeParameter("name", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname LIKE :surname ",
				"and A.birthDt >= :minDate");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that has only AND.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_similarNames() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.nameLast like :nameLast ",
				"and A.streetNo = :streetNo ",
				"and A.street = :street");
		
		/*
		 * Remove first parameter.
		 */
		String actual = parser.removeParameter("name", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.nameLast like :nameLast ",
				"and A.streetNo = :streetNo ",
				"and A.street = :street");
		checkStatement(expected, actual);
		
		/*
		 * Remove second parameter.
		 */
		actual = parser.removeParameter("nameLast", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.streetNo = :streetNo ",
				"and A.street = :street");
		checkStatement(expected, actual);
		
		/*
		 * Remove third parameter.
		 */
		actual = parser.removeParameter("street", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.nameLast like :nameLast ",
				"and A.streetNo = :streetNo");
		checkStatement(expected, actual);
		
		/*
		 * Remove fourth.
		 */
		actual = parser.removeParameter("streetNo", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.nameLast like :nameLast ",
				"and A.street = :street");
		
	}
	
	/**
	 * Remove the first and last parameter in a where clause that
	 * has only OR.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_allOr() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"or A.birthDt >= :minDate");
		
		/*
		 * Remove first parameter.
		 */
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE AS A ",
				"inner join xxxx.DOY AS B ",
				"on A.doy = B.doy ",
				"where A.name LIKE :name ",
				"or A.birthDt >= :minDate");
		checkStatement(expected, actual);
		
		/*
		 * Remove last parameter.
		 */
		actual = parser.removeParameter("minDate", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname LIKE :surname ",
				"or A.name LIKE :name");
		checkStatement(expected, actual);
		
		/*
		 * Remove middle parameter.
		 */
		actual = parser.removeParameter("name", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.birthDt >= :minDate");
	}
	
	/**
	 * Remove parameters from a where clause that has both AND and OR.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_mixedAndOr() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.years > :years ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate");
		
		/*
		 * Remove first parameter.
		 */
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.years > :years ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate");
		checkStatement(expected, actual);
		
		/*
		 * Remove last parameter.
		 */
		actual = parser.removeParameter("minDate", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.years > :years ",
				"or A.name like :name");
		checkStatement(expected, actual);
		
		/*
		 * Remove second parameter.
		 */
		actual = parser.removeParameter("years", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate");
		checkStatement(expected, actual);
		
		/*
		 * Remove third parameter.
		 */
		actual = parser.removeParameter("name", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.years > :years ",
				"and A.birthDt >= :minDate");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that does not have only
	 * named parameters.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_constantParameters() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.birthDt >= :minDate ",
				"or A.years > 35 ",
				"and B.doyNm like :doyNm");
		
		/*
		 * Remove first.
		 */
		String actual = parser.removeParameter("name", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.birthDt >= :minDate ",
				"or A.years > 35 ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove last.
		 */
		actual = parser.removeParameter("doyNm", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"and A.birthDt >= :minDate ",
				"or A.years > 35");
		checkStatement(expected, actual);
		
		/*
		 * Remove middle.
		 */
		actual = parser.removeParameter("minDate", sql);
		expected = StringUtils.concat(
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"or A.years > 35 ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
			
	}
	
	/**
	 * Remove parameters from a where clause that has a parenthesis.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_parenthesis() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and (A.salary < :minSalary or A.salary > :maxSalary or A.years > :years) ",
				"and B.doyNm like :doyNm");
		
		/*
		 * Remove first parenthesis parameter.
		 */
		String actual = parser.removeParameter("minSalary", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary > :maxSalary or A.years > :years ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove middle parenthesis parameter.
		 */
		actual = parser.removeParameter("maxSalary", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary < :minSalary or A.years > :years ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove last parenthesis parameter.
		 */
		actual = parser.removeParameter("years", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary < :minSalary or A.salary > :maxSalary ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove two parenthesis parameters.
		 */
		actual = parser.removeParameter("years", sql);
		actual = parser.removeParameter("minSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary > :maxSalary ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove all parenthesis parameters.
		 */
		actual = parser.removeParameter("years", sql);
		actual = parser.removeParameter("maxSalary", actual);
		actual = parser.removeParameter("minSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove all parameters before parenthesis.
		 */
		actual = parser.removeParameter("name", sql);
		actual = parser.removeParameter("surname", actual);
		actual = parser.removeParameter("minDate", actual);
		expected = StringUtils.concat(
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.salary < :minSalary or A.salary > :maxSalary or A.years > :years ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove all parameters after parenthesis.
		 */
		actual = parser.removeParameter("doyNm", sql);
		expected = StringUtils.concat(
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary < :minSalary or A.salary > :maxSalary or A.years > :years )");
		checkStatement(expected, actual);
		
		/*
		 * Leave only parenthesis parameters.
		 */
		actual = parser.removeParameter("doyNm", sql);
		actual = parser.removeParameter("minDate", actual);
		actual = parser.removeParameter("surname", actual);
		actual = parser.removeParameter("name", actual);
		expected = StringUtils.concat(
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.salary < :minSalary or A.salary > :maxSalary or A.years > :years )");
		checkStatement(expected, actual);
		
	}
	
	/**
	 * Remove parameters from a where clause that has a double parenthesis.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_doubleParenthesis() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and (A.salary < :minSalary or (A.salary > :maxSalary or A.years > :years)) ",
				"and B.doyNm like :doyNm");
		
		/*
		 * Remove years.
		 */
		String actual = parser.removeParameter("years", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( A.salary < :minSalary or ( A.salary > :maxSalary ) ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove minSalary. 
		 */
		actual = parser.removeParameter("minSalary", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( ( A.salary > :maxSalary or A.years > :years ) ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove both minSalary and maxSalary.
		 */
		actual = parser.removeParameter("minSalary", sql);
		actual = parser.removeParameter("maxSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and ( ( A.years > :years ) ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove all parenthesis parameters.
		 */
		actual = parser.removeParameter("minSalary", sql);
		actual = parser.removeParameter("maxSalary", actual);
		actual = parser.removeParameter("years", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"and A.birthDt >= :minDate ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that has two parentheses.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_twoParentheses() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where (A.surname like :surname ",
				"or A.name like :name) ",
				"and (A.salary > :maxSalary or A.years > :years) ",
				"and B.doyNm like :doyNm");
		
		/*
		 * Remove first parenthesis parameters.
		 */
		String actual = parser.removeParameter("surname", sql);
		actual = parser.removeParameter("name", actual);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.salary > :maxSalary or A.years > :years ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove second parenthesis parameters.
		 */
		actual = parser.removeParameter("maxSalary", sql);
		actual = parser.removeParameter("years", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.surname like :surname ",
				"or A.name like :name ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
	}
	
	/**
	 * Tests parameter removal from a union query.
	 * @throws SqlParseException
	 */
	@Test
	public void testRemoveParameter_union() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"union ",
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.years like :years");
		
		/*
		 * Remove common parameter.
		 */
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.name like :name ",
				"union ",
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.years like :years");
		checkStatement(expected, actual);
		
		/*
		 * Remove parameter of first select.
		 */
		actual = parser.removeParameter("name", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"union ",
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.years like :years");
		checkStatement(expected, actual);
		
		/*
		 * Remove parameter of second select.
		 */
		actual = parser.removeParameter("years", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"or A.name like :name ",
				"union ",
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that has is (not) null.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_isNull() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where (A.surname like :surname ",
				"or A.salary is not null) ",
				"and A.salary is null ",
				"and B.doyNm like :doyNm");
		
		String actual = parser.removeParameter("doyNm", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.surname like :surname ",
				"or A.salary is not null ) ",
				"and A.salary is null");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		expected = StringUtils.concat(
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ( A.salary is not null ) ",
				"and A.salary is null ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that has in with itemsList.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_inItems() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary in (:minSalary, :maxSalary, :medSalary) ",
				"and B.doyNm like :doyNm");
		
		/*
		 * Remove before in.
		 */
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.salary in ( :minSalary, :maxSalary, :medSalary ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove after in.
		 */
		actual = parser.removeParameter("doyNm", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary in ( :minSalary, :maxSalary, :medSalary )");
		checkStatement(expected, actual);
		
		/*
		 * Remove a parameter in in. 
		 */
		actual = parser.removeParameter("minSalary", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary in ( :maxSalary, :medSalary ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		/*
		 * Remove all in parameters.
		 */
		actual = parser.removeParameter("minSalary", sql);
		actual = parser.removeParameter("maxSalary", actual);
		actual = parser.removeParameter("medSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
	}
	
	/**
	 * Remove parameters from a where clause that has in with subselect.
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_inSubSelect() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary in (select C.salary from xxxx.SALARIES as C where C.doyNm = :doyNm) ",
				"and B.doyNm like :doyNm");
		
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.salary in ( select C.salary from xxxx.SALARIES as C where C.doyNm = :doyNm ) ",
				"and B.doyNm like :doyNm");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("doyNm", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary in ( select C.salary from xxxx.SALARIES as C )");
		checkStatement(expected, actual);
		
	}
	
	/**
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_groupByHaving() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary = :minSalary ",
				"group by A.name ",
				"having count(1)>10");
		
		String actual = parser.removeParameter("minSalary", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.salary = :minSalary ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		actual = parser.removeParameter("minSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
	}
	

	/**
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_orderByLimit() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.salary = :minSalary ",
				"order by A.id ",
				"limit 2,3");
		
		String actual = parser.removeParameter("minSalary", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"order by A.id ",
				"limit 3 offset 2");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.salary = :minSalary ",
				"order by A.id ",
				"limit 3 offset 2");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		actual = parser.removeParameter("minSalary", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"order by A.id ",
				"limit 3 offset 2");
		checkStatement(expected, actual);
	}
	
	/**
	 * 
	 * @throws SqlParseException 
	 */
	@Test
	public void testRemoveParameter_exists() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ",
				"not exists(select C.id from xxxx.EMPLOYEES as C where C.name = :name) ",
				"and A.name like :name ",
				"and A.surname like :surname");
		
		String actual = parser.removeParameter("surname", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ",
				"not exists ( select C.id from xxxx.EMPLOYEES as C where C.name = :name ) ",
				"and A.name like :name");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("name", sql);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ",
				"not exists ( select C.id from xxxx.EMPLOYEES as C ) ",
				"and A.surname like :surname");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("surname", sql);
		actual = parser.removeParameter("name", actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where ",
				"not exists ( select C.id from xxxx.EMPLOYEES as C )");
		checkStatement(expected, actual);
		
	}
	
	/**
	 * Unit test from Manolist.
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testManolis() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and (A.salary = :minSalary ",
				"and A.avgsalary >= :minSalary) ",
				"group by A.name ",
				"having count(1) > 10");
		
		String actual = parser.removeParameter("minSalary", sql);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
	}
	
	/**
	 * Unit test for int()
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testIntFunction() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and int(A.avgsalary) >= :minSalary ",
				"group by A.name ",
				"having count(1) > 10");
		
		String actual = parser.removeParameter("surname", sql);
		System.out.println(actual);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where int(A.avgsalary) >= :minSalary ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("minSalary", sql);
		System.out.println(actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"group by A.name ",
				"having count(1) > 10");
		checkStatement(expected, actual);
	}
	
	
	/**
	 * Unit test for int()
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testCustomFunction() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and kateros(A.avgsalary) >= :minSalary ",
				"group by A.name ",
		"having count(1) > 10");
		
		String actual = parser.removeParameter("surname", sql);
		System.out.println(actual);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where kateros(A.avgsalary) >= :minSalary ",
				"group by A.name ",
		"having count(1) > 10");
		checkStatement(expected, actual);
		
		actual = parser.removeParameter("minSalary", sql);
		System.out.println(actual);
		expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"group by A.name ",
		"having count(1) > 10");
		checkStatement(expected, actual);
	}
	
	/**
	 * Unit test for duplicate parameter
	 * 
	 * @throws SqlParseException
	 */
	@Test
	public void testDuplicateParameter() throws SqlParseException {
		String sql = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.surname like :surname ",
				"and A.age >= 20 ",
				"and A.name like :surname");
		
		String actual = parser.removeParameter("surname", sql);
		System.out.println(actual);
		String expected = StringUtils.concat( 
				"select A.id,A.name,A.doy,B.doyNm ",
				"from xxxx.PEOPLE as A ",
				"inner join xxxx.DOY as B ",
				"on A.doy = B.doy ",
				"where A.age >= 20");
		checkStatement(expected, actual);
	}
	
}
