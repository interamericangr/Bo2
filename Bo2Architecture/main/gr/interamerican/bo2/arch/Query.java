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


import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;


/**
 * Query is a java wrapper around a cursor to the data storage layer.
 * 
 * The cursor fetches rows one at a time.  
 * 
 *
 */
public interface Query extends Worker  {
    
    /**
     * Executes the query and creates the cursor. 
     *
     * @throws DataException
     */
    public void execute() 
    throws DataException;
    
    /**
     * Fetches the next row.
     * 
     * @return Returns true if the next row is fetched, 
     *         else it returns false.
     *         
     * @throws DataAccessException
     * 
     */
    public boolean next() 
    throws DataAccessException;
    
  
 
	/**
	 * Gets the current row number.
	 * 
	 * @return the current row number
	 * @throws DataAccessException
	 */
	public int getRow() throws DataAccessException;
	
	/**
	 * Instructs the query to avoid putting any lock to the
	 * data it reads.
	 * 
	 * Calling this method on a query with <code>true</code>
	 * instructs the query to avoid putting any locks to the
	 * data it reads. In case the query runs inside a transaction
	 * the fetched data should be locked, since update on these
	 * data would affect the current transaction. But in case
	 * the query does not run inside a transaction it could be 
	 * acceptable to let data be modified by other threads,
	 * while it might be unacceptable to lock the data for a
	 * long time. In the latter case, the query should be 
	 * instructed to avoid locks. <br/>
	 * It is possible that the technology used for the implementation 
	 * of the query can't support calling locking or avoiding locks.
	 * In such case, the method should throw a {@link DataOperationNotSupportedException}
	 * if this method is called with an argument <code>true</code> or
	 * <code>false</code> that can't be supported. <br/>
	 * 
	 * @param avoidLock 
	 *        Indicates if the query will avoid to lock fetched
	 *        data.
	 */
	public void setAvoidLock(boolean avoidLock);
    

	/**
	 * Indicates if the query will avoid to lock fetched
	 * data from access by other threads.
	 *        
	 * @return Returns an indicator showing if the query will
	 *         avoid to lock fetched data from access by other 
	 *         threads.
	 * 
	 * @see Query#setAvoidLock(boolean)
	 * 
	 * 
	 */
	public boolean isAvoidLock();    
  
    

}
