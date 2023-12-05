package ai.rnt.crm.config;

import static ai.rnt.crm.constants.SchedularConstant.EVERY_DAY_11_AM;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacheService {

	private final CacheManager cacheManager;
	
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
	public void clearCacheWithGivenName(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}

	@Scheduled(cron=EVERY_DAY_11_AM ,zone=INDIA_ZONE)//Running Everyday at 11 AM
	public void evictAllcachesAtIntervals() {
	    evictAllCaches();
	}
}
