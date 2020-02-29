package gr.interamerican.wicket.bo2.utils;

import java.util.function.Supplier;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.theme.DefaultTheme;

/**
 * An interface that keeps the Configuration shared between various
 * SelfDrawnComponents.
 */
public interface SelfDrawnComponentsConfiguration {

	/**
	 * Specifies the Theme that any Self Drawn Components that are
	 * {@link Palette} will use by default.<br>
	 * If not changed - the default is {@link DefaultTheme}.
	 */
	static Supplier<Behavior> DEFAULT_PALLETE_THEME = DefaultTheme::new;
}
