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
	 * @param type
	 * @return Fixture instance.
	 */
	<M> M resolveFixture(Class<M> type);
	
	/**
	 * Registers a fixture.
	 * 
	 * @param declarationType
	 * @param fixture
	 */
	<M> void registerFixture(Class<M> declarationType, M fixture);
	
	/**
	 * Registers a fixture.
	 * 
	 * @param declarationType
	 * @param fixtureFactory
	 */
	<M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory);
	
	/**
	 * Clears the fixtures cache of this {@link FixtureResolver} instance.
	 */
	void clearFixturesCache();

}
