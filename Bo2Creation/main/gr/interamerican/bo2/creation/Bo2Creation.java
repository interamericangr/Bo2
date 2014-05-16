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

import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Utility class for the Bo2Creation module.
 */
public class Bo2Creation {
	
	/**
	 * {@link ClassPool}.
	 */
	static ClassPool BO2_CLASSPOOL;
	
	/**
	 * Classpaths appended to {@link #BO2_CLASSPOOL}
	 */
	static final Map<Class<?>, ClassClassPath> APPENDED_CLASSPATHS = new HashMap<Class<?>, ClassClassPath>();
	
	/**
	 * Lazy initializer
	 * @return Bo2 Classpool.
	 */
	static ClassPool bo2ClassPool() {
		if(BO2_CLASSPOOL != null) {
			return BO2_CLASSPOOL;
		}
		synchronized(Bo2Creation.class) {
			if(BO2_CLASSPOOL != null) {
				return BO2_CLASSPOOL;
			}
			BO2_CLASSPOOL = new ClassPool();
			BO2_CLASSPOOL.appendSystemPath();
			return BO2_CLASSPOOL;
		}
	}
	
	/**
	 * Gets a CtClass by name
	 * @param className
	 * @return a CtClass by name
	 * @throws NotFoundException
	 */
	public static CtClass get(String className) throws NotFoundException {
		return bo2ClassPool().get(className);
	}
	
	/**
	 * Creates a CtClass with the specified name and returns it.
	 * @param classname
	 * @return created CtClass
	 */
	public static CtClass makeClass(String classname) {
		return bo2ClassPool().makeClass(classname);
	}
	
	/**
	 * Appends a ClassClassPath to the bo2 ClassPool
	 * @param clazz
	 */
	public static void appendClassPath(Class<?> clazz) {
		if(!APPENDED_CLASSPATHS.containsKey(clazz)) {
			ClassClassPath cp = new ClassClassPath(clazz);
			APPENDED_CLASSPATHS.put(clazz, cp);
			bo2ClassPool().appendClassPath(cp);
		}
	}
	
	/**
	 * Flush the bo2 classpool.
	 */
	public static void flushClassPool() {
		for(ClassClassPath cp : APPENDED_CLASSPATHS.values()) {
			bo2ClassPool().removeClassPath(cp);
		}
		APPENDED_CLASSPATHS.clear();
		BO2_CLASSPOOL = null;
	}

}
