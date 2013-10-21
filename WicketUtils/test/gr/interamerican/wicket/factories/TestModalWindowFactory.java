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

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestModalWindowFactory {
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
	 * 
	 */
	@Test
	public void testCreateModalWindow(){
		String path = "/gr/interamerican/wicket/factories/sample.properties"; //$NON-NLS-1$
		ModalWindow modalWindow = ModalWindowFactory.createModalWindow(path);
		Assert.assertSame(ModalWindow.class, modalWindow.getClass());
		Assert.assertEquals("userNotAuthorizedWindow", modalWindow.getId());  //$NON-NLS-1$
		Assert.assertEquals(400,modalWindow.getInitialWidth());
		Assert.assertEquals(200,modalWindow.getInitialHeight());
		Assert.assertEquals("PX",modalWindow.getWidthUnit()); //$NON-NLS-1$
		Assert.assertEquals("PX",modalWindow.getHeightUnit()); //$NON-NLS-1$
		Assert.assertFalse(modalWindow.isResizable());
		Assert.assertTrue(modalWindow.isUseInitialHeight());
		Assert.assertEquals("Authorization Failed",modalWindow.getTitle().getObject());  //$NON-NLS-1$
		
	}
}
