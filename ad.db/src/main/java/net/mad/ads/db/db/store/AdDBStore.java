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
package net.mad.ads.db.db.store;

import java.io.IOException;

import net.mad.ads.db.definition.AdDefinition;

public interface AdDBStore {
	
	public void open() throws IOException;
	public void close() throws IOException;
	
	public void addBanner (AdDefinition banner) throws IOException;
	public void deleteBanner (String id) throws IOException;
	
	public AdDefinition getBanner (String id);
	
	public int size ();
}
