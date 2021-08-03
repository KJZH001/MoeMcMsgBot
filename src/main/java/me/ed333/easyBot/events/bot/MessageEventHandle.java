package me.ed333.easyBot.events.bot;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.MessageEvent.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * <p>检查是否接收到了事件</p>
 * <p>如果接收到了事件就触发对应事件</p>
 */
public class MessageEventHandle {
    public MessageEventHandle(String event_json) {
        String EventType = JSON.parseObject(event_json).getString("type");

        PluginManager manager = Bukkit.getServer().getPluginManager();
        switch (EventType) {
            case "FriendRecallEvent":
                manager.callEvent(new FriendRecallEvent(event_json));
                break;
            case "GroupRecallEvent":
                manager.callEvent(new GroupRecallEvent(event_json));
                break;
            case "GroupMessage":
                manager.callEvent(new GroupMessageReceiveEvent(event_json));
                break;
            case "FriendMessage":
                manager.callEvent(new FriendMessageReceiveEvent(event_json));
                break;
            case "TempMessage":
                manager.callEvent(new TempMessageReceiveEvent(event_json));
                break;
        }
    }
}
