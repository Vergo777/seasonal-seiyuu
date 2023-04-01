package moe.vergo.seasonalseiyuuapi.adapter.in.web.cache;

public interface CacheManager<T> {
    public CacheService<T> initialiseCache(String cacheName);
}