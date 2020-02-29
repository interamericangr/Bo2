package gr.interamerican.wicket.samples.panels;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

/**
 * Custom {@link BeanWithOrderedFields} with hidden public constructor.
 */
public class MyBeanWithOrderedFields extends BeanWithOrderedFields {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hidden Constructor.
	 */
	private MyBeanWithOrderedFields() {
		super();
	}

	/**
	 * Creates a new instance.
	 * 
	 * @return New instance
	 */
	public static MyBeanWithOrderedFields newInstance() {
		return new MyBeanWithOrderedFields();
	}

	/**
	 * Public Constructor.
	 *
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @param third
	 *            the third
	 * @param fourth
	 *            the fourth
	 * @param fifth
	 *            the fifth
	 */
	public MyBeanWithOrderedFields(String first, String second, Integer third, Long fourth, Double fifth) {
		super(first, second, third, fourth, fifth);
	}

}
