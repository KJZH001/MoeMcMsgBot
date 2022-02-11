package me.ed333.easybot.api.events.botevent;



import com.google.gson.JsonObject;
import me.ed333.easybot.api.events.BaseEvent;

/**
 * 所有 BotEvent 的超类，
 * 该类的超类为 BaseEvent
 */
public class BotEvent extends BaseEvent {
    private static JsonObject json;

    public BotEvent(JsonObject json) {
        BotEvent.json = json;
    }

    public long getBotId() {
        return json.get("qq").getAsLong();
    }
}
