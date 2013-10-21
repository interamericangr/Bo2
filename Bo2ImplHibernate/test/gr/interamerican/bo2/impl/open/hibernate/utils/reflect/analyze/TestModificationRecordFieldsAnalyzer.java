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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.utils.beans.Tree;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ModificationRecordFieldsAnalyzer}.
 * @deprecated
 */
@Deprecated
public class TestModificationRecordFieldsAnalyzer {
	
	/**
	 * Test for analyze.
	 */
	@Test
	public void testAnalyze() {
		ModificationRecordFieldsAnalyzer analyzer = 
			new ModificationRecordFieldsAnalyzer();
		Invoice invoice = Factory.create(Invoice.class);
		Tree<VariableDefinition<?>> tree = analyzer.execute(invoice);
		System.out.println(tree);
		Assert.assertNotNull(tree);
		int nodesCount = tree.getTotalNodesCount();
		Assert.assertEquals(5, nodesCount);
		/*
		 * Expecting the following:
		 * 
: [[InvoiceImpl_AbstractClassImplementationBybo2][null]]
    customer: [[customer][interface gr.interamerican.bo2.test.def.posamples.InvoiceCustomer][null]]
    lines: [[lines][interface java.util.Set][[]]]
    rules: [[rules][interface java.util.Set][[]]]
    mdf: [[mdf][interface gr.interamerican.bo2.arch.ModificationRecord][NULL - null]]
		 */

		
	}

}
