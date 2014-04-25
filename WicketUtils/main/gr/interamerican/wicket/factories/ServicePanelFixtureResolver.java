package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;


/**
 * Resolves fixtures for the creation of ServicePanels by a {@link ServicePanelFactory}.
 */
public interface ServicePanelFixtureResolver {
	
	/**
	 * Resolves a fixture for a given class. If no fixture is found, null
	 * is returned.
	 * 
	 * @param panelId
	 * @return Fixture instance.
	 */
	<M extends ServicePanel> M resolveFixture(String panelId);
	
	/**
	 * Registers a fixture.
	 * 
	 * @param panelId
	 * @param fixture
	 */
	<M extends ServicePanel> void registerFixture(String panelId, M fixture);
	
	/**
	 * Clears the fixtures cache of this {@link ServicePanelFixtureResolver} instance.
	 */
	void clearFixturesCache();

}
