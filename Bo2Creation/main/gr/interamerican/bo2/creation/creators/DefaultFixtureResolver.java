package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.FixtureResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link FixtureResolver}
 * <br/>
 * This implementation relies on a ThreadLocal Map in order to support
 * multi-threaded execution of unit tests. The tests may configure
 * fixtures before they execute and MUST reset the fixtures afterwards.
 * <br/>
 * Resetting is the only way to remove the thread local value.
 */
public class DefaultFixtureResolver implements FixtureResolver {

	/**
	 * tlCache
	 */
	final ThreadLocal<Map<Object, Object>> tlCache = new ThreadLocal<Map<Object, Object>>();

	@Override
	@SuppressWarnings("unchecked")
	public <M> M resolveFixture(Object typeOrName) {
		if(tlCache.get()==null) { //no fixture has been configured
			return null;
		}
		return (M) tlCache.get().get(typeOrName);
	}
	
	@Override
	public void clearFixturesCache() {
		if(tlCache.get()==null) {
			return;
		}
		tlCache.get().clear();
		tlCache.remove();
		
	}

	@Override
	public <M> void registerFixture(Class<M> declarationType, M fixture) {
		if(tlCache.get()==null) {
			tlCache.set(new HashMap<Object, Object>());
		}
		tlCache.get().put(declarationType, fixture);
	}

	public void registerFixture(String declarationTypeName, Object fixture) {
		if(tlCache.get()==null) {
			tlCache.set(new HashMap<Object, Object>());
		}
		tlCache.get().put(declarationTypeName, fixture);
	}

}
