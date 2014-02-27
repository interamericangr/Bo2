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
	 * @param typeOrName
	 * @return Fixture instance.
	 */
	<M> M resolveFixture(Object typeOrName);
	
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
	 * @param declarationTypeName
	 * @param fixture
	 */
	void registerFixture(String declarationTypeName, Object fixture);
	
	/**
	 * Clears the fixtures cache of this {@link FixtureResolver} instance.
	 */
	void clearFixturesCache();

}
