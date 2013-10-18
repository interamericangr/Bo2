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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.records.Buffer;
import gr.interamerican.bo2.impl.open.records.BufferSpec;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.nf.NfDateFormatter;
import gr.interamerican.bo2.utils.meta.formatters.nf.NfDecimalFormatter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ToBuffer}.
 */
public class TestToBuffer {
	
	/**
	 * test
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute_withoutFormatters() {
		String[] fields = {"id", "amount", "integerField", "description", "renewalDate"};
		int[] lengths = {4,10,4,20,10};
		BufferSpec spec = new BufferSpec(fields, lengths);
		Bean1descriptor desc = new Bean1descriptor();
		ToBuffer<Bean1> toBuffer = new ToBuffer<Bean1>(spec,desc);
		
		Bean1 bean = new Bean1();
		BigDecimal bd = NumberUtils.newBigDecimal(154.55, 3);
		bean.setAmount(bd);
		bean.setFloatField(32.58f);
		bean.setId(25L);
		bean.setIntegerField(15);
		bean.setDescription("xxxxxxxxxxyyyyyyyyyyzz");
		bean.setRenewalDate(DateUtils.getDate(2010,Calendar.JANUARY,21));
		bean.setPercentage(32.55);
		
		Buffer buffer = toBuffer.execute(bean);
		String actual = buffer.getBuffer();
		String expected = "  25154,550000  15xxxxxxxxxxyyyyyyyyyy21/01/2010";		
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * test
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute_withFormatters() {
		Formatter<Date> dotsDateFormatter = new NfDateFormatter("dd.MM.yyyy");
		Formatter<Number> amountFormatter = new NfDecimalFormatter(2);
		Map<String, Formatter<?>> alternate = new HashMap<String, Formatter<?>>();
		alternate.put("renewalDate", dotsDateFormatter);
		alternate.put("amount", amountFormatter);
		
		
		String[] fields = {"id", "amount", "integerField", "description", "renewalDate"};
		int[] lengths = {4,10,4,20,10};
		BufferSpec spec = new BufferSpec(fields, lengths);
		Bean1descriptor desc = new Bean1descriptor();
		ToBuffer<Bean1> toBuffer = new ToBuffer<Bean1>(spec,desc,alternate);
		
		Bean1 bean = new Bean1();
		BigDecimal bd = NumberUtils.newBigDecimal(154.55, 3);
		bean.setAmount(bd);
		bean.setFloatField(32.58f);
		bean.setId(25L);
		bean.setIntegerField(15);
		bean.setDescription("xxxxxxxxxxyyyyyyyyyyzz");
		bean.setRenewalDate(DateUtils.getDate(2010,Calendar.JANUARY,21));
		bean.setPercentage(32.55);
		
		Buffer buffer = toBuffer.execute(bean);
		String actual = buffer.getBuffer();
		String expected = "  25    154,55  15xxxxxxxxxxyyyyyyyyyy21.01.2010";		
		Assert.assertEquals(expected, actual);
	}

}
