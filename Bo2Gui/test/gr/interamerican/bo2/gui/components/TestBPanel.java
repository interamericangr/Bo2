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
package gr.interamerican.bo2.gui.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.gui.properties.LabelProperties;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.awt.Component;
import java.awt.event.ActionListener;

import org.junit.Test;

/**
 * Unit test for {@link BPanel}.
 */
public class TestBPanel {
	
	/**
	 * TextField and Label properties for the tests.
	 * 
	 * @return Returns a TextFieldProperties.
	 */
	static TextFieldProperties textAndLabel() {
		TextFieldProperties tfp = Factory.create(TextFieldProperties.class);
		tfp.setColumns(20);
		tfp.setHasLabel(true);
		tfp.setLabelLength(20);	
		return tfp;
		
	}
	
	/**
	 * Assert that a button has one action listener.
	 * 
	 * @param button
	 */
	static void assertButtonHasOneListener(BButton button) {
		ActionListener[] listeners = button.getActionListeners();
		assertEquals(1, listeners.length);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Object o = new Object();				
		BPanel<Object> sample = new BPanel<Object>(o);
		assertEquals(sample.getModel(),o);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testAddButton() {
		String method = "getField1"; //$NON-NLS-1$
		BeanWith2Fields o = new BeanWith2Fields();				
		BPanel<BeanWith2Fields> panel = new BPanel<BeanWith2Fields>(o);		
		BButton expected = panel.addButton(method, null, o);
		BButton actual = panel.buttons.get(method);
		assertEquals(expected, actual);	
		assertButtonHasOneListener(actual);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testAddModelBoundButton() {
		String method = "getField1"; //$NON-NLS-1$
		BeanWith2Fields o = new BeanWith2Fields();				
		BPanel<BeanWith2Fields> panel = new BPanel<BeanWith2Fields>(o);		
		BButton expected = panel.addModelBoundButton(method, null);
		BButton actual = panel.buttons.get(method);		
		assertEquals(expected, actual);	
		assertButtonHasOneListener(actual);
	}
	
	/**
	 * Tests the addStaticLabel(string,componentProperties).
	 */
	@Test
	public void testAddStaticLabel() {
		BeanWith2Fields o = new BeanWith2Fields();		
		BPanel<BeanWith2Fields> panel = new BPanel<BeanWith2Fields>(o);		
		Component[] before = panel.getComponents();		
		LabelProperties lp = textAndLabel();
		panel.addStaticLabel("static", lp); //$NON-NLS-1$
		Component[] after = panel.getComponents();				
		assertEquals(before.length+1, after.length);
	}
	
	/**
	 * Tests the addModelBoundLabel(string,componentProperties).
	 */
	@Test
	public void testAddModelBoundLabel() {
		BeanWith2Fields o = new BeanWith2Fields();		
		BPanel<BeanWith2Fields> panel = new BPanel<BeanWith2Fields>(o);
		LabelProperties lp = textAndLabel();
		String name = "field1"; //$NON-NLS-1$
		panel.addModelBoundLabel(name, lp);
		ValueComponent label = panel.valueComponents.get(name);
		assertNotNull(label);
		assertTrue(label instanceof BLabel);
	}
	
	/**
	 * Tests the addModelBoundLabels(strings,componentProperties).
	 */
	@Test
	public void testAddModelBoundLabels() {
		BeanWith2Fields o = new BeanWith2Fields();		
		BPanel<BeanWith2Fields> panel = new BPanel<BeanWith2Fields>(o);
		LabelProperties lp = textAndLabel();		
		String[] names = {"field1", "field2"}; //$NON-NLS-1$ //$NON-NLS-2$
		panel.addModelBoundLabels(names, lp);		
		for (String string : names) {
			ValueComponent label = panel.valueComponents.get(string);
			assertNotNull(label);
			assertTrue(label instanceof BLabel);
		}		
	}
	
	
	
	
	
}
