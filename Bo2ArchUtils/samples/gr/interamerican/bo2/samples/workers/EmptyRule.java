package gr.interamerican.bo2.samples.workers;

import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;

/**
 * Empty Rule.
 */
public class EmptyRule extends EmptyWorker implements Rule {

	@Override
	public void apply() throws RuleException, DataException {
		/* empty */
	}
	

}
