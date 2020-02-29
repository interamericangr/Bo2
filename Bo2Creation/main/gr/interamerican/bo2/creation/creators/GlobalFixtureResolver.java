package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.FixtureResolver;
import gr.interamerican.bo2.creation.ObjectFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Global implementation of {@link FixtureResolver} <br>
 * It uses a final Map (Concurrent) that will be available on all Threads in the same JVM.
 */
public class GlobalFixtureResolver implements FixtureResolver {

	/** tlCache. */
	final ConcurrentHashMap<Class<?>, Object> cache = new ConcurrentHashMap<Class<?>, Object>();

	@Override
	@SuppressWarnings("unchecked")
	public <M> M resolveFixture(Class<M> type) {
		Object fixture = cache.get(type);
		if (fixture instanceof ObjectFactory) {
			ObjectFactory fixtureFactory = (ObjectFactory) fixture;
			return fixtureFactory.create(type);
		}
		return (M) cache.get(type);
	}

	@Override
	public void clearFixturesCache() {
		cache.clear();
	}

	@Override
	public <M> void registerFixture(Class<M> declarationType, M fixture) {
		cache.putIfAbsent(declarationType, fixture);
	}

	@Override
	public <M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory) {
		cache.putIfAbsent(declarationType, fixtureFactory);
	}
}