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



/**
 * A DataCommand is a command object that performs a data layer 
 * command.
 * 
 * The command method of the object is the <code>execute()</code> 
 * method. The DataCommand interface should be used in any case
 * of an update to the data layer, with the exception of updates
 * on persistent objects that are done by the associated persistence 
 * workers. <p>
 * A common use case of DataCommand is an update statement
 * that is ment to update multiple rows of a table at once.
 * A DataCommand can execute more than one statement in its
 * execute method. <p>
 *
 */
public interface DataCommand extends Worker {
    
    /**
     * Command main body
     * @throws DataException
     */
    public abstract void execute() throws DataException;

}
