package com.cafe.storeservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CacheScheduler {

    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 * * * *")
    public void clearHourlyCache() {
        clearCache("sales:hourly");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearDailyCache() {
        clearCache("sales:daily");
    }

    private void clearCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) cache.clear();
    }
}
