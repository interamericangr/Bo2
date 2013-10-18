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

import gr.interamerican.bo2.arch.enums.TargetEnvironment;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.PropertiesInitializedBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Bean with the properties of the current Bo2 deployment.
 */
public class Bo2DeploymentParams 
extends PropertiesInitializedBean {

	/**
	 * Creates a new Bo2DeploymentParams object. 
	 *
	 * @param properties
	 */
	public Bo2DeploymentParams(Properties properties) {
		super(properties);
		preLoadClasses();
	}
	
	/**
	 * Preloads classes.
	 */
	void preLoadClasses() {
		try {
			String[] classesToLoad = StreamUtils.readResourceFile(pathToPreLoadClasses);
			for (int i = 0; i < classesToLoad.length; i++) {
				if (!StringUtils.isNullOrBlank(classesToLoad[i])) {
					Class.forName(classesToLoad[i]);
				}
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}
	
	/**
	 * {@link Debug} enablement.
	 */
	private Boolean debugEnabled;
	
	/**
	 * {@link TargetEnvironment}.
	 */
	private TargetEnvironment targetEnvironment;
	
	/**
	 * Controls whether the stackTrace is shown in
	 * the error panel.
	 */
	private Boolean showStackTraceInWebPages;
	
	/**
	 * path To Default Factory Mappings
	 */
	private String pathToDefaultFactoryDefinition;
	
	/**
	 * path to default panel factory mappings.
	 */
	private String pathToDefaultPanelFactoryDefinition;
	
	/**
	 * path to the list of managers definitions.
	 */
	private String pathToManagersList;
	
	/**
	 * path to the properties file with manager aliases.
	 */
	private String pathToManagerAliases;
	
	/**
	 * path to the list of classes that are loaded immediately.
	 */
	private String pathToPreLoadClasses;

	/**
	 * Short date format.
	 */
	private String shortDateFormat;
	
	/**
	 * Long date format.
	 */
	private String longDateFormat;
	
	/**
	 * Iso date format.
	 */
	private String isoDateFormat;
	
	/**
	 * name of default resource manager for streams used by AbstractRuntimeCommands.
	 */
	private String streamsManagerName;
	
	/**
	 * Class name of TransactionManager.
	 */
	private String transactionManagerClass;
	
	/**
	 * Temporary directory.
	 */
	private String tempDir;
	
	/**
	 * Application server deployments normally use server provided JTA
	 * implementations for transaction management. In some cases, it is
	 * necessary to be able to perform a batch transaction in the container
	 * environment. For this reason, a secondary deployment configuration
	 * for batch operations may be defined.
	 */
	private String pathToSecondaryBatchDeployment;
	
	/**
	 * Encoding of test files accessed from the filesystem or from jars. 
	 */
	private String textEncoding;
	
	/**
	 * Gets the debugEnabled.
	 *
	 * @return Returns the debugEnabled
	 */
	public Boolean getDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Gets the targetEnvironment.
	 *
	 * @return Returns the targetEnvironment
	 */
	public TargetEnvironment getTargetEnvironment() {
		return Utils.notNull(targetEnvironment, TargetEnvironment.DEVELOPMENT);
	}

	/**
	 * Gets the shortDateFormat.
	 *
	 * @return Returns the shortDateFormat
	 */
	public String getShortDateFormat() {
		return shortDateFormat;
	}

	/**
	 * Gets the longDateFormat.
	 *
	 * @return Returns the longDateFormat
	 */
	public String getLongDateFormat() {
		return longDateFormat;
	}

	/**
	 * Gets the isoDateFormat.
	 *
	 * @return Returns the isoDateFormat
	 */
	public String getIsoDateFormat() {
		return isoDateFormat;
	}

	/**
	 * Path to the properties file that contains the definition of the
	 * default object factory.
	 * 
	 * @return Returns the path to the definition of the default object
	 *         factory.
	 */
	public String getPathToDefaultFactoryDefinition() {
		return pathToDefaultFactoryDefinition;
	}
	

	/**
	 * Path to the text file that contains the list of resource managers
	 * definitions.
	 * 
	 * @return Returns the path to the definitions of resource managers.
	 */
	public String getPathToManagersList() {
		return pathToManagersList;
	}

	/**
	 * Gets the name of the default streamsManager.
	 *
	 * @return Returns the streamsManagerName
	 */
	public String getStreamsManagerName() {
		return streamsManagerName;
	}

	/**
	 * Gets the pathToPreLoadClasses.
	 *
	 * @return Returns the pathToPreLoadClasses
	 */
	public String getPathToPreLoadClasses() {
		return pathToPreLoadClasses;
	}

	/**
	 * Gets the transactionManagerClass.
	 *
	 * @return Returns the transactionManagerClass
	 */
	public String getTransactionManagerClass() {
		return transactionManagerClass;
	}

	/**
	 * Gets the pathToManagerAliases.
	 * 
	 * @return Returns the path to the manager aliases properties file.
	 */
	public String getPathToManagerAliases() {
		return pathToManagerAliases;
	}

	/**
	 * Gets the pathToDefaultPanelFactoryDefinition.
	 * 
	 * @return Returns the path to the default panel factory mappings
	 *         properties file.
	 */
	public String getPathToDefaultPanelFactoryDefinition() {
		return pathToDefaultPanelFactoryDefinition;
	}

	/**
	 * Gets the showStackTraceInWebPages.
	 *
	 * @return Returns the showStackTraceInWebPages
	 */
	public Boolean getShowStackTraceInWebPages() {
		return showStackTraceInWebPages;
	}

	/**
	 * Assigns a new value to the showStackTraceInWebPages.
	 *
	 * @param showStackTraceInWebPages the showStackTraceInWebPages to set
	 */
	public void setShowStackTraceInWebPages(Boolean showStackTraceInWebPages) {
		this.showStackTraceInWebPages = showStackTraceInWebPages;
	}

	/**
	 * Gets the tempDir.
	 *
	 * @return Returns the tempDir
	 */
	public String getTempDir() {
		return tempDir;
	}

	/**
	 * Assigns a new value to the tempDir.
	 *
	 * @param tempDir the tempDir to set
	 */
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * Gets the pathToSecondaryBatchDeployment.
	 *
	 * @return Returns the pathToSecondaryBatchDeployment
	 */
	public String getPathToSecondaryBatchDeployment() {
		return pathToSecondaryBatchDeployment;
	}

	/**
	 * Assigns a new value to the pathToSecondaryBatchDeployment.
	 *
	 * @param pathToSecondaryBatchDeployment the pathToSecondaryBatchDeployment to set
	 */
	public void setPathToSecondaryBatchDeployment(String pathToSecondaryBatchDeployment) {
		this.pathToSecondaryBatchDeployment = pathToSecondaryBatchDeployment;
	}

	/**
	 * Gets the textEncoding.
	 *
	 * @return Returns the textEncoding
	 */
	public String getTextEncoding() {
		return textEncoding;
	}

	/**
	 * Assigns a new value to the textEncoding.
	 *
	 * @param textEncoding the textEncoding to set
	 */
	public void setTextEncoding(String textEncoding) {
		this.textEncoding = textEncoding;
	}
	
}
