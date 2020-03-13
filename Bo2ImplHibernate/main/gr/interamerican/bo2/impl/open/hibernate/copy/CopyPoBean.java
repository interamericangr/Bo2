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
package gr.interamerican.bo2.impl.open.hibernate.copy;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.operations.CopyComplexEntityOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;

import java.io.Serializable;

/**
 * interface that will contain information about the po to be copied.
 *
 * @param <K>            Key
 * @param <P>            Persistent object
 * @param <O>            PO operation.
 * @deprecated use {@link CopyComplexEntityOperation} instead
 */
@Deprecated
public interface CopyPoBean
<K extends Serializable & Comparable<? super K>,
P extends PersistentObject<?>,
O extends AbstractPoOperation<P>> {

	/**
	 * Gets the po interface.
	 *
	 * @return the interface of P.
	 */
	Class<P> getPoInterface();

	/**
	 * sets the interface of P.
	 *
	 * @param c the new po interface
	 */
	void setPoInterface(Class<P> c);
	
	/**
	 * Gets the from po.
	 *
	 * @return the {@link PersistentObject}
	 */
	P getFromPo();

	/**
	 * sets the {@link PersistentObject}.
	 *
	 * @param po the new from po
	 */
	void setFromPo(P po);

	/**
	 * Gets the to po.
	 *
	 * @return the {@link PersistentObject}
	 */
	P getToPo();

	/**
	 * sets the {@link PersistentObject}.
	 *
	 * @param po the new to po
	 */
	void setToPo(P po);

	/**
	 * Gets the operation.
	 *
	 * @return the intermediate operation
	 */
	O getOperation();

	/**
	 * sets the intermediate operation.
	 *
	 * @param op the new operation
	 */
	void setOperation(O op);

	/**
	 * Gets the from key.
	 *
	 * @return from key
	 */
	K getFromKey();

	/**
	 * set from key.
	 *
	 * @param key the new from key
	 */
	void setFromKey(K key);

	/**
	 * Gets the to key.
	 *
	 * @return to key.
	 */
	K getToKey();

	/**
	 * set to key.
	 *
	 * @param key the new to key
	 */
	void setToKey(K key);
}
