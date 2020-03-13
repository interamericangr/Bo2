/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.wicket.creators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.attributes.Labeled;
import gr.interamerican.bo2.utils.attributes.Named;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.MultipleSelectionsColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * Implementation of a {@link DataTableProvider} based on various methods
 * (getters) of the bean in question.<br>
 * Keep in mind that the order of the columns on the table is the one that they
 * are added to this.
 * 
 * @param <B>
 *            type of bean
 */
public class FunctionalDataTableCreator<B extends Serializable>
implements DataTableProviderWithColumns<B, SerializableComparableFunction<B,?>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Rows per page.
	 */
	Integer rowsPerPage = 10;

	/**
	 * Sort Criteria to use on initial Creation
	 */
	SerializableComparableFunction<B,?> defaultSort;

	/**
	 * All Columns of this.
	 */
	List<IColumn<B, SerializableComparableFunction<B,?>>> columns = new ArrayList<>();

	/**
	 * A modification applied to the resulting {@link DefaultDataTable} created by this
	 */
	SerializableUnaryOperator<DefaultDataTable<B, SerializableComparableFunction<B, ?>>> modification;

	/**
	 * If we want paging to be disabled
	 */
	Boolean disablePaging = false;

	/**
	 * Hidden Constructor.
	 */
	private FunctionalDataTableCreator() {
		// empty
	}

	/**
	 * Creates and returns a new instance of this.
	 * 
	 * @return New instance of this
	 */
	public static <B extends Serializable> FunctionalDataTableCreator<B> empty() {
		return new FunctionalDataTableCreator<>();
	}

	/**
	 * Creates and returns a new instance of this that will contain a
	 * {@link SingleSelectionColumn}.<br>
	 * The resource key {@link WellKnownResourceIds#PDT_SELECT_LABEL} is used,
	 * and if not found {@link StringConstants#EMPTY} is used for the label of
	 * the {@link SingleSelectionColumn}.
	 * 
	 * @return New instance of this
	 */
	public static <B extends Serializable> FunctionalDataTableCreator<B> picker() {
		StringResourceModel model = new StringResourceModel(WellKnownResourceIds.PDT_SELECT_LABEL)
				.setDefaultValue(StringConstants.EMPTY);
		return picker(model);
	}

	/**
	 * Creates and returns a new instance of this that will contain a
	 * {@link SingleSelectionColumn}.
	 * 
	 * @param columnLabel
	 *            The label of the {@link SingleSelectionColumn}.
	 * @return New instance of this
	 */
	public static <B extends Serializable> FunctionalDataTableCreator<B> picker(IModel<String> columnLabel) {
		FunctionalDataTableCreator<B> returned = new FunctionalDataTableCreator<>();
		returned.columns.add(new SingleSelectionColumn<>(columnLabel));
		return returned;
	}

	/**
	 * Creates and returns a new instance of this that will contain a
	 * {@link MultipleSelectionsColumn}.<br>
	 * The resource key {@link WellKnownResourceIds#MSDT_SELECT_LABEL} is used,
	 * and if not found {@link StringConstants#EMPTY} is used for the label of
	 * the {@link MultipleSelectionsColumn}.
	 * 
	 * @return New instance of this
	 */
	public static <B extends Serializable> FunctionalDataTableCreator<B> multiple() {
		StringResourceModel model = new StringResourceModel(WellKnownResourceIds.MSDT_SELECT_LABEL)
				.setDefaultValue(StringConstants.EMPTY);
		return multiple(model);
	}

	/**
	 * Creates and returns a new instance of this that will contain a
	 * {@link MultipleSelectionsColumn}.
	 * 
	 * @param columnLabel
	 *            The label of the {@link MultipleSelectionsColumn}.
	 * @return New instance of this
	 */
	public static <B extends Serializable> FunctionalDataTableCreator<B> multiple(IModel<String> columnLabel) {
		FunctionalDataTableCreator<B> returned = new FunctionalDataTableCreator<>();
		returned.columns.add(new MultipleSelectionsColumn<>(columnLabel));
		return returned;
	}

	/**
	 * Adds a column on this.<br>
	 * The data of the column are filled using the parameter {@link SerializableComparableFunction}
	 * and the label is the one provided.
	 * 
	 * @param getter
	 *            {@link SerializableComparableFunction} (getter) to provide the data of the column
	 * @param label
	 *            Label of the Column
	 * @return This for linking
	 */
	public <C extends Comparable<C> & Serializable> FunctionalDataTableCreator<B> add(SerializableComparableFunction<B, C> getter, String label) {
		return add(getter, new Model<>(label), null);
	}

	/**
	 * Adds a column on this.<br>
	 * The data of the column are filled using the parameter {@link SerializableFunction}s,
	 * which means it works for 2 functions (a nested property) and the label is
	 * the one provided.
	 * 
	 * @param first
	 *            First {@link SerializableFunction} (getter) to provide the data of the
	 *            column
	 * @param second
	 *            Second {@link SerializableFunction} (getter) to provide the data of the
	 *            column
	 * @param label
	 *            Label of the Column
	 * @return This for linking
	 */
	public <C extends Comparable<C> & Serializable, D> FunctionalDataTableCreator<B> add(SerializableFunction<B, D> first, SerializableComparableFunction<D, C> second,
			IModel<String> label) {
		SerializableComparableFunction<B, C> getter = SerializableComparableFunction.nullSafeSynthesize(first, second);
		return add(getter, label, null);
	}

	/**
	 * Adds a column on this.<br>
	 * The data of the column are filled using the parameter {@link SerializableFunction}s,
	 * which means it works for 3 functions (a nested property inside another
	 * nested property) and the label is the one provided.
	 * 
	 * @param first
	 *            First {@link SerializableFunction} (getter) to provide the data of the
	 *            column
	 * @param second
	 *            Second {@link SerializableFunction} (getter) to provide the data of the
	 *            column
	 * @param third
	 *            Third {@link SerializableFunction} (getter) to provide the data of the
	 *            column
	 * @param label
	 *            Label of the Column
	 * @return This for linking
	 */
	public <C extends Comparable<C> & Serializable, D, E> FunctionalDataTableCreator<B> add(SerializableFunction<B, D> first,
			SerializableFunction<D, E> second, SerializableComparableFunction<E, C> third, IModel<String> label) {
		SerializableFunction<B, E> function = first.andThen(second);
		SerializableComparableFunction<B, C> getter = SerializableComparableFunction.nullSafeSynthesize(function, third);
		return add(getter, label, null);
	}

	/**
	 * Adds a column on this.<br>
	 * The data of the column are filled using the parameter {@link SerializableComparableFunction}
	 * and the label is the one provided.
	 * 
	 * @param getter
	 *            {@link SerializableComparableFunction} (getter) to provide the data of the column
	 * @param label
	 *            Label of the Column
	 * @return This for linking
	 */
	public <C extends Comparable<C> & Serializable> FunctionalDataTableCreator<B> add(SerializableComparableFunction<B, C> getter, IModel<String> label) {
		return add(getter, label, null);
	}

	/**
	 * Adds a column on this.<br>
	 * The data of the column are filled using the parameter {@link SerializableComparableFunction}
	 * and the label is the one provided.<br>
	 * The input {@link Formatter} is also used for the final formatting of the
	 * data.
	 * 
	 * @param getter
	 *            {@link SerializableComparableFunction} (getter) to provide the data of the column
	 * @param label
	 *            Label of the Column
	 * @param formatter
	 *            Formatter Used to display the data
	 * @return This for linking
	 */
	public <C extends Comparable<C> & Serializable> FunctionalDataTableCreator<B> add(SerializableComparableFunction<B, C> getter, IModel<String> label,
			Formatter<C> formatter) {
		columns.add(Utils.cast(new FunctionalColumn<B, C>(label, getter, formatter)));
		return this;
	}

	@Override
	public DataTable<B, SerializableComparableFunction<B, ?>> createDataTable(String id, List<B> elements) {
		FunctionalSortableDataProvider<B> provider = new FunctionalSortableDataProvider<>(elements, defaultSort);
		int rows = rowsPerPage;
		if (disablePaging && provider.size() > 0) {
			rows = (int) provider.size();
		}

		DefaultDataTable<B, SerializableComparableFunction<B, ?>> returned = new DefaultDataTable<>(id, columns,
				provider, rows);
		if (modification != null) {
			returned = modification.apply(returned);
		}
		return returned;
	}

	/**
	 * Assigns a new value to the rowsPerPage.
	 *
	 * @param rowsPerPage
	 *            the rowsPerPage to set
	 * @return This for linking
	 */
	public FunctionalDataTableCreator<B> setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		return this;
	}

	/**
	 * Sets the sort Criteria to use on initial Creation of the
	 * {@link DataTable}
	 *
	 * @param defaultSort
	 *            Sort Criteria to use on initial Creation
	 * @return This for linking
	 */
	public <C extends Comparable<C>> FunctionalDataTableCreator<B> setDefaultSort(SerializableComparableFunction<B, C> defaultSort) {
		this.defaultSort = defaultSort;
		return this;
	}

	/**
	 * Assigns a new value to the disablePaging.
	 * 
	 * This flag controls whether the created {@link DataTable} will page its
	 * rows or not. This setting may change at any time and its change affect
	 * any tables created from that point on.
	 *
	 * @param disablePaging
	 *            the disablePaging to set
	 * @return This for linking
	 */
	public FunctionalDataTableCreator<B> setDisablePaging(Boolean disablePaging) {
		this.disablePaging = disablePaging;
		return this;
	}

	/**
	 * Adds user-created extra columns to the DataTable. The user is responsible
	 * for adding labels to these columns.
	 *
	 * @param extras
	 *            the columns
	 * @return This for linking
	 */
	@SafeVarargs
	public final <R extends Comparable<R>> FunctionalDataTableCreator<B> addExtraColumns(
			IColumn<B, SerializableComparableFunction<B, R>>... extras) {
		Arrays.asList(extras).forEach(this::addExtraColumn);
		return this;
	}

	/**
	 * Adds a user-created extra column to the DataTable. The user is
	 * responsible for adding labels to the column.
	 *
	 * @param extra
	 *            the column
	 * @return This for linking
	 */
	public final <R extends Comparable<R>> FunctionalDataTableCreator<B> addExtraColumn(
			IColumn<B, SerializableComparableFunction<B, R>> extra) {
		columns.add(Utils.cast(extra));
		return this;
	}

	/**
	 * Adds a {@link SerializableUnaryOperator} that will be modify the end
	 * result of this.
	 *
	 * @param modification
	 *            A modification applied to the resulting DefaultDataTable
	 *            created by this
	 * @return This for linking
	 */
	public final <R extends Comparable<R>> FunctionalDataTableCreator<B> setModification(
			SerializableUnaryOperator<DefaultDataTable<B, SerializableComparableFunction<B, ?>>> modification) {
		this.modification = modification;
		return this;
	}

	/**
	 * Adds to this a column for each {@link BoPropertyDescriptor} contained in
	 * a {@link BusinessObjectDescriptor}.
	 *
	 * @param descriptor
	 *            the columns
	 * @return This for linking
	 */
	public FunctionalDataTableCreator<B> addSelfDrawnColumns(BusinessObjectDescriptor<B> descriptor) {
		CollectionUtils.sort(descriptor.getPropertyDescriptors(), BoPropertyDescriptor::getIndex)
				.forEach(this::addSelfDrawnColumn);
		return this;
	}

	/**
	 * Adds a column for something with a name (property expression) and a label.
	 * 
	 * @param property
	 *            Something with a name (property expression) and a label
	 */
	@SuppressWarnings("unchecked")
	public <P extends Named & Labeled, C extends Comparable<C> & Serializable> void addSelfDrawnColumn(P property) {
		add((o) -> (C) PropertyResolver.getValue(property.getName(), o), property.getLabel());
	}

	@Override
	public List<IColumn<B, SerializableComparableFunction<B, ?>>> getColumns() {
		return Collections.unmodifiableList(columns);
	}
}