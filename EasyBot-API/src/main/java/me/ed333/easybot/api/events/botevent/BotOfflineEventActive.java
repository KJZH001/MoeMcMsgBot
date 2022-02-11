package me.ed333.easybot.api.events.botevent;


import com.google.gson.JsonObject;

/**
 * Bot 主动离线事件
 */
public class BotOfflineEventActive extends BotEvent{
    public BotOfflineEventActive(JsonObject json) {
        super(json);
    }
}
