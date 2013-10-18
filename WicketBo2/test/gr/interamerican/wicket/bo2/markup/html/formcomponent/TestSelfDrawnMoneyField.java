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
package gr.interamerican.wicket.bo2.markup.html.formcomponent;

import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.math.BigDecimal;

import org.junit.Assert;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestSelfDrawnMoneyField {

	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester = null;
	/**
	 * 
	 */
	@Before
	public void setUp(){	
		wicketTester = new WicketTester();	
		wicketTester.startPage(MoneyPage.class);
		wicketTester.assertRenderedPage(MoneyPage.class);	
	}
	
	/** 
	 * Tests the components
	 */
	@Test
	public void testSelfDrawnMoneyField(){
		SelfDrawnMoneyField component =(SelfDrawnMoneyField)wicketTester.getComponentFromLastRenderedPage("component"); //$NON-NLS-1$
		Assert.assertEquals(SelfDrawnMoneyField.class, component.getClass());
		Assert.assertEquals(component.getDefaultModelObject(),new MoneyImpl(new BigDecimal("5")));  //$NON-NLS-1$
	}
}
