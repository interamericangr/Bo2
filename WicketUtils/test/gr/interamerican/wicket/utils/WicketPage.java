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
package gr.interamerican.wicket.utils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.wicket.markup.html.panel.DataTableAjaxLinkPanel;
import gr.interamerican.wicket.markup.html.panel.DataTableRadioButtonPanel;
import gr.interamerican.wicket.markup.html.tabs.StatefulAjaxTabbedPanel;


/**
 * Wicket page is a webpage used for the tests of all 
 * the components in the module. We add the components 
 * here and we test them through the wicket tester.<br>
 * 
 * @deprecated to be removed
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Deprecated
public class WicketPage extends WebPage{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** button for sumbission of a form. */
	@SuppressWarnings("unused")
	private Button button = new Button("submit"); //$NON-NLS-1$

	/** The Constant REFRESH_PANEL_WICKET_ID. */
	@SuppressWarnings("unused")
	private static final String REFRESH_PANEL_WICKET_ID = "emptyPanel"; //$NON-NLS-1$
	
	/**
	 * Instantiates a new wicket page.
	 */
	@SuppressWarnings("nls")
	public WicketPage(){			

		//FeedBackPanel
		add(WicketUtils.createFeedbackPanel());

		//Test CreateLink
		Link<String> link = LinkUtils.createLink("link", this.getClass());
		add(link);	
		Link<String> link1 = LinkUtils.createLink(this.getClass());
		add(link1);
		Link<String> link2 = LinkUtils.createLink(new Pair<String, Class<? extends Page>>("link2", this.getClass()));
		add(link2);


		//Test DataTableLinkPanel
		DataTableLinkPanelImpl testDataTableLinkPanel = new
 DataTableLinkPanelImpl(
				"dateTableLinkPanel", new Model(), ImageType.COPY);
		add(testDataTableLinkPanel);				
		DataTableLinkPanelImpl testDataTableLinkPanel1 = new
 DataTableLinkPanelImpl(
				"dateTableLinkPanel1", new Model(), "This is a test");
		add(testDataTableLinkPanel1);
		
		Form form1=new Form("form");
		
	
		
		StatefulAjaxTabbedPanel tabs = new 
				 StatefulAjaxTabbedPanel("tabs",getITabs(),form1,null);
	
		form1.add(tabs);
		add(form1);

		//Test DataTableRadioButtonPanel		
		RadioGroup radioGroup = new RadioGroup("radioGroup");
		DataTableRadioButtonPanel<Serializable> dataTableRadioButtionPanel = 
 new DataTableRadioButtonPanel<Serializable>(
				"dataTableRadioButtonPanel", new Model());
		radioGroup.add(dataTableRadioButtionPanel);		
		add(radioGroup);
		
		
		//Test WicketUtils methods
		Form form2 = new Form("form2");
		TextField<String> textField = new TextField<String>("field");
		textField.setOutputMarkupId(true);
		form2.add(textField);
		WicketUtils.setVisibility(form2, new String[] { "field" }, true);
		WicketUtils.renderFields(new AjaxRequestHandler(this), form2, new String[] { "field" });
		add(form2);

	}

	


	/**
	 * Returns a list of tabs that contains one check box.
	 * @return List<ITab>
	 */
	@SuppressWarnings("serial")
	private List<ITab> getITabs(){		
		List<ITab> itabs = new ArrayList<ITab>();		
		itabs.add(new AbstractTab(new Model("firsttab")) { //$NON-NLS-1$
			@Override
			public Panel getPanel(String panelId) { 
				return new EmptyPanel(panelId); 
			}
		});		
		return itabs; 		
	}

	
	/**
	 * Test class to test the DataTableLinkPanel.
	 *
	 * @param <T> the generic type
	 */
	private class DataTableLinkPanelImpl<T> extends DataTableAjaxLinkPanel<T>{

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new data table link panel impl.
		 *
		 * @param id the id
		 * @param model the model
		 * @param linkText the link text
		 */
		public DataTableLinkPanelImpl(String id, IModel<T> model,
				String linkText ) {
			super(id, model, linkText);
		}

		/**
		 * 		
		 *
		 * @param id the id
		 * @param model the model
		 * @param imageType the image type
		 */
		public DataTableLinkPanelImpl(String id, IModel<T> model,
				ImageType imageType) {
			super(id, model, imageType);
		}

		@Override
		public void onLinkClicked(AjaxRequestTarget target) {
			//Do Nothing	
		}								
	}

}
