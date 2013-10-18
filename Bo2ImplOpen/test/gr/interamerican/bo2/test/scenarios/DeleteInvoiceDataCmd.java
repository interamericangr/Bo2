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
package gr.interamerican.bo2.test.scenarios;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcCommand;
import gr.interamerican.bo2.impl.open.jdbc.Sql;

import java.sql.SQLException;

/**
 * 
 */
@SuppressWarnings("nls")
public class DeleteInvoiceDataCmd extends JdbcCommand {
	/**
	 * sql
	 */
	@Sql private String sqlSubRules = "delete from TEST.INVOICESUBRULE";
	
	/**
	 * sql
	 */	
	@Sql private String sqlSubLines = "delete from TEST.INVOICESUBLINE";
	
	/**
	 * sql
	 */
	@Sql private String sqlRules = "delete from TEST.INVOICERULE";
	
	/**
	 * sql
	 */
	@Sql private String sqlLines = "delete from TEST.INVOICELINE";
	
	/**
	 * sql
	 */
	@Sql private String sqlCustomerAddress = "delete from TEST.CUSTOMERADDRESS";
	
	/**
	 * sql
	 */
	@Sql private String sqlInvoiceCustomers = "delete from TEST.INVOICECUSTOMER";
	
	/**
	 * sql
	 */
	@Sql private String sqlCustomers = "delete from TEST.CUSTOMER";
		
	/**
	 * sql
	 */
	@Sql private String sqlInvoices = "delete from TEST.INVOICE";
	
	@Override
	protected void work() throws DataException {
		String[] params = new String[0];
		try {
			executePreparedUpdate(sqlSubRules, params);
			executePreparedUpdate(sqlSubLines, params);
			executePreparedUpdate(sqlRules, params);
			executePreparedUpdate(sqlLines, params);
			executePreparedUpdate(sqlCustomerAddress, params);
			executePreparedUpdate(sqlInvoiceCustomers, params);
			executePreparedUpdate(sqlCustomers, params);
			executePreparedUpdate(sqlInvoices, params);
		} catch (SQLException e) {
			throw new DataException(e);
		}

	}

}
