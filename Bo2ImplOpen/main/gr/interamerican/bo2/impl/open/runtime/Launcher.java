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


/**
 * The {@link Launcher}'s responsibility is to execute a {@link RuntimeCommand}. <br/>
 *
 * This class has only a main method, that takes the name of a {@link RuntimeCommand} class as argument. Then it loads
 * the class, creates an instance of it and executes it.
 *
 * @deprecated use {@link MultiLauncher} instead
 */
@Deprecated
public class Launcher {

	/**
	 * Executes the command.
	 *
	 * @param args
	 *            Only one argument is expected. The one and only argument must be the the name of a
	 *            {@link RuntimeCommand} class. This class is loaded and then an instance of it is instantiated and
	 *            executed.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * When an exception is thrown in java, the JLS does not define the exit value.<br>
	 * We are using this method to catch any possible exceptions, log them and set the exit value to -1 (generally we
	 * need an exit value!=0 to denote an abnormal execution).<br>
	 * Specifically when running this program using ant and setting to the java task 'fork="true"' this is the only way
	 * for the ant process to exit.
	 *
	 * @param args
	 */
	private static void launch(String[] args) {
		try {
			String className = args[0];
			Class<?> cmdClass = Class.forName(className);
			RuntimeCommand cmd = (RuntimeCommand) cmdClass.newInstance();
			cmd.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}


}
