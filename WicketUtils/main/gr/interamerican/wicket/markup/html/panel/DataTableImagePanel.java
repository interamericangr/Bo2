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
package gr.interamerican.wicket.markup.html.panel;

import static gr.interamerican.bo2.utils.StringUtils.doubleQuotes;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;
import gr.interamerican.wicket.utils.ImageType;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Panel containing a textField.
 * 
 */
public class DataTableImagePanel extends Panel 
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	private static final String IMAGE_ID = "image"; //$NON-NLS-1$
	
	/**
	 * ImageType.
	 */
	protected ImageType imageType = null;
	
	/**
	 * Creates a new {@link DataTableImagePanel} object. 
	 *
	 * @param id
	 * @param model
	 * @param imageType 
	 */
	public DataTableImagePanel(String id, IModel<?> model, ImageType imageType) {
		super(id, model);
		this.imageType = imageType; 
		Image field = new Image(IMAGE_ID,  new ContextRelativeResource(imageType.getImage()));
		add(field);
	}
	
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> clazz) {
		@SuppressWarnings("nls")
		String html = StringUtils.concat(
			"<html><body><wicket:panel>", 
			"<img wicket:id=",doubleQuotes(IMAGE_ID),"></img>",
			"</wicket:panel></body></html>"); 
		return new StringAsResourceStream(html);
	}
	
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}

}
