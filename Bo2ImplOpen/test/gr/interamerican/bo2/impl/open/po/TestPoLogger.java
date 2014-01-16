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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for {@link PoLogger}.
 */
public class TestPoLogger {

	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PoUtils.class);


	/**
	 * tests print.
	 */
	@Test
	public void testPrintPo(){
		User b1 = new User();
		b1.setId(10);
		b1.setName("lalas"); //$NON-NLS-1$
		PoLogger.print(b1, logger);
	}

	/**
	 * tests printPoKeys.
	 */
	@Test
	public void testPrintPoKeys(){
		User b1 = new User();
		b1.setId(10);
		b1.setName("lalas"); //$NON-NLS-1$
		PoLogger.printPoKeys(b1, logger);
	}

}
