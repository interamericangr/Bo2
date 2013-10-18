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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.utils.Bo2;

/**
 * The {@link MultiLauncher}'s responsibility is to execute multiple {@link RuntimeCommand}. <br/>
 * 
 * This class has only a main method, that takes the name of a {@link RuntimeCommand} class as argument. Then it loads
 * the class, creates an instance of it and executes it.
 */
public class MultiLauncher {
	
	/**
	 * Executes a set of commands. If any of them fails, the jvm exits.
	 * 
	 * @param args
	 *            Multiple argument are expected. The arguments must be names of {@link RuntimeCommand}s class. This
	 *            class are loaded and then an instance of it is instantiated and executed.
	 * 
	 * @throws ClassNotFoundException
	 *             If the class specified as argument is not found.
	 * @throws IllegalAccessException
	 *             At instantiation of the RuntimeCommand.
	 * @throws InstantiationException
	 *             At instantiation of the RuntimeCommand.
	 * @throws LogicException
	 *             Thrown by the RuntimeCommand.
	 * @throws DataException
	 *             Thrown by the RuntimeCommand.
	 * @throws UnexpectedException
	 *             Thrown by the RuntimeCommand.
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnexpectedException, DataException, LogicException {
		for (String className : args) {
			launch(className);
		}
	}

	/**
	 * When an exception is thrown in java, the JLS does not define the exit value.<br>
	 * We are using this method to catch any possible exceptions, log them and set the exit value to -1 (generally we
	 * need an exit value!=0 to denote an abnormal execution).<br>
	 * Specifically when running this program using ant and setting to the java task 'fork="true"' this is the only way
	 * for the ant process to exit.
	 * 
	 * @param className
	 */
	private static void launch(String className) {
		try {
			Bo2.getLogger().info("Running:" + className); //$NON-NLS-1$
			Class<?> cmdClass = Class.forName(className);
			RuntimeCommand cmd = (RuntimeCommand) cmdClass.newInstance();
			cmd.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
}
