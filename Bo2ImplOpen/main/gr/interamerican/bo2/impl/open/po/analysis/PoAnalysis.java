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
package gr.interamerican.bo2.impl.open.po.analysis;

import gr.interamerican.bo2.arch.PersistentObject;

/**
 * An analysis of an entity that is supposed to be persisted (ideally a
 * {@link PersistentObject}). The Properties defined on the
 * {@link PropertiesOwner} are all the properties apart from key and version
 * properties which are defined on their own.
 */
public interface PoAnalysis extends PropertiesOwner {

	/**
	 * Returns the {@link PoProperty} for the key of this.
	 * 
	 * @return The {@link PoProperty} for the key of this
	 */
	PoProperty getKeyProperty();

	/**
	 * Sets the {@link PoProperty} for the key of this.
	 * 
	 * @param keyProperty
	 *            The {@link PoProperty} for the key of this
	 */
	void setKeyProperty(PoProperty keyProperty);

	/**
	 * Returns the {@link PoProperty} for the version property of this.<br>
	 * A {@link PoProperty} that is used for version control per entity.<br>
	 * This is usually a number or a timestamp that is auto-filled by the orm or
	 * db.
	 * 
	 * @return The {@link PoProperty} for the version property of this
	 */
	PoProperty getVersionProperty();

	/**
	 * Sets the {@link PoProperty} for the version property of this.<br>
	 * A {@link PoProperty} that is used for version control per entity.<br>
	 * This is usually a number or a timestamp that is auto-filled by the orm or
	 * db.
	 * 
	 * @param versionProperty
	 *            The {@link PoProperty} for the version property of this
	 */
	void setVersionProperty(PoProperty versionProperty);
}