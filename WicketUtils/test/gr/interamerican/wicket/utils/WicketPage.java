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


import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.CheckBoxPanel;
import gr.interamerican.wicket.markup.html.panel.DataTableAjaxLinkPanel;
import gr.interamerican.wicket.markup.html.panel.DataTableButtonPanel;
import gr.interamerican.wicket.markup.html.panel.DataTableRadioButtonPanel;
import gr.interamerican.wicket.markup.html.tabs.StatefulAjaxTabbedPanel;
import gr.interamerican.wicket.samples.actions.DummyCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


/**
 * Wicket page is a webpage used for the tests of all 
 * the components in the module. We add the components 
 * here and we test them through the wicket tester.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WicketPage extends WebPage{

	/**
	 * button for sumbission of a form
	 */
	@SuppressWarnings("unused")
	private Button button = new Button("submit"); //$NON-NLS-1$

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final String REFRESH_PANEL_WICKET_ID = "emptyPanel"; //$NON-NLS-1$
	/**
	 * 
	 */
	public WicketPage(){			

		//FeedBackPanel
		add(WicketUtils.createFeedbackPanel());

		//Test CreateLink
		Link<String> link = LinkUtils.createLink("link", this.getClass()); //$NON-NLS-1$
		add(link);	
		Link<String> link1 = LinkUtils.createLink(this.getClass());
		add(link1);
		Link<String> link2 = LinkUtils.createLink(new Pair<String, Class<? extends Page>>("link2", this.getClass())); //$NON-NLS-1$
		add(link2);



				

		//Test CheckBoxPanel
		CheckBoxPanelImpl<Boolean> testCheckBoxPanel = new 
 CheckBoxPanelImpl<Boolean>(
				"checkBoxPanel", new Model<Boolean>(), true); //$NON-NLS-1$
		add(testCheckBoxPanel);


		//Test DataTableLinkPanel
		DataTableLinkPanelImpl testDataTableLinkPanel = new
 DataTableLinkPanelImpl(
				"dateTableLinkPanel", new Model(), ImageType.COPY); //$NON-NLS-1$
		add(testDataTableLinkPanel);				
		DataTableLinkPanelImpl testDataTableLinkPanel1 = new
 DataTableLinkPanelImpl(
				"dateTableLinkPanel1", new Model(), "This is a test"); //$NON-NLS-1$ //$NON-NLS-2$
		add(testDataTableLinkPanel1);

		//Test StatefulAjaxTabbedPanel
		StatefulAjaxTabbedPanel tabs = new 
 StatefulAjaxTabbedPanel("tabs", getITabs(), new Form("form"), null); //$NON-NLS-1$ //$NON-NLS-2$
		add(tabs);

        //Test DataTableButtonPanel
		Form form1 = new Form("form1"); //$NON-NLS-1$
		DataTableButtonPanelImpl<?> testDataTableButtonPanel = 
 new DataTableButtonPanelImpl<Serializable>(
				"dataTableButtonPanel", new Model(), ImageType.ARROW_DOWN, new DummyCallback()); //$NON-NLS-1$
		form1.add(testDataTableButtonPanel);
		add(form1);
		
		//Test DataTableRadioButtonPanel		
		RadioGroup radioGroup = new RadioGroup("radioGroup"); //$NON-NLS-1$
		DataTableRadioButtonPanel<Serializable> dataTableRadioButtionPanel = 
 new DataTableRadioButtonPanel<Serializable>(
				"dataTableRadioButtonPanel", new Model()); //$NON-NLS-1$
		radioGroup.add(dataTableRadioButtionPanel);		
		add(radioGroup);
		
		
		//Test WicketUtils methods
		Form form2 = new Form("form2"); //$NON-NLS-1$
		TextField<String> textField = new TextField<String>("field"); //$NON-NLS-1$
		textField.setOutputMarkupId(true);
		form2.add(textField);
		WicketUtils.setVisibility(form2, new String[] { "field" }, true); //$NON-NLS-1$
		WicketUtils.renderFields(new AjaxRequestTarget(this), form2, new String[] { "field" }); //$NON-NLS-1$
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
				return new CheckBoxPanelImpl(panelId, new Model<Boolean>(), true); 
			}
		});		
		return itabs; 		
	}


	/**
	 * 
	 * Concrete sub-type of CheckBoxPanel for testing.
	 * @param <T>
	 */
	private class CheckBoxPanelImpl<T> extends CheckBoxPanel<T> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * @param id
		 * @param model
		 * @param isSelected
		 */
		public CheckBoxPanelImpl(String id, IModel<T> model, boolean isSelected) {
			super(id, model, isSelected);

		}

	}

	
	/**
	 * 
	 * Test class to test the DataTableLinkPanel
	 *
	 * @param <T>
	 */
	private class DataTableLinkPanelImpl<T> extends DataTableAjaxLinkPanel<T>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * @param id
		 * @param model
		 * @param linkText
		 */
		public DataTableLinkPanelImpl(String id, IModel<T> model,
				String linkText ) {
			super(id, model, linkText);
		}

		/**
		 * 		
		 * @param id
		 * @param model
		 * @param imageType
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


	/**
	 * 
	 * Test class to test the DataTableButtonPanel
	 *
	 * @param <T>
	 */
	private class DataTableButtonPanelImpl<T extends Serializable> extends DataTableButtonPanel<T>{

		/**
		 * serial id.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * @param id
		 * @param model
		 * @param imageType
		 * @param block
		 */
		public DataTableButtonPanelImpl(String id, IModel<T> model,
				ImageType imageType, CallbackAction block) {
			super(id, model, imageType, block);
		}

		/**
		 * 
		 */
		@Override
		protected void onButtonError(AjaxRequestTarget target, Form<?> form) {
			/* do nothing */
		}
	}
	
	

}
