package moe.vergo.seasonalseiyuuapi.cache;

public interface CacheService<T> {
    public boolean isPresentInCache(Integer key);
    public T getValueFromCache(Integer key);
    public void addValueToCache(Integer key, T value);
}
