package me.ed333.easybot.api.events.botevent;


import com.google.gson.JsonObject;

/**
 * Bot被服务器断开或因网络问题而掉线
 */
public class BotOfflineEventDropped extends BotEvent {
    public BotOfflineEventDropped(JsonObject json) {
        super(json);
    }
}
