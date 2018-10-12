package moe.vergo.seasonalseiyuuapi.cache;

import moe.vergo.seasonalseiyuuapi.utils.RequestThrottling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

public class CacheServiceImpl<T> implements CacheService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    private String cacheName;
    private ConcurrentMap<Integer, T> cacheMap;

    public CacheServiceImpl(String cacheName, ConcurrentMap<Integer, T> cacheMap) {
        this.cacheName = cacheName;
        this.cacheMap = cacheMap;
    }

    public boolean isPresentInCache(Integer key) {
        boolean result = cacheMap.containsKey(key);
        if(!result) {
            RequestThrottling.throttleRequests();
        }

        return result;
    }

    public T getValueFromCache(Integer key) {
        LOGGER.info("Getting cached data for {} from cache {}", key, cacheName);
        return cacheMap.get(key);
    }

    public void addValueToCache(Integer key, T value) {
        cacheMap.put(key, value);
    }
}
