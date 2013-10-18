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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.gui.components.BStaticLabel;
import gr.interamerican.bo2.gui.components.BTextField;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Unit tests for {@link Bo2GuiUtils}.
 */
public class TestBo2GuiUtils {
	
	
	/**
	 * Tests getContainerTopDownHierarchy().
	 */
	@Test
	public void testGetContainerTopDownHierarchy() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JTextField t1 = new JTextField();
		p1.add(p2);
		p2.add(t1);
		List<Component> hierarchy = 
			Bo2GuiUtils.getContainerTopDownHierarchy(t1);
		assertNotNull(hierarchy);
		assertEquals(3, hierarchy.size());
		assertEquals(p1, hierarchy.get(0));
		assertEquals(p2, hierarchy.get(1));
		assertEquals(t1, hierarchy.get(2));		
	}
	
	/**
	 * Tests refreshStaticContent().
	 */
	@Test
	public void testRefreshStaticContent() {
		Component[] components = {new BTextField("X")}; //$NON-NLS-1$
		BStaticLabel label = mock(BStaticLabel.class);		
		when(label.getComponents()).thenReturn(components);
		Bo2GuiUtils.refreshStaticContent(label);
		verify(label, times(1)).refreshContent();
	}

}
