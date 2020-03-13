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

import static org.junit.Assert.*;

import java.util.Random;

import org.apache.wicket.Component;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests the {@link DataTableRadioButtonPanel}.
 */
public class TestDataTableRadioButtonPanel extends WicketTest {

	/**
	 * Test check box panel.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDataTableRadioButtonPanel() {
		RadioGroup<BeanWith1Field> group = new RadioGroup<>("group");
		BeanWith1Field bean = new BeanWith1Field(new Random().nextLong());
		group.add(new DataTableRadioButtonPanel<BeanWith1Field>("tested", new Model<BeanWith1Field>(bean)));
		group.setMarkup(Markup.of("<wicket:extend> <input wicket:id=\"tested\" /></wicket:extend>"));
		tester.startComponentInPage(group);
		commonAssertions_noError(BaseWicketTester.StartComponentInPage.class);
		Component tested = tester.getComponentFromLastRenderedPage("group:tested");
		assertTrue(tested instanceof DataTableRadioButtonPanel);
		@SuppressWarnings("unchecked")
		Radio<BeanWith1Field> button = (Radio<BeanWith1Field>) tested.get("radioButton"); //$NON-NLS-1$
		assertSame(bean, button.getDefaultModelObject());
	}
}
