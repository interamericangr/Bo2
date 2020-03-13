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
package gr.interamerican.wicket.utils;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Enumeration with the image types of a link.
 * 
 * @deprecated Use {@link EmbeddedImage} instead. Various utilities using it
 *             have been marked as deprecated with alternatives being offered on
 *             each
 */
@Deprecated
@SuppressWarnings("nls")
public enum ImageType {
	
	/** Edit Image. */	
	EDIT("images/edit.jpeg"),
	
	/** Delete image. */
	DELETE("images/delete1.jpeg"),
	
	/** COPY image. */
	COPY("images/copy.jpeg"),
	
	/** PREVIEW image. */
	PREVIEW("images/preview.jpeg"),
	
	/** ARROW_UP image. */
	ARROW_UP("images/arrowup.jpeg"),
	
	/** ARROW_DOWN image. */
	ARROW_DOWN("images/arrowdown.jpeg"),
		
	/** SEND_EMAIL image. */
	SEND_EMAIL("images/send_email.jpeg"),
	
	/** CHECK image. */
	CHECK("images/tick.jpeg"),
	
	/** Blank image. */
	BLANK("images/blankimage.jpeg"),
	
	/**
	 * wicket id of components using this Enum.
	 */
	WICKET_ID("editItemLink"){
		@Override
		public String toString() {
			return this.image;
		}
	};
	
	/** Image for this. */
	String image;

	/**
	 * Creates a new ImageType object. 
	 *
	 * @param image the image
	 */
	private ImageType(String image) {
		this.image = image;
	}
	
	/**
	 * Gets the image.
	 *
	 * @return Returns the image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Legacy wicket 1.4 implementation
	 * TODO: remove it.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return StringUtils.concat(
				"<input type=\"image\" wicket:id=\"editItemLink\" src=\"",
				image,
				"\"></input>");
	}
	
	/**
	 * To string.
	 *
	 * @param contextRelativeUrl the context relative url
	 * @return html fragment for image
	 */
	public String toString(String contextRelativeUrl) {
		return StringUtils.concat(
				"<input type=\"image\" wicket:id=\"editItemLink\" src=\"",
				contextRelativeUrl,
				"\"></input>");
	}
	
	
}
