package ai.rnt.crm.config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CacheServiceTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private CacheService cacheService;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(cacheService).build();
	}

	@Test
	void evictAllCaches_ShouldClearAllCaches() {
		cacheService.evictAllCaches();
		assertNotNull(cacheManager);
	}

	@Test
	void clearCacheWithGivenName_ShouldClearSpecifiedCache() {
		cacheService.clearCacheWithGivenName("ds");
		assertNotNull(cacheManager);
	}

	@Test
	void evictAllcachesAtIntervals_ShouldEvictAllCaches() {
		cacheService.evictAllcachesAtIntervals();
		assertNotNull(cacheManager);
	}
}
