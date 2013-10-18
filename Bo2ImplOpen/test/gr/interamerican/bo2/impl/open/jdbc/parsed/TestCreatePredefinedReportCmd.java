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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;

import org.junit.Test;

/**
 * Unit test for {@link CreatePredefinedReportCmd}.
 */
public class TestCreatePredefinedReportCmd {
	
	
	
	/**
	 * Unit test for the whole lifecycle.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLifeCycle() throws InitializationException, DataException {
		Provider prov = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		CreatePredefinedReportCmd cmd = new CreatePredefinedReportCmd();
		cmd.setManagerName("LOCALDB");
		cmd.init(prov);
		cmd.open();
		/*
		 * 1. test for the following statement. 
		 */
		String sql1 = StringUtils.concat(
				"Select MAIN.invoice_no, sum(LINES.amount) as invoice_amount ",
				"from X__X.INVOICE MAIN ",
				"inner join X__X.INVOICELINE LINES ",
				"on MAIN.invoice_no = LINES.invoice_no ",
				"where MAIN.invoice_date = :invoiceDt ",
				" and MAIN.last_modified = :inUser",
		        " group by MAIN.invoice_no ");
		cmd.setSql(sql1);
		assertEquals(sql1, cmd.getSql());		
		cmd.execute();
		PredefinedReport report1 = cmd.getReport();
		assertEquals(sql1, report1.getSql());
		Column[] columns1 = report1.getColumns();
		assertEquals(2, columns1.length);
		assertEquals("invoiceNo",columns1[0].getName());
		assertEquals("invoiceAmount",columns1[1].getName());		
		Parameter[] params1 = report1.getParameters();
		assertEquals(2, params1.length);
		assertEquals("invoiceDt", params1[0].getName());
		assertEquals("inUser", params1[1].getName());
		
		/*
		 * 2. now test the same factory for another statement. 
		 */
		String sql2 = StringUtils.concat(
				"Select invoice_no, invoice_date, last_modified ",
				"from X__X.INVOICE ");
		cmd.setSql(sql2);
		cmd.execute();
		PredefinedReport report2 = cmd.getReport();
		assertEquals(sql2, report2.getSql());
		Column[] columns2 = report2.getColumns();
		assertEquals(3, columns2.length);
		assertEquals("invoiceNo",columns2[0].getName());
		assertEquals("invoiceDate",columns2[1].getName());		
		assertEquals("lastModified",columns2[2].getName());	
		Parameter[] params2 = report2.getParameters();
		assertEquals(0, params2.length);
		
		
		cmd.close();		
	}
	
	

}
