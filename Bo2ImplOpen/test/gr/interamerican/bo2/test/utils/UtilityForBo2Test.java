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
package gr.interamerican.bo2.test.utils;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.arch.utils.beans.SessionImpl;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.ObjectFactoryImpl;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsManagerImpl;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.def.samples.enums.Sex;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.Properties;

import org.mockito.Mockito;

/**
 * Utility class for tests.
 *
 */
public class UtilityForBo2Test {
	/**
	 * Path to deployment properties file for a non transactional Provider.
	 */
	public static final String BATCH_NO_TRAN = 
		"/gr/interamerican/bo2/deployparms/deployment_batch_notran.properties"; //$NON-NLS-1$
	
	/**
	 * Path to deployment properties file for a jta batch Provider.
	 */
	public static final String BATCH_JTA_TRAN = 
		"/gr/interamerican/bo2/deployparms/deployment_jta.properties"; //$NON-NLS-1$
	
	/**
	 * Object factory for PO tests that maps specific types used in the tests
	 * to concrete implementations.
	 * 
	 * @return returns the object factory.
	 */
	public static ObjectFactory getMappedFactory() {
		String path = "/gr/interamerican/rsrc/factories/potest/mapped.properties"; //$NON-NLS-1$
		Properties p = CollectionUtils.readProperties(path);
		return new ObjectFactoryImpl(p);		
	}
	
	/**
	 * Object factory for PO tests that maps specific types used in the tests
	 * to concrete implementations.
	 * 
	 * @return returns the object factory.
	 */
	public static ObjectFactory getNotMappedFactory() {
		String path = "/gr/interamerican/rsrc/factories/potest/nomappings.properties"; //$NON-NLS-1$
		Properties p = CollectionUtils.readProperties(path);
		return new ObjectFactoryImpl(p);		
	}
	
	
	/**
	 * @return Returns a Properties object with the properties
	 *         defined in the test.properties file.
	 */
	public static Properties getTestProperties() {
		return Bo2.getDefaultDeployment().getProperties();
	}
	
	/**
	 * Properties of a valid JdbcConnection.
	 * 
	 * @return Returns the properties for the JDBC connection.
	 */
	public static Properties getSampleJdbcProperties() {
		String path = "/gr/interamerican/bo2/deployparms/managers/localdb/of.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readEnhancedProperties(path);
		return properties;
	}
	
	/**
	 * Properties of a valid JdbcConnection in a JTA environment.
	 * 
	 * @return Returns the properties for the JDBC connection.
	 */
	public static Properties getSampleJdbcPropertiesForJta() {
		String path = "/gr/interamerican/bo2/deployparms/managers/localdbjta/of.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readEnhancedProperties(path);
		return properties;
	}
	
	/**
	 * Gets a sample NamedStreamsProvider for the tests.
	 * 
	 * @return Returns a NamedStreamsProvider.
	 */
	public static NamedStreamsManagerImpl getSampleNamedStreamsProvider() {
		return new NamedStreamsManagerImpl(getLocalFsProperties());
	}
	
	
	/**
	 * Gets the id of a user that exists in the database.
	 * 
	 * The unit tests assume that this user exists on the database,
	 * so a user with this id must exist in the database. 
	 * @return returns the id of an existing user.
	 */
	public static Integer getExistingUserId() {
		String string = getTestProperties().getProperty("EXISTING_USER"); //$NON-NLS-1$
		return Integer.parseInt(string);
	}
	
	/**
	 * Gets the {@link CompanyRole} code of a {@link CompanyUser}
	 * that exists in the database.
	 * 
	 * The unit tests assume that such a user exists on the database.
	 * 
	 * @return the company role code of the existing user
	 */
	public static Long getExistingCompanyUserRoleCd() {
		String string = getTestProperties().getProperty("EXISTING_COMPANY_USER_ROLECD"); //$NON-NLS-1$
		return Long.parseLong(string);
	}
	
	/**
	 * Gets the {@link Sex} of a {@link CompanyUser}
	 * that exists in the database.
	 * 
	 * The unit tests assume that such a user exists on the database.
	 * 
	 * @return the sex of the existing user
	 */
	public static Sex getExistingCompanyUserSex() {
		return Sex.MALE;
	}
	
	/**
	 * Gets the id of a user that does not exist in the database.
	 * 
	 * The unit tests assume that this user does not exist exists 
	 * in the database, so if a user with this id is found in the 
	 * database, the tests will fail.
	 *  
	 * @return returns the id of an existing user.
	 */
	public static int getNotExistingUserId() {
		String string = getTestProperties().getProperty("NOT_EXISTING_USER"); //$NON-NLS-1$
		return Integer.parseInt(string);
	}



	/**
	 * Gets the properties for localfs.
	 * 
	 * @return Returns the definition of localfs.
	 */
	public static Properties getLocalFsProperties() {
		String path = "/gr/interamerican/bo2/deployparms/managers/localfs/of.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readEnhancedProperties(path);	
		return properties;
	}
	
	/**
	 * Gets the Odf work directory.
	 * 
	 * @return Returns the Odf work directory.
	 */
	public static String getOdfWorkDirectory() {
		return getTestProperties().getProperty("odfWorkDirectory"); //$NON-NLS-1$
	}
	
	/**
	 * Gets the work directory for stream tests.
	 * 
	 * @return Returns the work directory for stream tests.
	 */
	public static String getStreamTestWorkDirectory() {
		return getTestProperties().getProperty("streamsWorkDirectory"); //$NON-NLS-1$
	}
	
	/**
	 * Full path for the filename in the test streams directory.
	 * 
	 * @param filename
	 * @return Returns a path for the filename.
	 */
	public static String getTestStreamPath(String filename) {
		return UtilityForBo2Test.getStreamTestWorkDirectory() + filename;
		
	}
	
	/**
	 * Gets the expected record in the text files used for test.
	 * 
	 * @return returns the record.
	 */
	public static String getRecordOfTestTextFile() {
		return "12345678901234567890"; //$NON-NLS-1$
	}
	
	
	/**
	 * Creates a new mock {@link Session} with a mock {@link User}
	 * that has the specified user id and sets it to {@link Bo2Session}.
	 * 
	 * @param userId
	 */
	public static void setCurrentUser(String userId) {
		@SuppressWarnings("unchecked")
		User<Object> user = Mockito.mock(User.class);
		Mockito.when(user.getUserId()).thenReturn(userId);
		SessionImpl<Object, Object> session = new SessionImpl<Object, Object>();
		session.setUser(user);
		Bo2Session.setSession(session);
	}
	
	/**
	 * Mocks the threadlocal transactionManager.
	 * 
	 * @param tmClass
	 *        The tmClass must have a default constructor. 
	 */
	public static void setCurrentTransactionManager(Class<? extends TransactionManager> tmClass) {
		Provider prov = Mockito.mock(Provider.class);
		Mockito.when(prov.getTransactionManager()).thenReturn(ReflectionUtils.newInstance(tmClass));
		Bo2Session.setProvider(prov);
	}
	
}
