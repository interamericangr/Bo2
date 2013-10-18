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
package gr.interamerican.bo2.odftoolkit.span;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
@SuppressWarnings("nls")
public class TestTextSpanFragment {


	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_onePhrase() {
		String txt = "this is it";
		TextSpanFragment tsf = new TextSpanFragment(txt);
		Assert.assertEquals(1, tsf.fragments.size());
	}

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_twoPhrases() {
		String txt = "this is    the place";
		TextSpanFragment tsf = new TextSpanFragment(txt);
		Assert.assertEquals(3, tsf.fragments.size());
		SpanFragment[] fragments = tsf.fragments.toArray(new SpanFragment[0]);

		Assert.assertTrue(fragments[0] instanceof PhraseSpanFragment);
		PhraseSpanFragment f0 = (PhraseSpanFragment) fragments[0];
		Assert.assertEquals("this is", f0.text);

		Assert.assertTrue(fragments[1] instanceof SpaceSpanFragment);
		SpaceSpanFragment f1 = (SpaceSpanFragment) fragments[1];
		Assert.assertEquals(4, f1.length);

		Assert.assertTrue(fragments[2] instanceof PhraseSpanFragment);
		PhraseSpanFragment f2 = (PhraseSpanFragment) fragments[2];
		Assert.assertEquals("the place", f2.text);



	}


}
