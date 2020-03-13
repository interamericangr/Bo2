package gr.interamerican.bo2.creation;

/**
 * {@link FixtureResolver} resolves fixtures configured
 * programmatically.
 * 
 * @see ObjectFactory#registerFixture(Class, Object)
 * @see ObjectFactory#resetFixtures()
 */
public interface FixtureResolver {
	
	/**
	 * Resolves a fixture for a given class. If no fixture is found, null
	 * is returned.
	 *
	 * @param <M> the generic type
	 * @param type the type
	 * @return Fixture instance.
	 */
	<M> M resolveFixture(Class<M> type);
	
	/**
	 * Registers a fixture.
	 *
	 * @param <M> the generic type
	 * @param declarationType the declaration type
	 * @param fixture the fixture
	 */
	<M> void registerFixture(Class<M> declarationType, M fixture);
	
	/**
	 * Registers a fixture.
	 *
	 * @param <M> the generic type
	 * @param declarationType the declaration type
	 * @param fixtureFactory the fixture factory
	 */
	<M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory);
	
	/**
	 * Clears the fixtures cache of this {@link FixtureResolver} instance.
	 */
	void clearFixturesCache();

}
