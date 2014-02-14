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

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.descriptors.TranslatableBoPropertyDescriptorWrapper;
import gr.interamerican.wicket.bo2.factories.meta.GenericBoPDComponentFactory;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntry;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
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
public class SelfDrawnPanel<T extends Serializable> extends Panel
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket id of Panel's label.
	 */
	private static final String TITLE_LABEL_WICKET_ID = "titleLabel"; //$NON-NLS-1$
	
	/**
	 * Wicket id of label.
	 */
	private static final String LABEL_WICKET_ID = "label"; //$NON-NLS-1$

	/**
	 * Wicket id of empty component.
	 */
	private static final String COMPONENT_WICKET_ID = "component"; //$NON-NLS-1$

	/**
	 * Name of index property of {@link BoPropertyDescriptor}. This is used to
	 * sort the components within the panel.
	 */
	private static final String INDEX_PROPERTY_NAME = "index"; //$NON-NLS-1$
	
	/**
	 * Name of name property of {@link BoPropertyDescriptor}. This is used to
	 * generate a unique for the BusinessObjectDescriptor. 
	 */
	private static final String NAME_PROPERTY_NAME = "name"; //$NON-NLS-1$

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
	private static final String MARKUP_HEAD = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnPanel_head.txt"; //$NON-NLS-1$
	
	/**
	 * Column portion of markup. This is repeated for the number of {@link #columns} in a row.
	 */
	private static final String MARKUP_COLUMN = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnPanel_column.txt"; //$NON-NLS-1$
	
	/**
	 * Bottom portion of markup.
	 */
	private static final String MARKUP_TAIL = "/gr/interamerican/wicket/bo2/markup/html/panel/SelfDrawnPanel_tail.txt"; //$NON-NLS-1$
	
	/**
	 * head of a row
	 */
	private static final String ROW_HEAD = "<tr style=\"height:23px;\">"; //$NON-NLS-1$
	
	/**
	 * bottom of a row
	 */
	private static final String ROW_BOTTOM = "</tr>"; //$NON-NLS-1$
	
	/**
	 * Calculated markups cache.
	 */
	static final ConcurrentHashMap<String, String> MARKUPS = new ConcurrentHashMap<String, String>(); 

	/**
	 * Model
	 */
	private final CompoundPropertyModel<T> model;
	
	/**
	 * Number of columns for the grid.
	 */
	private final int columns;
	
	/**
	 * Markup key for caching.
	 */
	private final String markupKey;

	/**
	 * Creates a new SelfDrawnGridPanel object.
	 * 
	 * @param id
	 * @param model
	 * @param boDescriptor
	 */
	public SelfDrawnPanel(String id, CompoundPropertyModel<T> model, BusinessObjectDescriptor<T> boDescriptor) {
		this(id, model, boDescriptor, 1);
	}
	
	/**
	 * Creates a new SelfDrawnGridPanel object.
	 * 
	 * @param id
	 * @param model
	 * @param boDescriptor
	 * @param columns
	 */
	public SelfDrawnPanel(String id, CompoundPropertyModel<T> model, BusinessObjectDescriptor<T> boDescriptor, int columns) {
		super(id, model);

		setOutputMarkupPlaceholderTag(true);

		this.model = model;
		this.columns = columns;
		this.markupKey = markupKey(boDescriptor);

		String labelStr = boDescriptor.getLabel();
		Label label = new Label(TITLE_LABEL_WICKET_ID, labelStr);
		if (StringUtils.isNullOrBlank(labelStr)) {
			label.setVisible(false);
		}
		
		List<BoPropertyDescriptor<?>> specs = boDescriptor.getPropertyDescriptors();
		specs = CollectionUtils.sort(specs, BoPropertyDescriptor.class, INDEX_PROPERTY_NAME);
		List<List<BoPropertyDescriptor<?>>> rowSpecs = CollectionUtils.partition(specs, columns);
		
		String markup = MARKUPS.get(markupKey);
		if(markup == null) {
			markup = calculateMarkup(rowSpecs);
			MARKUPS.putIfAbsent(markupKey, markup);
		}
		
		for (List<BoPropertyDescriptor<?>> rowSpec : rowSpecs) {
			populateRow(rowSpec);
		}
		
		add(label);
		
		addOnChangeBehaviorsOnAffectingDropDowns(boDescriptor);
	}
	
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return null; //no caching
	}

	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		return new StringAsResourceStream(MARKUPS.get(markupKey));
	}
	
	/**
	 * Calculate a unique key based on the BusinessObjectDescriptor to facilitate
	 * markup caching.
	 * 
	 * @param businessObjectDescriptor
	 * 
	 * @return Unique key.
	 */
	String markupKey(BusinessObjectDescriptor<T> businessObjectDescriptor) {
		List<BoPropertyDescriptor<?>> propertyDescriptors = CollectionUtils.sort(
				businessObjectDescriptor.getPropertyDescriptors(), BoPropertyDescriptor.class, NAME_PROPERTY_NAME);
		
		StringBuilder sb = new StringBuilder();
		sb.append(columns + StringConstants.COLON);
		sb.append(businessObjectDescriptor.getName() + StringConstants.COLON);
		for(BoPropertyDescriptor<?> bpd : propertyDescriptors) {
			sb.append(bpd.getName());
		}
		
		return sb.toString();
	}
	
	/**
	 * Populate the WebMarkupContainer that encapsulates a single row.
	 * @param rowSpec
	 */
	void populateRow(List<BoPropertyDescriptor<?>> rowSpec) {
		int counter = 0;
		for (BoPropertyDescriptor<?> spec : rowSpec) {
			String componentId = spec.getName();
			String labelId = labelWicketIdWithPropertyName(spec.getName());
			
			Pair<Component, Component> pair = GenericBoPDComponentFactory.INSTANCE.draw(model.bind(spec.getName()), spec, componentId, labelId);
			add(pair.getLeft());
			add(pair.getRight());
			counter++;
		}
		
		/*
		 * Add empty components for the rest.
		 */
		for (int i=counter; i<columns; i++) {
			add(empty(labelWicketIdWithColumn(i)));
			add(empty(componentWicketIdWithColumn(i)));
		}
	}
	
	/**
	 * Calculates the markup for this panel.
	 * 
	 * @param rowSpecs
	 *  
	 * @return Returns the calculated markup.
	 */
	String calculateMarkup(List<List<BoPropertyDescriptor<?>>> rowSpecs) {
		String head = null;
		String body = null;
		String tail = null;
		try {
			head = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_HEAD));
			body = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_COLUMN));
			tail = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP_TAIL));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(head);
		sb.append(StringConstants.NEWLINE);
		
		for (List<BoPropertyDescriptor<?>> rowSpec : rowSpecs) {
			sb.append(rowMarkup(rowSpec, body));
			sb.append(StringConstants.NEWLINE);
		}
		
		sb.append(tail);
		return sb.toString();
	}
	
	/**
	 * Creates the static markup for a single row.
	 * 
	 * @param rowSpec
	 * @param body
	 * @return Row markup.
	 */
	String rowMarkup(List<BoPropertyDescriptor<?>> rowSpec, String body) {
		StringBuilder sb = new StringBuilder();
		sb.append(ROW_HEAD);
		sb.append(StringConstants.NEWLINE);
		
		int counter = 0;
		for (BoPropertyDescriptor<?> spec : rowSpec) {
			String body_row = body.replace(PLACEHOLDER_LABEL_WICKET_ID, labelWicketIdWithPropertyName(spec.getName()));
			body_row = body_row.replace(PLACEHOLDER_COMPONENT_WICKET_ID, spec.getName());
			sb.append(body_row);
			sb.append(StringConstants.NEWLINE);
			counter++;
		}
		
		/*
		 * Add empty markup placeholders for the rest.
		 */
		for (int i=counter; i<columns; i++) {
			String body_row = body.replace(PLACEHOLDER_LABEL_WICKET_ID, labelWicketIdWithColumn(i));
			body_row = body_row.replace(PLACEHOLDER_COMPONENT_WICKET_ID, componentWicketIdWithColumn(i));
			sb.append(body_row);
			sb.append(StringConstants.NEWLINE);
		}
		
		sb.append(ROW_BOTTOM);
		
		return sb.toString();
	}
	
	/**
	 * This is not part of the public API.
	 * 
	 * The wicketId of the label of the form component for the specified property
	 * 
	 * @param propertyName
	 *        Name of the property
	 *        
	 * @return Wicket id of the label.
	 */
	public static String labelWicketIdWithPropertyName(String propertyName) {
		return propertyName + LABEL_WICKET_ID;
	}
	
	/**
	 * The wicketId of the label for a column that is empty.
	 * 
	 * @param column
	 *        Column number 
	 * 
	 * @return Wicket id of the empty label.
	 */
	String labelWicketIdWithColumn(int column) {
		return LABEL_WICKET_ID + column; 
	}
	
	/**
	 * The wicketId of the component for a column that is empty.
	 * 
	 * @param column
	 *        Column number
	 *        
	 * @return Wicket id of the empty component.
	 */
	String componentWicketIdWithColumn(int column) {
		return COMPONENT_WICKET_ID + column; 
	}
	
	/**
	 * Creates an empty invisible label.
	 * 
	 * @param id
	 *            Id of the label.
	 * 
	 * @return Returns an empty invisible label.
	 */
	Label empty(String id) {
		Label l = new Label(id);
		l.setVisible(false);
		return l;
	}
	
	/**
	 * Resolves DropDownChoices that affect other DropDownChoices and adds ajax
	 * 'onchange' behaviors. 
	 *  
	 * @param boDescriptor
	 */
	void addOnChangeBehaviorsOnAffectingDropDowns(BusinessObjectDescriptor<T> boDescriptor) {
		for(BoPropertyDescriptor<?> bpd : boDescriptor.getPropertyDescriptors()) {
			if(!StringUtils.isNullOrBlank(bpd.getAffected())) {
				addOnChangeBehaviorOnAffectingDropDown(boDescriptor, bpd.getName(), bpd.getAffected());
			}
		}
	}

	/**
	 * Adds an 'onchange' behavior on a DropDownChoice of this panel that
	 * affects another DropDownChoice of this panel.
	 * 
	 * @param boDescriptor
	 * @param affecting
	 * @param affected
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	<E extends TranslatableEntry<Long, ?, Long>> void addOnChangeBehaviorOnAffectingDropDown(
			BusinessObjectDescriptor<T> boDescriptor, String affecting, String affected) {
		DropDownChoiceForEntry<Long, E> affectingDdc = (DropDownChoiceForEntry<Long, E>) get(affecting);
		DropDownChoiceForEntry<Long, E> affectedDdc = (DropDownChoiceForEntry<Long, E>) get(affected);
		BoPropertyDescriptor<?> unchecked = boDescriptor.getDescriptorByName(affected);
		if (unchecked instanceof TranslatableBoPropertyDescriptorWrapper) {
			unchecked = ((TranslatableBoPropertyDescriptorWrapper<?, ?, ?>) unchecked).getDescriptor();
		}
		if (!(unchecked instanceof CachedEntryBoPropertyDescriptor)) {
			String msg = "Invalid BusinessObjectDescriptor affected property descriptors setup: "
					+ boDescriptor.getName();
			throw new RuntimeException(msg);
		}
		CachedEntryBoPropertyDescriptor<E, ?> affectedDescriptor = (CachedEntryBoPropertyDescriptor<E, ?>) unchecked;
		affectingDdc.add(new AffectingDdcBehavior<E>(affectingDdc, affectedDdc, affectedDescriptor));
	}
	
	/**
	 * Behavior that will affect the choices of a Drop Down Choice of this panel
	 * based on the selection performed on another Drop Down Choice of this panel
	 * that affects the first.
	 * 
	 * @param <E> Entry type. This is hacky, not both DDCs have to have the same type.
	 */
	static class AffectingDdcBehavior<E extends TranslatableEntry<Long, ?, Long>> extends AjaxFormComponentUpdatingBehavior {
		
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Affecting drop down choice
		 */
		DropDownChoiceForEntry<Long,E> affectingDdc;
		
		/**
		 * Affected drop down choice
		 */
		DropDownChoiceForEntry<Long,E> affectedDdc;
		
		/**
		 * CachedEntryBoPropertyDescriptor of the affected drop down choice
		 */
		CachedEntryBoPropertyDescriptor<E,?> affectedDescriptor;
		
		/**
		 * Creates a new SelfDrawnPanel.AffectingDdcBehavior object. 
		 * @param affectedDdc 
		 * @param affectedDescriptor 
		 * @param affectingDdc 
		 */
		public AffectingDdcBehavior(DropDownChoiceForEntry<Long,E> affectingDdc, DropDownChoiceForEntry<Long,E> affectedDdc, CachedEntryBoPropertyDescriptor<E,?> affectedDescriptor) {
			super("onchange"); //$NON-NLS-1$
			this.affectedDdc = affectedDdc;
			this.affectedDescriptor = affectedDescriptor;
			this.affectingDdc = affectingDdc;
		}
		
		@Override
		protected void onUpdate(AjaxRequestTarget target) {
			target.addComponent(affectedDdc);
			E affectingChoice = affectingDdc.getModelObject();
			Long affectedSubListCd = null;
			if(affectingChoice != null) {
				affectedSubListCd = affectingChoice.getCode();
			}
			Set<E> choices = affectedDescriptor.getValues(affectedSubListCd);
			List<E> choicesList = new ArrayList<E>(choices);
			affectedDdc.setChoices(choicesList);
			SelfDrawnUtils.<Long, E>sortCachedEntries(affectedDdc);
		}
		
	}

}
