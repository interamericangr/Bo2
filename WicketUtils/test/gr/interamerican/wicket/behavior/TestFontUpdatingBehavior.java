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

import static org.junit.Assert.*;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable.Caption;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link FontUpdatingBehavior}
 */
public class TestFontUpdatingBehavior extends WicketTest{

	/**
	 * Test method for {@link gr.interamerican.wicket.behavior.FontUpdatingBehavior#FontUpdatingBehavior(java.lang.String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFontUpdatingBehavior() {
		FontUpdatingBehavior tested = new FontUpdatingBehavior("10");
		assertEquals("10",tested.fontSize);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.behavior.FontUpdatingBehavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOnComponentTagComponentComponentTag() {
		FontUpdatingBehavior tested = new FontUpdatingBehavior("10");
		Caption component = new Caption("sampleId", Model.of("text"));
		ComponentTag tag = new ComponentTag("test", TagType.OPEN_CLOSE);
		
		tested.onComponentTag(component, tag );
		assertNotNull(tag.getAttribute("style"));
	}

}
