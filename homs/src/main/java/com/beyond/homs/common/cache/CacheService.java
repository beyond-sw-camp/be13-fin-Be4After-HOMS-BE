package com.beyond.homs.common.cache;

public interface CacheService {
    String get(String key);

    void set(String key, String value, long seconds);

    void delete(String key);
}
