package gr.interamerican.bo2.utils.runnables;



import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * {@link RunnableCommand} adapts a {@link SimpleCommand}
 * to the {@link Runnable} interface.
 * 
 * The run method of this Runnable executes the SimpleCommand.
 */
public class RunnableCommand 
implements Runnable {
	

	/**
	 * Command to execute.
	 */
	SimpleCommand command;
	
	/**
	 * Creates a new RunnableCommand.
	 * 
	 * @param command
	 *        SimpleCommand that will be executed by the command.
	 */
	public RunnableCommand(SimpleCommand command) {
		super();
		this.command = command;		
	}

	@Override
	public void run() {
		command.execute();		
	}

}
