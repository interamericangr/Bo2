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
package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;

/**
 * Persistence utility of a persistent class. 
 * 
 * This class undertakes persistence of the class PO to a data storage.
 * The PersistenceWorker hides the technology specifics used for 
 * the underlying persistence implementation.
 * The main methods of a {@link PersistenceUtility} are four methods
 * used for CRUD operations.
 * <li> <code>store(po)</code> </li>
 * <li> <code>read(po)</code> </li>  
 * <li> <code>update(po)</code> </li>
 * <li> <code>delete(po)</code> </li>
 * The name store is used instead of create, implying that the
 * object is created in the JVM, and it is just stored in the
 * database. <br/>
 * All methods have only one argument, the object to persist.
 * Ideally these methods will apply any change that would take place
 * during the database operation to the argument it self. 
 * The implementation specifics of some persistence technologies 
 * make it hard to follow this rule always. For those cases, the
 * CRUD methods of this interface return the object associated with 
 * the persistence operation.
 * 
 * @param <PO> Class that is persisted by this {@link PersistenceUtility}. 
 *   
 */
public interface PersistenceUtility<PO> {
	
    /**
     * Reads an object.
     *  
     * @param o Object to read from data storage.
     * 
     * @return Returns the object related with the persistence
     *         operation.
     * 
     * @throws DataException
     * @throws PoNotFoundException
     */
    public PO read(PO o) 
    throws DataException, PoNotFoundException;

    /**
     * Stores the object.
     *  
     * @param o Object to be stored to data storage.
     * 
     * @return Returns the object related with the persistence
     *         operation.
     * 
     * @throws DataException
     * @throws PoNotFoundException
     */
    public PO store(PO o) 
    throws DataException, PoNotFoundException;
    
    
    /**
     * Deletes an object from the data storage.
     * 
     * @param o Object to be deleted from data storage.
     * 
     * @return Returns the object related with the persistence
     *         operation.
     * 
     * @throws DataException
     * @throws PoNotFoundException
     */
    public PO delete(PO o) 
    throws DataException, PoNotFoundException;  
    
    
    
    /**
     * Updates an object in the data storage.
     * 
     * @param o Object to be updated.     
     * 
     * @return Returns the object related with the persistence
     *         operation.
     * 
     * @throws DataException
     * @throws PoNotFoundException
     */
    public PO update (PO o) 
    throws DataException , PoNotFoundException;
    
    /**
     * Indicates if this PersistenceUtility reads all associations
     * of the PersistentObject or if it omits a part. 
     * 
     * @return true if this PersistenceUtility omits a part of the 
     *         object graph.
     */
    public boolean ignoresSomething();

}
