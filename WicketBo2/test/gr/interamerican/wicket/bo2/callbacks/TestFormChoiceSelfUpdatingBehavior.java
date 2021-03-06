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
package gr.interamerican.wicket.bo2.callbacks;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

/**
 * Unit tests for {@link FormChoiceSelfUpdatingBehavior}
 */
public class TestFormChoiceSelfUpdatingBehavior {

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.FormChoiceSelfUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@Test
	public void testOnUpdateAjaxRequestTarget() {
		AjaxRequestTarget sampleTarget = mock(AjaxRequestTarget.class);
		FormChoiceSelfUpdatingBehavior tested = new FormChoiceSelfUpdatingBehavior();
		tested.onUpdate(sampleTarget);
		
		verify(sampleTarget).add(isNull());
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.FormChoiceSelfUpdatingBehavior#getAjaxIndicatorMarkupId()}.
	 */
	@Test
	public void testGetAjaxIndicatorMarkupId() {
		FormChoiceSelfUpdatingBehavior tested = new FormChoiceSelfUpdatingBehavior();
		assertNull(tested.getAjaxIndicatorMarkupId());
	}

}
