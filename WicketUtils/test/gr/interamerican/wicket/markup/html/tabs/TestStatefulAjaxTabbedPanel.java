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
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.WicketPage;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 
 *
 */
public class TestStatefulAjaxTabbedPanel 
extends WicketTest {


	/**
	 * Tests rendering of a panel.
	 */
	@Test
	public void testRendering(){
		tester.startPage(WicketPage.class);	
		tester.assertComponent("tabs", AjaxTabbedPanel.class); //$NON-NLS-1$
		AjaxTabbedPanel panel = (AjaxTabbedPanel) 
			tester.getComponentFromLastRenderedPage("tabs");   //$NON-NLS-1$
		assertEquals(panel.getTabs().size(),1L);	    	 
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor(){
		ITab tab1 = mock(ITab.class);
		ITab tab2 = mock(ITab.class);
		ITab[] array = {tab1, tab2};
		List<ITab> tabs = Arrays.asList(array);
		@SuppressWarnings("unchecked") Form<Object> form = mock(Form.class);
		Button button = mock(Button.class);
		
		String id = "TabId"; //$NON-NLS-1$
		StatefulAjaxTabbedPanel panel = 
			new StatefulAjaxTabbedPanel(id, tabs, form, button);			   
		assertEquals(panel.getTabs().size(),tabs.size());
		assertEquals(form, panel.form);
		assertEquals(button, panel.button);
	}
	
	/**
	 * Tests newLink(id,index).
	 */
	@Test
	public void testNewLink(){
		ITab tab1 = mock(ITab.class);
		ITab tab2 = mock(ITab.class);
		ITab[] array = {tab1, tab2};
		List<ITab> tabs = Arrays.asList(array);
		@SuppressWarnings("unchecked") Form<Object> form = mock(Form.class);
		Button button = mock(Button.class);
		
		String id = "TabId"; //$NON-NLS-1$
		String linkid = "LinkId"; //$NON-NLS-1$
		int index = 1;
		StatefulAjaxTabbedPanel panel = 
			new StatefulAjaxTabbedPanel(id, tabs, form, button);
		WebMarkupContainer link = panel.newLink(linkid, index);
		Assert.assertNotNull(link);
	}


}
