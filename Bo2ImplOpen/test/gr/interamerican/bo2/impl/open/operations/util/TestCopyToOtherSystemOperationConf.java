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
package gr.interamerican.bo2.impl.open.operations.util;

import gr.interamerican.bo2.test.def.posamples.InvoiceLineKey;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link CopyToOtherSystemOperationConf}.
 */
@SuppressWarnings("nls")
public class TestCopyToOtherSystemOperationConf {

	/**
	 * Test with simple keys.
	 */
	@Test
	public void test_simpleKeys() {
		Properties props = CollectionUtils.readProperties("/gr/interamerican/bo2/impl/open/operations/util/conf.simplekey.properties");
		CopyToOtherSystemOperationConf<Long> subject = new CopyToOtherSystemOperationConf<Long>(props);
		Assert.assertTrue(subject.getFromKeys().size()==3);
	}
	
	/**
	 * Test with Bo2 keys.
	 */
	@Test
	public void test_bo2Keys() {
		Properties props = CollectionUtils.readProperties("/gr/interamerican/bo2/impl/open/operations/util/conf.bo2key.properties");
		CopyToOtherSystemOperationConf<InvoiceLineKey> subject = new CopyToOtherSystemOperationConf<InvoiceLineKey>(props);
		Assert.assertTrue(subject.getFromKeys().size()==3);
		InvoiceLineKey key = subject.getFromKeys().get(0);
		Assert.assertEquals(key.getInvoiceNo(), "3");
		Assert.assertEquals(key.getLineNo(), new Integer(1));
	}

}
