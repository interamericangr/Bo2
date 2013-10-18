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
package gr.interamerican.wicket.bo2.protocol.http;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.wicket.samples.pages.EmptyPage;

import java.util.Collection;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2WicketApplication}.
 */
public class TestBo2WicketApplication {
	
	/**
	 * Tests the Page
	 */
	@Test
	public void testConstructor(){
		MockBo2WicketApplicaton app = new MockBo2WicketApplicaton();
		WicketTester wicketTester = new WicketTester(app);
		wicketTester.startPage(app.getHomePage());		
		wicketTester.assertRenderedPage(app.getHomePage());		
	}

	
	/**
	 * 
	 * Test mock class to test the Bo2WicketApplication
	 *
	 */
	private class MockBo2WicketApplicaton extends Bo2WicketApplication<String,String>{

		@Override
		public String defaultLanguageId() {
			return null;
		}

		@Override
		public Collection<TranslatableEntry<String, ?, String>> supportedLanguages() {
			return null;
		}

		@Override
		public Class<? extends Page> getHomePage() {
			return EmptyPage.class;
		}
		
	}
	
	
	
}
