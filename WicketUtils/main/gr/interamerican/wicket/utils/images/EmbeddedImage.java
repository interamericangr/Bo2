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
package gr.interamerican.wicket.utils.images;

import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Enumeration with some images embedded in bo2.
 */
@SuppressWarnings("nls")
public enum EmbeddedImage {

	/** Edit Image. */
	EDIT("edit.jpeg"),

	/** Delete image. */
	DELETE("delete1.jpeg"),

	/** COPY image. */
	COPY("copy.jpeg"),

	/** PREVIEW image. */
	PREVIEW("preview.jpeg"),

	/** ARROW_UP image. */
	ARROW_UP("arrowup.jpeg"),

	/** ARROW_DOWN image. */
	ARROW_DOWN("arrowdown.jpeg"),

	/** SEND_EMAIL image. */
	SEND_EMAIL("send_email.jpeg"),

	/** CHECK image. */
	CHECK("tick.jpeg"),

	/** Blank image. */
	BLANK("blankimage.jpeg"),

	/** Add New. */
	ADD_NEW("addNew.jpeg"),

	/** Delete image. */
	DELETE_ALTERNATIVE("delete.jpeg"),

	/** Contacts icon. */
	CONTACTS("contacts.jpeg");

	/** ResourceReference of this */
	private final PackageResourceReference reference;

	/**
	 * Constructor.
	 *
	 * @param image
	 *            the image
	 */
	private EmbeddedImage(String image) {
		this.reference = new PackageResourceReference(EmbeddedImage.class, image);
	}

	/**
	 * Gets the reference.
	 *
	 * @return Returns the reference
	 */
	public PackageResourceReference getReference() {
		return reference;
	}
}