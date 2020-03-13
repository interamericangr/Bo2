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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;


import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.po.utils.DefaultPoAnalyzer;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzerResolver;

import org.hibernate.metadata.ClassMetadata;

/**
 * {@link PoAnalyzerResolver} that also generates {@link HibernatePoAnalyzer}.
 */
public class HibernateAwarePoAnalyzerResolver implements PoAnalyzerResolver {

	@Override
	public PoAnalyzer getAnalyzer(Class<?> clz, String manager) {
		try {
			HibernateSessionProvider provider = Bo2Session.getProvider().getResource(manager,
					HibernateSessionProvider.class);
			ClassMetadata meta = provider.getHibernateSession().getSessionFactory().getClassMetadata(clz);
			if (meta != null) {
				return new HibernatePoAnalyzer(provider.getHibernateSession());
			}
		} catch (Exception e) {
			// do nothing - go to default
		}
		return new DefaultPoAnalyzer();
	}
}