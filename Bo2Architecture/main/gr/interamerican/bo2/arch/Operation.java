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



/**
 * An operation is a command object that performs a business 
 * operation.
 * <br>
 * Any inputs to the operation must be provided with setters. 
 * The method throws any errors that might be thrown during it.
 * Any output, must be returned through a getter.
 * <br> 
 * An Operation execution should be <i>consistent</i>. This 
 * means that multiple invocations of {@link #execute()} with 
 * the same input must produce the same output. 
 */
public interface Operation extends LogicOperation, Worker {
	/* empty */    
}
