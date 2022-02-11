package me.ed333.easybot.api.events.messageevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroup;

public class GroupMessage extends MessageEvent {
    private final JsonObject json;
    public GroupMessage(JsonObject json) {
        super(json);
        this.json = json;
    }

    public IGroup getGroup() {
        return BotAPI.getIbu().getGroup(json.get("sender").getAsJsonObject().get("group").getAsJsonObject().get("id").getAsLong());
    }
}
