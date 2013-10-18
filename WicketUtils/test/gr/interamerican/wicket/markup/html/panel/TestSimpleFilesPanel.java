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

import gr.interamerican.bo2.utils.attributes.NamedDescribed;
import gr.interamerican.wicket.callback.ChainedCallbackAction;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link SimpleFilesPanel}.
 */
public class TestSimpleFilesPanel extends WicketTest {
	
	/**
	 * Test construct
	 */
	@Test
	public void testConstruct() {
		tester.startPage(testPageSource());
	}
	
	@Override
	protected Component initializeComponent() {
		List<NamedDescribed> defs = new ArrayList<NamedDescribed>();
		NamedDescribed def1 = new NamedDescribedImpl();
		def1.setName("file1"); //$NON-NLS-1$
		def1.setDescription("file1 descr"); //$NON-NLS-1$
		NamedDescribed def2 = new NamedDescribedImpl();
		def2.setName("file2"); //$NON-NLS-1$
		def2.setDescription("file2 descr"); //$NON-NLS-1$
		defs.add(def1);
		defs.add(def2);
		
		SimpleFilesPanelDef def = new SimpleFilesPanelDefImpl();
		def.setSubmitAction(Mockito.mock(ChainedCallbackAction.class));
		def.setWicketId(TestPage.TEST_ID);
		def.setFileDefinitions(defs);
		
		return new SimpleFilesPanel(def);
		
	}
	
	@SuppressWarnings("javadoc")
	class NamedDescribedImpl implements NamedDescribed {

		String name;
		String description;
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public void setDescription(String description) {
			this.description = description;
		}
		
	}

}
