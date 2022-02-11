package me.ed333.easybot.api.events.botevent;


import com.google.gson.JsonObject;

/**
 * Bot被挤下线
 */
public class BotOfflineEventForce extends BotEvent {
    public BotOfflineEventForce(JsonObject json) {
        super(json);
    }
}
