package gr.interamerican.bo2.utils.runnables;

import static org.mockito.Mockito.*;

import org.junit.Test;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * Test for {@link RunnableCommand}.
 */
public class TestRunnableCommand {
	
	/**
	 * test for run().
	 */
	@Test
	public void testRun() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		RunnableCommand runnable = new RunnableCommand(cmd);
		runnable.run();
		verify(cmd, times(1)).execute();
	}

}
