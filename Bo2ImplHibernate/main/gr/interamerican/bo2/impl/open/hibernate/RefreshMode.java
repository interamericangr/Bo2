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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.FastGetAndRefresh;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.GetAndRefresh;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.GetFromSession;
import gr.interamerican.bo2.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines in which cases an object persisted by a PersistenceUtility
 * is being refreshed from the database.
 */
public class RefreshMode {
	
	/**
	 * The object is never refreshed. <br/>
	 * 
	 * The object is retrieved from the hibernate Session.
	 * This mode is the most efficient. This is the mode to
	 * use when the only class that performs modifications in
	 * the database is the Hibernate Session. <br/>
	
	 * The object is refreshed only the first time it 
	 * is being stored. <br/>
	 * 
	 * The object is retrieved from the hibernate Session.
	 * Only the first time an object is stored, is the object
	 * read from the database. On all other occasions the object 
	 * is get by the Hibernate Session. <br/>
	 * This is the most appropriate mode when the tables in the
	 * database have columns declared as not nullable with a 
	 * default value. In this case, when an object has null value
	 * in any of not nullable properties, the database row will
	 * have the default values while the instance in the session
	 * will have nulls. <br/>
	 * Using this mode will have the object refreshed from the 
	 * database right after it is stored, so that the default values
	 * in the not nullable columns are reflected in the properties of
	 * the persistent object. <br/>
	 * This mode must be used also if there is a trigger enabled by
	 * an insert statement that is executed when storing the persistent
	 * instance, if the trigger makes any modification in the rows
	 * where the persistent instance is stored.
	
	 * The object is refreshed when it is stored or updated. <br/>
	 * 
	 * The object is retrieved from the hibernate Session by the
	 * <code>read(o)</code> statement. When the object is being 
	 * stored or updated, then it is always being refreshed. <br/>
	 * This mode must be used also if there is a trigger enabled by
	 * an update statement that is executed when updating the persistent
	 * instance, if the trigger makes any modification in the rows
	 * where the persistent instance is stored.
	
	 * The object is refreshed when it is stored or updated. <br/>
	 * 
	 * The object is retrieved from the hibernate Session by the
	 * <code>read(o)</code> statement. When the object is being 
	 * stored or updated, then it is always being refreshed. <br/>
	 * This mode must be used also if there is a trigger enabled by
	 * an update statement that is executed when updating the persistent
	 * instance, if the trigger makes any modification in the rows
	 * where the persistent instance is stored.
	
	 * The object is refreshed on all persistence operations
	 * except from update. <br/>
	 * 
	 * The object is refreshed from the database always, except 
	 * when it is updated. <br/>
	 * This is the most appropriate mode when the reasons that suggest 
	 * 
	 *  tables in the
	 * database have columns declared as not nullable with a 
	 * default value. In this case, when an object has null value
	 * in any of not nullable properties, the database row will
	 * have the default values while the instance in the session
	 * will have nulls. <br/>
	 * Using this mode will have the object refreshed from the 
	 * database right after it is stored, so that the default values
	 * in the not nullable columns are reflected in the properties of
	 * the persistent object. <br/>
	 * This mode must be used also if there is a trigger enabled by
	 * an insert statement that is executed when storing the persistent
	 * instance, if the trigger makes any modification in the rows
	 * where the persistent instance is stored.
	 
	 * The object is refreshed on read operations. <br/>
	 * 
	 * The object is refreshed from the database always, except 
	 * when it is stored or updated. <br/>
	 * This is the most appropriate mode when there are JDBC commands
	 * that update the object, so the state of the object in hibernate
	 * session could be different than in the database.
	 
	 * The object is refreshed always from the database. <br/>
	 * 
	 * Always after any persistence operation (read, store, update) 
	 * the object is refreshed from the database. <br/>
	 * This mode should be used when the rows where the persistent 
	 * object is stored could be updated by another class using
	 * direct JDBC calls or any means other than the hibernate session.
	 */
	
	
	/**
	 * Refresh modes.
	 */
	static Map<Class<?>, RefreshMode> modes = new HashMap<Class<?>, RefreshMode>();
	
	/**
	 * Default refresh mode: ALWAYS.
	 */
	static RefreshMode defaultMode = new RefreshMode
		(FastGetAndRefresh.INSTANCE, FastGetAndRefresh.INSTANCE, GetAndRefresh.INSTANCE);
	
	/**
	 * Sets the refresh mode for a persistent class.
	 * 
	 * @param persistentClass
	 * @param mode
	 */
	public static void setRefreshMode(Class<?> persistentClass, RefreshMode mode) {
		modes.put(persistentClass, mode);		
	}
	
	/**
	 * Gets the refresh mode defined for the specified persistent class.
	 * @param persistentClass
	 * 
	 * @return Returns the refresh mode for the specified class.
	 */
	public static RefreshMode getRefreshMode(Class<?> persistentClass) {
		RefreshMode mode = modes.get(persistentClass);		
		return Utils.notNull(mode, defaultMode);
	}
	
	/**
	 * Gets the defaultMode.
	 *
	 * @return Returns the defaultMode
	 */
	public static RefreshMode getDefaultMode() {
		return defaultMode;
	}

	/**
	 * Assigns a new value to the defaultMode.
	 *
	 * @param defaultMode the defaultMode to set
	 */
	public static void setDefaultMode(RefreshMode defaultMode) {
		RefreshMode.defaultMode = defaultMode;
	}
	
	/**
	 * Get strategy for onStore events.
	 */
	private GetFromSession onStore;
	/**
	 * Get strategy for onUpdate events.
	 */
	private GetFromSession onUpdate;
	/**
	 * Get strategy for onRead events.
	 */
	private GetFromSession onRead;
	
	
	
	
	/**
	 * Creates a new RefreshMode object. 
	 *
	 * @param onStore
	 * @param onUpdate
	 * @param onRead
	 */
	public RefreshMode(GetFromSession onStore, GetFromSession onUpdate, GetFromSession onRead) {
		this.onStore = onStore;
		this.onUpdate = onUpdate;
		this.onRead = onRead;
	}

	/**
	 * Gets the onStore.
	 *
	 * @return Returns the onStore
	 */
	public GetFromSession getOnStore() {
		return onStore;
	}

	/**
	 * Gets the onUpdate.
	 *
	 * @return Returns the onUpdate
	 */
	public GetFromSession getOnUpdate() {
		return onUpdate;
	}

	/**
	 * Gets the onRead.
	 *
	 * @return Returns the onRead
	 */
	public GetFromSession getOnRead() {
		return onRead;
	}

	


}
