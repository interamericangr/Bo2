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
package gr.interamerican.wicket.model;

import gr.interamerican.bo2.utils.beans.Range;

import org.apache.wicket.model.IModel;

/**
 * A {@link IModel} for a specific object type that will wrap it to an input {@link IModel}
 * concerning {@link Range} of that object type.
 * 
 * @param <T>
 *            Type of Actual Object
 */
public class RangeModel<T extends Comparable<? super T>> implements IModel<T> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 524452L;

	/**
	 * Model of {@link Range}
	 */
	private IModel<Range<T>> iModel;

	/**
	 * If this is for the left element of the {@link Range}
	 */
	private boolean isLeft;

	/**
	 * Public Constructor.
	 * 
	 * @param iModel
	 *            Model of {@link Range}
	 * @param isLeft
	 *            If this is for the left element of the {@link Range}
	 */
	public RangeModel(IModel<Range<T>> iModel, boolean isLeft) {
		this.iModel = iModel;
		this.isLeft = isLeft;
	}

	@Override
	public void detach() {
		iModel.detach();
	}

	@Override
	public T getObject() {
		Range<T> range = iModel.getObject();
		if (range != null) {
			return isLeft ? range.getLeft() : range.getRight();
		}
		return null;
	}

	@Override
	public void setObject(T object) {
		Range<T> range = iModel.getObject();
		if (range == null) {
			range = new Range<T>();
			iModel.setObject(range);
		}
		if (isLeft) {
			range.setLeft(object);
		} else {
			range.setRight(object);
		}
	}
}