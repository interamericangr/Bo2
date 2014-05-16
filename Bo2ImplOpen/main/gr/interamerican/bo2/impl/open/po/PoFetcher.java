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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that fetches PersistentObjects either from the data store
 * or from a global cache.
 */
public class PoFetcher {

	/**
	 * Caches.
	 */
	static final Map<Class<?>, PoCache<?, ?>> caches =
			new HashMap<Class<?>, PoCache<?,?>>();

	/**
	 * Alternate fetch methods.
	 */
	static final Map<Class<?>, MethodCall> fetchMethods =
			new HashMap<Class<?>, MethodCall>();

	/**
	 * Modifications to apply on fetched objects.
	 */
	static final TypeBasedSelection<Modification<?>> modifications =
			new TypeBasedSelection<Modification<?>>();

	/**
	 * Registers a global cache for a PersistentObject class. <br/>
	 * 
	 * If there is already a cache for the same class, then the old class
	 * will be replaced by a new empty cache.
	 * <br/>
	 * When a cache is registered for a class, then the objects of this
	 * cache must be usable by different threads, because the cache is
	 * static, so the same object could be served to different clients.
	 * Modification of a cached object by one thread, will have side effects
	 * in a multithreaded environment as a web application. <br/>
	 * Persistent classes that require binding of the persistent entity
	 * with an instance of the persister are not suitable for being
	 * cached in static space. Persistent classes that could have parts of
	 * them lazily loaded by an ORM framework fall to this category,
	 * because instances of these classes need a persister attached to them
	 * in order to deal with lazily loaded parts of the object.
	 * 
	 * @param poClass
	 *        Class of {@link PersistentObject}s being stored in the
	 *        cache.
	 * @param maxSize
	 *        Maximum size of cache.
	 * @param <K>
	 *        Type of persistent object key.
	 * @param <P>
	 *        Type of persistent object
	 * 
	 */
	public static <K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	void setCacheSize (Class<P> poClass, int maxSize) {
		newCache(poClass, maxSize);
	}

	/**
	 * Registers a global cache for a PersistentObject class. <br/>
	 * 
	 * If there is already a cache for the same class, then the old class
	 * will be replaced by a new empty cache.
	 * <br/>
	 * When a cache is registered for a class, then the objects of this
	 * cache must be usable by different threads, because the cache is
	 * static, so the same object could be served to different clients.
	 * Modification of a cached object by one thread, will have side effects
	 * in a multithreaded environment as a web application. <br/>
	 * Persistent classes that require binding of the persistent entity
	 * with an instance of the persister are not suitable for being
	 * cached in static space. Persistent classes that could have parts of
	 * them lazily loaded by an ORM framework fall to this category,
	 * because instances of these classes need a persister attached to them
	 * in order to deal with lazily loaded parts of the object.
	 * 
	 * @param poClass
	 *        Class of {@link PersistentObject}s being stored in the
	 *        cache.
	 * @param maxSize
	 *        Maximum size of cache.
	 * @param <K>
	 *        Type of persistent object key.
	 * @param <P>
	 *        Type of persistent object
	 * 
	 * @return Returns the cache.
	 * 
	 */
	static <K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	PoCache<K, P> newCache (Class<P> poClass, int maxSize) {
		@SuppressWarnings("unchecked")
		Modification<P> modification = (Modification<P>)
		modifications.selectionForType(poClass);
		PoCache<K, P> cache = new PoCache<K, P>(poClass, maxSize, modification);
		caches.put(poClass, cache);
		return cache;
	}

	/**
	 * Gets the cache for the specified class.
	 * 
	 * @param poClass
	 *        Class of {@link PersistentObject}s being stored in the
	 *        cache.
	 * 
	 * @param <K>
	 *        Type of persistent object key.
	 * @param <P>
	 *        Type of persistent object
	 * 
	 * @return Returns the cache.
	 * 
	 */
	static synchronized <K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	PoCache<K, P> getCache (Class<P> poClass) {
		@SuppressWarnings("unchecked")
		PoCache<K, P> cache = (PoCache<K, P>) caches.get(poClass);
		if (cache==null) {
			cache = newCache(poClass, 0);
		}
		return cache;
	}


