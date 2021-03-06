/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.server.utils.filter;

import net.mad.ads.db.definition.AdDefinition;
import net.mad.ads.server.utils.RuntimeContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

/**
 * Dieser Filter verhindert das doppelte Anzeigen eines Banners auf der selben 
 * Seite für den selben Benutzer
 * 
 * die request_id ist von allen AdRequest die durch einen Pageview erzeugt werden identisch
 * 
 * @author thmarx
 *
 */
public class DuplicatAdFilter implements Predicate<AdDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(DuplicatAdFilter.class);
	
	private String requestID = null;
	
	public DuplicatAdFilter (String request_id) {
		this.requestID = request_id;
	}
	
	@Override
	public boolean apply(AdDefinition banner) {
		logger.debug("Requestid: " + RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId()));
		if (RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId())) {
			return false;
		}
		
		return true;
	}

}
