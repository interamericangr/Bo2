package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.FixtureResolver;
import gr.interamerican.bo2.creation.ObjectFactory;

/**
 * Mock implementation of {@link FixtureResolver} used internally when no
 * {@link FixtureResolver} is configured.
 * <br/>
 * This implementation never returns a fixture.
 */
public class MockFixtureResolver implements FixtureResolver {

	@Override
	public <M> M resolveFixture(Class<M> type) {
		return null;
	}

	@Override
	public void clearFixturesCache() {
		/* empty */
	}

	@Override
	public <M> void registerFixture(Class<M> declarationType, M fixture) {
		/* empty */
	}

	public <M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory) {
		/* empty */
	}

}
