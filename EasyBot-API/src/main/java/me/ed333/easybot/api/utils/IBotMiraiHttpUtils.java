package me.ed333.easybot.api.utils;

/**
 * Mirai-api-http 版本的插件的额外接口 <br/>
 * 该接口继承了 {@link IBotUtils}
 */
public interface IBotMiraiHttpUtils extends IBotUtils {

    /**
     * 仅 mirai-api-http 对接的 EasyBot 插件可用 <br/>
     * 获取 mirai-api-http 的版本 <br/>
     * @return mirai-api-http 的版本
     */
    String getApiVer();

    /**
     *  获取bot绑定的session
     * @return session
     */
    String getSession();
}
