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
package gr.interamerican.wicket.condition;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for {@link AbstractAjaxEnabledCondition}.
 */
public class TestAbstractAjaxEnabledCondition {
	
	/**
	 * Test for setCaller(component).
	 */
	@Test
	public void testSetCaller() {		
		AbstractAjaxEnabledCondition<Object> cond = 
			new AbstractAjaxEnabledCondition<Object>() {				
				@Override
				public boolean check(Object t, AjaxRequestTarget target) {
					return false;
				}
			};
		Component caller = Mockito.mock(Component.class);
		cond.setCaller(caller);
		Assert.assertEquals(caller, cond.caller);
	}
	
	

}
