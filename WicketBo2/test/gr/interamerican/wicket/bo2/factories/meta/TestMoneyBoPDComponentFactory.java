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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.utils.meta.ext.MoneyOwnerObject;
import gr.interamerican.bo2.samples.utils.meta.ext.MoneyOwnerObjectDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.formcomponent.MoneyPage;
import gr.interamerican.wicket.bo2.markup.html.formcomponent.SelfDrawnMoneyField;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.utils.WicketUtils;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestMoneyBoPDComponentFactory extends Bo2WicketTest{
	/**
	 * 
	 */
	protected static final String COMPONENT = "component"; //$NON-NLS-1$
	/**
	 * moneyOwner.
	 */
	private MoneyOwnerObject moneyOwner = new MoneyOwnerObject(new MoneyImpl());
	
	/**
	 * moneyOwner Descriptor.
	 */
	private MoneyOwnerObjectDescriptor moneyDescriptor = new MoneyOwnerObjectDescriptor();
	
	/**
	 * 
	 */
	private MoneyBoPDComponentFactory moneyBoPDComponentFactory = 
		new MoneyBoPDComponentFactory();
	
	/**
	 * 
	 */
	private MoneyBoPropertyDescriptor moneyBoPropertyDescriptor = 
		moneyDescriptor.moneyDescriptor();
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester = null;
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = moneyBoPDComponentFactory.drawMain(moneyBoPropertyDescriptor,COMPONENT);
		Assert.assertNotNull(component);
		wicketTester = new WicketTester();
		MoneyPage moneyPage = new MoneyPage(component);
		wicketTester.startPage(moneyPage);
		wicketTester.assertRenderedPage(moneyPage.getClass());
		wicketTester.assertComponent(WicketUtils.wicketPath(COMPONENT), SelfDrawnMoneyField.class);  
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Sec(){
		PropertyModel<Money> model  = new PropertyModel<Money>(moneyOwner, moneyBoPropertyDescriptor.getName());
		Component component = moneyBoPDComponentFactory.drawMain(COMPONENT,model,moneyBoPropertyDescriptor);
		wicketTester = new WicketTester();
		MoneyPage moneyPage = new MoneyPage(component);
		wicketTester.startPage(moneyPage);
		wicketTester.assertRenderedPage(moneyPage.getClass());
		wicketTester.assertComponent(WicketUtils.wicketPath(COMPONENT), SelfDrawnMoneyField.class);  
	}

}
