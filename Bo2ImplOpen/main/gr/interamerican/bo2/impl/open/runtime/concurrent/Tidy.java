package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * Calls tidy on a {@link LongProcess}.
 */
public class Tidy implements VoidOperation<LongProcess>{
	
	@Override
	public void execute(LongProcess a) {
		a.tidy();		
	}

}
