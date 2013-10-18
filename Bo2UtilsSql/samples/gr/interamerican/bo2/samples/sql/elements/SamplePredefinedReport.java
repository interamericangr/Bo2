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
package gr.interamerican.bo2.samples.sql.elements;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.elements.PredefinedReport;
import gr.interamerican.bo2.utils.sql.types.DateType;
import gr.interamerican.bo2.utils.sql.types.DoubleType;
import gr.interamerican.bo2.utils.sql.types.IntegerType;
import gr.interamerican.bo2.utils.sql.types.StringType;

/**
 * Sample predefined report.
 */
public class SamplePredefinedReport extends PredefinedReport {

	/**
	 * Creates a new SamplePredefinedReport object. 
	 *
	 */
	@SuppressWarnings("nls")
	public SamplePredefinedReport() {
		super();		
		String sql = StringUtils.concat(
			"Select MAIN.invoice_no, MAIN.invoice_date, CUST.customer_no, LINE.line_no, LINE.amount ",
			"from X__X.INVOICE main ",
			"inner join X__X.INVOICECUSTOMER CUST ",
			"on MAIN.invoice_no = CUST.invoice_no ",
			"inner join X__X.INVOICELINE LINE ",
			"on MAIN.invoice_no = LINE.invoice_no ",
			"where MAIN.invoice_date <= :maxInvoiceDt ", 
			"and   MAIN.invoice_date >= :minInvoiceDt ",
		    "and   LINE.amount >= :minAmount ");
		this.setSql(sql);
		this.setColumns(createColumns());
		this.setParameters(createParameters());
		this.setDescription("Sample predefined report");
		this.setTitle("Invoices");
		this.setUniqueId("X34213C23XSDw2");
	}
	
	/**
	 * Creates the columns.
	 * 
	 * @return Returns the columns.
	 */
	@SuppressWarnings("nls")
	Parameter[] createParameters() {
		Parameter maxInvoiceDt = new Parameter();
		maxInvoiceDt.setName("maxInvoiceDt");
		maxInvoiceDt.setType(DateType.INSTANCE);
		
		Parameter minInvoiceDt = new Parameter();
		minInvoiceDt.setName("minInvoiceDt");
		minInvoiceDt.setType(DateType.INSTANCE);
		
		Parameter minAmount = new Parameter();
		minAmount.setName("minAmount");
		minAmount.setType(DoubleType.INSTANCE);
		
		Parameter[] params = {maxInvoiceDt, minInvoiceDt, minAmount};
		return params;
	}
	
	
	/**
	 * Creates the columns.
	 * 
	 * @return Returns the columns.
	 */
	@SuppressWarnings("nls")
	Column[] createColumns() {
		Column invoiceNo = new Column();		
		invoiceNo.setColumnNo(1);
		invoiceNo.setColumnType(StringType.INSTANCE);
		invoiceNo.setLabel("invoice no");
		invoiceNo.setLength(32);
		invoiceNo.setName("invoice_no");		
		invoiceNo.setScale(0);
		invoiceNo.setTbCreator("X__X");
		invoiceNo.setTbName("INVOICE");
		
		Column invoiceDt = new Column();		
		invoiceDt.setColumnNo(2);
		invoiceDt.setColumnType(DateType.INSTANCE);
		invoiceDt.setLabel("invoice date");
		invoiceDt.setLength(10);
		invoiceDt.setName("invoice_date");		
		invoiceDt.setScale(0);
		invoiceDt.setTbCreator("X__X");
		invoiceDt.setTbName("INVOICE");
		
		Column customerNo = new Column();		
		customerNo.setColumnNo(3);
		customerNo.setColumnType(StringType.INSTANCE);
		customerNo.setLabel("customer no");
		customerNo.setLength(10);
		customerNo.setName("customer_no");		
		customerNo.setScale(0);
		customerNo.setTbCreator("X__X");
		customerNo.setTbName("INVOICECUSTOMER");
		
		Column lineNo = new Column();		
		lineNo.setColumnNo(4);
		lineNo.setColumnType(IntegerType.INSTANCE);
		lineNo.setLabel("line no");
		lineNo.setLength(10);
		lineNo.setName("line_no");		
		lineNo.setScale(0);
		lineNo.setTbCreator("X__X");
		lineNo.setTbName("INVOICELINE");
		
		Column amount = new Column();		
		amount.setColumnNo(5);
		amount.setColumnType(DoubleType.INSTANCE);
		amount.setLabel("amount");
		amount.setLength(10);
		amount.setName("amount");		
		amount.setScale(0);
		amount.setTbCreator("X__X");
		amount.setTbName("INVOICELINE");
		
		Column[] cols = {invoiceNo, invoiceDt, customerNo, lineNo, amount};
		return cols;
	}

}
