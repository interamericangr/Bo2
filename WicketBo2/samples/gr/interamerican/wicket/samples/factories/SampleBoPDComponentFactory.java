package gr.interamerican.wicket.samples.factories;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.factories.meta.AbstractBoPDComponentFactory;

/**
 * Sample implementation of {@link AbstractBoPDComponentFactory} used to test concrete methods. 
 */
public class SampleBoPDComponentFactory extends AbstractBoPDComponentFactory<BooleanBoPropertyDescriptor>{

	@Override
	public Component drawMain(BooleanBoPropertyDescriptor descriptor, String wicketId) {
		return null;
	}

	@Override
	public Component drawMain(String wicketId, IModel<?> model, BooleanBoPropertyDescriptor descriptor) {
		return null;
	}
}