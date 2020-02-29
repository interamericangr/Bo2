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

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.ResourceReference;

import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Panel containing an {@link Image}
 */
public class DataTableImagePanel extends Panel {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket Id for the Image
	 */
	private static final String IMAGE = "image"; //$NON-NLS-1$

	/**
	 * Creates a new {@link DataTableImagePanel} object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param imageType
	 *            the image type
	 * @deprecated Use Alternative Constructors.
	 */
	@Deprecated
	public DataTableImagePanel(String id, IModel<?> model, gr.interamerican.wicket.utils.ImageType imageType) {
		super(id, model);
		add(new Image(IMAGE, new ContextRelativeResource(imageType.getImage())));
	}

	/**
	 * Public Constructor.
	 *
	 *
	 * @param id
	 *            the id
	 * @param image
	 *            {@link EmbeddedImage} to use
	 */
	public DataTableImagePanel(String id, EmbeddedImage image) {
		this(id, null, image.getReference());
	}

	/**
	 * Public Constructor.
	 *
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            Model Object
	 * @param image
	 *            {@link EmbeddedImage} to use
	 */
	public DataTableImagePanel(String id, IModel<?> model, EmbeddedImage image) {
		this(id, model, image.getReference());
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 */
	public DataTableImagePanel(String id, ResourceReference reference) {
		this(id, null, reference);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param model
	 *            Model Object
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 */
	public DataTableImagePanel(String id, IModel<?> model, ResourceReference reference) {
		super(id, model);
		add(new Image(IMAGE, reference));
	}
}