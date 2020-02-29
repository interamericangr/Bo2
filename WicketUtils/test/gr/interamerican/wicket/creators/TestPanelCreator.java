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
package gr.interamerican.wicket.creators;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDefImpl;

/**
 * Unit tests for {@link PanelCreator}
 */
public class TestPanelCreator {

	/**
	 * Wicket Id passed from {@link #withMode(String, IModel, PanelCreatorMode)}
	 * or {@link #withoutMode(String, IModel)}
	 */
	private String t;

	/**
	 * Model passed from {@link #withMode(String, IModel, PanelCreatorMode)} or
	 * {@link #withoutMode(String, IModel)}
	 */
	private IModel<String> u;
	/**
	 * {@link PanelCreator} passed from
	 * {@link #withMode(String, IModel, PanelCreatorMode)}
	 */
	private PanelCreatorMode mode;

	/**
	 * Resets the values of {@link #t}, {@link #u}, {@link #mode}.
	 */
	@Before
	public void setup() {
		t = null;
		u = null;
		mode = null;
	}

	/**
	 * Gets a {@link ModeAwareBeanPanelDef}.
	 * 
	 * @param id
	 *            Id
	 * @param model
	 *            Model
	 * @param m
	 *            {@link PanelCreatorMode}
	 * @return New {@link ModeAwareBeanPanelDef}
	 */
	private ModeAwareBeanPanelDef<String> getDefinition(String id, IModel<String> model, PanelCreatorMode m) {
		ModeAwareBeanPanelDef<String> def = new ModeAwareBeanPanelDefImpl<>();
		def.setWicketId(id);
		def.setBeanModel(model);
		def.setBeanFieldsPanelMode(m);
		return def;
	}

	/**
	 * Method that Implements {@link SimplePanelConstructor} and
	 * {@link SimpleCompoundPanelConstructor} and sets the values passed to
	 * {@link #t}, {@link #u}.
	 * 
	 * @param in
	 *            Id
	 * @param model
	 *            Model
	 * @return Whatever Panel
	 */
	Panel withoutMode(String in, IModel<String> model) {
		return withMode(in, model, null);
	}

	/**
	 * Method that Implements {@link PanelConstructor} and
	 * {@link CompoundPanelConstructor} and sets the values passed to
	 * {@link #t}, {@link #u} and {@link #mode}.
	 * 
	 * @param in
	 *            Id
	 * @param model
	 *            Model
	 * @param inMode
	 *            In {@link PanelCreatorMode}
	 * @return Whatever Panel
	 */
	Panel withMode(String in, IModel<String> model, PanelCreatorMode inMode) {
		this.t = in;
		this.u = model;
		this.mode = inMode;
		return null;
	}

	/**
	 * Test method for {@link PanelCreator#getCreator(SimplePanelConstructor)}.
	 */
	@Test
	public void testGetCreatorSimplePanelConstructorOfT() {
		PanelCreator<String> tested = PanelCreator.getCreator(this::withoutMode);
		String id = UUID.randomUUID().toString();
		String modelObject = UUID.randomUUID().toString();
		tested.createPanel(getDefinition(id, new Model<>(modelObject), PanelCreatorMode.VIEW));
		assertEquals(id, t);
		assertEquals(modelObject, u.getObject());
		assertNull(mode);
	}

	/**
	 * Test method for {@link PanelCreator#getCreator(PanelConstructor)}.
	 */
	@Test
	public void testGetCreatorPanelConstructorOfT() {
		PanelCreator<String> tested = PanelCreator.getCreator(this::withMode);
		String id = UUID.randomUUID().toString();
		String modelObject = UUID.randomUUID().toString();
		tested.createPanel(getDefinition(id, new Model<>(modelObject), PanelCreatorMode.CREATE));
		assertEquals(id, t);
		assertEquals(modelObject, u.getObject());
		assertEquals(PanelCreatorMode.CREATE, mode);
	}

	/**
	 * Test method for
	 * {@link PanelCreator#getCompoundCreator(SimpleCompoundPanelConstructor)}.
	 */
	@Test
	public void testGetCompoundCreatorSimpleCompoundPanelConstructorOfT() {
		PanelCreator<String> tested = PanelCreator.getCompoundCreator(this::withoutMode);
		String id = UUID.randomUUID().toString();
		String modelObject = UUID.randomUUID().toString();
		tested.createPanel(getDefinition(id, new Model<>(modelObject), PanelCreatorMode.CREATE));
		assertEquals(id, t);
		assertEquals(modelObject, u.getObject());
		assertTrue(u instanceof CompoundPropertyModel);
		assertNull(mode);
		IModel<String> compound = new CompoundPropertyModel<>("compound"); //$NON-NLS-1$
		tested.createPanel(getDefinition(id, compound, PanelCreatorMode.CREATE));
		assertSame(compound, u);
	}

	/**
	 * Test method for
	 * {@link PanelCreator#getCompoundCreator(CompoundPanelConstructor)}.
	 */
	@Test
	public void testGetCompoundCreatorCompoundPanelConstructorOfT() {
		PanelCreator<String> tested = PanelCreator.getCompoundCreator(this::withMode);
		String id = UUID.randomUUID().toString();
		String modelObject = UUID.randomUUID().toString();
		tested.createPanel(getDefinition(id, new Model<>(modelObject), PanelCreatorMode.VIEW));
		assertEquals(id, t);
		assertEquals(modelObject, u.getObject());
		assertTrue(u instanceof CompoundPropertyModel);
		assertEquals(PanelCreatorMode.VIEW, mode);
		IModel<String> compound = new CompoundPropertyModel<>("w/e"); //$NON-NLS-1$
		tested.createPanel(getDefinition(id, compound, PanelCreatorMode.CREATE));
		assertSame(compound, u);
	}
}