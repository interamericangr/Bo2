package gr.interamerican.bo2.utils.adapters.cmd;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * A sequence of simple commands.
 */
public class SimpleCommandSequence implements SimpleCommand {
	
	/**
	 * Indicates if an exception that could occur in one execution 
	 * would not stop the rest of the sequence of executions. 
	 */
	boolean failSafe;
	
	/**
	 * List of commands.
	 */
	List<SimpleCommand> commands = new ArrayList<SimpleCommand>();
	
	/**
	 * Adds a SimpleCommand.
	 * 
	 * This method is null safe. If the operand is null then
	 * nothing happens. 
	 * 
	 * @param cmd
	 *        Command to add. 
	 */
	public void addCommand(SimpleCommand cmd) {
		if (cmd!=null) {	
			SimpleCommand command=cmd;
			if (failSafe) {
				command = new FailSafeCommand(cmd);
			}
			this.commands.add(command);			
		}
	}

	/**
	 * Creates a new SimpleCommandSequence object.
	 * 
	 * @param failSafe
	 *        Specifies if a {@link RuntimeException} that could be thrown by
	 *        the execution of one command would be ignored.
	 *        If this argument is <code>true</code> , then all SimpleCommands of the 
	 *        sequence  will be executed, even if any of them throw a {@link RuntimeException}.
	 *        If this argument is <code>false</code> , then all SimpleCommands of the
	 *        would not stop the rest of the sequence of executions.
	 */
	public SimpleCommandSequence(boolean failSafe) {
		super();
		this.failSafe = failSafe;
	}

	@Override
	public void execute() {
		for (SimpleCommand simpleCommand : commands) {
			simpleCommand.execute();
		}
	}
	
	
	
	

}
