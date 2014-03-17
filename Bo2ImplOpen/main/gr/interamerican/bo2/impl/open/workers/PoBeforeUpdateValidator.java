package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.impl.open.po.PoUtils;

/**
 * {@link Rule} that performs validations on a PersistentObject in the unit of
 * work that will ultimately update the persistent object in the data layer. If
 * the validation fails the object is detached before re-throwing the RuleException.
 * <br/><br/>
 * The main application of this implementation is to reset side-effects caused by PO
 * reattachment in the unit of work.
 * 
 * @param <P> 
 *        Type of PersistentObject
 *        
 * @see DetachStrategy
 */
public abstract class PoBeforeUpdateValidator<P extends PersistentObject<?>> 
extends AbstractPoValidator<P> {

	public void apply() throws RuleException, DataException {
		DetachStrategy ds = PoUtils.getDetachStrategy(po);
		try {
			doApply();
		} catch (RuleException e) {
			if (ds!=null) {
				ds.detach(po, getProvider());
			}
			throw e;
		}
	}
	
	/**
	 * Perform the actual validation. API users implement this.
	 * 
	 * @throws RuleException
	 * @throws DataException
	 */
	public abstract void doApply() throws RuleException, DataException;

}
