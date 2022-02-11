package me.ed333.easybot.api.events.botevent;


import com.google.gson.JsonObject;

/**
 * Bot主动重新登录
 */
public class BotReloginEvent extends BotEvent {
    public BotReloginEvent(JsonObject json) {
        super(json);
    }
}
