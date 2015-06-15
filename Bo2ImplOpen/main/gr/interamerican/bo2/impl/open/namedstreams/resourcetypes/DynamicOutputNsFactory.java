package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 * factory for the dynamic {@link NamedStream} The idea behind the {@link DynamicOutputNsFactory} is
 * that the {@link NamedStreamDefinition#getUri()} denotes a base directory that all the files will
 * be placed. Each file in there will be named by {@link NamedStreamDefinition#getName()}+{serial
 * number}<br>
 * When factory creates a new file, it first creates a directory lock in order for other file
 * creations to wait for this lock to be lifted.
 */
public class DynamicOutputNsFactory extends WriteOnlyNsFactory {

	/**
	 * number of times it will test for the .lock
	 */
	private static final int RETRIES = 10;
	/**
	 * how much time will wait each retry
	 */
	private static final int SECONDS2WAIT = 1;
	/**
	 * .lock filename
	 */
	private static final String LOCK_FILE = ".lock"; //$NON-NLS-1$

	/**
	 * default constructor
	 */
	public DynamicOutputNsFactory() {
		super(StreamResourceEnum.DYNAMIC);
	}

	/**
	 * @param def
	 * @return the name of the lockfile
	 */
	String getLockDirName(NamedStreamDefinition def) {
		return def.getUri() + File.separator + LOCK_FILE;
	}

	/**
	 * tests for the .lock file and while it's on waits up to RETRIES*SEC2WAIT
	 *
	 * @param def
	 * @throws CouldNotCreateNamedStreamException
	 */
	void testForLockAndLock(NamedStreamDefinition def) throws CouldNotCreateNamedStreamException {
		int i = 0;
		String lockFilename = getLockDirName(def);
		File f = new File(lockFilename);
		for (i = 0; i < RETRIES; i++) {
			try {
				if (f.createNewFile()) {
					break;
				}
			} catch (IOException e) {
				throw new CouldNotCreateNamedStreamException(e);
			}
		}
		if (i == RETRIES) {// failed to acquire lock for directory
			throw new CouldNotCreateNamedStreamException(
					"Time out exceeded (" + (RETRIES * SECONDS2WAIT) + " secs) trying to acquire directory lock"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}


	/**
	 * unlocks the directory
	 *
	 * @param def
	 * @throws CouldNotCreateNamedStreamException
	 */
	void unlockDirectory(NamedStreamDefinition def) throws CouldNotCreateNamedStreamException {
		String lockFilename = getLockDirName(def);
		File f = new File(lockFilename);
		if (!f.exists()) {
			return;
		}
		if (!f.delete()) {
			throw new CouldNotCreateNamedStreamException(
					"Could not delete LOCK FILE DO IT MANUALLY"); //$NON-NLS-1$
		}
	}

	/**
	 * tests for the existence or creates the base directory.
	 *
	 * @param def
	 * @return the created directory.
	 * @throws CouldNotCreateNamedStreamException
	 */
	File testOrCreateDirectory(NamedStreamDefinition def) throws CouldNotCreateNamedStreamException {
		String uri = def.getUri();
		File f = new File(uri);
		if (f.exists()) {
			if (!f.isDirectory()) {
				throw new CouldNotCreateNamedStreamException(uri
						+ " exists and it's not a directory"); //$NON-NLS-1$
			}
		} else {
			if (!f.mkdir()) {
				throw new CouldNotCreateNamedStreamException("Could not create directory " + uri); //$NON-NLS-1$
			}
		}
		return f;
	}

	/**
	 * @param def
	 * @return
	 */
	String basicFileName(NamedStreamDefinition def) {
		return def.getUri() + File.separator + def.getName();
	}

	/**
	 * @param files
	 * @param def
	 * @return the name of the new file to be created
	 */
	String createNameForNewFile(String[] files, NamedStreamDefinition def) {
		return basicFileName(def) + files.length;
	}

	/**
	 * creates a new file descriptor in the directory. It wonm't create an actual file, but it can
	 * be passed to a method that will create the stream.
	 *
	 * @param dir
	 * @param def
	 * @return the created file.
	 */
	File createNewFileInDirectory(File dir, NamedStreamDefinition def) {
		File f = null;
		String[] files = dir.list(FileFilterUtils.andFileFilter(FileFilterUtils.fileFileFilter(),
				FileFilterUtils.prefixFileFilter(def.getName())));
		f = new File(createNameForNewFile(files, def));
		return f;
	}
	@Override
	protected OutputStream openOutputStream(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {
		File dir = testOrCreateDirectory(def);
		try {
			testForLockAndLock(def);
			return FileUtils.openOutputStream(createNewFileInDirectory(dir, def));
		} catch (IOException e) {
			throw new CouldNotCreateNamedStreamException(e);
		} finally {
			unlockDirectory(def);
		}
	}

}
