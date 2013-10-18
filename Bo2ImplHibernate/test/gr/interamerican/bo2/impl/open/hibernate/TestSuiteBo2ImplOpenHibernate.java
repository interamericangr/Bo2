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

import gr.interamerican.bo2.arch.enums.TestSuiteBo2ArchEnums;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.hibernate.inheritance.TestSuiteBo2ImplOpenHibernateInheritance;
import gr.interamerican.bo2.impl.open.hibernate.operations.TestSuiteBo2ImplOpenHibernateOperations;
import gr.interamerican.bo2.impl.open.hibernate.types.TestSuiteBo2ImplOpenHibernateTypes;
import gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze.TestSuiteBo2ImplOpenHibernateUtilsReflectAnalyze;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open.hibernate</code>.
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{	
		
		TestHibernateConfigurations.class,
		TestHibernateSessionProviderImpl.class,
		TestFlushStrategy.class,		
		TestAbstractHibernateWorker.class,
		TestAbstractHibernatePersistenceUtility.class,
		TestGenericHibernatePersistenceUtility.class,
		TestGenericHibernatePersistenceWorker.class,
		TestScenarioInvoiceHibernateOperations.class,
		TestScenarioWorkerWithJdbcAndHibernate.class,
		TestAbstractHibernateQuery.class,
		TestAbstractCriteriaQuery.class,
		TestAbstractHqlQuery.class,
		TestAbstractHibernateDetachStrategy.class,
		TestSmallTableLoaderQuery.class,		
		TestHibernateBo2Utils.class,
		TestPersistenceWorkerFactoryImpl.class,
		TestProxySessionPropagation.class,
		TestRefreshMode.class,
		
		TestSuiteBo2ImplOpenHibernateOperations.class,
		TestSuiteBo2ImplOpenHibernateTypes.class,
		TestSuiteBo2ImplOpenHibernateInheritance.class,
		TestSuiteBo2ImplOpenHibernateUtilsReflectAnalyze.class,
		
		TestSuiteImplOpenTestsForHibernate.class,
		
		TestSuiteBo2ArchEnums.class
	}
)
public class TestSuiteBo2ImplOpenHibernate {
	
	
	/**
	 * Initialization of tests.
	 */
	@SuppressWarnings("nls")
	@BeforeClass
	public static void initializeSuite() {
		Bo2AnnoUtils.setManagerName(User.class, "LOCALDB");
		try {
			Cache<Long> dummy = new CacheImpl<Long>();
			CacheRegistry.registerCache("cache", dummy, Long.class); 
		} catch (RuntimeException e) {
			/* if this cache exists, then it is no problem, continue */
		}
	}
}


