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
package test.ad.addb;


import java.io.IOException;
import java.util.List;

import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.AdDefinition;
import net.mad.ads.db.definition.impl.ad.image.ImageAdDefinition;
import net.mad.ads.db.model.format.impl.MediumRectangleAdFormat;
import net.mad.ads.db.model.type.impl.ImageAdType;
import net.mad.ads.db.services.AdFormats;
import net.mad.ads.db.services.AdTypes;

public class AdDBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		AdDB db = new AdDB();
		
		AdDBManager.getInstance().getAdDB().open();
		
		ImageAdDefinition ib = new ImageAdDefinition();
		ib.setFormat(AdFormats.forCompoundName(new MediumRectangleAdFormat().getCompoundName()));
		ib.setId("1");
		AdDBManager.getInstance().getAdDB().addBanner(ib);
		
		ib = new ImageAdDefinition();
		ib.setFormat(AdFormats.forCompoundName(new MediumRectangleAdFormat().getCompoundName()));
		ib.setId("2");
		AdDBManager.getInstance().getAdDB().addBanner(ib);
		
		AdDBManager.getInstance().getAdDB().reopen();
		
		AdRequest request = new AdRequest();
		request.getFormats().add(AdFormats.forCompoundName(new MediumRectangleAdFormat().getCompoundName()));
		request.getTypes().add(AdTypes.forType(ImageAdType.TYPE));
		
		List<AdDefinition> result = AdDBManager.getInstance().getAdDB().search(request);
		
		System.out.println(result.size());
		
		
		AdDBManager.getInstance().getAdDB().close();
	}

}
