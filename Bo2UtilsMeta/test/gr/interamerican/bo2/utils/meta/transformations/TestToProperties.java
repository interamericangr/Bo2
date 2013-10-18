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
package gr.interamerican.bo2.utils.meta.transformations;

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ToProperties}.
 */
public class TestToProperties {
	
	/**
	 * test
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Bean1descriptor desc = new Bean1descriptor();
		ToProperties<Bean1> toProp = new ToProperties<Bean1>(desc);
		Bean1 bean = new Bean1();
		
		bean.setAmount(new BigDecimal(154.55));
		bean.setFloatField(32.58f);
		bean.setId(25L);
		bean.setIntegerField(15);
		bean.setDescription("this is the description");
		bean.setRenewalDate(new Date());
		bean.setPercentage(32.55);
		
		Properties p = toProp.execute(bean);
		Assert.assertNotNull(p);
		
		String amount = p.getProperty("amount");
		Assert.assertNotNull(amount);
		String floatField = p.getProperty("floatField");
		Assert.assertNotNull(floatField);
		String id = p.getProperty("id");
		Assert.assertNotNull(id);
		String integerField = p.getProperty("integerField");
		Assert.assertNotNull(integerField);
		String description = p.getProperty("description");
		Assert.assertNotNull(description);
		String renewalDate = p.getProperty("renewalDate");
		Assert.assertNotNull(renewalDate);
	}

}
