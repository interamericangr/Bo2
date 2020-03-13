package gr.interamerican.bo2.gui.batch;

import static gr.interamerican.bo2.gui.batch.ComponentPropertiesFactory.bareCheckBox;

import java.awt.Dimension;

import gr.interamerican.bo2.gui.batch.model.BatchProcessExecutionStatusPanelModel;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
/**
 * panel to shwo the status of the batch process
 */
public class BatchProcessExecutionStatusPanel extends BPanel<BatchProcessExecutionStatusPanelModel> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private static final String[] STATUSES = new String[] { "start", "preProcessExecution", "mainQueryExecution", "qprocessorsExecution", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	"postProcess" }; //$NON-NLS-1$

	/**
	 *
	 */
	private static final String[] LABELS = new String[] { "start", "pre", "query", "main", "post" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$

	/**
	 * constructor
	 *
	 * @param model
	 */
	public BatchProcessExecutionStatusPanel(BatchProcessExecutionStatusPanelModel model) {
		super(model);
	}

	@Override
	public void paint() {
		int length = 0;
		for (int i=0;i<STATUSES.length;i++) {
			length += LABELS[i].length();
			addStaticLabel(LABELS[i], ComponentPropertiesFactory.label(LABELS[i].length()));
			addModelBoundCheckBox(STATUSES[i], bareCheckBox(false));
		}
		int space = 5;
		int ffl = 2;
		Layout.layAsRow(this, space, space);
		double width = (STATUSES.length * ((ffl * Sizes.FORM_COMPONENT_CHAR_WIDTH) + space)) + (5 * space)
				+ (length * (Sizes.FORM_COMPONENT_CHAR_WIDTH + space)) + (3 * space);
		double height = Sizes.FORM_COMPONENT_HEIGHT + (2 * space);
		Dimension size = Sizes.dimension(width, height);
		setPreferredSize(size);
		Layout.layAsRow(this, space, space);
	}

}
