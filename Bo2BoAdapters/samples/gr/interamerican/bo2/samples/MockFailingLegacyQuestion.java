package gr.interamerican.bo2.samples;

import interamerican.architecture.Question;
import interamerican.architecture.exceptions.DataException;
import interamerican.architecture.exceptions.LogicException;

/**
 * Mock implementation of {@link Question}.
 */
@SuppressWarnings("rawtypes")
public class MockFailingLegacyQuestion 
extends MockLegacyWorker 
implements Question {
	/**
	 * if true, throws LogicException otherwise throws DataException.
	 */
	boolean throwLogic = true;
	
	/**
	 * Creates a new MockFailingLegacyQuestion object. 
	 *
	 * @param throwLogic
	 */
	public MockFailingLegacyQuestion(boolean throwLogic) {
		super();
		this.throwLogic = throwLogic;
	}
	
	public void ask() throws DataException, LogicException {
		if (throwLogic) {
			throw new LogicException();
		} else {
			throw new DataException();
		}
	}

	public Object getAnswer() {		
		return "answer"; //$NON-NLS-1$
	}

}
