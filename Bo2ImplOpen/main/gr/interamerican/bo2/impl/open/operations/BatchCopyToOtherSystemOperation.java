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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copies a list of {@link PersistentObject}s from one system
 * to another, given their {@link Key}s.
 * 
 * @param <P> 
 *        Type of PersistentObject
 * @param <K> 
 *        Type of Key
 */
public class BatchCopyToOtherSystemOperation
<P extends PersistentObject<K>, 
K extends Serializable & Comparable<? super K>> 
extends CopyToOtherSystemOperation<P, K> {
	
	/**
	 * Keys to copy.
	 */
	Set<K> keys;

	/**
	 * Creates a new BatchCopyToOtherSystemOperation object. 
	 *
	 * @param poClass
	 * @param fromManager
	 * @param toManager
	 */
	public BatchCopyToOtherSystemOperation(Class<P> poClass, String fromManager, String toManager) {
		super(poClass, fromManager, toManager);
	}
	
	/**
	 * Creates a new BatchCopyToOtherSystemOperation object. 
	 *
	 * @param poClass
	 * @param fromManager
	 * @param toManager
	 * @param prepareForCopy 
	 */
	public BatchCopyToOtherSystemOperation(Class<P> poClass, String fromManager, String toManager, AbstractPoOperation<P> prepareForCopy) {
		super(poClass, fromManager, toManager, prepareForCopy);
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		List<P> sourcePos = new ArrayList<P>();
		for(K k : keys) {
			P po = Factory.create(poClass);
			po.setKey(k);
			po = pwFrom.read(po);
			P copy = PoUtils.deepCopy(po);
			sourcePos.add(copy);
		}
		if (prepareForCopy!=null) {
			prepareForCopy.execute();
		}
		for (P sourcePo : sourcePos) {
			pwTo.store(sourcePo);
		}
	}

	/**
	 * Gets the keys.
	 *
	 * @return Returns the keys
	 */
	public Set<K> getKeys() {
		return keys;
	}

	/**
	 * Assigns a new value to the keys.
	 *
	 * @param keys the keys to set
	 */
	public void setKeys(Set<K> keys) {
		this.keys = keys;
	}

}
