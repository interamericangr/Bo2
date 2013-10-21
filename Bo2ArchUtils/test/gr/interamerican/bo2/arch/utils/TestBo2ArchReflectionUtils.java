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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.utils.beans.CodifiedNamedImpl;
import gr.interamerican.bo2.samples.archutil.beans.BeanWithCodifiedProperty;
import gr.interamerican.bo2.samples.archutil.beans.SampleBean1;
import gr.interamerican.bo2.utils.StringConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2ArchReflectionUtils}.
 */
public class TestBo2ArchReflectionUtils {
	
	/**
	 * test copyPropertyHandlingCodifieds with Codified<Long> that is
	 * copied to an Integer property.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCopyPropertyHandlingCodifieds_IntegerTargetLongCode() {
		Long code = 1L;
		String name = StringConstants.DOUBLE_QUOTE;
		Codified<Long> codified = new CodifiedNamedImpl<Long>();
		codified.setCode(code);
		
		BeanWithCodifiedProperty source = new BeanWithCodifiedProperty();
		source.setNumberProperty(codified);
		source.setName(name);
		
		SampleBean1 target = new SampleBean1();
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target, "numberProperty");
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target, "name");
		
		Assert.assertTrue(target.getNumberProperty()==code.intValue());
		Assert.assertEquals(name, target.getName());
		
		BeanWithCodifiedProperty target2 = new BeanWithCodifiedProperty();
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target2, "numberProperty");
		Assert.assertEquals(codified, target2.getNumberProperty());
	}
	
	/**
	 * test copyPropertyHandlingCodifieds with Codified<String> that is
	 * copied to a String property
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCopyPropertyHandlingCodifieds_StringCode() {
		String code = "1";
		String name = StringConstants.DOUBLE_QUOTE;
		
		Codified<String> codified = new CodifiedNamedImpl<String>();
		codified.setCode(code);
		BeanWithCodifiedProperty source = new BeanWithCodifiedProperty();
		source.setStringProperty(codified);
		source.setName(name);
		SampleBean1 target = new SampleBean1();
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target, "stringProperty");
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target, "name");
		
		Assert.assertEquals(code, target.getStringProperty());
		Assert.assertEquals(name, target.getName());
		
		BeanWithCodifiedProperty target2 = new BeanWithCodifiedProperty();
		Bo2ArchReflectionUtils.copyPropertyHandlingCodifieds(source, target2, "stringProperty");
		Assert.assertEquals(codified, target2.getStringProperty());
	}

}
