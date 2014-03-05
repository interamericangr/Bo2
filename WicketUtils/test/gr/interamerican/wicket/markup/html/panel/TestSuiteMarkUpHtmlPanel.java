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
package gr.interamerican.wicket.markup.html.panel;

import gr.interamerican.wicket.markup.html.panel.back.TestServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.bean.TestSingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.TestCrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.listTable.TestListTablePanel;
import gr.interamerican.wicket.markup.html.panel.picker.TestMultipleSelectionsPanel;
import gr.interamerican.wicket.markup.html.panel.picker.TestPickerPanel;
import gr.interamerican.wicket.markup.html.panel.searchFlow.TestSearchFlowPanel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



/**
 * Test suite for package <code>gr.interamerican.wicket.utils</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{

	TestCheckBoxPanel.class,
	TestDataTableLinkPanel.class,
	TestDataTableRadioButtonPanel.class,
	TestServicePanelWithBack.class,
	TestSingleBeanPanel.class,
	TestListTablePanel.class,
	TestPickerPanel.class,
	TestMultipleSelectionsPanel.class,
	TestCrudPickerPanel.class,
	TestSearchFlowPanel.class,
	TestFilePanel.class,
	TestSimpleFilesPanel.class,
	}
)
public class TestSuiteMarkUpHtmlPanel { /* empty */ }
