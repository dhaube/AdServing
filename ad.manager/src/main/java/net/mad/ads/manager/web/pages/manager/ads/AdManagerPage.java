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
package net.mad.ads.manager.web.pages.manager.ads;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.StringResourceModel;
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.DateUtil;
import net.mad.ads.manager.web.pages.BasePage;
import net.mad.ads.manager.web.pages.manager.campaign.data.CampaignDataProvider;
import net.mad.ads.manager.web.pages.manager.campaign.edit.EditCampaignPage;
import net.mad.ads.manager.web.pages.manager.campaign.edit.NewCampaignPage;
import net.mad.ads.manager.web.pages.manager.site.data.SiteDataProvider;
import net.mad.ads.manager.web.pages.manager.site.edit.EditSitePage;
import net.mad.ads.manager.web.pages.manager.site.edit.NewSitePage;

@AuthorizeInstantiation("ADMIN")
public class AdManagerPage extends BasePage {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdManagerPage.class);

	private static final long serialVersionUID = 701015869883210133L;

	public AdManagerPage() {
		super();

		add(new BookmarkablePageLink<Void>("newAd", NewCampaignPage.class)
				.add(new ButtonBehavior()));
		
		final List<Campaign> campaigns = new ArrayList<Campaign>();
		try {
			campaigns.addAll(RuntimeContext.getCampaignService().findAll());
		} catch (ServiceException e) {
			LOGGER.error("", e);
		}
		
		add(new DropDownChoice<Campaign>("campaigns", campaigns, new IChoiceRenderer<Campaign>() {
	        public String getDisplayValue(Campaign camp) {
	            return camp.getName();
	        }

	        public String getIdValue(Campaign camp, int index) {
	        	return String.valueOf(camp.getId());
	        }
	    }));

		
		DataView<Campaign> dataView = new DataView<Campaign>("pageable",
				new CampaignDataProvider()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Campaign> item) {
				final Campaign campaign = item.getModelObject();
				item.add(new Label("id", String.valueOf(campaign.getId())));
				item.add(new Label("name", campaign.getName()));
				item.add(new Label("created", DateUtil.format(campaign.getCreated())));
				item.add(new EditPanel("editAd", item.getModel()));

				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {
							private static final long serialVersionUID = 1L;

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
			}
		};

		dataView.setItemsPerPage(5);
		add(dataView);

		add(new PagingNavigator("navigator", dataView));
		
	}

	class EditPanel extends Panel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7925212999572127761L;

		/**
		 * @param id
		 *            component id
		 * @param model
		 *            model for contact
		 */
		public EditPanel(String id, IModel<Campaign> model) {
			super(id, model);
			add(new Link<Void>("edit") {
				@Override
				public void onClick() {
					setResponsePage(new EditCampaignPage((Campaign) getParent()
							.getDefaultModelObject()));
				}
			});
		}
	}
}
