/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.creation;

/**
 * Object Factory. <br/>
 * 
 * This factory supports the distinction of declaration and implementation types.
 * The declaration type can be an interface, an abstract class or even a concrete
 * class. The implementation type is always a concrete class. The implementation
 * type does not need to pre-exist; it could be created dynamically by the factory. 
 * <br/>
 * The methods of this factory are declared as not throwing exception. This is so
 * in order to make the code that uses the factory more readable. Any exception 
 * thrown by the method's of the ObjectFactory should be wrapped inside a 
 * RuntimeException.
 */
public interface ObjectFactory {

	/**
	 * Creates an object of the specified type.
	 * 
	 * @param type 
	 *        Declaration type of the object to be created.
	 *        The declaration type, could be an interface, abstract
	 *        or concrete class.
	 * @param <M> 
	 *        Type of object to be created.
	 * 
	 * @return Returns an object of the specified type.
	 *         The implementation type of the returned object is
	 *         the type returned by <code>getImplementationType(Class<?> declarationType)</code>.
	 */
	public <M> M create(Class<M> type);

	/**
	 * Creates an object for the specified declaration type name.
	 * 
	 * @param declarationTypeName
	 *            the declaration type name
	 * @return an object of the corresponding implementation type
	 */
	public Object create(String declarationTypeName);

	/**
	 * Gets the class that is assignable from the specified class and
	 * is used by this factory for the instantiation of objects of the
	 * specified type. 
	 *
	 * If the type is existing (i.e. it has been created in the past) it is
	 * found in the Factory internal store.
	 * 
	 * If it is not existing, the Factory first checks the available type
	 * mappings to get an implementation name. If no implementation name is
	 * found, the Factory uses a NameResolver in order to get the class name of
	 * the default implementation (i.e. gr.intermarican...def...Foo -->
	 * gr.interamerican...impl...FooImpl).
	 * 
	 * The Factory goes on to load that class, unless (a) it is abstract or (b)
	 * it is an interface. In both of these cases the Factory uses Javassist
	 * first in order to convert it to a concrete class which it then loads.
	 * 
	 * @param declarationType
	 *            Type used as declaration type. Usually this is an interface.
	 * 
	 * @return Returns the type implementing the type defined by the parameter
	 *         <code>declarationType</code> in this deployment.
	 */
	public Class<?> getImplementationType(Class<?> declarationType);

	/**
	 * Returns the implementation type that corresponds to a declaration
	 * type name. <br/>
	 * 
	 * The work is delegated to getImplementationType(Class<?> type) as soon
	 * as the declaration type is resolved from the declaration type name.
	 * 
	 * @param declarationTypeName 
	 *        Name of the declaration class
	 *         
	 * @return returns the corresponding implementation type.
	 */
	public Class<?> getImplementationType(String declarationTypeName);

	/**
	 * Finds the declaration type name for the specified implementation type.
	 * 
	 * If the type was created in the past, then the result is found in the
	 * Factory internal store. If it was not, the result is null.
	 * 
	 * @param implementationType
	 *            Specified implementation type
	 * @return the corresponding declaration type name
	 */
	public String getDeclarationTypeName(Class<?> implementationType);
	
	/**
	 * Finds the declarationType that corresponds to an implementation type.
	 * 
	 * @param implementationType the of the implementation class.
	 * @return the corresponding declaration class.
	 */
	public Class<?> getDeclarationType(Class<?> implementationType);
	
	/**
	 * Defines that the specified class will be used as declaration and implementation
	 * type.
	 * 
	 * This method is provided as a facility, that lets the object factory be aware of
	 * concrete classes that do not implement an interface.
	 * 
	 * @param declaration
	 *        Concrete class that will be used as declaration and implementation.
	 */
	void registerImplementationAsDeclaration(Class<?> declaration);
	
	/**
	 * Registers a fixture that this {@link ObjectFactory} will use
	 * when the application requires the creation of an object instance
	 * for the supplied <code>declarationType</code>
	 * <br/>
	 * The normal process for object creation will not be used if a
	 * fixture has been set. 
	 * <br/>
	 * This facility is meant to allow developers to specify mock instances
	 * to be created for a declarationType in certain unit testing scenarios
	 * where the actual implementation is not available in the classpath.
	 * <br/>
	 * The fixtures only affect calls to the {@link #create(Class)} method
	 * of the {@link ObjectFactory}. 
	 *
	 * @param <M> 
	 *         Type of fixture.
	 * @param declarationType
	 *         Declaration class
	 * @param fixture
	 *         Instance to be returned upon a request for a declarationType
	 *         object creation
	 */
	<M> void registerFixture(Class<M> declarationType, M fixture);
	
	/**
	 * Registers a fixture that this {@link ObjectFactory} will use
	 * when the application requires the creation of an object instance
	 * for the supplied <code>declarationType</code>
	 * <br/>
	 * The normal process for object creation will not be used if a
	 * fixture has been set. 
	 * <br/>
	 * This facility is meant to allow developers to specify ObjectFactory 
	 * instances that will be used for the instantiation of a declarationType 
	 * in certain unit testing scenarios where the actual implementation 
	 * is not available in the classpath.
	 * <br/>
	 * The fixtures only affect calls to the {@link #create(Class)} method
	 * of this {@link ObjectFactory}. 
	 *
	 * @param <M> 
	 *         Type of fixture.
	 * @param declarationType
	 *         Declaration class
	 * @param fixtureFactory
	 *         ObjectFactory that will actually perform the instantiation.
	 */
	<M> void registerFixture(Class<M> declarationType, ObjectFactory fixtureFactory);
	
	/**
	 * Resets any fixtures configured programmatically using 
	 * {@link #registerFixture(Class, Object)}
	 */
	void resetFixtures();

}
