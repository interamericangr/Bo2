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
import gr.interamerican.bo2.impl.open.jdbc.DriverConnectionStrategy;
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
	 * Gets the qualifier.
	 *
	 * @param managerName the manager name
	 * @return the default qualifier for the given manager
	 */
	public String getQualifier(String managerName) {
		Properties p = getPropertiesOfManager(managerName);
		return p.getProperty(JdbcConnectionProviderImpl.KEY_DBSCHEMA);
	}

	/**
	 * Gets the info for manager.
	 *
	 * @param deplPath the depl path
	 * @param managerName the manager name
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
					"Inspection failed " , ExceptionUtils.getThrowableStackTrace(e),
					"Salvaged info: ", System.lineSeparator(), info.toString());
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
				String name = p.getProperty(Bo2Utils.RW_MANAGER_NAME_KEY);
				boolean createsJdbcResources = 
						!StringUtils.isNullOrBlank(p.getProperty(DriverConnectionStrategy.KEY_DBURL)) || 
						!StringUtils.isNullOrBlank(p.getProperty("DBJNDINAME")); //$NON-NLS-1$
				if(createsJdbcResources) {
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
	 * @param managerName the manager name
	 * @param deplPath the depl path
	 * @return Properties with configuration.
	 */
	public Properties getPropertiesOfManager(String managerName, String deplPath) {
		try {
			String managerNameLocal = getRealManagerNameForAlias(managerName, deplPath);
			Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
			String path = bo2Depl.getDeploymentBean().getPathToManagersList();
			String[] managerPaths = StreamUtils.readResourceFile(path);
			for(String managerPath : managerPaths) {
				Properties p = getPropertiesOfManager0(managerPath);
				String name = p.getProperty(Bo2Utils.RW_MANAGER_NAME_KEY);
				if(name.equals(managerNameLocal)) {
					return p;
				}
			}
		}catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return null;
	}
	
	/**
	 * Returns the properties of a specific manager configuration file. This is a
	 * defensive copy, the user cannot alter the actual configuration using this.
	 * 
	 * @param managerPath
	 *        Resource path of manager factory configuration
	 *        
	 * @return Returns the properties of a specific manager configuration file.
	 */
	Properties getPropertiesOfManager0(String managerPath) {
		Properties props = CollectionUtils.readProperties(managerPath);
		
		Properties enhancedProps = CollectionUtils.readEnhancedProperties(managerPath);
		Properties result = new Properties();
		
		for (Object key : props.keySet()) {
			if(enhancedProps.get(key)!=null && !JdbcConnectionProviderImpl.KEY_DBPASS.equals(key)) {
				result.put(key, enhancedProps.get(key));
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the configuration of a specified manager.
	 *
	 * @param managerName the manager name
	 * @return Properties with configuration.
	 */
	public Properties getPropertiesOfManager(String managerName) {
		return getPropertiesOfManager(managerName, Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}
	
	/**
	 * Gets the info for manager.
	 *
	 * @param managerName the manager name
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
	 * @param managerName the manager name
	 * @param deplPath the depl path
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
	 * @param managerName the manager name
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
	 * @param deplPath the depl path
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
			String name = p.getProperty(Bo2Utils.RW_MANAGER_NAME_KEY);
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
	 * Hidden.
	 */
	private Bo2DeploymentInfoUtility() {
		super();
	}
	
	/**
	 * Gets the configuration summary.
	 *
	 * @return Returns the configuration summary of the default deployment.
	 */
	@SuppressWarnings("nls")
	public String getConfigurationSummary() {
		StringBuilder sb = new StringBuilder();
		String te = String.valueOf(Bo2.getDefaultDeployment().getDeploymentBean().getTargetEnvironment());
		String newLine = System.lineSeparator();
		sb.append("Environment: " + te + newLine);
		sb.append("--------------------------------------------" + newLine);
		
		List<String> managerNames = get().getManagerNames();
		for (String name : managerNames) {
			String real = get().getRealManagerNameForAlias(name);
			if(real.equals(name)) {
				sb.append(name + newLine);
				sb.append("--------------------------------------------" + newLine);
				Properties props = get().getPropertiesOfManager(name);
				sb.append(propsToString(props) + newLine);
				sb.append("--------------------------------------------" + newLine);
			} else {
				sb.append(name + " is an alias of " + real + newLine);
				sb.append("--------------------------------------------" + newLine);
			}
			
		}
		sb.append("System Variables " + newLine);
		sb.append("--------------------------------------------" + newLine);
		Properties properties = System.getProperties();
		properties.forEach((k, v) -> sb.append(k + "=" + v+ newLine));
		sb.append("--------------------------------------------" + newLine);
		
		
		return sb.toString();
	}
	
	/**
	 * Props to string.
	 *
	 * @param props the props
	 * @return String representation of Properties instance.
	 */
	@SuppressWarnings("nls")
	private String propsToString(Properties props) {
		String result = props.toString();
		result = result.replaceAll("\\{", "");
		result = result.replaceAll("\\}", "");
		result = result.replaceAll(", ", System.lineSeparator());
		return result;
	}
	
	
	/**
	 * Info wrapper class.
	 */
	private static class Bo2DeploymentInfo {
		
		/** The Constant NOT_APPLICABLE. */
		private static final String NOT_APPLICABLE = "N/A"; //$NON-NLS-1$
		
		/** The Constant FAILED. */
		private static final String FAILED = "failed to inspect"; //$NON-NLS-1$
		
		/** The db url. */
		private String dbUrl = FAILED;
		
		/** The ds jndi. */
		private String dsJndi = FAILED;
		
		/** The db schema. */
		private String dbSchema = FAILED;
		
		/** The transaction manager. */
		private String transactionManager = FAILED;
		
		/** The connection strategy. */
		private String connectionStrategy = FAILED;
		
		/** The environment. */
		private String environment = FAILED;
		
		/** The manager. */
		private String manager = FAILED;
		
		/** The batch provider class. */
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
					"*******************************************",System.lineSeparator(),
					"Info for manager " + manager,System.lineSeparator(),
					"-----------------------------------------",System.lineSeparator(),
					"[DEPLOYMENT] Environment: " + environment,System.lineSeparator(),
					"[DEPLOYMENT] TransactionManager: " + transactionManager,System.lineSeparator(),
					"[MANAGER] Database schema: " + Utils.notNull(dbSchema, NOT_APPLICABLE),System.lineSeparator(),
					"[MANAGER] Database URL: " + Utils.notNull(dbUrl, NOT_APPLICABLE),System.lineSeparator(),
					"[MANAGER] Datasource JNDI: " + Utils.notNull(dsJndi, NOT_APPLICABLE),System.lineSeparator(),
					"[MANAGER] Connection strategy: " + Utils.notNull(connectionStrategy, NOT_APPLICABLE),System.lineSeparator(),
					"[Manager] Batch provider class: " + Utils.notNull(batchProviderClass, NOT_APPLICABLE),System.lineSeparator(),
					"*******************************************",System.lineSeparator());
			
			return info;
		}
	}
}