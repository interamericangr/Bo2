package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

/**
 * Adapts a legacy Bo question to Bo2.
 * 
 * @param <A>
 *        Type of answer. 
 */
public class LegacyQuestionAdapter<A> extends LegacyWorkerAdapter 
implements Question<A>{
	
	/**
	 * Legacy question.
	 */
	@SuppressWarnings("rawtypes")
	private interamerican.architecture.Question question;

	/**
	 * Creates a new LegacyQuestionAdapter object. 
	 *
	 * @param legacy
	 */
	@SuppressWarnings("rawtypes")
	public LegacyQuestionAdapter(interamerican.architecture.Question legacy) {
		super(legacy);
		this.question = legacy;
	}

	public void ask() throws DataException, LogicException {
		try {
			question.ask();
		} catch (interamerican.architecture.exceptions.DataException e) {
			throw new DataException(e);
		} catch (interamerican.architecture.exceptions.LogicException e) {
			throw new LogicException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public A getAnswer() {
		return (A) question.getAnswer();
	}
	
}
