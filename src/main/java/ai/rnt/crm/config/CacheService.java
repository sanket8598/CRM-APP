package ai.rnt.crm.config;

import static ai.rnt.crm.constants.CacheConstant.CITY;
import static ai.rnt.crm.constants.CacheConstant.CONTACT;
import static ai.rnt.crm.constants.CacheConstant.COUNTRY;
import static ai.rnt.crm.constants.CacheConstant.DOMAIN;
import static ai.rnt.crm.constants.CacheConstant.EXCEL_HEADER;
import static ai.rnt.crm.constants.CacheConstant.LEADS;
import static ai.rnt.crm.constants.CacheConstant.LEAD_SOURCE;
import static ai.rnt.crm.constants.CacheConstant.ROLES;
import static ai.rnt.crm.constants.CacheConstant.SERVICE_FALLS;
import static ai.rnt.crm.constants.CacheConstant.STATES;
import static ai.rnt.crm.constants.SchedularConstant.EVERY_1_HOUR;
import static ai.rnt.crm.constants.SchedularConstant.EVERY_DAY_11_AM;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hazelcast.core.ICacheManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {

	private final ICacheManager cacheManager;

	public void evictAllCaches() {
		log.info("inside the CacheService evictAllCaches method");
		cacheManager.getCache(LEAD_SOURCE).destroy();
		cacheManager.getCache(ROLES).destroy();
		cacheManager.getCache(SERVICE_FALLS).destroy();
		cacheManager.getCache(LEADS).destroy();
		cacheManager.getCache(EXCEL_HEADER).destroy();
		cacheManager.getCache(STATES).destroy();
		cacheManager.getCache(COUNTRY).destroy();
		cacheManager.getCache(CITY).destroy();
		cacheManager.getCache(CONTACT).destroy();
		cacheManager.getCache(DOMAIN).destroy();
	}

	public void clearCacheWithGivenName(String cacheName) {
		cacheManager.getCache(cacheName).destroy();
	}

	@Scheduled(cron = EVERY_DAY_11_AM, zone = INDIA_ZONE) // Running Everyday at 11 AM
	@Scheduled(cron = EVERY_1_HOUR, zone = INDIA_ZONE) // Running Everyday 1 hour
	public void evictAllcachesAtIntervals() {
		log.info("inside the CacheService evictAllcachesAtIntervals method");
		evictAllCaches();
	}
}
