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
package gr.interamerican.wicket.bo2.callbacks;

import java.util.List;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.wicket.bo2.utils.Bo2WicketUtils;
import gr.interamerican.wicket.bo2.utils.DownloadFileFromListBean;
import gr.interamerican.wicket.bo2.utils.DownloadFileFromListInput;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDef;

/**
 * This action will export the elements of a {@link ListTablePanel} to a
 * csv.<br>
 * It works on a similar way to {@link List2CsvAction}, but instead of using the
 * {@link List} of the input {@link DownloadFileFromListInput}, it will use the
 * one provided on {@link ListTablePanelDef}.
 * 
 * @deprecated Functionality is Embedded to {@link ListTablePanel} now
 */
@Deprecated
public class ListTable2CsvAction 
implements SerializableRunnable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** Definition of the {@link ListTablePanelDef}. */
	private ListTablePanelDef<?> def;
	
	/** Input of the Action. */
	private DownloadFileFromListInput input;
	
	/**
	 * Creates a new List2CsvAction object.
	 * 
	 * @param input
	 *            Input of the Action
	 * @param def
	 *            Definition of the {@link ListTablePanelDef}
	 */
	public ListTable2CsvAction(DownloadFileFromListInput input, ListTablePanelDef<?> def) {
		this.input = input;
		this.def = def;
	}

	@Override
	public void run() {
		DownloadFileFromListBean bean = Factory.create(DownloadFileFromListBean.class);
		ReflectionUtils.copyPropertiesExcluding(input, bean, new String[] { "list" }); //$NON-NLS-1$
		bean.setList(def.getList());
		Bo2WicketUtils.createDownloadFileFromList(bean);
	}
}