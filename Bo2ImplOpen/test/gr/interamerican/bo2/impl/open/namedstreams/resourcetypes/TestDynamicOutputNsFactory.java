package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test suite for {@link DynamicOutputNsFactory}
 */
public class TestDynamicOutputNsFactory {

	/**
	 * {@link DynamicOutputNsFactory}
	 */
	DynamicOutputNsFactory fac = (DynamicOutputNsFactory) StreamResourceEnum.DYNAMIC.factory;
	/**
	 * {@link NamedStreamDefinition}
	 */
	NamedStreamDefinition def = Factory.create(NamedStreamDefinition.class);

	/**
	 *
	 */
	private static String PATH = "/temp/tmp/"; //$NON-NLS-1$
	/**
	 *
	 */
	private static String BASE_FILENAME = "lala"; //$NON-NLS-1$

	/**
	 *
	 */
	@Before
	public void before() {
		def.setUri(PATH);
		def.setName(BASE_FILENAME);
	}

	/**
	 * @throws CouldNotCreateNamedStreamException
	 */
	@After
	public void after() throws CouldNotCreateNamedStreamException {
		fac.unlockDirectory(def);
	}

	/**
	 * delete left-overs
	 */
	@AfterClass
	public static void afterClass() {
		File dir = new File(PATH);
		String[] files = dir.list(FileFilterUtils.andFileFilter(FileFilterUtils.fileFileFilter(),
				FileFilterUtils.prefixFileFilter(BASE_FILENAME)));
		for (String string : files) {
			(new File(PATH + string)).delete();
		}
	}

	/**
	 * test case for {@link DynamicOutputNsFactory#testForLockAndLock(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test
	public void testTestForLockAndLock() throws CouldNotCreateNamedStreamException {
		fac.testForLockAndLock(def);
		Assert.assertTrue((new File(fac.getLockDirName(def)).exists()));
		Assert.assertTrue((new File(fac.getLockDirName(def)).isFile()));
	}

	/**
	 * test case for {@link DynamicOutputNsFactory#testForLockAndLock(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test(expected = CouldNotCreateNamedStreamException.class)
	public void testTestForLockAndLockWithException() throws CouldNotCreateNamedStreamException {
		fac.testForLockAndLock(def);
		fac.testForLockAndLock(def);
	}

	/**
	 * test case for {@link DynamicOutputNsFactory#testOrCreateDirectory(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test
	public void testTestOrCreateDirectory() throws CouldNotCreateNamedStreamException {
		String uri = def.getUri() + File.separator + UUID.randomUUID();
		def.setUri(uri);
		fac.testOrCreateDirectory(def);
		File f = new File(def.getUri());
		Assert.assertTrue(f.isDirectory());
		f.delete();
		Assert.assertFalse(f.exists());
	}

	/**
	 * test case for {@link DynamicOutputNsFactory#testOrCreateDirectory(NamedStreamDefinition)}
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test
	public void testCreateNewFileInDirectory() throws CouldNotCreateNamedStreamException {
		File f = fac.createNewFileInDirectory(fac.testOrCreateDirectory(def), def);
		Assert.assertFalse(f.exists());
	}

	/**
	 * @throws CouldNotCreateNamedStreamException
	 * @throws IOException
	 */
	@Test
	public void testCreateNewFileInDirectoryCustomDir() throws CouldNotCreateNamedStreamException,
	IOException {
		File f1 = null;
		File f2 = null;
		File dir = null;
		try {
			String uri = def.getUri() + File.separator + UUID.randomUUID();
			def.setUri(uri);
			fac.testOrCreateDirectory(def);
			f1 = fac.createNewFileInDirectory(fac.testOrCreateDirectory(def), def);
			Assert.assertTrue(f1.createNewFile());
			dir = new File(def.getUri());
			dir.deleteOnExit();
			String[] files = dir.list();
			Assert.assertEquals(1, files.length);
			f2 = fac.createNewFileInDirectory(fac.testOrCreateDirectory(def), def);
			Assert.assertTrue(f2.createNewFile());
			files = dir.list();
			Assert.assertEquals(2, files.length);
			Assert.assertTrue(f1.getName().contains(def.getName()));
			Assert.assertTrue(f2.getName().contains(def.getName()));
			String p1 = f1.getName().replace(def.getName(), StringConstants.EMPTY);
			int l1 = NumberUtils.string2Int(p1);
			String p2 = f2.getName().replace(def.getName(), StringConstants.EMPTY);
			int l2 = NumberUtils.string2Int(p2);
			Assert.assertTrue((l2 - l1) == 1);
		} finally {
			f1.delete();
			f2.delete();
			FileUtils.deleteDirectory(dir);
		}
	}

	/**
	 * test case for {@link DynamicOutputNsFactory#openOutputStream(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test
	public void testOpenOutputStream() throws CouldNotCreateNamedStreamException {
		OutputStream out = fac.openOutputStream(def);
		Assert.assertNotNull(out);
	}
}
