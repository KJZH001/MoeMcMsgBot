package me.ed333.easybot.api.events.groupevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroupMember;

public class MemberMuteEvent extends GroupEvent {
    private static JsonObject json;

    public MemberMuteEvent(JsonObject json) {
        super(json.get("data").getAsJsonObject());
        MemberMuteEvent.json = json.get("data").getAsJsonObject();
    }

    /**
     * 获取被禁言的时间
     * @return Integer
     */
    public Integer getDurationSeconds() {
        return json.get("durationSeconds").getAsInt();
    }

    /**
     * 获取被执行此操作的群员的属性 <br/>
     * @return IGroupMember
     */
    public IGroupMember getMember() {
        return BotAPI.getIbu().getGroup(
                json.get("group").getAsJsonObject().get("id").getAsLong()
        ).getGroupMember(
                json.get("member").getAsJsonObject().get("id").getAsLong()
        );
    }
}
