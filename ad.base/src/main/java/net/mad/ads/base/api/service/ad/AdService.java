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
package net.mad.ads.base.api.service.ad;

import java.util.Collection;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ads.Advertisement;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;

public interface AdService {
	
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	
	public void add(Advertisement obj) throws ServiceException;
    public void update(Advertisement obj) throws ServiceException;
    public void delete(Advertisement obj) throws ServiceException;
    public long count() throws ServiceException;
    public Advertisement findByPrimaryKey(String id) throws ServiceException;
    public List<Advertisement> findAll() throws ServiceException;
    public List<Advertisement> findAll(int first, int perPage) throws ServiceException;
    
    public List<Advertisement> byCampaign(Campaign campaign) throws ServiceException;
    public List<Advertisement> byCampaign(Campaign campaign, int first, int perPage) throws ServiceException;
}
