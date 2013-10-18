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
import gr.interamerican.bo2.arch.exceptions.InitializationException;




/**
 * Worker is the basic interface for any runtime independent operation. <br/>
 * 
 * A Worker is a class that requires runtime dependent resources
 * to complete its tasks. It will acquire these resources by the
 * runtime layer abstraction that is represented by a {@link Provider}.
 * Workers must never destroy or modify the resources that are
 * provided by a provider. The provider is responsible for the
 * life cycle of any resource it provides. <br/>
 * For example, a worker that receives a database connection 
 * by his provider should never close or destroy this connection. <br/>
 * Typical use of a worker w is like this: <br/>
 * <code>
 * w.init(provider); <br/>
 * w.open(); <br/>
 * //do something with w here <br/>
 * w.close(); <br/>
 * </code>
 *
 *
 */
public interface Worker {
    
    /**
     * Prepares the worker.
     * 
     * Creation or initialization of any attribute that requires 
     * runtime dependent resources takes place here.
     *  
     * @throws DataException
     *         When the preparation of the worker fails.
     */
    public void open() throws DataException;
    
    
    /**
     * Worker cleanup.
     * 
     * Cleanup of any resources that were created by the worker. 
     * These resources are created by the <code>open()</code> method. 
     * Resources provided by the parent provider in the 
     * <code>init(Provider p)</code> method belong to the parent
     * and their state should not be modified by the worker.
     *  
     * @throws DataException
     *         When the cleanup fails.
     */
    public void close() throws DataException;
    
    
    /**
     * Introduces the runtime context (environment) to the worker. 
     * 
     * The parent provides the runtime context. The runtime context 
     * can be anything that facilitates access to any back-end system 
     * (Databases, Files, Application Server etc). <br/>
     * Different implementations of Worker, will require from their 
     * parent provider resources of different types. Sub-interfaces
     * of {@link Provider} can provide resources of any type. The
     * implementations of worker must get the specific sub-type of
     * Provider by their <code>parent</code> by calling the method
     * <code>parent.getProvider(subclass)</code> where subclass is
     * the sub-interface of Provider that is required. <br/> 
     * 
     * @param parent 
     *        The runtime provider
     * 
     * @throws InitializationException
     *         When the <code>parent</code> fails to provide the 
     *         resources required by the worker. 
     */
    public void init(Provider parent) throws InitializationException;
    
    
    /**
     * Gets a reference to this worker's parent Provider.
     * 
     * @return Returns a reference to the Worker's parent Provider.
     */
    public Provider getProvider();


}
