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
package gr.interamerican.wicket.bo2.creators;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.wicket.samples.actions.PanelDependentAction;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Unit tests for {@link ChangeAwareSelfDrawnPanelCreator}.
 */
public class TestChangeAwareSelfDrawnPanelCreator {
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unused")
		PanelDependentAction pda = mock(PanelDependentAction.class);
		Bean1descriptor descriptor = new Bean1descriptor();
		Set<String> components = new HashSet<String>();
		PanelDependentAction action = mock(PanelDependentAction.class);
		
		ChangeAwareSelfDrawnPanelCreator<Bean1, PanelDependentAction> creator = 
			new ChangeAwareSelfDrawnPanelCreator<Bean1, PanelDependentAction>
			(descriptor, components, action);
		
		assertEquals(descriptor, creator.beanDescriptor);
		assertEquals(action, creator.onChangeAction);
		assertEquals(components, creator.onChangeUpdatingComponents);
	}
	
	

}
