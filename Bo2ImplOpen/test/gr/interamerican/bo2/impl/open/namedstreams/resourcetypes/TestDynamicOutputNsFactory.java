package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;

/**
 * test suite for {@link DynamicOutputNsFactory}.
 */
public class TestDynamicOutputNsFactory {

	/** {@link DynamicOutputNsFactory}. */
	DynamicOutputNsFactory fac = (DynamicOutputNsFactory) StreamResourceEnum.DYNAMIC.factory;
	
	/** {@link NamedStreamDefinition}. */
	NamedStreamDefinition def = Factory.create(NamedStreamDefinition.class);

	/**
	 * 
	 */
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	/**
	 *
	 */
	private static String BASE_FILENAME = "lala"; //$NON-NLS-1$

	/**
	 * @throws IOException
	 *
	 */
	@Before
	public void before() throws IOException {
		File tempFolder = testFolder.newFolder("folder"); //$NON-NLS-1$
		def.setUri(tempFolder.getAbsolutePath());
		def.setName(BASE_FILENAME);
	}

	/**
	 * After.
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@After
	public void after() throws CouldNotCreateNamedStreamException {
		fac.unlockDirectory(def);
	}

	/**
	 * test case for
	 * {@link DynamicOutputNsFactory#testForLockAndLock(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test
	public void testTestForLockAndLock() throws CouldNotCreateNamedStreamException {
		fac.testForLockAndLock(def);
		Assert.assertTrue((new File(fac.getLockDirName(def)).exists()));
		Assert.assertTrue((new File(fac.getLockDirName(def)).isFile()));
	}

	/**
	 * test case for
	 * {@link DynamicOutputNsFactory#testForLockAndLock(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test(expected = CouldNotCreateNamedStreamException.class)
	public void testTestForLockAndLockWithException() throws CouldNotCreateNamedStreamException {
		fac.testForLockAndLock(def);
		fac.testForLockAndLock(def);
	}

	/**
	 * test case for
	 * {@link DynamicOutputNsFactory#testOrCreateDirectory(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
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
	 * test case for
	 * {@link DynamicOutputNsFactory#testOrCreateDirectory(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test
	public void testCreateNewFileInDirectory() throws CouldNotCreateNamedStreamException {
		File f = fac.createNewFileInDirectory(fac.testOrCreateDirectory(def), def);
		System.out.println(f.getAbsolutePath());
		System.out.println(f.exists());
		Assert.assertFalse(f.exists());
	}

	/**
	 * Test create new file in directory custom dir.
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testCreateNewFileInDirectoryCustomDir() throws CouldNotCreateNamedStreamException, IOException {
		File f1 = null;
		File f2 = null;
		File dir = null;
		try {
			String uri = def.getUri() + UUID.randomUUID();
			if (!uri.endsWith(File.separator)) {
				uri += File.separator;
			}
			def.setUri(uri);
			dir = fac.testOrCreateDirectory(def);
			// def.setUri(def.getUri());
			f1 = fac.createNewFileInDirectory(dir, def);
			Assert.assertTrue(f1.createNewFile());
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
	 * test case for
	 * {@link DynamicOutputNsFactory#openOutputStream(NamedStreamDefinition)}
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test
	public void testOpenOutputStream() throws CouldNotCreateNamedStreamException {
		OutputStream out = fac.openOutputStream(def);
		Assert.assertNotNull(out);
	}
}
