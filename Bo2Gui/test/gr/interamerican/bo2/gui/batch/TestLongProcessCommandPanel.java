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
package gr.interamerican.bo2.gui.batch;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.batch.LongProcessControl;
import gr.interamerican.bo2.gui.components.BButton;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * test case for {@link LongProcessControlPanel}
 */
public class TestLongProcessCommandPanel {

	/**
	 * test method for the constructor.
	 */
	@Test
	public void testConstructor() {		
		LongProcessControl model = Mockito.mock(LongProcessControl.class);
		LongProcessControlPanel p = new LongProcessControlPanel(model);		
		Assert.assertEquals(model, p.getModel());
		
		BButton pause = p.getButton("pause"); //$NON-NLS-1$
		Assert.assertNotNull(pause);
		pause.doClick();
		verify(model, atLeastOnce()).pause();
		
		BButton resume = p.getButton("resume"); //$NON-NLS-1$
		Assert.assertNotNull(resume);
		resume.doClick();
		verify(model, atLeastOnce()).resume();
		
		BButton forceQuit = p.getButton("forceQuit"); //$NON-NLS-1$
		Assert.assertNotNull(forceQuit);
		forceQuit.doClick();
		verify(model, atLeastOnce()).forceQuit();		
	}
	
}
