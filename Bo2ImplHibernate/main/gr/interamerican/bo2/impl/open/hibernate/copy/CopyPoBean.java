package gr.interamerican.bo2.impl.open.hibernate.copy;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;

import java.io.Serializable;

/**
 * interface that will contain information about the po to be copied
 * 
 * @param <K>
 *            Key
 * @param <P>
 *            Persistent object
 * @param <O>
 *            PO operation.
 */
public interface CopyPoBean
<K extends Serializable & Comparable<? super K>,
P extends PersistentObject<?>,
O extends AbstractPoOperation<P>> {

	/**
	 * @return the interface of P.
	 */
	Class<P> getPoInterface();

	/**
	 * sets the interface of P
	 * 
	 * @param c
	 */
	void setPoInterface(Class<P> c);
	/**
	 * @return the {@link PersistentObject}
	 */
	P getFromPo();

	/**
	 * sets the {@link PersistentObject}
	 * 
	 * @param po
	 */
	void setFromPo(P po);

	/**
	 * @return the {@link PersistentObject}
	 */
	P getToPo();

	/**
	 * sets the {@link PersistentObject}
	 * 
	 * @param po
	 */
	void setToPo(P po);

	/**
	 * @return the intermediate operation
	 */
	O getOperation();

	/**
	 * sets the intermediate operation.
	 * 
	 * @param op
	 */
	void setOperation(O op);

	/**
	 * @return from key
	 */
	K getFromKey();

	/**
	 * set from key.
	 * 
	 * @param key
	 */
	void setFromKey(K key);

	/**
	 * @return to key.
	 */
	K getToKey();

	/**
	 * set to key.
	 * 
	 * @param key
	 */
	void setToKey(K key);
}
