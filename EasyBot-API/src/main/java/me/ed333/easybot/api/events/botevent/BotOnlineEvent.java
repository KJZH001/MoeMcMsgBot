package me.ed333.easybot.api.events.botevent;


import com.google.gson.JsonObject;

/**
 * BOT 登录成功事件
 */
public class BotOnlineEvent extends BotEvent {
    public BotOnlineEvent(JsonObject json) {
        super(json);
    }
}
