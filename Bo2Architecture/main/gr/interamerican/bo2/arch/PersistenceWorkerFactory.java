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
package gr.interamerican.bo2.arch;




/**
 * Factory of {@link PersistenceWorker} objects. 
 * 
 * A PersistenceWorkerFactory provides a convenient way to
 * create a PersistenceWorker for a type of PersistentObject.
 */
public interface PersistenceWorkerFactory {

	/**
	 * Creates a {@link PersistenceWorker} for a type of 
	 * {@link PersistentObject}. <br>
	 * 
	 * @param <M> Type of PersistentObject for which the the
	 *            PersistenceWorker will be created.
	 * @param type The PersistentObject class.
	 * 
	 * @return Returns an PersistenceWorker for the specified type
	 *         of PersistentObject.
	 */
	public <M extends PersistentObject<?>> PersistenceWorker<M> 
	createPw(Class<M> type);
	
	/**
	 * Gets a new {@link DetachStrategy} instance of the DetachStrategy type associated with the persistence
	 * worker of the specified type of {@link PersistentObject}.
	 *
	 * @param <M>        Type of {@link PersistentObject}.	 
	 *            
	 * @param type        Class of {@link PersistentObject}.
	 * @return Returns the {@link DetachStrategy} for the specified type
	 *         of persistent object.
	 */
	public <M extends PersistentObject<?>> DetachStrategy 
	getDetachStrategy(Class<M> type);

	/**
	 * Registers a fixture that the underlying {@link PersistenceWorkerFactory} will use
	 * when the application requires the creation of a {@link PersistenceWorker}
	 * for the supplied <code>declarationType</code>
	 * <br>
	 * The normal process for {@link PersistenceWorker} creation will not be used if a
	 * fixture has been set. 
	 * <br>
	 * This facility is meant to allow developers to specify mock instances
	 * to be created for a declarationType in certain unit testing scenarions
	 * where the actual implementation is not available in the classpath.
	 * <br>
	 * The fixtures concern only invocations to {@link #createPw(Class)}.
	 * 
	 * @param declarationType
	 *         Declaration class
	 * @param fixture
	 *         Instance to be returned upon a request for a declarationType
	 *        {@link PersistenceWorker} creation
	 */
	public <M extends PersistentObject<?>, T extends PersistenceWorker<M>>  void 
	registerPwFixture(Class<M> declarationType, T fixture);
	
	/**
	 * Resets any fixtures configured programmatically to the underlying
	 * {@link PersistenceWorkerFactory} using {@link #registerPwFixture(Class, PersistenceWorker)}
	 */
	public void resetPwFixtures();

}
