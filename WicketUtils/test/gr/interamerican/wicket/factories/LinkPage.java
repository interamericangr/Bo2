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
package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.util.resource.StringAsResourceStream;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * 
 */
public class LinkPage extends WebPage implements IMarkupResourceStreamProvider,IMarkupCacheKeyProvider{
	
	/**
	 * �he wicket id of component that we are going to test.
	 */
	private String wicketId;

	/**
	 * Creates a new LinkPage object. 
	 * 
	 * @param form 
	 * 		  The form that contains the component.
	 * @param wicketId 
	 * 		  The wicketId of component.
	 *
	 */
	public LinkPage(Form<?> form,String wicketId) {
		super();
		this.wicketId = wicketId;
		EmptyPanel emptyPanel = new EmptyPanel(wicketId);
		emptyPanel.setOutputMarkupPlaceholderTag(true);
		form.add(emptyPanel);
		this.add(form);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.IMarkupCacheKeyProvider#getCacheKey(org.apache.wicket.MarkupContainer, java.lang.Class)
	 */
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.IMarkupResourceStreamProvider#getMarkupResourceStream(org.apache.wicket.MarkupContainer, java.lang.Class)
	 */
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		 StringBuilder sb = new StringBuilder();
		 sb.append("<html><form wicket:id=\"tf\">"); //$NON-NLS-1$ 
		 sb.append("<input wicket:id=\""+wicketId+"Link\""); //$NON-NLS-1$ //$NON-NLS-2$
		 sb.append("></input>");  //$NON-NLS-1$ 
		 sb.append("<div wicket:id=\""+wicketId+"\" />"); //$NON-NLS-1$ //$NON-NLS-2$
		 sb.append("</form></html>"); //$NON-NLS-1$
         return new StringAsResourceStream(sb.toString());
	}

}
