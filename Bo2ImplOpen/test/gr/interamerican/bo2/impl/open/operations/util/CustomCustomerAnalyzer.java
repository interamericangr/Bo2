package gr.interamerican.bo2.impl.open.operations.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.analysis.OneToManyProperty;
import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;
import gr.interamerican.bo2.impl.open.po.analysis.PoProperty;
import gr.interamerican.bo2.impl.open.po.analysis.SimpleProperty;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;

/**
 * A {@link PoAnalyzer} for unit tests that covers the entity of
 * {@link Customer} and it's child entity {@link CustomerAddress}.<br>
 * Not all properties are described actually.
 */
public class CustomCustomerAnalyzer implements PoAnalyzer {

	@SuppressWarnings("nls")
	@Override
	public PoAnalysis getAnalysis(Class<?> class1) {
		PoAnalysis analysis = Factory.create(PoAnalysis.class);
		PoProperty keyProperty = createProperty("key", String.class, SimpleProperty.class);
		analysis.setKeyProperty(keyProperty);
		List<PoProperty> result = new ArrayList<PoProperty>();
		analysis.setProperties(result);
		if (Customer.class.isAssignableFrom(class1)) {
			result.add(createProperty("taxId", String.class, SimpleProperty.class));
			OneToManyProperty addresses = createProperty("addresses", Set.class, OneToManyProperty.class);
			addresses.setAnalysis(getAnalysis(CustomerAddress.class));
			result.add(addresses);
			return analysis;
		}
		if (CustomerAddress.class.isAssignableFrom(class1)) {
			result.add(createProperty("streetNo", String.class, SimpleProperty.class));
			result.add(createProperty("street", String.class, SimpleProperty.class));
			return analysis;
		}
		throw new RuntimeException(MessageFormat.format("Type {0} not supported by this", class1));
	}

	/**
	 * Creates a {@link PoProperty} with the input arguments.
	 * 
	 * @param name
	 *            Name
	 * @param clz
	 *            Class
	 * @param clzOfProperty
	 *            Class of the {@link PoProperty}
	 * @return New {@link PoProperty}
	 */
	<T extends PoProperty> T createProperty(String name, Class<?> clz, Class<T> clzOfProperty) {
		T result = Factory.create(clzOfProperty);
		result.setName(name);
		result.setType(clz);
		return result;
	}
}