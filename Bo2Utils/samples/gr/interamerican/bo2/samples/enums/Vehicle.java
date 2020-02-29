package gr.interamerican.bo2.samples.enums;

import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

/**
 * Sample for Corner Case of {@link TypeAnalysis}.
 */
public enum Vehicle implements Indexed {

	/**
	 * 
	 */
	CAR {
		@Override
		void doSomeRandomShit() {
			// empty
		}
	},

	/**
	 * 
	 */
	TRUCK {
		@Override
		void doSomeRandomShit() {
			// empty
		}
	};

	/**
	 * id
	 */
	final Integer id = new Integer(ordinal());

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Some Random Method
	 */
	abstract void doSomeRandomShit();
}