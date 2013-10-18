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
package gr.interamerican.wicket;



import gr.interamerican.wicket.ajax.markup.html.form.TestSuiteAjaxMarkupHtmlForm;
import gr.interamerican.wicket.behavior.TestSuiteWicketUtilsBehavior;
import gr.interamerican.wicket.callback.TestSuiteCallback;
import gr.interamerican.wicket.components.TestSuiteComponents;
import gr.interamerican.wicket.condition.TestSuiteCondition;
import gr.interamerican.wicket.creators.TestSuiteWicketCreators;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.TestSuiteExtensionsMarkupHtmlRepeaterUtil;
import gr.interamerican.wicket.factories.TestSuiteFactories;
import gr.interamerican.wicket.markup.html.TestSuiteMarkUpHtml;
import gr.interamerican.wicket.markup.html.panel.service.TestSuiteServicePanel;
import gr.interamerican.wicket.modifiers.TestSuiteModifiers;
import gr.interamerican.wicket.util.resource.TestSuiteUtilResource;
import gr.interamerican.wicket.utils.TestSuiteUtils;

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
		TestSuiteAjaxMarkupHtmlForm.class,
		TestSuiteWicketUtilsBehavior.class,
		TestSuiteCallback.class,
		TestSuiteFactories.class,
		TestSuiteWicketCreators.class,		
		TestSuiteExtensionsMarkupHtmlRepeaterUtil.class,		
		TestSuiteMarkUpHtml.class,
		TestSuiteUtilResource.class,
		TestSuiteUtils.class,	
		TestSuiteServicePanel.class,
		TestSuiteModifiers.class,
		TestSuiteCondition.class,
		TestSuiteComponents.class,
	}
)
public class TestSuiteWicketUtils {
  //empty
}
