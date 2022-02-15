package me.ed333.easybot.plugin.with_mirai_api_http.utils;

import me.ed333.easybot.api.utils.ICacheUtils;

import java.util.HashMap;

public class CacheUtils implements ICacheUtils {
    @Override
    public void save(String key, String path, Object cacheContent, String description) {

    }

    @Override
    public void saveAll(HashMap<Object, Object> cacheContentHashMap, String path, String description) {

    }

    @Override
    public String getCache(String key, String path) {
        return null;
    }

    @Override
    public HashMap<Object, Object> getAll(String path) {
        return null;
    }
}
