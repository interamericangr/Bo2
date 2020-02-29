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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.wicket.bo2.callbacks.List2CsvAction;
import gr.interamerican.wicket.bo2.utils.DownloadFileFromListBean;
import gr.interamerican.wicket.creators.PropertiesBasedDataTableCreator;
import gr.interamerican.wicket.links.RunnableLink;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDef;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDefImpl;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * This Panel Contains a {@link ListTablePanel} + an {@link Link} that exports
 * the contents of the table to a CSV File.<br>
 * The Labels of the properties to be displayed are searched with resourceId as
 * the name of the column property + "Label" (i.e. fooLabel for property foo),
 * and the supplied Parent Component is being used.<br>
 * The Label of the {@link ListTablePanel} is searched with resourceId the
 * WicketId of the panel + "Label".
 * 
 * @param <B>
 *            Type of entries to display
 * @deprecated Functionality is Embedded to {@link ListTablePanel} now
 */
@Deprecated
public class ListTablePanelWithExport<B extends Serializable> 
extends Panel {

	/** Serial Version UID. */
	private static final long serialVersionUID = 1L;
	
	/** Wicket id for the ExportCsvLink. */
	public final String EXPORT_CSV_LINK_WICKET_ID = "exportCsvLink"; //$NON-NLS-1$
	
	/** Wicket id for the ListTablePanel. */
	public final String LIST_TABLE_PANEL_WICKET_ID = "listTablePanel"; //$NON-NLS-1$
	
	/** Label Text. */
	private static final String LABEL = "Label"; //$NON-NLS-1$

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param clz
	 *            Class of the Entities to Display
	 * @param entities
	 *            Entities to display or download
	 * @param propertiesToExport
	 *            Properties of the Entities to display
	 * @param fileName
	 *            Name of the to be down-loaded file
	 * @param parentComponent
	 *            The Parent Component From Which we'll get
	 */
	public ListTablePanelWithExport(String id, Class<B> clz, List<B> entities, String[] propertiesToExport,
			String fileName, Component parentComponent) {
		super(id);
		// Create the ListTablePanel
		String[] labels = WicketUtils.convertStringResourceModels(propertiesToExport, parentComponent, LABEL);
		ListTablePanelDef<B> listPanelDef = new ListTablePanelDefImpl<B>();
		listPanelDef.setWicketId(LIST_TABLE_PANEL_WICKET_ID);
		listPanelDef.setListLabelModel(new StringResourceModel(id.concat(LABEL), parentComponent).setDefaultValue(id));
		listPanelDef.setList(entities);
		listPanelDef.setDataTableCreator(new PropertiesBasedDataTableCreator<B>(clz, propertiesToExport, labels));
		add(new ListTablePanel<B>(listPanelDef));
		// Create the Download to CSV Link
		DownloadFileFromListBean bean = Factory.create(DownloadFileFromListBean.class);
		bean.setList(entities);
		bean.setDownloadedFileName(fileName);
		bean.setColumnLabels(labels);
		bean.setPropertiesToExport(propertiesToExport);
		add(new RunnableLink(EXPORT_CSV_LINK_WICKET_ID, new List2CsvAction(bean)));
	}
}