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

import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.ObjectFactoryAssistant;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Runtime manager info. Use it for sanity checks.
 */
public class Bo2DeploymentInfoUtility {
	
	/**
	 * @param managerName
	 * @return the default qualifier for the given manager
	 */
	public String getQualifier(String managerName) {
		Properties p = getPropertiesOfManager(managerName);
		return p.getProperty(JdbcConnectionProviderImpl.KEY_DBSCHEMA);
	}

	/**
	 * @param deplPath
	 * @param managerName
	 * 
	 * @return Returns a short summary of configuration data for the specified manager name in the specified deployment.
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public String getInfoForManager(String deplPath, String managerName) {
		Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
		Bo2DeploymentInfo info = Bo2DeploymentInfo.newInstance();
		info.manager = managerName;
		info.transactionManager = bo2Depl.getDeploymentBean().getTransactionManagerClass();
		info.environment = String.valueOf(bo2Depl.getDeploymentBean().getTargetEnvironment());
		
		try {
			Map<String, String> managerAliases = (Map<String, String>) ReflectionUtils.get("managerAliases", bo2Depl);
			Map<String, ObjectFactory> managerFactories = (Map<String, ObjectFactory>) ReflectionUtils.get("managerFactories", bo2Depl);
			
			ObjectFactory of = managerFactories.get(managerName);
			if(of == null) {
				String alias = managerAliases.get(managerName);
				of = managerFactories.get(alias);
			}
			
			ObjectFactoryAssistant assistant = (ObjectFactoryAssistant) ReflectionUtils.get("assistant", of);
			Properties props = (Properties) ReflectionUtils.get("properties", assistant);
			info.dsJndi = props.getProperty("DBJNDINAME");
			info.dbUrl = props.getProperty("DBURL");
			info.dbSchema = props.getProperty("DBSCHEMA");
			info.connectionStrategy = props.getProperty("connectionStrategy");
			info.batchProviderClass = props.getProperty("JWF_JDBCPROVIDER_CLASS");
		} catch (RuntimeException e) {
			String msg = StringUtils.concat(
					"Inspection failed " + ExceptionUtils.getThrowableStackTrace(e),
					"Salvaged info: \n" + info.toString());
			return msg;
		}
		
		return info.toString();
	}
	
	/**
	 * Finds and returns the manager names of all managers that use
	 * jdbc resources for the default Bo2 deployment. 
	 * 
	 * @return The manager names of all managers that use jdbc resources.
	 */
	public String[] getJdbcManagers() {
		return getJdbcManagers(Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}
	
	/**
	 * Finds and returns the manager names of all managers that use
	 * jdbc resources. 
	 * 
	 * @param deplPath
	 *        Path of the Bo2 deployment properties
	 * 
	 * @return The manager names of all managers that use jdbc resources.
	 */
	public String[] getJdbcManagers(String deplPath) {
		try {
			Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
			List<String> managers = new ArrayList<String>();
			String path = bo2Depl.getDeploymentBean().getPathToManagersList();
			String[] managerPaths = StreamUtils.readResourceFile(path);
			for(String managerPath : managerPaths) {
				Properties p = CollectionUtils.readEnhancedProperties(managerPath);
				String name = p.getProperty("resourceWrappersManagerName"); //$NON-NLS-1$
				if(!StringUtils.isNullOrBlank(p.getProperty("DBURL"))) { //$NON-NLS-1$
					managers.add(name);
				}
			}
			return managers.toArray(new String[]{});
		}catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	/**
	 * Gets the configuration of a specified manager. A defensive copy of the
	 * real configuration is supplied.
	 * 
	 * @param managerName
	 * @param deplPath
	 * @return Properties with configuration.
	 */
	public Properties getPropertiesOfManager(String managerName, String deplPath) {
		try {
			String managerNameLocal = getRealManagerNameForAlias(managerName, deplPath);
			Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
			String path = bo2Depl.getDeploymentBean().getPathToManagersList();
			String[] managerPaths = StreamUtils.readResourceFile(path);
			for(String managerPath : managerPaths) {
				Properties p = CollectionUtils.readEnhancedProperties(managerPath);
				String name = p.getProperty("resourceWrappersManagerName"); //$NON-NLS-1$
				if(name.equals(managerNameLocal)) {
					return new Properties(p);
				}
			}
		}catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return null;
	}
	
	/**
	 * Gets the configuration of a specified manager.
	 * 
	 * @param managerName
	 * 
	 * @return Properties with configuration.
	 */
	public Properties getPropertiesOfManager(String managerName) {
		return getPropertiesOfManager(managerName, Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}
	
	/**
	 * @param managerName
	 * @return Returns a short summary of configuration data for the specified manager name
	 *         in the current default deployment.
	 */
	public String getInfoForManager(String managerName) {
		return getInfoForManager(Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH, managerName);
	}
	
	/**
	 * Gets the real manager name for an alias in the specified Bo2 deployment. 
	 * If the name supplied is not an alias, it returns the name. If the name 
	 * does not exist, null is returned.
	 * 
	 * @param managerName
	 * @param deplPath
	 * 
	 * @return Concrete manager name.
	 */
	public String getRealManagerNameForAlias(String managerName, String deplPath) {
		if(getManagerNames().contains(managerName)) {
			return managerName;
		}
		
		String pathToManagerAliases = CollectionUtils.readProperties(deplPath).getProperty("pathToManagerAliases"); //$NON-NLS-1$
		Properties aliases = CollectionUtils.readProperties(pathToManagerAliases);
		
		if(!aliases.containsKey(managerName) && !aliases.containsValue(managerName)) {
			throw new RuntimeException(managerName + " does not exist on Bo2 deployment: " + deplPath); //$NON-NLS-1$
		}
		
		String currentAlias = managerName;
		while(aliases.getProperty(currentAlias)!=null) {
			currentAlias = aliases.getProperty(currentAlias);
		}
		return currentAlias;
	}

	/**
	 * Gets the real manager name for an alias. If the name supplied is not
	 * alias, it returns the name. If the name does not exist, null is returned.
	 * The managers of the default Bo2 deployment are considered. 
	 * 
	 * @param managerName
	 * @return Concrete manager name.
	 */
	public String getRealManagerNameForAlias(String managerName) {
		return getRealManagerNameForAlias(managerName, Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}
	
	/**
	 * Returns the manager names of the default deployment. This does not include aliases.
	 * 
	 * @return Manager names.
	 */
	public List<String> getManagerNames() {
		return getManagerNames(Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}
	
	/**
	 * Returns the manager names of a deployment. This does not include aliases.
	 * 
	 * @param deplPath
	 * @return Manager names.
	 */
	public List<String> getManagerNames(String deplPath) {
		List<String> managers = new ArrayList<String>();
		Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
		String path = bo2Depl.getDeploymentBean().getPathToManagersList();
		String[] managerPaths;
		try {
			managerPaths = StreamUtils.readResourceFile(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for(String managerPath : managerPaths) {
			Properties p = CollectionUtils.readEnhancedProperties(managerPath);
			String name = p.getProperty("resourceWrappersManagerName"); //$NON-NLS-1$
			managers.add(name);
		}
		return managers;
	}
	
	/**
	 * Factory method.
	 * @return Bo2DeploymentInfoUtility instance.
	 */
	public static Bo2DeploymentInfoUtility get() {
		return new Bo2DeploymentInfoUtility();
	}
	
	/**
	 * Hidden 
	 */
	private Bo2DeploymentInfoUtility() {
		super();
	}
	
	/**
	 * Info wrapper class.
	 */
	@SuppressWarnings("javadoc")
	private static class Bo2DeploymentInfo {
		
		private static final String NOT_APPLICABLE = "N/A"; //$NON-NLS-1$
		private static final String FAILED = "failed to inspect"; //$NON-NLS-1$
		
		private String dbUrl = FAILED;
		private String dsJndi = FAILED;
		private String dbSchema = FAILED;
		private String transactionManager = FAILED;
		private String connectionStrategy = FAILED;
		private String environment = FAILED;
		private String manager = FAILED;
		private String batchProviderClass = FAILED;
		
		/**
		 * Factory.
		 * @return a new Bo2DeploymentInfo instance.
		 */
		static Bo2DeploymentInfo newInstance() {
			return new Bo2DeploymentInfo();
		}
		
		@Override
		@SuppressWarnings("nls")
		public String toString() {
			String info = StringUtils.concat(
					"*******************************************",
					"\nInfo for manager " + manager,
					"\n-----------------------------------------",
					"\n[DEPLOYMENT] Environment: " + environment,
					"\n[DEPLOYMENT] TransactionManager: " + transactionManager,
					"\n[MANAGER] Database schema: " + Utils.notNull(dbSchema, NOT_APPLICABLE),
					"\n[MANAGER] Database URL: " + Utils.notNull(dbUrl, NOT_APPLICABLE),
					"\n[MANAGER] Datasource JNDI: " + Utils.notNull(dsJndi, NOT_APPLICABLE),
					"\n[MANAGER] Connection strategy: " + Utils.notNull(connectionStrategy, NOT_APPLICABLE),
					"\n[Manager] Batch provider class: " + Utils.notNull(batchProviderClass, NOT_APPLICABLE),
					"\n*******************************************\n");
			
			return info;
		}
	}

}
