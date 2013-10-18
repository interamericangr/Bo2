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
package gr.interamerican.bo2.impl.open.hibernate.types;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Unit tests for {@link UserTypeUtil}
 */
public class TestUserTypeUtil {

	/**
	 * tests isUserType()
	 */
	@Test
	public void testIsUserType() {
		Sex s = Sex.FEMALE;
		assertTrue(UserTypeUtil.isUserType(s));

		Money money = new MoneyImpl(new BigDecimal(100));
		assertTrue(UserTypeUtil.isUserType(money));

		TypedSelectable<Long> entry = new TypedSelectableImpl<Long>();
		assertTrue(UserTypeUtil.isUserType(entry));

		String str = "falseValue"; //$NON-NLS-1$
		assertFalse(UserTypeUtil.isUserType(str));
	}

}
