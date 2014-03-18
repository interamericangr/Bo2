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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.Copier;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.arch.utils.copiers.DateCopier;
import gr.interamerican.bo2.arch.utils.copiers.ImmutableObjectCopier;
import gr.interamerican.bo2.arch.utils.copiers.KeyCopier;
import gr.interamerican.bo2.arch.utils.copiers.MoneyCopier;
import gr.interamerican.bo2.arch.utils.copiers.NullCopier;
import gr.interamerican.bo2.impl.open.transformations.Copy;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Utilities for copying persistent objects.
 */
public class PoCopier {


	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PoUtils.class);

	/**
	 * Copiers.
	 */
	TypeBasedSelection<Copier<?>> copiers = new TypeBasedSelection<Copier<?>>();

	/**
	 * Singleton instance.
	 */
	private static final PoCopier copier = new PoCopier();

	/**
	 * Transformation before copying a persistent object.
	 */
	private Modification<PersistentObject<?>> onCopyPo = null;

	/**
	 * Transformation before copying a persistent object that is annotated
	 * as child.
	 */
	private Modification<PersistentObject<?>> onCopyChildPo = null;


	/**
	 * Static singleton accessor.
	 * 
	 * @return the singleton instance.
	 */
	public static PoCopier get() {
		return copier;
	}

	/**
	 * Creates a new PoCopier object.
	 *
	 */
	private PoCopier() {
		super();
		copiers.registerSelection(Integer.class, new ImmutableObjectCopier<Integer>());
		copiers.registerSelection(Short.class, new ImmutableObjectCopier<Short>());
		copiers.registerSelection(Long.class, new ImmutableObjectCopier<Long>());
		copiers.registerSelection(Float.class, new ImmutableObjectCopier<Float>());
		copiers.registerSelection(Double.class, new ImmutableObjectCopier<Double>());
		copiers.registerSelection(Byte.class, new ImmutableObjectCopier<Byte>());
		copiers.registerSelection(Character.class, new ImmutableObjectCopier<Character>());
		copiers.registerSelection(String.class, new ImmutableObjectCopier<String>());
		copiers.registerSelection(BigDecimal.class, new ImmutableObjectCopier<BigDecimal>());

		copiers.registerSelection(int.class, new ImmutableObjectCopier<Integer>());
		copiers.registerSelection(short.class, new ImmutableObjectCopier<Short>());
		copiers.registerSelection(long.class, new ImmutableObjectCopier<Long>());
		copiers.registerSelection(float.class, new ImmutableObjectCopier<Float>());
		copiers.registerSelection(double.class, new ImmutableObjectCopier<Double>());
		copiers.registerSelection(byte.class, new ImmutableObjectCopier<Byte>());
		copiers.registerSelection(char.class, new ImmutableObjectCopier<Character>());

		copiers.registerSelection(Date.class, new DateCopier());

		/*
		 * I don't like this, because it is not really copying, but replacing.
		 * This shouldn't be here. Modification record should be reset by deepCopy(o).
		 */
		copiers.registerSelection(ModificationRecordImpl.class,
				new Copy<ModificationRecordImpl>(ModificationRecordImpl.class));

		copiers.registerSelection(Money.class, new MoneyCopier());
		copiers.registerSelection(Key.class, new KeyCopier());
		copiers.registerSelection(DetachStrategy.class, new NullCopier<DetachStrategy>());
	}

	/**
	 * Finds a registered copier and has the object copied by him.
	 * 
	 * @param <T>
	 * @param objectToCopy
	 * 
	 * @return the copied object.
	 */
	@SuppressWarnings("nls")
	public <T> T copy(T objectToCopy) {
		@SuppressWarnings("unchecked")
		Copier<T> c = (Copier<T>) copiers.select(objectToCopy);
		if(c==null) {
			if (logger.isTraceEnabled()) {
				String msg = StringUtils.concat(
						"No suitable copier found for class ",
						objectToCopy.getClass().getName(),
						". Returning a reference to the same object.");
				logger.trace(msg);
			}
			return objectToCopy;
		} else {
			logger.trace("Using copier: " + c.getClass().getName());
			return c.copy(objectToCopy);
		}
	}

	/**
	 * Registers a copier of a type.
	 * 
	 * @param <T>
	 * @param type type to register
	 * @param c corresponding copier
	 */
	@SuppressWarnings("nls")
	public <T> void register(Class<?> type, Copier<T> c) {
		if(PersistentObject.class.isAssignableFrom(type)) {
			logger.warn("Are you sure that a copier for " + type.getName() + " must be registered?");
		}
		copiers.registerSelection(type, c);
	}

	/**
	 * Gets the onCopyPo.
	 *
	 * @return Returns the onCopyPo
	 */
	public Modification<PersistentObject<?>> getOnCopyPo() {
		return onCopyPo;
	}

	/**
	 * Assigns a new value to the onCopyPo.
	 *
	 * @param onCopyPo the onCopyPo to set
	 */
	public void setOnCopyPo(Modification<PersistentObject<?>> onCopyPo) {
		copier.onCopyPo = onCopyPo;
	}

	/**
	 * Gets the onCopyChildPo.
	 *
	 * @return Returns the onCopyChildPo
	 */
	public Modification<PersistentObject<?>> getOnCopyChildPo() {
		return onCopyChildPo;
	}

	/**
	 * Assigns a new value to the onCopyChildPo.
	 *
	 * @param onCopyChildPo the onCopyChildPo to set
	 */
	public void setOnCopyChildPo(
			Modification<PersistentObject<?>> onCopyChildPo) {
		copier.onCopyChildPo = onCopyChildPo;
	}

}
