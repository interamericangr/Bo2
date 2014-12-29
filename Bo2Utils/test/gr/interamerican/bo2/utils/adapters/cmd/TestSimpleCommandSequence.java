package gr.interamerican.bo2.utils.adapters.cmd;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * Tests for SimpleCommandSequence.
 */
public class TestSimpleCommandSequence {
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		boolean failsafe = true;
		SimpleCommandSequence scs = new SimpleCommandSequence(failsafe);
		assertEquals(failsafe, scs.failSafe);
		assertEquals(0, scs.commands.size());
	}
	
	/**
	 * Tests the addCommand(cmd) method.
	 */
	@Test
	public void testAddCommand_FailSafe() {		
		SimpleCommandSequence scs = new SimpleCommandSequence(true);
		SimpleCommand cmd = mock(SimpleCommand.class);				
		scs.addCommand(cmd);
		SimpleCommand command = scs.commands.get(0);
		assertTrue(command instanceof FailSafeCommand);
		FailSafeCommand nofail = (FailSafeCommand) command;
		assertEquals(cmd, nofail.getWrappedCommand());
	}
	
	/**
	 * Tests the addCommand(cmd) method.
	 */
	@Test
	public void testAddCommand_Simple() {		
		SimpleCommandSequence scs = new SimpleCommandSequence(false);
		SimpleCommand cmd = mock(SimpleCommand.class);				
		scs.addCommand(cmd);
		SimpleCommand command = scs.commands.get(0);
		assertEquals(cmd, command);
	}
	
	/**
	 * Tests the execute() method.
	 */
	@Test
	public void testExecute_FailSafe() {		
		SimpleCommandSequence scs = new SimpleCommandSequence(true);
		SimpleCommand cmd1 = mock(SimpleCommand.class);		
		SimpleCommand cmd2 = mock(SimpleCommand.class);
		doThrow(RuntimeException.class).when(cmd2).execute();
		SimpleCommand cmd3 = mock(SimpleCommand.class);
		scs.addCommand(cmd1);
		scs.addCommand(cmd2);
		scs.addCommand(cmd3);
		scs.execute();
		verify(cmd1, times(1)).execute();
		verify(cmd2, times(1)).execute();
		verify(cmd3, times(1)).execute();
	}
	
	/**
	 * Tests the execute() method.
	 */
	@Test
	public void testExecute_Simple() {		
		SimpleCommandSequence scs = new SimpleCommandSequence(false);
		SimpleCommand cmd1 = mock(SimpleCommand.class);		
		SimpleCommand cmd2 = mock(SimpleCommand.class);
		doThrow(RuntimeException.class).when(cmd2).execute();
		SimpleCommand cmd3 = mock(SimpleCommand.class);
		scs.addCommand(cmd1);
		scs.addCommand(cmd2);
		scs.addCommand(cmd3);
		boolean thrown = false;
		try {
			scs.execute();
		} catch (RuntimeException re) {
			thrown = true;
		}
		assertTrue(thrown);
		verify(cmd1, times(1)).execute();
		verify(cmd2, times(1)).execute();
		verify(cmd3, times(0)).execute();
	}
	
	

}
