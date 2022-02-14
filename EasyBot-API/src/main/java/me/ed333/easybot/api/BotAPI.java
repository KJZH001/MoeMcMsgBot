package me.ed333.easybot.api;

import me.ed333.easybot.api.utils.*;

public final class BotAPI {
    private static IBotUtils iBotUtils;
    private static IBotMiraiHttpUtils iBotMiraiHttpUtils;

    private static ICacheUtils iCacheUtils;
    private static IConfigManager iConfigManager;
    private static ILanguageUtils iLanguageUtils;

    /**
     * 初始化 IConfigManager 接口，该方法插件调用
     * @param iConfigManager configManager
     */
    public static void setIConfigManager(IConfigManager iConfigManager) {
        BotAPI.iConfigManager = iConfigManager;
    }

    /**
     * 初始化 IBotUtils 接口，该方法插件调用
     * @param ibu IBotUtils
     */
    public static void setIBotUtils(IBotUtils ibu) {
        BotAPI.iBotUtils = ibu;
    }

    /**
     * 初始化 IBotMiraiHttpUtils 接口, 该方法插件调用
     * @param ibmh IBotMiraiHttpUtils
     */
    public static void setIBotMiraiHttpUtils(IBotMiraiHttpUtils ibmh) {
        BotAPI.iBotMiraiHttpUtils = ibmh;
    }

    /**
     * 初始化 ICacheUtils 接口，该方法插件调用
     * @param iCacheUtils ICacheUtils
     */
    public static void setICacheUtils(ICacheUtils iCacheUtils) {
        BotAPI.iCacheUtils = iCacheUtils;
    }

    /**
     * 初始化 ILanguageUtils 接口，该方法插件调用
     * @param iLanguageUtils ILanguageUtils
     */
    public static void setILanguageUtils(ILanguageUtils iLanguageUtils) {
        BotAPI.iLanguageUtils = iLanguageUtils;
    }

    /**
     * 获取 bot 的通用工具接口
     * @return bot通用工具接口
     */
    public static IBotUtils getIbu() {
        return iBotUtils;
    }

    /**
     * 获取 Mirai-api-http 版本插件的额外接口, 该接口继承 {@link IBotUtils}
     * @return Mirai-api-http 版本插件的额外接口
     */
    public static IBotMiraiHttpUtils getIBotMiraiHttpUtils() {
        return iBotMiraiHttpUtils;
    }

    /**
     * 获取缓存工具接口
     * @return 缓存工具接口
     */
    public static ICacheUtils getICacheUtils() { return BotAPI.iCacheUtils; }

    /**
     * ConfigManager
     * @return ConfigManager
     */
    public static IConfigManager getiConfigManager() {
        return iConfigManager;
    }

    /**
     * ILanguageUtils
     * @return ILanguageUtils
     */
    public static ILanguageUtils getILanguageUtils() {
        return iLanguageUtils;
    }
}
