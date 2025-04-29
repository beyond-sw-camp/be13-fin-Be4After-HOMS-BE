package com.beyond.homs;

import com.beyond.homs.common.cache.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class RedisTest {
    @Autowired
    private CacheService cacheService;

    @Test
    public void testRedisCache() {
        // Set a value in the cache
        cacheService.set("testKey", "testValue", 60);

        // Get the value from the cache
        String value = cacheService.get("testKey");

        // Assert that the value is as expected
        assertThat(value).isEqualTo("testValue");

        // Delete the value from the cache
        cacheService.delete("testKey");

        // Assert that the value is no longer in the cache
        String deletedValue = cacheService.get("testKey");
        assertThat(deletedValue).isNull();
    }
}
