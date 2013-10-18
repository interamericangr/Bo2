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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.beans.UserIdBean;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.def.samples.enums.Sex;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class TestAbstractHqlQuery {
	
	/**
	 * the name of the cache used by the EntryUserType on this test.
	 */
	public static final String CACHE_NAME = "cache"; //$NON-NLS-1$
	
	/**
	 * sample role
	 */
	private static CompanyRole role = new CompanyRole(1L, null, UtilityForBo2Test.getExistingCompanyUserRoleCd(), "user"); //$NON-NLS-1$
	
	/**
	 * sets up the cache
	 */
	@BeforeClass
	public static void setup() {
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
		if (cache==null) {
			cache = new CacheImpl<Long>();			
			CacheRegistry.registerCache(CACHE_NAME, cache, Long.class);		
		}	
		cache.put(role);
	}
	
	/**
	 * tests reading a user
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReadUser() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				ReadUserHQLQuery query = new ReadUserHQLQuery();
				query.setUserId(UtilityForBo2Test.getExistingUserId());
				query.init(this.getProvider());
				query.open();
				query.execute();
				if (query.next()) {
					User result = query.getEntity();
					assertNotNull(result);
					assertEquals(UtilityForBo2Test.getExistingUserId(), result.getId());
				} else {
					fail("No user returned by query"); //$NON-NLS-1$
				}
				assertFalse(query.next());
				query.close();
			}
		}.execute();
	}
	
	/**
	 * tests reading a {@link CompanyUser} based on a given {@link CompanyRole}
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReadCompanyUserWithRole() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {

			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				ReadCompanyUserWithRoleHqlQuery query = new ReadCompanyUserWithRoleHqlQuery();
				
				query.setRole(role);
				query.init(this.getProvider());
				query.open();
				query.execute();
				if (query.next()) {
					CompanyUser result = query.getEntity();
					assertNotNull(result);
					assertEquals(UtilityForBo2Test.getExistingCompanyUserRoleCd(), result.getCompanyRole().getCode());
				}
			}
			
		}.execute();
	}
	
	/**
	 * tests reading a {@link CompanyUser} based on a given {@link Sex}
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReadCompanyUserWithSex() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {

			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				ReadCompanyUserWithSexHqlQuery query = new ReadCompanyUserWithSexHqlQuery();
				
				Sex sex = Sex.MALE;
				
				query.setSex(sex);
				query.init(this.getProvider());
				query.open();
				query.execute();
				if (query.next()) {
					CompanyUser result = query.getEntity();
					assertNotNull(result);
					assertEquals(UtilityForBo2Test.getExistingCompanyUserSex(), result.getSex());
				}
			}
		}.execute();
	}
	
	/**
	 * tests reading a {@link CompanyUser} based on a given a {@link UserIdBean}
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReadCompanyUserWithJavaBean() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {

			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				BeanBasedReadUserHQLQuery query = new BeanBasedReadUserHQLQuery();
				
				UserIdBean bean = Factory.create(UserIdBean.class);
				bean.setUserId(UtilityForBo2Test.getExistingUserId());
				
				query.setUserId(bean);
				query.init(this.getProvider());
				query.open();
				query.execute();
				if (query.next()) {
					User result = query.getEntity();
					assertNotNull(result);
					assertEquals(UtilityForBo2Test.getExistingUserId(), result.getId());
				}
			}
		}.execute();
	}
	
	/**
	 * Hibernate HQL query to read a user based on a role.
	 */
	@ManagerName("LOCALDB")
	private class ReadCompanyUserWithRoleHqlQuery
	extends AbstractHqlQuery<CompanyUser> {

		/**
		 * user role.
		 */
		@Parameter private CompanyRole companyRole;
		
		@Override
		protected String hql() {
			return "select u from CompanyUser u where u.companyRole = :companyRole"; //$NON-NLS-1$
		}
		
		/**
		 * Sets the role.
		 * 
		 * @param role
		 */
		public void setRole(CompanyRole role) {
			this.companyRole = role;
		}
	}
	
	/**
	 * Hibernate HQL query to read a user based on his/her sex.
	 */
	@ManagerName("LOCALDB")
	private class ReadCompanyUserWithSexHqlQuery
	extends AbstractHqlQuery<CompanyUser> {

		/**
		 * user role.
		 */
		@Parameter private Sex sex;
		
		@Override
		protected String hql() {
			return "select u from CompanyUser u where u.sex = :sex"; //$NON-NLS-1$
		}
		
		/**
		 * Sets the sex.
		 * 
		 * @param sex
		 */
		public void setSex(Sex sex) {
			this.sex = sex;
		}
	}
	
	/**
	 * Hibernate HQL query to read a user based on a userId.
	 */
	@ManagerName("LOCALDB")
	private class ReadUserHQLQuery 
	extends AbstractHqlQuery<User> {

		/**
		 * user id.
		 */
		private @Parameter Integer userId;

		@Override
		protected String hql() {
			return "select u from User u where u.key.id = :userId"; //$NON-NLS-1$
		}		
		/**
		 * Gets the user id.
		 * 
		 * @return Returns the user id.
		 */
		@SuppressWarnings("unused")
		public Integer getUserId() {
			return userId;
		}		
		/**
		 * Sets the user id.
		 * 
		 * @param userId
		 */
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
	}
	
	/**
	 * Hibernate HQL query to read a user based on a userId that is
	 * encapsulated in a java bean.
	 */
	@ManagerName("LOCALDB")
	private class BeanBasedReadUserHQLQuery 
	extends AbstractHqlQuery<User> {

		/**
		 * user id.
		 */
		
		@Parameter(isBean=true) 
		@Property UserIdBean userIdBean;

		@Override
		protected String hql() {
			return "select u from User u where u.key.id = :userId"; //$NON-NLS-1$
		}
		
		/**
		 * @param userIdBean
		 */
		public void setUserId(UserIdBean userIdBean) {
			this.userIdBean = userIdBean;
		}
		
		/**
		 * @return user id.
		 */
		@SuppressWarnings("unused")
		public UserIdBean getUserId() {
			return this.userIdBean;
		}
	} 
}
