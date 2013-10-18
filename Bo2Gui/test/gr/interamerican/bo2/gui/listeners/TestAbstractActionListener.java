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
package gr.interamerican.bo2.gui.listeners;

import gr.interamerican.bo2.utils.handlers.ExceptionHandler;

import java.awt.event.ActionEvent;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link AbstractActionListener}.
 */
public class TestAbstractActionListener {
	
	/**
	 * Unit test for action performed.
	 */
	@Test
	public void testActionPerformed() {
		Cal cal = new Cal();
		ActionEvent event = Mockito.mock(ActionEvent.class);
		cal.actionPerformed(event);
		ActionEvent actual = cal.getHandlerParameter(ActionEvent.class);
		Assert.assertEquals(event, actual);
	}
	
	
	/**
	 * Concrete implementation for testing.
	 */
	@SuppressWarnings("serial")
	class Cal extends AbstractActionListener {
		/**
		 * Creates a new Cal object. 
		 */
		public Cal() {
			super(Mockito.mock(ExceptionHandler.class));
		}

		@Override
		void work() {
			/* empty */			
		}
	}
	

}
