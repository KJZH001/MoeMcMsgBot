package me.ed333.easybot.api.utils;


import java.util.HashMap;

/**
 * 缓存工具接口
 */
public interface ICacheUtils {

    /**
     * 缓存内容
     * @param key 用于获取缓存
     * @param path 保存缓存文件的路径
     * @param cacheContent 缓存内容
     * @param description 对缓存文件的描述
     */
    void save(String key,  String path,  Object cacheContent, String description);

    /**
     * 缓存一个 HashMap 中的所有内容
     * @param cacheContentHashMap 存有内容从HashMap
     * @param path 保存缓存文件的路径
     * @param description 对缓存文件的描述
     */
    void saveAll(HashMap<Object, Object> cacheContentHashMap,  String path, String description);

    /**
     * 获取缓存内容
     * @param key 保存时的Key
     * @param path 保存缓存文件的路径
     * @return 缓存内容
     */
    String getCache(String key,  String path);

    /**
     * 获取缓存文件中的所有内容
     * @param path 保存缓存文件的路径
     * @return 缓存内容
     */
    HashMap<Object, Object> getAll(String path);
}