	/**
	 * Defines a method of class Q that will fetch instances of class P.
	 * 
	 * If class P is a child of class Q, and class Q has a method that takes
	 * one argument of type equal with the type of key of class P, then
	 * this method can be used to fetch elements of P. <br/>
	 * 
	 * If Q is PersistentObject<L> and  P is PersistentObject<K> and
	 * P is a child element of Q, then if Q has a method that takes
	 * an element of type K (key type of P), and returns an instance
	 * of P, then this method can be used to fetch elements of P. <br/>
	 * This feature is useful in the case that a cache has been registered
	 * for the type Q. In this case, the elements of P are children of Q
	 * and they could be retrieved from their cached Q parent. <br/>
	 * 
	 * @param <K>
	 * @param <P>
	 * @param <L>
	 * @param <Q>
	 * @param poClass
	 * @param fatherClass
	 * @param methodName
	 * @param argNames
	 */
	@SuppressWarnings("nls")
	public static
	<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>,
	L extends Serializable & Comparable<? super L>, Q extends PersistentObject<L>>
	void setGetChildMethod (Class<P> poClass, Class<Q> fatherClass, String methodName, String... argNames) {
		@SuppressWarnings("unchecked")
		Class<K> keyType = (Class<K>) PoUtils.getKeyType(poClass);
		TypeAnalysis analysis = TypeAnalysis.analyze(keyType);
		Class<?>[] argTypes = new Class<?>[argNames.length];
		for (int i = 0; i < argNames.length; i++) {
			BeanPropertyDefinition<?> bpd = analysis.getFirstPropertyByName(argNames[i]);
			if (bpd==null) {
				String msg = StringUtils.concat(
						"The key type (", keyType.getSimpleName(),
						") of class ", poClass.getSimpleName(),
						" does not have a property with the name ", argNames[i]);
				throw new RuntimeException(msg);
			}
			argTypes[i] = bpd.getType();
		}

		Method getChild =
				ReflectionUtils.getPublicMethod(methodName, fatherClass, argTypes);
		if (getChild==null) {
			String msg = StringUtils.concat	(
					"No method with name ", methodName,
					" and argument of type ", keyType.getSimpleName(),
					" found in class ", fatherClass.getSimpleName());
			throw new RuntimeException(msg);
		}
		if (!getChild.getReturnType().equals(poClass)) {
			String msg = StringUtils.concat	(
					"The return type of method ", methodName,
					" of class ", fatherClass.getSimpleName(),
					" is not ", poClass.getSimpleName());
			throw new RuntimeException(msg);
		}
		MethodCall mc = new MethodCall();
		mc.method = getChild;
		mc.argNames = argNames;
		mc.poClass = fatherClass;
		mc.poKeyClass = PoUtils.getKeyType(fatherClass);
		fetchMethods.put(poClass, mc);
	}


	/**
	 * Puts the specified persistent object in the cache, so that
	 * future calls to <code>get(clazz,key)</code> will return this element
	 * for the specified key.
	 * 
	 * This method is useful in unit testing by putting fixture objects
	 * in the cache and thus imitating the behavior of a system that would
	 * read these fixtures from the database.
	 * 
	 * @param poClass
	 *        Class of persistent objects.
	 * @param po
	 *        Persistent object to put in the cache.
	 * @param <K>
	 * @param <P>
	 */
	public static
	<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	void set(Class<P> poClass, P po){
		PoCache<K, P> cache = getCache(poClass);
		cache.set(po);
	}

	/**
	 * Unloads the given element from the cache.
	 * 
	 * @param poClass
	 *            of the element
	 * @param po
	 *            to remove.
	 */
	public static <K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> void unload(
			Class<P> poClass, P po) {
		PoCache<K, P> cache = getCache(poClass);
		cache.remove(po.getKey());
	}

