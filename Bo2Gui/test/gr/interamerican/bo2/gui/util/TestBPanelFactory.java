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
package gr.interamerican.bo2.gui.util;

import static gr.interamerican.bo2.gui.util.BPanelFactory.create;
import static gr.interamerican.bo2.gui.util.BPanelFactory.getModelClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.panels.BeanWith2FieldsPanel;
import gr.interamerican.bo2.utils.Utils;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit tests for {@link BPanelFactory}.
 */
public class TestBPanelFactory {
	
	/**
	 * Tests getModelClass().
	 */
	@Test
	public void testGetModelClass() {		
		Class<?> clazz = getModelClass(BeanWith2FieldsPanel.class);
		Assert.assertEquals(BeanWith2Fields.class, clazz);
	}
	
	/**
	 * Unit test for create(properties).	  
	 */
	@Test
	public void testCreate_withProperties() {
		String string = "s"; //$NON-NLS-1$
		Integer four = 4;
		Properties p = new Properties();
		p.setProperty("panelType", BeanWith2FieldsPanel.class.getName()); //$NON-NLS-1$
		p.setProperty("field1", string); //$NON-NLS-1$
		p.setProperty("field2", four.toString()); //$NON-NLS-1$
		BeanWith2FieldsPanel panel = Utils.cast(BPanelFactory.create(p));
		assertNotNull(panel);
		BeanWith2Fields model = panel.getModel();
		assertNotNull(model);
		assertEquals(string, model.getField1());
		assertEquals(four, model.getField2());
		
	}
	
	/**
	 * Tests create(clazz).
	 */
	@Test
	public void testCreate_withClass() {		
		BeanWith2FieldsPanel panel = create(BeanWith2FieldsPanel.class);
		assertNotNull(panel);
		assertNotNull(panel.getModel());
	}
	
	/**
	 * Tests create(clazz,model).
	 */
	@Test
	public void testCreate_withModel() {
		BeanWith2Fields bean = new BeanWith2Fields();
		BeanWith2FieldsPanel panel = create(BeanWith2FieldsPanel.class, bean);
		assertNotNull(panel);
		assertEquals(bean, panel.getModel());
	}
	
	
	

}
