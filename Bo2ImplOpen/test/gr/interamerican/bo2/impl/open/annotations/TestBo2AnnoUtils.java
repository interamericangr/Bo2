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
package gr.interamerican.bo2.impl.open.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.test.def.posamples.Invoice;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2AnnoUtils}.
 */
public class TestBo2AnnoUtils {
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withAnnotatedClass() {
		String name = Bo2AnnoUtils.getManagerName(Sample.class);
		assertEquals("NAME", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withAnnotatedSuperClass() {
		String name = Bo2AnnoUtils.getManagerName(SubSample.class);
		assertEquals("NAME", name); //$NON-NLS-1$
	}

	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withAnnotatedPackage() {
		String name = Bo2AnnoUtils.getManagerName(Invoice.class);
		assertEquals("LOCALDB", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withImplementedInterface() {
		String name = Bo2AnnoUtils.getManagerName(Impl.class);
		assertEquals("INTER", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withAnnotatedTheSuperInterface() {
		String name = Bo2AnnoUtils.getManagerName(ExtendedInter.class);
		assertEquals("INTER", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withClassThatHasAnnotatedTheSuperInterface() {
		String name = Bo2AnnoUtils.getManagerName(Impl2.class);
		assertEquals("INTER", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withClassThatHasAnnotatedTheSuperClass() {
		String name = Bo2AnnoUtils.getManagerName(NotAnnotatedPo.class);
		assertEquals("POMAN", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withReannotatedClass() {
		String name = Bo2AnnoUtils.getManagerName(ReAnnotatedPo.class);
		assertEquals("OTHERPO", name); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetKeyProperties() {
		String[] sample = Bo2AnnoUtils.getKeyProperties(Sample.class);
		assertNull(sample);
		String[] inter = Bo2AnnoUtils.getKeyProperties(Inter.class);
		assertEquals(2, inter.length); 
		assertEquals(inter[0], "name"); //$NON-NLS-1$
		assertEquals(inter[1], "time"); //$NON-NLS-1$
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withClassThatHasNoManager() {
		String name = Bo2AnnoUtils.getManagerName(NoPoOrWorker.class);
		assertNull(name);
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withBaseClass1() {
		String name = Bo2AnnoUtils.getManagerName(PersistentObject.class);
		assertNull(name);
	}
	
	/**
	 * tests getManagerName
	 */
	@Test
	public void testGetManagerName_withBaseClass2() {
		String name = Bo2AnnoUtils.getManagerName(Worker.class);
		assertNull(name);
	}
	
	/**
	 * Tests copyManagerName()
	 */
	@Test
	public void testCopyManagerName() {
		String before = Bo2AnnoUtils.getManagerName(SomeWorker.class);
		String impl2Manager = Bo2AnnoUtils.getManagerName(Impl2.class);
		Assert.assertEquals(impl2Manager, before);
		Bo2AnnoUtils.copyManagerName(ReAnnotatedPo.class, SomeWorker.class);
		String after = Bo2AnnoUtils.getManagerName(SomeWorker.class);
		String expected = Bo2AnnoUtils.getManagerName(ReAnnotatedPo.class);
		Assert.assertEquals(expected, after);
	}
	
	/**
	 * Tests 
	 */
	@Test
	public void testGetParameterNames_withClassAnnotated() {
		String[] actuals = Bo2AnnoUtils.getParameterNames(Impl.class);
		@SuppressWarnings("nls")
		String[] expecteds = {"a", "b"};
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Tests 
	 */
	@Test
	public void testGetParameterNames_withSuperClassAnnotated() {
		String[] actuals = Bo2AnnoUtils.getParameterNames(SubImpl.class);
		@SuppressWarnings("nls")
		String[] expecteds = {"a", "b"};
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Tests 
	 */
	@Test
	public void testGetParameterNames_withClassAndSuperClassAnnotated() {
		String[] actuals = Bo2AnnoUtils.getParameterNames(OverridenSubImpl.class);
		@SuppressWarnings("nls")
		String[] expecteds = {"b", "a"};
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	
	
	/**
	 * Sample class.
	 */
	@SuppressWarnings("serial")
	@ManagerName("NAME")
	abstract class Sample implements PersistentObject<Integer> {/* empty */}
	
	/**
	 * Sample sub class.
	 */	
	@SuppressWarnings("serial")
	abstract class SubSample extends Sample {/* empty */}
	
	/**
	 * Sample worker class.
	 */
	@ParametersOrder({"a","b"})
	class Impl implements Inter {
		public void open() throws DataException {/*empty*/}
		public void close() throws DataException {/*empty*/}
		public void init(Provider parent) throws InitializationException {/*empty*/}
		public Provider getProvider() {return null;}
	}
	
	/**
	 * Worker class that inherits its parameters order by its superclass.
	 */
	class SubImpl extends Impl {
		/* empty */
	}
	
	/**
	 * Worker class that inherits its parameters order by its superclass.
	 */
	@ParametersOrder({"b,a"})
	class OverridenSubImpl extends Impl {
		/* empty */
	}
	
	
	
	/**
	 * Sample class.
	 */
	class Impl2 implements ExtendedInter {
		public void open() throws DataException {/*empty*/}
		public void close() throws DataException {/*empty*/}
		public void init(Provider parent) throws InitializationException {/*empty*/}
		public Provider getProvider() {return null;}
	}
	
	/**
	 * Class that has a ManagerName annotation, but isn't a Po
	 * or Worker.
	 */
	@ManagerName("SOMEMAN")
	class NoPoOrWorker {/*empty*/}
	
	/**
	 * Sample interface.
	 */
	@ManagerName("INTER")
	@KeyProperties("name,time")
	interface Inter extends Worker {/* empty */}
	
	/**
	 * Interface that extends an annotated.
	 */
	interface ExtendedInter extends Inter{/*empty*/}
	
	/**
	 * Abstract PO with manager name.
	 */
	@SuppressWarnings("serial")
	@ManagerName("POMAN")
	abstract class AnnotatedPo implements PersistentObject<Integer> {
		/**
		 * Key.
		 */
		Integer key;
	}
	/**
	 * Po without manager name that inherits its manager
	 * name from its superclass.
	 */
	@SuppressWarnings("serial")
	class NotAnnotatedPo extends AnnotatedPo {
		public Integer getKey() {return key;}
		public void setKey(Integer key) {this.key=key;}
		public void tidy() {/* empty*/ 	}		
	}
	/**
	 * Po with manager name who'w supertype has a different manager.
	 */
	@SuppressWarnings("serial")
	@ManagerName("OTHERPO")
	class ReAnnotatedPo extends NotAnnotatedPo {
		/* empty */
	}
	
	/**
	 * This is used to test copyManagerName()
	 */
	class SomeWorker extends Impl2 {
		/*empty*/
	}

}
