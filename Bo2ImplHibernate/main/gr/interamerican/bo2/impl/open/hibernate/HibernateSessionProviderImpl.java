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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ProviderUtils;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.impl.open.po.PoUtils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of{@link HibernateSessionProvider} that will load a
 * hibernate.cfg.xml and a set of hibernate mapping files and will create a
 * static SessionFactory in order to provide Sessions.
 * 
 * The Session is passed a Jdbc Connection. Information about the driver, the
 * connection, and hibernate configuration and mappings is provided in a
 * properties object.
 * 
 * Note that the supplied hibernate.cfg.xml does not need to provide connection
 * related properties (e.g. connection.driver_class, connection.url,
 * connection.username, connection.password).
 * 
 * This is the first class that is loaded for Bo2 hibernate support.
 * Any initialization of Bo2 necessary for Hibernate must take place here.
 * 
 */
public class HibernateSessionProviderImpl
extends JdbcConnectionProviderImpl
implements HibernateSessionProvider {
	
	static {
		Bo2ImplHibernateInitializer.initialize();
	}
	
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(HibernateSessionProviderImpl.class);
	
	/**
	 * Input properties Property name for hibernate.cfg.xml resource path
	 */
	private static final String HIBERNATE_CFG_XML = "HIBERNATE_CFG_XML"; //$NON-NLS-1$
	
	/**
	 * Input properties Property name for hibernate interceptor.
	 */
	private static final String SESSION_INTERCEPTOR = "SESSION_INTERCEPTOR"; //$NON-NLS-1$
	
	/**
	 * Input properties Property name for hibernate mappings indexer.
	 * The indexer lists a number of txt files that, in turn, list class
	 * paths to hbm.xml files.
	 * <br/>
	 * This file, as well as the txt files it points to may, or may not exist.
	 */
	private static final String HIBERNATE_MAPPINGS = "HIBERNATE_MAPPINGS"; //$NON-NLS-1$
	
	/**
	 * Hibernate SessionFactory used to create sessions
	 */
	private SessionFactory sessionFactory;
		
	/**
	 * the hibernate session this provider provides
	 */
	private Session session;
	
	/**
	 * list of loaded entities in this session.
	 */
	Set<Object> entities = new HashSet<Object>();
	
	/**
	 * list of entities that should be evicted before flush().
	 */
	Set<Object> excluded = new HashSet<Object>();
	
	/**
	 * Flush policy.
	 */
	private FlushStrategy flushStrategy = FlushStrategy.SESSION;
	
	/**
	 * Creates a new HibernateSessionProviderImpl object. 
	 *
	 * @param properties
	 * @throws InitializationException 
	 */
	public HibernateSessionProviderImpl(Properties properties) 
	throws InitializationException {
		super(properties);
		openSession();
	}
	
	@Override
	protected void parseProperties() throws InitializationException {	
		super.parseProperties();
		String pathToCfg = ProviderUtils.getMandatoryProperty(getProperties(), HIBERNATE_CFG_XML);
		String dbSchema = ProviderUtils.getMandatoryProperty(getProperties(), KEY_DBSCHEMA);
		String sessionInterceptor = getProperties().getProperty(SESSION_INTERCEPTOR);
		String hibernateMappingsPath = getProperties().getProperty(HIBERNATE_MAPPINGS);
		sessionFactory = HibernateConfigurations.getSessionFactory(pathToCfg, dbSchema, sessionInterceptor, hibernateMappingsPath);
	}
	
	public Session getHibernateSession() {		
		return session;
	}
	
	/**
	 * Opens the session.
	 * 
	 * @throws InitializationException
	 */
	private void openSession() throws InitializationException {
		try {
			session = sessionFactory.openSession(getConnection());
		} catch (HibernateException e) {
			throw new InitializationException(e);
		}
	}
	
	/**
	 * Closes the session.
	 * 
	 * @throws DataException
	 */
	private void closeSession() throws DataException {
		try {
			if (session != null && session.isOpen()) {
				session.disconnect();
				session.close();
				session = null;
			}
		} catch (HibernateException e) {
			throw new DataException(e);
		}
	}

	@Override
	public void close() throws DataException {
		closeSession();
		super.close();	
		entities.clear();
		excluded.clear();
		if(logger.isDebugEnabled()) {
			HibernateBo2Utils.logSessionFactoryStatistics(sessionFactory);
		}
	}

	public void flush(Object object) {
		flushStrategy.flush(object, session, entities, excluded);
	}
	
	public void register(Object object) {		
		entities.add(object);
	}

	public void unregister(Object object) {
		entities.remove(object);
		PoUtils.setDetachStrategy(object, null);
	}

	public void setExcluded(Object object) {
		excluded.add(object);
	}
	
	public void setNotExcluded(Object object) {
		excluded.remove(object);
	}
	
	public void setFlushStrategy(FlushStrategy flushStrategy) {
		this.flushStrategy = flushStrategy;
	}

}
