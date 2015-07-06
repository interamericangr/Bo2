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
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The {@link PropertiesLauncher}'s responsibility is to execute a
 * {@link AbstractBo2RuntimeWithPropertiesCmd}. <br/>
 *
 * This class has only a main method, that takes the name of a
 * {@link AbstractBo2RuntimeWithPropertiesCmd} class as argument and a second argument the path of
 * the properties file. Then it loads the class, creates an instance of it and executes it.
 *
 */
public class PropertiesLauncher {
	/**
	 * Default logger for Bo2.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PropertiesLauncher.class);


	/**
	 * @param path
	 * @return the properties read from file (resource of filesystem)
	 * @throws IOException
	 */
	private static Properties getPropertiesFromFile(String path) throws IOException {
		InputStream stream;
		try{
			stream = StreamUtils.getResourceStream(path);
			LOG.trace("File Opened from Resource Stream" + path); //$NON-NLS-1$
		}catch (IOException e){
			stream = StreamUtils.getFileStream(path);
			LOG.trace("File Opened from File System Stream" + path); //$NON-NLS-1$
		}
		Properties p = new Properties();
		p.load(stream);
		LOG.trace("Properties provided " + p); //$NON-NLS-1$
		return p;

	}

	/**
	 * launches the pre-process class if it exists.
	 *
	 * @param p
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 */
	static void launchPreprocess(Properties p) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, DataException, LogicException,
	UnexpectedException {
		String preProcess = p.getProperty(PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS);
		if ((preProcess != null) && (preProcess.length() > 0)) {
			LOG.debug("Launching pre process:" + preProcess); //$NON-NLS-1$
			launchRuntimeCommand(preProcess);
		}
	}

	/**
	 * launches the post-process class if it exists.
	 *
	 * @param p
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 */
	static void launchPostprocess(Properties p) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, DataException, LogicException,
	UnexpectedException {
		String postProcess = p.getProperty(PropertiesLauncherParamsNames.POST_PROCESSING_CLASS);
		if ((postProcess != null) && (postProcess.length() > 0)) {
			LOG.debug("Launching post process:" + postProcess); //$NON-NLS-1$
			launchRuntimeCommand(postProcess);
		}
	}

	/**
	 * launches a {@link RuntimeCommand}
	 *
	 * @param name
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	static void launchRuntimeCommand(String name) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, DataException, LogicException,
	UnexpectedException {
		Class<?> cmdClass = Class.forName(name);
		RuntimeCommand cmd = (RuntimeCommand) cmdClass.newInstance();
		cmd.execute();
	}

	/**
	 * launches the main class passing also the properties.
	 *
	 * @param className
	 * @param p
	 * @throws ClassNotFoundException
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	static void launchAbstractBo2RuntimeWithPropertiesCmd(String className, Properties p)
			throws ClassNotFoundException, DataException, LogicException, UnexpectedException,
			InstantiationException, IllegalAccessException {
		LOG.trace("Launching " + className); //$NON-NLS-1$
		Class<?> cmdClass = Class.forName(className);
		AbstractBo2RuntimeWithPropertiesCmd cmd = (AbstractBo2RuntimeWithPropertiesCmd) cmdClass
				.newInstance();
		cmd.setExecutionProperties(p);
		cmd.execute();
	}

	/**
	 * hides all parameters accessed by the {@link PropertiesLauncher}.
	 *
	 * @param p
	 * @return
	 */
	static Properties hidePrePostOperation(Properties p) {
		Properties hidden = new Properties();
		for (String key : p.stringPropertyNames()) {
			if (PropertiesLauncherParamsNames.POST_PROCESSING_CLASS.equals(key)
					|| PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS.equals(key)
					|| PropertiesLauncherParamsNames.CLASSNAME.equals(key)) {
				continue;
			}
			hidden.setProperty(key, p.getProperty(key));
		}
		return hidden;
	}

	/**
	 * When an exception is thrown in java, the JLS does not define the exit value.<br>
	 * We are using this method to catch any possible exceptions, log them and set the exit value to
	 * -1 (generally we need an exit value!=0 to denote an abnormal execution).<br>
	 * Specifically when running this program using ant and setting to the java task 'fork="true"'
	 * this is the only way
	 * for the ant process to exit.
	 *
	 * @param arg
	 */
	private static void launch(String arg) {
		try {
			String propertiesFile = arg;
			Properties p=getPropertiesFromFile(propertiesFile);
			launchPreprocess(p);
			launchAbstractBo2RuntimeWithPropertiesCmd(
					p.getProperty(PropertiesLauncherParamsNames.CLASSNAME), hidePrePostOperation(p));
			launchPostprocess(p);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Executes the command.
	 *
	 * @param args
	 *            Only one argument is expected. The one and only argument must be the the name of a
	 *            {@link RuntimeCommand} class. This class is loaded and then an instance of it is
	 *            instantiated and
	 *            executed.
	 */
	public static void main(String[] args) {
		LOG.trace("Launching the following args " + args); //$NON-NLS-1$
		for (String arg : args) {
			launch(arg);
		}
	}
}
