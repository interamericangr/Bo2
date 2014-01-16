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
package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.ThreadLocalConnectionStrategy;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.SystemUtils;
import gr.interamerican.bo2.utils.mail.MailConstants;
import gr.interamerican.bo2.utils.mail.MailServer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bo2 is a utility class for the Bo2 framework.
 */
public class Bo2 {

	/**
	 * Path for deployment properties.
	 */
	public static final String DEFAULT_DEPLOYMENT_PROPERTIES_PATH =
			"/gr/interamerican/bo2/deployparms/deployment.properties"; //$NON-NLS-1$

	/**
	 * Default logger for Bo2.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Bo2.class);

	/**
	 * Default deployment.
	 */
	private static final Bo2Deployment DEFAULT_DEPLOYMENT =
			new Bo2Deployment(DEFAULT_DEPLOYMENT_PROPERTIES_PATH);

	/**
	 * Deployments.
	 */
	private static final Map<String,Bo2Deployment> DEPLOYMENTS =
			new HashMap<String, Bo2Deployment>();

	static {
		DEPLOYMENTS.put(DEFAULT_DEPLOYMENT_PROPERTIES_PATH, DEFAULT_DEPLOYMENT);
		initBo2();
	}

	/**
	 * Checks that the specified element is not null and if so,
	 * throws a {@link RuntimeException}.
	 * 
	 * @param object
	 * @param property
	 */
	@SuppressWarnings("nls")
	static void checkNotNull(Object object, String property) {
		if (object==null) {
			String msg = "Property " + property + " not specified in default deployment";
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Initialization.
	 *
	 */
	@SuppressWarnings("nls")
	private static void initBo2() {
		Bo2DeploymentParams dp = DEFAULT_DEPLOYMENT.getDeploymentBean();

		/*
		 * Debug.
		 */
		Debug.setEnabled(dp.getDebugEnabled());
		/*
		 * Date formats.
		 */
		String shortDf = dp.getShortDateFormat();
		checkNotNull(shortDf, "shortDateFormat");
		String isoDf = dp.getIsoDateFormat();
		checkNotNull(isoDf, "isoDateFormat");
		String longDf = dp.getLongDateFormat();
		checkNotNull(longDf, "longDateFormat");
		/*
		 * fs text file encoding
		 */
		String textCharset = dp.getTextEncoding();
		checkNotNull(textCharset, "textEncoding");
		/*
		 * resource text file
		 */
		String resourceFileCharset = dp.getResourceFileEncoding();
		checkNotNull(resourceFileCharset, "resourceFileEncoding");
		/*
		 * set Bo2UtilsEnvironment
		 */
		Bo2UtilsEnvironment.setEnvironment(shortDf, isoDf, longDf, textCharset, resourceFileCharset);
		/*
		 * SMTP initialization.
		 */
		String smtpHost = DEFAULT_DEPLOYMENT.getProperties().getProperty(MailConstants.SMTP_HOST);
		String smtpPort = DEFAULT_DEPLOYMENT.getProperties().getProperty(MailConstants.SMTP_PORT);
		MailServer.INSTANCE.setHost(smtpHost);
		MailServer.INSTANCE.setPort(smtpPort);
	}





	// /**
	// * Gets the default logger for Bo2.
	// *
	// * @return Returns the logger.
	// *
	// * @deprecated Use another logger, preferably one that does not belong to another project.
	// */
	// @Deprecated
	// public static Logger getLogger() {
	// return LOG;
	// }

	/**
	 * The Bo2 class is not being instantiated.
	 */
	private Bo2() {	/* empty */	}


	/**
	 * Sets the connection of the ThreadLocal variable of
	 * {@link ThreadLocalConnectionStrategy}. <br/>
	 * 
	 * This can be useful in cases where the core system running in the current
	 * deployment is not a Bo2 system, but a JDBC based system that can only
	 * use the JDBC connection transaction mechanism. In such a deployment the
	 * Bo2 modules can share the JDBC connection of the core system using a
	 * {@link ThreadLocalConnectionStrategy}. This means that the
	 * <code>connectionStrategy</code> property in the manager's properties file
	 * should have be set to  {@link ThreadLocalConnectionStrategy}.
	 * In such a case, the <code>close()</code> method of the provider should not
	 * be called. The core module that uses the Bo2 operation should close the
	 * resources and commit the transactions explicitly. In this case the Bo2
	 * deployment should not use any transaction manager; the transaction should
	 * be coordinated by the calling module. <br/>
	 * In a case like this, it would be preferable first to create the {@link Provider}
	 * and let him coordinate the transaction and provide the JDBC connection to the
	 * core module.
	 * 
	 * @param connection
	 *        JDBC connection to set.
	 */
	public static void setThreadLocalConnection(Connection connection) {
		ThreadLocalConnectionStrategy.THREAD_CONNECTION.set(connection);
	}

	/**
	 * Prints system status information.
	 * @param header
	 *        Header for the printout.
	 */
	@SuppressWarnings("nls")
	public static void sysInfo(String header) {
		String msg = "\n" + header;
		msg += "\nUsed memory:   " + SystemUtils.usedMemory();
		msg += "\nFree memory:   " + SystemUtils.freeMemory();
		msg += "\nTotal memory:  " + SystemUtils.totalMemory();
		msg += "\nMax VM memory: " + SystemUtils.maxMemory();
		if(LOG.isInfoEnabled()) {
			LOG.info(msg);
		}
	}

	/**
	 * Gets the defaultDeployment.
	 *
	 * @return Returns the defaultDeployment
	 */
	public static Bo2Deployment getDefaultDeployment() {
		return DEFAULT_DEPLOYMENT;
	}

	/**
	 * Gets the Bo2Deployment that is defined in the properties file
	 * found in the specified resource path.
	 * 
	 * @param path
	 *        Path to the resource file that contains the deployment
	 *        properties.
	 *
	 * @return Returns the Bo2Deployment object that was created using
	 *         the properties file found in the specified path. If the
	 *         creation of the Bo2Deployment file fails,
	 *         then a RuntimeException is thrown.
	 */
	public static Bo2Deployment getDeployment(String path) {
		Bo2Deployment depl = DEPLOYMENTS.get(path);
		if (depl==null) {
			depl=new Bo2Deployment(path);
			DEPLOYMENTS.put(path, depl);
		}
		return depl;
	}

	/**
	 * Gets a new provider, created by the default Bo2Deployment.
	 * 
	 * This method delegates to {@link Bo2Deployment#getProvider()} which
	 * always creates a new Provider.
	 * 
	 * @return Returns a new provider, created by the {@link Bo2Deployment}
	 *         that has been marked as default.
	 * 
	 * @throws InitializationException
	 *         If the creation of the Provider fails.
	 */
	public static Provider getProvider() throws InitializationException {
		return DEFAULT_DEPLOYMENT.getProvider();
	}

}
