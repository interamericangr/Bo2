package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.FixtureResolver;
import gr.interamerican.bo2.creation.ObjectFactory;

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
	final ThreadLocal<Map<Class<?>, Object>> tlCache = new ThreadLocal<Map<Class<?>, Object>>();

	@Override
	@SuppressWarnings("unchecked")
	public <M> M resolveFixture(Class<M> type) {
		if(tlCache.get()==null) { //no fixture has been configured
			return null;
		}
		Object fixture = tlCache.get().get(type);
		if (fixture instanceof ObjectFactory) {
			ObjectFactory fixtureFactory = (ObjectFactory) fixture;
			return fixtureFactory.create(type);
		}
		return (M) tlCache.get().get(type);
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
			tlCache.set(new HashMap<Class<?>, Object>());
		}
		tlCache.get().put(declarationType, fixture);
	}

	public <M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory) {
		if(tlCache.get()==null) {
			tlCache.set(new HashMap<Class<?>, Object>());
		}
		tlCache.get().put(declarationType, fixtureFactory);
	}

}
