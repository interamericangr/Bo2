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
package gr.interamerican.bo2.impl.open.creation.test;


import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.impl.open.creation.test.conditions.StringIsClass;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.ConditionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.StringStartsWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.junit.Test;

/**
 * Base class for tests that collectively test the creation of a class
 * of Objects {@link PersistenceWorker}s.
 *
 * @see ObjectCreationTest
 * @see PersistenceWorkerCreationTest
 */
public abstract class AbstractCreationTest {
	
	/**
	 * Creates a new CreationTestBean object. 
	 *
	 * @param className the class name
	 * @throws ClassNotFoundException the class not found exception
	 */
	protected AbstractCreationTest(String className) throws ClassNotFoundException {
		super();
		this.clazz = Class.forName(className);
	}
	
	/**
	 * Class being tested.
	 */
	protected Class<?> clazz;
	
	/**
	 * Tests the creation of instances of clazz, where clazz 
	 * can be an interface.
	 */
	@Test
	public void test() {
		testClass(clazz);
	}
	
	/**
	 * Tests the creation of instances of clazz, where clazz can be an
	 * interface.
	 *
	 * @param type the type
	 */
	public abstract void testClass(Class<?> type);

	/**
	 * Fetches the test parameters.<br>
	 * In order to fetch them :
	 * <ul>
	 * <li>We read the contents of an input file pointed by the path parameter
	 * (sharp character and empty lines are omitted)
	 * <li>If a line is the full qualified name of a class then it is added on
	 * the result (i.e. java.lang.String)
	 * <li>If the above case does not apply for a line - then this searches for
	 * any classes in the current class-path that start with the given line and
	 * also pass the input condition. If any classes exist then they are added
	 * to the result. If there are no such classes then the line will be added
	 * to the result. (i.e. the line "java.lang" will add to the result all the
	 * contents of the package java.lang - and any sub-packages). But the line
	 * java.lang.Srting will insert java.lang.Srting to the result.
	 * <li>If the excluded parameter is not null - then the contents of the file
	 * pointed by that parameter are omitted from the end result
	 * </ul>
	 *
	 * @param path            Path that points to the input file
	 * @param excluded            Path that points to the excluded classes file (this can be
	 *            null)
	 * @param condition            Condition to apply when searching for classes
	 * @return Returns the test parameters.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected static Collection<?> parameters(String path, String excluded, Condition<String> condition)
			throws IOException {
		// Find All Classes and Possible Prefixes
		List<String> inputClasses = Arrays.asList(StreamUtils.readResourceFile(path, true, true));
		List<String> result = ConditionUtils.getSubset(inputClasses, new StringIsClass());
		// Search For Classes that start with the remaining contents of the
		// input file
		List<String> possiblePrefixes = new ArrayList<String>(inputClasses);
		possiblePrefixes.removeAll(result);
		if (!possiblePrefixes.isEmpty()) {
			// Find all the Classes in the current class-path
			List<String> allClasses = findAllClasses();
			// Remove all the used ones till now
			allClasses.removeAll(result);
			for (String possiblePrefix : possiblePrefixes) {
				// For each remaining line of the file find if there are classes
				// that start with it and also meet the input condition
				Set<String> prefixSet = new HashSet<String>();
				prefixSet.add(possiblePrefix);
				List<String> foundWithPrefix = ConditionUtils.getSubset(allClasses, new StringStartsWith(prefixSet));
				foundWithPrefix = ConditionUtils.getSubset(foundWithPrefix, condition);
				// If we have found any classes then use them
				if (!foundWithPrefix.isEmpty()) {
					result.addAll(foundWithPrefix);
					allClasses.removeAll(foundWithPrefix);
				} else {
					// If not - then add the wrong line on the final results
					result.add(possiblePrefix);
				}
			}
		}
		// If a file with excluded classes exists - exclude them from the result
		if (excluded != null) {
			List<String> excludedClasses = Arrays.asList(StreamUtils.readResourceFile(excluded, true, true));
			result.removeAll(excludedClasses);
		}
		return ArrayUtils.arrayAsListOfArrays(result.toArray());
	}

	/**
	 * Returns a List with all the class names currently on the class path.<br>
	 * The Class-path either points on system folders, or to jars files.
	 *
	 * @return List of all available class names
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static List<String> findAllClasses() throws IOException {
		List<String> result = new ArrayList<String>();
		String[] classpathEntries = System.getProperty("java.class.path").split(File.pathSeparator); //$NON-NLS-1$
		for (String classpathEntry : classpathEntries) {
			File file = new File(classpathEntry);
			if (!file.exists()) {
				continue;
			}
			if (file.isDirectory()) {
				searchDirectory(StringConstants.EMPTY, file, result);
			} else {
				try (JarInputStream is = new JarInputStream(new FileInputStream(file))) {
					JarEntry entry;
					while ((entry = is.getNextJarEntry()) != null) {
						addClassToResult(entry.getName(), result);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Searches the input directory for classes and adds them to the input list
	 * with the results.<br>
	 * This will search also any sub-directories of this directory with
	 * recursive calls.
	 * 
	 * @param packageName
	 *            Package Name (or empty if it is the default) for the directory
	 *            we are looking
	 * @param directory
	 *            Directory we are currently searching
	 * @param result
	 *            List with all the results
	 */
	static void searchDirectory(String packageName, File directory, List<String> result) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				searchDirectory(packageName + file.getName() + StringConstants.DOT, file, result);
			} else {
				addClassToResult(packageName + file.getName(), result);
			}
		}
	}

	/**
	 * Adds a candidate file on the result set - if it ends with ".class".<br>
	 * Omits the ".class" , and also replaces the SLASH character from the name
	 * (usually the Slash is used to describe the path inside 'jar' files, and
	 * also the {@link File#separator} character with Dot.
	 * 
	 * @param fullName
	 *            Candidate File to be added
	 * @param result
	 *            List with all the results
	 */
	static void addClassToResult(String fullName, List<String> result) {
		String suffix = ".class"; //$NON-NLS-1$
		int extIndex = fullName.lastIndexOf(suffix);
		if (extIndex > 0 && fullName.endsWith(suffix)) {
			String finalName = fullName.substring(0, extIndex);
			finalName = finalName.replace(StringConstants.SLASH, StringConstants.DOT);
			finalName = finalName.replace(File.separator, StringConstants.DOT);
			result.add(finalName);
		}
	}
}