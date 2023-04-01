package moe.vergo.seasonalseiyuuapi.adapter.in.web.cache;

import org.mapdb.DB;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

@Component
public class CacheManagerImpl<T> implements CacheManager<T> {
    @Autowired
    private DB db;

    public CacheService<T> initialiseCache(String cacheName) {
        ConcurrentMap<Integer, T> cacheMap = db.<Integer, T>hashMap(cacheName, Serializer.INTEGER, Serializer.JAVA).createOrOpen();
        return new CacheServiceImpl<>(cacheName, cacheMap);
    }
}