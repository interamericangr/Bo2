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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.wicket.factories.ServicePanelFactory;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

/**
 * Facade of {@link ServicePanelFactory} implementations.
 */
public class PanelFactory {
	
	/**
	 * {@link ServicePanelFactory} instance.
	 */
	private static ServicePanelFactory factory = new ServicePanelFactoryImpl();
	
	/**
	 * Creates a {@link ServicePanel}. Delegates to a {@link ServicePanelFactory}
	 * implementation.
	 * 
	 * @param <P>
	 *        ServicePanel type.
	 * @param definition
	 *        ServicePanel definition.
	 * 
	 * @return Returns an instance of the ServicePanel.
	 */
	public static <P extends ServicePanel> P create(ServicePanelDef definition) {
		return factory.createPanel(definition); 
	}
	
	/**
	 * Registers a fixture.
	 * 
	 * @param panelId
	 * @param fixture
	 */
	public static <M extends ServicePanel> void registerFixture(String panelId, M fixture) {
		factory.getFixtureResolver().registerFixture(panelId, fixture);
	}
	
	/**
	 * Resets any fixtures configured programmatically to the underlying
	 * {@link ServicePanelFactory}
	 */
	public static void resetFixtures() {
		factory.getFixtureResolver().clearFixturesCache();
	}
	
}
