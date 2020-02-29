package gr.interamerican.bo2.impl.open.runtime.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.monitor.MonitoringConstants;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import java.util.Properties;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for {@link BatchProcessUtility}.
 */
public class TestBatchProcessUtility {

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("rawtypes") Session session = mock(Session.class);
		BatchProcessUtility bpu = new BatchProcessUtility(session);
		assertEquals(session, bpu.session);
	}

	/**
	 * Tests the getFactory(properties).
	 */
	@Test
	public void testGetFactory_default() {
		@SuppressWarnings("rawtypes") Session session = mock(Session.class);
		BatchProcessUtility bpu = new BatchProcessUtility(session);
		Properties properties = new Properties();
		BatchProcessParmsFactory factory = bpu.getFactory(properties);
		assertNotNull(factory);
	}

	/**
	 * Tests the getFactory(properties).
	 */
	@Test
	public void testGetFactory_byProperty() {
		String className = "foo"; //$NON-NLS-1$
		ObjectFactory of = mock(ObjectFactory.class);
		BatchProcessParmsFactory expected = mock(BatchProcessParmsFactory.class);
		when(of.create(className)).thenReturn(expected);
		Factory.setCurrentFactory(of);

		@SuppressWarnings("rawtypes") Session session = mock(Session.class);
		BatchProcessUtility bpu = new BatchProcessUtility(session);
		Properties properties = new Properties();
		properties.setProperty(BatchProcessParmNames.PARAMETERS_FACTORY_CLASS, className);
		BatchProcessParmsFactory actual = bpu.getFactory(properties);
		assertEquals(expected, actual);

		Factory.resetCurrentFactory();
	}

	/**
	 * Tests the createMonitoringOperation(classname, properties).
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMonitoringOperation() {
		String className = "foo"; //$NON-NLS-1$
		ObjectFactory of = mock(ObjectFactory.class);
		MonitoringOperation mo = mock(MonitoringOperation.class);
		when(of.create(className)).thenReturn(mo);
		Factory.setCurrentFactory(of);

		BatchProcessUtility bpu = new BatchProcessUtility(mock(Session.class));
		MonitoringOperation actual = bpu.createMonitoringOperation(className, new Properties());
		assertEquals(mo, actual);

		Factory.resetCurrentFactory();
	}

	/**
	 * Tests the createMonitoringOperation(classname, properties).
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMonitoringOperation_withMoMo() {
		String className = "foo"; //$NON-NLS-1$
		ObjectFactory of = mock(ObjectFactory.class);
		MoMo mo = mock(MoMo.class);
		when(of.create(className)).thenReturn(mo);
		Factory.setCurrentFactory(of);

		BatchProcessUtility bpu = new BatchProcessUtility(mock(Session.class));
		Properties p = new Properties();
		MonitoringOperation actual = bpu.createMonitoringOperation(className, p);
		assertEquals(mo, actual);
		verify(mo, times(1)).beModified(p);

		Factory.resetCurrentFactory();
	}


	/**
	 * Tests the getMonitoringOperationClasses(properties).
	 */
	@Test
	public void testGetMonitoringOperationClasses() {
		Properties properties = new Properties();
		String op1 = "foo1"; //$NON-NLS-1$
		String op2 = "foo2"; //$NON-NLS-1$
		properties.setProperty(MonitoringConstants.MONITOR_KEY, op1 + StringConstants.COMMA + op2);
		BatchProcessUtility bpu = new BatchProcessUtility(mock(Session.class));
		Set<String> set = bpu.getMonitoringOperationClasses(properties);

		assertEquals(2, set.size());
		assertTrue(set.contains(op1));
		assertTrue(set.contains(op2));
		set = bpu.getMonitoringOperationClasses(new Properties());
		assertEquals(0, set.size());
	}

	/**
	 * Modifiable by properties MonitoringOperation.
	 */
	interface MoMo extends MonitoringOperation<Object>, ModifiableByProperties {
		// empty
	}
}
