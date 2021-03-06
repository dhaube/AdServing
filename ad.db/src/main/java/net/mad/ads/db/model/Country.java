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
package net.mad.ads.db.model;

import java.io.Serializable;

import net.mad.ads.db.AdDBConstants;

public class Country implements Serializable {
	
	public static Country ALL = new Country(AdDBConstants.ADDB_AD_COUNTRY_ALL); 
	
	private String code = "";
	public Country (String code) {
		this.code = code;
	}
	
	public String getCode () {
		return this.code;
	}
}
