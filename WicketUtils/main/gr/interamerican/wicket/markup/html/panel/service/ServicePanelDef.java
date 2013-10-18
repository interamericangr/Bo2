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

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.factories.ServicePanelFactory;

import java.io.Serializable;


/**
 * {@link ServicePanelDef} is the base type for definition beans of wicket
 * components that provide user interface services. <br/>
 * 
 * Wicket service definitions are the means for information exchange between
 * service client and service provider. They also act as keys used in order 
 * to locate the appropriate wicket service implementation. <br/>
 * 
 * ServicePanels execute actions provided by their clients. The providers may
 * also provide a {@link Flag} for each action that indicates whether the
 * current user is authorized to use the button. If Flag.isUp() returns true
 * or Flag is null, the user is allowed to execute the action.  
 * 
 * Whether the buttons are
 * disabled or show an error message when pressed for unauthorized users is
 * controlled by the <code>disableUnauthorizedButtons</code> property.
 */
public interface ServicePanelDef extends Serializable {
	
	/**
	 * Gets the panelId.
	 *
	 * @return Returns the panelId
	 */
	public String getPanelId();

	/**
	 * Assigns a new value to the panelId. The panelId is used for
	 * Panel creation by the {@link ServicePanelFactory} implementation.
	 *
	 * @param panelId the panelId to set
	 */
	public void setPanelId(String panelId);
	
	/**
	 * Gets the wicketId.
	 *
	 * @return Returns the wicketId
	 */
	public String getWicketId();

	/**
	 * Assigns a new value to the wicketId.
	 *
	 * @param wicketId the wicketId to set
	 */
	public void setWicketId(String wicketId);
	
	/**
	 * Gets the {@link ServicePanel} that is created
	 * by this definition instance.
	 * 
	 * @return the ServicePanel.
	 */
	public ServicePanel getServicePanel();
	
	/**
	 * Sets the {@link ServicePanel} that was created with this definition instance. 
	 * This is performed automatically in the {@link ServicePanel} constructor.
	 * This method is not part of the public Bo2 API.
	 * 
	 * @param servicePanel 
	 */
	public void setServicePanel(ServicePanel servicePanel);
	
	/**
	 * Gets the disableUnauthorizedButtons.
	 * 
	 * @see #setDisableUnauthorizedButtons(Boolean)
	 * 
	 * @return disableUnauthorizedButtons.
	 */
	public Boolean getDisableUnauthorizedButtons();
	
	/**
	 * [OPTIONAL]
	 * Assigns a new value to the disableUnauthorizedButtons.
	 * This property indicates whether the buttons the user is not authorized
	 * to press will be disabled or will be enabled and a message will be
	 * displayed when they are pressed. This defaults to false.
	 * 
	 * @param disableUnauthorizedButtons
	 *        The disableUnauthorizedButtons to set.
	 */
	public void setDisableUnauthorizedButtons(Boolean disableUnauthorizedButtons);
	
}
