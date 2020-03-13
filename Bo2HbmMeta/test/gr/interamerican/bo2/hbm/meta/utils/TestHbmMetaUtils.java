package gr.interamerican.bo2.hbm.meta.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.hbm.meta.beans.Clazz;
import gr.interamerican.bo2.hbm.meta.beans.HibernateMapping;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * 
 */
public class TestHbmMetaUtils {
	
	/**
	 * Temporary folder for the test
	 */
	@Rule public TemporaryFolder tempFolder = new TemporaryFolder();
	
	/**
	 * File name.
	 */
	static final String FILE = "sample.xml"; //$NON-NLS-1$
	
	/**
	 * Test table
	 */
	static final String TABLE = "TESTTABLE"; //$NON-NLS-1$
	
	/**
	 * File path.
	 */
	private String filePath; 
	
	/**
	 * Before
	 */
	@Before
	public void before() {
		HibernateMapping hbm = new HibernateMapping();
		hbm.setAutoImport("true"); //$NON-NLS-1$
		Clazz clazz = new Clazz();
		clazz.setTable(TABLE);
		clazz.setCheck("true"); //$NON-NLS-1$
		hbm.getMetaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject().add(clazz);
		filePath = StringUtils.concat(tempFolder.getRoot().getAbsolutePath(), StringConstants.SLASH, FILE);
		HbmMetaUtils.marshal(filePath, hbm);
	}
	
	/**
	 * After
	 */
	@After
	public void after() {
		deleteFile();
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.hbm.meta.utils.HbmMetaUtils#marshal(java.lang.String, gr.interamerican.bo2.hbm.meta.beans.HibernateMapping)}.
	 */
	@Test
	public void testMarshal() {
		assertTrue(deleteFile());
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.hbm.meta.utils.HbmMetaUtils#unmarshal(String)}
	 */
	@Test
	public void testUnmarshal() {
		assertNotNull(HbmMetaUtils.unmarshal(filePath));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.hbm.meta.utils.HbmMetaUtils#getSubListOfClass(List, Class)}
	 */
	@Test
	public void testGetSubListOfClass() {
		HibernateMapping hbm = HbmMetaUtils.unmarshal(filePath);
		List<Clazz> clazzList = HbmMetaUtils.getSubListOfClass(hbm.getMetaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject(), Clazz.class);
		assertEquals(1, clazzList.size());
	}
	
	/**
	 * Test method for {@link gr.interamerican.bo2.hbm.meta.utils.HbmMetaUtils#getClazz(HibernateMapping)}
	 */
	@Test
	public void testGetClazz() {
		HibernateMapping hbm = HbmMetaUtils.unmarshal(filePath);
		assertNotNull(HbmMetaUtils.getClazz(hbm));
	}
	
	
	/**
	 * Deletes the file
	 * @return <code>true</code> id the file successfully deleted 
	 */
	boolean deleteFile() {
		File f = new File(filePath);
		return f.delete();
	}

}
