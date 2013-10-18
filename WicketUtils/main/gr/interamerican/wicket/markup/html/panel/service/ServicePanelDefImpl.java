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

/**
 * Implementation of {@link ServicePanelDef}.
 */
public class ServicePanelDefImpl implements ServicePanelDef {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * wicket id.
	 */
	String wicketId;

	/**
	 * panel id.
	 */
	String panelId;
	
	/**
	 * This property indicates whether the buttons the user is not authorized
	 * to press will be disabled or will be enabled and a message will be
	 * displayed when they are pressed.
	 */
	Boolean disableUnauthorizedButtons;

	/**
	 * Service panel that is created with this definition instance.
	 */
	ServicePanel servicePanel;
	
	public String getWicketId() {
		return wicketId;
	}

	public void setWicketId(String wicketId) {
		this.wicketId = wicketId;
	}

	public String getPanelId() {
		return panelId;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}

	public Boolean getDisableUnauthorizedButtons() {
		return disableUnauthorizedButtons;
	}

	public void setDisableUnauthorizedButtons(Boolean disableUnauthorizedButtons) {
		this.disableUnauthorizedButtons = disableUnauthorizedButtons;
	}
	
	public ServicePanel getServicePanel() {
		return servicePanel;
	}

	public void setServicePanel(ServicePanel servicePanel) {
		this.servicePanel = servicePanel;
	}

}
