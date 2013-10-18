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
package gr.interamerican.wicket.markup.html;

import gr.interamerican.wicket.util.resource.StringAsResourceStream;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * This {@link Panel} is used as the test subject of a test
 * page whenever it is not possible to add the component under
 * test to the {@link TestPage} markup.
 * The component under test should be placed in this panel and 
 * appropriate markup should be provided in the panel's constructor.
 */
public class TestPanel extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Markup builder.
	 */
	private String markup;
	
	/**
	 * Creates a new TestPanel object. 
	 *
	 * @param id
	 * @param markup 
	 */
	public TestPanel(String id, String markup) {
		super(id);
		this.markup = markup;
	}

	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null;
	}

	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		return new StringAsResourceStream(markup);
	}

}
