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
package gr.interamerican.bo2.impl.open.workers;

import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Test;

/**
 * 
 */
public class TestAbstractPoValidator {
	
	/**
	 * unit test for getPo and setPo.
	 */
	@Test
	public void testSetGetPo() {
		AbstractPoValidator<User> v = new AbstractPoValidator<User>() {			
			public void apply() throws RuleException, DataException {/* empty */}
		};
		User user = new User();
		v.setPo(user);
		assertSame(user, v.getPo());		
	}

}
