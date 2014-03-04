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
package gr.interamerican.wicket.markup.html.tabs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Test;

/**
 * Unit tests for {@link StatefulAjaxTabbedPanelSubmitLink}.
 */
public class TestStatefulAjaxTabbedPanelSubmitLink {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		String id = "LinkId"; //$NON-NLS-1$
		int index = 5;
		StatefulAjaxTabbedPanel panel = mock(StatefulAjaxTabbedPanel.class);
		StatefulAjaxTabbedPanelSubmitLink link = 
			new StatefulAjaxTabbedPanelSubmitLink(id, panel, index);
		assertEquals(id, link.getId());
		assertEquals(index, link.index);
		assertEquals(panel, link.owner);
	}
	
	/**
	 * Tests onSubmit().
	 */
	@Test
	public void testOnSubmit() {
		String id = "LinkId"; //$NON-NLS-1$
		int index = 5;
		StatefulAjaxTabbedPanel panel = mock(StatefulAjaxTabbedPanel.class);
		StatefulAjaxTabbedPanelSubmitLink link = 
			new StatefulAjaxTabbedPanelSubmitLink(id, panel, index);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		@SuppressWarnings("unchecked") Form<Object> form = mock(Form.class);		
		link.onSubmit(target, form);
		verify(target, times(1)).add(panel);
		verify(panel, times(1)).setSelectedTab(index);
		verify(panel, times(1)).callOnAjaxUpdate(target);
	}
	
	/**
	 * Tests onSubmit().
	 */
	@Test
	public void testOnError() {
		String id = "LinkId"; //$NON-NLS-1$
		int index = 5;
		StatefulAjaxTabbedPanel panel = mock(StatefulAjaxTabbedPanel.class);
		StatefulAjaxTabbedPanelSubmitLink link = 
			new StatefulAjaxTabbedPanelSubmitLink(id, panel, index);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		@SuppressWarnings("unchecked") Form<Object> form = mock(Form.class);		
		link.onError(target, form);
		verify(target, times(1)).add(panel);		
		verify(panel, times(1)).callOnAjaxUpdate(target);
	}



}
