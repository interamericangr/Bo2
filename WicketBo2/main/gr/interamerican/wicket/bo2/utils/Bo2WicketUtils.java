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
package gr.interamerican.wicket.bo2.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.workers.IteratorQuery;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.wicket.bo2.callbacks.AbstractCsvAction;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * Utilities for Bo2Wicket.
 */
public class Bo2WicketUtils {
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2WicketUtils.class);
	
	/**
	 * hidden constructor. 
	 * 
	 */
	private Bo2WicketUtils() {
		/* empty */
	}
	
	/**
	 * Returns a new CompoundPropertyModel with a new model object.
	 *
	 * @param <T> the generic type
	 * @param className the class name
	 * @return CompoundPropertyModel&lt;T&gt;
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public static  <T> CompoundPropertyModel<T> returnModel(Class<T> className){
		CompoundPropertyModel<T> cpm = null;
		/*
		 * if it is an interface, act normally.
		 */
		if(className.isInterface()) {
			cpm = new CompoundPropertyModel<T>(Factory.create(className));
		}
		/*
		 * if it is an implementation, then attempt to find the
		 * interface first, then create it with the Factory.
		 * If the type has not been created in the past, null
		 * will be returned. However, this can only happen by
		 * explicitly calling this method with an implementation class,
		 * e.g. FooImpl.class.
		 */
		else {
			Debug.debug(logger, "Argument class was not an interface: " + className.getName());
			Class<?> decl = Factory.getCurrentFactory().getDeclarationType(className);
			if(decl != null) {
				cpm = new CompoundPropertyModel<T>((T) Factory.create(decl));
			} else {
				cpm = null;
			}
			
		}
		return cpm;	
	}

	/**
	 * Adds a {@link LegacyCallbackAction} to be called after the existing
	 * Actions on a {@link CrudPickerPanelDef} on save, update and delete.
	 * 
	 * @param def
	 *            Definition to add the second action
	 * @param action
	 *            Second Action to Add
	 */
	public static <B extends Serializable> void addActionAfterSaveUpdateDelete(CrudPickerPanelDef<B> def,
			LegacyCallbackAction action) {
		def.setSaveAction(def.getSaveAction().chainAfter(action));
		def.setUpdateAction(def.getUpdateAction().chainAfter(action));
		def.setDeleteAction(def.getDeleteAction().doChainAfter(action));
	}

	/**
	 * Creates a chained action that consists of 2 actions.
	 * 
	 * @param firstAction
	 *            First Action
	 * @param secondAction
	 *            Second Action
	 * @return New ChainedCallbackAction
	 */
	@Deprecated
	public static gr.interamerican.wicket.callback.ChainedCallbackAction createChainedAction(
			gr.interamerican.wicket.callback.CallbackAction firstAction,
			gr.interamerican.wicket.callback.CallbackAction secondAction) {
		gr.interamerican.wicket.callback.ChainedCallbackAction returned = new gr.interamerican.wicket.callback.ChainedCallbackActionImpl(
				firstAction);
		returned.chainAfter(secondAction);
		return returned;
	}

	/**
	 * Creates a file for download based on the input.<br>
	 * The input is basically an input List - essentially the content of the
	 * File, and various setup elements for the
	 * {@link gr.interamerican.bo2.impl.open.operations.EntitiesQuery2CsvPrintStreamOperation}
	 * that is being executed by this.
	 *
	 * @param input
	 *            The Utility Input
	 * @deprecated Use one of the sub-classes on {@link AbstractCsvAction} instead
	 */
	@Deprecated
	public static final void createDownloadFileFromList(DownloadFileFromListInput input) {
		if (CollectionUtils.isNullOrEmpty(input.getList())) {
			return;
		}
		try {
			List<Object> list = Utils.cast(input.getList());
			EntitiesQuery<Object> query = new IteratorQuery<Object>(list.iterator());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream output = new PrintStream(out, false, Bo2UtilsEnvironment.get().getDefaultTextCharset().name());
			gr.interamerican.bo2.impl.open.operations.EntitiesQuery2CsvPrintStreamOperation<Object, EntitiesQuery<Object>> toCsv = new gr.interamerican.bo2.impl.open.operations.EntitiesQuery2CsvPrintStreamOperation<Object, EntitiesQuery<Object>>(
					query, input.getPropertiesToExport(), input.getSpecialFormatters(), input.getColumnLabels(),
					output);
			toCsv.setFirstRows(input.getFirstRows());
			toCsv.setLastRows(input.getLastRows());
			Bo2WicketRequestCycle.execute(toCsv);
			WicketUtils.sendFileForDownload(out.toByteArray(), input.getDownloadedFileName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}