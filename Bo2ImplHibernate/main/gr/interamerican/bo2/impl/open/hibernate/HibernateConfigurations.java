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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.hibernate.tuple.Bo2PojoEntityTuplizer;
import gr.interamerican.bo2.impl.open.hibernate.tuple.resolver.Bo2EntityNameResolver;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manages all hibernate configurations and their SessionFactory 
 * objects.
 */
public class HibernateConfigurations {
	
	/**
	 * Logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HibernateConfigurations.class);
	
	/**
	 * Schema property on a hibernate config.
	 */
	static final String SCHEMA_PROPERTY = "hibernate.default_schema"; //$NON-NLS-1$
	
	/**
	 * Static map that stores all session factories, mapped to the path
	 * of their configuration files.
	 */
	static Map<String, SessionFactory> sessionFactories = 
		new HashMap<String, SessionFactory>();
	
	/**
	 * Gets the session factory that corresponds to the specified configuration
	 * file.
	 * 
	 * @param pathToCfg
	 *        Path to the configuration resource file.
	 * @param dbSchema
	 *        Db schema.
	 * @param sessionInterceptor 
	 *        Hibernate session interceptor.
	 * @param hibernateMappingsPath
	 *        Path to file that lists files indexing hbm files this session factory
	 *        should be configured with.
	 * 
	 * @return Returns the session factory.
	 * 
	 * @throws InitializationException
	 *         If the creation of the session factory fails.
	 */
	public static synchronized SessionFactory getSessionFactory(
		String pathToCfg, String dbSchema, String sessionInterceptor, String hibernateMappingsPath) 
	throws InitializationException {
		SessionFactory sessionFactory = sessionFactories.get(key(pathToCfg, dbSchema));
		if (sessionFactory==null) {
			sessionFactory = createSessionFactory(pathToCfg, dbSchema, sessionInterceptor, hibernateMappingsPath);
			sessionFactories.put(key(pathToCfg, dbSchema), sessionFactory);
		}
		return sessionFactory;
	}
	
	/**
	 * Closes opened session factories. A web application should call this before
	 * unloading or stopping.
	 */
	public static void flushConfigurations() {
		for(Map.Entry<String, SessionFactory> e : sessionFactories.entrySet()) {
			e.getValue().close();
		}
		sessionFactories.clear();
	}
	
	/**
	 * Creates a SessionFactory.
	 * 
	 * @param pathToCfg
	 *        Path to the hibernate configuration file.
	 * @param dbSchema
	 *        Db schema.
	 * @param sessionInterceptor
	 *        Hibernate session interceptor.
	 * @param hibernateMappingsPath
	 *        Path to file that lists files indexing hbm files this session factory
	 *        should be configured with
	 *        
	 * @return Returns the session factory.
	 * 
	 * @throws InitializationException 
	 *         If the creation of the SessionFactory fails.
	 */
	@SuppressWarnings("nls")
	static SessionFactory createSessionFactory(String pathToCfg, String dbSchema, String sessionInterceptor, String hibernateMappingsPath) 
	throws InitializationException {
		try {
			Configuration conf = new Configuration();
			
			Interceptor interceptor = getInterceptor(sessionInterceptor);
			if(interceptor!=null) {
				conf.setInterceptor(interceptor);
			}
			
			conf.setProperty(SCHEMA_PROPERTY, dbSchema);
			
			List<String> hbms = getHibernateMappingsIfAvailable(hibernateMappingsPath);
			for(String entityMapping : hbms) {
				LOGGER.debug("Adding " + entityMapping + " to the session factory configuration.");
				conf.addResource(entityMapping);
			}
			
			conf.configure(pathToCfg);
			
			conf.getEntityTuplizerFactory().registerDefaultTuplizerClass(EntityMode.POJO, Bo2PojoEntityTuplizer.class);
			SessionFactory sessionFactory = conf.buildSessionFactory();
			((SessionFactoryImpl) sessionFactory).registerEntityNameResolver(Bo2EntityNameResolver.INSTANCE, EntityMode.POJO);
			sessionFactory.getStatistics().setStatisticsEnabled(true);
			return sessionFactory;
		} catch (HibernateException e) {
			throw new InitializationException(e);
		}
	}
	
	/**
	 * Get the interceptor instance if it is configured.
	 * @param sessionInterceptor
	 * @return Interceptor instance.
	 */
	private static Interceptor getInterceptor(String sessionInterceptor) {
		if(StringUtils.isNullOrBlank(sessionInterceptor)) {
			return null;
		}
		return ReflectionUtils.newInstance(sessionInterceptor);
	}
	
	/**
	 * @param pathToCfg
	 * @param dbSchema
	 * @return Returns a key for local caching.
	 */
	private static String key(String pathToCfg, String dbSchema) {
		return pathToCfg + StringConstants.UNDERSCORE + dbSchema;
	}
	
	/**
	 * Extracts a list of hibernate mapping files given a resource path.
	 * The resource path points to a file. The file is plain text and 
	 * each line contains one resource path that points to an hbm.xml file.
	 * If an indexed hdm.xml file does not exist on the classpath an error
	 * is logged, but no exception is thrown. This is meant to facilitate
	 * tests that need to create a SessionFactory and do not have all indexed
	 * hibernate mapping files in their running classpath.
	 * 
	 * @param managerHbmIndex
	 *        Resource path. Null tolerant.
	 *        
	 * @return Indexed hbm files resource paths.
	 */
	@SuppressWarnings("nls")
	static List<String> getHibernateMappingsIfAvailable(String managerHbmIndex) {
		List<String> hbms = new ArrayList<String>();
		
		if(StringUtils.isNullOrBlank(managerHbmIndex)) {
			return hbms;
		}
		
		String[] hbmPaths = null;
		try {
			hbmPaths = StreamUtils.readResourceFile(managerHbmIndex, true, true);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		
		if(hbmPaths==null) {
			String msg = "Non existant deployment hbm.xml index: " + managerHbmIndex;
			throw new RuntimeException(msg);
		}
		
		for(String hbmPath : hbmPaths) {
			String[] hbmContent = null;
			
			try {
				hbmContent = StreamUtils.readResourceFile(StringConstants.SLASH + hbmPath.trim(), true, true);
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			
			if(hbmContent==null) {
				String msg = StringUtils.concat(
						"Non existant hibernate mappings file: ",
						hbmPath,
						". This is acceptable for unit tests, but FATAL in every other case and should be investigated.");
				LOGGER.error(msg);
				continue;
			}
			
			hbms.add(hbmPath);

		}
		
		return hbms;
	}
	
}
