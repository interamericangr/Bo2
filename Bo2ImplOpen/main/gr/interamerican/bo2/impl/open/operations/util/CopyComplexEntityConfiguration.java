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
package gr.interamerican.bo2.impl.open.operations.util;


import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.beans.ResultKey;
import gr.interamerican.bo2.impl.open.operations.CopyComplexEntityOperation;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.attributes.Input;

import java.util.List;

/**
 * This is the Configuration for a specific entity to work with
 * {@link CopyComplexEntityOperation}.<br>
 * The entities defined on {@link #getBefore()} are copied first, then the root entity will be copied and finally the entities on
 * {@link #getAfter()}
 * 
 * @param <I>
 *            Input of the Operation
 * @param <RPO>
 *            Type of Root Entity
 */
public interface CopyComplexEntityConfiguration<I, RPO extends PersistentObject<? extends I>> {

	/**
	 * Sets the Root Entity to copy.
	 * 
	 * @param poClz
	 *            Root Entity Class
	 */
	void setRootEntity(Class<RPO> poClz);

	/**
	 * Returns the Root Entity to copy.
	 * 
	 * @return Root Entity Class
	 */
	Class<RPO> getRootEntity();

	/**
	 * Sets the Custom Root Modifications.<br>
	 * The modifications will be run just before persisting the entity on the
	 * target environment.<br>
	 * If for some reason you want to skip an entry from being copied - the
	 * modification has to return null. These modifications can implement the
	 * {@link Input} or {@link ResultKey} if the corresponding bean of the
	 * operation is required for some reason on the modification as well.
	 * 
	 * @param rootModifications
	 *            Custom Root Modifications
	 */
	void setRootModifications(List<Modification<RPO>> rootModifications);

	/**
	 * Returns the Custom Root Modifications
	 * 
	 * @return Custom Root Modifications
	 */
	List<Modification<RPO>> getRootModifications();

	/**
	 * Sets the {@link CopyMode} of the Root entity.
	 * 
	 * @param rootCopyMode
	 *            The Root Copy Mode
	 */
	void setRootCopyMode(CopyMode rootCopyMode);

	/**
	 * Returns the {@link CopyMode} of Root entity.
	 * 
	 * @return The Root Copy Mode
	 */
	CopyMode getRootCopyMode();

	/**
	 * {@link CopyComplexEntityChildConfiguration} for entities to be copied
	 * before the root entity is.
	 * 
	 * @return Defines Entities to be copied before the root entity
	 */
	CopyComplexEntityChildConfiguration<I> getBefore();

	/**
	 * Sets the {@link CopyComplexEntityChildConfiguration} for entities to be
	 * copied before the root entity is.
	 * 
	 * @param before
	 *            Defines Entities to be copied before the root entity
	 */
	void setBefore(CopyComplexEntityChildConfiguration<I> before);

	/**
	 * {@link CopyComplexEntityChildConfiguration} for entities to be copied
	 * after the root entity is.
	 * 
	 * @return Defines Entities to be copied after the root entity
	 */
	CopyComplexEntityChildConfiguration<I> getAfter();

	/**
	 * Sets the {@link CopyComplexEntityChildConfiguration} for entities to be
	 * copied after the root entity is.
	 * 
	 * @param after
	 *            Defines Entities to be copied after the root entity
	 */
	void setAfter(CopyComplexEntityChildConfiguration<I> after);
}