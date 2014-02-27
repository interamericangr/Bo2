package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.FixtureResolver;

/**
 * Mock implementation of {@link FixtureResolver} used internally when no
 * {@link FixtureResolver} is configured.
 * <br/>
 * This implementation never returns a fixture.
 */
public class MockFixtureResolver implements FixtureResolver {

	@Override
	public <M> M resolveFixture(Object typeOrName) {
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

	public void registerFixture(String declarationTypeName, Object fixture) {
		/* empty */
	}

}
