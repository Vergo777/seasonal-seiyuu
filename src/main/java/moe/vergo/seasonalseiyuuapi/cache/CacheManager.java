package moe.vergo.seasonalseiyuuapi.cache;

public interface CacheManager<T> {
    public CacheService<T> initialiseCache(String cacheName);
}
