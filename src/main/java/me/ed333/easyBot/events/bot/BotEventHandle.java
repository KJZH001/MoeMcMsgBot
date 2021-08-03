package me.ed333.easyBot.events.bot;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.BotEvent.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * <p>检查是否接收到了事件</p>
 * <p>如果接收到了事件就触发对应事件</p>
 */
public class BotEventHandle {
    public BotEventHandle(String event_json) {
        String EventType = JSON.parseObject(event_json).getString("type");
        PluginManager manager = Bukkit.getServer().getPluginManager();
        switch (EventType) {
            case"BotOnlineEvent":
                manager.callEvent(new BotOnlineEvent(event_json));
                break;
            case "BotOfflineEventActive":
                manager.callEvent(new BotOfflineEventActive(event_json));
                break;
            case "BotOfflineEventForce":
                manager.callEvent(new BotOfflineEventForce(event_json));
                break;
            case "BotOfflineEventDropped":
                manager.callEvent(new BotOfflineEventDropped(event_json));
                break;
            case "BotReloginEvent":
                manager.callEvent(new BotReloginEvent(event_json));
                break;
        }
    }
}
