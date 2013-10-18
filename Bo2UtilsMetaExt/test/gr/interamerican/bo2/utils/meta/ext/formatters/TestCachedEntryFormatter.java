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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CachedEntryFormatter}.
 */
public class TestCachedEntryFormatter {
	
	/**
	 * Sample
	 */
	CachedEntryFormatter<TypedSelectable<Long>, Long> formatter = 
		new CachedEntryFormatter<TypedSelectable<Long>, Long>(
				FormatterResolver.getFormatter(Long.class));

	/**
	 * Test method for format.
	 */
	@Test
	public void testFormat() {
		TypedSelectable<Long> ts = new TypedSelectableImpl<Long>();
		ts.setCode(1L);
		Assert.assertEquals(StringConstants.NULL, formatter.format(null));
		Assert.assertEquals(new Long(1).toString(), formatter.format(ts));
	}

}
