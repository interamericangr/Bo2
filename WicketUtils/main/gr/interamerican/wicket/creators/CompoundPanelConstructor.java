package gr.interamerican.wicket.creators;

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * {@link FunctionalInterface} for a very common pattern for constructors of
 * panels - which takes as input the wicket Id, an {@link CompoundPropertyModel}
 * and a {@link PanelCreatorMode}.
 * 
 * @param <T>
 *            Type of Bean
 * @see PanelConstructor also
 */
@FunctionalInterface
public interface CompoundPanelConstructor<T> extends Serializable {

	/**
	 * Returns a Panel
	 * 
	 * @param t
	 *            Wicket Id
	 * @param u
	 *            Model Object
	 * @param mode
	 *            The {@link PanelCreatorMode}
	 * @return The new Panel
	 */
	Panel apply(String t, CompoundPropertyModel<T> u, PanelCreatorMode mode);
}