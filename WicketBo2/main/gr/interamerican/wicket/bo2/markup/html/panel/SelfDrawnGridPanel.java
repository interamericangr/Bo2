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
package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.wicket.bo2.factories.meta.GenericBoPDComponentFactory;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * The components are drawn inside a {@link RepeatingView}.
 * <br/>
 * Each component may optionally contain a label to indicate
 * what it is.
 * <br/>
 * This Panel requires a {@link CompoundPropertyModel}. If the
 * model object of this model changes, the change is propagated
 * to the contained components.
 * <br/>
 * The components are drawn in a grid layout. The user specifies the
 * number of columns. The markup is calculated dynamically.
 * 
 * @param <T>
 *            Type of model object.
 */
public class SelfDrawnGridPanel<T extends Serializable> extends BaseSelfDrawnPanel<T>
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Placeholder for Wicket id of label on body template.
	 */
	private static final String PLACEHOLDER_LABEL_WICKET_ID = ":LABEL_ID"; //$NON-NLS-1$

	/**
	 * Placeholder for Wicket id of component on body template.
	 */
	private static final String PLACEHOLDER_COMPONENT_WICKET_ID = ":COMPONENT_ID"; //$NON-NLS-1$
	
	/**
	 * Top portion of markup.
	 */
	private static final String MARKUP_HEAD = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnGridPanel_head.txt"; //$NON-NLS-1$
	
	/**
	 * Main portion of markup. This is repeated for the number of {@link #columns}
	 */
	private static final String MARKUP_BODY = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnGridPanel_body.txt"; //$NON-NLS-1$
	
	/**
	 * Bottom portion of markup.
	 */
	private static final String MARKUP_TAIL = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnGridPanel_tail.txt"; //$NON-NLS-1$
	
	/**
	 * Calculated markups cache.
	 */
	static final ConcurrentHashMap<Integer, String> MARKUPS = new ConcurrentHashMap<Integer, String>(); 

	/**
	 * Model
	 */
	private final CompoundPropertyModel<T> model;
	
	/**
	 * Number of columns for the grid.
	 */
	private final int columns;

	/**
	 * Creates a new SelfDrawnGridPanel object.
	 * 
	 * @param id
	 * @param model
	 * @param businessObjectDescriptor
	 * @param columns
	 */
	@SuppressWarnings("rawtypes")
	public SelfDrawnGridPanel(String id, CompoundPropertyModel<T> model,
			BusinessObjectDescriptor<T> businessObjectDescriptor, int columns) {
		super(id, model);

		this.model = model;
		this.columns = columns;

		setOutputMarkupPlaceholderTag(true);
		String labelStr = businessObjectDescriptor.getLabel();
		Label label = new Label(TITLE_LABEL_WICKET_ID, labelStr);
		this.add(label);
		if (StringUtils.isNullOrBlank(labelStr)) {
			label.setVisible(false);
		}
		RepeatingView rows = new RepeatingView(REPEATER_WICKET_ID);

		List<BoPropertyDescriptor<?>> specs = businessObjectDescriptor.getPropertyDescriptors();
		
		List<BoPropertyDescriptor> specsRawTyped = new ArrayList<BoPropertyDescriptor>(specs);
		specsRawTyped = CollectionUtils.sort(specsRawTyped, BoPropertyDescriptor.class, INDEX_PROPERTY_NAME);

		List<List<BoPropertyDescriptor>> rowSpecs = CollectionUtils.partition(specsRawTyped, columns);
		
		for (List<BoPropertyDescriptor> rowSpec : rowSpecs) {
			WebMarkupContainer row = new WebMarkupContainer(createRowId(rowSpec));
			populateRow(rowSpec, row);
			rows.add(row);
		}
		this.add(rows);
	}
	
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null; //no caching
	}

	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		return new StringAsResourceStream(getMarkup());
	}
	
	/**
	 * Unique row id.
	 * @param rowSpec
	 * @return row id
	 */
	String createRowId(@SuppressWarnings("rawtypes") List<BoPropertyDescriptor> rowSpec) {
		String rowId = StringConstants.EMPTY;
		for (BoPropertyDescriptor<?> bpd : rowSpec) {
			rowId += bpd.getName();
		}
		return rowId;
	}

	/**
	 * Populate the WebMarkupContainer that encapsulates a single row.
	 * @param rowSpec
	 * @param row
	 */
	@SuppressWarnings("rawtypes")
	void populateRow(List<BoPropertyDescriptor> rowSpec, WebMarkupContainer row) {
		int counter = 0;
		for (BoPropertyDescriptor<?> bpd : rowSpec) {
			String labelId = LABEL_WICKET_ID + String.valueOf(counter);
			String componentId = COMPONENT_WICKET_ID + String.valueOf(counter);
			
			Pair<Component, Component> pair = GenericBoPDComponentFactory.INSTANCE.draw(model.bind(bpd.getName()), bpd, componentId, labelId);
			row.add(pair.getLeft());
			row.add(pair.getRight());
			counter++;
		}
		
		/*
		 * Add empty components for the rest.
		 */
		for (int i=counter; i<columns; i++) {
			String labelId = LABEL_WICKET_ID + String.valueOf(i);
			String componentId = COMPONENT_WICKET_ID + String.valueOf(i);
			row.add(empty(labelId));
			row.add(empty(componentId));
		}
	}
	
	/**
	 * @return Returns the possibly cached markup. Calculates and caches if appropriate.
	 */
	String getMarkup() {
		String markup = MARKUPS.get(columns);
		if(markup == null) {
			markup = calculateMarkup();
			MARKUPS.putIfAbsent(columns, markup);
		}
		return markup;
	}
	
	/**
	 * Calculates the markup for the number of {@link #columns}.
	 * @return Returns the calculated markup.
	 */
	String calculateMarkup() {
		String head = null;
		String body = null;
		String tail = null;
		try {
			head = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_HEAD));
			body = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_BODY));
			tail = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_TAIL));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		String result = head;
		for(int i=0; i<columns; i++) {
			String body_i = body.replace(PLACEHOLDER_LABEL_WICKET_ID, LABEL_WICKET_ID+String.valueOf(i));
			body_i = body_i.replace(PLACEHOLDER_COMPONENT_WICKET_ID, COMPONENT_WICKET_ID+String.valueOf(i));
			result += body_i;
		}
		result += tail;
		return result;
	}

}
