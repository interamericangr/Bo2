package gr.interamerican.bo2.samples;

import interamerican.archimpl.workers.SimpleOperation;

/**
 * Sample operation.
 * 
 * This operation has two construtors. The no argument constructor
 * creates an operation that will not throw any exception when
 * executed. The constructor that takes a throwable as argument
 * will throw this throwable when executed.
 */
public class SampleLegacyBoOperation extends SimpleOperation {
	/**
	 * Object to throw on execution.
	 */
	private Throwable t=null;
	
	/**
	 * Creates a new SampleLegacyBoOperation object. 
	 *
	 */
	public SampleLegacyBoOperation() {
		super();		
	}

	/**
	 * Creates a new SampleLegacyBoOperation object. 
	 *
	 * @param t Throwable that will be thrown on execution.
	 */
	public SampleLegacyBoOperation(Throwable t) {
		super();
		this.t = t;
	}



	/**
	 * indicator of successful execution.
	 */
	private boolean success = false;
	
	@Override
	public void execute() throws Throwable {
		if (t!=null) {
			throw t;
		}
		success = true;
	}

	/**
	 * Gets the success.
	 *
	 * @return Returns the success
	 */
	public boolean isSuccess() {
		return success;
	}
	
}
