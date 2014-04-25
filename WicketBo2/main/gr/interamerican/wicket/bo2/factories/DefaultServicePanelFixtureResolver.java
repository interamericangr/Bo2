package gr.interamerican.wicket.bo2.factories;

import java.util.HashMap;
import java.util.Map;

import gr.interamerican.wicket.factories.ServicePanelFixtureResolver;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

/**
 * ServicePanelFixtureResolver implementation
 */
public class DefaultServicePanelFixtureResolver implements ServicePanelFixtureResolver {
	
	/**
	 * tlCache
	 */
	final ThreadLocal<Map<String, ServicePanel>> tlCache = new ThreadLocal<Map<String, ServicePanel>>();

	@SuppressWarnings("unchecked")
	public <M extends ServicePanel> M resolveFixture(String panelId) {
		if(tlCache.get()==null) { //no fixture has been configured
			return null;
		}
		return (M) tlCache.get().get(panelId);
	}

	public <M extends ServicePanel> void registerFixture(String panelId, M fixture) {
		if(tlCache.get()==null) {
			tlCache.set(new HashMap<String, ServicePanel>());
		}
		tlCache.get().put(panelId, fixture);
	}

	public void clearFixturesCache() {
		if(tlCache.get()==null) {
			return;
		}
		tlCache.get().clear();
		tlCache.remove();
	}

}
