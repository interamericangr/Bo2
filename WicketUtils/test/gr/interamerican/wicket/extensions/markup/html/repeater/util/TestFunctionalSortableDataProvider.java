package gr.interamerican.wicket.extensions.markup.html.repeater.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;

/**
 * Unit tests for {@link FunctionalSortableDataProvider}
 */
public class TestFunctionalSortableDataProvider {

	/**
	 * Sample list.
	 */
	static List<String> rows;

	/**
	 * Method that initializes data that will be used in tests.
	 */
	@SuppressWarnings("nls")
	@Before
	public void buildUp() {
		rows = new ArrayList<>();
		rows.add("a");
		rows.add("bb");
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#FunctionalSortableDataProvider(java.util.List)}.
	 */
	@Test
	public void testFunctionalSortableDataProviderListOfT() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows);
		assertEquals(rows, tested.getRows());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#FunctionalSortableDataProvider(java.util.List, gr.interamerican.bo2.utils.functions.SerializableComparableFunction)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFunctionalSortableDataProviderListOfTSerializableComparableFunctionOfTQ() {
		SerializableComparableFunction<String, Boolean> sortFunc = i -> i.length() < 2;
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, sortFunc);
		String actual = tested.getRows().iterator().next();
		assertEquals("bb", actual);

		tested = new FunctionalSortableDataProvider<>(rows, null);
		assertEquals(rows, tested.getRows());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#getRows()}.
	 */
	@Test
	public void testGetRows() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		List<String> actual = tested.getRows();

		assertEquals(rows, actual);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#setRows(java.util.List)}.
	 */
	@Test
	public void testSetRows() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		tested.setRows(rows);

		assertEquals(rows, tested.getRows());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#model(java.io.Serializable)}.
	 */
	@Test
	public void testModel() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		IModel<String> actual = tested.model(StringConstants.FIVE);
		assertEquals(Model.of(StringConstants.FIVE), actual);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#iterator(long, long)}.
	 */
	@Test
	public void testIterator() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		Iterator<? extends String> iterator = tested.iterator(0L, 1L);
		String actual = iterator.next();
		assertEquals("a", actual); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#sortList()}.
	 */
	@Test
	public void testSortList() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		tested.sortList();
		assertEquals(rows, tested.getRows());
		
		//Case where sort will be applied as it is
		SerializableComparableFunction<String, Boolean> sortFunc = i -> i.length() < 2;
		tested = new FunctionalSortableDataProvider<>(rows, null);
		tested.setSort(new SortParam<SerializableComparableFunction<String,?>>(sortFunc, false));
		tested.sortList();
		List<String> actual = tested.getRows();
		assertEquals("bb", actual.iterator().next()); //$NON-NLS-1$
		
		//Case where sort will be applied in a ascending order
		tested.setSort(new SortParam<SerializableComparableFunction<String,?>>(sortFunc, true));
		tested.sortList();
		actual = tested.getRows();
		assertEquals("a",actual.iterator().next()); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider#size()}.
	 */
	@Test
	public void testSize() {
		FunctionalSortableDataProvider<String> tested = new FunctionalSortableDataProvider<>(rows, null);
		assertEquals(rows.size(), tested.size());
	}

}
