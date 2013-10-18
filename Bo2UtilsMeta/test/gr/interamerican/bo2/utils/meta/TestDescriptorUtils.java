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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link DescriptorUtils}.
 */
public class TestDescriptorUtils {
	
	/**
	 * test
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException {
		Properties p = new Properties();
		
		p.setProperty("amount", "158,80");
		p.setProperty("floatField", "188,80");
		p.setProperty("id", "4");
		p.setProperty("integerField", "1");
		p.setProperty("description", "description of bean");		
		p.setProperty("renewalDate", "13/11/2011");		
		
		Bean1descriptor desc = new Bean1descriptor();
		Map<BoPropertyDescriptor<?>, Object> values = DescriptorUtils.parse(p, desc);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<BoPropertyDescriptor<?>, Object> entry : values.entrySet()) {
			map.put(entry.getKey().getName(), entry.getValue());
		}
		Assert.assertNotNull(map.get("amount"));
		Assert.assertNotNull(map.get("floatField"));
		Assert.assertNotNull(map.get("id"));
		Assert.assertNotNull(map.get("integerField"));
		Assert.assertNotNull(map.get("description"));
		Assert.assertNotNull(map.get("renewalDate"));
	}

}
