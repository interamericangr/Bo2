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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link StatefulAjaxTabbedPanel}
 */
public class TestStatefulAjaxTabbedPanel extends WicketTest {

	/**
	 * Tests rendering of a panel.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRendering() {
		ArrayList<AbstractTab> tabs = new ArrayList<AbstractTab>();
		tabs.add(new AbstractTab(Model.of("sampleTab")) {
			
			/**
			 * Default UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new EmptyPanel(panelId);
			}
		});
		
		AjaxTabbedPanel<AbstractTab> tabsPanel = new AjaxTabbedPanel<>("tabs", tabs);
		tabsPanel = tester.startComponentInPage(tabsPanel);
		assertEquals(1, tabsPanel.getTabs().size());
	}

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		ITab tab1 = mock(ITab.class);
		ITab tab2 = mock(ITab.class);
		ITab[] array = { tab1, tab2 };
		List<ITab> tabs = Arrays.asList(array);
		String id = "TabId"; //$NON-NLS-1$
		StatefulAjaxTabbedPanel panel = new StatefulAjaxTabbedPanel(id, tabs);
		assertEquals(panel.getTabs().size(), tabs.size());
	}

	/**
	 * Tests newLink(id,index).
	 */
	@Test
	public void testNewLink() {
		ITab tab1 = mock(ITab.class);
		ITab tab2 = mock(ITab.class);
		ITab[] array = { tab1, tab2 };
		List<ITab> tabs = Arrays.asList(array);
		String id = "TabId"; //$NON-NLS-1$
		String linkid = "LinkId"; //$NON-NLS-1$
		int index = 1;
		StatefulAjaxTabbedPanel panel = new StatefulAjaxTabbedPanel(id, tabs);
		WebMarkupContainer link = panel.newLink(linkid, index);
		Assert.assertNotNull(link);
	}
}