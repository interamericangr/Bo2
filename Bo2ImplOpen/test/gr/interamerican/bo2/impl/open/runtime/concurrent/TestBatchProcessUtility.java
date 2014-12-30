package gr.interamerican.bo2.impl.open.runtime.concurrent;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Properties;
import java.util.Set;

import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

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
		@SuppressWarnings("nls") String[] classNames = {"foo1", "foo2"}; 
		for (int i = 0; i < classNames.length; i++) {
			String s = BatchProcessUtility.MONITOR_PREFIX + classNames[i];
			properties.setProperty(s, StringConstants.EMPTY);
		}		
		BatchProcessUtility bpu = new BatchProcessUtility(mock(Session.class));
		Set<String> set = bpu.getMonitoringOperationClasses(properties);
		
		assertEquals(classNames.length, set.size());		
		for (int i = 0; i < classNames.length; i++) {
			assertTrue(set.contains(classNames[i]));
		}
	}
	
	/**
	 * Modifiable by properties MonitoringOperation.
	 */
	interface MoMo extends MonitoringOperation<Object>, ModifiableByProperties {}; 


	
	

}
