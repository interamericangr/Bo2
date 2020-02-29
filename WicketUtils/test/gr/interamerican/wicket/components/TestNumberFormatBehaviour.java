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
package gr.interamerican.wicket.components;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link NumberFormatBehaviour}.
 */
public class TestNumberFormatBehaviour extends WicketTest {

	/**
	 * Does any tests possible in java on that Behavior.
	 */
	@Test
	public void test() {
		XmlTag tag = new XmlTag();
		NumberFormatBehaviour behavior = new NumberFormatBehaviour();
		behavior.onComponentTag(null, new ComponentTag(tag));
		String value = (String) tag.getAttribute("onchange"); //$NON-NLS-1$
		tester.startResourceReference(NumberFormatBehaviour.JS);
		String method = value.substring(0, value.indexOf("(")); //$NON-NLS-1$
		assertTrue(tester.getLastResponseAsString().contains(method));
	}
}