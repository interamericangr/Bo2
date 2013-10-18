package gr.interamerican.bo2.samples;

import interamerican.architecture.Question;
import interamerican.architecture.exceptions.DataException;
import interamerican.architecture.exceptions.LogicException;

/**
 * Mock implementation of {@link Question}.
 */
@SuppressWarnings("rawtypes")
public class MockLegacyQuestion 
extends MockLegacyWorker 
 implements Question {

	
	

	
	public void ask() throws DataException, LogicException {
		/* empty */
	}

	public Object getAnswer() {		
		return "answer"; //$NON-NLS-1$
	}

}
