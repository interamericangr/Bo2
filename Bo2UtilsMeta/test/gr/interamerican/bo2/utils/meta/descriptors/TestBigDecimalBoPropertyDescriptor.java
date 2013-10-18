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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * BigDecimalBoPropertyDescriptorTest
 */
public class TestBigDecimalBoPropertyDescriptor {

	/**
	 * BigDecimalBoPropertyDescriptor
	 */
	private BigDecimalBoPropertyDescriptor bigDecDesc = new BigDecimalBoPropertyDescriptor();

	/**
	 * Test Parse
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testParse() throws ParseException {
		bigDecDesc.setLengthOfDecimalPart(3);
		assertEquals(BigDecimal.valueOf(356000.457), bigDecDesc.parse("356000,4567")); //$NON-NLS-1$

		bigDecDesc.setLengthOfDecimalPart(2);
		assertEquals(BigDecimal.valueOf(356000.46), bigDecDesc.parse("356000,4567")); //$NON-NLS-1$

		bigDecDesc.setLengthOfDecimalPart(1);
		assertEquals(BigDecimal.valueOf(356000.5), bigDecDesc.parse("356000,4567")); //$NON-NLS-1$
	}

	/**
	 * Test format
	 */
	@Test
	public void testFormat() {
		BigDecimal bd = new BigDecimal(350000.44578);
		bigDecDesc.setLengthOfDecimalPart(1);
		assertEquals("350.000,4", bigDecDesc.format(bd)); //$NON-NLS-1$

		BigDecimal bd2 = new BigDecimal(350000.44578);
		bigDecDesc.setLengthOfDecimalPart(2);
		assertEquals("350.000,45", bigDecDesc.format(bd2)); //$NON-NLS-1$

		BigDecimal bd3 = new BigDecimal(350000.44578);
		bigDecDesc.setLengthOfDecimalPart(3);
		assertEquals("350.000,446", bigDecDesc.format(bd3)); //$NON-NLS-1$

		assertNull(bigDecDesc.format(null));
	}

}
