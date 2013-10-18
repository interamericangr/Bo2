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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;

/**
 * {@link UnitTestCmd} is a runtime command developed for writing 
 * unit tests.
 * 
 * Each instance of UnitTestCmd serves for a unit test of a class.
 * 
 * @param <T> Declaration type of object being tested.
 */
public abstract class UnitTestCmd<T> 
extends AbstractBo2RuntimeCmd {
	
	/**
	 * Test subject.
	 */
	protected T subject;
	
	/**
	 * Implementation class of test subject.
	 */
	private Class<? extends T> classUnderTest; 
	
	/**
	 * Creates a new UnitTestCmd object. 
	 * 
	 * @param classUnderTest Class being tested.
	 *
	 */
	public UnitTestCmd(Class<? extends T> classUnderTest) {
		super();
		this.classUnderTest = classUnderTest;
	}


	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd#work()
	 */
	@Override
	public void work() 
	throws LogicException, DataException, InitializationException, UnexpectedException {
		subject = Factory.create(classUnderTest);
		if (subject instanceof Worker) {
			Worker worker = (Worker) subject;
			worker.init(this.getProvider());
			worker.open();
		}
		test();
		if (subject instanceof Worker) {
			((Worker) subject).close();
		}
	}
	
	
	/**
	 * Unit test code goes here.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws InitializationException
	 * @throws UnexpectedException
	 */
	public abstract void test() 
	throws LogicException, DataException, InitializationException, UnexpectedException;
	

}
