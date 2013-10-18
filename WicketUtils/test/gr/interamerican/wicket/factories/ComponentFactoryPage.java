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

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * ComponentFactoryPage is a webpage used for the {@link ComponentFactory} tests. 
 * We add the components here and the markupConstant that is needed.
 */
public class ComponentFactoryPage extends WebPage implements IMarkupResourceStreamProvider,IMarkupCacheKeyProvider{
	/**
	 * A dummy Bean.
	 */
	@SuppressWarnings("unused")
	private Bean1 bean1 = new Bean1();
	/**
	 * button for sumbission of a form
	 */
	@SuppressWarnings("unused")
	private Button button = new Button("submit"); //$NON-NLS-1$
	/**
	 * 
	 */
	private String markupConstant;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String wicketId;
	
	/**
	 * Creates a new ComponentFactoryPage object. 
	 * @param cmp 
	 * 		  The component that you test
	 * @param markupConstant 
	 *		  The {@link MarkupConstants} that is needed
	 *		  for the test.
	 * @param type 
	 * 		  The tag of type that is needed for the test
	 */
	public ComponentFactoryPage(Component cmp,String markupConstant,String type) {
		super();
		this.markupConstant = markupConstant;
		this.type = type;
		Form<Void> form = new Form<Void>("tf"); //$NON-NLS-1$
		form.add(cmp);
		this.add(form);	
	}

	/**
	 * Creates a new ComponentFactoryPage object. 
	 * @param form 
	 * 		  The form that has the component that you want to test.
	 * @param markupConstant 
	 *		  The {@link MarkupConstants} that is needed
	 *		  for the test.
	 * @param type 
	 * 		  The tag of type that is needed for the test
	 */
	public ComponentFactoryPage(Form<?> form,String markupConstant,String type) {
		super();
		this.markupConstant = markupConstant;
		this.type = type;
		this.add(form);	
	}
	
	/**
	 * Creates a new ComponentFactoryPage object. 
	 * @param form 
	 * 		  The form that has the component that you want to test.
	 * @param markupConstant 
	 *		  The {@link MarkupConstants} that is needed
	 *		  for the test.
	 * @param type 
	 * 		  The tag of type that is needed for the test
	 * @param wicketId 
	 * 		  The wicketId that should have in the markup 
	 * 		  the component that you want to test.
	 */
	public ComponentFactoryPage(Form<?> form,String markupConstant,String type,String wicketId) {
		super();
		this.markupConstant = markupConstant;
		this.wicketId = wicketId;
		this.type = type;
		this.add(form);	
	}
	
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {	
		return null;
	}

	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		  StringBuilder sb = new StringBuilder();
		  
		  sb.append("<html><form wicket:id=\"tf\">"); //$NON-NLS-1$
		  if(wicketId==null){
			  wicketId = TestPage.TEST_ID;
		  }
		  sb.append("<"+markupConstant+" wicket:id=\""+wicketId+"\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		  if(!StringConstants.EMPTY.equals(type)){
			  sb.append("type=\""+type+"\""); //$NON-NLS-1$ //$NON-NLS-2$
		  }  
		  sb.append("></"+markupConstant+">");  //$NON-NLS-1$//$NON-NLS-2$ 
		  sb.append("</form></html>"); //$NON-NLS-1$

          return new StringAsResourceStream(sb.toString());
	}
}
