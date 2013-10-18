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

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;

import org.junit.Test;

/**
 * Unit test for {@link CachedEntryOwnerFormatter}.
 */
public class TestCachedEntryOwnerFormatter {
	
	/**
	 * Sample
	 */
	CachedEntryOwnerFormatter<TranslatableEntryOwner<Long,?,?>, Long> formatter = 
		new CachedEntryOwnerFormatter<TranslatableEntryOwner<Long,?,?>, Long>(
			FormatterResolver.getFormatter(Long.class));

	/**
	 * Test method for format.
	 */
	@Test
	public void testFormat() {
		assertEquals(StringConstants.NULL, formatter.format(null));
		assertEquals(new Long(ObjectType.OBJECT1.ordinal()+1).toString(), formatter.format(ObjectType.OBJECT1));
	}

}
