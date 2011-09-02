package net.mad.ads.server.utils.filter;

import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;

/**
 * Filter für das entfernen von Bannern, die die konfigurierte Anzahl
 * von Klicks für einen bestimmten Zeitraum schon erreicht oder überschritten haben
 * 
 * @author tmarx
 *
 */
public class ClickExpirationFilter implements Predicate<BannerDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(ClickExpirationFilter.class);
	
//	private static final ClickExpirationFilter FILTER = new ClickExpirationFilter();
//	
//	public static final Predicate<BannerDefinition> getFilter () {
//		return FILTER;
//	}
	
	@Override
	public boolean apply(BannerDefinition banner) {
		// Banner enthält keine View-Beschränkung
		if (!banner.hasConditionDefinition(ConditionDefinitions.CLICK_EXPIRATION)){
			return true;
		} else if (RuntimeContext.getTrackService() == null) {
			return true;
		}
		
		
		/*
		 * 1. Prüfen, ob die Clicks für diesen Monat schon erreicht wurden
		 * 2. Prüfen, ob die Clicks für diese Woche schon erreicht wurden
		 * 3. Prüfen, ob die Clicks für diesen Tag schon erreicht wurden
		 */
		
		ViewExpirationConditionDefinition def = (ViewExpirationConditionDefinition) banner.getConditionDefinition(ConditionDefinitions.CLICK_EXPIRATION);
		
		try {
			if (def.getViewExpirations().containsKey(ExpirationResolution.MONTH)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				int impressions = RuntimeContext.getTrackService().countClicks(banner.getId(), from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
			if (def.getViewExpirations().containsKey(ExpirationResolution.WEEK)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				from.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				to.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				int impressions = RuntimeContext.getTrackService().countClicks(banner.getId(), from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
			if (def.getViewExpirations().containsKey(ExpirationResolution.DAY)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				from.set(Calendar.DAY_OF_MONTH, 1);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				
				int lastDate = to.getActualMaximum(Calendar.DATE);
				to.set(Calendar.DATE, lastDate); 
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				int impressions = RuntimeContext.getTrackService().countClicks(banner.getId(), from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
		} catch (ServiceException e) {
			logger.error("", e);
		}
		
		return false;
	}

}
