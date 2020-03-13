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
package gr.interamerican.bo2.utils.beans;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.NumberBean;
import gr.interamerican.bo2.utils.adapters.vo.CalculateRoundedPercentage;

/**
 * Unit test for {@link CalculateRoundedPercentage}.
 */
public class TestCalculateRoundedPercentage {

	/**
	 * Unit test for execute.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExecute() {
		NumberBean nb = new NumberBean();
		BigDecimal bd1 = new BigDecimal("13.145");
		nb.setBd1(bd1);

		CalculateRoundedPercentage<NumberBean> calc = new CalculateRoundedPercentage<NumberBean>(NumberBean::getBd1,NumberBean::setBd2, BigDecimal.valueOf(0.23), 2, true);
		calc.execute(nb);
		Assert.assertEquals(BigDecimal.valueOf(3.02), nb.getBd2());

		//Scenario where the input is null.
		nb.setBd1(null);
		calc.execute(nb);
		Assert.assertNull(nb.getBd2());
	}

}
