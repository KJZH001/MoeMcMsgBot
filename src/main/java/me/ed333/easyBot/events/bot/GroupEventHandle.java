package me.ed333.easyBot.events.bot;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.GroupEvent.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * <p>检查是否接收到了事件</p>
 * <p>如果接收到了事件就触发对应事件</p>
 */
public class GroupEventHandle {
    public GroupEventHandle(String event_json) {
        String EventType = JSON.parseObject(event_json).getString("type");
        PluginManager manager = Bukkit.getServer().getPluginManager();
        switch (EventType) {
            case "MemberMuteEvent" :
                manager.callEvent(new MemberMuteEvent(event_json));
                break;
            case "MemberUnmuteEvent":
                manager.callEvent(new MemberUnmuteEvent(event_json));
                break;
            case "GroupMuteAllEvent" :
                manager.callEvent(new GroupMuteAllEvent(event_json));
                break;
            case "MemberLeaveEventKick":
                manager.callEvent(new MemberLeaveEventKick(event_json));
                break;
            case "MemberLeaveEventQuit":
                manager.callEvent(new MemberLeaveEventQuit(event_json));
                break;
        }
    }
}
