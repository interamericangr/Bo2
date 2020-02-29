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
package gr.interamerican.bo2.impl.open.creation;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistenceWorkerFactory;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.impl.open.utils.Messages;
import gr.interamerican.bo2.impl.open.utils.Util;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link PersistenceWorkerFactory}.
 * 
 * This implementation searches to find the appropriate implementation type of
 * {@link PersistenceWorker} for each {@link PersistentObject} type in a
 * mappings file.
 */
public class PersistenceWorkerFactoryImpl implements PersistenceWorkerFactory {

	/**
	 * message key for invalid type mapping.
	 */
	private static final String NO_IMPLEMENTATION_TYPE = "Factory.NO_IMPLEMENTATION_TYPE"; //$NON-NLS-1$

	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PersistenceWorkerFactoryImpl.class);

	/**
	 * Maps classes with their names.
	 **/
	protected Map<Class<? extends PersistentObject<?>>, Class<? extends PersistenceWorker<?>>> pwClasses = new HashMap<Class<? extends PersistentObject<?>>, Class<? extends PersistenceWorker<?>>>();

	/**
	 * Maps classes with their names.
	 */
	private HashMap<String, String> pwMappings = new HashMap<String, String>();

	/**
	 * Maps class with the registered fixtures.
	 */
	private ThreadLocal<Map<Class<? extends PersistentObject<?>>, PersistenceWorker<?>>> fixtureMappings = new ThreadLocal<Map<Class<? extends PersistentObject<?>>, PersistenceWorker<?>>>();

	/**
	 * Creates a new PersistenceWorkerFactory object.
	 * 
	 * The factory is initialized with the type associations that are retrieved
	 * by a mappings file. The mappings file contains a list of file paths to
	 * mapping properties file. Each mapping properties file associates
	 * {@link PersistentObject} types with the corresponding implementation type
	 * of {@link PersistenceWorker}.
	 * 
	 * @param properties
	 *            Properties file that contains the property
	 *            <code>pwMappingsFile</code> which shows to the path of the
	 *            file that contains the list of mappings files.
	 */
	public PersistenceWorkerFactoryImpl(Properties properties) {
		String mappingsFilePath = properties.getProperty("pwMappingsFile"); //$NON-NLS-1$
		if (!StringUtils.isNullOrBlank(mappingsFilePath)) {
			try {
				initialize(mappingsFilePath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public <M extends PersistentObject<?>> PersistenceWorker<M> createPw(Class<M> poClass) {
		PersistenceWorker<M> pw = createMappedPw(poClass);
		if (pw == null) {
			noImplementation(poClass);
		}
		return pw;
	}

	/**
	 * Creates the PersistenceWorker for poClass according to the mappings
	 * specified.
	 * 
	 * @param <M>
	 *            Type of persistent object.
	 * @param poClass
	 *            Class of persistent object.
	 * 
	 * @return Returns a persistence worker for the specified class, according
	 *         to the mappings. If there is no mapping specified for this type
	 *         of persistent object, returns null.
	 */
	@SuppressWarnings("unchecked")
	protected <M extends PersistentObject<?>> PersistenceWorker<M> createMappedPw(Class<M> poClass) {
		if (fixtureMappings.get() != null) {
			PersistenceWorker<M> pw = (PersistenceWorker<M>) fixtureMappings.get().get(poClass);
			if (pw != null) {
				return pw;
			}
		}
		Class<? extends PersistenceWorker<?>> pwClass = findMappedClass(poClass);
		if (pwClass != null) {
			return (PersistenceWorker<M>) newInstance(poClass, pwClass);
		}
		return null;
	}

	/**
	 * The method will throw a RuntimeException, signing that there is no
	 * implementation type for the PersistenceWorker of the specified persistent
	 * object class.
	 * 
	 * @param poClass
	 *            Class of persistent object.
	 * 
	 */
	protected void noImplementation(Class<?> poClass) {
		String pwName = PersistenceWorker.class.getName() + StringUtils.generics(poClass.getName());
		throw Exceptions.runtime(NO_IMPLEMENTATION_TYPE, pwName);
	}

	/**
	 * Gets the PersistenceWorker class for the persistent objects of poClass
	 * according to the mappings specified.
	 *
	 * @param <M>
	 *            the generic type
	 * @param poClass
	 *            Class of persistent object.
	 * @return Returns a persistence worker class for the specified class,
	 *         according to the mappings. If there is no mapping specified for
	 *         this type of persistent object, returns null.
	 */
	protected <M extends PersistentObject<?>> Class<? extends PersistenceWorker<M>> findMappedClass(Class<M> poClass) {
		Class<? extends PersistenceWorker<?>> pwClass = pwClasses.get(poClass);
		if (pwClass != null) {
			@SuppressWarnings("unchecked")
			Class<? extends PersistenceWorker<M>> clazz = (Class<? extends PersistenceWorker<M>>) pwClass;
			return clazz;
		}
		String poName = poClass.getName();
		String pwClassName = pwMappings.get(poName);
		if (pwClassName != null) {
			pwClass = loadPwClass(pwClassName);
			@SuppressWarnings("unchecked")
			Class<? extends PersistenceWorker<M>> clazz = (Class<? extends PersistenceWorker<M>>) pwClass;
			associate(poClass, clazz);
			return clazz;
		}
		return null;
	}

	/**
	 * Registers the association between the specified persistent object class
	 * and the specified persistence worker class.
	 *
	 * @param poClass
	 *            Persistent object class.
	 * @param pwClass
	 *            Persistence worker class.
	 */
	protected synchronized void associate(Class<? extends PersistentObject<?>> poClass,
			Class<? extends PersistenceWorker<?>> pwClass) {
		if (!pwClasses.containsKey(poClass)) {
			pwClasses.put(poClass, pwClass);

			if (logger.isInfoEnabled()) {
				String msg = "Associated PersistentObject class " //$NON-NLS-1$
						+ poClass.getName() + " with PersistenceWorker class " //$NON-NLS-1$
						+ pwClass.getName();
				logger.info(msg);
			}

		}
	}

	/**
	 * Creates a new instance of the specified PersistenceWorker class for the
	 * specified PersistentObject class.
	 *
	 * @param poClass
	 *            Persistent object class.
	 * @param pwClass
	 *            Persistence worker class.
	 * 
	 * @return Returns the persistence worker.
	 */
	protected PersistenceWorker<?> newInstance(Class<? extends PersistentObject<?>> poClass,
			Class<? extends PersistenceWorker<?>> pwClass) {
		Constructor<?> classArgConstructor = ReflectionUtils.getConstructor(pwClass, Class.class);
		if (classArgConstructor != null) {
			return (PersistenceWorker<?>) ReflectionUtils.newInstance(classArgConstructor, poClass);
		}
		return ReflectionUtils.newInstance(pwClass);
	}

	/**
	 * Loads and returns the PersistenceWorker class that has been mapped.
	 * 
	 * @param className
	 *            Name of PersistenceWorker class.
	 * @return Returns the class of PersistenceWorker.
	 */
	protected Class<? extends PersistenceWorker<?>> loadPwClass(String className) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends PersistenceWorker<?>> pwClass = (Class<? extends PersistenceWorker<?>>) Class
					.forName(className);
			return pwClass;
		} catch (ClassNotFoundException cnfe) {
			String message = Messages.getString(Messages.CLASS_NOT_FOUND);
			throw new RuntimeException(message, cnfe);
		} catch (ClassCastException cce) {
			String message = Messages.getString(Messages.INVALID_TYPE_MAPPING);
			throw new RuntimeException(message, cce);
		}
	}

	/**
	 * Initializes the Factory with the type associations that are retrieved
	 * from a mappings file.
	 * 
	 * The mappings file contains a list of paths to properties files that can
	 * be found inside the local classpath. Each properties file contains
	 * associations between a declaration type and an implementation type.
	 * Declaration type is usually an interface and implementation type is the
	 * concrete class that implements it. <br>
	 * Implementation type can also be a concrete class. In this case, the
	 * corresponding implementation class has to be the same class, or a
	 * subclass of the declaration class. <br>
	 * Different properties files can provide different mapping for the same
	 * type. In this case, the last mapping will overwrite any other. This
	 * implies that the order in which the properties files are placed in the
	 * mappings files catalog, is important.
	 *
	 * @param mappingsFilePath
	 *            Path to the file that contains the paths to the mapping files.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void initialize(String mappingsFilePath) throws IOException {
		String[] paths = Util.readFile(mappingsFilePath);

		for (int i = 0; i < paths.length; i++) {
			String path = paths[i].trim();
			if (path.length() != 0) {
				Properties p = loadPropertiesIfAvailable(path);
				CollectionUtils.putPropertiesToMap(p, pwMappings);
			}
		}
	}

	/**
	 * Loads a properties file resource, if it is available. If unavailable, a
	 * warning is printed. This is not normally acceptable and should be
	 * considered a fatal error when in production.
	 * 
	 * @param path
	 *            Resource path.
	 * 
	 * @return loaded properties.
	 */
	@SuppressWarnings("nls")
	private Properties loadPropertiesIfAvailable(String path) {
		try {
			Properties p = CollectionUtils.readProperties(path);
			return p;
		} catch (RuntimeException rtex) {
			if (ExceptionUtils.isCausedBy(rtex, IOException.class)) {
				String msg = StringUtils.concat("Non existant pw mappings file: ", path,
						". This is FATAL in production deployments and should be investigated.");
				logger.warn(msg);
			} else {
				throw rtex;
			}
		}
		return new Properties();
	}

	@Override
	public <M extends PersistentObject<?>> DetachStrategy getDetachStrategy(Class<M> type) {
		PersistenceWorker<M> pw = createPw(type);
		return pw.getDetachStrategy();
	}

	@Override
	public <M extends PersistentObject<?>, T extends PersistenceWorker<M>> void registerPwFixture(
			Class<M> declarationType, T fixture) {
		if (nullFixtureMappings()) {
			fixtureMappings.set(new HashMap<Class<? extends PersistentObject<?>>, PersistenceWorker<?>>());
		}
		fixtureMappings.get().put(declarationType, fixture);
	}

	@Override
	public void resetPwFixtures() {
		if (!nullFixtureMappings()) {
			fixtureMappings.get().clear();
		}
	}

	/**
	 * Checks if {@link #fixtureMappings} is null
	 * 
	 * @return <code>true</code> if {@link #fixtureMappings} is null
	 */
	boolean nullFixtureMappings() {
		return fixtureMappings.get() == null ? true : false;
	}
}