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
package gr.interamerican.wicket.markup.html.panel.service;

import gr.interamerican.wicket.def.FeedbackOwner;

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * {@link ServicePanelDef} with a feedback panel.
 */
public abstract class ServicePanelWithFeedback 
extends ServicePanel implements FeedbackOwner {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Feedback panel.
	 */
	protected FeedbackPanel feedBackPanel;
	
	/**
	 * wicket id.
	 */
	private static final String FEEDBACK_PANEL_ID = "feedback"; //$NON-NLS-1$
	
	/**
	 * Creates a new ServicePanelWithFeedback object. 
	 * 
	 * @param definition 
	 */
	public ServicePanelWithFeedback(ServicePanelDef definition) {
		super(definition);
		setOutputMarkupPlaceholderTag(true);
	}

	@Override
	protected abstract void paint();
	
	@Override
	protected void validateDef() {
		super.validateDef();
	}

	@Override
	protected void init() {
		feedBackPanel = new FeedbackPanel(FEEDBACK_PANEL_ID, new ContainerFeedbackMessageFilter(this));
		feedBackPanel.setOutputMarkupPlaceholderTag(true);
	}
	
	public FeedbackPanel getFeedBackPanel() {
		return feedBackPanel;
	}

}
