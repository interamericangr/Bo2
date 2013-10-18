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
package gr.interamerican.bo2.creation.update;

import gr.interamerican.bo2.creation.Bo2Creation;
import gr.interamerican.bo2.creation.exception.ClassCreationException;

import java.util.Arrays;
import java.util.Collection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ClassCompiler} creates a new class and then enhances it
 * in multiple steps by applying the updates done by an array of 
 * {@link AbstractClassUpdater}.
 * 
 */
public class ClassCompiler {
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(ClassCompiler.class);
	
	/**
	 * Updaters that will do the job.
	 */
	Collection<AbstractClassUpdater> updaters;

	/**
	 * Creates a new ClassCreator object. 
	 *
	 * @param updaters
	 */
	public ClassCompiler(AbstractClassUpdater... updaters) {
		this(Arrays.asList(updaters));
	}
	
	/**
	 * Creates a new ClassCreator object. 
	 *
	 * @param updaters
	 */
	public ClassCompiler(Collection<AbstractClassUpdater> updaters) {
		super();
		this.updaters = updaters;
	}
	
	/**
	 * Creates a new class.
	 * 
	 * @param className
	 *        Name of the new class.
	 *        
	 * @return Returns the new class.
	 * 
	 * @throws ClassCreationException
	 */
	public Class<?> createType(String className) 
	throws ClassCreationException {
		ClassPool cp = Bo2Creation.getBo2ClassPool();
		
		Class<?> candidate = possiblyExistingInClassPool(className);
		if(candidate!=null) {
			return candidate;
		}
		
		CtClass newClass = cp.makeClass(className);
		try {
			for (AbstractClassUpdater updater : updaters) {
				updater.updateType(newClass);
			}		
			Class<?> createdType = newClass.toClass();
			return createdType;
		} catch (CannotCompileException e) {
			throw new ClassCreationException(e);
		} catch (NotFoundException e) {
			throw new ClassCreationException(e);
		}
	}
	
	/**
	 * The CtClass with this name may already exist in the ClassPool.
	 * In this case, an attempt is made to get it from the current
	 * ClassLoader. If this fails, it should be safe to compile the 
	 * Class again.
	 * 
	 * @param className
	 * 
	 * @return Returns the Class with name equal to className.
	 * @throws ClassCreationException
	 */
	private Class<?> possiblyExistingInClassPool(String className) throws ClassCreationException {
		ClassPool cp = Bo2Creation.getBo2ClassPool();
		try {
			CtClass ctClass = cp.get(className);
			if(ctClass!=null) {
				Class<?> fromClassLoader = getFromClassLoader(className);
				if(fromClassLoader!=null) {
					return fromClassLoader;
				}
				String msg = className + " found in ClassPool. There is an issue with reloading Bo2 libraries."; //$NON-NLS-1$
				logger.warn(msg);
				return ctClass.toClass();
			}
		} catch (NotFoundException e) {
			return null;
		} catch (CannotCompileException e) {
			throw new ClassCreationException(e);
		}
		return null;
	}
	
	/**
	 * Searches the current ClassLoader for the Class with className.
	 * 
	 * @param className
	 * 
	 * @return Returns the Class with name equal to className. 
	 */
	private Class<?> getFromClassLoader(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException cnfe) {
			return null;
		}
	}

}
