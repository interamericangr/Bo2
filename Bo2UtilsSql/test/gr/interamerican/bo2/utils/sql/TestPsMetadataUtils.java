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
package gr.interamerican.bo2.utils.sql;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.help.ConnectionUtility;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.types.DateType;
import gr.interamerican.bo2.utils.sql.types.DoubleType;
import gr.interamerican.bo2.utils.sql.types.IntegerType;
import gr.interamerican.bo2.utils.sql.types.StringType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;



/**
 * Unit tests for {@link PsMetadataUtils}.
 */
public class TestPsMetadataUtils {
	/**
	 * statement to test.
	 */
	@SuppressWarnings("nls")
	String stmt1 = StringUtils.concat("Select ",
		"MAIN.invoice_no, MAIN.invoice_date, CUST.customer_no, LINE.line_no, LINE.amount ",
		"from test.INVOICE main ",
		"inner join test.INVOICECUSTOMER CUST ",
		"on MAIN.invoice_no = CUST.invoice_no ",
		"inner join test.INVOICELINE LINE ",
		"on MAIN.invoice_no = LINE.invoice_no ",
		"where MAIN.invoice_date <= ? ", 
		"and   MAIN.invoice_date >= ? ",
    	"and   LINE.amount >= ? ");
	
	
	/**
	 * statement to test.
	 */
	@SuppressWarnings("nls")
	String stmt2 = "select id, usr_nm from TEST.users";
	
	
	/**
	 * Unit test for getColumns()
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testGetColumns_stmt2() throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionUtility.getConnection();
		PreparedStatement ps = conn.prepareStatement(stmt2);
		
		Column[] columns = PsMetadataUtils.getColumns(ps);
		
		assertEquals(2, columns.length);
		
		Column c0 = columns[0];
		assertEquals( null, c0.getAlias());
		assertEquals("id", c0.getName()); //$NON-NLS-1$
		assertEquals("USERS", c0.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c0.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)1, c0.getColumnNo());		
		assertEquals(IntegerType.INSTANCE, c0.getColumnType());
		assertEquals("ID",c0.getLabel()); //$NON-NLS-1$
		assertEquals( null, c0.getRemarks());
		
		Column c1 = columns[1];
		assertEquals( null, c1.getAlias());
		assertEquals("usrNm", c1.getName()); //$NON-NLS-1$
		assertEquals("USERS", c1.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c1.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)2, c1.getColumnNo());		
		assertEquals(StringType.INSTANCE, c1.getColumnType());
		assertEquals("USR_NM",c1.getLabel()); //$NON-NLS-1$
		assertEquals( null, c1.getRemarks());
	}
	
	
	
	
	/**
	 * Unit test for getColumns()
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testGetColumns_stmt1() throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionUtility.getConnection();
		PreparedStatement ps = conn.prepareStatement(stmt1);
		
		Column[] columns = PsMetadataUtils.getColumns(ps);
		
		assertEquals(5, columns.length);
		
		Column c0 = columns[0];
		assertEquals( null, c0.getAlias());
		assertEquals("invoiceNo", c0.getName()); //$NON-NLS-1$
		assertEquals("INVOICE", c0.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c0.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)1, c0.getColumnNo());
		assertEquals(StringType.INSTANCE, c0.getColumnType());
		assertEquals((Integer)32,c0.getLength());
		assertEquals((Integer)0,c0.getScale());
		assertEquals("INVOICE_NO",c0.getLabel()); //$NON-NLS-1$
		assertEquals( null, c0.getRemarks());
		
		Column c1 = columns[1];
		assertEquals( null, c1.getAlias());
		assertEquals("invoiceDate", c1.getName()); //$NON-NLS-1$
		assertEquals("INVOICE", c1.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c1.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)2, c1.getColumnNo());
		assertEquals(DateType.INSTANCE, c1.getColumnType());
		assertEquals((Integer)10,c1.getLength());
		assertEquals((Integer)0,c1.getScale());
		assertEquals("INVOICE_DATE",c1.getLabel()); //$NON-NLS-1$
		assertEquals( null, c1.getRemarks());
		
		Column c2 = columns[2];
		assertEquals( null, c2.getAlias());
		assertEquals("customerNo", c2.getName()); //$NON-NLS-1$
		assertEquals("INVOICECUSTOMER", c2.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c2.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)3, c2.getColumnNo());
		assertEquals(StringType.INSTANCE, c2.getColumnType());
		assertEquals((Integer)32,c2.getLength());
		assertEquals((Integer)0,c2.getScale());
		assertEquals("CUSTOMER_NO",c2.getLabel()); //$NON-NLS-1$
		assertEquals( null, c2.getRemarks());
		
		Column c3 = columns[3];
		assertEquals( null, c3.getAlias());
		assertEquals("lineNo", c3.getName()); //$NON-NLS-1$
		assertEquals("INVOICELINE", c3.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c3.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)4, c3.getColumnNo());
		assertEquals(IntegerType.INSTANCE, c3.getColumnType());
		assertEquals((Integer)11,c3.getLength());
		assertEquals((Integer)0,c3.getScale());
		assertEquals("LINE_NO",c3.getLabel()); //$NON-NLS-1$
		assertEquals( null, c3.getRemarks());
		
		Column c4 = columns[4];
		assertEquals( null, c4.getAlias());
		assertEquals("amount", c4.getName()); //$NON-NLS-1$
		assertEquals("INVOICELINE", c4.getTbName()); //$NON-NLS-1$
		assertEquals("TEST", c4.getTbCreator()); //$NON-NLS-1$
		assertEquals((Integer)5, c4.getColumnNo());
		assertEquals(DoubleType.INSTANCE, c4.getColumnType());
		assertEquals((Integer)22,c4.getLength());
		assertEquals((Integer)0,c4.getScale());
		assertEquals("AMOUNT",c4.getLabel()); //$NON-NLS-1$
		assertEquals( null, c4.getRemarks());		
		
		ps.close();
		conn.close();
		
	}
	
	/**
	 * Unit test for getColumns()
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testGetParameters_stmt1() throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionUtility.getConnection();
		PreparedStatement ps = conn.prepareStatement(stmt1);
		
		Parameter [] parameters = PsMetadataUtils.getParameters(ps);
		
		assertEquals(3, parameters.length);
		
		Parameter p0 = parameters[0];
		assertEquals("parm1",p0.getName()); //$NON-NLS-1$
		assertEquals(Date.class,p0.getType().getJavaType());
		
		Parameter p1 = parameters[1];
		assertEquals("parm2",p1.getName()); //$NON-NLS-1$
		assertEquals(DateType.INSTANCE, p1.getType());
		
		Parameter p2 = parameters[2];
		assertEquals("parm3",p2.getName()); //$NON-NLS-1$
		assertEquals(DoubleType.INSTANCE, p2.getType());
		
		ps.close();
		conn.close();

	}

	/**
	 * Test notSupported
	 */
	@Test(expected = RuntimeException.class)
	public void testNotSupported() {
		PsMetadataUtils.notSupported("UnsupportedType"); //$NON-NLS-1$
	}
	
	
}
