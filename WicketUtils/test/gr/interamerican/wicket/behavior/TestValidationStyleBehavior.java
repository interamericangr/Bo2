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
package gr.interamerican.wicket.behavior;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ValidationStyleBehavior}.
 */
public class TestValidationStyleBehavior extends WicketTest {

	/**
	 * Test method for onComponentTag.
	 */
	@Test
	public void testOnComponentTag() {
		/*
		 * Field's HTML element already has class attribute
		 */
		RequiredSelfDrawnTextField field = new RequiredSelfDrawnTextField(TestPage.TEST_ID);
		field.add(new AttributeModifier(MarkupConstants.CSS_CLASS, new Model<String>(MarkupConstants.TITLE)));
		field.add(ValidationStyleBehavior.INSTANCE);
		tester.startPage(getTestPage(field));
		FormTester formTester = getFormTester();
		formTester.setValue(TestPage.TEST_ID, StringConstants.EMPTY);
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		TagTester tagTester = tester.getTagByWicketId(TestPage.TEST_ID);
		String expected = MarkupConstants.INVALID + StringConstants.SPACE + MarkupConstants.TITLE;
		String actual = tagTester.getAttribute(MarkupConstants.CSS_CLASS);
		Assert.assertEquals(expected, actual);
		
		/*
		 * Field's HTML element does not have class attribute
		 */
		field = new RequiredSelfDrawnTextField(TestPage.TEST_ID);
		field.add(ValidationStyleBehavior.INSTANCE);
		tester.startPage(getTestPage(field));
		formTester = getFormTester();
		formTester.setValue(TestPage.TEST_ID, StringConstants.EMPTY);
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		tagTester = tester.getTagByWicketId(TestPage.TEST_ID);
		expected = MarkupConstants.INVALID;
		actual = tagTester.getAttribute(MarkupConstants.CSS_CLASS);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * A text field that will work as the {@link TestPage} subject
	 * and is required.
	 */
	private class RequiredSelfDrawnTextField extends TextField<String> {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new SelfDrawnTextField object. 
		 *
		 * @param id
		 */
		public RequiredSelfDrawnTextField(String id) {
			super(id, new Model<String>());
			this.setRequired(true);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			tag.setName(MarkupConstants.INPUT);
	        tag.put(MarkupConstants.TYPE, MarkupConstants.TEXT);
	        tag.put(MarkupConstants.STYLE, MarkupConstants.WIDTH);
	        super.onComponentTag(tag);
		}
	}

}