	/**
	 * Fetches the PersistentObject with the specified key.
	 * 
	 * This method delegates the fetch to a {@link PoCache} associated
	 * with the specified class. If there is no cache already created
	 * for the specified class, then a new cache with size 0 is created.
	 *
	 * @param poClass
	 *        Class of persistent object.
	 * @param key
	 *        Key of the specified persistent object.
	 * 
	 * @param <K>
	 * @param <P>
	 * 
	 * @return Returns the cache for the specified class.
	 */
	@SuppressWarnings("unchecked")
	public static
	<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	P get(Class<P> poClass, K key) {
		MethodCall mc = fetchMethods.get(poClass);
		if (mc!=null) {
			/*
			 * P here is the class of father, not the class of po,
			 * however it is valid to make the substitution cautiously.
			 * There is no other way to make this compile.
			 */
			Class<P> fatherClass = (Class<P>) mc.poClass;
			Class<K> fatherKeyClass = (Class<K>) mc.poKeyClass;
			K fatherKey = Factory.create(fatherKeyClass);
			ReflectionUtils.copyProperties(key, fatherKey);
			P father = get(fatherClass, fatherKey);
			if (father==null) {
				return null;
			}
			Method getChild = mc.method;
			Object[] args = new Object[mc.argNames.length];
			for (int i = 0; i < args.length; i++) {
				args[i] = ReflectionUtils.getProperty(mc.argNames[i], key);
			}

			/*
			 * Now P is the type of po.
			 * no need to re-attach po, because its father is either just read or re-attached.
			 */
			P po = (P) ReflectionUtils.invoke(getChild, father, args);
			return po;
		}

		PoCache<K, P> cache = getCache(poClass);
		return cache.get(key);

	}




	/**
	 * Fetches a persistent object that is referenced by an other persistent
	 * object.
	 * 
	 * If the persistent object type P has a reference to a persistent object
	 * R by having the properties of R's key, then this method can be used
	 * to fetch the R instance that is referenced by the specified P po.
	 * 
	 * @param po
	 *        Persistent object that contains a reference to another
	 *        persistent object's key properties.
	 * @param refClass
	 *        Class of the referenced persistent object.
	 * 
	 * @param <K>
	 * @param <P>
	 * @param <L>
	 * @param <Q>
	 * 
	 * @return Returns the referenced persistent object.
	 */
	@SuppressWarnings({ "unchecked" })
	public static
	<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>,
	L extends Serializable & Comparable<? super L>, Q extends PersistentObject<L>>
	Q getReferenced(P po, Class<Q> refClass) {
		Class<L> refKeyType = (Class<L>) PoUtils.getKeyType(refClass);
		L refKey = Factory.create(refKeyType);
		String[] refKeyProperties = PoUtils.getKeyProperties(refClass);
		ReflectionUtils.copyProperties(po, refKey, refKeyProperties);
		return get(refClass,refKey);
	}


	/**
	 * Registers a modification to be applied to objects of the
	 * specified class. Also checks existing caches that might need to
	 * be configured with this modification. The latter only happens
	 * if a cache does not have a modification and the newly added
	 * modification is selected from the registered modifications with
	 * the cache's persistent object type.
	 * 
	 * @param clazz
	 *        Class.
	 * @param modification
	 *        Modification to apply on objects of the specified type.
	 * 
	 */
	public static void registerModification(Class<?> clazz, Modification<?> modification) {
		modifications.registerSelection(clazz, modification);

		for(Map.Entry<Class<?>, PoCache<?,?>> entry : caches.entrySet()) {
			Class<?> cacheClass = entry.getKey();
			PoCache<?,?> cache = entry.getValue();

			if(cache.modification!=null) {
				continue;
			}

			Modification<?> mod = modifications.selectionForType(cacheClass);
			if(mod==modification) {
				cache.modification = Utils.cast(modification);
			}

		}
	}


	/**
	 * Class that encapsulates the information necessary for a method
	 * call that gets a child.
	 */
	static class MethodCall {
		/**
		 * Class of PersistentObject.
		 */
		Class<?> poClass;
		/**
		 * Class of Key.
		 */
		Class<?> poKeyClass;
		/**
		 * Method that selects the child
		 */
		Method method;
		/**
		 * Names of key properties that are also method arguments.
		 */
		String[] argNames;
	}

	/**
	 * Clears all the caches referenced by the PoFetcher.
	 */
	public static void flushCaches() {
		caches.clear();
	}

}
