package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.components.BooleanDdc;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Self-drawn {@link BooleanDdc}. 
 */
public class SelfDrawnBooleanDdc extends BooleanDdc {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SelfDrawnBooleanDdc object. 
	 *
	 * @param id the id
	 * @param descriptor the descriptor
	 */
	public SelfDrawnBooleanDdc(String id, BooleanBoPropertyDescriptor descriptor) {
		this(id, new Model<Boolean>(), descriptor);
	}
	
	/**
	 * Creates a new SelfDrawnBooleanDdc object. 
	 *
	 * @param id the id
	 * @param model the model
	 * @param descriptor the descriptor
	 */
	public SelfDrawnBooleanDdc(String id, IModel<Boolean> model, BooleanBoPropertyDescriptor descriptor) {
		super(id, model);
		
		//handle descriptor info for default values and readOnly
		if (descriptor.isHasDefault() && getDefaultModelObject()==null) {
			setDefaultModelObject(descriptor.getDefaultValue());
		}
		setEnabled(!descriptor.isReadOnly());
	}

	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SELECT);
		tag.put(MarkupConstants.STYLE, MarkupConstants.WIDTH);
		super.onComponentTag(tag);
	}

}
