package gr.interamerican.bo2.impl.open.hibernate.copy;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.utils.beans.Pair;

import java.util.List;

/**
 * basic interface to be extends by copy pos Operations.
 *
 * @param <T> the generic type
 */
public interface CopyPosBaseProperties<T extends Key> {

	/**
	 * sets the manager to perform the operation (null for package-info manager).
	 *
	 * @param manager the new from manager
	 */
	void setFromManager(String manager);

	/**
	 * sets the manager to perform the operation (null for package-info manager).
	 *
	 * @param manager the new to manager
	 */
	void setToManager(String manager);

	/**
	 * sets pair of keys to copy.
	 *
	 * @param keys the keys
	 */
	void setKeys(List<Pair<T, T>> keys);
}
