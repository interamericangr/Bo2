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
import gr.interamerican.bo2.arch.exceptions.LogicException;


/**
 * Question to the data storage layer that returns an answer.
 * 
 * The question can have multiple input parameters. All these can be specified
 * with the appropriate setter methods. The answer to the question is retrieved
 * with the <code>getAnswer()</code> method. <b/>
 * Question is a sub-interface of worker based on the command pattern.
 * The command method of a question is <code>ask()</code>. 
 * 
 * It makes the question with <code>ask()</code> and returns the answer as an object
 * with <code>getAnswer()</code>. <br/>
 * 
 * Typical use of a question q is like this: <br/>
 * <code>
 * q.init(provider); <br/>
 * q.open(); <br/>
 * //set parameters, something like: <br/>
 * q.setParam1(inParam1); <br/>
 * q.setParam1(inParam2); <br/>
 * q.ask(); <br/>
 * answer1=q.getAnswer(); <br/>
 * //the question can be asked again with different parameters <br/> 
 * q.setParam1(inParam3); <br/>
 * q.setParam1(inParam4); <br/>
 * q.ask(); <br/>
 * answer2=q.getAnswer(); <br/>
 * w.close(); <br/>
 * </code>
 *  
 * @param <A> Class of answer.
 *
 */
public interface Question<A> extends Worker {
    
    /**
     * Asks the question and creates the answer
     * @throws DataException Caused by exceptions on data storage layer operations.
     * @throws LogicException Caused by logical conditions.
     */
    public void ask() 
    throws DataException, LogicException;
    
    /**
     * Gets the answer.
     * 
     * @return the answer
     * 
     */
    public A getAnswer();
    

}
