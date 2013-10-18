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
package gr.interamerican.bo2.gui.layout;

import java.awt.Dimension;

/**
 * 
 */
public class Sizes {
	/**
	 * Height of a form component.
	 */
	public static final int FORM_COMPONENT_HEIGHT = 24;
	/**
	 * Height of a form component.
	 */
	public static final int FORM_COMPONENT_CHAR_WIDTH = 9;
	
	/**
	 * Default pane size.
	 */
	public static final Dimension PANEL = new Dimension(1250, 800);
	/**
	 * Default pane size.
	 */
	public static final Dimension ROW_PANEL = 
		new Dimension(1250, FORM_COMPONENT_HEIGHT);
	
	/**
	 * Dimension for tables.
	 */
	public static Dimension TABLE_SIZE = new Dimension(1230, 600);

	
	/**
	 * Default size for form components.
	 */
	public static final Dimension FORM_COMPONENT = square(10, 1);
		
	/**
	 * Default size for form components.
	 */
	public static final Dimension SMALL_CHECKBOX = square(2, 1);
	
	
	
		
	/**
	 * width expressed relatively to default form component height.
	 *  
	 * @param length
	 *  
	 * @return Returns the width. 
	 */
	public static int width(int length) {
		return length * FORM_COMPONENT_CHAR_WIDTH;
	}
	
	/**
	 * width expressed relatively to default form component height.
	 *  
	 * @param length
	 *  
	 * @return Returns the width. 
	 */
	public static int height(int length) {
		return length * FORM_COMPONENT_HEIGHT;
	}
	
	/**
	 * dimension for text fields
	 * @param length 
	 * @return Returns the dimension.
	 */
	public static Dimension fieldSize(int length) {
		return new Dimension(width(length), FORM_COMPONENT_HEIGHT);
	}
	
	/**
	 * Creates the dimension of a square.
	 * 
	 * @param w
	 *        Width in characters.
	 * @param h
	 *        Height in characters.
	 * @param adjustHeight 
	 *        Indicates if the height has to be increased.
	 *        
	 * @return Returns the Dimension for the specified square.
	 */
	public static Dimension square(int w, int h, boolean adjustHeight) {
		int ah = height(h);		
		if (adjustHeight) {
			ah = ah + 14;
		}		 
		return new Dimension(width(w), ah);
	}
	
	/**
	 * Creates the dimension of a square.
	 * 
	 * @param w
	 *        Width in characters.
	 * @param h
	 *        Height in characters.
	 *        
	 * @return Returns the Dimension for the specified square.
	 */
	public static Dimension square(int w, int h) {
		return square(w, h, false);
	}
	
	/**
	 * Factory method.
	 * 
	 * @param w
	 * @param h
	 * 
	 * @return Returns a new Dimension object.
	 */
	public static Dimension dimension(double w, double h) {
		int wi = Double.valueOf(w).intValue();
		int hi = Double.valueOf(h).intValue();
		return new Dimension(wi,hi);
	}
	
	/**
	 * Creates a new Dimension that is equivalent to the specified
	 * dimensions stacked one on the other. <br/>
	 * 
	 * The resulting dimension has width equal to the maximum width
	 * found in the specified dimensions and height equal to the sum
	 * of heights of the specified dimensions.
	 * 
	 * @param dimensions
	 *        Dimensions to add as a stack.
	 *  
	 * @return Returns a new dimension that has enough space for all the
	 *         specified dimensions to be stacked in.
	 */
	public static Dimension stack(Dimension...dimensions) {
		double w=0.0;
		double h=0.0;
		for (Dimension d : dimensions) {
			if (w<d.getWidth()) {
				w = d.getWidth();
			}
			h = h + d.getHeight();
		}
		return dimension(w, h);
	}
	
	/**
	 * Creates a new Dimension sized relevant to the specified 
	 * dimension and increased according to the specified
	 * width and height. <br/>
	 *  
	 * @param size
	 *        Reference dimension.
	 * @param w
	 *        Width to increase the specified dimension.
	 *        If this is a negative number, then the resulting
	 *        dimension will have less width than the reference
	 *        one.
	 * @param h
	 *        Height to increase the specified dimension.
	 *        If this is a negative number, then the resulting
	 *        dimension will have less height than the reference
	 *        one.
	 *        
	 * @return Returns a new dimension with height and width resulting
	 *         from the addition of the specified dimension's height
	 *         and width with the specified height and width.
	 */
	public static Dimension increase(Dimension size, int w, int h) {
		double hh = size.getHeight()+h;
		double ww = size.getWidth()+w;
		return dimension(ww, hh);
	}
		
		
	
	
	
	
}
