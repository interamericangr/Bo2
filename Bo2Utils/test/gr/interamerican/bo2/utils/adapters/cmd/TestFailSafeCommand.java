package gr.interamerican.bo2.utils.adapters.cmd;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * Tests for {@link FailSafeCommand}.
 */
public class TestFailSafeCommand {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		FailSafeCommand fsc = new FailSafeCommand(cmd);
		Assert.assertEquals(cmd, fsc.command);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute_withException() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		doThrow(RuntimeException.class).when(cmd).execute();
		FailSafeCommand fsc = new FailSafeCommand(cmd);
		fsc.execute();
		verify(cmd, times(1)).execute();		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute_withoutException() {
		SimpleCommand cmd = mock(SimpleCommand.class);		
		FailSafeCommand fsc = new FailSafeCommand(cmd);
		fsc.execute();
		verify(cmd, times(1)).execute();		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testGetWrappedCommand() {
		SimpleCommand cmd = mock(SimpleCommand.class);		
		FailSafeCommand fsc = new FailSafeCommand(cmd);
		Assert.assertEquals(fsc.command, fsc.getWrappedCommand());
	}



}
