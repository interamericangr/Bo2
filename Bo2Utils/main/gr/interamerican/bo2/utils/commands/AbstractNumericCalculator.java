package gr.interamerican.bo2.utils.commands;

import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.bo2.utils.attributes.NumericCalculator;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * Abstract NumericCalculator.
 * 
 * 
 * @param <T>  
 *        Type of input.
 */
public abstract class AbstractNumericCalculator<T> 
implements NumericCalculator, SimpleCommand, Input<T> {
	
	/**
	 * Operation result.
	 */
	protected int numericResult;
	
	/**
	 * input object.
	 */
	protected T input;
	
	@Override
	public Number getNumericResult() {	
		return numericResult;
	}
	
	@Override
	public void setInput(T input) {
		this.input = input;
		
	}

	@Override
	public T getInput() {		
		return input;
	}

}



