package gr.interamerican.wicket.creators;

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * {@link FunctionalInterface} for a very common pattern for constructors of
 * panels - which takes as input the wicket Id and an {@link IModel}.
 * 
 * @param <T>
 *            Type of Bean
 * @see PanelConstructor also
 */
@FunctionalInterface
public interface SimplePanelConstructor<T> extends Serializable {

	/**
	 * Returns a Panel
	 * 
	 * @param t
	 *            Wicket Id
	 * @param u
	 *            Model Object
	 * @return The new Panel
	 */
	Panel apply(String t, IModel<T> u);
}