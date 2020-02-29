package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;

/**
 * panel to display statistics of the {@link BatchProcess}. atm it displays
 * <ul>
 * <li>the next element to process</li>
 * <li>The remaining elements in the queue</li>
 * <li>The process time of a single element (in ms)</li>
 * <li>The throughput (elements per second)</li>
 * <li>ETA</li>
 * </ul>
 */
public class BatchProcessStatsPanel extends BPanel<MultiThreadedLongProcess> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** fields. */
	@SuppressWarnings("nls")
	private static String[] FIELDS = { "nextElementToProcessFormatted", "queueSize", "processTime", "throughput", "eta" };

	/**
	 * default constructor.
	 *
	 * @param model the model
	 */
	public BatchProcessStatsPanel(MultiThreadedLongProcess model) {
		super(model);
	}

	/**
	 * Component properties.
	 *
	 * @return Returns a ComponentProperties object.
	 */
	TextFieldProperties properties() {
		TextFieldProperties properties = Factory.create(TextFieldProperties.class);
		properties.setEnabled(true);
		properties.setEditable(false);
		properties.setColumns(30);
		properties.setHasLabel(true);
		properties.setLabelLength(10);
		return properties;
	}

	@Override
	public void paint() {
		TextFieldProperties fp = properties();
		addModelBoundTextFields(FIELDS, fp);
		setPreferredSize(Sizes.square(fp.getColumns() + fp.getLabelLength(), FIELDS.length + 1, true));
		Layout.layAsStackOfLabeledFields(this, 5, 5);
	}
}
