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
package gr.interamerican.wicket.bo2;

import gr.interamerican.wicket.bo2.callbacks.TestSuiteWicketBo2Callbacks;
import gr.interamerican.wicket.bo2.creators.TestSuiteWicketBo2Creators;
import gr.interamerican.wicket.bo2.descriptors.TestSuiteWicketBo2Descriptors;
import gr.interamerican.wicket.bo2.factories.TestSuiteWicketBo2Factories;
import gr.interamerican.wicket.bo2.factories.meta.TestSuiteComponentFactories;
import gr.interamerican.wicket.bo2.markup.html.form.TestSuiteWicketBo2MarkUpHtmlForm;
import gr.interamerican.wicket.bo2.markup.html.formcomponent.TestSelfDrawnMoneyField;
import gr.interamerican.wicket.bo2.markup.html.panel.TestSuiteWicketBo2MarkUpHtmlPanel;
import gr.interamerican.wicket.bo2.protocol.http.TestSuiteWicketBo2ProtocolHttp;
import gr.interamerican.wicket.bo2.util.resource.TestSuiteWicketBo2UtilResource;
import gr.interamerican.wicket.bo2.utils.TestSuiteWicketBo2Utils;
import gr.interamerican.wicket.bo2.validation.TestSuiteWicketBo2Validation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open.po.collections</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{			
		TestSuiteWicketBo2MarkUpHtmlForm.class,
		TestSelfDrawnMoneyField.class,
	    TestSuiteWicketBo2ProtocolHttp.class,
	    TestSuiteComponentFactories.class,
	    TestSuiteWicketBo2Factories.class,
	    TestSuiteWicketBo2Creators.class,
	    TestSuiteWicketBo2Descriptors.class,
		TestSuiteWicketBo2Utils.class,
		TestSuiteWicketBo2MarkUpHtmlPanel.class,
		TestSuiteWicketBo2UtilResource.class,
		TestSuiteWicketBo2Validation.class,
		TestSuiteWicketBo2Callbacks.class,
	}
)
public class TestSuiteWicketBo2 {
	/* empty */
}
