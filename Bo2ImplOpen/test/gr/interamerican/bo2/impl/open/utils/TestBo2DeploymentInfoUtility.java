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

import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Bo2DeploymentInfoUtility}.
 */
public class TestBo2DeploymentInfoUtility {
	
	/**
	 * Test subject.
	 */
	Bo2DeploymentInfoUtility subject = Bo2DeploymentInfoUtility.get();
	
	/**
	 * Test getInfoForManager()
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetInfoForManager() {
		String localDb = subject.getInfoForManager("LOCALDB");
		String main = subject.getInfoForManager("MAIN");
		
		Assert.assertTrue(localDb.contains("Connection strategy: "));
		Assert.assertTrue(main.contains("Connection strategy: "));
		
		String fs = subject.getInfoForManager("FS");
		Assert.assertTrue(fs.contains("N/A"));
		
		String localDb2 = subject.getInfoForManager(Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH, "LOCALDB");
		Assert.assertEquals(localDb, localDb2);
		
		String localDbCustomDepl = subject.getInfoForManager(
			Bo2.getDefaultDeployment().getDeploymentBean().getPathToSecondaryBatchDeployment(), "LOCALDB");
		Assert.assertTrue(localDbCustomDepl.contains("Connection strategy: "));
	}
	
	/**
	 * Test getJdbcManagers()
	 */
	@Test
	public void testGetJdbcManagers() {
		String[] managers = subject.getJdbcManagers();
		Assert.assertNotNull(managers);
		Assert.assertTrue(managers.length > 0);
	}
	
	/**
	 * Test getRealManagerNameForAlias
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetRealManagerNameForAlias() {
		String real = subject.getRealManagerNameForAlias("MAIN");
		Assert.assertEquals("LOCALDB", real);
		
		real = subject.getRealManagerNameForAlias("LOCALDB");
		Assert.assertEquals("LOCALDB", real);
	}
	
	/**
	 * Test getRealManagerNameForAlias
	 */
	@Test(expected=RuntimeException.class)
	public void testGetRealManagerNameForAlias_nonExistant() {
		subject.getRealManagerNameForAlias("Foobar"); //$NON-NLS-1$
	}
	
	/**
	 * test getPropertiesOfManager
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPropertiesOfManager() {
		Properties p = subject.getPropertiesOfManager("MAIN");
		String schema = p.getProperty("DBSCHEMA");
		Assert.assertEquals("TEST", schema);
	}
	
	/**
	 * Test getManagerNames
	 */
	@Test
	public void testGetManagerNames() {
		List<String> managers = subject.getManagerNames();
		Assert.assertTrue(managers.size()>0);
	}

}
