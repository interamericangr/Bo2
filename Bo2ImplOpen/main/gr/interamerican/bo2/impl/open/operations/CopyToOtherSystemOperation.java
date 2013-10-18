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
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.Serializable;

/**
 * Copies a single {@link PersistentObject} from one system
 * to another, given its {@link Key}.
 * 
 * @param <P> 
 *        Type of PersistentObject
 * @param <K> 
 *        Type of Key
 */
public class CopyToOtherSystemOperation
<P extends PersistentObject<K>, 
 K extends Serializable & Comparable<? super K>> 
extends AbstractOperation {
	
	/**
	 * Class of persistent object.
	 */
	Class<P> poClass;
	
	/**
	 * Name of manager from whom the po is read.
	 */
	String fromManager;

	/**
	 * Name of manager to whom the po is stored.
	 */
	String toManager;
	
	/**
	 * PW to read the po.
	 */
	@Child PersistenceWorker<P> pwFrom;
	
	/**
	 * PW to write the po.
	 */	
	@Child PersistenceWorker<P> pwTo;
	
	/**
	 * Unique Id of persistent object.
	 */
	K key;
	
	/**
	 * Operation to operate on the object before the copy.
	 */
	@Child AbstractPoOperation<P> prepareForCopy;
	
	/**
	 * Creates a new CopyToOtherSystemOperation object. 
	 *
	 * @param poClass
	 *        Class of persistent object.
	 * @param fromManager
	 *        Name of manager for the persistence worker that reads the persistent
	 *        object.
	 * @param toManager
	 *        Name of manager for the persistence worker that stores the persistent
	 *        object.
	 * @param prepareForCopy 
	 *        Operation to execute on the object before copying it.
	 * 
	 */
	public CopyToOtherSystemOperation(Class<P> poClass, String fromManager, String toManager, AbstractPoOperation<P> prepareForCopy) {
		super();
		this.poClass = poClass;
		this.pwFrom = Factory.createPw(poClass);
		this.prepareForCopy = prepareForCopy;
		if (this.prepareForCopy!=null) {
			this.prepareForCopy.setManagerName(getManagerName());
		}
		AbstractResourceConsumer from = (AbstractResourceConsumer) pwFrom;
		from.setManagerName(fromManager);
		this.pwTo = Factory.createPw(poClass);
		AbstractResourceConsumer to = (AbstractResourceConsumer) pwTo;
		to.setManagerName(toManager);
	}
	
	/**
	 * Creates a new CopyToOtherSystemOperation object. 
	 *
	 * @param poClass
	 *        Class of persistent object.
	 * @param fromManager
	 *        Name of manager for the persistence worker that reads the persistent
	 *        object.
	 * @param toManager
	 *        Name of manager for the persistence worker that stores the persistent
	 *        object.
	 */
	public CopyToOtherSystemOperation(Class<P> poClass, String fromManager, String toManager) {
		this(poClass, fromManager, toManager, null);
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		P po = Factory.create(poClass);
		po.setKey(key);
		po = pwFrom.read(po);
		P copy = PoUtils.deepCopy(po);
		if (prepareForCopy!=null) {
			prepareForCopy.setPo(copy);
			prepareForCopy.execute();
		}
		pwTo.store(copy);
	}

	/**
	 * Gets the key.
	 *
	 * @return Returns the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Assigns a new value to the key.
	 *
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}
	
	

}
