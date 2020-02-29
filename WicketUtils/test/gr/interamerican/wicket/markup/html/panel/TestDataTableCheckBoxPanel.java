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
package gr.interamerican.wicket.markup.html.panel;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.wicket.Component;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests the {@link DataTableCheckBoxPanel}.
 */
public class TestDataTableCheckBoxPanel extends WicketTest {

	/**
	 * Test check box panel.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDataTableCheckBoxPanel() {
		CheckGroup<BeanWithOrderedFields> group = new CheckGroup<>("group", new ArrayList<>());
		group.add(new DataTableCheckBoxPanel<BeanWithOrderedFields>("tested",
				new Model<BeanWithOrderedFields>(new BeanWithOrderedFields())));
		group.setMarkup(Markup.of("<wicket:extend> <input wicket:id=\"tested\" /></wicket:extend>"));
		tester.startComponentInPage(group);
		commonAssertions_noError(BaseWicketTester.StartComponentInPage.class);
		Component component = tester.getComponentFromLastRenderedPage("group:tested");
		assertTrue(component instanceof DataTableCheckBoxPanel);
	}
}