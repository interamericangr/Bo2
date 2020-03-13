package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.creation.annotations.MockMethods;
import gr.interamerican.bo2.creation.annotations.Property;

import java.io.Serializable;

/**
 * Dummy Question for a Key that will just copy the key of the existing Po.<br>
 * This is used by {@link ServicePanelDefinitionFactory#crudPickerPanelDefWithActions(Class)}.
 * 
 * @param <K>
 *            Key to find
 * @param <P>
 *            {@link PersistentObject} of which the key we search
 * @deprecated to be removed
 */
@Deprecated
@MockMethods("open,close,init,getProvider")
abstract class JustCopyIdQuestion<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
		implements Question<K>, PoDependent<P> {

	/** The {@link PersistentObject}. */
	@Property P po;

	/** The Answer. */
	@Property K answer;

	@Override
	public void ask() throws DataException, LogicException {
		answer = po.getKey();
	}
}