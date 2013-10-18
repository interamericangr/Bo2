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
package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.WicketUtils;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 *  Test {@link LinkFactory}.
 */
public class TestLinkFactory extends WicketTest{
	
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
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.LinkFactory#createLink(java.lang.String, org.apache.wicket.model.IModel, gr.interamerican.wicket.callback.CallbackAction)}.
	 */
	@Test
	public void testCreateLinkStringIModelOfStringCallbackAction() {
		Form<Void> testForm  = new Form<Void>(TestPage.FORM_ID);
		AjaxLink<?> testLink = LinkFactory.createLink(TestPage.TEST_ID + "Link", new Model<String>(), null); //$NON-NLS-1$
		testForm.add(testLink);
		wicketTester.startPage(new LinkPage(testForm, TestPage.TEST_ID));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID+"Link"), AjaxLink.class); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.LinkFactory#createLink(java.lang.String, gr.interamerican.wicket.callback.CallbackAction)}.
	 */
	@Test
	public void testCreateLinkStringCallbackAction() {
		Form<Void> testForm  = new Form<Void>(TestPage.FORM_ID);
		AjaxLink<?> testLink = LinkFactory.createLink(TestPage.TEST_ID+"Link",null); //$NON-NLS-1$
		testForm.add(testLink);
		wicketTester.startPage(new LinkPage(testForm, TestPage.TEST_ID));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID+"Link"), AjaxLink.class); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.LinkFactory#createTogleVisibleLink(java.lang.String, org.apache.wicket.MarkupContainer)}.
	 */
	@Test
	public void testCreateTogleVisibleLink() {
		Form<Void> testForm  = new Form<Void>(TestPage.FORM_ID);
		AjaxLink<?> testLink = LinkFactory.createTogleVisibleLink(TestPage.TEST_ID, testForm);
		testForm.add(testLink);
		wicketTester.startPage(new LinkPage(testForm, TestPage.TEST_ID));
		wicketTester.dumpPage();
		AjaxLink<?> link = (AjaxLink<?>) wicketTester.getComponentFromLastRenderedPage("tf:testIdLink");  //$NON-NLS-1$
		link.setEnabled(true);
		wicketTester.clickLink("tf:testIdLink", true); //$NON-NLS-1$
		wicketTester.isVisible("tf:testId"); //$NON-NLS-1$
	}
	
	

}
