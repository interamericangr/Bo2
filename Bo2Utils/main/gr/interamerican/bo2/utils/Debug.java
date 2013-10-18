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
package gr.interamerican.bo2.utils;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This utility class provides useful static methods for debugging.
 */
public class Debug {
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Debug.class);

	/**
	 * Key for active module debug variable.
	 */
	private static final String ACTIVE_MODULE = "Debug.Active_module"; //$NON-NLS-1$
	
	/**
	 * Active modules.
	 */
	private static Stack<String> activeModules = new Stack<String>();
	
	
	/**
	 * HashMap with debug entries.
	 */
	private static HashMap<String, Object> debug = 
		new HashMap<String, Object>();
	
	static {
		setActiveModule("Java"); //$NON-NLS-1$	
		
	}
	
	/**
	 * Indication if the Debug facilities are enabled.
	 */
	private static boolean enabled = true;

	
	/**
	 * Creates a new Debug object. 
	 */
	private Debug() {
		/* empty */
	}
	
	/**
	 * Gets the value of the enabled flag.
	 * 
	 * @return Returns the value of the enabled property.
	 */
	public static boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the value of the enabled flag.
	 * 
	 * @param enabled New flag value.
	 */
	public static void setEnabled(boolean enabled) {
		Debug.enabled = enabled;
	}



	/**
	 * Sets an object to the debug map.
	 * 
	 * @param key Key for the object.
	 * @param object Object to set to the map.
	 */
	public static void set(String key, Object object) {
		if (enabled) {
			debug.put(key, object);
		}
	}
	
	/**
	 * Gets an object from the debug map.
	 * 
	 * @param key Key for the object.
	 * @return Returns the object associated with the key.
	 */
	public static Object get(String key) {
		return debug.get(key);
	}
	
	/**
	 * Gets a String from the debug map.
	 * 
	 * @param key Key for the object.
	 * @return Returns the String returned by the toString() method 
	 *         of the object associated with the key.
	 */
	public static String getString(String key) {		 
		return StringUtils.toString(get(key));
	}
	
	/**
	 * Gets the value of a debug counter.
	 * 
	 * @param key Counter name.
	 * @return Returns the value of the counter.
	 */
	public static int getCounter(String key) {
		Integer counter = Utils.notNull((Integer)get(key), 0);
		return counter;
	}
	
	/**
	 * Sets the value of a counter.
	 * 
	 * @param key Counter key.
	 * @param value New value for the counter.
	 */
	public static void setCounter(String key, int value) {
		if (enabled) {
			logCounter(key, value);	       
			set(key,value);
		}
	}	
	
	/**
	 * Sets the value of a counter to 0.
	 * 
	 * @param key Counter key.
	 */
	public static void resetCounter(String key) {
		if (enabled) {				       
			setCounter(key,0);
		}
	}	
	
	/**
	 * Increases by 1 the value of the counter.
	 * 
	 * @param key Counter name.
	 * @return Returns the new value of the counter.
	 */
	public static int increaseCounter(String key) {
		int value = getCounter(key)+1;
		setCounter(key, value);
		return value;
	}
	
	/**
	 * Sets an object as being the active module.
	 * 
	 * @param object Active module.
	 */
	public static void setActiveModule(Object object) {
		if (enabled) {
			String module = activeModuleName(object);
			String previous = peekActiveModule(); 
			activeModules.push(module);
			set(ACTIVE_MODULE,module);
			logActiveModuleChange(previous, module);	
		}
	}
	
	/**
	 * Exception safe peek.
	 * 
	 * @return Returns the active module.
	 */
	private static String peekActiveModule() {
		try {
			return activeModules.peek();
		} catch (EmptyStackException e) {
			return null;
		}
	}
	
	/**
	 * Exception safe pop.
	 * 
	 * @return Returns the active module.
	 */
	private static String popActiveModule() {
		try {
			return activeModules.pop();
		} catch (EmptyStackException e) {
			return null;
		}
	}
	
	/**
	 * Creates a String for the active module.
	 * 
	 * @param activeModule
	 * @return Returns a String for the active module.
	 */
	static String activeModuleName(Object activeModule) {
		return (StringUtils.toString(activeModule));
	}
	
	/**
	 * Resets the active module to its previous value.
	 */
	public static void resetActiveModule() {
		if (enabled) {
			String previous = popActiveModule();
			String current = peekActiveModule();
			logActiveModuleChange(previous, current);
			set(ACTIVE_MODULE,current);	
		}
	}
	
	/**
	 * Logs the active module changes.
	 * 
	 * @param previous Previous module.
	 * @param current New module.
	 */
	@SuppressWarnings("nls")
	private static void logActiveModuleChange(String previous, String current) {
		if (logger.isDebugEnabled()) {
			String msg = "\nActive module change from ["
			       + previous + "]\n to [" + current + "]";
			logger.debug(msg);
		}
	}
	
	/**
	 * Logs the value of a counter.
	 * 
	 * @param name Counter name.
	 * @param value Couner value.
	 */
	@SuppressWarnings("nls")
	private static void logCounter(String name, int value) {
		if (logger.isDebugEnabled()) {
			String msg = "\nCounter " + name + "=" + value;
			logger.debug(msg);
		}
	}
	
	/**
	 * Gets the name of the active module.
	 * 
	 * @return Returns the name of the active module.
	 */
	public static String getActiveModule() {
		return getString(ACTIVE_MODULE);
	}
	
	/**
	 * Prints a debug message with a given target logger. 
	 * 
	 * @param target 
	 * @param msg
	 */
	public static void debug(Logger target, String msg) {
		if(target.isDebugEnabled()) {
			target.debug(msg);
		}
	}
	
}
