package me.ed333.easybot.api.events.groupevent;


import com.google.gson.JsonObject;

/**
 * 全员禁言事件
 */
public class GroupMuteAllEvent extends GroupEvent {
    private static JsonObject json;

    public GroupMuteAllEvent(JsonObject json) {
        super(json.get("data").getAsJsonObject());
        GroupMuteAllEvent.json = json.get("data").getAsJsonObject();
    }

    /**
     * 获取原有的禁言状态
     * @return boolean
     */
    public boolean getOriginStatus() {
        return json.get("origin").getAsBoolean();
    }

    /**
     * 获取现有的禁言状态
     * @return boolean
     */
    public boolean getCurrentStatus() {
        return json.get("current").getAsBoolean();
    }

}
