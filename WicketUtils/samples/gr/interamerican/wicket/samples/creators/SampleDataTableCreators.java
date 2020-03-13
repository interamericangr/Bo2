package gr.interamerican.wicket.samples.creators;

import org.apache.wicket.model.Model;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.creators.FunctionalDataTableCreator;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.MultipleSelectionsColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;

/**
 * Samples Factory for some {@link DataTableCreator}s used in tests.
 */
@SuppressWarnings("nls")
public interface SampleDataTableCreators {

	/**
	 * Returns a {@link FunctionalDataTableCreator} of a
	 * {@link BeanWithOrderedFields} with just 3 columns.
	 * 
	 * @param rows
	 *            Number of Rows
	 * @return {@link FunctionalDataTableCreator}
	 */
	public static FunctionalDataTableCreator<BeanWithOrderedFields> empty(int rows) {
		return FunctionalDataTableCreator.<BeanWithOrderedFields> empty().setRowsPerPage(rows)
				.add(BeanWithOrderedFields::getFirst, "first").add(BeanWithOrderedFields::getSecond, "second")
				.add(BeanWithOrderedFields::getThird, "third");
	}

	/**
	 * Returns a {@link FunctionalDataTableCreator} of a
	 * {@link BeanWithOrderedFields} with 3 columns and a
	 * {@link MultipleSelectionsColumn} (to select multiple items).
	 * 
	 * @param rows
	 *            Number of Rows
	 * @return {@link FunctionalDataTableCreator}
	 */
	public static FunctionalDataTableCreator<BeanWithOrderedFields> checkBoxes(int rows) {
		return empty(rows).addExtraColumns(new MultipleSelectionsColumn<>(new Model<>("pickerColumn")));
	}

	/**
	 * Returns a {@link FunctionalDataTableCreator} of a
	 * {@link BeanWithOrderedFields} with 3 columns and a
	 * {@link SingleSelectionColumn} (to select one item).
	 * 
	 * @param rows
	 *            Number of Rows
	 * @return {@link FunctionalDataTableCreator}
	 */
	public static FunctionalDataTableCreator<BeanWithOrderedFields> radio(int rows) {
		return empty(rows).addExtraColumns(new SingleSelectionColumn<>(new Model<>("pickerColumn")));
	}
}
