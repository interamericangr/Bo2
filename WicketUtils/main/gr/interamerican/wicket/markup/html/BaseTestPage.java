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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.def.FeedbackOwner;
import gr.interamerican.wicket.def.WicketOutputMedium;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Base class for unit test classes of Wicket components.
 * 
 * Provides a single argument constructor in order to make easy the
 * usage of {@link WicketTester}.
 */
public abstract class BaseTestPage 
extends WebPage 
implements FeedbackOwner, WicketOutputMedium {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id of component to test.
	 */
	public static final String TEST_ID = "testId"; //$NON-NLS-1$
	
	/**
	 * id of form containing the component to test.
	 */
	public static final String FORM_ID = "tf"; //$NON-NLS-1$
	
	/**
	 * id of form containing the component to test.
	 */
	public static final String FEEDBACK_PANEL_ID = "feedback"; //$NON-NLS-1$
	
	/** feedback panel. */
	protected FeedbackPanel feedbackPanel = new FeedbackPanel(FEEDBACK_PANEL_ID);
	
	/**
	 * Model of label.
	 */
	private Model<String> errorLabelModel = new Model<String>();
	/**
	 * Error label.
	 */
	private Label errorLabel = new Label("errorLabel", errorLabelModel); //$NON-NLS-1$
	
	/** Was an error rendered?. */
	private boolean error = false;

	/**
	 * Creates a new BaseTestPage object.
	 */
	public BaseTestPage() {
		feedbackPanel.setOutputMarkupId(true);
		errorLabel.setOutputMarkupId(true);
		add(feedbackPanel);
		add(errorLabel);
	}

	@Override
	public FeedbackPanel getFeedBackPanel() {
		return feedbackPanel;
	}
	@Override
	public void clearMessages(AjaxRequestTarget target) {
		target.add(errorLabel);
		errorLabelModel.setObject(StringConstants.EMPTY);
	}

	@Override
	public void showError(Throwable t, AjaxRequestTarget target) {
		error = true;
		target.add(errorLabel);
		errorLabelModel.setObject(t.getMessage());
	}

	/**
	 * Gets the error.
	 *
	 * @return Returns the error
	 */
	public boolean isError() {
		return error;
	}

}
