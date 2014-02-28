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
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.hibernate.HibernateBo2Utils;
import gr.interamerican.bo2.impl.open.po.PoReattachAnalysis.PoReattachAnalysisResult;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

/**
 * This adapter accepts an {@link AbstractBasePo} instance inspects
 * its fields and produces a List of objects that may be considered
 * for manual re-attaching to the Hibernate Session and a list of
 * objects that are currently transient and should not be affected
 * by the re-attachment process.
 * <br/>
 * The two lists are returned as a {@link PoReattachAnalysisResult}.
 * <br/>
 * This is achieved by inspecting recursively owned entities and
 * owned collections of entities and including in the results referenced
 * entities and referenced collections of entities.
 * <br/>
 * No force initialization of lazy proxies of entities or collections
 * of entities is caused by this adapter. The application of the adapter
 * to an {@link AbstractBasePo} does not alter its state. 
 * <br/>
 * 
 * @see DetachStrategy#reattach(Object, gr.interamerican.bo2.arch.Provider)
 */
public class PoReattachAnalysis implements Transformation<AbstractBasePo<?>, PoReattachAnalysisResult> {
	
	/**
	 * Singleton.
	 */
	private static final PoReattachAnalysis INSTANCE = new PoReattachAnalysis();
	
	/**
	 * Singleton accessor.
	 * 
	 * @return Singleton.
	 */
	public static PoReattachAnalysis get() {
		return INSTANCE;
	}

	public PoReattachAnalysisResult execute(AbstractBasePo<?> a) {
		PoReattachAnalysisResult analysis = new PoReattachAnalysisResult();
		
		analysis.getPosToReattachManually().addAll(getReferences(a));
		if(HibernateBo2Utils.isTransient(a)) {
			analysis.getTransientPos().add(a);
		}
		
		for(Object child : ReflectionUtils.get(a.getChildFields(), a)) {
			if(child instanceof HibernateProxy) {
				HibernateProxy proxy = (HibernateProxy) child;
				if(proxy.getHibernateLazyInitializer().isUninitialized()) {
					continue;
				}
				PoReattachAnalysisResult intermediate = INSTANCE.execute((AbstractBasePo<?>) proxy.getHibernateLazyInitializer().getImplementation());
				analysis.getPosToReattachManually().addAll(intermediate.getPosToReattachManually());
				analysis.getTransientPos().addAll(intermediate.getTransientPos());
			} else if(child instanceof PersistentCollection) {
				PersistentCollection pColl = (PersistentCollection) child;
				if(!pColl.wasInitialized()) {
					continue;
				}
				for(Object obj : (Collection<?>) pColl) {
					if(obj instanceof HibernateProxy) {
						HibernateProxy proxy = (HibernateProxy) obj;
						if(proxy.getHibernateLazyInitializer().isUninitialized()) {
							continue;
						}
						PoReattachAnalysisResult intermediate = INSTANCE.execute((AbstractBasePo<?>) proxy.getHibernateLazyInitializer().getImplementation());
						analysis.getPosToReattachManually().addAll(intermediate.getPosToReattachManually());
						analysis.getTransientPos().addAll(intermediate.getTransientPos());
					} else if(obj instanceof AbstractBasePo) {
						PoReattachAnalysisResult intermediate = INSTANCE.execute((AbstractBasePo<?>) obj);
						analysis.getPosToReattachManually().addAll(intermediate.getPosToReattachManually());
						analysis.getTransientPos().addAll(intermediate.getTransientPos());
					}
				}
			} else if (child instanceof AbstractBasePo){ 
				PoReattachAnalysisResult intermediate = INSTANCE.execute((AbstractBasePo<?>) child);
				analysis.getPosToReattachManually().addAll(intermediate.getPosToReattachManually());
				analysis.getTransientPos().addAll(intermediate.getTransientPos());
			} else if (child instanceof Collection) {
				for(Object obj : (Collection<?>) child) {
					if(obj instanceof AbstractBasePo) {
						PoReattachAnalysisResult intermediate = INSTANCE.execute((AbstractBasePo<?>) obj);
						analysis.getPosToReattachManually().addAll(intermediate.getPosToReattachManually());
						analysis.getTransientPos().addAll(intermediate.getTransientPos());
					}
				}
			}
		}
		
		return analysis;
	}
	
	/**
	 * There are two possible scenarios for references that need re-attaching:<br/>
	 * <li>A non-child {@link PersistentObject} that is an initialized {@link HibernateProxy}.</li>
	 * <li>The elements of a non-child {@link PersistentCollection} that has been initialized.</li>
	 * <li>A non-child {@link PersistentObject}</li>
	 * <li>The elements of a non-child {@link Collection} of {@link PersistentObject}s</li>
	 * 
	 * These are checked with the aforementioned order and only one case is taken into account.
	 * For example, an object that is both a HibernateProxy and a PersistentObject, will be
	 * treated as a HibernateProxy only.
	 * 
	 * @param a
	 *        AbstractBasePo instance.
	 *        
	 * @return Returns the references that need re-attaching for the current object.
	 */
	private List<Object> getReferences(AbstractBasePo<?> a) {
		List<Object> references = new ArrayList<Object>();
		for(Object nonChild : ReflectionUtils.get(a.getNonChildFields(), a)) {
			if(nonChild instanceof HibernateProxy) {
				references.add(nonChild);
			} else if(nonChild instanceof PersistentCollection) {
				PersistentCollection pColl = (PersistentCollection) nonChild;
				if(!(pColl instanceof Collection)) {
					continue;
				}
				if(pColl.wasInitialized()) {
					for(Object obj : (Collection<?>) pColl) {
						references.add(obj);
					}
				}
			} else if(nonChild instanceof PersistentObject) {
				references.add(nonChild);
			} else if(nonChild instanceof Collection) {
				for(Object obj : (Collection<?>) nonChild) {
					if(obj instanceof PersistentObject) {
						references.add(obj);
					}
				}
			}
		}
		return references;
	}
	
	/**
	 * Results bean
	 */
	public static class PoReattachAnalysisResult {
		
		/**
		 * Transient pos found.
		 */
		private final Set<Object> transientPos = new HashSet<Object>();
		
		/**
		 * Many-to-one association pos found.
		 */
		private final Set<Object> posToReattachManually = new HashSet<Object>();
		
		/**
		 * Gets the transientPos.
		 *
		 * @return Returns the transientPos
		 */
		public Set<Object> getTransientPos() {
			return transientPos;
		}

		/**
		 * Gets the posToReattachManually.
		 *
		 * @return Returns the posToReattachManually
		 */
		public Set<Object> getPosToReattachManually() {
			return posToReattachManually;
		}
		
	}
	
	/**
	 * Use Singleton instance.
	 */
	private PoReattachAnalysis() { /* hidden, empty */ }

}
